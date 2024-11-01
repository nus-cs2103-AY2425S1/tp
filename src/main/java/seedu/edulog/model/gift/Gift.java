package seedu.edulog.model.gift;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Gift in the gift list.
 */
public class Gift {

    public final String giftName;

    /**
     * Constructs a {@code Gift}.
     *
     * @param giftName A valid gift name.
     */
    public Gift(String giftName) {
        requireNonNull(giftName);
        this.giftName = giftName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Gift)) {
            return false;
        }

        Gift otherGift = (Gift) other;
        return giftName.equals(otherGift.giftName);
    }

    @Override
    public int hashCode() {
        return giftName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        return giftName;
    }

}
