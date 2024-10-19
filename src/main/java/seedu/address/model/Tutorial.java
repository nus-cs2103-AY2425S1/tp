package seedu.address.model;

import static java.util.Objects.requireNonNull;


public class Tutorial {

    private String subject;

    public Tutorial(String subject) {
        requireNonNull(subject);
        this.subject = subject;
    }

}
