package TimetrackerApplication.example.TimetrackerApplication.Request;

import lombok.Data;

@Data
public class CheckOutRequest {
    private Long userId;
    private Long categoryId;
    private Long entryId;
}
