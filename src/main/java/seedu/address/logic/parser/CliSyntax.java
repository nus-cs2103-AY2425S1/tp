package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");

    //=========== Client Command Prefixes =============================================================
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_FROM = new Prefix("fr/");
    public static final Prefix PREFIX_TO = new Prefix("to/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    //=========== Listing Command Prefixes =============================================================
    public static final Prefix PREFIX_PRICE = new Prefix("pr/");
    public static final Prefix PREFIX_AREA = new Prefix("ar/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("add/");
    public static final Prefix PREFIX_REGION = new Prefix("reg/");
    public static final Prefix PREFIX_SELLER = new Prefix("sel/");
    public static final Prefix PREFIX_BUYER = new Prefix("buy/");

}
