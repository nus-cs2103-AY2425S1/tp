package seedu.internbuddy.testutil;

import seedu.internbuddy.model.application.AppStatus;
import seedu.internbuddy.model.application.Application;
import seedu.internbuddy.model.application.Description;
import seedu.internbuddy.model.name.Name;

/**
 * A utility class to help with building Application objects.
 */
public class ApplicationBuilder {
    public static final String DEFAULT_NAME = "Software Engineering Intern";
    public static final String DEFAULT_DESCRIPTION = "Requires: ReactJS and ExpressJS";
    public static final String DEFAULT_STATUS = "APPLIED";

    private Name name;
    private Description description;
    private AppStatus appStatus;

    /**
     * Creates a {@code ApplicationBuilder} with the default details.
     */
    public ApplicationBuilder() {
        name = new Name(DEFAULT_NAME);
        description = new Description(DEFAULT_DESCRIPTION);
        appStatus = new AppStatus(DEFAULT_STATUS);
    }

    /**
     * Initializes the ApplicationBuilder with the data of {@code applicationToCopy}.
     */
    public ApplicationBuilder(Application applicationToCopy) {
        name = applicationToCopy.getName();
        description = applicationToCopy.getDescription();
        appStatus = applicationToCopy.getAppStatus();
    }

    /**
     * Sets the {@code Name} of the {@code Application} that we are building.
     */
    public ApplicationBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Application} that we are building.
     */
    public ApplicationBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code AppStatus} of the {@code Application} that we are building.
     */
    public ApplicationBuilder withAppStatus(String status) {
        this.appStatus = new AppStatus(status);
        return this;
    }

    public Application build() {
        return new Application(name, description, appStatus);
    }

}
