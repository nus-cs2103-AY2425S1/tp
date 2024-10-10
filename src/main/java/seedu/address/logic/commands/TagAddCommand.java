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
 * Adds a tag to an existing person in the address book.
 */
public class TagAddCommand extends Command {

    public static final String COMMAND_WORD = "tag-add";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a tag to the person identified "
            + "by their name. "
            + "Parameters: "
            + "n/NAME t/[TAG]\n"
            + "Example: " + COMMAND_WORD + " n/ Li Sirui "
            + "Jane and Tom 230412";

    public static final String MESSAGE_ARGUMENTS = "Name: %1$s, Tag: %2$s";
    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added tag '%1$s' to contact: %2$s";
    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Removed tag '%1$s' from contact: %1$s";

    private final Name name;
    private final Set<Tag> tags;

    /**
     * @param name of the person in the person list to edit the tags
     * @param tags of the person to be updated to
     */
    public TagAddCommand(Name name, Set<Tag> tags) {
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
                personToEdit.getAddress(), personToEdit.getJob(), tags);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagAddCommand)) {
            return false;
        }

        TagAddCommand e = (TagAddCommand) other;
        return tags.equals(e.tags);
    }

    /**
     * Generates a command execution success message based on whether
     * the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !tags.isEmpty() ? MESSAGE_ADD_TAG_SUCCESS : MESSAGE_DELETE_TAG_SUCCESS;
        return String.format(message, Messages.formatForTags(personToEdit), personToEdit.getName());
    }
}
