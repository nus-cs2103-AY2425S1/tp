package seedu.internbuddy.model.application;

import static seedu.internbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.internbuddy.commons.util.ToStringBuilder;
import seedu.internbuddy.model.name.Name;

/**
 * Represents an Application in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Application {
    private final Name name;
    private final Description description;
    private final AppStatus appStatus;

    /**
     * Every field must be present and not null.
     */
    public Application(Name name, Description description, AppStatus appStatus) {
        requireAllNonNull(name, description, appStatus);
        this.name = name;
        this.description = description;
        this.appStatus = appStatus;
    }

    public Name getName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    public AppStatus getAppStatus() {
        return appStatus;
    }

    /**
     * Returns new Application with updated status.
     * @param newAppStatus
     * @return Application
     */
    public Application setAppStatus(AppStatus newAppStatus) {
        return new Application(name, description, newAppStatus);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("description", description)
                .add("appStatus", appStatus)
                .toString();
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
                && otherApplication.getAppStatus().equals(getAppStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, appStatus);
    }
}
