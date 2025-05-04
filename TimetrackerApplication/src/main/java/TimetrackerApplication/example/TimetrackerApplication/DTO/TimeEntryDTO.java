package TimetrackerApplication.example.TimetrackerApplication.DTO;

import java.time.LocalDateTime;

public record TimeEntryDTO(Long entryId, LocalDateTime startTime, LocalDateTime endTime, boolean isActive, Long userId, Long categoryId) {}
