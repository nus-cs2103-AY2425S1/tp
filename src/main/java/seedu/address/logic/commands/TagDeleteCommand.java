package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
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
 * Represents a command to delete specified tags from an existing person in the address book.
 * This command also removes the person as a participant from weddings corresponding to the deleted tags.
 */
public class TagDeleteCommand extends Command {

    public static final String COMMAND_WORD = "tag-del";
    public static final String COMMAND_WORD_SHORT = "td";
    public static final String COMMAND_FUNCTION = COMMAND_WORD + " OR " + COMMAND_WORD_SHORT
            + ": Deletes the specified tag from the person identified "
            + "by their name.\n"
            + "Also deletes them as a participant from the wedding given by the specified tag.";

    public static final String MESSAGE_USAGE = COMMAND_FUNCTION
            + "\nParameters: "
            + PREFIX_NAME + "NAME " + PREFIX_TAG + "[TAG]\n"
            + "Example: " + COMMAND_WORD_SHORT + " " + PREFIX_NAME + "Li Sirui "
            + PREFIX_TAG + "Jane Lim & Tom Koh";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Removed existing Tag(s): '%1$s' from Contact: '%2$s'."
            + "\n" + "Contact: '%2$s' has been removed from Wedding(s): '%1$s'.";
    public static final String MESSAGE_PERSON_DOESNT_EXIST = "Contact: '%1$s' does not exist in the address book.";
    public static final String MESSAGE_TAG_DOESNT_EXIST = "Given Tag(s): '%1$s' do not exist for Contact: '%2$s'.";

    private final Name name;
    private final Set<Tag> tagsToDelete;

    /**
     * Creates a TagDeleteCommand to delete the specified tags from the person with the input name.
     *
     * @param name         of the person in the person list to edit the tags.
     * @param tagsToDelete the set of tags to be deleted from the person.
     */
    public TagDeleteCommand(Name name, Set<Tag> tagsToDelete) {
        requireAllNonNull(name, tagsToDelete);

        this.name = name;
        this.tagsToDelete = tagsToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

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

        model.updatePersonInWedding(personToEdit, editedPerson);
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateDeleteMessage(personToEdit, editedPerson, model));
    }

    /**
     * Edits the original set of tags to remove the tags that need to be deleted.
     *
     * @param ogTags     the original {@code Set} of tags of the person.
     * @param deleteTags the {@code Set} of tags to be deleted from the person.
     * @return the edited {@code Set} of tags that no longer include the deleted tags.
     */
    public static Set<Tag> getTagsAfterDelete(Set<Tag> ogTags, Set<Tag> deleteTags) {
        assert deleteTags != null;
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
     * Generates a message after deleting tags from a person.
     * The message varies depending on whether the tags existed and were successfully deleted.
     *
     * @param personToEdit the original {@code Person} before deletion.
     * @param editedPerson the {@code Person} after tags have been deleted.
     * @param model        the {@code Model} in which the command operates.
     * @return the result message {@code String} to display to the user.
     */
    private String generateDeleteMessage(Person personToEdit, Person editedPerson, Model model) {
        Set<Tag> tagsInBoth = new HashSet<>(personToEdit.getTags());
        Set<Tag> tagsInNeither = new HashSet<>(tagsToDelete);

        assert tagsInBoth != null;
        assert tagsInNeither != null;

        if (!personToEdit.getTags().containsAll(tagsToDelete)) {
            tagsInBoth.retainAll(tagsToDelete);
            tagsInNeither.removeAll(tagsInBoth);

            if (tagsInBoth.isEmpty()) {
                return String.format(MESSAGE_TAG_DOESNT_EXIST, Messages.tagSetToString(tagsToDelete),
                        Messages.getName(editedPerson));
            } else {
                model.deletePersonInWedding(editedPerson, tagsInBoth);

                String tagsNotExist = String.format(MESSAGE_TAG_DOESNT_EXIST + "\n",
                        Messages.tagSetToString(tagsInNeither), Messages.getName(personToEdit));

                String tagsExist = String.format(MESSAGE_DELETE_TAG_SUCCESS, Messages.tagSetToString(tagsInBoth),
                        Messages.getName(editedPerson));

                return tagsNotExist + tagsExist;
            }
        }

        model.deletePersonInWedding(editedPerson, tagsToDelete);
        return String.format(MESSAGE_DELETE_TAG_SUCCESS, Messages.tagSetToString(tagsToDelete),
                Messages.getName(editedPerson));
    }
}
