package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.delivery.Delivery;

/**
 * Adds a delivery to the address book.
 */
public class AddDeliveryCommand extends Command {
    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_SUCCESS = "New delivery added: %1$s";
    public static final String MESSAGE_DUPLICATE_DELIVERY = "Delivery is already added!!!";
    private final Delivery deliveryToAdd;
    /**
     * Creates an AddDeliveryCommand to add the specified {@code deliveryToAdd}
     */
    public AddDeliveryCommand(Delivery deliveryToAdd) {
        requireNonNull(deliveryToAdd);
        this.deliveryToAdd = deliveryToAdd;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (false) {
            throw new CommandException(MESSAGE_DUPLICATE_DELIVERY);
        }

        //create new method in Model interface
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.deliveryToAdd));
    }

    /**
     * Returns true if deliveryToAdd object of both objects are same.
     *
     * @param other Object to be compared with
     * @return True if object is an instance of AddDeliveryCommand and both
     *          deliveries have the same identity and data fields.
     * */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddDeliveryCommand)) {
            return false;
        }

        AddDeliveryCommand otherAddDeliveryCommand = (AddDeliveryCommand) other;
        return this.deliveryToAdd.equals(otherAddDeliveryCommand.deliveryToAdd);
    }

    /**
     * Represents the String value of AddDeliveryCommand paired with the deliveryToAdd.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("deliveryToAdd", this.deliveryToAdd)
                .toString();
    }
}
