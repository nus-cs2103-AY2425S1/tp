package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {
    public static final Prefix PREFIX_SUPPLIER = new Prefix("-s");
    public static final Prefix PREFIX_DELIVERY = new Prefix("-d");

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_COMPANY = new Prefix("com/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    public static final Prefix PREFIX_PRODUCT = new Prefix("pro/");
    public static final Prefix PREFIX_DATETIME = new Prefix("on/");
    public static final Prefix PREFIX_SUPPLIER_INDEX = new Prefix("s/");
    public static final Prefix PREFIX_QUANTITY = new Prefix("q/");
    public static final Prefix PREFIX_COST = new Prefix("c/");
    public static final Prefix PREFIX_STATUS = new Prefix("stat/");
    public static final Prefix PREFIX_SORT_ORDER = new Prefix("so/");
    public static final Prefix PREFIX_SORT_BY = new Prefix("sb/");
}
