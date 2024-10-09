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
    public static final Prefix PREFIX_EVENT_NAME = new Prefix("E/");
    public static final Prefix PREFIX_EVENT_TIME = new Prefix("T/");
    public static final Prefix PREFIX_EVENT_VENUE = new Prefix("V/");

    public static final Prefix PREFIX_EVENT_CONTACT = new Prefix("C/");

}
