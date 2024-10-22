package seedu.address.logic.commands.util;


/**
 * Represents a pair of a search field and a keyword for querying.
 * The field specifies the type of field to search, and the keyword
 * is the search term provided by the user.
 */
public class FieldQuery {
    private SearchField field;
    private String keyword;

    /**
     * Constructs a FieldQuery class.
     * @param field SearchField object representing field in search query e.g. name.
     * @param keyword Query keyword.
     */
    public FieldQuery(SearchField field, String keyword) {
        this.field = field;
        this.keyword = keyword;
    }

    // Getters
    public SearchField getField() {
        return field;
    }

    public String getKeyword() {
        return keyword;
    }

    @Override
    public String toString() {
        return field.toString() + ": " + keyword;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FieldQuery)) {
            return false;
        }

        FieldQuery fieldQuery = (FieldQuery) other;
        return field.equals(fieldQuery.field) && keyword.equals(fieldQuery.keyword);
    }
}
