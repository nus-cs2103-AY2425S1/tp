package seedu.address.logic.parser;

/**
 * Represents the different model types in the application, with a shorthand for each type.
 * It supports PERSON, EVENT, and NEITHER models.
 */
public enum ModelType {
    /**
     * Represents a person model with shorthand "p".
     */
    PERSON("p"),

    /**
     * Represents an event model with shorthand "e".
     */
    EVENT("e"),

    /**
     * Represents an unknown or invalid model type.
     */
    NEITHER("neither");

    private final String shorthand;

    /**
     * Constructs a ModelType with the given shorthand.
     *
     * @param shorthand The shorthand string representing the model type.
     */
    ModelType(String shorthand) {
        this.shorthand = shorthand;
    }

    /**
     * Returns the shorthand representation of the model type.
     *
     * @return A string representing the shorthand for the model type.
     */
    public String getShorthand() {
        return shorthand;
    }

    /**
     * Returns the corresponding ModelType for a given shorthand string.
     *
     * @param shorthand The shorthand string representing a model type.
     * @return The ModelType associated with the given shorthand,
     *     or NEITHER if the shorthand does not match any known model types.
     */
    public static ModelType fromShorthand(String shorthand) {
        if (shorthand == null) {
            return NEITHER;
        }
        switch (shorthand) {
        case "p":
            return PERSON;
        case "e":
            return EVENT;
        default:
            return NEITHER; // Return NEITHER if shorthand does not match any known model types
        }
    }
}
