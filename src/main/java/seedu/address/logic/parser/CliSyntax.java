package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions for buyer class */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_BUDGET = new Prefix("b/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");


    /* Prefix definitions for meet-up class */
    public static final Prefix PREFIX_INFO = new Prefix("i/");
    public static final Prefix PREFIX_FROM = new Prefix("from/");
    public static final Prefix PREFIX_TO = new Prefix("to/");
    public static final Prefix PREFIX_ADDED_BUYER = new Prefix("add/");

    /* Prefix definitions for property class */
    public static final Prefix PREFIX_ASKING_PRICE = new Prefix("a/");
    public static final Prefix PREFIX_LOCATION = new Prefix("l/");
    public static final Prefix PREFIX_TYPE = new Prefix("type/");
}
