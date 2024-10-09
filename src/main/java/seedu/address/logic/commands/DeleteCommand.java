package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person whose name contains the specified keyword (case-insensitive).\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " alice";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "No person found with the specified keyword.";
    public static final String MESSAGE_MULTIPLE_PERSONS_FOUND = "Multiple persons found with the keyword. "
            + "Please refine your input.";

    private final NameContainsKeywordsPredicate targetName;

    public DeleteCommand(NameContainsKeywordsPredicate targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> fullPersonList = model.getAddressBook().getPersonList();
        List<Person> matchingPerson = fullPersonList.stream()
                .filter(targetName)
                .toList();
        if (matchingPerson.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND));
        }

        if (matchingPerson.size() > 1) {
            throw new CommandException(String.format(MESSAGE_MULTIPLE_PERSONS_FOUND));
        }
        Person personToDelete = matchingPerson.get(0);
        model.deletePerson(personToDelete);
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
        return targetName.equals(otherDeleteCommand.targetName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetName", targetName)
                .toString();
    }
}
