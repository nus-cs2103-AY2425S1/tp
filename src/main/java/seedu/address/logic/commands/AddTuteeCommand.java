package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Tutee;

/**
 * Adds a person to the address book.
 */
public class AddTuteeCommand extends Command {

    public static final String COMMAND_WORD = "addTutee";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tutee to the address book. \n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_HOURS + "HOURS "
            + PREFIX_SUBJECT + "SUBJECT \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johndoe@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_HOURS + "6 "
            + PREFIX_SUBJECT + "Math ";


    public static final String MESSAGE_SUCCESS = "New tutee added: \n %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This tutee already exists in the address book";

    private final Tutee toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddTuteeCommand(Tutee tutee) {
        requireNonNull(tutee);
        toAdd = tutee;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTuteeCommand)) {
            return false;
        }

        AddTuteeCommand otherAddCommand = (AddTuteeCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAddTutee", toAdd)
                .toString();
    }
}
