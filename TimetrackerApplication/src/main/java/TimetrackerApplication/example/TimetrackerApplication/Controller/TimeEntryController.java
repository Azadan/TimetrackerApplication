package TimetrackerApplication.example.TimetrackerApplication.Controller;

import TimetrackerApplication.example.TimetrackerApplication.DTO.TimeEntryDTO;
import TimetrackerApplication.example.TimetrackerApplication.Exceptions.CategoryNotFoundException;
import TimetrackerApplication.example.TimetrackerApplication.Exceptions.TimeEntryNotFoundException;
import TimetrackerApplication.example.TimetrackerApplication.Exceptions.UserNotFoundException;
import TimetrackerApplication.example.TimetrackerApplication.Model.TimeEntry;
import TimetrackerApplication.example.TimetrackerApplication.Repository.TimeEntryRepository;
import TimetrackerApplication.example.TimetrackerApplication.Request.CheckInRequest;
import TimetrackerApplication.example.TimetrackerApplication.Request.CheckOutRequest;
import TimetrackerApplication.example.TimetrackerApplication.Response.ApiResponse;
import TimetrackerApplication.example.TimetrackerApplication.Service.TimeEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/timeentry")
public class TimeEntryController {
    private final TimeEntryService timeEntryService;

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
