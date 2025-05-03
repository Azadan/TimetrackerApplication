package TimetrackerApplication.example.TimetrackerApplication.DTO;

import TimetrackerApplication.example.TimetrackerApplication.Model.Category;

import java.util.List;

public record UserDto (Long id, String email, boolean isAdmin, List<CategoryDto> categoriesDto) {}
