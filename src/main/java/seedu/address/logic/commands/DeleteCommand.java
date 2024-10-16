package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by, "
            + "the contact's name "
            + "or, the index number used in the displayed person list\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME]"
            + " or "
            + "[" + PREFIX_ID + "ID] \n"
            + "Examples: " + COMMAND_WORD + " " + PREFIX_ID + "1 \n"
            + "                "
            + COMMAND_WORD + " " + PREFIX_NAME + "John Doe";

    public static final String MESSAGE_DELETE_BY_ID = "Please delete by ID using id/";
    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_MULTIPLE_MATCHES = "Multiple persons found with the name";
    public static final String MESSAGE_NAME_NOT_FOUND = "No person with that name found.";

    private final Index targetIndex;
    private final Name targetName;

    /**
     * Creates a DeleteCommand that targets a person by its index in the list.
     *
     * @param targetIndex The index of the person to be deleted.
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetName = null;
    }
    /**
     * Creates a DeleteCommand that targets a person by its name in the list.
     *
     * @param targetName The index of the person to be deleted.
     */
    public DeleteCommand(Name targetName) {
        this.targetName = targetName;
        this.targetIndex = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (targetName == null) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deletePerson(personToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
        } else {
            List<Person> matchingPersons = model.getFilteredPersonList().stream()
                    .filter(person -> person.getName().equals(targetName))
                    .toList();

            if (matchingPersons.isEmpty()) {
                throw new CommandException(MESSAGE_NAME_NOT_FOUND);
            } else if (matchingPersons.size() == 1) {
                Person personToDelete = matchingPersons.get(0);
                model.deletePerson(personToDelete);
                return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
            } else {
                StringBuilder message = new StringBuilder(MESSAGE_MULTIPLE_MATCHES)
                        .append(" '")
                        .append(targetName)
                        .append("'. ")
                        .append(MESSAGE_DELETE_BY_ID)
                        .append('\n');
                model.updateFilteredPersonList(person -> person.getName().equals(targetName));
                return new CommandResult(message.toString());
            }
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
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
