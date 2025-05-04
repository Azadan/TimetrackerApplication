package TimetrackerApplication.example.TimetrackerApplication.Request;

import lombok.Data;

@Data
public class CheckInRequest {
    private Long userId;
    private Long categoryId;
}