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
    public static final Prefix PREFIX_KEY = new Prefix("k/");
    public static final Prefix PREFIX_POSTALCODE = new Prefix("c/");
    public static final Prefix PREFIX_UNITNUMBER = new Prefix("u/");
    public static final Prefix PREFIX_TYPE = new Prefix("t/");
    public static final Prefix PREFIX_MEETING_TITLE = new Prefix("mt/");
    public static final Prefix PREFIX_MEETING_DATE = new Prefix("d/");
    public static final Prefix PREFIX_BID = new Prefix("b/");
    public static final Prefix PREFIX_ASK = new Prefix("a/");
    public static final Prefix PREFIX_GTE = new Prefix("gte/");
    public static final Prefix PREFIX_LTE = new Prefix("lte/");

    public static final Prefix PREFIX_BUYER_PHONE = new Prefix("bp/");
    public static final Prefix PREFIX_SELLER_PHONE = new Prefix("sp/");
}
