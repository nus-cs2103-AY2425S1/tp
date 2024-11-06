package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CHILD;

import seedu.address.model.person.Name;

public class TypicalNames {
    public static final String VALID_NAME_STRING_JOHN = "John Doe";
    public static final String VALID_NAME_STRING_JANE = "Jane Doe";
    public static final Name VALID_NAME_JOHN = new Name(VALID_NAME_STRING_JOHN);
    public static final Name VALID_NAME_JANE = new Name(VALID_NAME_STRING_JANE);
    public static final String VALID_CHILD_NAME_DESC_JOHN = " " + PREFIX_CHILD + VALID_NAME_STRING_JOHN;
    public static final String VALID_CHILD_NAME_DESC_JANE = " " + PREFIX_CHILD + VALID_NAME_STRING_JANE;

    public static final String INVALID_CHILD_NAME = "!@#$%^&*()";
    public static final String INVALID_CHILD_NAME_DESC = " " + PREFIX_CHILD + INVALID_CHILD_NAME;
}
