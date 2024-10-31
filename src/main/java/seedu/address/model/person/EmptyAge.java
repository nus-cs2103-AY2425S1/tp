package seedu.address.model.person;

/**
 * Represents an empty Age of a Person in the address book.
 * Guarantees: immutable
 */
class EmptyAge extends Age {

    public static final String INTERNAL_REPRESENTATION = "<REPRESENTATION FOR EMPTY AGE>";

    private static final EmptyAge emptyAge = new EmptyAge();

    private EmptyAge() {
        super();
    }

    /**
     * Provides an object representing an empty age.
     */
    public static EmptyAge get() {
        return emptyAge;
    }

    /**
     * Returns true
     */
    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public String toString() {
        return INTERNAL_REPRESENTATION;
    }

    /**
     * Returns the String to be presented on the UI.
     */
    @Override
    public String getValueForUi() {
        return "-";
    }
}
