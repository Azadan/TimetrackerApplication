package TimetrackerApplication.example.TimetrackerApplication.Service;

import TimetrackerApplication.example.TimetrackerApplication.DTO.CategoryDto;
import TimetrackerApplication.example.TimetrackerApplication.DTO.TimeEntryDTO;
import TimetrackerApplication.example.TimetrackerApplication.DTO.UserDto;
import TimetrackerApplication.example.TimetrackerApplication.Exceptions.UserAlreadyExistException;
import TimetrackerApplication.example.TimetrackerApplication.Exceptions.UserNotFoundException;
import TimetrackerApplication.example.TimetrackerApplication.Model.Category;
import TimetrackerApplication.example.TimetrackerApplication.Model.User;
import TimetrackerApplication.example.TimetrackerApplication.Repository.UserRepository;
import TimetrackerApplication.example.TimetrackerApplication.Request.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(CreateUserRequest createUserRequest) {
        return Optional.of(createUserRequest).filter(user -> !userRepository.existsByEmail(user.getEmail()))
                .map(createUserRequest1 -> {
                    User user = new User();
                    user.setEmail(createUserRequest1.getEmail());
                    user.setPassword(createUserRequest1.getPassword());
                    return userRepository.save(user);
                }).orElseThrow(() -> new UserAlreadyExistException("User with this email already exists"));
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with this " + userId + " not found"));
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (!users.isEmpty()) {
            return users.stream()
                    .map(this::convertToDto)
                    .toList();
    } else {
            throw new UserNotFoundException("No users found");
        }
    }

    public UserDto convertToDto(User user) {
        List<CategoryDto> categoryDtos = user.getCategories().stream()
                .map(category -> new CategoryDto(
                        category.getCategoryName(),
                        category.getDescription(),
                        user.getUserId(),
                        category.getCategoryId()
                ))
                .toList();

        List<TimeEntryDTO> timeEntryDtos = user.getTimeEntries().stream()
                .map(timeEntry -> new TimeEntryDTO(
                        timeEntry.getEntryId(),
                        timeEntry.getStartTime(),
                        timeEntry.getEndTime(),
                        timeEntry.isActive(),
                        user.getUserId(),
                        timeEntry.getCategory().getCategoryId()
                ))
                .toList();

    return new UserDto(
            user.getUserId(),
            user.getEmail(),
            user.isAdmin(),
            categoryDtos,
            timeEntryDtos
    );
    }
}