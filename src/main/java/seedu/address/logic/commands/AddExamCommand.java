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
 * Adds an exam to all current persons in the address book.
 */
public class AddExamCommand extends Command {

    public static final String COMMAND_WORD = "addExam";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an exam to every student in the address book.\n"
            + "Parameters: ex/EXAM_NAME\n"
            + "Example: " + COMMAND_WORD + " ex/Midterm";

    public static final String MESSAGE_ADDEXAM_SUCCESS = "New exam added: %1$s";
    public static final String MESSAGE_DUPLICATE_EXAM = "This exam already exists.";
    public static final String MESSAGE_UPDATE_EXAM = "Exam updated for all students: %1$s";

    private final Exam exam;

    /**
     * Creates an AddExamCommand to add the specified {@code Exam}
     */
    public AddExamCommand(Exam exam) {
        requireNonNull(exam);
        this.exam = exam;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        boolean update = false;
        boolean skip = false;

        for (Person personToEdit : lastShownList) {
            // If a student is added to the address book after an exam is added, adding the same exam will result in
            // the exam being added to the new student without duplicates in existing students.
            if (personToEdit.getExams().contains(exam)) {
                skip = true;
                continue;
            }
            update = true;
            Set<Exam> updatedExams = personToEdit.getExams();
            updatedExams.add(exam);
            Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getAddress(), personToEdit.getRegisterNumber(), personToEdit.getSex(),
                    personToEdit.getStudentClass(), personToEdit.getEcName(), personToEdit.getEcNumber(),
                    updatedExams, personToEdit.getTags());
            model.setPerson(personToEdit, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }
        if (!update) {
            // No updates, exam is a duplicate
            throw new CommandException(MESSAGE_DUPLICATE_EXAM);
        } else if (!skip) {
            // No skips, exam is a new exam
            return new CommandResult(String.format(MESSAGE_ADDEXAM_SUCCESS, exam));
        }
        // Both skips and updates, exam is added for newly added students
        return new CommandResult(String.format(MESSAGE_UPDATE_EXAM, exam));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddExamCommand)) {
            return false;
        }

        AddExamCommand otherAddExamCommand = (AddExamCommand) other;
        return exam.equals(otherAddExamCommand.exam);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("exam", exam)
                .toString();
    }
}
