package TimetrackerApplication.example.TimetrackerApplication.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class StatisticsDto {
    private String categoryName;
    private String minutes;

    public StatisticsDto(String categoryName, Long minutes) {
        this.categoryName = categoryName;
        this.minutes = formattedTime(minutes);

    }

    public String formattedTime(Long minutes) {
        long hours = minutes / 60;
        long remainingMinutes = minutes % 60;
        return hours + "h " + remainingMinutes + "m";
    }
}
