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

    public static final String MESSAGE_SUCCESS = "Tag(s) deleted.";
    public static final String MESSAGE_NONEXISTENT = "Some tag(s) provided have not been added before.\n";
    public static final String MESSAGE_CONFIRMATION = "The following tags are tagged on some guests:\n"
            + "%s\n"
            + "Deleting the tags will remove them from the guests.\n"
            + "Are you sure you want to delete? Click 'OK' to confirm.";

    public static final String MESSAGE_CANCELLED = "Deletion has been cancelled.";

    private final List<Tag> tags;

    /**
     * @param tags The tag object to be added.
     */
    public DeleteTagCommand(List<Tag> tags) {
        requireAllNonNull(tags);
        this.tags = tags;
    }

    private void removeTagsFromPersons(Model model) {
        for (Tag tag : tags) {
            model.removeTagFromPersons(tag);
        }
    }

    private CommandResult createCommandResult(boolean isSuccessful) {
        if (!isSuccessful) {
            return new CommandResult(MESSAGE_NONEXISTENT);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);

        // Get a set of tags currently being used to tag any Person.
        Set<Tag> tagsInUse = model.getTagsInUse();

//        if (!tagsInUse.isEmpty()) {
//            getUserConfirmation(tagsInUse);
//        }

        boolean isSuccessful = model.deleteTags(tags);
        removeTagsFromPersons(model);

        return createCommandResult(isSuccessful);
    }

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
