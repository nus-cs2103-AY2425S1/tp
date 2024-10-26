package seedu.edulog.logic.commands;

import seedu.edulog.commons.util.ToStringBuilder;
import seedu.edulog.logic.commands.exceptions.CommandException;
import seedu.edulog.model.Model;

/**
 * Generates an idea for a gift to give to a student
 */
public class GiftCommand extends Command {
    public static final String COMMAND_WORD = "gift";

    public static final String MESSAGE_GIFT_SUCCESS_PREFIX = "Gift Idea: ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generates a gift idea for a student";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(MESSAGE_GIFT_SUCCESS_PREFIX + "randomGift");
    }

    @Override
    public boolean equals(Object object) {
        return (object instanceof GiftCommand);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
