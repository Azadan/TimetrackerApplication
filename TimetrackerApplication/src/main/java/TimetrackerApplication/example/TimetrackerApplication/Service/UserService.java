package TimetrackerApplication.example.TimetrackerApplication.Service;

import TimetrackerApplication.example.TimetrackerApplication.Exceptions.UserAlreadyExistException;
import TimetrackerApplication.example.TimetrackerApplication.Model.User;
import TimetrackerApplication.example.TimetrackerApplication.Repository.UserRepository;
import TimetrackerApplication.example.TimetrackerApplication.Request.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}