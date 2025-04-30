package TimetrackerApplication.example.TimetrackerApplication.Controller;

import TimetrackerApplication.example.TimetrackerApplication.DTO.UserDto;
import TimetrackerApplication.example.TimetrackerApplication.Exceptions.UserAlreadyExistException;
import TimetrackerApplication.example.TimetrackerApplication.Model.User;
import TimetrackerApplication.example.TimetrackerApplication.Request.CreateUserRequest;
import TimetrackerApplication.example.TimetrackerApplication.Response.ApiResponse;
import TimetrackerApplication.example.TimetrackerApplication.Service.UserService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody CreateUserRequest req) {
        try {
            User user = userService.createUser(req);
            UserDto userDto = userService.convertToDto(user);
            return ResponseEntity.ok(new ApiResponse("User created successfully", true, userDto));
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), false, null));
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(BAD_REQUEST).body(new ApiResponse(e.getMessage(), false, null));
        }
    }
}
