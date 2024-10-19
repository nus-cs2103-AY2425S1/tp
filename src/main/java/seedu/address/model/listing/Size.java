package seedu.address.model.listing;

/**
 * Represents the size of a listing in square meters in the real estate application.
 * The size is stored as an integer value representing the area in square meters (m²).
 */
public class Size {
    private final Integer squareMeters;

    /**
     * Constructs a {@code Size}.
     *
     * @param squareMeters The size of the listing in square meters.
     */
    public Size(int squareMeters) {
        this.squareMeters = squareMeters;
    }

    public int getSize() {
        return this.squareMeters;
    }

    @Override
    public String toString() {
        return String.format("%d  m²", this.squareMeters);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Size)) {
            return false;
        }

        Size otherSize = (Size) other;
        return squareMeters.equals(otherSize.squareMeters);
    }

}
