package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": filters contacts by tags. \n"
            + "Parameters: t/ [TAG]\n"
            + "Example: " + COMMAND_WORD + "t/ friends";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET =
            "Filter command not implemented yet";

    private final String tag;

    public FilterCommand(String tag) {
        this.tag = tag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(tag);
    }
}
