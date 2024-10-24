package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.assignment.AssignmentList;
import seedu.address.model.student.Student;
import seedu.address.model.tut.Tutorial;
import seedu.address.model.tut.TutorialList;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Data has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        model.setAssignments(new AssignmentList());
        model.setTutorials(new TutorialList());
        for (Student s : Tutorial.none().getStudents()) {
            Tutorial.none().deleteStudent(s);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
