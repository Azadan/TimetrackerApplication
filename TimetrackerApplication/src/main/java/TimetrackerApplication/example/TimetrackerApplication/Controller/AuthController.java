package TimetrackerApplication.example.TimetrackerApplication.Controller;

import TimetrackerApplication.example.TimetrackerApplication.DTO.UserDto;
import TimetrackerApplication.example.TimetrackerApplication.Exceptions.UserAlreadyExistException;
import TimetrackerApplication.example.TimetrackerApplication.Model.User;
import TimetrackerApplication.example.TimetrackerApplication.Repository.UserRepository;
import TimetrackerApplication.example.TimetrackerApplication.Request.AuthenticationRequest;
import TimetrackerApplication.example.TimetrackerApplication.Request.CreateUserRequest;
import TimetrackerApplication.example.TimetrackerApplication.Response.ApiResponse;
import TimetrackerApplication.example.TimetrackerApplication.Response.AuthenticationResponse;
import TimetrackerApplication.example.TimetrackerApplication.Service.UserService;
import TimetrackerApplication.example.TimetrackerApplication.Util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtils;
    private final UserService userService;


    @PostMapping("/login")
    public AuthenticationResponse authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new AuthenticationResponse(jwtUtils.generateToken(userDetails.getUsername(), user.getUserId()));    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody CreateUserRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new UserAlreadyExistException("Email already in use");
        }
        User user = new User();
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setAdmin(req.isAdmin());
        userRepository.save(user);
        UserDto userDto = userService.convertToDto(user);
        return ResponseEntity.ok(new ApiResponse("User registered successfully",true, userDto));

    }

}
