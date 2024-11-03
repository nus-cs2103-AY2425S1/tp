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

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Deletes specified tags from the tag list, but only if they are not in use by any persons.
 */
public class DeleteTagCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "deletetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes existing tag(s) from the tag list (case insensitive). Each tag can contain a maximum of "
            + "50 alphanumeric characters, spaces, parentheses, and apostrophes.\n"
            + "Parameters: " + PREFIX_TAG + "TAG...\n"
            + "Example: " + COMMAND_WORD + " t/bride's side t/groom's side";

    public static final String MESSAGE_SUCCESS = "Deleted successfully.";
    public static final String MESSAGE_SUCCESS_FORCE = "Force deleted successfully.";
    public static final String MESSAGE_TAGS_IN_USE = "The following tags are in use and cannot be deleted:\n%s\n"
            + "Use 'deletetag -force' to delete tags that are in use.";
    public static final String MESSAGE_NONEXISTENT = "tag(s) have not been added before.\n";

    private final List<Tag> tags;
    private final Map<Tag, Set<Person>> deletedSet = new HashMap<>();
    private final boolean isForceDelete;

    /**
     * Constructs a DeleteTagCommand to delete the specified tags.
     *
     * @param tags The tags to be deleted.
     */
    public DeleteTagCommand(List<Tag> tags, boolean isForceDelete) {
        requireAllNonNull(tags);
        this.tags = tags;
        this.isForceDelete = isForceDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);
        StringBuilder errorMsg = new StringBuilder();
        StringBuilder successMsg = new StringBuilder();
        boolean showErrorMsg = false;
        boolean showSuccessMsg = false;

        // Proceed with force deletion logic
        if (isForceDelete) {
            for (Tag tag : tags) {
                if (!model.hasTag(tag)) {
                    errorMsg.append(tag.toString() + " ");
                    showErrorMsg = true;
                }
            }

            for (Tag tag : tags) {
                if (model.hasTag(tag)) {
                    model.deleteTags(List.of(tag)); // Remove from model tags
                    model.removeTagFromPersons(tag); // Remove from all persons
                    showSuccessMsg = true;
                }
            }

            successMsg.append(MESSAGE_SUCCESS_FORCE);
            String successMessage = successMsg.toString();

            if (showErrorMsg) {
                errorMsg.append(MESSAGE_NONEXISTENT);
                String errorMessage = errorMsg.toString();
                if (showSuccessMsg) {
                    return new CommandResult(successMessage + " " + errorMessage);
                }
                return new CommandResult(errorMessage);
            }
            return new CommandResult(successMessage);

        } else {
            // Check if any tags are in use
            Set<Tag> tagsInUse = model.getTagsInUse();
            Set<Tag> matchingTags = tagsInUse.stream().filter(tag -> tags.contains(tag)).collect(Collectors.toSet());

            if (!matchingTags.isEmpty()) {
                return new CommandResult(String.format(MESSAGE_TAGS_IN_USE, matchingTags));
            }

            for (Tag tag : tags) {
                if (!model.hasTag(tag)) {
                    errorMsg.append(tag.toString() + " ");
                    showErrorMsg = true;
                }
            }

            // Standard delete logic
            for (Tag tag : tags) {
                if (model.hasTag(tag)) {
                    model.deleteTags(List.of(tag)); // Just delete from the tag list
                    showSuccessMsg = true;
                }

            }

            successMsg.append(MESSAGE_SUCCESS);
            String successMessage = successMsg.toString();

            if (showErrorMsg) {
                errorMsg.append(MESSAGE_NONEXISTENT);
                String errorMessage = errorMsg.toString();
                if (showSuccessMsg) {
                    return new CommandResult(successMessage + " " + errorMessage);
                }
                return new CommandResult(errorMessage);
            } else {
                return new CommandResult(successMessage);
            }


        }
    }
    @Override
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

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DeleteTagCommand)) {
            return false;
        }
        DeleteTagCommand otherCommand = (DeleteTagCommand) other;
        return tags.equals(otherCommand.tags)
                && isForceDelete == otherCommand.isForceDelete;
    }
}
