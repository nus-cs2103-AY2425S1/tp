package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.delivery.Cost;
import seedu.address.model.delivery.Date;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryId;
import seedu.address.model.delivery.Eta;
import seedu.address.model.delivery.Id;
import seedu.address.model.delivery.ItemName;
import seedu.address.model.delivery.Status;
import seedu.address.model.delivery.Time;
import seedu.address.model.person.Address;

/**
 * Jackson-friendly version of {@link Delivery}.
 */
public class JsonAdaptedDelivery {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Delivery's %s field is missing!";

    private final String deliveryId;
    private final String itemName;
    private final String address;
    private final String cost;
    private final String date;
    private final String time;
    private final String eta;
    private final String status;

    /**
     * Constructs a {@code JsonAdaptedDelivery} with the given delivery details.
     */
    @JsonCreator
    public JsonAdaptedDelivery(@JsonProperty("deliveryId") String deliveryId,
                               @JsonProperty("itemName") String itemName,
                               @JsonProperty("address") String address,
                               @JsonProperty("cost") String cost,
                               @JsonProperty("date") String date,
                               @JsonProperty("time") String time,
                               @JsonProperty("eta") String eta,
                               @JsonProperty("status") String status) {
        this.deliveryId = deliveryId;
        this.itemName = itemName;
        this.address = address;
        this.cost = cost;
        this.date = date;
        this.time = time;
        this.eta = eta;
        this.status = status;
    }

    /**
     * Converts a given {@code Delivery} into this class for Jackson use.
     */
    public JsonAdaptedDelivery(Delivery source) {
        deliveryId = String.valueOf(source.getDeliveryId().value);
        itemName = source.getItemName().value;
        address = source.getAddress().value;
        cost = source.getCost().value;
        date = source.getDate().value.toString();
        time = source.getTime().value.toString();
        eta = source.getEta().value.toString();
        status = source.getStatus().getValue();
    }

    /**
     * Converts this Jackson-friendly adapted delivery object into the model's {@code Delivery} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Delivery toModelType() throws IllegalValueException {
        if (deliveryId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Id.class.getSimpleName()));
        }

        final DeliveryId modelDeliveryId = new DeliveryId();

        if (itemName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ItemName.class.getSimpleName()));
        }
        if (!ItemName.isValidItemName(itemName)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final ItemName modelItemName = new ItemName(itemName);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (cost == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Cost.class.getSimpleName()));
        }
        if (!Cost.isValidCost(cost)) {
            throw new IllegalValueException(Cost.MESSAGE_CONSTRAINTS);
        }
        final Cost modelCost = new Cost(cost);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(time)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
        final Time modelTime = new Time(time);

        if (eta == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Eta.class.getSimpleName()));
        }
        if (!Eta.isValidEta(eta)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
        final Eta modelEta = new Eta(eta);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }
        if (!Status.isValidStatus(status)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        final Status modelStatus = new Status(status);

        return new Delivery(modelDeliveryId, modelItemName, modelAddress, modelCost, modelDate, modelTime, modelEta,
                modelStatus);
    }
}
