package seedu.eventtory.logic.commands;

import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_VENDOR;

/**
 * Parent abstract class for create commands.
 */
public abstract class CreateCommand extends Command {

    public static final String COMMAND_WORD = "create";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a vendor or event and adds it to the list.\n"
            + "Parameters: " + PREFIX_VENDOR + " <other vendor parameters> or "
            + PREFIX_EVENT + " <other event parameters>" + "\n"
            + "Example to create a vendor: " + COMMAND_WORD + " " + PREFIX_VENDOR + " "
            + PREFIX_NAME + "Adam's Bakery "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_DESCRIPTION + "Pastries and cakes, bake in a day "
            + PREFIX_TAG + "pastry "
            + PREFIX_TAG + "fast" + "\n"
            + "Example to create an event: " + COMMAND_WORD + " " + PREFIX_EVENT + " "
            + PREFIX_NAME + "John Baby Shower" + " "
            + PREFIX_DATE + "2021-10-10";
}
