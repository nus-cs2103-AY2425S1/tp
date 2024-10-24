package seedu.address.model.tutorial;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.participation.Participation;

/**
 * Represents a Tutorial class for the students
 * Guarantees: details are present and not null, field values are validated
 */
public class Tutorial {

    public static final String MESSAGE_CONSTRAINTS =
            "Tutorial name should only contain alphanumeric characters and spaces, and it should not be blank.";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    private final String subject;
    private final List<Participation> participationList = new ArrayList<>();

    /**
     * Every field must be present and not null.
     */
    public Tutorial(String subject) {
        requireAllNonNull(subject, participationList);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public List<Participation> getParticipationList() {
        return participationList;
    }

    /**
     * Checks if the tutorial already contains a participation
     * @param participation the participation object to check
     * @return true if it already contains the participation. false otherwise
     */
    public boolean hasParticipation(Participation participation) {
        return participationList.stream()
                .anyMatch(currentParticipation -> currentParticipation.equals(participation));

    }

    /**
     * Adds a participation object to the participation list in tutorial
     * @param participation object to be added
     */
    public void addParticipation(Participation participation) {
        participationList.add(participation);
    }

    /**
     * Removes a participation object from the participation list
     * @param participation object to be removed
     */
    public void removeParticipation(Participation participation) {
        participationList.remove(participation);
    }

    /**
     * Returns true if both tutorials are of the same subject.
     * This defines a weaker notion of equality between two tutorials.
     */
    public boolean isSameTutorial(Tutorial otherTutorial) {
        if (otherTutorial == this) {
            return true;
        }
        return otherTutorial != null && otherTutorial.getSubject().equalsIgnoreCase(getSubject());
    }
    public static boolean isValidTutorial(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if both tutorials have the same data fields.
     * This defines a stronger notion of equality between two tutorials.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Tutorial)) {
            return false;
        }

        Tutorial otherTutorial = (Tutorial) other;
        return subject.equalsIgnoreCase(otherTutorial.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject);
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("subject", subject)
                .toString();
    }
}
