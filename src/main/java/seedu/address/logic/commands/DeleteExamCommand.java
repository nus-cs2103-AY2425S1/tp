package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.exam.Exam;
import seedu.address.model.person.Person;

/**
 * Deletes the specified exam for all current persons in the address book.
 */
public class DeleteExamCommand extends Command {

    public static final String COMMAND_WORD = "deleteExam";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the specified exam for every student "
            + "in the address book.\n"
            + "Parameters: ex/EXAM_NAME\n"
            + "Example: " + COMMAND_WORD + " ex/Midterm";

    public static final String MESSAGE_DELETEEXAM_SUCCESS = "Exam deleted: %1$s";
    public static final String MESSAGE_EXAM_NOT_FOUND = "This exam was not found.";

    private final Exam exam;

    /**
     * Creates an DeleteExamCommand to delete the specified {@code Exam}
     */
    public DeleteExamCommand(Exam exam) {
        requireNonNull(exam);
        this.exam = exam;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        boolean update = false;

        for (Person personToEdit : lastShownList) {
            Set<Exam> updatedExams = personToEdit.getExams();
            if (updatedExams.remove(exam)) {
                update = true;
            }
            Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getAddress(), personToEdit.getRegisterNumber(), personToEdit.getSex(),
                    personToEdit.getStudentClass(), personToEdit.getEcName(), personToEdit.getEcNumber(),
                    updatedExams, personToEdit.getTags(), personToEdit.getAttendances(), personToEdit.getSubmissions());
            model.setPerson(personToEdit, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }
        if (!update) {
            // No updates, exam not found in any students
            throw new CommandException(MESSAGE_EXAM_NOT_FOUND);
        }
        return new CommandResult(String.format(MESSAGE_DELETEEXAM_SUCCESS, exam));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteExamCommand)) {
            return false;
        }

        DeleteExamCommand otherdeleteExamCommand = (DeleteExamCommand) other;
        return exam.equals(otherdeleteExamCommand.exam);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("exam", exam)
                .toString();
    }
}
