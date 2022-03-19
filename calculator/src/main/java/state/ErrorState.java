package state;

public class ErrorState implements State {
    private final String message;

    public ErrorState(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
