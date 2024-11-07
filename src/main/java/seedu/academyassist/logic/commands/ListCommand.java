package seedu.academyassist.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.academyassist.model.Model;

/**
 * Lists all student in the management system to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all students details\n"
            + "Students are sorted by student ID";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.sortAcademyAssistById();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
