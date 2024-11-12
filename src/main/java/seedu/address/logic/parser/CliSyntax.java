package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_GENDER = new Prefix("g/");
    public static final Prefix PREFIX_AGE = new Prefix("a/");
    public static final Prefix PREFIX_DETAIL = new Prefix("d/");
    public static final Prefix PREFIX_STUDY_GROUP_TAG = new Prefix("t/");
    public static final Prefix PREFIX_REMOVE_TAG = new Prefix("-t/");

}
