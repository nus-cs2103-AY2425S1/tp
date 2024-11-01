package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * Deletes specified tags from the tag list, but only if they are not in use by any persons.
 */
public class DeleteTagCommand extends Command {
    public static final String COMMAND_WORD = "deletetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes existing tag(s) from the tag list (case insensitive). Each tag can contain a maximum of "
            + "50 alphanumeric characters, spaces, parentheses, and apostrophes.\n"
            + "Parameters: " + PREFIX_TAG + "TAG...\n"
            + "Example: " + COMMAND_WORD + " t/bride's side t/groom's side";

    public static final String MESSAGE_SUCCESS = "Tag(s) deleted successfully.";
    public static final String MESSAGE_SUCCESS_FORCE = "Tag(s) force deleted successfully.";
    public static final String MESSAGE_TAGS_IN_USE = "The following tags are in use and cannot be deleted:\n%s\n"
            + "Use 'deletetag -force' to delete tags that are in use.";
    public static final String MESSAGE_NONEXISTENT = "Some tag(s) provided have not been added before.\n";

    private final List<Tag> tags;
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

        for (Tag tag : tags) {
            if (!model.hasTag(tag)) {
                return new CommandResult(MESSAGE_NONEXISTENT);
            }
        }

        // Proceed with force deletion logic
        if (isForceDelete) {
            for (Tag tag : tags) {
                model.deleteTags(List.of(tag)); // Remove from model tags
                model.removeTagFromPersons(tag); // Remove from all persons
            }
            return new CommandResult(MESSAGE_SUCCESS_FORCE);
        } else {
            // Check if any tags are in use
            Set<Tag> tagsInUse = model.getTagsInUse();
            Set<Tag> matchingTags = tagsInUse.stream().filter(tag -> tags.contains(tag)).collect(Collectors.toSet());

            if (!matchingTags.isEmpty()) {
                return new CommandResult(String.format(MESSAGE_TAGS_IN_USE, tagsInUse));
            }


            // Standard delete logic
            for (Tag tag : tags) {
                model.deleteTags(List.of(tag)); // Just delete from the tag list
            }
            return new CommandResult(MESSAGE_SUCCESS);
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
