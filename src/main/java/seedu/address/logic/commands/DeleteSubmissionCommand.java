package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.submission.Submission;

/**
 * Deletes the specified submission for all current persons in the address book.
 */
public class DeleteSubmissionCommand extends Command {

    public static final String COMMAND_WORD = "deleteSubmission";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the specified submission for every student "
            + "in the address book.\n"
            + "Parameters: sm/SUBMISSION_NAME\n"
            + "Example: " + COMMAND_WORD + " sm/Assignment 1";

    public static final String MESSAGE_DELETESUBMISSION_SUCCESS = "Submission deleted: %1$s";
    public static final String MESSAGE_SUBMISSION_NOT_FOUND = "This submission was not found.";

    private static final Logger logger = LogsCenter.getLogger(DeleteSubmissionCommand.class);

    private final Submission submission;

    /**
     * Creates a DeleteSubmissionCommand to delete the specified {@code Submission}.
     */
    public DeleteSubmissionCommand(Submission submission) {
        requireNonNull(submission);
        this.submission = submission;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getUnfilteredPersonList();
        assert lastShownList != null;

        boolean isUpdated = false;

        for (Person personToEdit : lastShownList) {
            Set<Submission> updatedSubmissions = personToEdit.getSubmissions();
            if (updatedSubmissions.remove(submission)) {
                isUpdated = true;
            }
            Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getAddress(), personToEdit.getRegisterNumber(), personToEdit.getSex(),
                    personToEdit.getStudentClass(), personToEdit.getEcName(), personToEdit.getEcNumber(),
                    personToEdit.getExams(), personToEdit.getTags(), personToEdit.getAttendances(), updatedSubmissions);
            model.setPerson(personToEdit, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }
        if (!isUpdated) {
            // No updates, submission not found in any students
            logger.log(Level.WARNING, "Submission does not exist.");
            throw new CommandException(MESSAGE_SUBMISSION_NOT_FOUND);
        }
        logger.log(Level.INFO, "Submission deleted.");
        return new CommandResult(String.format(MESSAGE_DELETESUBMISSION_SUCCESS, submission));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteSubmissionCommand)) {
            return false;
        }

        DeleteSubmissionCommand otherDeleteSubmissionCommand = (DeleteSubmissionCommand) other;
        return submission.equals(otherDeleteSubmissionCommand.submission);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("submission", submission)
                .toString();
    }
}
