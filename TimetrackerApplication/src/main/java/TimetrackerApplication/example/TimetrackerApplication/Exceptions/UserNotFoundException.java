package TimetrackerApplication.example.TimetrackerApplication.Exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
