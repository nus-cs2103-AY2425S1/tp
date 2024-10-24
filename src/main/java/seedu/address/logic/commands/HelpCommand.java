package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_HELP_KEYWORD;
import static seedu.address.logic.Messages.getOrderedListString;

import seedu.address.logic.commands.edit.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " <Command Keyword>"
            + ": Shows usage of the given command keyword\n"
            + "Example: " + COMMAND_WORD + " add\n"
            + COMMAND_WORD + ": Shows full list of keywords and usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String[] CURRENT_SUPPORTED_INSTRUCTION_USAGES = new String[]{
        AddCommand.MESSAGE_USAGE,
        DeleteCommand.MESSAGE_USAGE,
        EditCommand.MESSAGE_USAGE,
        FindCommand.MESSAGE_USAGE,
        ListCommand.MESSAGE_USAGE,
        HelpCommand.MESSAGE_USAGE,
        ClearCommand.MESSAGE_USAGE,
        ExitCommand.MESSAGE_USAGE
    };

    public static final String FULL_HELP_MESSAGE = getOrderedListString(CURRENT_SUPPORTED_INSTRUCTION_USAGES);

    private final String keyword;

    public HelpCommand(String keyword) {
        this.keyword = keyword.trim().toLowerCase();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        requireNonNull(keyword);
        switch (keyword) {
        case "add":
            return new CommandResult(AddCommand.MESSAGE_USAGE, false, false);
        case "delete":
            return new CommandResult(DeleteCommand.MESSAGE_USAGE, false, false);
        case "edit":
            return new CommandResult(EditCommand.MESSAGE_USAGE, false, false);
        case "find":
            return new CommandResult(FindCommand.MESSAGE_USAGE, false, false);
        case "list":
            return new CommandResult(ListCommand.MESSAGE_USAGE, false, false);
        case "help":
            return new CommandResult(MESSAGE_USAGE, false, false);
        case "clear":
            return new CommandResult(ClearCommand.MESSAGE_USAGE, false, false);
        case "exit":
            return new CommandResult(ExitCommand.MESSAGE_USAGE, false, false);
        case "":
            return new CommandResult(FULL_HELP_MESSAGE, true, false);
        default:
            throw new CommandException(String.format(MESSAGE_INVALID_HELP_KEYWORD, keyword));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HelpCommand)) {
            return false;
        }

        HelpCommand otherHelpCommand = (HelpCommand) other;
        return this.keyword.equals(otherHelpCommand.keyword);
    }

    @Override
    public String toString() {
        return String.format("help %s", keyword);
    }
}
