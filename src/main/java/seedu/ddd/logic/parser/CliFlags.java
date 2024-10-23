package seedu.ddd.logic.parser;

/**
 * Contains Command Line Interface (CLI) flag definitions common to multiple commands
 */
public class CliFlags {

    /* Flag definitions */
    public static final Prefix FLAG_CLIENT = new Prefix("-c");
    public static final Prefix FLAG_VENDOR = new Prefix("-v");
    public static final Prefix FLAG_EVENT = new Prefix("-e");
}
