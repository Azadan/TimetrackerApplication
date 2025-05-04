package TimetrackerApplication.example.TimetrackerApplication.DTO;

import java.time.LocalDateTime;

public record TimeEntryDTO(Long id, LocalDateTime startTime, LocalDateTime endTime, String description, long userId, long categoryId) {}
