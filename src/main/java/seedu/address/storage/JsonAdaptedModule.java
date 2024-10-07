package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Module;

/**
 * Jackson-friendly version of {@link Module}.
 */
class JsonAdaptedModule {

    private final String module;

    /**
     * Constructs a {@code JsonAdaptedModule} with the given {@code module}.
     */
    @JsonCreator
    public JsonAdaptedModule(String module) {
        this.module = module;
    }

    /**
     * Converts a given {@code Module} into this class for Jackson use.
     */
    public JsonAdaptedModule(Module source) {
        module = source.module;
    }

    @JsonValue
    public String getModule() {
        return module;
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
        return new Module(module);
    }

}
