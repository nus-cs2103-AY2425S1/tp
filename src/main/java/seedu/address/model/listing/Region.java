package seedu.address.model.listing;

import java.util.HashMap;

/**
 * Represents the various geographical regions where a listing can be situated.
 */
public enum Region {
    NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST, CENTRAL;

    private static final HashMap<Region, String> hm = new HashMap<>();

    // Static initializer to assign colors to each region
    static {
        hm.put(EAST, "green");
        hm.put(WEST, "green");
        hm.put(NORTHEAST, "purple");
        hm.put(SOUTH, "red");
        hm.put(NORTH, "red");
        hm.put(NORTHWEST, "red");
        hm.put(SOUTHEAST, "orange");
        hm.put(SOUTHWEST, "orange");
        hm.put(CENTRAL, "orange");
    }

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

    /**
     * Gets the color associated with the region.
     *
     * @return the color as a String.
     */
    public String getColor() {
        return hm.get(this);
    }
}
