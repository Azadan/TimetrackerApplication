package TimetrackerApplication.example.TimetrackerApplication.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class AuthenticationResponse {
    private String jwt;
}
