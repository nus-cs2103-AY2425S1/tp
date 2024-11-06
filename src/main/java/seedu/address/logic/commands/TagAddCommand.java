package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

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
 * Represents a command to add tags to an existing person in the address book.
 * This command also adds the person as a participant to weddings that correspond to the added tags.
 * If the wedding does not exist, the user will be prompted to create the wedding first.
 */
public class TagAddCommand extends Command {

    public static final String COMMAND_WORD = "tag-add";
    public static final String COMMAND_WORD_SHORT = "ta";
    public static final String COMMAND_FUNCTION = COMMAND_WORD_SHORT
            + ": Adds a tag to the person identified "
            + "by their name.\n"
            + "Also adds them as a participant in wedding given by specified tag.";

    public static final String MESSAGE_USAGE = COMMAND_FUNCTION
            + "\nParameters: "
            + PREFIX_NAME + "NAME " + PREFIX_TAG + "[TAG]\n"
            + "Example: " + COMMAND_WORD_SHORT + " " + PREFIX_NAME + "Li Sirui "
            + PREFIX_TAG + "Jane Lim & Tom Koh";

    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added Tag(s) '%1$s' to Contact: '%2$s'." + "\n"
            + "Contact: '%2$s' has been added to Wedding(s): '%1$s'.";
    public static final String MESSAGE_DUPLICATE_TAGS = "Contact '%1$s' already has the Tag(s) '%2$s'.";
    public static final String MESSAGE_PERSON_DOESNT_EXIST = "Contact: '%1$s' does not exist in the address book.";
    public static final String MESSAGE_WEDDING_DOESNT_EXIST = "Tag(s): '%1$s' does not exist as a Wedding yet."
            + "\n"
            + "Wedding needs to be created with Tag(s): '%2$s' using command '"
            + AddWeddingCommand.COMMAND_WORD_SHORT
            + "' first.";
    private final Name name;
    private final Set<Tag> tagsToAdd;

    /**
     * Creates a TagAddCommand to add the specified tags to the person with the given name.
     *
     * @param name      of the person in the person list to edit the tags.
     * @param tagsToAdd of the person to be updated to.
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


        model.updatePersonInWedding(personToEdit, editedPerson);
        model.setPersonInWedding(editedPerson, personToEdit);
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        if (message.isEmpty()) {
            return new CommandResult(generateSuccessMessage(personToEdit, editedPerson));
        } else {
            return new CommandResult(message + generateSuccessMessage(personToEdit, editedPerson));
        }
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
     * Generates a success message for the execution of the TagAddCommand.
     * The message varies depending on whether new tags are successfully added.
     *
     * @param personToEdit the original person before adding the tags.
     * @param editedPerson the person after adding the tags.
     * @return the success message to display to the user.
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
                        Messages.getName(editedPerson));
            } else { // if there are some duplicates
                // gets the tags that we actually want to add
                String nonDuplicateTagsExist = String.format(MESSAGE_ADD_TAG_SUCCESS + "\n",
                        Messages.tagSetToString(tagsInNeither), Messages.getName(editedPerson));
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
     * Generates message based on whether tag can be added, which depends on whether wedding exists or not.
     *
     * @param model current Model containing necessary wedding address book.
     * @param editedTags Set of tags that exist as a wedding as well.
     * @return String message stating whether tag exists as a wedding or not.
     * @throws CommandException if none of the weddings corresponding to the tags exist.
     */
    private String handleWeddingDoesntExist(Model model, Set<Tag> editedTags) throws CommandException {
        List<Wedding> weddingList = model.getWeddingFromTags(editedTags);

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
}
