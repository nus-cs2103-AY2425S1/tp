package seedu.sellsavvy.logic.commands.customercommands;

import static java.util.Objects.requireNonNull;
import static seedu.sellsavvy.logic.Messages.MESSAGE_SIMILAR_NAME_WARNING;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.sellsavvy.commons.util.ToStringBuilder;
import seedu.sellsavvy.logic.Messages;
import seedu.sellsavvy.logic.commands.Command;
import seedu.sellsavvy.logic.commands.CommandResult;
import seedu.sellsavvy.logic.commands.exceptions.CommandException;
import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.customer.Customer;

/**
 * Adds a customer to the address book.
 */
public class AddCustomerCommand extends Command {

    public static final String COMMAND_WORD = "addcustomer";
    public static final String COMMAND_ALIAS = "addc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a customer to the address book. \n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New customer added: %1$s";
    public static final String MESSAGE_DUPLICATE_CUSTOMER = "This customer already exists in the address book.";
    public static final String MESSAGE_SIMILAR_TAGS_WARNING = "Note: "
            + "This customer has 2 or more similar tags, "
            + "verify if this is a mistake.\n";

    private final Customer toAdd;

    /**
     * Creates an AddCustomerCommand to add the specified {@code Customer}
     */
    public AddCustomerCommand(Customer customer) {
        requireNonNull(customer);
        toAdd = customer;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasCustomer(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CUSTOMER);
        }

        String feedbackToUser = model.hasSimilarCustomer(toAdd)
                ? MESSAGE_SIMILAR_NAME_WARNING
                : "";
        feedbackToUser += toAdd.hasSimilarTags()
                ? MESSAGE_SIMILAR_TAGS_WARNING
                : "";

        model.addCustomer(toAdd);
        return new CommandResult(feedbackToUser + String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCustomerCommand)) {
            return false;
        }

        AddCustomerCommand otherAddCustomerCommand = (AddCustomerCommand) other;
        return toAdd.equals(otherAddCustomerCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
