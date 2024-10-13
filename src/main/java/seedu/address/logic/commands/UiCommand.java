package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FONTSIZE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_THEME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Customize the Ui.
 */
public class UiCommand extends Command {

    public static final String COMMAND_WORD = "ui";

    public static final String COMMAND_USAGE = COMMAND_WORD + ": Change the UI setting. "
            + "Parameters: "
            + PREFIX_THEME + "THEME "
            + PREFIX_FONTSIZE + "FONTSIZE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_THEME + "light "
            + PREFIX_FONTSIZE + "medium ";

    public static final String MESSAGE_SUCCESS = "Ui updated.";
    public static final String MESSAGE_INVALID_THEME = "Invalid theme. "
            + "Please choose a string between “light” and “dark”.";
    public static final String MESSAGE_INVALID_FONTSIZE = "Invalid fontsize. "
            + "Please provide a positive integer between … and …,"
            + " or choose a string from “small”, “medium” and “large”.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult("This is UI command.");
    }
}
