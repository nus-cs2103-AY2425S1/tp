package seedu.address.storage;

import java.time.LocalDateTime;
import java.util.logging.Logger;

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
    private static final Logger logger = Logger.getLogger(JsonAdaptedAppt.class.getName());

    private final String dateTime;
    private final String healthService;

    /**
     * Constructs a {@code JsonAdaptedAppt} with the given appointment details.
     * @param dateTime
     * @param healthService
     */
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
        this.dateTime = source.getDateTime().format(Appt.STRICT_FORMATTER).toString();
        this.healthService = source.getHealthService().toString();
    }

    /**
     * Converts this Jackson-friendly adapted appointment object into the model's {@code Appt} object.
     * @return Appt
     */
    public Appt toModelType() throws IllegalValueException {
        if (!Appt.isValidDateTime(dateTime)) {
            logger.severe("Invalid date and time.");
            throw new IllegalValueException(Appt.DATETIME_MESSAGE_CONSTRAINTS);
        }

        if (!HealthService.isValidHealthServiceName(healthService)) {
            logger.severe("Invalid health service.");
            throw new IllegalValueException(HealthService.MESSAGE_CONSTRAINTS);
        }
        return new Appt(LocalDateTime.parse(dateTime, Appt.STRICT_FORMATTER), new HealthService(healthService));
    }
}
