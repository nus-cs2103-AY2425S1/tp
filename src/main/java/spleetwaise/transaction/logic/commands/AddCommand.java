package spleetwaise.transaction.logic.commands;

import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_DATE;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_PERSONINDEX;

import java.util.List;
import java.util.Set;

import spleetwaise.address.commons.core.index.Index;
import spleetwaise.address.commons.util.CollectionUtil;
import spleetwaise.address.commons.util.ToStringBuilder;
import spleetwaise.address.logic.Messages;
import spleetwaise.address.model.person.Person;
import spleetwaise.commons.logic.commands.Command;
import spleetwaise.commons.logic.commands.CommandResult;
import spleetwaise.commons.logic.commands.exceptions.CommandException;
import spleetwaise.commons.model.CommonModel;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Category;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * This class represents a command for adding transactions.
 */
public class AddCommand extends Command {

    /**
     * The word used to trigger this command in the input.
     */
    public static final String COMMAND_WORD = "addTxn";

    /**
     * The message that is displayed upon successful execution of this command.
     */
    public static final String MESSAGE_SUCCESS = "Transaction added: %s";

    public static final String MESSAGE_DUPLICATE_TXN = "Transaction already exists in the transaction book";

    /**
     * The message usage string that explains how to use this command.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add a new transaction.\n"
            + "Parameters: "
            + PREFIX_PERSONINDEX + "PERSON_INDEX "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "[" + PREFIX_DATE + "DATE] "
            + PREFIX_CATEGORY + "FOOD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PERSONINDEX + "1 "
            + PREFIX_AMOUNT + "10.00 "
            + PREFIX_DESCRIPTION + "Paid John for lunch "
            + PREFIX_DATE + "23012024 "
            + PREFIX_CATEGORY + "FOOD";

    private final Index targetIndex;
    private final Amount amount;
    private final Description description;
    private final Date date;
    private final Set<Category> categories;
    private Transaction transactionToAdd = null;

    /**
     * Creates an AddCommand to add the specified {@code Transaction}.
     *
     * @param targetIndex Index of the person
     * @param amount      Amount the person owes
     * @param description Description of the person
     * @param date        Date of transaction
     * @param categories  Transaction Categories
     */
    public AddCommand(Index targetIndex, Amount amount, Description description, Date date, Set<Category> categories) {
        CollectionUtil.requireAllNonNull(targetIndex, amount, description, date, categories);
        this.targetIndex = targetIndex;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.categories = categories;
    }

    /**
     * This method executes the add command.
     *
     * @return the result of the execution.
     */
    @Override
    public CommandResult execute() throws CommandException {
        CommonModel model = CommonModel.getInstance();

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personForTransaction = lastShownList.get(targetIndex.getZeroBased());

        transactionToAdd = new Transaction(personForTransaction, amount, description, date, categories);

        if (model.hasTransaction(transactionToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TXN);
        }

        model.addTransaction(transactionToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, transactionToAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddCommand otherAddCommand)) {
            return false;
        }

        return this.targetIndex.equals(otherAddCommand.targetIndex)
                && this.amount.equals(otherAddCommand.amount)
                && this.description.equals(otherAddCommand.description)
                && this.date.equals(otherAddCommand.date)
                && this.categories.equals(otherAddCommand.categories);
    }

    @Override
    public String toString() {
        String transactionToAddStr = String.format("Person index (#%s): %s on %s for $%s with categories: %s",
                targetIndex, description, date, amount, categories
        );
        return new ToStringBuilder(this).add("txnToAdd", transactionToAddStr).toString();
    }

}
