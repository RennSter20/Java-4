package hr.java.vjezbe.iznimke;

/**
 * Iznimka koja se baca u slučaju da su više studenata najmlađi.
 */
public class PostojiViseNajmadjihStudenataException extends RuntimeException {

    public PostojiViseNajmadjihStudenataException() {
    }

    public PostojiViseNajmadjihStudenataException(String message) {
        super(message);
    }

    public PostojiViseNajmadjihStudenataException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostojiViseNajmadjihStudenataException(Throwable cause) {
        super(cause);
    }
}
