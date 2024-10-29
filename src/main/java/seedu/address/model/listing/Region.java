package seedu.address.model.listing;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the various geographical regions where a listing can be situated.
 */
public enum Region {
    NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST, CENTRAL;

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
