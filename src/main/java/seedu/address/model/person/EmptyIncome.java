package seedu.address.model.person;

/**
 * Represents an empty Income of a Person in the address book.
 * Guarantees: immutable
 */
class EmptyIncome extends Income {

    public static final String INTERNAL_REPRESENTATION = "<REPRESENTATION FOR EMPTY INCOME>";

    private static final EmptyIncome emptyIncome = new EmptyIncome();

    private EmptyIncome() {
        super();
    }

    /**
     * Provides an object representing an empty income.
     */
    public static EmptyIncome get() {
        return emptyIncome;
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
        return "No income provided";
    }
}
