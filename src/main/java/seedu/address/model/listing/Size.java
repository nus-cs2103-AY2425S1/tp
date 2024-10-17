package seedu.address.model.listing;

public class Size {
    private final Integer squareMeters;

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
