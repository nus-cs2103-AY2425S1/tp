package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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

    @JsonValue
    public String getHealthServiceName() {
        return healthServiceName;
    }

}
