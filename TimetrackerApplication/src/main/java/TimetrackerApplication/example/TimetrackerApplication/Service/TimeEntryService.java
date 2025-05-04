package TimetrackerApplication.example.TimetrackerApplication.Service;

import TimetrackerApplication.example.TimetrackerApplication.Model.Category;
import TimetrackerApplication.example.TimetrackerApplication.Model.TimeEntry;
import TimetrackerApplication.example.TimetrackerApplication.Model.User;
import TimetrackerApplication.example.TimetrackerApplication.Repository.CategoryRepository;
import TimetrackerApplication.example.TimetrackerApplication.Repository.TimeEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimeEntryService {
    private final TimeEntryRepository timeEntryRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public TimeEntry checkIn(Long userId, Long categoryId) {
        User user = userService.getUserById(userId);
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setStartTime(LocalDateTime.now());
        timeEntry.setActive(true);
        timeEntry.setUser(user);


    }

    private void timeAndDateFormatter(TimeEntry timeEntry) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String formatted = timeEntry.getStartTime().format(formatter);
    }
}
