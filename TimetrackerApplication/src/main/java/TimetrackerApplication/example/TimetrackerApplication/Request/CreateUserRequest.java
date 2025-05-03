package TimetrackerApplication.example.TimetrackerApplication.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequest {
        @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 8, max = 100, message = "Password must be at least 8 characters long")
    @NotBlank(message = "Password is required")
    private String password;
}
