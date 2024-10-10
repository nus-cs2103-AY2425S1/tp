package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_RENTAL_START_DATE = new Prefix("s/");
    public static final Prefix PREFIX_RENTAL_END_DATE = new Prefix("e/");
    public static final Prefix PREFIX_RENT_DUE_DATE = new Prefix("dd/");
    public static final Prefix PREFIX_MONTHLY_RENT = new Prefix("m/");
    public static final Prefix PREFIX_DEPOSIT = new Prefix("d/");
    public static final Prefix PREFIX_CUSTOMER_LIST = new Prefix("cl/");
}
