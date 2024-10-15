package seedu.internbuddy.model.application;

import static seedu.internbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Application in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Application {
    private final Description description;
    private final AppStatus status;

    /**
     * Every field must be present and not null.
     */
    public Application(Description description, AppStatus status) {
        requireAllNonNull(description, status);
        this.description = description;
        this.status = status;
    }

    public Description getDescription() {
        return description;
    }

    public AppStatus getStatus() {
        return status;
    }

    /**
     * Returns new Application with updated status.
     * @param newStatus
     * @return
     */
    public Application setAppStatus(AppStatus newStatus) {
        return new Application(description, newStatus);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Description: ")
                .append(getDescription())
                .append(" Status: ")
                .append(getStatus());
        return builder.toString();
    }

    /**
     * Returns true if both applications have the same description.
     * This defines a weaker notion of equality between two applications.
     */
    public boolean isSameApplication(Application otherApplication) {
        if (otherApplication == this) {
            return true;
        }

        return otherApplication != null
                && otherApplication.getDescription().equals(getDescription());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Application)) {
            return false;
        }

        Application otherApplication = (Application) other;
        return otherApplication.getDescription().equals(getDescription())
                && otherApplication.getStatus().equals(getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, status);
    }
}
