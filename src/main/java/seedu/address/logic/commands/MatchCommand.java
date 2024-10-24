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
import seedu.address.model.job.JobCompany;
import seedu.address.model.job.JobDescription;
import seedu.address.model.job.JobSalary;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.skill.Skill;
import seedu.address.model.tag.Tag;

/**
 * Matches a contact to a job.
 */
public class MatchCommand extends Command {

    public static final int CONTACT_INDEX_POS = 0;
    public static final int JOB_INDEX_POS = 1;

    public static final String COMMAND_WORD = "match";
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Match a contact to a job\nParameters: <CONTACT_INDEX> <JOB_INDEX>\nExample: "
                    + COMMAND_WORD + " 2 1";
    public static final String MESSAGE_MATCH_SUCCESS = "Matched Contact: %1$s with Job: %2$s";
    public static final String MESSAGE_HAS_OTHER_MATCHES = "Contact already has another job!";
    public static final String MESSAGE_ALREADY_MATCHED = "Contact already matched with this job!";

    private final Index contactIndex;
    private final Index jobIndex;

    /**
     * @param contactIndex Index of the contact in the filtered person list to match.
     * @param jobIndex Index of the job in the filtered contact list to match.
     */
    public MatchCommand(Index contactIndex, Index jobIndex) {
        requireNonNull(contactIndex);
        requireNonNull(jobIndex);

        this.contactIndex = contactIndex;
        this.jobIndex = jobIndex;
    }

    private static Person matchContactToJob(Person contact, String jobIdentifier) {
        Name name = contact.getName();
        Phone phone = contact.getPhone();
        Email email = contact.getEmail();
        Role role = contact.getRole();
        Set<Skill> skills = contact.getSkills();
        return new Person(name, phone, email, role, skills, jobIdentifier);
    }

    private static Job matchJobToContact(Job job, String contactIdentifier) {
        Name name = job.getName();
        JobCompany company = job.getCompany();
        JobSalary salary = job.getSalary();
        JobDescription description = job.getDescription();
        Set<Tag> requirements = job.getRequirements();
        Set<String> matches = job.getMatches();
        matches.add(contactIdentifier);
        return new Job(name, company, salary, description, requirements, matches);
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

        Person contactToMatch = lastShownPersonList.get(contactIndex.getZeroBased());
        Job jobToMatch = lastShownJobList.get(jobIndex.getZeroBased());

        assert contactToMatch != null;
        assert jobToMatch != null;

        // TODO: Let's assume contact name is used preserve uniqueness for now
        final String contactIdentifier = contactToMatch.getIdentifier();
        final String jobIdentifier = jobToMatch.getIdentifier();

        boolean hasContactMatchedJob = contactToMatch.hasMatched(jobIdentifier);
        boolean hasJobMatchedContact = jobToMatch.hasMatched(contactIdentifier);

        if (hasContactMatchedJob && hasJobMatchedContact) {
            throw new CommandException(MESSAGE_ALREADY_MATCHED);
        }

        if (contactToMatch.isMatchPresent()) {
            throw new CommandException(MESSAGE_HAS_OTHER_MATCHES);
        }

        // The bi-direction association should always be maintained
        // Assert when there is a unidirectional association
        assert hasContactMatchedJob == hasJobMatchedContact;

        Person matchedContact = matchContactToJob(contactToMatch, jobIdentifier);
        Job matchedJob = matchJobToContact(jobToMatch, contactIdentifier);

        model.setPerson(contactToMatch, matchedContact);
        model.setJob(jobToMatch, matchedJob);
        return new CommandResult(
                String.format(MESSAGE_MATCH_SUCCESS, Messages.format(matchedContact), Messages.format(matchedJob)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MatchCommand)) {
            return false;
        }

        MatchCommand otherEditCommand = (MatchCommand) other;
        return contactIndex.equals(otherEditCommand.contactIndex) && jobIndex.equals(otherEditCommand.jobIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("contactIndex", contactIndex).add("jobIndex", jobIndex).toString();
    }
}
