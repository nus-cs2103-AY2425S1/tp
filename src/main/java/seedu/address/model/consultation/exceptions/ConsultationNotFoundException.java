package seedu.address.model.consultation.exceptions;

/**
 * Signals that the specified consultation could not be found in the list.
 */
public class ConsultationNotFoundException extends RuntimeException {
    public ConsultationNotFoundException() {
        super("Consultation not found in the list");
    }
}
