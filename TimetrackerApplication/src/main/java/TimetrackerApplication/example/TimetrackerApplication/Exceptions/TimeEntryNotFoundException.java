package TimetrackerApplication.example.TimetrackerApplication.Exceptions;

public class TimeEntryNotFoundException extends RuntimeException{
    public TimeEntryNotFoundException(String message) {
        super(message);
    }
}
