package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.buyer.Buyer;
import seedu.address.model.buyer.UniqueBuyerList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameBuyer comparison)
 */
public class BuyerList implements ReadOnlyBuyerList {
    private static final Logger logger = LogsCenter.getLogger(BuyerList.class);

    private final UniqueBuyerList buyers;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        buyers = new UniqueBuyerList();
    }

    public BuyerList() {
    }

    /**
     * Creates an BuyerList using the Buyers in the {@code toBeCopied}
     */
    public BuyerList(ReadOnlyBuyerList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the buyer list with {@code buyers}.
     * {@code buyers} must not contain duplicate buyers.
     */
    public void setBuyers(List<Buyer> buyers) {
        requireNonNull(buyers);
        this.buyers.setBuyers(buyers);
    }

    /**
     * Resets the existing data of this {@code BuyerList} with {@code newData}.
     */
    public void resetData(ReadOnlyBuyerList newData) {
        requireNonNull(newData);
        setBuyers(newData.getBuyerList());
    }

    //// buyer-level operations

    /**
     * Returns true if a buyer with the same identity as {@code buyer} exists in the buyer list.
     */
    public boolean hasBuyer(Buyer buyer) {
        requireNonNull(buyer);
        return buyers.contains(buyer);
    }

    /**
     * Adds a buyer to the buyer list.
     * The buyer must not already exist in the buyer list.
     */
    public void addBuyer(Buyer b) {
        logger.info(String.format("Adding [%s] to buyer list", b.getName()));
        buyers.add(b);
    }

    /**
     * Replaces the given buyer {@code target} in the list with {@code editedBuyer}.
     * {@code target} must exist in the buyer list.
     * The buyer identity of {@code editedBuyer} must not be the same as another existing buyer in the buyer list.
     */
    public void setBuyer(Buyer target, Buyer editedBuyer) {
        requireNonNull(editedBuyer);
        logger.info(String.format("Replacing buyer with its edited form in buyer list"));
        buyers.setBuyer(target, editedBuyer);
    }

    /**
     * Removes {@code key} from this {@code BuyerList}.
     * {@code key} must exist in the buyer list.
     */
    public void removeBuyer(Buyer key) {
        logger.info(String.format("Removing [%s] from buyer list", key.getName()));
        buyers.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("buyers", buyers)
                .toString();
    }

    @Override
    public ObservableList<Buyer> getBuyerList() {
        return buyers.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BuyerList)) {
            return false;
        }

        BuyerList otherBuyerList = (BuyerList) other;
        return buyers.equals(otherBuyerList.buyers);
    }

    @Override
    public int hashCode() {
        return buyers.hashCode();
    }
}
