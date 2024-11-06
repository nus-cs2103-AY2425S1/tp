package seedu.internbuddy.storage;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.internbuddy.commons.core.LogsCenter;
import seedu.internbuddy.commons.exceptions.IllegalValueException;
import seedu.internbuddy.model.application.AppStatus;
import seedu.internbuddy.model.application.Application;
import seedu.internbuddy.model.application.Description;
import seedu.internbuddy.model.name.Name;

/**
 * Jackson-friendly version of {@link Application}.
 */
public class JsonAdaptedApplication {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Application's %s field is missing!";
    private static final Logger logger = LogsCenter.getLogger(JsonAdaptedApplication.class);

    private final String name;
    private final String description;
    private final String appStatus;

    /**
     * Constructs a {@code JsonAdaptedApplication} with the given {@code description} and {@code status}.
     */
    @JsonCreator
    public JsonAdaptedApplication(@JsonProperty("name") String name, @JsonProperty("description") String description,
          @JsonProperty("status") String appStatus) {
        this.name = name;
        this.description = description;
        this.appStatus = appStatus;
    }

    /**
     * Converts a given {@link Application} into this class for Jackson use.
     */
    public JsonAdaptedApplication(Application source) {
        name = source.getName().fullName;
        description = source.getDescription().fullDescription;
        appStatus = source.getAppStatus().value;
    }

    /**
     * Converts this Jackson-friendly adapted application object into the model's {@link Application} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Application
     */
    public Application toModelType() throws IllegalValueException {
        logger.info("Attempting to convert to " + Application.class + "...");

        if (name == null) {
            logger.info("Missing `name` field.");
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            logger.info("`name` has invalid format.");
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (description == null) {
            logger.info("Missing `description` field.");
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            logger.info("`description` has invalid format.");
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (appStatus == null) {
            logger.info("Missing `appStatus` field.");
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AppStatus.class.getSimpleName()));
        }
        if (!AppStatus.isValidStatus(appStatus)) {
            logger.info("`appStatus` has invalid format.");
            throw new IllegalValueException(AppStatus.MESSAGE_CONSTRAINTS);
        }
        final AppStatus modelAppStatus = new AppStatus(appStatus);

        return new Application(modelName, modelDescription, modelAppStatus);
    }
}
