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
 * Adds a tag to an existing person in the address book.
 */
public class TagAddCommand extends Command {

    public static final String COMMAND_WORD = "tag-add";
    public static final String COMMAND_FUNCTION = COMMAND_WORD
            + ": Adds a tag to the person identified "
            + "by their name. ";
    public static final String MESSAGE_USAGE = COMMAND_FUNCTION
            + "Parameters: "
            + PREFIX_NAME + "NAME " + PREFIX_TAG + "[TAG]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "Li Sirui "
            + PREFIX_TAG + "Jane and Tom 230412";

    public static final String MESSAGE_ARGUMENTS = "Name: %1$s, Tag: %2$s";
    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added tag(s) '%1$s' to contact: %2$s.";
    public static final String MESSAGE_ADD_TAG_FAILURE = "Tag(s) must be non-empty string(s) with"
            + " only alphanumeric characters and underscores.";
    public static final String MESSAGE_DUPLICATE_TAGS = "Contact '%1$s' already has the tag(s) '%2$s'.";
    public static final String MESSAGE_PERSON_DOESNT_EXIST = "Contact: %1$s does not exist in KnottyPlanners";

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

        Set<Tag> editedTags = handleDuplicateTags(personToEdit);

        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getJob(), editedTags);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(personToEdit, editedPerson));
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
            if (tagsInBoth.isEmpty()) {
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
     * @param person EditedPerson from the execute method above.
     * @return A set of tags that contains distinct tags from both existing tags and
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
}
