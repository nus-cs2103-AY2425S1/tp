package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.ID_SPECIAL_CHARACTER;
import static seedu.address.logic.Messages.WARD_ID_SPECIAL_CHARACTER;
import static seedu.address.logic.Messages.WARD_SPECIAL_CHARACTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIAGNOSIS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WARD;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a patient to Ward Watch.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_ID + "ID "
            + PREFIX_WARD + "WARD "
            + PREFIX_DIAGNOSIS + "DIAGNOSIS "
            + PREFIX_MEDICATION + "MEDICATION "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_ID + "P12345 "
            + PREFIX_WARD + "A1 "
            + PREFIX_DIAGNOSIS + "TYPE 1 DIABETES "
            + PREFIX_MEDICATION + "METFORMIN ";

    public static final String MESSAGE_SUCCESS = "New patient added: \n\n%1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This patient already exists in the address book";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);

        if (toAdd.hasSpecialCharactersInWard() && toAdd.hasSpecialCharactersInId()) {
            return new CommandResult(String.format(WARD_ID_SPECIAL_CHARACTER
                    + MESSAGE_SUCCESS, Messages.format(toAdd)));
        } else if (toAdd.hasSpecialCharactersInWard()) {
            return new CommandResult(String.format(WARD_SPECIAL_CHARACTER
                    + MESSAGE_SUCCESS, Messages.format(toAdd)));
        } else if (toAdd.hasSpecialCharactersInId()) {
            return new CommandResult(String.format(ID_SPECIAL_CHARACTER
                    + MESSAGE_SUCCESS, Messages.format(toAdd)));
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
        }

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
