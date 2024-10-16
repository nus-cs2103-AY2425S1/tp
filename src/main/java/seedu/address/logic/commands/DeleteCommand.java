package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
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
            + ": Deletes the person specified by their name or index.\n"
            + "Parameters: [INDEX | NAME]\n"
            + "Example 1: " + COMMAND_WORD + " 1\n" // Example for index-based deletion
            + "Example 2: " + COMMAND_WORD + " alice"; // Example for name-based deletion

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Successfully deleted the contact: %1$s.";
    public static final String MESSAGE_EXACT_PERSON_NOT_FOUND = "No exact match found for the name \"%1$s\".\n"
            + "The displayed list has been filtered based on the name \"%1$s\".\n"
            + "Please use fullname or try using an index.";
    public static final String MESSAGE_MULTIPLE_PERSONS_FOUND = "Multiple contact match the name \"%1$s\".\n"
            + "The displayed list has been filtered based on the name \"%1$s\".\n"
            + "Please specify an index to delete the correct person.";
    public static final String MESSAGE_PARTIAL_PERSON_NOT_FOUND = "No contact found with an exact or "
            + "partial match for the name \"%1$s\".";

    private final String nameToDelete;
    private final Index targetIndex;

    /**
     * Constructor for name-based deletion
     * @param nameToDelete
     */
    public DeleteCommand(String nameToDelete) {
        this.nameToDelete = nameToDelete;
        this.targetIndex = null; // No index provided
    }
    /**
     * Constructor for index-based deletion
     * @param targetIndex
     */
    public DeleteCommand(Index targetIndex) {
        this.nameToDelete = null; // No name provided
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException, ParseException {
        requireNonNull(model);
        if (targetIndex != null) {
            List<Person> lastShownList = model.getFilteredPersonList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deletePerson(personToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
        }
        List<Person> fullPersonList = model.getAddressBook().getPersonList();
        List<Person> exactMatch = fullPersonList.stream()
                .filter(person -> person.getName().fullName.equalsIgnoreCase(nameToDelete))
                .toList();
        List<Person> partialMatches = fullPersonList.stream()
                .filter(person -> person.getName().fullName.toLowerCase().contains(nameToDelete.toLowerCase()))
                .toList();

        if (exactMatch.size() == 1) {
            Person personToDelete = exactMatch.get(0);
            model.deletePerson(personToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
        } else {
            if (partialMatches.isEmpty()) {
                throw new CommandException(String.format(MESSAGE_PARTIAL_PERSON_NOT_FOUND, nameToDelete));
            }
            FindCommandParser findCommandParser = new FindCommandParser();
            findCommandParser.parse(nameToDelete).execute(model);
            if (exactMatch.size() > 1) {
                throw new CommandException(String.format(MESSAGE_MULTIPLE_PERSONS_FOUND, nameToDelete));
            }
            throw new CommandException(String.format(MESSAGE_EXACT_PERSON_NOT_FOUND, nameToDelete));
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
        return (nameToDelete != null && nameToDelete.equals(otherDeleteCommand.nameToDelete))
                || (targetIndex != null && targetIndex.equals(otherDeleteCommand.targetIndex));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nameToDelete", nameToDelete)
                .toString();
    }
}
