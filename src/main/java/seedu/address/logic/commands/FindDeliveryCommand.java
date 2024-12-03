package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER_INDEX;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliverySupplierPredicate;
import seedu.address.model.delivery.SupplierIndex;
import seedu.address.model.supplier.Supplier;

/**
 * Finds and lists all deliveries in the address book that match the specified criteria.
 */
public class FindDeliveryCommand extends FindCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_DELIVERY
            + " : Finds all deliveries based on the provided filters.\n"
            + "Parameters: "
            + PREFIX_DATETIME + " DELIVERY_DATE_TIME "
            + PREFIX_STATUS + " STATUS "
            + PREFIX_SUPPLIER_INDEX + " SUPPLIER_INDEX "
            + PREFIX_PRODUCT + " PRODUCT\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_DELIVERY + " "
            + PREFIX_DATETIME + "23-08-2024 20:21 "
            + PREFIX_STATUS + "DELIVERED "
            + PREFIX_PRODUCT + "iPhone";

    private final Optional<SupplierIndex> supplierIndex;
    private final Predicate<Delivery> predicate;

    /**
     * Creates a FindDeliveryCommand instance based on the filter predicates given by user.
     *
     * @param predicate Consists of status, date and product filters.
     * @param supplierIndex Index of supplier by which deliveries will be filtered.
     */
    public FindDeliveryCommand(Predicate<Delivery> predicate, Optional<SupplierIndex> supplierIndex) {
        requireNonNull(predicate);
        this.predicate = predicate;
        this.supplierIndex = supplierIndex;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Predicate<Delivery> finalPredicate = predicate;

        // If supplier index is provided, retrieve the supplier and filter by that supplier
        if (supplierIndex.isPresent()) {
            List<Supplier> lastShownList = model.getModifiedSupplierList();
            assert lastShownList != null;

            SupplierIndex index = supplierIndex.get();
            if (index.getZeroBased() >= lastShownList.size()) {
                return new CommandResult(MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
            }

            Supplier supplier = lastShownList.get(index.getZeroBased());
            finalPredicate = finalPredicate.and(new DeliverySupplierPredicate(supplier));
        }

        model.updateFilteredDeliveryList(finalPredicate);
        return new CommandResult(String.format(Messages.MESSAGE_DELIVERIES_FOUND_OVERVIEW,
                model.getFilteredDeliveryList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindDeliveryCommand // instanceof handles nulls
                && predicate.equals(((FindDeliveryCommand) other).predicate)
                && supplierIndex.equals(((FindDeliveryCommand) other).supplierIndex));
    }

    public Optional<SupplierIndex> getSupplierIndex() {
        return this.supplierIndex;
    }

    public Predicate<Delivery> getPredicate() {
        return this.predicate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
