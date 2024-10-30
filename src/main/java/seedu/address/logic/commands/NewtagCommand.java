package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagList;

/**
 * Adds a new predefined tag.
 */
public class NewtagCommand extends Command {
    public static final String COMMAND_WORD = "newtag";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates new tag(s) (case insensitive). Maximum of 50 alphanumeric characters, spaces, parenthesis "
            + "and apostrophes per tag.\n"
            + "Parameters: " + PREFIX_TAG + "TAG...\n"
            + "Example: " + COMMAND_WORD + " t/bride's side t/groom's side";

    public static final String MESSAGE_SUCCESS = "New tag(s) created.\n";
    public static final String MESSAGE_DUPLICATE = "Some tag(s) already exist(s).\n"
            + "Non-duplicate tags (if any) have been added successfully.\n";
    public static final String MESSAGE_TOO_MANY_TAGS = "You have more than " + TagList.MAXIMUM_TAGLIST_SIZE
            + " tags.\nPlease remove some using 'deletetag'.\n";

    private final List<Tag> tags;


    /**
     * Constructs a NewtagCommand to add the specified {@code tags}.
     * @param tags The {@code List} of tags to be added.
     */
    public NewtagCommand(List<Tag> tags) {
        requireAllNonNull(tags);
        this.tags = tags;
    }

    /**
     * @throws CommandException if the number of defined tags in the system will exceed the maximum
     *      * allowable number if the new tags were to be added.
     */
    private void validateTagListSize(Model model) throws CommandException {
        if (!model.checkAcceptableTagListSize(tags.size())) {
            throw new CommandException(MESSAGE_TOO_MANY_TAGS);
        }
    }

    /**
     * Adds the tags to the model and checks for duplicates.
     * @param model The model to which tags will be added.
     */
    private boolean addTagsToModel(Model model) {
        boolean isSuccessful = model.addTags(tags);
        model.updateTagList();
        return isSuccessful;
    }

    /**
     * Creates a CommandResult based on the success of adding tags to the model.
     *
     * @param isSuccessful Indicates if the tags were successfully added.
     * @return The corresponding CommandResult.
     */
    private CommandResult createCommandResult(boolean isSuccessful) {
        if (!isSuccessful) {
            return new CommandResult(MESSAGE_DUPLICATE);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);
        validateTagListSize(model);
        boolean isSuccessful = addTagsToModel(model);
        return createCommandResult(isSuccessful);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof NewtagCommand)) {
            return false;
        }

        NewtagCommand otherCommand = (NewtagCommand) other;
        return tags.equals(otherCommand.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tags", tags)
                .toString();
    }
}
