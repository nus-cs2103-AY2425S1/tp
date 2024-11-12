package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("/name");
    public static final Prefix PREFIX_GENDER = new Prefix("/gender");
    public static final Prefix PREFIX_PHONE = new Prefix("/contact");
    public static final Prefix PREFIX_EMAIL = new Prefix("/email");
    public static final Prefix PREFIX_ADDRESS = new Prefix("/address");
    public static final Prefix PREFIX_TAG = new Prefix("/tag");
    public static final Prefix PREFIX_SUBJECT = new Prefix("/subject");
    public static final Prefix PREFIX_CLASSES = new Prefix("/classes");
    public static final Prefix PREFIX_ATTENDANCE = new Prefix("/attendance");
    public static final Prefix PREFIX_NEXT_OF_KIN = new Prefix("/nok");
    public static final Prefix PREFIX_EMERGENCY_CONTACT = new Prefix("/emergency");

}
