package seedu.academyassist.model.sort;

import static java.util.Objects.requireNonNull;

import seedu.academyassist.commons.util.AppUtil;

/**
 * Represents a parameter for Sort command
 */
public class SortParam {

    public static final String MESSAGE_CONSTRAINTS = "Sort parameters can only be 'name' or 'class' or 'studentId' ";

    private SortField field;

    private enum SortField {
        NAME,
        CLASS,
        ID
    };

    /**
     * Constructs a SortParam
     *
     * @param sortParamString either "name", "class" OR "studentId"
     */
    public SortParam(String sortParamString) {
        requireNonNull(sortParamString);
        AppUtil.checkArgument(isValidSortParam(sortParamString), MESSAGE_CONSTRAINTS);
        if (sortParamString.equals("name")) {
            this.field = SortField.NAME;
        } else if (sortParamString.equals("class")) {
            this.field = SortField.CLASS;
        } else if (sortParamString.equals("studentId")) {
            this.field = SortField.ID;
        }
    }

    /**
     * Returns true if a given string is a valid sort parameter.
     */
    public static boolean isValidSortParam(String testString) {
        return (testString.equals("name") || testString.equals("class") || testString.equals("studentId"));
    }

    @Override
    public String toString() {
        if (this.field == SortField.CLASS) {
            return "class";
        } else if (this.field == SortField.NAME) {
            return "name";
        } else if (this.field == SortField.ID) {
            return "studentId";
        }
        return null;
    }
}
