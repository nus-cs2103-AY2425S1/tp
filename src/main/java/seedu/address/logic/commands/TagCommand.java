package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
            + "Parameters: INDEX (must be a positive integer or 'all') "
            + PREFIX_TAG + "TAG...\n"
            + "Example: " + COMMAND_WORD + " 1 t/paidFee t/groupA";

    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added tag(s): %1$s\n" + "to Person: %2$s";
    public static final String MESSAGE_ADD_TAG_TO_ALL_SUCCESS = "Added tag(s): %1$s to all contacts.";
    public static final String MESSAGE_DUPLICATE_TAG = "Duplicate tag (case-insensitive) detected!\n"
        + "The tag %1$s has either been added before or there is a duplicate in the tag input.";
    public static final String MESSAGE_INVALID_INDEX_OR_STRING = "The person index provided is invalid. Index must either be:\n"
            + "1. A positive integer within the size of the list\n"
            + "2. 'all' if you want to add the tag to all contacts in the list.";
    public static final String MESSAGE_NO_CONTACTS_TO_TAG = "There are no contacts to add tags to.\n"
            + "Enter the command help to start adding student or company contacts!";
    private final Index index;
    private final Set<Tag> tagsToAdd;
    private final boolean shouldAddToAll;


    /**
     * Constructor for adding tags to a specific person.
     * @param index Index of the person in the filtered person list to add tags to.
     * @param tagsToAdd Set of tags to be added to the person.
     */
    public TagCommand(Index index, Set<Tag> tagsToAdd) {
        requireNonNull(index);
        requireNonNull(tagsToAdd);

        this.index = index;
        this.tagsToAdd = tagsToAdd;
        this.shouldAddToAll = false;
    }

    /**
     * Constructor for adding tags to all persons in the list.
     * @param all String "all" indicating that tags should be added to all persons.
     * @param tagsToAdd Set of tags to be added to all persons.
     */
    public TagCommand(String all, Set<Tag> tagsToAdd) {
        requireNonNull(tagsToAdd);

        this.index = null;
        this.tagsToAdd = tagsToAdd;
        this.shouldAddToAll = true;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        // Checks if there are contacts in the list to add the tags to
        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_NO_CONTACTS_TO_TAG);
        }

        if (shouldAddToAll) {
            // Checks for duplicate tags across all contacts
            for (Tag tag : tagsToAdd) {
                for (Person person : lastShownList) {
                    if (personHasTag(person, tag)) {
                        throw new CommandException(String.format(MESSAGE_DUPLICATE_TAG, tag));
                    }
                }
            }

            // Adds tags to all contacts
            for (Person person : lastShownList) {
                Person updatedPerson = addTagsToPerson(person, tagsToAdd);
                model.setPerson(person, updatedPerson);
            }
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_ADD_TAG_TO_ALL_SUCCESS, tagsToAdd));
        } else {
            assert index != null;
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(MESSAGE_INVALID_INDEX_OR_STRING);
            }

            assert index.getOneBased() > 0;
            Person personToEdit = lastShownList.get(index.getZeroBased());
            Person updatedPerson = addTagsToPerson(personToEdit, tagsToAdd);

            model.setPerson(personToEdit, updatedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

            // Concatenates tags for success message
            String addedTags = tagsToAdd.stream()
                    .map(Tag::toString)
                    .collect(Collectors.joining(", "));

            return new CommandResult(String.format(MESSAGE_ADD_TAG_SUCCESS, addedTags, Messages.format(updatedPerson)));
        }
    }

    /**
     * Checks if the person already has the specified tag, ignoring case.
     * @param person The person to check.
     * @param tag The tag to check for.
     * @return True if the person has the tag, false otherwise.
     */
    private boolean personHasTag(Person person, Tag tag) {
        return person.getTags().stream()
                .map(t -> t.tagName.toLowerCase())
                .anyMatch(existingTag -> existingTag.equals(tag.tagName.toLowerCase()));
    }

    /**
     * Adds tags to a person, ensuring no duplicate tags are added.
     * @param personToEdit The person to which tags are being added.
     * @param tagsToAdd The tags to add to the person.
     * @return A new person object with updated tags.
     * @throws CommandException If duplicate tags are detected.
     */
    private static Person addTagsToPerson(Person personToEdit, Set<Tag> tagsToAdd) throws CommandException {
        Set<String> existingTagNames = personToEdit.getTags().stream()
                .map(tag -> tag.tagName.toLowerCase())
                .collect(Collectors.toSet());

        // Detect duplicates based on lowercase comparison
        for (Tag tag : tagsToAdd) {
            String lowerTagName = tag.tagName.toLowerCase();
            if (existingTagNames.contains(lowerTagName)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_TAG, tag.toString()));
            }
            existingTagNames.add(lowerTagName);
        }

        Set<Tag> updatedTags = new HashSet<>(personToEdit.getTags());
        updatedTags.addAll(tagsToAdd);

        if (personToEdit instanceof Student studentToEdit) {
            return new Student(studentToEdit.getName(), studentToEdit.getStudentId(), studentToEdit.getPhone(),
                    studentToEdit.getEmail(), studentToEdit.getAddress(), updatedTags);
        } else {
            Company companyToEdit = (Company) personToEdit;
            return new Company(companyToEdit.getName(), companyToEdit.getIndustry(), companyToEdit.getPhone(),
                    companyToEdit.getEmail(), companyToEdit.getAddress(), updatedTags);
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
        return (index == null || index.equals(otherCommand.index))
                && tagsToAdd.equals(otherCommand.tagsToAdd);
    }
}
