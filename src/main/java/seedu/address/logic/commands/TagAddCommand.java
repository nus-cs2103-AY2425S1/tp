package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
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
import seedu.address.model.wedding.WeddingName;

/**
 * Adds a tag to an existing person in the address book.
 */
public class TagAddCommand extends Command {

    public static final String COMMAND_WORD = "tag-add";
    public static final String COMMAND_FUNCTION = COMMAND_WORD
            + ": Adds a tag to the person identified "
            + "by their name.\n"
            + "Also adds them as a participant in wedding given by specified tag.\n";

    public static final String MESSAGE_USAGE = COMMAND_FUNCTION
            + "Parameters: "
            + PREFIX_NAME + "NAME " + PREFIX_TAG + "[TAG]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "Li Sirui "
            + PREFIX_TAG + "Jane and Tom 230412";

    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added Tag(s) '%1$s' to Contact: '%2$s'." + "\n"
            + "Contact: '%3$s' has been added to Wedding(s): '%4$s'.";
    public static final String MESSAGE_DUPLICATE_TAGS = "Contact '%1$s' already has the Tag(s) '%2$s'.";
    public static final String MESSAGE_PERSON_DOESNT_EXIST = "Contact: '%1$s' does not exist in the address book.";
    public static final String MESSAGE_WEDDING_DOESNT_EXIST = "Tag(s): '%1$s' does not exist as a Wedding yet." + "\n"
            + "Wedding needs to be created with Tag(s): '%2$s' using command 'add-wedding' first.";

    private final Name name;
    private final Set<Tag> tagsToAdd;

    /**
     * @param name      of the person in the person list to edit the tags
     * @param tagsToAdd of the person to be updated to
     */
    public TagAddCommand(Name name, Set<Tag> tagsToAdd) {
        requireAllNonNull(name, tagsToAdd);

        this.name = name;
        this.tagsToAdd = tagsToAdd;
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

        String message = handleWeddingDoesntExist(model, tagsToAdd);

        Set<Tag> editedTags = handleDuplicateTags(personToEdit);

        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getJob(), editedTags);

        updatePersonInWedding(editedPerson, personToEdit, model);
        setPersonInWedding(editedPerson, personToEdit, model);
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(message + "\n" + generateSuccessMessage(personToEdit, editedPerson));
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
        return tagsToAdd.equals(e.tagsToAdd);
    }

    /**
     * Generates a command execution success message based on whether
     * the tag is successfully added.
     * {@code personToEdit}.
     */
    public String generateSuccessMessage(Person personToEdit, Person editedPerson) {
        Set<Tag> tagsInBoth = new HashSet<>(personToEdit.getTags());
        Set<Tag> tagsInNeither = new HashSet<>(tagsToAdd);
        // if all tags in og person matches the tags to add, means all tags to be added
        // are duplicates, so don't go inside the loop
        if (!personToEdit.getTags().containsAll(tagsToAdd)) {
            tagsInBoth.retainAll(tagsToAdd); // duplicates that we don't want to add
            tagsInNeither.removeAll(tagsInBoth); // new tags minus the duplicates that we want to add
            if (tagsInBoth.isEmpty() && !tagsToAdd.isEmpty()) {
                // if there are no duplicates, this is a clean addition
                return String.format(MESSAGE_ADD_TAG_SUCCESS, Messages.tagSetToString(tagsToAdd),
                        Messages.getName(editedPerson), Messages.getName(editedPerson),
                        Messages.tagSetToString(tagsToAdd));
            } else { // if there are some duplicates
                // gets the tags that we actually want to add
                String nonDuplicateTagsExist = String.format(MESSAGE_ADD_TAG_SUCCESS + "\n",
                        Messages.tagSetToString(tagsInNeither), Messages.getName(editedPerson),
                        Messages.getName(editedPerson), Messages.tagSetToString(tagsInNeither));
                // gets the duplicate tags that we dont want to add
                String duplicateTagsExist = String.format(MESSAGE_DUPLICATE_TAGS,
                        Messages.getName(editedPerson), Messages.tagSetToString(tagsInBoth));
                return nonDuplicateTagsExist + duplicateTagsExist;
            }
        }
        return String.format(MESSAGE_DUPLICATE_TAGS, Messages.getName(editedPerson),
                Messages.tagSetToString(tagsToAdd));
    }

    /**
     * Handles duplicate tags as inputted by the user.
     * Checks if any of the tags from user input matches existing tags in the
     * original Person.
     * Matching tags are added to a separate set that keeps track of duplicate tags.
     * Unique tags are added to a separate list of tags.
     *
     * @param person Person editedPerson from the execute method above.
     * @return A Set of tags that contains distinct tags from both existing tags and
     *     those inputted by the user.
     */
    private Set<Tag> handleDuplicateTags(Person person) {
        Set<Tag> ogTags = person.getTags();
        Set<Tag> mergedTags = new HashSet<>();

        for (Tag newTag : tagsToAdd) {
            if (!ogTags.contains(newTag)) {
                mergedTags.add(newTag);
            }
        }

        for (Tag tag : ogTags) {
            if (!mergedTags.contains(tag)) {
                mergedTags.add(tag);
            }
        }

        return mergedTags;
    }

    /**
     * Gets a list of weddings whose name matches that of the tags in the set.
     * @param model current Model containing necessary wedding address book.
     * @param tags Set of tags input by the user.
     * @return a List of weddings that match the tag.
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
     * Generates message based on whether tag can added, which depends on whether wedding exists or not.
     * @param model current Model containing necessary wedding address book.
     * @param editedTags Set of tags that exist as a wedding as well.
     * @return String message stating whether tag exists as a wedding or not.
     * @throws CommandException
     */
    private String handleWeddingDoesntExist(Model model, Set<Tag> editedTags) throws CommandException {
        List<Wedding> weddingList = getWeddingfromTags(model, editedTags);
        if (weddingList.isEmpty()) {
            Set<Tag> tagsDontExist = new HashSet<>(editedTags);
            editedTags.removeAll(editedTags);
            throw new CommandException(String.format(MESSAGE_WEDDING_DOESNT_EXIST,
                    Messages.tagSetToString(tagsDontExist), Messages.tagSetToString(tagsDontExist)));
        }
        if (weddingList.size() < editedTags.size()) {
            Set<Tag> weddingSet = weddingList.stream().map(Wedding::getWeddingName)
                            .map(WeddingName::toString).map(Tag::new).collect(Collectors.toSet());
            Set<Tag> tagsDontExist = new HashSet<>(editedTags);
            editedTags.retainAll(weddingSet);
            tagsDontExist.removeAll(weddingSet);
            return String.format(MESSAGE_WEDDING_DOESNT_EXIST, Messages.tagSetToString(tagsDontExist),
                    Messages.tagSetToString(tagsDontExist));
        }
        return "";
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
     * Sets the person being tagged as a participant in the wedding that matches the tag.
     * @param editedPerson Person whose tags have been added to them.
     * @param personToEdit Person who has tags currently being added to them.
     * @param model current Model containing necessary wedding address book.
     */
    private void setPersonInWedding(Person editedPerson, Person personToEdit, Model model) {
        List<Wedding> weddingList = getWeddingfromTags(model, editedPerson.getTags());

        List<Set<Person>> weddingParticipantsSet = weddingList.stream().map(Wedding::getParticipants)
                .toList();

        for (Set<Person> set : weddingParticipantsSet) {
            set.remove(personToEdit);
            set.add(editedPerson);
        }
    }
}
