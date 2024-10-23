package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IC_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.owner.Owner;

/**
 * Adds a person to PawPatrol.
 */
public class AddOwnerCommand extends Command {

    public static final String COMMAND_WORD = "owner";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an owner to PawPatrol. "
        + "Parameters: "
        + PREFIX_IC_NUMBER + "IC_NUMBER "
        + PREFIX_NAME + "NAME "
        + PREFIX_PHONE + "PHONE "
        + PREFIX_EMAIL + "EMAIL "
        + PREFIX_ADDRESS + "ADDRESS "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "John Doe "
        + PREFIX_PHONE + "98765432 "
        + PREFIX_EMAIL + "johnd@example.com "
        + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 ";

    public static final String MESSAGE_SUCCESS = "New owner added: %1$s";
    public static final String MESSAGE_DUPLICATE_OWNER = "This owner already exists in PawPatrol";

    private final Owner toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddOwnerCommand(Owner owner) {
        requireNonNull(owner);
        toAdd = owner;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasOwner(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_OWNER);
        }

        model.addOwner(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddOwnerCommand)) {
            return false;
        }

        AddOwnerCommand otherAddCommand = (AddOwnerCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("toAdd", toAdd)
            .toString();
    }
}
