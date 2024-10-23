package seedu.internbuddy.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (appStatus == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AppStatus.class.getSimpleName()));
        }
        if (!AppStatus.isValidStatus(appStatus)) {
            throw new IllegalValueException(AppStatus.MESSAGE_CONSTRAINTS);
        }
        final AppStatus modelAppStatus = new AppStatus(appStatus);

        return new Application(modelName, modelDescription, modelAppStatus);
    }
}
