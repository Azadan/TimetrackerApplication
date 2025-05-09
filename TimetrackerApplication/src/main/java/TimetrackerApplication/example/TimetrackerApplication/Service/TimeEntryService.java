package TimetrackerApplication.example.TimetrackerApplication.Service;

import TimetrackerApplication.example.TimetrackerApplication.DTO.StatisticsDto;
import TimetrackerApplication.example.TimetrackerApplication.DTO.TimeEntryDTO;
import TimetrackerApplication.example.TimetrackerApplication.Exceptions.TimeEntryNotFoundException;
import TimetrackerApplication.example.TimetrackerApplication.Exceptions.UserNotFoundException;
import TimetrackerApplication.example.TimetrackerApplication.Model.Category;
import TimetrackerApplication.example.TimetrackerApplication.Model.TimeEntry;
import TimetrackerApplication.example.TimetrackerApplication.Model.User;
import TimetrackerApplication.example.TimetrackerApplication.Repository.TimeEntryRepository;
import TimetrackerApplication.example.TimetrackerApplication.Request.CheckInRequest;
import TimetrackerApplication.example.TimetrackerApplication.Request.CheckOutRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeEntryService {
    private final TimeEntryRepository timeEntryRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public TimeEntry checkIn(CheckInRequest req) {
        User user = userService.getUserById(req.getUserId());
        Category category = categoryService.getCategoryByIdAndUser(req.getCategoryId(), req.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        boolean activeTimeEntries = user.getTimeEntries().stream()
                .anyMatch(timeEntry -> timeEntry.isActive());
        if (activeTimeEntries) {
            throw new IllegalStateException("User has active time entries");
        }
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setStartTime(LocalDateTime.now());
        timeEntry.setEndTime(null);
        timeEntry.setActive(true);
        timeEntry.setUser(user);
        timeEntry.setCategory(category);
        return timeEntryRepository.save(timeEntry);
    }

    public TimeEntry checkOut(CheckOutRequest req) {
        TimeEntry timeEntry = timeEntryRepository.findById(req.getEntryId())
                .orElseThrow(() -> new IllegalArgumentException("Time entry not found"));
        if (!timeEntry.getUser().getUserId().equals(req.getUserId())) {
            throw new IllegalArgumentException("User does not own this time entry");
        }
        if (!timeEntry.isActive()) {
            throw new IllegalStateException("Time entry is already checked out");
        }
        timeEntry.setEndTime(LocalDateTime.now());
        timeEntry.setActive(false);
        return timeEntryRepository.save(timeEntry);
    }

    public List<TimeEntry> getAllEntriesByUserId (Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User has not been found");
        }
        return user.getTimeEntries();
    }


    public Map<String, Long> calculateStatistics(Long userId) {
        List<TimeEntry> timeEntries = getAllEntriesByUserId(userId);
        if (timeEntries.isEmpty()) {
            throw new TimeEntryNotFoundException("No time entries found for this user");
        }

        LocalDateTime now = LocalDateTime.now(); // Assuming this is Thursday
        LocalDateTime startOfWeek = now.minusDays(now.getDayOfWeek().getValue() - 1).toLocalDate().atStartOfDay();
        LocalDateTime endOfWeek = startOfWeek.plusDays(7).minusNanos(1);

        return timeEntries.stream()
                .filter(entry -> !entry.isActive() && entry.getEndTime() != null)
                .filter(entry -> !entry.getStartTime().isBefore(startOfWeek) && !entry.getStartTime().isAfter(endOfWeek))
                .collect(Collectors.groupingBy(
                        entry -> entry.getCategory().getCategoryName(),
                        Collectors.summingLong(entry -> {
                            Duration duration = Duration.between(entry.getStartTime(), entry.getEndTime());
                            return duration.toMinutes();
                        })
                ));
    }

    public TimeEntry getActiveTimeEntryById(Long entryId) {
        User user = userService.getUserById(entryId);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return user.getTimeEntries().stream()
                .filter(TimeEntry::isActive)
                .findFirst()
                .orElse(null);
    }

    public List<TimeEntryDTO> convertToDtoList(List<TimeEntry> timeEntries) {
        return timeEntries.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TimeEntryDTO convertToDto(TimeEntry timeEntry) {
        return new TimeEntryDTO(
                timeEntry.getEntryId(),
                timeEntry.getStartTime(),
                timeEntry.getEndTime(),
                timeEntry.isActive(),
                timeEntry.getUser().getUserId(),
                timeEntry.getCategory().getCategoryId()
        );
    }

    public List<StatisticsDto> convertStatisticsToDto(Map<String, Long> statistics) {
        return statistics.entrySet().stream()
                .map(entry -> new StatisticsDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
