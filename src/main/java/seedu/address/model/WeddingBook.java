package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.wedding.UniqueWeddingList;
import seedu.address.model.wedding.Wedding;

/**
 * Wraps all data at the wedding-book level
 * Duplicates are not allowed (by .isSameWedding comparison)
 */
public class WeddingBook implements ReadOnlyWeddingBook {

    private final UniqueWeddingList weddings;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        weddings = new UniqueWeddingList();
    }

    public WeddingBook() {}

    /**
     * Creates a WeddingBook using the Weddings in the {@code toBeCopied}
     */
    public WeddingBook(ReadOnlyWeddingBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the wedding list with {@code weddings}.
     * {@code weddings} must not contain duplicate weddings.
     */
    public void setWeddings(List<Wedding> weddings) {
        this.weddings.setWeddings(weddings);
    }

    /**
     * Resets the existing data of this {@code WeddingBook} with {@code newData}.
     */
    public void resetData(ReadOnlyWeddingBook newData) {
        requireNonNull(newData);

        setWeddings(newData.getWeddingList());
    }

    //// wedding-level operations

    /**
     * Returns true if a wedding with the same identity as {@code wedding} exists in the wedding book.
     */
    public boolean hasWedding(Wedding wedding) {
        requireNonNull(wedding);
        return weddings.contains(wedding);
    }

    /**
     * Adds a wedding to the address book.
     * The wedding must not already exist in the address book.
     */
    public void addWedding(Wedding p) {
        weddings.add(p);
    }

    /**
     * Replaces the given wedding {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the wedding book.
     * The wedding identity of {@code editedPerson} must not be the same as another existing wedding in the
     * wedding book.
     */
    public void setWedding(Wedding target, Wedding editedPerson) {
        requireNonNull(editedPerson);

        weddings.setWedding(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code WeddingBook}.
     * {@code key} must exist in the wedding book.
     */
    public void removeWedding(Wedding key) {
        weddings.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("weddings", weddings)
                .toString();
    }

    @Override
    public ObservableList<Wedding> getWeddingList() {
        return weddings.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WeddingBook)) {
            return false;
        }

        WeddingBook otherWeddingBook = (WeddingBook) other;
        return weddings.equals(otherWeddingBook.weddings);
    }

    @Override
    public int hashCode() {
        return weddings.hashCode();
    }
}
