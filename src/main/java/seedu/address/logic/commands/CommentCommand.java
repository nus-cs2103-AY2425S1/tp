package seedu.address.logic.commands;

import seedu.address.model.Model;

public class CommentCommand extends Command {

    public static final String COMMAND_WORD = "remark";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Hello from comment");
    }

}
