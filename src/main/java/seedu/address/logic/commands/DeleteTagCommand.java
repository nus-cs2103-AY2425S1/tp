package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.company.Company;
import seedu.address.model.person.student.Student;
import seedu.address.model.tag.Tag;

/**
 * Deletes specific tag(s) from an existing contact in the list.
 */
public class DeleteTagCommand extends Command {

    public static final String COMMAND_WORD = "deletetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes tag(s) to the person identified "
            + "by the index number used in the displayed person list. "
            + "Parameters: INDEX (must be a positive integer) "
            + "TAG...\n"
            + "Example: " + COMMAND_WORD + " 1 t/Y3 t/Engineering";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tags from Contact: %1$s";
    public static final String MESSAGE_INVALID_TAG = "The tag %1$s does not exist.";
    private final Index targetIndex;
    private final Set<Tag> tagsToDelete;

    /**
     * @param targetIndex of the person in the filtered person list to add tags to
     * @param tagsToDelete the tags to be added to the person
     */
    public DeleteTagCommand(Index targetIndex, Set<Tag> tagsToDelete) {
        requireNonNull(targetIndex);
        requireNonNull(tagsToDelete);

        this.targetIndex = targetIndex;
        this.tagsToDelete = tagsToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        assert targetIndex.getOneBased() > 0;
        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
        Person editedPerson = deleteTagsFromPerson(personToEdit, tagsToDelete);
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, editedPerson));
    }

    /**
     * Deletes specified tag(s) from a specified person
     * @param personToEdit
     * @param tagsToDelete
     * @return Person
     * @throws CommandException if the specified tag(s) does not exist for the specified person
     */
    private Person deleteTagsFromPerson(Person personToEdit, Set<Tag> tagsToDelete) throws CommandException {
        Set<Tag> currentTags = new HashSet<>(personToEdit.getTags());
        for (Tag tag : tagsToDelete) {
            if (currentTags.contains(tag)) {
                currentTags.remove(tag);
            } else {
                throw new CommandException(String.format(MESSAGE_INVALID_TAG, tag));
            }
        }

        if (personToEdit instanceof Student) {
            Student studentToEdit = (Student) personToEdit;
            return new Student(studentToEdit.getName(), studentToEdit.getStudentID(), studentToEdit.getPhone(),
                    studentToEdit.getEmail(), studentToEdit.getAddress(), currentTags);
        } else {
            Company companyToEdit = (Company) personToEdit;
            return new Company(companyToEdit.getName(), companyToEdit.getIndustry(), companyToEdit.getPhone(),
                    companyToEdit.getEmail(), companyToEdit.getAddress(), currentTags);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteTagCommand)) {
            return false;
        }

        DeleteTagCommand otherCommand = (DeleteTagCommand) other;
        return targetIndex.equals(otherCommand.targetIndex) && tagsToDelete.equals(otherCommand.tagsToDelete);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("tagsToDelete", tagsToDelete)
                .toString();
    }
}
