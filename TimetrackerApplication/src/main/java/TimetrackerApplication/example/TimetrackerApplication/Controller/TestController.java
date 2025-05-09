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

    @PostMapping("/test/create-test-user1")
    public ResponseEntity<ApiResponse> createTestUser1() {
        try {
            String email = "user1@example.com";

            if (userRepository.existsByEmail(email)) {
                return ResponseEntity.status(CONFLICT).body(new ApiResponse("Test user 1 already exists", false, null));
            }

            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("password"));
            user.setAdmin(false);
            User savedUser = userRepository.save(user);

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

            Category healthCategory = new Category();
            healthCategory.setCategoryName("Health");
            healthCategory.setDescription("Health and wellness activities");
            healthCategory.setUser(savedUser);
            Category savedHealthCategory = categoryRepository.save(healthCategory);


            TimeEntry workEntry = new TimeEntry();
            workEntry.setStartTime(LocalDateTime.now().minusDays(5));
            workEntry.setEndTime(LocalDateTime.now().minusDays(5).plusHours(2));
            workEntry.setActive(false);
            workEntry.setUser(savedUser);
            workEntry.setCategory(savedWorkCategory);
            timeEntryRepository.save(workEntry);

            TimeEntry studyEntry = new TimeEntry();
            studyEntry.setStartTime(LocalDateTime.now().minusDays(4));
            studyEntry.setEndTime(LocalDateTime.now().minusDays(4).plusHours(3));
            studyEntry.setActive(false);
            studyEntry.setUser(savedUser);
            studyEntry.setCategory(savedStudyCategory);
            timeEntryRepository.save(studyEntry);

            TimeEntry personalEntry = new TimeEntry();
            personalEntry.setStartTime(LocalDateTime.now().minusDays(3));
            personalEntry.setEndTime(LocalDateTime.now().minusDays(3).plusHours(1));
            personalEntry.setActive(false);
            personalEntry.setUser(savedUser);
            personalEntry.setCategory(savedPersonalCategory);
            timeEntryRepository.save(personalEntry);

            TimeEntry healthEntry = new TimeEntry();
            healthEntry.setStartTime(LocalDateTime.now().minusDays(2));
            healthEntry.setEndTime(LocalDateTime.now().minusDays(2).plusHours(2));
            healthEntry.setActive(false);
            healthEntry.setUser(savedUser);
            healthEntry.setCategory(savedHealthCategory);
            timeEntryRepository.save(healthEntry);

            UserDto userDto = userService.convertToDto(savedUser);

            return ResponseEntity.ok(new ApiResponse("Test user 1 created successfully", true, userDto));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse("Failed to create test user 1: " + e.getMessage(), false, null));
        }
    }

    @PostMapping("/test/create-test-user2")
    public ResponseEntity<ApiResponse> createTestUser2() {
        try {
            String email = "user2@example.com";

            if (userRepository.existsByEmail(email)) {
                return ResponseEntity.status(CONFLICT).body(new ApiResponse("Test user 2 already exists", false, null));
            }

            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("password"));
            user.setAdmin(false);
            User savedUser = userRepository.save(user);

            Category codingCategory = new Category();
            codingCategory.setCategoryName("Coding");
            codingCategory.setDescription("Programming tasks");
            codingCategory.setUser(savedUser);
            Category savedCodingCategory = categoryRepository.save(codingCategory);

            Category readingCategory = new Category();
            readingCategory.setCategoryName("Reading");
            readingCategory.setDescription("Books and articles");
            readingCategory.setUser(savedUser);
            Category savedReadingCategory = categoryRepository.save(readingCategory);

            Category exerciseCategory = new Category();
            exerciseCategory.setCategoryName("Exercise");
            exerciseCategory.setDescription("Physical activities");
            exerciseCategory.setUser(savedUser);
            Category savedExerciseCategory = categoryRepository.save(exerciseCategory);

            Category entertainmentCategory = new Category();
            entertainmentCategory.setCategoryName("Entertainment");
            entertainmentCategory.setDescription("Movies and games");
            entertainmentCategory.setUser(savedUser);
            Category savedEntertainmentCategory = categoryRepository.save(entertainmentCategory);


            TimeEntry codingEntry = new TimeEntry();
            codingEntry.setStartTime(LocalDateTime.now().minusDays(6));
            codingEntry.setEndTime(LocalDateTime.now().minusDays(6).plusHours(4));
            codingEntry.setActive(false);
            codingEntry.setUser(savedUser);
            codingEntry.setCategory(savedCodingCategory);
            timeEntryRepository.save(codingEntry);


            TimeEntry readingEntry = new TimeEntry();
            readingEntry.setStartTime(LocalDateTime.now().minusDays(5));
            readingEntry.setEndTime(LocalDateTime.now().minusDays(5).plusHours(2));
            readingEntry.setActive(false);
            readingEntry.setUser(savedUser);
            readingEntry.setCategory(savedReadingCategory);
            timeEntryRepository.save(readingEntry);


            TimeEntry exerciseEntry = new TimeEntry();
            exerciseEntry.setStartTime(LocalDateTime.now().minusDays(4));
            exerciseEntry.setEndTime(LocalDateTime.now().minusDays(4).plusHours(1));
            exerciseEntry.setActive(false);
            exerciseEntry.setUser(savedUser);
            exerciseEntry.setCategory(savedExerciseCategory);
            timeEntryRepository.save(exerciseEntry);


            TimeEntry entertainmentEntry = new TimeEntry();
            entertainmentEntry.setStartTime(LocalDateTime.now().minusDays(3));
            entertainmentEntry.setEndTime(LocalDateTime.now().minusDays(3).plusHours(3));
            entertainmentEntry.setActive(false);
            entertainmentEntry.setUser(savedUser);
            entertainmentEntry.setCategory(savedEntertainmentCategory);
            timeEntryRepository.save(entertainmentEntry);


            UserDto userDto = userService.convertToDto(savedUser);

            return ResponseEntity.ok(new ApiResponse("Test user 2 created successfully", true, userDto));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse("Failed to create test user 2: " + e.getMessage(), false, null));
        }
    }

    @PostMapping("/test/create-test-user3")
    public ResponseEntity<ApiResponse> createTestUser3() {
        try {
            String email = "user3@example.com";

            if (userRepository.existsByEmail(email)) {
                return ResponseEntity.status(CONFLICT).body(new ApiResponse("Test user 3 already exists", false, null));
            }

            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("password"));
            user.setAdmin(false);
            User savedUser = userRepository.save(user);

            Category projectACategory = new Category();
            projectACategory.setCategoryName("Project A");
            projectACategory.setDescription("Project A tasks");
            projectACategory.setUser(savedUser);
            Category savedProjectACategory = categoryRepository.save(projectACategory);

            Category projectBCategory = new Category();
            projectBCategory.setCategoryName("Project B");
            projectBCategory.setDescription("Project B tasks");
            projectBCategory.setUser(savedUser);
            Category savedProjectBCategory = categoryRepository.save(projectBCategory);

            Category projectCCategory = new Category();
            projectCCategory.setCategoryName("Project C");
            projectCCategory.setDescription("Project C tasks");
            projectCCategory.setUser(savedUser);
            Category savedProjectCCategory = categoryRepository.save(projectCCategory);

            Category projectDCategory = new Category();
            projectDCategory.setCategoryName("Project D");
            projectDCategory.setDescription("Project D tasks");
            projectDCategory.setUser(savedUser);
            Category savedProjectDCategory = categoryRepository.save(projectDCategory);


            TimeEntry projectAEntry = new TimeEntry();
            projectAEntry.setStartTime(LocalDateTime.now().minusDays(7));
            projectAEntry.setEndTime(LocalDateTime.now().minusDays(7).plusHours(5));
            projectAEntry.setActive(false);
            projectAEntry.setUser(savedUser);
            projectAEntry.setCategory(savedProjectACategory);
            timeEntryRepository.save(projectAEntry);

            TimeEntry projectBEntry = new TimeEntry();
            projectBEntry.setStartTime(LocalDateTime.now().minusDays(6));
            projectBEntry.setEndTime(LocalDateTime.now().minusDays(6).plusHours(3));
            projectBEntry.setActive(false);
            projectBEntry.setUser(savedUser);
            projectBEntry.setCategory(savedProjectBCategory);
            timeEntryRepository.save(projectBEntry);

            TimeEntry projectCEntry = new TimeEntry();
            projectCEntry.setStartTime(LocalDateTime.now().minusDays(5));
            projectCEntry.setEndTime(LocalDateTime.now().minusDays(5).plusHours(4));
            projectCEntry.setActive(false);
            projectCEntry.setUser(savedUser);
            projectCEntry.setCategory(savedProjectCCategory);
            timeEntryRepository.save(projectCEntry);

            TimeEntry projectDEntry = new TimeEntry();
            projectDEntry.setStartTime(LocalDateTime.now().minusDays(4));
            projectDEntry.setEndTime(LocalDateTime.now().minusDays(4).plusHours(2));
            projectDEntry.setActive(false);
            projectDEntry.setUser(savedUser);
            projectDEntry.setCategory(savedProjectDCategory);
            timeEntryRepository.save(projectDEntry);

            UserDto userDto = userService.convertToDto(savedUser);

            return ResponseEntity.ok(new ApiResponse("Test user 3 created successfully", true, userDto));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse("Failed to create test user 3: " + e.getMessage(), false, null));
        }
    }

    @PostMapping("/test/create-test-user4")
    public ResponseEntity<ApiResponse> createTestUser4() {
        try {
            String email = "user4@example.com";

            if (userRepository.existsByEmail(email)) {
                return ResponseEntity.status(CONFLICT).body(new ApiResponse("Test user 4 already exists", false, null));
            }

            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("password"));
            user.setAdmin(false);
            User savedUser = userRepository.save(user);

            Category morningCategory = new Category();
            morningCategory.setCategoryName("Morning");
            morningCategory.setDescription("Morning activities");
            morningCategory.setUser(savedUser);
            Category savedMorningCategory = categoryRepository.save(morningCategory);

            Category afternoonCategory = new Category();
            afternoonCategory.setCategoryName("Afternoon");
            afternoonCategory.setDescription("Afternoon activities");
            afternoonCategory.setUser(savedUser);
            Category savedAfternoonCategory = categoryRepository.save(afternoonCategory);

            Category eveningCategory = new Category();
            eveningCategory.setCategoryName("Evening");
            eveningCategory.setDescription("Evening activities");
            eveningCategory.setUser(savedUser);
            Category savedEveningCategory = categoryRepository.save(eveningCategory);

            Category nightCategory = new Category();
            nightCategory.setCategoryName("Night");
            nightCategory.setDescription("Night activities");
            nightCategory.setUser(savedUser);
            Category savedNightCategory = categoryRepository.save(nightCategory);

            TimeEntry morningEntry = new TimeEntry();
            morningEntry.setStartTime(LocalDateTime.now().minusDays(8));
            morningEntry.setEndTime(LocalDateTime.now().minusDays(8).plusHours(3));
            morningEntry.setActive(false);
            morningEntry.setUser(savedUser);
            morningEntry.setCategory(savedMorningCategory);
            timeEntryRepository.save(morningEntry);

            TimeEntry afternoonEntry = new TimeEntry();
            afternoonEntry.setStartTime(LocalDateTime.now().minusDays(7));
            afternoonEntry.setEndTime(LocalDateTime.now().minusDays(7).plusHours(4));
            afternoonEntry.setActive(false);
            afternoonEntry.setUser(savedUser);
            afternoonEntry.setCategory(savedAfternoonCategory);
            timeEntryRepository.save(afternoonEntry);

            TimeEntry eveningEntry = new TimeEntry();
            eveningEntry.setStartTime(LocalDateTime.now().minusDays(6));
            eveningEntry.setEndTime(LocalDateTime.now().minusDays(6).plusHours(2));
            eveningEntry.setActive(false);
            eveningEntry.setUser(savedUser);
            eveningEntry.setCategory(savedEveningCategory);
            timeEntryRepository.save(eveningEntry);

            TimeEntry nightEntry = new TimeEntry();
            nightEntry.setStartTime(LocalDateTime.now().minusDays(5));
            nightEntry.setEndTime(LocalDateTime.now().minusDays(5).plusHours(1));
            nightEntry.setActive(false);
            nightEntry.setUser(savedUser);
            nightEntry.setCategory(savedNightCategory);
            timeEntryRepository.save(nightEntry);

            UserDto userDto = userService.convertToDto(savedUser);

            return ResponseEntity.ok(new ApiResponse("Test user 4 created successfully", true, userDto));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse("Failed to create test user 4: " + e.getMessage(), false, null));
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
