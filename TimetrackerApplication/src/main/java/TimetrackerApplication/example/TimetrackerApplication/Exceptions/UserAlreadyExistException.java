package TimetrackerApplication.example.TimetrackerApplication.Exceptions;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
