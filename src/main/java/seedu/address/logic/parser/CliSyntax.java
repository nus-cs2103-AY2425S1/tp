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
    public static final Prefix PREFIX_ID = new Prefix("id/");

    /* Prefix definitions for Tutorial-related commands */
    public static final Prefix PREFIX_TUT_NAME = new Prefix("tn/"); // Prefix for Tutorial Name
    public static final Prefix PREFIX_TUT_ID = new Prefix("id/"); // Prefix for Tutorial ID

}
