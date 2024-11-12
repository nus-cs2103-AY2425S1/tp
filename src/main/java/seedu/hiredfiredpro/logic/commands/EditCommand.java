package seedu.hiredfiredpro.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_INTERVIEW_SCORE;
import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_SKILLS;
import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.hiredfiredpro.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.hiredfiredpro.model.person.Person.DEFAULT_TAG_PENDING;
import static seedu.hiredfiredpro.model.person.Person.TAG_HIRED;
import static seedu.hiredfiredpro.model.person.Person.TAG_REJECTED;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.hiredfiredpro.commons.core.index.Index;
import seedu.hiredfiredpro.commons.util.CollectionUtil;
import seedu.hiredfiredpro.commons.util.ToStringBuilder;
import seedu.hiredfiredpro.logic.Messages;
import seedu.hiredfiredpro.logic.commands.exceptions.CommandException;
import seedu.hiredfiredpro.model.Model;
import seedu.hiredfiredpro.model.person.Email;
import seedu.hiredfiredpro.model.person.InterviewScore;
import seedu.hiredfiredpro.model.person.Job;
import seedu.hiredfiredpro.model.person.Name;
import seedu.hiredfiredpro.model.person.Person;
import seedu.hiredfiredpro.model.person.Phone;
import seedu.hiredfiredpro.model.skill.Skill;
import seedu.hiredfiredpro.model.tag.Tag;

/**
 * Edits the details of an existing candidate in HiredFiredPro.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the candidate identified "
            + "by the index number used in the displayed candidate list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_JOB + "JOB] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_SKILLS + "SKILL] "
            + "[" + PREFIX_INTERVIEW_SCORE + "INTERVIEW SCORE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited candidate: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This candidate already exists in HiredFiredPro.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)), true,
                editedPerson);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    public static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Job updatedJob = editPersonDescriptor.getJob().orElse(personToEdit.getJob());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Set<Skill> updatedSkills = editPersonDescriptor.getSkills().orElse(personToEdit.getSkills());
        InterviewScore updatedInterviewScore = editPersonDescriptor.getInterviewScore()
                .orElse(personToEdit.getInterviewScore());

        Set<Tag> currentTags = personToEdit.getTags();
        Set<Tag> statusTags = currentTags.stream()
                .filter(tag -> tag.equalsIgnoreCase(TAG_HIRED) || tag.equalsIgnoreCase(TAG_REJECTED)
                        || tag.equalsIgnoreCase(DEFAULT_TAG_PENDING))
                .collect(Collectors.toSet());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        updatedTags = updatedTags.stream()
                .filter(tag ->!(tag.equalsIgnoreCase(TAG_HIRED)) && !(tag.equalsIgnoreCase(TAG_REJECTED))
                        && !(tag.equalsIgnoreCase(DEFAULT_TAG_PENDING)))
                .collect(Collectors.toSet());
        updatedTags.addAll(statusTags);

        Person editedPerson = new Person(updatedName, updatedJob, updatedPhone, updatedEmail, updatedSkills,
                updatedInterviewScore, updatedTags);
        if (personToEdit.isHired()) {
            editedPerson.markAsHired();
        } else if (personToEdit.isRejected()) {
            editedPerson.markAsRejected();
        }
        return editedPerson;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Job job;
        private Phone phone;
        private Email email;
        private Set<Skill> skills;
        private InterviewScore interviewScore;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code skills} and {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setJob(toCopy.job);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setSkills(toCopy.skills);
            setInterviewScore(toCopy.interviewScore);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, job, phone, email, skills, interviewScore, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setJob(Job job) {
            this.job = job;
        }

        public Optional<Job> getJob() {
            return Optional.ofNullable(job);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        /**
         * Sets {@code skills} to this object's {@code skills}.
         * A defensive copy of {@code skills} is used internally.
         */
        public void setSkills(Set<Skill> skills) {
            this.skills = (skills != null) ? new HashSet<>(skills) : null;
        }

        /**
         * Returns an unmodifiable skill set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code skills} is null.
         */
        public Optional<Set<Skill>> getSkills() {
            return (skills != null) ? Optional.of(Collections.unmodifiableSet(skills)) : Optional.empty();
        }

        public void setInterviewScore(InterviewScore interviewScore) {
            this.interviewScore = interviewScore;
        }

        public Optional<InterviewScore> getInterviewScore() {
            return Optional.ofNullable(interviewScore);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(job, otherEditPersonDescriptor.job)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(skills, otherEditPersonDescriptor.skills)
                    && Objects.equals(interviewScore, otherEditPersonDescriptor.interviewScore)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("job", job)
                    .add("phone", phone)
                    .add("email", email)
                    .add("skills", skills)
                    .add("interview score", interviewScore)
                    .add("tags", tags)
                    .toString();
        }
    }
}
