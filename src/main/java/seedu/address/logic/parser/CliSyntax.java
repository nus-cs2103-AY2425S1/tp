package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("addr/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    public static final Prefix PREFIX_BIRTHDAY = new Prefix("b/");
    public static final Prefix PREFIX_SEARCH_APPOINTMENT = new Prefix("a/");
    public static final Prefix PREFIX_SEARCH_POLICY = new Prefix("p/");

    public static final Prefix PREFIX_APPOINTMENT = new Prefix("appt/");
    public static final Prefix PREFIX_POLICY = new Prefix("po/");
    public static final Prefix PREFIX_POLICY_NAME = new Prefix("pon/");
    public static final Prefix PREFIX_POLICY_START_DATE = new Prefix("pos/");
    public static final Prefix PREFIX_POLICY_END_DATE = new Prefix("poe/");
    public static final Prefix PREFIX_NEXT_PAYMENT_DATE = new Prefix("paydate/");
    public static final Prefix PREFIX_PAYMENT_AMOUNT = new Prefix("amt/");



}
