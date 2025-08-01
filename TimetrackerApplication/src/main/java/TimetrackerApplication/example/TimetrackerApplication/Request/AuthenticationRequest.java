package TimetrackerApplication.example.TimetrackerApplication.Request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthenticationRequest {
    private String email;
    private String password;
}
