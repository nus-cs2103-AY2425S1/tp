package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagCategory;


/**
 * Implements command to categorize all occurrences of specified tags.
 * Format: cattag t/TAG1 [t/TAG2 ... t/TAGn] CATEGORY
 */
public class CategorizeTagCommand extends Command {

    public static final String COMMAND_WORD = "cattag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Categorizes a tag. Changes all occurrences of the specified tag to the desired category.\n"
            + "Parameters: " + PREFIX_TAG + "TAG (at least one existing tag label) CATEGORY\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG + "CS1101S acads";

    public static final String MESSAGE_CAT_TAG_SUCCESS = "Category of tag(s) %1$s has been changed successfully.";
    public static final String MESSAGE_TAG_NOT_EXIST = "Tag not found: %1$s";
    public static final String MESSAGE_INVALID_CATEGORY = "Invalid category: %1$s";
    public static final String MESSAGE_DUPLICATE_CATEGORY = "Current category of %s is already %s";
    private final List<Tag> targetTags;
    private final TagCategory updatedCategory;

    /**
     * Constructs a command to categorize a tag.
     */
    public CategorizeTagCommand(List<Tag> targetTags, TagCategory updatedCategory) {
        requireNonNull(targetTags);
        requireNonNull(updatedCategory);
        this.targetTags = targetTags;
        this.updatedCategory = updatedCategory;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        assert model != null;
        for (Tag targetTag : targetTags) {
            if (!model.containsTag(targetTag)) {
                throw new CommandException(String.format(MESSAGE_TAG_NOT_EXIST, targetTag));
            }

            TagCategory existingCat = model.getTagCategory(targetTag);
            if (isDuplicateCategory(existingCat)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_CATEGORY, targetTag, updatedCategory));
            }
            model.setTagsCategory(targetTag, updatedCategory);
            model.refreshCampusConnect();
        }

        String formattedTags = formatTags(targetTags);
        return new CommandResult(String.format(MESSAGE_CAT_TAG_SUCCESS, formattedTags));
    }

    private boolean isDuplicateCategory(TagCategory cat) {
        return updatedCategory.equals(cat);
    }

    private String formatTags(List<Tag> tags) {
        String result = tags.toString();
        return result.substring(1, result.length() - 1);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CategorizeTagCommand)) {
            return false;
        }

        CategorizeTagCommand otherCategorizeTagCommand = (CategorizeTagCommand) other;
        return targetTags.equals(otherCategorizeTagCommand.targetTags)
                && updatedCategory.equals(otherCategorizeTagCommand.updatedCategory);
    }
}
