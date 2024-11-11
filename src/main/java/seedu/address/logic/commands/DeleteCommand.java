package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTITY_NUMBER;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.IdentityNumber;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the patient identified by either the index number used in the displayed person list or"
            + " the Identity Number.\n"
            + "Parameters: INDEX(must be a positive integer) or i/NRIC (NRIC must be 9 characters long, "
            + "starting with 'S', 'T', 'F', or 'G', followed by 7 digits and ending with a checksum letter "
            + "(e.g., S1234567D))\n"
            + "Example: " + COMMAND_WORD + " 1 or " + COMMAND_WORD + " " + PREFIX_IDENTITY_NUMBER + "S1234567A";

    public static final String MESSAGE_INDEX_AND_IDENTITY_NUMBER = "Please provide either an index "
            + "or an identity number.";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final IdentityNumber identityNumber;
    private final Index targetIndex;

    /**
     * Creates a DeleteCommand to delete the person with the specified {@code identityNumber}.
     */
    //@@ author junyi73
    public DeleteCommand(IdentityNumber identityNumber) {
        this.identityNumber = identityNumber;
        this.targetIndex = null;
    }

    /**
     * Creates a DeleteCommand to delete the person at the specified {@code targetIndex}.
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.identityNumber = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (identityNumber == null) {
            return deleteByIndex(model);
        } else {
            return deleteByIdentityNumber(model);
        }
    }

    //@@ author junyi73
    @Override
    public void validateInput(Model model) throws CommandException {
        requireNonNull(model);
        if (identityNumber == null) {
            List<Person> lastShownList = model.getFilteredPersonList();
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        } else {
            List<Person> lastShownList = model.getPersonList();
            Person personToDelete = null;
            for (Person person : lastShownList) {
                if (person.getIdentityNumber().equals(identityNumber)) {
                    personToDelete = person;
                    break;
                }
            }

            if (personToDelete == null) {
                throw new CommandException(String.format(Messages.MESSAGE_PERSON_NOT_FOUND, identityNumber));
            }
        }
    }

    /**
     * Deletes the person by identity number.
     *
     * @param model The model to execute the command on.
     * @return The result of the command.
     * @throws CommandException If the person is not found.
     */
    //@@author junyi73
    private CommandResult deleteByIdentityNumber(Model model) throws CommandException {
        List<Person> lastShownList = model.getPersonList();
        Person personToDelete = null;

        for (Person person : lastShownList) {
            if (person.getIdentityNumber().equals(identityNumber)) {
                personToDelete = person;
                break;
            }
        }

        if (personToDelete == null) {
            throw new CommandException(Messages.MESSAGE_PERSON_NOT_FOUND);
        }

        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    /**
     * Deletes the person by index.
     *
     * @param model The model to execute the command on.
     * @return The result of the command.
     * @throws CommandException If the index is invalid.
     */
    private CommandResult deleteByIndex(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
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
        return identityNumber.equals(otherDeleteCommand.identityNumber);
    }

    @Override
    public String toString() {
        if (identityNumber != null) {
            return "Delete person with NRIC: " + identityNumber;
        } else {
            return "Delete person with Index: " + targetIndex.getOneBased();
        }
    }
}
