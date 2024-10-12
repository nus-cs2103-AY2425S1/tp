package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.FindCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
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
    public static final String MESSAGE_PERSON_NOT_FOUND = "Exact match not found. Partial match listed.";
    public static final String MESSAGE_MULTIPLE_PERSONS_FOUND = "Multiple persons found with the keyword. "
            + "Please refine your input.";

    private final String nameToDelete;

    public DeleteCommand(String nameToDelete) {
        this.nameToDelete = nameToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, ParseException {
        requireNonNull(model);

        List<Person> fullPersonList = model.getAddressBook().getPersonList();
        List<Person> exactMatches = fullPersonList.stream()
                .filter(person -> person.getName().fullName.equalsIgnoreCase(nameToDelete))
                .toList();

        if (!exactMatches.isEmpty()) {
            if (exactMatches.size() > 1) {
                throw new CommandException(String.format(MESSAGE_MULTIPLE_PERSONS_FOUND));
            }
            Person personToDelete = exactMatches.get(0);
            model.deletePerson(personToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
        } else {
            FindCommandParser findCommandParser = new FindCommandParser();
            findCommandParser.parse(nameToDelete).execute(model);
            return new CommandResult(MESSAGE_PERSON_NOT_FOUND);
        }
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
        return nameToDelete.equals(otherDeleteCommand.nameToDelete);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nameToDelete", nameToDelete)
                .toString();
    }
}
