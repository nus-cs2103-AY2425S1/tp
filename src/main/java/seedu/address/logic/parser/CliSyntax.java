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

    //Prefixes meant for events
    public static final Prefix EVENT_PREFIX_NAME = new Prefix("n/");
    public static final Prefix EVENT_PREFIX_START_TIME = new Prefix("s/");
    public static final Prefix EVENT_PREFIX_END_TIME = new Prefix("e/");
    public static final Prefix EVENT_PREFIX_DATE = new Prefix("d/");
    public static final Prefix EVENT_PREFIX_LOCATION = new Prefix("l/");
    public static final Prefix EVENT_PREFIX_DESCRIPTION = new Prefix("des/");

}
