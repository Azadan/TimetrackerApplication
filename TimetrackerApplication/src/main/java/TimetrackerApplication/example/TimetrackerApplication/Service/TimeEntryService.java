package TimetrackerApplication.example.TimetrackerApplication.Service;

import TimetrackerApplication.example.TimetrackerApplication.DTO.TimeEntryDTO;
import TimetrackerApplication.example.TimetrackerApplication.Model.Category;
import TimetrackerApplication.example.TimetrackerApplication.Model.TimeEntry;
import TimetrackerApplication.example.TimetrackerApplication.Model.User;
import TimetrackerApplication.example.TimetrackerApplication.Repository.CategoryRepository;
import TimetrackerApplication.example.TimetrackerApplication.Repository.TimeEntryRepository;
import TimetrackerApplication.example.TimetrackerApplication.Request.CheckInRequest;
import TimetrackerApplication.example.TimetrackerApplication.Request.CheckOutRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
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
}
