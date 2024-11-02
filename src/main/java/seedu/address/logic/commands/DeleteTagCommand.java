package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.ui.UserConfirmation;

/**
 * Deletes specified tags from the tag list and removes them from any persons tagged with these tags.
 * Prompts the user for confirmation if the tags are used to tag existing persons.
 */
public class DeleteTagCommand extends Command implements UndoableCommand {
    public static final String COMMAND_WORD = "deletetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes existing tag(s) from the tag list (case insensitive). Each tag can contain a maximum of "
            + "50 alphanumeric characters, spaces, parentheses, and apostrophes.\n"
            + "Parameters: " + PREFIX_TAG + "TAG...\n"
            + "Example: " + COMMAND_WORD + " t/bride's side t/groom's side";

    public static final String MESSAGE_SUCCESS = "Tag(s) deleted.";
    public static final String MESSAGE_NONEXISTENT = "Some tag(s) provided have not been added before.\n";
    public static final String MESSAGE_CONFIRMATION = "The following tags are tagged on some guests:\n"
            + "%s\n"
            + "Deleting the tags will remove them from the guests.\n"
            + "Are you sure you want to delete? Click 'OK' to confirm.";

    public static final String MESSAGE_CANCELLED = "Deletion has been cancelled.";
    private final Map<Tag, Set<Person>> deletedSet = new HashMap<>();

    private final List<Tag> tags;

    /**
     * Constructs a DeleteTagCommand to delete the specified tags.
     *
     * @param tags The tags to be deleted.
     */
    public DeleteTagCommand(List<Tag> tags) {
        requireAllNonNull(tags);
        this.tags = tags;
    }

    /**
     * Removes the specified tags from all persons who are tagged with these tags.
     *
     * @param model Model that contains the list of persons to remove tags from.
     */
    private void removeTagsFromPersons(Model model) {
        for (Tag tag : tags) {
            deletedSet.put(tag, model.removeTagFromPersons(tag));
        }
    }

    /**
     * Creates and returns a CommandResult based on the success of the deletion.
     *
     * @param isSuccessful Boolean indicating if the deletion was successful.
     * @return CommandResult indicating the outcome of the deletion attempt.
     */
    private CommandResult createCommandResult(boolean isSuccessful) {
        if (!isSuccessful) {
            return new CommandResult(MESSAGE_NONEXISTENT);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Executes the DeleteTagCommand by deleting the specified tags from the tag list in the model.
     * If the tags are currently being used to tag any persons, the user is prompted for confirmation.
     *
     * @param model Model containing the tags and persons to operate on.
     * @return CommandResult indicating the result of the command.
     * @throws CommandException If the user cancels the deletion confirmation.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);

        // Get a set of tags currently being used to tag any Person.
        Set<Tag> tagsInUse = model.getTagsInUse();

        if (!tagsInUse.isEmpty()) {
            getUserConfirmation(tagsInUse);
        }

        boolean isSuccessful = model.deleteTags(tags);
        removeTagsFromPersons(model);

        return createCommandResult(isSuccessful);
    }

    /**
     * Undoes the previous DeleteTag command
     */
    public void undo(Model model) {
        requireNonNull(model);
        for (Map.Entry<Tag, Set<Person>> entry: deletedSet.entrySet()) {
            Tag deletedTag = entry.getKey();
            model.addTag(deletedTag);
            for (Person person: entry.getValue()) {
                Set<Tag> newTags = new HashSet<>(person.getTags());
                newTags.add(deletedTag);
                Person updatedPerson = new Person(person.getName(), person.getPhone(), person.getEmail(),
                        person.getRsvpStatus(), newTags);
                model.setPerson(person, updatedPerson);
            }
        }
    }

    /**
     * Prompts the user for confirmation before deleting tags that are currently in use by any persons.
     *
     * @param tagsInUse Set of tags that are in use by persons in the model.
     * @throws CommandException If the user chooses to cancel the deletion.
     */
    private void getUserConfirmation(Set<Tag> tagsInUse) throws CommandException {
        String tagsInUseString = tagsInUse.stream()
                .map(Tag::toString)
                .collect(Collectors.joining(", "));

        String confirmationMessage = String.format(MESSAGE_CONFIRMATION, tagsInUseString);
        boolean isConfirmedDeletion = UserConfirmation.getConfirmation(confirmationMessage);
        if (!isConfirmedDeletion) {
            throw new CommandException(MESSAGE_CANCELLED);
        }
    }

    /**
     * Compares this DeleteTagCommand to another object for equality.
     *
     * @param other The other object to compare with.
     * @return True if the other object is a DeleteTagCommand with the same tags.
     */
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

    /**
     * Returns a string representation of this DeleteTagCommand for debugging purposes.
     *
     * @return String representation of the command.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tags", tags)
                .toString();
    }
}
