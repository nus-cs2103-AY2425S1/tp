package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.concert.Concert;

/**
 * Adds a concert to the concert phonebook.
 */
public class AddConcertCommand extends Command {

    public static final String COMMAND_WORD = "addc";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a concert to the address book. " + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_DATE + "DATE" + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Coachella "
            + PREFIX_ADDRESS + "81800 51st Ave, Indio, Southern California, United States "
            + PREFIX_DATE + "2024-12-20 1010";

    public static final String MESSAGE_SUCCESS = "New concert added: %1$s";
    public static final String MESSAGE_DUPLICATE_CONCERT = "This concert already exists";
    private final Concert toAdd;

    /**
     * Creates an AddConcertCommand to add the specified {@code Concert}
     *
     * @param concert The concert to be added.
     */
    public AddConcertCommand(Concert concert) {
        requireNonNull(concert);
        toAdd = concert;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasConcert(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONCERT);
        }

        model.addConcert(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddConcertCommand)) {
            return false;
        }

        AddConcertCommand otherAddCommand = (AddConcertCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("toAdd", toAdd).toString();
    }
}
