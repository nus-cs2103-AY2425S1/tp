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
    public static final Prefix PREFIX_DAY = new Prefix("d/");
    public static final Prefix PREFIX_SCHEDULE = new Prefix("t/");
    public static final Prefix PREFIX_SUBJECT = new Prefix("s/");
    public static final Prefix PREFIX_RATE = new Prefix("r/");
    public static final Prefix PREFIX_PAID_AMOUNT = new Prefix("paid/");
    public static final Prefix PREFIX_OWED_AMOUNT = new Prefix("owed/");
    public static final Prefix PREFIX_AMOUNT = new Prefix("amount/");
    public static final Prefix PREFIX_HOUR = new Prefix("hr/");
}
