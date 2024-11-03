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
    public static final Prefix PREFIX_POLICY_TYPE = new Prefix("pt/");
    public static final Prefix PREFIX_POLICY_PREMIUM_AMOUNT = new Prefix("pa/");
    public static final Prefix PREFIX_POLICY_COVERAGE_AMOUNT = new Prefix("ca/");
    public static final Prefix PREFIX_POLICY_EXPIRY_DATE = new Prefix("ed/");
    public static final Prefix PREFIX_CLAIM_STATUS = new Prefix("s/");
    public static final Prefix PREFIX_CLAIM_DESC = new Prefix("d/");
    public static final Prefix PREFIX_CLAIM_INDEX = new Prefix("c/");

}
