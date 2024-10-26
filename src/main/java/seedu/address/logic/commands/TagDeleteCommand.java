package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;
import seedu.address.model.wedding.Wedding;

/**
 * Deletes tags from an existing person in the address book.
 */
public class TagDeleteCommand extends Command {

    public static final String COMMAND_WORD = "tag-delete";
    public static final String COMMAND_FUNCTION = COMMAND_WORD
            + ": Deletes the specified tag from the person identified "
            + "by their name. Also deletes them as participant from the wedding given by specified tag.";
  
    public static final String MESSAGE_USAGE = COMMAND_FUNCTION
            + "Parameters: "
            + "tag-delete n/NAME & NAME t/[TAG]\n"
            + "Example: " + COMMAND_WORD + " n/ Li Sirui "
            + "t/ Jane Lim & Tom Koh";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Removed existing Tag(s): '%1$s' from Contact: %2$s." + "\n"
            + "Contact: '%3$s' has been removed from Wedding(s): '%4$s'.";
    public static final String MESSAGE_PERSON_DOESNT_EXIST = "Contact: '%1$s' does not exist in the address book.";
    public static final String MESSAGE_TAG_DOESNT_EXIST = "Given Tag(s): '%1$s' do not exist for Contact: '%2$s'.";

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

        updatePersonInWedding(editedPerson, personToEdit, model);
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateDeleteMessage(personToEdit, editedPerson, model));
    }

    /**
     * Edits the original set of tags to remove the tags that need to be deleted
     * @param ogTags the original Set of tags of the person
     * @param deleteTags the Set of tags to be deleted from the person
     * @return the edited Set of tags that no longer include the deleted tags
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
    private String generateDeleteMessage(Person personToEdit, Person editedPerson, Model model) {
        Set<Tag> tagsInBoth = new HashSet<>(personToEdit.getTags());
        Set<Tag> tagsInNeither = new HashSet<>(tagsToDelete);
        if (!personToEdit.getTags().containsAll(tagsToDelete)) {
            tagsInBoth.retainAll(tagsToDelete);
            tagsInNeither.removeAll(tagsInBoth);
            if (tagsInBoth.isEmpty()) {
                return String.format(MESSAGE_TAG_DOESNT_EXIST, Messages.tagSetToString(tagsToDelete),
                        Messages.format(editedPerson));
            } else {
                deletePersonInWedding(editedPerson, model, tagsInBoth);
                String tagsNotExist = String.format(MESSAGE_TAG_DOESNT_EXIST + "\n",
                        Messages.tagSetToString(tagsInNeither), Messages.format(personToEdit));
                String tagsExist = String.format(MESSAGE_DELETE_TAG_SUCCESS, Messages.tagSetToString(tagsInBoth),
                        Messages.format(editedPerson), Messages.format(editedPerson),
                        Messages.tagSetToString(tagsInBoth));
                return tagsNotExist + tagsExist;
            }
        }
        deletePersonInWedding(editedPerson, model, tagsToDelete);
        return String.format(MESSAGE_DELETE_TAG_SUCCESS, Messages.tagSetToString(tagsToDelete),
                Messages.format(editedPerson), Messages.format(editedPerson),
                Messages.tagSetToString(tagsToDelete));
    }

    /**
     * Gets a list of weddings whose name matches that of the tags in the set.
     * @param model current Model containing necessary wedding address book.
     * @param tags Set of tags input by the user.
     * @return List of weddings that match the tag.
     */
    private List<Wedding> getWeddingfromTags(Model model, Set<Tag> tags) {
        List<String> predicate = tags
                .stream().map(Tag::getTagName).collect(Collectors.toList());
        List<Wedding> list = new ArrayList<>();
        for (Wedding wedding : model.getFilteredWeddingList()) {
            for (String tagName : predicate) {
                if (wedding.getWeddingName().toString().equals(tagName)) {
                    list.add(wedding);
                }
            }
        }
        return list;
    }

    /**
     * Updates the rest the list of weddings with the editedPerson.
     * @param editedPerson Person whose new tags have been added to them.
     * @param personToEdit Person who has tags currently being added to them.
     * @param model current Model containing necessary wedding address book.
     */
    private void updatePersonInWedding(Person editedPerson, Person personToEdit, Model model) {
        List<Wedding> weddingList = model.getFilteredWeddingList();

        List<Set<Person>> weddingParticipantsSet = weddingList.stream().map(Wedding::getParticipants)
                .toList();

        for (Set<Person> set : weddingParticipantsSet) {
            if (set.contains(personToEdit)) {
                set.remove(personToEdit);
                set.add(editedPerson);
            }
        }
    }

    /**
     * Deletes the person whose tag(s) are being deleted from the set of participants in the wedding that matches tag.
     * @param editedPerson Person whose specified tags have been deleted from.
     * @param model current Model containing necessary wedding address book.
     * @param editedTags Set of tags that exist as a wedding as well.
     */
    private void deletePersonInWedding(Person editedPerson, Model model, Set<Tag> editedTags) {
        List<Wedding> weddingList = getWeddingfromTags(model, editedTags);

        List<Set<Person>> weddingParticipantsSet = weddingList.stream().map(Wedding::getParticipants)
                .toList();

        for (Set<Person> set : weddingParticipantsSet) {
            set.remove(editedPerson);
        }
    }
}
