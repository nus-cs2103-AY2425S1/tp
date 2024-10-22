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
    public static final Prefix PREFIX_PRIORITY = new Prefix("pri/");
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
    public static final Prefix PREFIX_DATE_OF_BIRTH = new Prefix("dob/");
    public static final Prefix PREFIX_INCOME = new Prefix("income/");
    public static final Prefix PREFIX_FAMILY_SIZE = new Prefix("familysize/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    public static final Prefix PREFIX_DATE = new Prefix("date/");
    public static final Prefix PREFIX_FROM = new Prefix("from/");
    public static final Prefix PREFIX_TO = new Prefix("to/");
}
