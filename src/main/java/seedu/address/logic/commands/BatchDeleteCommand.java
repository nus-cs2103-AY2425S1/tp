package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.PersonContainsTagsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Delete all person with specified tags from the address book.
 */
public class BatchDeleteCommand extends Command {

    public static final String COMMAND_WORD = "batch-delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes all person with specified tag(s)\n"
            + "Parameters: "
            + PREFIX_TAG + "TAG...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TAG + "sec 4";

    public static final String MESSAGE_BATCH_DELETE_EACH_PERSON_SUCCESS = "Deleted: %1$s\n";
    public static final String MESSAGE_BATCH_DELETE_NO_PERSON_WITH_TAG = "No person with Tag(s) %s is found";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final Set<Tag> tags;

    private final PersonContainsTagsPredicate predicate;

    /**
     * Initializes command to batch delete all person with tags from the address book.
     *
     * @param tags      Tags belonging to a person.
     * @param predicate Predicate to find all people with specified tags.
     */
    public BatchDeleteCommand(Set<Tag> tags, PersonContainsTagsPredicate predicate) {
        requireNonNull(tags);
        requireNonNull(predicate);
        this.tags = tags;
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("----------------Execute batch-delete----------------");
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredPersonList(predicate);
        List<Person> lastShownList = new ArrayList<>(model.getFilteredPersonList());

        if (lastShownList.isEmpty()) {
            logger.info("No Person deleted");
            return new CommandResult(String.format(MESSAGE_BATCH_DELETE_NO_PERSON_WITH_TAG, tags));
        }

        StringBuilder feedbackToUser = new StringBuilder();

        deleteListOfPerson(lastShownList, model, feedbackToUser);
        logger.info("Person(s) deleted: "
                + lastShownList.stream().map(person -> person.getName().toString()).toList());

        logger.info("----------------Execute batch-edit successful----------------");
        return new CommandResult(feedbackToUser.toString());
    }

    private void deleteListOfPerson(List<Person> personList, Model model, StringBuilder feedbackToUser) {
        for (Person person : personList) {
            feedbackToUser.append(String
                    .format(MESSAGE_BATCH_DELETE_EACH_PERSON_SUCCESS,
                            Messages.format(person))
            );
            model.deletePerson(person);
        }
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
                .add("predicate", predicate)
                .toString();
    }
}
