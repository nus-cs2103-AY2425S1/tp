package tutorease.address.logic.commands;

import static java.util.Objects.requireNonNull;

import tutorease.address.model.LessonSchedule;
import tutorease.address.model.Model;
import tutorease.address.model.TutorEase;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setTutorEase(new TutorEase());
        model.setLessonSchedule(new LessonSchedule());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
