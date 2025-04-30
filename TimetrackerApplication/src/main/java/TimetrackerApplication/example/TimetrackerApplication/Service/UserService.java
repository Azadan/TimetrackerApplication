package TimetrackerApplication.example.TimetrackerApplication.Service;

import TimetrackerApplication.example.TimetrackerApplication.DTO.UserDto;
import TimetrackerApplication.example.TimetrackerApplication.Exceptions.UserAlreadyExistException;
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

    public UserDto convertToDto(User user) {
        List<Long> categoryId = user.getCategories().stream()
                .map(Category::getCategoryId)
                .toList();

    return new UserDto(
            user.getUserId(),
            user.getEmail(),
            user.isAdmin(),
            categoryId
    );
    }
}