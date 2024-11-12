package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("name/", "n/");
    public static final Prefix PREFIX_EMAIL = new Prefix("email/", "e/");
    public static final Prefix PREFIX_TAG = new Prefix("tag/", "t/");
    public static final Prefix PREFIX_ASSIGNMENT = new Prefix("assignment/", "a/");
    public static final Prefix PREFIX_SCORE = new Prefix("score/", "s/");
    public static final Prefix PREFIX_TELEGRAM = new Prefix("telegram/", "tele/");
    public static final Prefix PREFIX_GITHUB = new Prefix("github/", "g/");
    public static final Prefix PREFIX_WEEK = new Prefix("week/", "w/");
    public static final Prefix PREFIX_SORTORDER = new Prefix("order/", "o/");
    public static final Prefix PREFIX_PATH = new Prefix("path/", "p/");
}
