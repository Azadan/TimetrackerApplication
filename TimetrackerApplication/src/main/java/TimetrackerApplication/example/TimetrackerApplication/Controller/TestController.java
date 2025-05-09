package TimetrackerApplication.example.TimetrackerApplication.Controller;


import TimetrackerApplication.example.TimetrackerApplication.DTO.UserDto;
import TimetrackerApplication.example.TimetrackerApplication.Model.Category;
import TimetrackerApplication.example.TimetrackerApplication.Model.TimeEntry;
import TimetrackerApplication.example.TimetrackerApplication.Model.User;
import TimetrackerApplication.example.TimetrackerApplication.Repository.CategoryRepository;
import TimetrackerApplication.example.TimetrackerApplication.Repository.TimeEntryRepository;
import TimetrackerApplication.example.TimetrackerApplication.Repository.UserRepository;
import TimetrackerApplication.example.TimetrackerApplication.Response.ApiResponse;
import TimetrackerApplication.example.TimetrackerApplication.Service.TimeEntryService;
import TimetrackerApplication.example.TimetrackerApplication.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.CONFLICT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class TestController {

    private final TimeEntryService timeEntryService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TimeEntryRepository timeEntryRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    @GetMapping("/test/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    public String userAccess() {
        return "User Content.";
    }

    @PostMapping("/test/create-test-user")
    public ResponseEntity<ApiResponse> createTestUser() {
        try {
            // Check if user already exists
            if (userRepository.existsByEmail("tes1t@example.com")) {
                return ResponseEntity.status(CONFLICT).body(new ApiResponse("Test user already exists", false, null));
            }

            // Create user

            User user = new User();
            user.setEmail("test1@example.com");
            user.setPassword(passwordEncoder.encode("password"));
            user.setAdmin(false);
            User savedUser = userRepository.save(user);

            // Create categories
            Category workCategory = new Category();
            workCategory.setCategoryName("Work");
            workCategory.setDescription("Work-related activities");
            workCategory.setUser(savedUser);
            Category savedWorkCategory = categoryRepository.save(workCategory);

            Category studyCategory = new Category();
            studyCategory.setCategoryName("Study");
            studyCategory.setDescription("Learning and education");
            studyCategory.setUser(savedUser);
            Category savedStudyCategory = categoryRepository.save(studyCategory);

            Category personalCategory = new Category();
            personalCategory.setCategoryName("Personal");
            personalCategory.setDescription("Personal activities and errands");
            personalCategory.setUser(savedUser);
            Category savedPersonalCategory = categoryRepository.save(personalCategory);

            // Create time entries
            // Completed work entry
            TimeEntry workEntry = new TimeEntry();
            workEntry.setStartTime(LocalDateTime.now().minusDays(2));
            workEntry.setEndTime(LocalDateTime.now().minusDays(2).plusHours(4));
            workEntry.setActive(false);
            workEntry.setUser(savedUser);
            workEntry.setCategory(savedWorkCategory);
            timeEntryRepository.save(workEntry);

            // Completed study entry
            TimeEntry studyEntry = new TimeEntry();
            studyEntry.setStartTime(LocalDateTime.now().minusDays(1));
            studyEntry.setEndTime(LocalDateTime.now().minusDays(1).plusHours(2));
            studyEntry.setActive(false);
            studyEntry.setUser(savedUser);
            studyEntry.setCategory(savedStudyCategory);
            timeEntryRepository.save(studyEntry);

            // Active personal entry
            TimeEntry activeEntry = new TimeEntry();
            activeEntry.setStartTime(LocalDateTime.now().minusHours(1));
            activeEntry.setEndTime(null);
            activeEntry.setActive(true);
            activeEntry.setUser(savedUser);
            activeEntry.setCategory(savedPersonalCategory);
            timeEntryRepository.save(activeEntry);

            // Convert user to DTO for response
            UserDto userDto = userService.convertToDto(savedUser);

            return ResponseEntity.ok(new ApiResponse("Test user created successfully with categories and time entries", true, userDto));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse("Failed to create test user: " + e.getMessage(), false, null));
        }
    }

    @PostMapping("/test/create-admin")
    public ResponseEntity<ApiResponse> createAdminUser() {
        try {
            String adminEmail = "admin@example.com";
            String adminPassword = "admin123";


            if (userRepository.existsByEmail(adminEmail)) {
                return ResponseEntity.ok(new ApiResponse("Admin user already exists", true, null));
            }


            User adminUser = new User();
            adminUser.setEmail(adminEmail);
            adminUser.setPassword(passwordEncoder.encode(adminPassword));
            adminUser.setAdmin(true);


            User savedUser = userRepository.save(adminUser);


            UserDto userDto = userService.convertToDto(savedUser);

            return ResponseEntity.ok(new ApiResponse("Admin user created successfully", true, userDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse("Error creating admin user: " + e.getMessage(), false, null));
        }
    }
}
