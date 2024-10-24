package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
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
    public static final String COMMAND_FUNCTION = COMMAND_WORD
            + ": Deletes the specified tag from the person identified "
            + "by their name. ";
    public static final String MESSAGE_USAGE = COMMAND_FUNCTION
            + "Parameters: "
            + "tag-delete n/NAME t/[TAG]\n"
            + "Example: " + COMMAND_WORD + " n/ Li Sirui "
            + "t/ Jane and Tom 230412";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Removed existing tags: '%1$s' from contact: %2$s";
    public static final String MESSAGE_PERSON_DOESNT_EXIST = "Contact: %1$s does not exist in KnottyPlanners";
    public static final String MESSAGE_TAG_DOESNT_EXIST = "Given tag(s): '%1$s' do not exist for contact: %2$s";

    private final Name name;
    private final Set<Tag> tagsToDelete;

    /**
     * @param name of the person in the person list to edit the tags
     * @param tagsToDelete the set of tags to be deleted from the person
     */
    public TagDeleteCommand(Name name, Set<Tag> tagsToDelete) {
        requireAllNonNull(name, tagsToDelete);

        this.name = name;
        this.tagsToDelete = tagsToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> matchingPersons = model.getFilteredPersonList().stream()
                .filter(person -> person.getName().fullName.equalsIgnoreCase(name.toString()))
                .toList();

        if (matchingPersons.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_PERSON_DOESNT_EXIST, name));
        }

        Person personToEdit = matchingPersons.get(0);


        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getJob(),
                getTagsAfterDelete(personToEdit.getTags(), tagsToDelete));

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateDeleteMessage(personToEdit, editedPerson));
    }

    /**
     * Edits the original set of tags to remove the tags that need to be deleted
     * @param ogTags the original set of tags of the person
     * @param deleteTags the tags to be deleted from the person
     * @return the edited set of tags that no longer include the deleted tags
     */
    public Set<Tag> getTagsAfterDelete(Set<Tag> ogTags, Set<Tag> deleteTags) {
        Set<Tag> copyOgTags = new HashSet<>(ogTags);
        copyOgTags.removeAll(deleteTags);
        return copyOgTags;
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
        return tagsToDelete.equals(e.tagsToDelete);
    }

    /**
     * Generates a command execution success message when
     * the tags are removed
     * {@code personToEdit}.
     */
    private String generateDeleteMessage(Person personToEdit, Person editedPerson) {
        Set<Tag> tagsInBoth = new HashSet<>(personToEdit.getTags());
        Set<Tag> tagsInNeither = new HashSet<>(tagsToDelete);
        if (!personToEdit.getTags().containsAll(tagsToDelete)) {
            tagsInBoth.retainAll(tagsToDelete);
            tagsInNeither.removeAll(tagsInBoth);
            if (tagsInBoth.isEmpty()) {
                return String.format(MESSAGE_TAG_DOESNT_EXIST, Messages.tagSetToString(tagsToDelete),
                        Messages.format(editedPerson));
            } else {
                String tagsNotExist = String.format(MESSAGE_TAG_DOESNT_EXIST + "\n",
                        Messages.tagSetToString(tagsInNeither), Messages.format(personToEdit));
                String tagsExist = String.format(MESSAGE_DELETE_TAG_SUCCESS, Messages.tagSetToString(tagsInBoth),
                        Messages.format(editedPerson));
                return tagsNotExist + tagsExist;
            }
        }
        return String.format(MESSAGE_DELETE_TAG_SUCCESS, Messages.tagSetToString(tagsToDelete),
                Messages.format(editedPerson));
    }
}
