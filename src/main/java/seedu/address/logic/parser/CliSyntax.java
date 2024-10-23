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
    public static final Prefix PREFIX_TAG = new Prefix("t/"); // Can be deleted later

    public static final Prefix PREFIX_NRIC = new Prefix("i/");
    public static final Prefix PREFIX_SEX = new Prefix("s/");
    public static final Prefix PREFIX_BIRTHDATE = new Prefix("d/");
    public static final Prefix PREFIX_HEALTHSERVICE = new Prefix("h/");
    public static final Prefix PREFIX_ALLERGY = new Prefix("al/");
    public static final Prefix PREFIX_BLOODTYPE = new Prefix("b/");
    public static final Prefix PREFIX_HEALTHRECORD = new Prefix("hr/");
    public static final Prefix PREFIX_NOTE = new Prefix("no/");
    public static final Prefix PREFIX_NOKNAME = new Prefix("nokn/");
    public static final Prefix PREFIX_NOKPHONE = new Prefix("nokp/");
    public static final Prefix PREFIX_HEALTHRISK = new Prefix("rl/");
    public static final Prefix PREFIX_APPOINTMENT = new Prefix("appt/");
    public static final Prefix PREFIX_DATETIME = new Prefix("dt/");
    public static final Prefix PREFIX_STARTDATE = new Prefix("sd/");
    public static final Prefix PREFIX_ENDDATE = new Prefix("ed/");

}
