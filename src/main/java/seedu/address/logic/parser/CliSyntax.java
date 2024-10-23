package seedu.address.logic.parser;

import static seedu.address.logic.commands.FindCommand.SPECIFIC_FIND_PREFIX;

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
    public static final Prefix PREFIX_DELETE_TAG = new Prefix("dt/");
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
    public static final Prefix PREFIX_PROPERTY_LISTING = new Prefix("l/");
    public static final Prefix PREFIX_SPECIFIC_FIND = new Prefix(SPECIFIC_FIND_PREFIX);

}
