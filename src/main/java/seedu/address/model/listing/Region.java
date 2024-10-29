package seedu.address.model.listing;

import java.util.HashMap;

import seedu.address.commons.exceptions.IllegalValueException;

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

    public static final String MESSAGE_CONSTRAINTS = "Region should be a direction (NSEW, etc)";

    /**
     * Converts a string input to the corresponding Region enum.
     * The input is case-insensitive and ignores leading/trailing whitespace.
     *
     * @param regionString The string representation of the region.
     * @return The corresponding Region enum.
     * @throws IllegalArgumentException if the input does not match any Region.
     */
    public static Region fromString(String regionString) throws IllegalArgumentException {
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

    /**
     * Checks if the provided string represents a valid region in the {@code Region} enum.
     * The check is case-insensitive and trims any leading or trailing whitespace.
     *
     * @param region The string representation of the region to validate.
     * @return {@code true} if the input string is a valid region; {@code false} otherwise.
     * @throws IllegalValueException if the provided string is {@code null} or empty.
     */
    public static boolean isValidRegion(String region) {
        if (region == null || region.trim().isEmpty()) {
            return false;
        }
        for (Region r : Region.values()) {
            if (r.name().equals(region.trim().toUpperCase())) {
                return true;
            }
        }
        return false;
    }
}
