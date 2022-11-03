package hr.java.vjezbe.iznimke;

/**
 * Iznimka koja se baca kada je student napisao jedan od ispita s ocjenom nedovoljan.
 */
public class NemoguceOdreditiProsjekStudentaException extends Exception {


    public NemoguceOdreditiProsjekStudentaException() {
    }

    public NemoguceOdreditiProsjekStudentaException(String message) {
        super(message);
    }

    public NemoguceOdreditiProsjekStudentaException(String message, Throwable cause) {
        super(message, cause);
    }

    public NemoguceOdreditiProsjekStudentaException(Throwable cause) {
        super(cause);
    }
}
