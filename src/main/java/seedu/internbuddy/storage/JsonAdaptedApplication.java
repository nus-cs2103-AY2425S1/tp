package seedu.internbuddy.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.internbuddy.commons.exceptions.IllegalValueException;
import seedu.internbuddy.model.application.AppStatus;
import seedu.internbuddy.model.application.Application;
import seedu.internbuddy.model.application.Description;

/**
 * Jackson-friendly version of {@link Application}.
 */
public class JsonAdaptedApplication {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Application's %s field is missing!";

    private final String description;
    private final String status;

    /**
     * Constructs a {@code JsonAdaptedApplication} with the given {@code description} and {@code status}.
     */
    @JsonCreator
    public JsonAdaptedApplication(@JsonProperty("description") String description,
          @JsonProperty("status") String status) {
        this.description = description;
        this.status = status;
    }

    /**
     * Converts a given {@link Application} into this class for Jackson use.
     */
    public JsonAdaptedApplication(Application source) {
        description = source.getDescription().fullDescription;
        status = source.getStatus().value;
    }

    /**
     * Converts this Jackson-friendly adapted application object into the model's {@link Application} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Application
     */
    public Application toModelType() throws IllegalValueException {
        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AppStatus.class.getSimpleName()));
        }
        if (!AppStatus.isValidStatus(status)) {
            throw new IllegalValueException(AppStatus.MESSAGE_CONSTRAINTS);
        }
        final AppStatus modelAppStatus = new AppStatus(status);

        return new Application(modelDescription, modelAppStatus);
    }
}
