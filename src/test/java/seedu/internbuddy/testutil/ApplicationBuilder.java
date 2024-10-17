package seedu.internbuddy.testutil;

import seedu.internbuddy.model.application.AppStatus;
import seedu.internbuddy.model.application.Application;
import seedu.internbuddy.model.application.Description;

/**
 * A utility class to help with building Application objects.
 */
public class ApplicationBuilder {
    public static final String DEFAULT_DESCRIPTION = "Software Engineering Intern";
    public static final String DEFAULT_STATUS = "APPLIED";

    private Description description;
    private AppStatus status;

    /**
     * Creates a {@code ApplicationBuilder} with the default details.
     */
    public ApplicationBuilder() {
        description = new Description(DEFAULT_DESCRIPTION);
        status = new AppStatus(DEFAULT_STATUS);
    }

    /**
     * Initializes the ApplicationBuilder with the data of {@code applicationToCopy}.
     */
    public ApplicationBuilder(Application applicationToCopy) {
        description = applicationToCopy.getDescription();
        status = applicationToCopy.getStatus();
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
    public ApplicationBuilder withStatus(String status) {
        this.status = new AppStatus(status);
        return this;
    }

    public Application build() {
        return new Application(description, status);
    }



}
