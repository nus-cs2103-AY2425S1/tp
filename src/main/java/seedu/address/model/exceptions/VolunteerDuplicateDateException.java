package seedu.address.model.exceptions;

public class VolunteerDuplicateDateException extends RuntimeException {
    public VolunteerDuplicateDateException(String d) {
        super(String.format("The volunteer cannot be free on %s twice!", d));
    }
}
