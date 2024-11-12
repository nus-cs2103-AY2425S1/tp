package tahub.contacts.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_MATRICULATION_NUMBER = new Prefix("m/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_COURSE_CODE = new Prefix("c/");
    // t/ prefix is used by tags...
    public static final Prefix PREFIX_TUTORIAL_ID = new Prefix("tut/");
    public static final Prefix PREFIX_TUTORIAL = new Prefix("tut/");
}
