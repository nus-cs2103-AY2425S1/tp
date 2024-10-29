package seedu.address.storage;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.healthservice.HealthService;
import seedu.address.model.patient.Appt;

/**
 * Jackson-friendly version of {@link Appt}.
 * This class is used to convert between JSON and Java objects.
 */
public class JsonAdaptedAppt {
    private final String dateTime;
    private final String healthService;

    @JsonCreator
    public JsonAdaptedAppt(@JsonProperty("dateTime") String dateTime, 
        @JsonProperty("healthService") String healthService) {
        this.dateTime = dateTime;
        this.healthService = healthService;
    }

    /**
     * Converts a given {@code Appt} into this class for Jackson use.
     * @param source
     * @throws IllegalValueException
     */
    public JsonAdaptedAppt(Appt source) {
        this.dateTime = source.getDateTime().toString();
        this.healthService = source.getHealthService().toString();
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getHealthServiceName() {
        return healthService;
    }

    /**
     * Converts this Jackson-friendly adapted appointment object into the model's {@code Appt} object.
     * @throws IllegalValueException
     */
    public Appt toModelType() throws IllegalValueException {
        if (!Appt.isValidAppt(dateTime)) {
            throw new IllegalValueException(Appt.MESSAGE_CONSTRAINTS);
        }
        if (!HealthService.isValidHealthserviceName(healthService) || healthService == null) {
            throw new IllegalValueException(HealthService.MESSAGE_CONSTRAINTS);
        }
        return new Appt(LocalDateTime.parse(dateTime), new HealthService(healthService));
    }
}
