public class PersonException extends Exception {
    static final long serialVersionUID = -3387516993124229948L;

    public PersonException() {
    }

    public PersonException(String message) {
        super(message);
    }

    public PersonException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersonException(Throwable cause) {
        super(cause);
    }
}
