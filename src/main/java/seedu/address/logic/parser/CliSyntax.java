package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_COMPANY = new Prefix("c/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_AMOUNT = new Prefix("amt/");
    public static final Prefix PREFIX_OTHER_PARTY = new Prefix("o/");
    public static final Prefix PREFIX_DATE = new Prefix("dt/");
    public static final Prefix PREFIX_START_MONTH = new Prefix("s/");
    public static final Prefix PREFIX_END_MONTH = new Prefix("e/");
}
