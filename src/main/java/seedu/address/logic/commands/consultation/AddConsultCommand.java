package seedu.address.logic.commands.consultation;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.consultation.Consultation;

/**
 * Adds a student to the address book.
 */
public class AddConsultCommand extends Command {

    public static final String COMMAND_WORD = "addconsult";
    public static final CommandType COMMAND_TYPE = CommandType.ADDCONSULT;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a consultation to TAHub. "
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_TIME + "TIME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "2024-10-20 "
            + PREFIX_TIME + "14:00 ";

    public static final String MESSAGE_SUCCESS = "New consult added: %1$s";

    private final Consultation newConsult;

    /**
     * Creates an AddCommand to add the specified {@code Student}
     */
    public AddConsultCommand(Consultation newConsult) {
        requireNonNull(newConsult);
        this.newConsult = newConsult;
    }

    /**
     * Returns Command Type ADDCONSULT
     *
     * @return Command Type ADDCONSULT
     */
    @Override
    public CommandType getCommandType() {
        return COMMAND_TYPE;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.addConsult(newConsult);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(newConsult)),
                COMMAND_TYPE);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddConsultCommand)) {
            return false;
        }

        AddConsultCommand otherAddCommand = (AddConsultCommand) other;
        return newConsult.equals(otherAddCommand.newConsult);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("newConsult", newConsult)
                .toString();
    }
}
