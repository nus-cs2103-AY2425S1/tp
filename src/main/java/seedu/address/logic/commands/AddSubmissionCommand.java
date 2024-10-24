package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.submission.Submission;

/**
 * Adds a submission to all current persons in the address book.
 */
public class AddSubmissionCommand extends Command {

    public static final String COMMAND_WORD = "addSubmission";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a submission to every student in the address book.\n"
            + "Parameters: sm/SUBMISSION_NAME\n"
            + "Example: " + COMMAND_WORD + " sm/Assignment 1";

    public static final String MESSAGE_ADDSUBMISSION_SUCCESS = "New submission added: %1$s";
    public static final String MESSAGE_DUPLICATE_SUBMISSION = "This submission already exists.";
    public static final String MESSAGE_UPDATE_SUBMISSION = "Submission updated for all students: %1$s";

    private final Submission submission;

    /**
     * Creates an AddSubmissionCommand to add the specified {@code Submission}
     */
    public AddSubmissionCommand(Submission submission) {
        requireNonNull(submission);
        this.submission = submission;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        boolean update = false;
        boolean skip = false;

        for (Person personToEdit : lastShownList) {
            // If a student is added to the address book after a submission is added, adding the same submission will
            // result in the submission being added to the new student without duplicates in existing students.
            if (personToEdit.getSubmissions().contains(submission)) {
                skip = true;
                continue;
            }
            update = true;
            Set<Submission> updatedSubmissions = personToEdit.getSubmissions();
            updatedSubmissions.add(submission);
            Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getAddress(), personToEdit.getRegisterNumber(), personToEdit.getSex(),
                    personToEdit.getStudentClass(), personToEdit.getEcName(), personToEdit.getEcNumber(),
                    personToEdit.getExams(), personToEdit.getTags(), personToEdit.getAttendances(), updatedSubmissions);
            model.setPerson(personToEdit, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }
        if (!update) {
            // No updates, submission is a duplicate
            throw new CommandException(MESSAGE_DUPLICATE_SUBMISSION);
        } else if (!skip) {
            // No skips, submission is a new submission
            return new CommandResult(String.format(MESSAGE_ADDSUBMISSION_SUCCESS, submission));
        }
        // Both skips and updates, submission is added for newly added students
        return new CommandResult(String.format(MESSAGE_UPDATE_SUBMISSION, submission));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddSubmissionCommand)) {
            return false;
        }

        AddSubmissionCommand otherAddSubmissionCommand = (AddSubmissionCommand) other;
        return submission.equals(otherAddSubmissionCommand.submission);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("submission", submission)
                .toString();
    }
}
