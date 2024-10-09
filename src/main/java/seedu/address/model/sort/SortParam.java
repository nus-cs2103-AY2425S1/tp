package seedu.address.model.sort;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.tag.Tag.isValidTagName;

public class SortParam {

    public static final String MESSAGE_CONSTRAINTS = "Sort parameters can only be 'name' or 'class'";

    private SortField field;

    private enum SortField{
        NAME,
        CLASS
    };

    /**
     * Constructs a SortParam
     *
     * @param sortParamString either "name" or "class"
     */
    public SortParam(String sortParamString) {
        requireNonNull(sortParamString);
        checkArgument(isValidSortParam(sortParamString), MESSAGE_CONSTRAINTS);
        if (sortParamString == "name") {
            System.out.println("name");
            this.field = SortField.NAME;
        } else if (sortParamString == "class") {
            System.out.println("class");
            this.field = SortField.CLASS;
        }
    }

    /**
     * Returns true if a given string is a valid sort parameter.
     */
    public static boolean isValidSortParam(String test) {
        return (test.equals("name") || test.equals("class"));
    }
}
