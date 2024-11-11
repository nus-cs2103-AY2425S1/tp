package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n\\");
    public static final Prefix PREFIX_PHONE = new Prefix("p\\");
    public static final Prefix PREFIX_EMAIL = new Prefix("e\\");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a\\");
    public static final Prefix PREFIX_TAG = new Prefix("t\\");
    public static final Prefix PREFIX_FINANCIAL_INFO = new Prefix("fi\\");
    public static final Prefix PREFIX_SOCIAL_MEDIA_HANDLE = new Prefix("s\\");
    public static final Prefix PREFIX_EXPORT_FORMAT = new Prefix("format\\");
}
