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
    public static final Prefix PREFIX_PARENT_NAME = new Prefix("pn/");
    public static final Prefix PREFIX_PARENT_PHONE = new Prefix("pp/");
    public static final Prefix PREFIX_PARENT_EMAIL = new Prefix("pe/");
    public static final Prefix PREFIX_EDUCATION = new Prefix("edu/");
    public static final Prefix PREFIX_GRADE = new Prefix("g/");
}
