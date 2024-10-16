package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR;

/**
 * Parent abstract class for create commands.
 * Contains the index of the target to be deleted.
 */
public abstract class CreateCommand extends Command {

    public static final String COMMAND_WORD = "create";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a vendor or event and adds it to the list.\n"
            + "Parameters: " + PREFIX_VENDOR + "<empty> or " + PREFIX_EVENT + "<empty>" + "\n"
            + "Example to create a vendor: " + COMMAND_WORD + " " + PREFIX_VENDOR + " <other vendor parameters>\n"
            + "Example to create an event: " + COMMAND_WORD + " " + PREFIX_EVENT + " <other event parameters>";
}
