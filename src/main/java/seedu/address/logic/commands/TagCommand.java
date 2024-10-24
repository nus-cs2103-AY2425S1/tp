package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.company.Company;
import seedu.address.model.person.student.Student;
import seedu.address.model.tag.Tag;

/**
 * Adds tag(s) to an existing person in the list without overwriting existing tags.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds tag(s) to the person identified "
            + "by the index number used in the displayed person list. "
            + "Parameters: INDEX (must be a positive integer) "
            + "TAG...\n"
            + "Example: " + COMMAND_WORD + " 1 t/paidFee t/groupA";

    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added Tags to Person: %1$s";
    public static final String MESSAGE_DUPLICATE_TAG = "The tag %1$s has already been added before.";

    private final Index index;
    private final Set<Tag> tagsToAdd;

    /**
     * @param index of the person in the filtered person list to add tags to
     * @param tagsToAdd the tags to be added to the person
     */
    public TagCommand(Index index, Set<Tag> tagsToAdd) {
        requireNonNull(index);
        requireNonNull(tagsToAdd);

        this.index = index;
        this.tagsToAdd = tagsToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person updatedPerson = addTagsToPerson(personToEdit, tagsToAdd);

        model.setPerson(personToEdit, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_TAG_SUCCESS, updatedPerson));
    }

    /**
     * Adds the new tags to the person's existing tags, with duplicate tag detection.
     */
    private static Person addTagsToPerson(Person personToEdit, Set<Tag> tagsToAdd) throws CommandException {
        Set<Tag> existingTags = new HashSet<>(personToEdit.getTags());

        for (Tag tag : tagsToAdd) {
            if (existingTags.contains(tag)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_TAG, tag.tagName));
            }
        }

        Set<Tag> updatedTags = new HashSet<>(existingTags);
        updatedTags.addAll(tagsToAdd);

        if (personToEdit instanceof Student) {
            Student studentToEdit = (Student) personToEdit;
            return new Student(studentToEdit.getName(), studentToEdit.getStudentID(), studentToEdit.getPhone(),
                    studentToEdit.getEmail(), studentToEdit.getAddress(), updatedTags
            );
        } else {
            Company companyToEdit = (Company) personToEdit;
            return new Company(companyToEdit.getName(), companyToEdit.getIndustry(), companyToEdit.getPhone(),
                    companyToEdit.getEmail(), companyToEdit.getAddress(), updatedTags
            );
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TagCommand)) {
            return false;
        }

        TagCommand otherCommand = (TagCommand) other;
        return index.equals(otherCommand.index)
                && tagsToAdd.equals(otherCommand.tagsToAdd);
    }
}
