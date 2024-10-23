package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.delivery.Cost;
import seedu.address.model.delivery.DateTime;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.Quantity;
import seedu.address.model.delivery.Status;
import seedu.address.model.person.Person;
import seedu.address.model.product.Product;

/**
 * Jackson-friendly version of {@link Delivery}.
 */
public class JsonAdaptedDelivery {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Delivery's %s field is missing!";
    private final String product;
    private final JsonAdaptedPerson sender; // CHANGE TO SUPPLIER LATER ON
    private final String status;
    private final String deliveryTime;
    private final String cost;
    private final String quantity;
    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedDelivery(@JsonProperty("product") String product,
                               @JsonProperty("status") String status,
                               @JsonProperty("deliveryTime") String deliveryTime,
                               @JsonProperty("cost") String cost,
                               @JsonProperty("quantity") String quantity,
                               @JsonProperty("sender") JsonAdaptedPerson sender) {
        this.product = product;
        this.sender = sender;
        this.status = status;
        this.deliveryTime = deliveryTime;
        this.cost = cost;
        this.quantity = quantity;
    }

    /**
     * Converts a given {@code Delivery} into this class for Jackson use.
     */
    public JsonAdaptedDelivery(Delivery source) {
        this.product = source.getDeliveryProduct().productName;
        this.sender = new JsonAdaptedPerson(source.getDeliverySender());
        this.status = source.getDeliveryStatus().toString();
        this.deliveryTime = source.getDeliveryDate().toString();
        this.cost = source.getDeliveryCost().value;
        this.quantity = source.getDeliveryQuantity().value;
    }

    /**
     * Converts this Jackson-friendly adapted delivery object into the model's {@code Delivery} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted delivery.
     */
    public Delivery toModelType() throws IllegalValueException {

        if (product == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Product.class.getSimpleName()));
        }
        if (!Product.isValidProductName(product)) {
            throw new IllegalValueException(Product.MESSAGE_CONSTRAINTS);
        }
        final Product productObj = new Product(this.product);


        if (sender == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName()));
        }
        // Check if sender is valid ?? --> JsonAdaptedPerson class will check for us
        final Person senderObj = this.sender.toModelType();


        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }
        // Status is enum is there is no need for a check?
        final Status statusObj = Status.valueOf(this.status.toUpperCase());


        if (deliveryTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidTime(this.deliveryTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_CONSTRAINTS);
        }
        final DateTime deliveryDateTimeObj = new DateTime(this.deliveryTime);


        if (cost == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Cost.class.getSimpleName()));
        }
        if (!Cost.isValidCost(cost)) {
            throw new IllegalValueException(Cost.MESSAGE_CONSTRAINTS);
        }
        final Cost costObj = new Cost(cost);


        if (quantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Quantity.class.getSimpleName()));
        }
        if (!Quantity.isValidQuantity(quantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_CONSTRAINTS);
        }
        final Quantity quantityObj = new Quantity(quantity);

        return new Delivery(productObj, senderObj, statusObj, deliveryDateTimeObj, costObj,
                quantityObj);
    }
}
