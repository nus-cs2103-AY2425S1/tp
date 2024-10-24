package seedu.address.model.listing;

/**
 * Represents the various geographical regions where a listing can be situated.
 */
public enum Region {
    NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST, CENTRAL;

    /**
     * Converts a string input to the corresponding Region enum.
     * The input is case-insensitive and ignores leading/trailing whitespace.
     *
     * @param regionString The string representation of the region.
     * @return The corresponding Region enum.
     * @throws IllegalArgumentException if the input does not match any Region.
     */
    public static Region fromString(String regionString) {
        if (regionString == null || regionString.trim().isEmpty()) {
            throw new IllegalArgumentException("Region string cannot be null or empty.");
        }
        try {
            return Region.valueOf(regionString.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid region: " + regionString, e);
        }
    }
}
