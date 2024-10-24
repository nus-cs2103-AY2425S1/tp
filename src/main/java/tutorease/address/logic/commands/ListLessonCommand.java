package tutorease.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorease.address.model.Model.PREDICATE_SHOW_ALL_LESSONS;

import tutorease.address.logic.commands.exceptions.CommandException;
import tutorease.address.model.Model;

/**
 * Lists all lessons in the lesson list.
 */
public class ListLessonCommand extends Command {
    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all lessons";
    public static final String MESSAGE_NO_LESSONS_FOUND = "No lessons have been created:).";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        if (model.getFilteredLessonList().isEmpty()) {
            return new CommandResult(MESSAGE_NO_LESSONS_FOUND); // No lessons found
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
