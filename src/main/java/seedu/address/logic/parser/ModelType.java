package seedu.address.logic.parser;

public enum ModelType {
    PERSON("p"),
    EVENT("e");

    private final String shorthand;

    ModelType(String shorthand) {
        this.shorthand = shorthand;
    }

    public String getShorthand() {
        return shorthand;
    }

    public static ModelType fromShorthand(String shorthand) throws IllegalArgumentException {
        switch (shorthand) {
            case "p":
                return PERSON;
            case "e":
                return EVENT;
            default:
                throw new IllegalArgumentException("Unknown model type shorthand: " + shorthand);
        }
    }
}
