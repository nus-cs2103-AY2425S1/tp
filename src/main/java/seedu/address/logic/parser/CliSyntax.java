package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */

    //Prefixes meant for events
    public static final Prefix EVENT_PREFIX_NAME = new Prefix("n/");
    public static final Prefix EVENT_PREFIX_START_TIME = new Prefix("s/");
    public static final Prefix EVENT_PREFIX_END_TIME = new Prefix("e/");
    public static final Prefix EVENT_PREFIX_DATE = new Prefix("d/");
    public static final Prefix EVENT_PREFIX_LOCATION = new Prefix("l/");
    public static final Prefix EVENT_PREFIX_DESCRIPTION = new Prefix("des/");

    /* Prefix meant for volunteer */
    public static final Prefix VOLUNTEER_PREFIX_NAME = new Prefix("n/");
    public static final Prefix VOLUNTEER_PREFIX_PHONE = new Prefix("p/");
    public static final Prefix VOLUNTEER_PREFIX_EMAIL = new Prefix("em/");
    public static final Prefix VOLUNTEER_PREFIX_AVAILABLE_DATE = new Prefix("d/");

    /* Prefix meant for assigning */
    public static final Prefix ASSIGN_VOLUNTEER_PREFIX_NAME = new Prefix("v/");
    public static final Prefix ASSIGN_EVENT_PREFIX_NAME = new Prefix("e/");
    public static final Prefix UNASSIGN_VOLUNTEER_PREFIX_NAME = new Prefix("v/");
    public static final Prefix UNASSIGN_EVENT_PREFIX_NAME = new Prefix("e/");

}
