package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsDeletePredicate;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String SHORT_COMMAND_WORD = "d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by their name in the displayed person list.\n"
            + "Parameters: Name (String & must be non-empty)\n"
            + "Example: " + COMMAND_WORD + " John Doe " + " " + " or " + " "
            + " Example: " + SHORT_COMMAND_WORD + " John Doe \n"
            + "To delete a person with a common name, please provide fullname or use /"
            + " to indicate the end of the name eg Jon snow/ \n";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final NameContainsKeywordsDeletePredicate predicate;

    public DeleteCommand(NameContainsKeywordsDeletePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateFilteredPersonList(predicate);
        if (model.getDisplayPersons().isEmpty()) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            throw new CommandException(Messages.MESSAGE_PERSON_NOT_FOUND);

        }
        if (model.getDisplayPersons().size() > 1) {
            model.updateFilteredPersonList(predicate);
            throw new CommandException(Messages.MESSAGE_VAGUE_DELETE);
        }
        Person personToDelete = model.getDisplayPersons().get(0);
        model.deletePerson(personToDelete);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return predicate.equals(otherDeleteCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
