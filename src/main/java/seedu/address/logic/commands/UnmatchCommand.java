package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.common.Name;
import seedu.address.model.job.Job;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.skill.Skill;

/**
 * Unmatches a contact from their job.
 */
public class UnmatchCommand extends Command {
    public static final int CONTACT_INDEX_POS = 0;
    public static final int JOB_INDEX_POS = 1;
    public static final String COMMAND_WORD = "unmatch";
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Unmatch a contact from a job\nParameters: <CONTACT_INDEX> <JOB_INDEX>\nExample: "
                    + COMMAND_WORD + " 2 1";

    public static final String MESSAGE_UNMATCH_SUCCESS = "Unmatched Contact: %1$s with Job: %2$s";
    public static final String MESSAGE_CONTACT_NOT_MATCHED = "This contact is not matched with this job!";
    public static final String MESSAGE_CONTACT_HAS_NO_JOBS = "The contact %1$s is not associated with any job";

    private final Index contactIndex;
    private final Index jobIndex;

    /**
     * @param contactIndex Index of the contact in the filtered person list to unmatch.
     * @param jobIndex Index of the job in the filtered job list to unmatch.
     */
    public UnmatchCommand(Index contactIndex, Index jobIndex) {
        requireNonNull(contactIndex);
        requireNonNull(jobIndex);

        this.contactIndex = contactIndex;
        this.jobIndex = jobIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownPersonList = model.getFilteredPersonList();
        if (contactIndex.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        List<Job> lastShownJobList = model.getFilteredJobList();
        if (jobIndex.getZeroBased() >= lastShownJobList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        Person contactToUnmatch = lastShownPersonList.get(contactIndex.getZeroBased());
        Job jobToUnmatch = lastShownJobList.get(jobIndex.getZeroBased());

        assert contactToUnmatch != null;
        assert jobToUnmatch != null;

        validateMatching(contactToUnmatch, jobToUnmatch);

        // Proceed with unmatching the contact from the job
        Person unmatchedContact = unmatchContactFromJob(contactToUnmatch);

        // Update the model with the unmatched contact and job
        model.setPerson(contactToUnmatch, unmatchedContact);

        return new CommandResult(
                String.format(MESSAGE_UNMATCH_SUCCESS, Messages.format(unmatchedContact), Messages.format(jobToUnmatch))
        );
    }

    /**
     * Removes the job from the contact.
     */
    private static Person unmatchContactFromJob(Person contact) {
        Name name = contact.getName();
        Phone phone = contact.getPhone();
        Email email = contact.getEmail();
        Role role = contact.getRole();
        Set<Skill> skills = contact.getSkills();
        return new Person(name, phone, email, role, skills);
    }

    /**
     * Validates that the contact and job are matched to each other and that both
     * entities have relevant matches.
     */
    private void validateMatching(Person contactToUnmatch, Job jobToUnmatch) throws CommandException {
        // Check if the contact has no jobs associated at all
        if (!contactToUnmatch.isMatchPresent()) {
            throw new CommandException(String.format(MESSAGE_CONTACT_HAS_NO_JOBS, contactToUnmatch.getName()));
        }

        boolean hasContactMatchedJob = contactToUnmatch.hasMatched(jobToUnmatch.getIdentifier());

        // Check and ensure the contact and job are matched to each other before unmatching
        if (!(hasContactMatchedJob)) {
            throw new CommandException(MESSAGE_CONTACT_NOT_MATCHED);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UnmatchCommand)) {
            return false;
        }

        UnmatchCommand otherCommand = (UnmatchCommand) other;
        return contactIndex.equals(otherCommand.contactIndex)
                && jobIndex.equals(otherCommand.jobIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("contactIndex", contactIndex)
                .add("jobIndex", jobIndex).toString();
    }
}
