package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Module;

/**
 * Jackson-friendly version of {@link Module}.
 */
class JsonAdaptedModule {

    private final String module;
    private final String grade; // grade can be null

    /**
     * Constructs a {@code JsonAdaptedModule} with the given {@code module} and optional {@code grade}.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("module") String module,
                             @JsonProperty("grade") String grade) {
        this.module = module;
        this.grade = grade; // grade can be null
    }

    /**
     * Converts a given {@code Module} into this class for Jackson use.
     */
    public JsonAdaptedModule(Module source) {
        module = source.value;
        grade = (source.getGrade() != null) ? source.getGrade().toString() : null; // Handle optional grade
    }

    @JsonProperty("module")
    public String getModule() {
        return module;
    }

    @JsonProperty("grade")
    public String getGrade() {
        return grade;
    }

    /**
     * Converts this Jackson-friendly adapted module object into the model's {@code Module} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted module.
     */
    public Module toModelType() throws IllegalValueException {
        if (!Module.isValidModule(this.module)) {
            throw new IllegalValueException(Module.MESSAGE_CONSTRAINTS);
        }

        // Create the module instance
        Module module = new Module(this.module);

        // Handle grade if it is present
        if (this.grade != null) {
            if (!Grade.isValidGrade(this.grade)) {
                throw new IllegalValueException(Grade.MESSAGE_CONSTRAINTS);
            }
            Grade grade = new Grade(this.grade);
            module.setGrade(grade); // Set the grade on the module
        }
        return module;
    }
}
