package seedu.address.logic.commands;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonContainsTagsPredicate;
import seedu.address.model.tag.Tag;

import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

/**
 * Delete all person with specified tags from the address book.
 */
public class BatchDeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete-batch";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Delete all person with specified tag"
            + "Parameters: "
            + PREFIX_TAG + "TAG...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TAG + "TAG...";

    public static final String MESSAGE_BATCH_DELETE_EACH_PERSON_SUCCESS = "Deleted: %1$s";

    private final Set<Tag> tags;

    private final PersonContainsTagsPredicate predicate;

    /**
     * Initializes command to batch delete all person with tags from the address book.
     *
     * @param tags Tags of a person.
     */
    public BatchDeleteCommand(Set<Tag> tags, PersonContainsTagsPredicate predicate) {
        this.tags = tags;
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        List<Person> lastShownList = model.getFilteredPersonList();


        StringBuilder feedbackToUser = new StringBuilder();
        for (Person person : lastShownList) {
            feedbackToUser.append(String
                    .format(MESSAGE_BATCH_DELETE_EACH_PERSON_SUCCESS,
                            Messages.format(person))
            );
            model.deletePerson(person);
        }

        return new CommandResult(feedbackToUser.toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BatchDeleteCommand)) {
            return false;
        }

        BatchDeleteCommand otherBatchDeleteCommand = (BatchDeleteCommand) other;
        return tags.equals(otherBatchDeleteCommand.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tags", tags)
                .toString();
    }
}
