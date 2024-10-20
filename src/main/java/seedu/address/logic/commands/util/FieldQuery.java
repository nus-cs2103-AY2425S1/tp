package seedu.address.logic.commands.util;

public class FieldQuery {
    private SearchField field;
    private String keyword;

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
}
