package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_ASSIGNMENT = new Prefix("a/");
    public static final Prefix PREFIX_DEADLINE = new Prefix("d/");
    public static final Prefix PREFIX_STATUS = new Prefix("s/");
    public static final Prefix PREFIX_GRADE = new Prefix("g/");
    public static final Prefix PREFIX_TUTORIAL_GROUP = new Prefix("tg/");
    public static final Prefix PREFIX_STUDENT_NUMBER = new Prefix("sn/");
    public static final Prefix PREFIX_DATE = new Prefix("dt/");
    public static final Prefix PREFIX_PRESENT = new Prefix("pr/");
    public static final Prefix[] PREFIX_ARRAY = {
        PREFIX_NAME, PREFIX_PHONE,
        PREFIX_ASSIGNMENT, PREFIX_DEADLINE,
        PREFIX_STATUS, PREFIX_GRADE,
        PREFIX_TUTORIAL_GROUP, PREFIX_STUDENT_NUMBER,
        PREFIX_DATE, PREFIX_PRESENT
    };
}
