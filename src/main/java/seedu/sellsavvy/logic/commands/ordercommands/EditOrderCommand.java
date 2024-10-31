package seedu.sellsavvy.logic.commands.ordercommands;

import static java.util.Objects.requireNonNull;
import static seedu.sellsavvy.logic.Messages.MESSAGE_ORDERLIST_DOES_NOT_EXIST;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_ITEM;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.sellsavvy.model.order.Date.MESSAGE_OUTDATED_WARNING;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.sellsavvy.commons.core.index.Index;
import seedu.sellsavvy.commons.util.CollectionUtil;
import seedu.sellsavvy.commons.util.ToStringBuilder;
import seedu.sellsavvy.logic.Messages;
import seedu.sellsavvy.logic.commands.Command;
import seedu.sellsavvy.logic.commands.CommandResult;
import seedu.sellsavvy.logic.commands.exceptions.CommandException;
import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.order.Date;
import seedu.sellsavvy.model.order.Item;
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.order.OrderList;
import seedu.sellsavvy.model.order.Quantity;
import seedu.sellsavvy.model.order.Status;

/**
 * Edits the details of an existing order in the address book.
 */
public class EditOrderCommand extends Command {

    public static final String COMMAND_WORD = "editOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the order identified "
            + "by the index number used in the displayed order list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: ORDER_INDEX (must be a positive integer) "
            + "[" + PREFIX_ITEM + "ITEM] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_QUANTITY + "QUANTITY] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ITEM + "LED light bulb "
            + PREFIX_DATE + "20-12-2024 ";

    public static final String MESSAGE_EDIT_ORDER_SUCCESS = "Edited Order: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ORDER_WARNING = "Note: "
            + "This customer already has a %1$s order with the same details, "
            + "verify if this is a mistake\n";

    private final Index index;
    private final EditOrderDescriptor editOrderDescriptor;

    /**
     * @param index of the order in the filtered order list to edit
     * @param editOrderDescriptor details to edit the order with
     */
    public EditOrderCommand(Index index, EditOrderDescriptor editOrderDescriptor) {
        requireNonNull(index);
        requireNonNull(editOrderDescriptor);

        this.index = index;
        this.editOrderDescriptor = new EditOrderDescriptor(editOrderDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownOrderList = model.getFilteredOrderList();

        if (lastShownOrderList == null) {
            throw new CommandException(MESSAGE_ORDERLIST_DOES_NOT_EXIST);
        }

        if (index.getZeroBased() >= lastShownOrderList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToEdit = lastShownOrderList.get(index.getZeroBased());
        Order editedOrder = createEditedOrder(orderToEdit, editOrderDescriptor);

        OrderList orderList = model.getSelectedOrderList();
        String feedbackToUser = !orderToEdit.isSameOrder(editedOrder)
                && orderList.contains(editedOrder)
                ? String.format(MESSAGE_DUPLICATE_ORDER_WARNING,
                editedOrder.getStatus().toString().toLowerCase())
                : "";
        feedbackToUser += editedOrder.hasDateElapsed()
                ? MESSAGE_OUTDATED_WARNING
                : "";

        model.setOrder(orderToEdit, editedOrder);
        return new CommandResult(feedbackToUser
                + String.format(MESSAGE_EDIT_ORDER_SUCCESS, Messages.format(editedOrder)));
    }

    /**
     * Creates and returns a {@code Order} with the details of {@code orderToEdit}
     * edited with {@code editOrderDescriptor}.
     */
    private static Order createEditedOrder(Order orderToEdit, EditOrderDescriptor editOrderDescriptor) {
        assert orderToEdit != null;

        Item updatedItem = editOrderDescriptor.getItem().orElse(orderToEdit.getItem());
        Quantity updatedQuantity = editOrderDescriptor.getQuantity().orElse(orderToEdit.getQuantity());
        Date updatedDate = editOrderDescriptor.getDate().orElse(orderToEdit.getDate());
        Status status = orderToEdit.getStatus(); // status cannot be updated

        Order orderToReturn = new Order(updatedItem, updatedQuantity, updatedDate, status);
        return orderToReturn;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditOrderCommand)) {
            return false;
        }

        EditOrderCommand otherEditOrderCommand = (EditOrderCommand) other;
        return index.equals(otherEditOrderCommand.index)
                && editOrderDescriptor.equals(otherEditOrderCommand.editOrderDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editOrderDescriptor", editOrderDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditOrderDescriptor {
        private Item item;
        private Date date;
        private Quantity quantity;

        public EditOrderDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditOrderDescriptor(EditOrderDescriptor toCopy) {
            setItem(toCopy.item);
            setDate(toCopy.date);
            setQuantity(toCopy.quantity);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(item, date, quantity);
        }

        public void setItem(Item item) {
            this.item = item;
        }

        public Optional<Item> getItem() {
            return Optional.ofNullable(item);
        }

        public void setQuantity(Quantity quantity) {
            this.quantity = quantity;
        }

        public Optional<Quantity> getQuantity() {
            return Optional.ofNullable(quantity);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }


        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditOrderDescriptor)) {
                return false;
            }

            EditOrderDescriptor otherEditOrderDescriptor = (EditOrderDescriptor) other;
            return Objects.equals(item, otherEditOrderDescriptor.item)
                    && Objects.equals(date, otherEditOrderDescriptor.date)
                    && Objects.equals(quantity, otherEditOrderDescriptor.quantity);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("item", item)
                    .add("quantity", quantity)
                    .add("date", date)
                    .toString();
        }
    }
}
