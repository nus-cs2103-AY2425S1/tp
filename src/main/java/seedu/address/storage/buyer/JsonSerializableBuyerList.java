package seedu.address.storage.buyer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.BuyerList;
import seedu.address.model.ReadOnlyBuyerList;
import seedu.address.model.buyer.Buyer;

/**
 * An Immutable BuyerList that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
public class JsonSerializableBuyerList {

    public static final String MESSAGE_DUPLICATE_BUYER = "Buyers list contains duplicate buyer(s).";

    private final List<JsonAdaptedBuyer> buyers = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableBuyerList} with the given buyers.
     */
    @JsonCreator
    public JsonSerializableBuyerList(@JsonProperty("buyers") List<JsonAdaptedBuyer> buyers) {
        this.buyers.addAll(buyers);
    }

    /**
     * Converts a given {@code ReadOnlyBuyerList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableBuyerList}.
     */
    public JsonSerializableBuyerList(ReadOnlyBuyerList source) {
        buyers.addAll(source.getBuyerList().stream().map(JsonAdaptedBuyer::new).collect(Collectors.toList()));
    }

    /**
     * Converts this buyer list into the model's {@code BuyerList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public BuyerList toModelType() throws IllegalValueException {
        BuyerList buyerList = new BuyerList();

        for (JsonAdaptedBuyer jsonAdaptedBuyer : buyers) {
            Buyer buyer = jsonAdaptedBuyer.toModelType();
            if (buyerList.hasBuyer(buyer)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_BUYER);
            }
            buyerList.addBuyer(buyer);
        }
        return buyerList;
    }

}
