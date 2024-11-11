package seedu.address.model.tag;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Provides categories to sort Tags into, as well as colour codes for display in the UI.
 */
public enum TagCategory {
    GENERAL("#A9A9A9"), // Dark Grey for default color
    ACADEMICS("#FFD700"), // Gold
    ACTIVITIES("#1E90FF"), // Dodger Blue
    NETWORKING("#32CD32"), // Lime Green
    MENTORSHIP("#FF69B4"); // Hot Pink

    private static final String INVALID_CATEGORY = "Error! %s is not a valid category!";
    private final String colorCode;

    TagCategory(String colorCode) {
        this.colorCode = colorCode;
    }

    /**
     * Returns TagCategory from a String.
     * @return TagCategory represented by the String.
     */
    public static TagCategory fromString(String category) throws IllegalValueException {
        switch (category) {
        case "ACADEMICS":
            return ACADEMICS;

        case "ACTIVITIES":
            return ACTIVITIES;

        case "NETWORKING":
            return NETWORKING;

        case "MENTORSHIP":
            return MENTORSHIP;

        case "GENERAL":
            return GENERAL;

        default:
            throw new IllegalValueException(String.format(INVALID_CATEGORY, category));
        }
    }

    /**
     * Returns the colour code of the {@code TagCategory}.
     * @return String containing the hex code for the colour.
     */
    public String getColorCode() {
        return this.colorCode;
    }
}
