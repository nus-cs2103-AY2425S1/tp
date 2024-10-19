package seedu.sellsavvy.testutil;

import seedu.sellsavvy.logic.commands.ordercommands.EditOrderCommand.EditOrderDescriptor;
import seedu.sellsavvy.model.order.Count;
import seedu.sellsavvy.model.order.Date;
import seedu.sellsavvy.model.order.Item;
import seedu.sellsavvy.model.order.Order;

/**
 * A utility class to help with building EditOrderDescriptor objects.
 */
public class EditOrderDescriptorBuilder {

    private EditOrderDescriptor descriptor;

    public EditOrderDescriptorBuilder() {
        this.descriptor = new EditOrderDescriptor();
    }

    /**
     * Returns an {@code EditOrderDescriptor} with fields containing {@code order}'s details
     */
    public EditOrderDescriptorBuilder(Order order) {
        descriptor = new EditOrderDescriptor();
        descriptor.setDate(order.getDate());
        descriptor.setQuantity(order.getCount());
        descriptor.setItem(order.getItem());
    }

    /**
     * Sets the {@code Item} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withItem(Item item) {
        descriptor.setItem(item);
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withQuantity(Count quantity) {
        descriptor.setQuantity(quantity);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withDate(Date date) {
        descriptor.setDate(date);
        return this;
    }

    public EditOrderDescriptor build() {
        return descriptor;
    }
}
