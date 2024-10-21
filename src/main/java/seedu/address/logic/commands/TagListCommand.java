package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class TagListCommand extends Command {
    public static final String COMMAND_WORD = "taglist";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(new String("These are the existing tags: \n" + model.getTagList()));
    }
}
