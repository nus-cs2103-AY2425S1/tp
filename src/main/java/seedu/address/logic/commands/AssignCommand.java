package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Age;
import seedu.address.model.person.Detail;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.StudyGroupTag;

/**
 * Assigns the current listed participants into study groups
 */
public class AssignCommand extends Command {

    public static final String COMMAND_WORD = "assign";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns persons in the displayed person list randomly to the distinct input study group(s).\n"
            + "Parameters: "
            + "STUDY_GROUP_1 [...]\n"
            + "Example: " + COMMAND_WORD + " P90-Placebo P90-Experimental";

    public static final String MESSAGE_SUCCESS = "Assigned participants successfully!";
    public static final String MESSAGE_DUPLICATE_STUDYGROUP = "There are duplicate study groups in your input!";
    public static final String MESSAGE_EXISTING_STUDYGROUP = "This study group has already been assigned to one "
            + " or more participant(s) in this list!";

    private final List<AssignStudyGroupTagDescriptor> possibleAssignment;

    /**
     * Creates an AssignCommand to add the list of {@code AssignStudyGroupTagDescriptor}
     */
    public AssignCommand(List<AssignStudyGroupTagDescriptor> assignStudyGroupTagDescriptors) {
        requireNonNull(assignStudyGroupTagDescriptors);
        possibleAssignment = assignStudyGroupTagDescriptors;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<StudyGroupTag> existingStudyGroups = model.getFilteredPersonList().stream()
                .flatMap(person -> person.getStudyGroupTags().stream())
                .distinct()
                .collect(Collectors.toList());

        for (AssignStudyGroupTagDescriptor d : possibleAssignment) {
            if (existingStudyGroups.contains(d.getStudyGroupTag())) {
                throw new CommandException(MESSAGE_EXISTING_STUDYGROUP);
            }
        }

        model.getFilteredPersonList().stream()
                .forEach(person -> {
                    int choice = new Random().nextInt(possibleAssignment.size());
                    model.setPerson(person,
                            createAssignedPerson(person, possibleAssignment.get(choice)));
                });

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignCommand)) {
            return false;
        }

        AssignCommand otherAssignCommand = (AssignCommand) other;
        return possibleAssignment.equals(otherAssignCommand.possibleAssignment);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("possible assignments", possibleAssignment)
                .toString();
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit} edited with
     * {@code editPersonDescriptor}.
     */
    private static Person createAssignedPerson(Person personToEdit,
            AssignStudyGroupTagDescriptor assignStudyGroupTagDescriptor) {
        assert personToEdit != null;
        assert assignStudyGroupTagDescriptor != null;

        Name name = personToEdit.getName();
        Email email = personToEdit.getEmail();
        Gender gender = personToEdit.getGender();
        Age age = personToEdit.getAge();
        Detail detail = personToEdit.getDetail();

        Set<StudyGroupTag> updatedStudyGroups = new HashSet<>();
        updatedStudyGroups.addAll(personToEdit.getStudyGroupTags());
        updatedStudyGroups.add(assignStudyGroupTagDescriptor.getStudyGroupTag());

        return new Person(name, email, gender, age, detail, updatedStudyGroups);
    }

    /**
     * Stores the possible study groups a person can be assigned to.
     */
    public static class AssignStudyGroupTagDescriptor {
        private StudyGroupTag toAssign;

        public AssignStudyGroupTagDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public AssignStudyGroupTagDescriptor(AssignStudyGroupTagDescriptor toCopy) {
            assert toCopy != null;
            setStudyGroupTag(toCopy.toAssign);
        }

        /**
         * Sets this object's {@code toAssign} to {@code studyGroupTag}.
         */
        public void setStudyGroupTag(StudyGroupTag studyGroupTag) {
            this.toAssign = studyGroupTag;
        }

        public StudyGroupTag getStudyGroupTag() {
            return toAssign;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof AssignStudyGroupTagDescriptor)) {
                return false;
            }

            AssignStudyGroupTagDescriptor otherAssignStudyGroupTagDescriptor = (AssignStudyGroupTagDescriptor) other;
            return Objects.equals(toAssign, otherAssignStudyGroupTagDescriptor.toAssign);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("study groups to assign", toAssign)
                    .toString();
        }
    }
}
