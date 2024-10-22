package seedu.address.model.listing;

/**
 * Represents the size of a listing in square meters in the real estate application.
 * The size is stored as an integer value representing the area in square meters (m²).
 */
public class Area {
    private final Integer squareMeters;

    /**
     * Constructs a {@code Area}.
     *
     * @param squareMeters The size of the listing in square meters.
     */
    public Area(int squareMeters) {
        this.squareMeters = squareMeters;
    }

    public int getArea() {
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
        if (!(other instanceof Area)) {
            return false;
        }

        Area otherSize = (Area) other;
        return squareMeters.equals(otherSize.squareMeters);
    }

}
