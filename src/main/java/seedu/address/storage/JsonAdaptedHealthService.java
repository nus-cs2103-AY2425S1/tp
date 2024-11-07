package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.healthservice.HealthService;

/**
 * Jackson-friendly version of {@link HealthService}.
 */
class JsonAdaptedHealthService {

    private final String healthServiceName;

    /**
     * Constructs a {@code JsonAdaptedHealthService} with the given {@code healthServiceName}.
     */
    @JsonCreator
    public JsonAdaptedHealthService(String healthServiceName) {
        this.healthServiceName = healthServiceName;
    }

    /**
     * Converts a given {@code HealthService} into this class for Jackson use.
     */
    public JsonAdaptedHealthService(HealthService source) {
        healthServiceName = source.healthServiceName;
    }

    @JsonValue
    public String getHealthServiceName() {
        return healthServiceName;
    }

    /**
     * Converts this Jackson-friendly adapted healthService object into the model's {@code HealthService} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted healthService.
     */
    public HealthService toModelType() throws IllegalValueException {
        if (!HealthService.isValidHealthServiceName(healthServiceName)) {
            throw new IllegalValueException(HealthService.MESSAGE_CONSTRAINTS);
        }
        return new HealthService(healthServiceName);
    }

}
