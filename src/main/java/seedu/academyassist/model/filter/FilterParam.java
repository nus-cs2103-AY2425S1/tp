package seedu.academyassist.model.filter;

import static java.util.Objects.requireNonNull;

import seedu.academyassist.commons.util.AppUtil;

/**
 * Represents a parameter for Filter command
 */
public class FilterParam {

    public static final String MESSAGE_CONSTRAINTS = "Filter parameters can only be 'yearGroup' or 'class'";

    private FilterField field;

    private enum FilterField {
        YEARGROUP,
        SUBJECT
    };

    /**
     * Constructs a FilterParam
     *
     * @param filterParamString either "yearGroup" or "class"
     */
    public FilterParam(String filterParamString) {
        requireNonNull(filterParamString);
        AppUtil.checkArgument(isValidFilterParam(filterParamString), MESSAGE_CONSTRAINTS);
        if (filterParamString.equals("yearGroup")) {
            this.field = FilterField.YEARGROUP;
        } else if (filterParamString.equals("subject")) {
            this.field = FilterField.SUBJECT;
        }
    }

    /**
     * Returns true if a given string is a valid filter parameter.
     */
    public static boolean isValidFilterParam(String test) {
        return (test.equals("yearGroup") || test.equals("subject"));
    }

    /**
     * Returns True if FilterField of FilterParam is of type YEARGROUP
     */
    public boolean isYearGroup() {
        return this.field.equals(FilterField.YEARGROUP);
    }

    /**
     * Returns True if FilterField of FilterParam is of type SUBJECT
     */
    public boolean isSubject() {
        return this.field.equals(FilterField.SUBJECT);
    }

    @Override
    public String toString() {
        if (this.field == FilterField.YEARGROUP) {
            return "yearGroup";
        } else {
            return "subject";
        }
    }
}
