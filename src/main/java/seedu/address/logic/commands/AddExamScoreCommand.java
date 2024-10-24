package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.exam.Exam;
import seedu.address.model.person.Person;

/**
 * Adds a score for an existing exam to an existing person in the address book.
 */
public class AddExamScoreCommand extends Command {

    public static final String COMMAND_WORD = "addExamScore";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an exam score to a student identified by index.\n"
            + "Parameters: [INDEX] ex/EXAM_NAME sc/EXAM_SCORE\n"
            + "Example: " + COMMAND_WORD + " 1 ex/Midterm sc/70";

    public static final String MESSAGE_ADDEXAMSCORE_SUCCESS = "Added exam score for person: %1$s";
    public static final String MESSAGE_EXAM_NOT_FOUND = "This exam does not exist.";

    private final Index index;
    private final Exam exam;
    private final String examScore;

    /**
     * Creates an AddExamScoreCommand to add a {@code examScore} to the specified {@code Exam}
     */
    public AddExamScoreCommand(Index index, Exam exam, String examScore) {
        requireAllNonNull(index, exam, examScore);
        this.index = index;
        this.exam = exam;
        this.examScore = examScore;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Set<Exam> updatedExams = personToEdit.getExams();
        Exam updatedExam = new Exam(exam.examName, examScore);
        if (!updatedExams.remove(exam)) {
            throw new CommandException(MESSAGE_EXAM_NOT_FOUND);
        }
        updatedExams.add(updatedExam);

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRegisterNumber(), personToEdit.getSex(),
                personToEdit.getStudentClass(), personToEdit.getEcName(), personToEdit.getEcNumber(),
                updatedExams, personToEdit.getTags(), personToEdit.getAttendances());
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADDEXAMSCORE_SUCCESS, Messages.format(editedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddExamScoreCommand)) {
            return false;
        }

        AddExamScoreCommand otherAddExamScoreCommand = (AddExamScoreCommand) other;
        return index.equals(otherAddExamScoreCommand.index)
                && exam.equals(otherAddExamScoreCommand.exam)
                && examScore.equals(otherAddExamScoreCommand.examScore);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("exam", exam)
                .add("examScore", examScore)
                .toString();
    }
}
