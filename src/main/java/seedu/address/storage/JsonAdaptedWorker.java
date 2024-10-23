package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.delivery.DeliveryId;
import seedu.address.model.person.Worker;

/**
 * Jackson-friendly version of {@link Worker}.
 */
public class JsonAdaptedWorker {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Worker's %s field is missing!";

    private static final Logger logger = LogsCenter.getLogger(JsonAdaptedWorker.class);

    private final List<String> deliveryIds = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedWorker} with the given worker details.
     */
    @JsonCreator
    public JsonAdaptedWorker(@JsonProperty("deliveryIds") List<String> deliveryIds) {
        if (deliveryIds != null) {
            this.deliveryIds.addAll(deliveryIds);
            if (deliveryIds.stream().map(DeliveryId::isValidDeliveryId).anyMatch(result -> !result)) {
                logger.info("Invalid DeliveryId in Worker");
            }
        }
    }

    /**
     * Converts a given {@code Worker} into this class for Jackson use.
     */
    public JsonAdaptedWorker(Worker source) {
        deliveryIds.addAll(source.getDeliveryIds().stream()
                               .map(deliveryId -> String.valueOf(deliveryId.value))
                               .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted worker object into the model's {@code Worker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted worker.
     */
    public Worker toModelType() throws IllegalValueException {
        final List<DeliveryId> modelDeliveryIds = new ArrayList<>();
        for (String deliveryIdStr : deliveryIds) {
            if (deliveryIdStr == null) {
                logger.info("Load Worker Failure. Wrong DeliveryId");
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DeliveryId.class.getSimpleName()));
            }
            final DeliveryId modelDeliveryId = new DeliveryId(deliveryIdStr);
            modelDeliveryIds.add(modelDeliveryId);
        }

        if (deliveryIds.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "deliveryIds"));
        }
        return new Worker(new HashSet<>(modelDeliveryIds));
    }
}
