public class InvalidTrackingIdException extends Exception {

    // The message is passed up to the Exception class
    public InvalidTrackingIdException(String message) {
        super(message);
    }
}