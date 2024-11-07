package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.person.Worker;

/**
 * Jackson-friendly version of {@link Worker}.
 */
public class JsonAdaptedWorker {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Worker's %s field is missing!";

    private static final Logger logger = LogsCenter.getLogger(JsonAdaptedWorker.class);

    private final List<JsonAdaptedDelivery> deliveries = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedWorker} with the given worker details.
     */
    @JsonCreator
    public JsonAdaptedWorker(@JsonProperty("deliveries") List<JsonAdaptedDelivery> deliveries) {
        if (deliveries != null) {
            this.deliveries.addAll(deliveries);
        }
    }

    /**
     * Converts a given {@code Worker} into this class for Jackson use.
     */
    public JsonAdaptedWorker(Worker source) {
        deliveries.addAll(source.getUnmodifiableAssignedDeliveryList().stream()
                               .map(JsonAdaptedDelivery::new)
                               .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted worker object into the model's {@code Worker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted worker.
     */
    public Worker toModelType() throws IllegalValueException {
        final List<Delivery> workerDeliveries = new ArrayList<>();
        for (JsonAdaptedDelivery delivery: deliveries) {
            workerDeliveries.add(delivery.toModelType());
        }
        Worker worker = new Worker();
        worker.setDeliveryList(workerDeliveries);

        return worker;
    }
}
