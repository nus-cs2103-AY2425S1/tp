package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_DATE = new Prefix("-d ");

    public static final Prefix PREFIX_INDEX = new Prefix("-i ");

    public static final Prefix PREFIX_NAME = new Prefix("-n ");
    public static final Prefix PREFIX_PHONE = new Prefix("-p ");
    public static final Prefix PREFIX_EMAIL = new Prefix("-e ");
    public static final Prefix PREFIX_RELATIONSHIP = new Prefix("-rs ");
    public static final Prefix PREFIX_ATTENDEES = new Prefix("-a ");
    public static final Prefix PREFIX_REMOVE_ATTENDEE = new Prefix("-r ");

}
