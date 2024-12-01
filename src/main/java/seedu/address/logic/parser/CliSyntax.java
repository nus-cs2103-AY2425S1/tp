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
    public static final Prefix PREFIX_EMERGENCY_CONTACT_TO_EDIT = new Prefix("ec/");
    public static final Prefix PREFIX_EMERGENCY_CONTACT_NAME = new Prefix("ecname/");
    public static final Prefix PREFIX_EMERGENCY_CONTACT_PHONE = new Prefix("ecphone/");
    public static final Prefix PREFIX_EMERGENCY_CONTACT_RELATIONSHIP = new Prefix("ecrs/");
    public static final Prefix PREFIX_DOC_NAME = new Prefix("dname/");
    public static final Prefix PREFIX_DOC_PHONE = new Prefix("dphone/");
    public static final Prefix PREFIX_DOC_EMAIL = new Prefix("demail/");
}
