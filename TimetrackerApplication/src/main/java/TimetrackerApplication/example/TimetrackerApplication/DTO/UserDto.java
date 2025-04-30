package TimetrackerApplication.example.TimetrackerApplication.DTO;

import java.util.List;

public record UserDto (Long id, String email, boolean isAdmin, List<Long> categoryId) {}
