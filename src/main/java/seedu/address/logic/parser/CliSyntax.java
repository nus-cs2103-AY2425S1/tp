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
    public static final Prefix PREFIX_SUBJECT = new Prefix("s/");
    public static final Prefix PREFIX_INFO = new Prefix("i/");
    public static final Prefix PREFIX_FROM = new Prefix("f/");
    public static final Prefix PREFIX_TO = new Prefix("t/");
    public static final Prefix PREFIX_ADDED_BUYER = new Prefix("n/");

    /* Prefix definitions for property class */
    public static final Prefix PREFIX_ASKING_PRICE = new Prefix("s/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TYPE = new Prefix("t/");
}
