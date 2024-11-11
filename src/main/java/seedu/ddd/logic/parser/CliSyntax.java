package seedu.ddd.logic.parser;

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

    public static final Prefix PREFIX_CLIENTS = new Prefix("c/");
    public static final Prefix PREFIX_VENDORS = new Prefix("v/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_SERVICE = new Prefix("s/");
    public static final Prefix PREFIX_ID = new Prefix("id/");
    public static final Prefix PREFIX_DESC = new Prefix("des/");

    public static final Prefix[] ALL_PREFIXES = {
        PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG,
        PREFIX_CLIENTS, PREFIX_VENDORS, PREFIX_DATE, PREFIX_SERVICE, PREFIX_ID, PREFIX_DESC
    };
}
