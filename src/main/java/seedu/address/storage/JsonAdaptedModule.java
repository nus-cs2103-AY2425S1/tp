package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Module;

/**
 * Jackson-friendly version of {@link Module}.
 */
class JsonAdaptedModule {

    private final String module;
    private int grade;

    /**
     * Constructs a {@code JsonAdaptedModule} with the given {@code module}.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("module") String module, @JsonProperty("grade") Integer grade) {
        this.module = module;
        this.grade = grade;
    }

    /**
     * Converts a given {@code Module} into this class for Jackson use.
     */
    public JsonAdaptedModule(Module source) {
        module = source.module;
        if ("Ungraded".equals(source.getGrade())) {
            grade = -1;
        } else {
            grade = Integer.parseInt(source.getGrade());
        }
    }
    public String getModule() {
        return module;
    }
    public int getGrade() {
        return grade;
    }
    /**
     * Converts this Jackson-friendly adapted module object into the model's {@code Module} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted module.
     */
    public Module toModelType() throws IllegalValueException {
        if (!Module.isValidModule(module)) {
            throw new IllegalValueException(Module.MESSAGE_CONSTRAINTS);
        }
        if (!Module.isValidGrade(grade)) {
            throw new IllegalValueException(Module.GRADE_CONSTRAINTS);
        }
        Module moduleObject = new Module(module);
        moduleObject.assignGrade(grade);
        return moduleObject;
    }
}
