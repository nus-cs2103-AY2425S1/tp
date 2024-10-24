package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_TASK = new Prefix("tk/");
    public static final Prefix PREFIX_WEDDING = new Prefix("w/");
    public static final Prefix PREFIX_EDIT_WEDDING_PERSON_1 = new Prefix("p1/");
    public static final Prefix PREFIX_EDIT_WEDDING_PERSON_2 = new Prefix("p2/");
    public static final Prefix PREFIX_FORCE = new Prefix("f/");
}
