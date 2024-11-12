package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.submission.Submission;

/**
 * Adds a status for an existing submission to an existing person in the address book.
 */
public class AddSubmissionStatusCommand extends Command {

    public static final String COMMAND_WORD = "addSubmissionStatus";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a submission status to a student identified by index.\n"
            + "Parameters: [INDEX] sm/SUBMISSION_NAME ss/SUBMISSION_STATUS\n"
            + "Example: " + COMMAND_WORD + " 1 sm/Assignment 1 ss/Y";

    public static final String MESSAGE_ADDSUBMISSIONSTATUS_SUCCESS = "Added submission status for person: %1$s\n"
            + "Submission: %2$s\n"
            + "Status: %3$s";
    public static final String MESSAGE_SUBMISSION_NOT_FOUND = "This submission does not exist.";
    public static final String MESSAGE_SUBMISSIONSTATUS_NOT_EDITED = "The submission status was not changed!.";

    private static final Logger logger = LogsCenter.getLogger(AddSubmissionStatusCommand.class);

    private final Index index;
    private final Submission submission;
    private final String submissionStatus;

    /**
     * Creates an AddSubmissionStatusCommand to add a {@code SubmissionStatus} to the specified {@code Submission}.
     */
    public AddSubmissionStatusCommand(Index index, Submission submission, String submissionStatus) {
        requireAllNonNull(index, submission, submissionStatus);
        this.index = index;
        this.submission = submission;
        this.submissionStatus = submissionStatus;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        assert lastShownList != null;

        if (index.getZeroBased() >= lastShownList.size()) {
            logger.log(Level.WARNING, "Index out of bounds.");
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        assert personToEdit != null;

        Set<Submission> updatedSubmissions = personToEdit.getSubmissions();

        // throw an error if the input submission status is the same as in the system
        for (Submission submissionInSet : updatedSubmissions) {
            if (submissionInSet.equals(submission) && submissionInSet.submissionStatus.equals(submissionStatus)) {
                logger.log(Level.WARNING, "Submission status unchanged.");
                throw new CommandException(MESSAGE_SUBMISSIONSTATUS_NOT_EDITED);
            }
        }
        Submission updatedSubmission = new Submission(submission.submissionName, submissionStatus);
        if (!updatedSubmissions.remove(submission)) {
            logger.log(Level.WARNING, "Submission does not exist.");
            throw new CommandException(MESSAGE_SUBMISSION_NOT_FOUND);
        }
        updatedSubmissions.add(updatedSubmission);

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRegisterNumber(), personToEdit.getSex(),
                personToEdit.getStudentClass(), personToEdit.getEcName(), personToEdit.getEcNumber(),
                personToEdit.getExams(), personToEdit.getTags(), personToEdit.getAttendances(), updatedSubmissions);
        model.setPerson(personToEdit, editedPerson);

        logger.log(Level.INFO, "Submission status added.");
        return new CommandResult(String.format(MESSAGE_ADDSUBMISSIONSTATUS_SUCCESS, editedPerson.getDisplayedName(),
                submission, submissionStatus));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddSubmissionStatusCommand)) {
            return false;
        }

        AddSubmissionStatusCommand otherAddSubmissionStatusCommand = (AddSubmissionStatusCommand) other;
        return index.equals(otherAddSubmissionStatusCommand.index)
                && submission.equals(otherAddSubmissionStatusCommand.submission)
                && submissionStatus.equals(otherAddSubmissionStatusCommand.submissionStatus);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("submission", submission)
                .add("submissionStatus", submissionStatus)
                .toString();
    }
}
