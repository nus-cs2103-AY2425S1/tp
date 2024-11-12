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
    public static final Prefix PREFIX_ISSUE = new Prefix("i/");
    public static final Prefix PREFIX_VRN = new Prefix("vrn/");
    public static final Prefix PREFIX_VIN = new Prefix("vin/");
    public static final Prefix PREFIX_MAKE = new Prefix("make/");
    public static final Prefix PREFIX_MODEL = new Prefix("model/");
}
