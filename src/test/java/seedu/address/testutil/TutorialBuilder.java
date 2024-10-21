package seedu.address.testutil;

import seedu.address.model.tutorial.Tutorial;

/**
 * A utility class to help with building Tutorial objects.
 */
public class TutorialBuilder {
    public static final String DEFAULT_SUBJECT = "Computer Science";

    private String subject;

    /**
     * Creates a {@code TutorialBuilder} with the default details.
     */
    public TutorialBuilder() {
        subject = DEFAULT_SUBJECT;
    }

    /**
     * Initializes the TutorialBuilder with the data of {@code tutorialToCopy}.
     */
    public TutorialBuilder(Tutorial tutorialToCopy) {
        subject = tutorialToCopy.getSubject();
    }

    /**
     * Sets the {@code subject} of the {@code Tutorial} that we are building.
     */
    public TutorialBuilder withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public Tutorial build() {
        return new Tutorial(subject);
    }
}
