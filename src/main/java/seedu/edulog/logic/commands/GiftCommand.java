package seedu.edulog.logic.commands;

import seedu.edulog.commons.exceptions.DataLoadingException;
import seedu.edulog.commons.util.GiftUtil;
import seedu.edulog.commons.util.ToStringBuilder;
import seedu.edulog.logic.commands.exceptions.CommandException;
import seedu.edulog.model.Model;
import seedu.edulog.model.gift.Gift;

/**
 * Generates an idea for a gift to give to a student
 */
public class GiftCommand extends Command {
    public static final String COMMAND_WORD = "gift";

    public static final String MESSAGE_GIFT_SUCCESS_PREFIX = "Gift Idea: ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generates a gift idea for a student";

    public static final String MESSAGE_GIFT_FAILURE = "Failed to generate a gift idea due to data loading error.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            Gift gift = GiftUtil.getRandomGift();
            return new CommandResult(MESSAGE_GIFT_SUCCESS_PREFIX + gift);
        } catch (DataLoadingException e) {
            throw new CommandException(MESSAGE_GIFT_FAILURE, e);
        }
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
