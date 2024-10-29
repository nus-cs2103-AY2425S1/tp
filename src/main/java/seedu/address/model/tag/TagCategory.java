package seedu.address.model.tag;

/**
 * Provides categories to sort Tags into, as well as colour codes for display in the UI.
 */
public enum TagCategory {
    GENERAL("#3E7B91"),
    ACADEMICS("#FFD700"), // Gold
    ACTIVITIES("#1E90FF"), // Dodger Blue
    NETWORKING("#32CD32"), // Lime Green
    MENTORSHIP("#FF69B4"); // Hot Pink

    private final String colorCode;

    TagCategory(String colorCode) {
        this.colorCode = colorCode;
    }

    /**
     * Returns the colour code of the {@code TagCategory}.
     * @return String containing the hex code for the colour.
     */
    public String getColorCode() {
        return this.colorCode;
    }
}
