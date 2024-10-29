package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represent Favourite Attribute of a Person Contact
 */
public class Favourite {
    public final Boolean favouriteStatus;
    public final String value;
    /**
     * Constructs a {@code Favourite}.
     *
     * @param favourite indicates whether the person is a favourite contact.
     */
    public Favourite(Boolean favourite) {
        requireNonNull(favourite);
        favouriteStatus = favourite;
        value = String.valueOf(favourite);
    }
    /**
     * returns {@String favouriteStatus}.
     */
    @Override
    public String toString() {
        return String.valueOf(favouriteStatus);
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof Favourite)) {
            return false;
        }
        Favourite otherFavourite = (Favourite) other;
        return favouriteStatus.equals(otherFavourite.favouriteStatus);
    }
}
