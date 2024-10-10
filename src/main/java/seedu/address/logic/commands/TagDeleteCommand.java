package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;

/**
 * Deletes tags from an existing person in the address book.
 */
public class TagDeleteCommand extends Command {

    public static final String COMMAND_WORD = "tag-delete";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the specified tag from the person identified "
            + "by their name. "
            + "Parameters: "
            + "tag-delete n/NAME t/[TAG]\n"
            + "Example: " + COMMAND_WORD + " n/ Li Sirui "
            + "t/ Jane and Tom 230412";

    public static final String MESSAGE_ARGUMENTS = "Name: %1$s, Tag: %2$s";
    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Removed tag from Person: %1$s";

    private final Name name;
    private final Set<Tag> tags;

    /**
     * @param name of the person in the person list to edit the tags
     * @param tags of the person to be updated to
     */
    public TagDeleteCommand(Name name, Set<Tag> tags) {
        requireAllNonNull(name, tags);

        this.name = name;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> matchingPersons = model.getFilteredPersonList().stream()
                .filter(person -> person.getName().fullName.equalsIgnoreCase(name.toString()))
                .toList();

        Person personToEdit = matchingPersons.get(0);


        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), getTagsAfterDelete(personToEdit.getTags(), tags));

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateDeleteMessage(editedPerson));
    }

    /**
     * Edits the original set of tags to remove the tags that need to be deleted
     * @param ogTags the original set of tags of the person
     * @param deleteTags the tags to be deleted from the person
     * @return the edited set of tags that no longer include the deleted tags
     */
    public Set<Tag> getTagsAfterDelete(Set<Tag> ogTags, Set<Tag> deleteTags) {
        ogTags.removeAll(deleteTags);
        return ogTags;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagDeleteCommand)) {
            return false;
        }

        TagDeleteCommand e = (TagDeleteCommand) other;
        return tags.equals(e.tags);
    }

    /**
     * Generates a command execution success message when
     * the tags are removed
     * {@code personToEdit}.
     */
    private String generateDeleteMessage(Person personToEdit) {
        String message = MESSAGE_DELETE_TAG_SUCCESS;
        return String.format(message, Messages.format(personToEdit));
    }
}
