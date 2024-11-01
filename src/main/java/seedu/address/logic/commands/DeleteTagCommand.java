package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.ui.UserConfirmation;

/**
 * Adds a new predefined tag.
 */
public class DeleteTagCommand extends Command {
    public static final String COMMAND_WORD = "deletetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes existing tag(s) in tag list(case insensitive). Maximum of 50 alphanumeric characters,"
            + "spaces, parenthesis and apostrophes per tag.\n"
            + "Parameters: " + PREFIX_TAG + "TAG...\n"
            + "Example: " + COMMAND_WORD + " t/bride's side t/groom's side";

    public static final String MESSAGE_SUCCESS = "Tag(s) deleted: ";
    public static final String MESSAGE_TAGLIST_PREFIX = "Your tags: ";
    public static final String MESSAGE_NONEXISTENT = "Some tag(s) provided have not been added before.\n";
    public static final String MESSAGE_CONFIRMATION = "The following tags are tagged on some guests:\n"
            + "%s\n"
            + "Deleting the tags will remove them from the guests.\n"
            + "Are you sure you want to delete? Click 'OK' to confirm.";
    private final List<Tag> tags;

    private String tagToDelete;

    /**
     * @param tags The tag object to be added.
     */
    public DeleteTagCommand(List<Tag> tags) {
        requireAllNonNull(tags);
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);

        // Check if all tags exist in the model's tag list
        Set<Tag> existingTags = new HashSet<>(model.getTagListAsObservableList());
        for (Tag tag : tags) {
            if (!existingTags.contains(tag)) {
                throw new CommandException(MESSAGE_NONEXISTENT);
            }
        }

        // Check if tags is in use
        Set<Tag> tagsInUse = getTagsInUse(model);

        if (!tagsInUse.isEmpty()) {
            String tagsInUseString = tagsInUse.stream()
                    .map(Tag::toString)
                    .collect(Collectors.joining(", "));

            tagToDelete = tagsInUseString;
            String confirmationMessage = String.format(MESSAGE_CONFIRMATION, tagsInUseString);
            boolean isConfirmed = UserConfirmation.getConfirmation(confirmationMessage);

            if (!isConfirmed) {
                return new CommandResult("Tag deletion canceled by user.");
            }

        }

        for (Tag tag : tags) {
            removeTagFromPersons(model, tag);
        }

        String successMessage = MESSAGE_SUCCESS + tags + "\n";
        String currentTags = MESSAGE_TAGLIST_PREFIX + model.getTagList();
        return new CommandResult(successMessage + currentTags);
    }

    /**
     * Returns a set of tags that are in use by the persons in the address book.
     * @param model
     */
    private Set<Tag> getTagsInUse(Model model) {
        Set<Tag> tagsInUse = new HashSet<>();
        List<Person> persons = model.getFullPersonList();
        for (Person person : persons) {
            Set<Tag> personTags = person.getTags();
            addPersonTags(tagsInUse, personTags);
        }
        return tagsInUse;
    }

    private void addPersonTags(Set<Tag> tagsInUse, Set<Tag> personTags) {
        for (Tag tag : tags) {
            if (personTags.contains(tag)) {
                tagsInUse.add(tag);
            }
        }
    }

    /**
     * Removes the deleted {@code Tag} from all persons in the address book.
     */
    private void removeTagFromPersons(Model model, Tag tag) {
        model.getTagListAsObservableList().remove(tag);
        List<Person> persons = model.getFullPersonList();
        for (Person person : persons) {
            if (person.hasTag(tag)) {
                // Create a new Set with the tag removed
                Set<Tag> updatedTags = new HashSet<>(person.getTags());
                updatedTags.remove(tag);

                // Create an updated Person instance with the modified tags
                Person updatedPerson = new Person(person.getName(), person.getPhone(),
                        person.getEmail(), person.getRsvpStatus(), updatedTags);

                // Replace the original person with the updated person in the model
                model.setPerson(person, updatedPerson);
            }
        }
    }

    private void replacePerson(Model model, Person person, Tag tag) {
        Set<Tag> newTags = new HashSet<>(person.getTags());
        newTags.remove(tag);

        Person updatedPerson = new Person(person.getName(), person.getPhone(),
                person.getEmail(), person.getRsvpStatus(), newTags);
        model.setPerson(person, updatedPerson);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof DeleteTagCommand)) {
            return false;
        }

        DeleteTagCommand otherCommand = (DeleteTagCommand) other;
        return tags.equals(otherCommand.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tags", tags)
                .toString();
    }
}
