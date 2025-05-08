package TimetrackerApplication.example.TimetrackerApplication.Controller;

import TimetrackerApplication.example.TimetrackerApplication.DTO.StatisticsDto;
import TimetrackerApplication.example.TimetrackerApplication.DTO.TimeEntryDTO;
import TimetrackerApplication.example.TimetrackerApplication.Exceptions.CategoryNotFoundException;
import TimetrackerApplication.example.TimetrackerApplication.Exceptions.TimeEntryNotFoundException;
import TimetrackerApplication.example.TimetrackerApplication.Exceptions.UserNotFoundException;
import TimetrackerApplication.example.TimetrackerApplication.Model.TimeEntry;
import TimetrackerApplication.example.TimetrackerApplication.Request.CheckInRequest;
import TimetrackerApplication.example.TimetrackerApplication.Request.CheckOutRequest;
import TimetrackerApplication.example.TimetrackerApplication.Response.ApiResponse;
import TimetrackerApplication.example.TimetrackerApplication.Service.TimeEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/timeentry")
@CrossOrigin("*")
public class TimeEntryController {
    private final TimeEntryService timeEntryService;

    @GetMapping("/findbyuser/{userId}")
    public ResponseEntity<ApiResponse> findByUserId(@PathVariable Long userId) {
        try {
            List<TimeEntry> timeEntries = timeEntryService.getAllEntriesByUserId(userId);
            List<TimeEntryDTO> timeEntryDTOS = timeEntryService.convertToDtoList(timeEntries);
            return ResponseEntity.ok(new ApiResponse("Time entries found", true, timeEntryDTOS));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), false, null));
        } catch (TimeEntryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No time entries found for this user", false, null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse("User with this userId not found", false, null));
        }
    }

    @GetMapping("/statistics/weekly/{userId}")
    public ResponseEntity<ApiResponse> getWeeklyStatsByUser(@PathVariable Long userId) {
        try {
            Map<String, Long> statistics = timeEntryService.calculateStatistics(userId);
            List<StatisticsDto> statisticsDtos = timeEntryService.convertStatisticsToDto(statistics);
            return ResponseEntity.ok(new ApiResponse("Weekly statistics found", true, statisticsDtos));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("User with this userId not found", false, null));
        } catch (TimeEntryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Time entries has not been found", false, null));
        }
    }

    @PostMapping("/checkin")
    public ResponseEntity<ApiResponse> checkIn(@RequestBody CheckInRequest checkInRequest) {
        try {
            TimeEntry timeEntry = timeEntryService.checkIn(checkInRequest);
            TimeEntryDTO timeEntryDTO = timeEntryService.convertToDto(timeEntry);
            return ResponseEntity.ok(new ApiResponse("Check-in successful", true, timeEntryDTO));
        } catch (TimeEntryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), false, null));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("User with this userId not found", false, null));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse("Category with this categoryId not found", false, null));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse("User has active time entries", false, null));
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse> checkOut(@RequestBody CheckOutRequest request) {
        try {
            TimeEntry timeEntry = timeEntryService.checkOut(request);
            TimeEntryDTO timeEntryDTO = timeEntryService.convertToDto(timeEntry);
            return ResponseEntity.ok(new ApiResponse("Check-out successful", true, timeEntryDTO));
        } catch (TimeEntryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), false, null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse("Time entry not found", false, null));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse("Time entry is already checked out", false, null));
        }
    }
}
