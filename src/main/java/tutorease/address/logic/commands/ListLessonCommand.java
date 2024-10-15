package tutorease.address.logic.commands;

import static java.util.Objects.requireNonNull;
import tutorease.address.logic.commands.exceptions.CommandException;
import tutorease.address.model.Model;

/**
 * Lists all lessons in the lesson list.
 */
public class ListLessonCommand extends Command{
    public static final String SUB_COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all lessons";
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateLessonList(PREDICATE_SHOW_ALL_LESSONS);
        return CommandResult(MESSAGE_SUCCESS);
    }
}
