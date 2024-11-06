package seedu.academyassist.model.sort;

import static java.util.Objects.requireNonNull;

import seedu.academyassist.commons.util.AppUtil;

/**
 * Represents a parameter for Sort command
 */
public class SortParam {

    public static final String MESSAGE_CONSTRAINTS = "Sort parameters can only be "
            + "'name','subject','studentId' or 'yearGroup'";

    private SortField field;

    private enum SortField {
        NAME,
        SUBJECT,
        ID,
        YEARGROUP
    };

    /**
     * Constructs a SortParam
     *
     * @param sortParamString either "name", "subject", "studentId" or "yearGroup"
     */
    public SortParam(String sortParamString) {
        requireNonNull(sortParamString);
        AppUtil.checkArgument(isValidSortParam(sortParamString), MESSAGE_CONSTRAINTS);
        if (sortParamString.equals("name")) {
            this.field = SortField.NAME;
        } else if (sortParamString.equals("subject")) {
            this.field = SortField.SUBJECT;
        } else if (sortParamString.equals("studentId")) {
            this.field = SortField.ID;
        } else if (sortParamString.equals("yearGroup")) {
            this.field = SortField.YEARGROUP;
        }
    }

    /**
     * Returns true if a given string is a valid sort parameter.
     */
    public static boolean isValidSortParam(String testString) {
        return (testString.equals("name") || testString.equals("subject")
                || testString.equals("studentId") || testString.equals("yearGroup"));
    }

    @Override
    public String toString() {
        if (this.field == SortField.SUBJECT) {
            return "subject";
        } else if (this.field == SortField.NAME) {
            return "name";
        } else if (this.field == SortField.ID) {
            return "studentId";
        } else if (this.field == SortField.YEARGROUP) {
            return "yearGroup";
        }
        return null;
    }
}
