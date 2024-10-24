package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.delivery.Archive;
import seedu.address.model.delivery.Cost;
import seedu.address.model.delivery.Date;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryId;
import seedu.address.model.delivery.Eta;
import seedu.address.model.delivery.ItemName;
import seedu.address.model.delivery.Status;
import seedu.address.model.delivery.Time;
import seedu.address.model.person.Address;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Delivery}.
 */
public class JsonAdaptedDelivery {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Delivery's %s field is missing!";

    private final String deliveryId;
    private final List<JsonAdaptedItem> items = new ArrayList<>();
    private final String address;
    private final String cost;
    private final String date;
    private final String time;
    private final String eta;
    private final String status;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final String archive;

    /**
     * Constructs a {@code JsonAdaptedDelivery} with the given delivery details.
     */
    @JsonCreator
    public JsonAdaptedDelivery(@JsonProperty("deliveryId") String deliveryId,
                               @JsonProperty("itemName") List<JsonAdaptedItem> items,
                               @JsonProperty("address") String address,
                               @JsonProperty("cost") String cost,
                               @JsonProperty("date") String date,
                               @JsonProperty("time") String time,
                               @JsonProperty("eta") String eta,
                               @JsonProperty("status") String status,
                               @JsonProperty("tags") List<JsonAdaptedTag> tags,
                               @JsonProperty("archive") String archive) {
        this.deliveryId = deliveryId;
        if (items != null) {
            this.items.addAll(items);
        }
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.address = address;
        this.cost = cost;
        this.date = date;
        this.time = time;
        this.eta = eta;
        this.status = status;
        this.archive = archive;
    }

    /**
     * Converts a given {@code Delivery} into this class for Jackson use.
     */
    public JsonAdaptedDelivery(Delivery source) {
        deliveryId = String.valueOf(source.getDeliveryId().value);
        items.addAll(source.getItems().stream()
                .map(JsonAdaptedItem::new)
                .collect(Collectors.toList()));
        tags.addAll(source.getTags().stream().map(JsonAdaptedTag::new).toList());
        address = source.getAddress().value;
        cost = source.getCost().value;
        date = source.getDate().value.toString();
        time = source.getTime().value.toString();
        eta = source.getEta().value.toString();
        status = source.getStatus().getValue();
        archive = source.getArchive().value;
    }

    /**
     * Converts this Jackson-friendly adapted delivery object into the model's {@code Delivery} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Delivery toModelType() throws IllegalValueException {
        if (deliveryId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DeliveryId.class.getSimpleName()));
        }
        final DeliveryId modelDeliveryId = new DeliveryId(deliveryId);

        final List<ItemName> deliveryItems = new ArrayList<>();
        for (JsonAdaptedItem item : items) {
            deliveryItems.add(item.toModelType());
        }
        if (items.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "items"));
        }
        final Set<ItemName> modelItems = new HashSet<>(deliveryItems);

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
            throw new IllegalValueException(Eta.MESSAGE_CONSTRAINTS);
        }
        final Eta modelEta = new Eta(eta);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }
        if (!Status.isValidStatus(status)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        final Status modelStatus = new Status(status);

        final List<Tag> deliveryTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            deliveryTags.add(tag.toModelType());
        }
        final Set<Tag> modelTags = new HashSet<>(deliveryTags);

        if (archive == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Archive.class.getSimpleName()));
        }
        if (!Archive.isValidArchive(archive)) {
            throw new IllegalValueException(Archive.MESSAGE_CONSTRAINTS);
        }
        final Archive modelArchive = new Archive(archive);

        return new Delivery(modelDeliveryId, modelItems, modelAddress, modelCost, modelDate, modelTime, modelEta,
                modelStatus, modelTags, modelArchive);
    }
}
