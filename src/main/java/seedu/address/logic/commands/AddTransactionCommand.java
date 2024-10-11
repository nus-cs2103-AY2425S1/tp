package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Transaction;

/**
 * Adds a transaction to an existing person in the address book.
 */
public class AddTransactionCommand extends Command {

    public static final String COMMAND_WORD = "addt";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": adds a transaction to selected person.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer)"
            + "d/" + "DESCRIPTION"
            + "amt/" + "AMOUNT"
            + "o/" + "OTHER PARTY"
            + "dt/" + "DATE\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "d/" + "buy raw materials"
            + "amt/" + "-100"
            + "o/" + "Company XYZ"
            + "dt" + "10/10/2024";

    public static final String MESSAGE_SUCCESS = "New transaction added: %1$s";

    private final Index index;
    private final String description;
    private final int amount;
    private final String otherParty;
    private final String date;

    /**
     * @param index index of person to add transactions to
     * @param description description of transaction
     * @param amount amount of money earned or spent
     * @param otherParty name of the other party involved in transaction
     * @param date date of transaction
     */
    public AddTransactionCommand(Index index, String description, int amount,
                                 String otherParty, String date) {
        requireAllNonNull(index, description, amount, otherParty, date);

        this.index = index;
        this.description = description;
        this.amount = amount;
        this.otherParty = otherParty;
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        //to be implemented
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person selected = lastShownList.get(index.getZeroBased());
        Transaction toAdd = new Transaction(description, amount, otherParty, date);
        selected.getTransactions().add(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(selected)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddTransactionCommand)) {
            return false;
        }

        AddTransactionCommand otherCommand = (AddTransactionCommand) other;
        return index.equals(otherCommand.index)
                && description.equals(otherCommand.description)
                && amount == otherCommand.amount
                && otherParty.equals(otherCommand.otherParty)
                && date.equals(otherCommand.otherParty);
    }

}
