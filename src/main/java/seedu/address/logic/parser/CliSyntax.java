package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_ASSIGNMENT_NAME = new Prefix("an/");
    public static final Prefix PREFIX_ASSIGNMENT_SCORE = new Prefix("s/");
    public static final Prefix PREFIX_STUDENT_INDEX = new Prefix("si/");
    public static final Prefix PREFIX_ASSIGNMENT_INDEX = new Prefix("ai/");
    public static final Prefix PREFIX_ASSIGNMENT_MAX_SCORE = new Prefix("ms/");
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
}
