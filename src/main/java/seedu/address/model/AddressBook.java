package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.buyer.Buyer;
import seedu.address.model.buyer.UniqueBuyerList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameBuyer comparison)
 */
public class AddressBook implements ReadOnlyBuyerList {

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

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyBuyerList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the buyer list with {@code buyers}.
     * {@code buyers} must not contain duplicate buyers.
     */
    public void setBuyers(List<Buyer> buyers) {
        this.buyers.setBuyers(buyers);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyBuyerList newData) {
        requireNonNull(newData);

        setBuyers(newData.getBuyerList());
    }

    //// buyer-level operations

    /**
     * Returns true if a buyer with the same identity as {@code buyer} exists in the address book.
     */
    public boolean hasBuyer(Buyer buyer) {
        requireNonNull(buyer);
        return buyers.contains(buyer);
    }

    /**
     * Adds a buyer to the address book.
     * The buyer must not already exist in the address book.
     */
    public void addBuyer(Buyer p) {
        buyers.add(p);
    }

    /**
     * Replaces the given buyer {@code target} in the list with {@code editedBuyer}.
     * {@code target} must exist in the address book.
     * The buyer identity of {@code editedBuyer} must not be the same as another existing buyer in the address book.
     */
    public void setBuyer(Buyer target, Buyer editedBuyer) {
        requireNonNull(editedBuyer);

        buyers.setBuyer(target, editedBuyer);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Buyer key) {
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
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return buyers.equals(otherAddressBook.buyers);
    }

    @Override
    public int hashCode() {
        return buyers.hashCode();
    }
}
