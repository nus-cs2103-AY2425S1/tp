package seedu.address.logic.commands.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LESSONS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.model.Model;

/**
 * Lists all lessons in the address book to the user.
 */
public class ListLessonsCommand extends Command {

    public static final String COMMAND_WORD = "listlessons";
    public static final CommandType COMMAND_TYPE = CommandType.LISTLESSONS;
    public static final String MESSAGE_SUCCESS = "Listed all lessons";

    @Override
    public CommandType getCommandType() {
        return COMMAND_TYPE;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        return new CommandResult(MESSAGE_SUCCESS, COMMAND_TYPE);
    }
}
