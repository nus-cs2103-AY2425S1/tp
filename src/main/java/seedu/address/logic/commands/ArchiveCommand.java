package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.delivery.Archive;
import seedu.address.model.delivery.Cost;
import seedu.address.model.delivery.Date;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryId;
import seedu.address.model.delivery.DeliveryList;
import seedu.address.model.delivery.Eta;
import seedu.address.model.delivery.ItemName;
import seedu.address.model.delivery.Status;
import seedu.address.model.delivery.Time;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.DeliveryAction;
import seedu.address.ui.InspectWindow;


/**
 * Archive a delivery identified using it's displayed index from the address book.
 */

public class ArchiveCommand extends Command {

    public static final String COMMAND_WORD = "archive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Archives the delivery identified by the index number used in the displayed delivery list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ARCHIVED_DELIVERY_SUCCESS = "Archived Delivery for %1$s: %2$s";

    public static final String MESSAGE_INVALID_WINDOW = "Cannot archive contact. "
            + "Navigate to inspection window to archive deliveries";

    private final List<Index> indexList;

    public ArchiveCommand(List<Index> indexList) {
        this.indexList = indexList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Sort the indexList in descending order
        indexList.sort(Comparator.comparing(Index::getZeroBased).reversed());

        if (AddressBookParser.getInspect()) {
            return handleDeliveryArchive(model);
        } else {
            throw new CommandException(MESSAGE_INVALID_WINDOW);
        }
    }

    /**
     * Handles the deletion of deliveries from the inspected person's delivery list based on the indexList.
     *
     * @param model The model containing the inspected person.
     * @return A CommandResult containing a success message with details of the deleted deliveries.
     * @throws CommandException if any index in the indexList is out of bounds or if duplicates are found.
     */
    private CommandResult handleDeliveryArchive(Model model) throws CommandException {
        requireNonNull(model);
        Person inspectedPerson = InspectWindow.getInspectedPerson();
        DeliveryList deliveryList = inspectedPerson.getDeliveryList();
        validateIndexes(inspectedPerson.getDeliveryListSize(), indexList);

        List<Delivery> deliveryToArchiveList = archiveDeliveries(inspectedPerson, deliveryList);

        return new CommandResult(String.format(
                MESSAGE_ARCHIVED_DELIVERY_SUCCESS,
                inspectedPerson.getName(),
                Messages.formatDeliveryList(deliveryToArchiveList)),
                DeliveryAction.ARCHIVE);
    }

    /**
     * Validates the indexes in the indexList to ensure none are out of bounds or duplicates.
     *
     * @param listSize The size of the list from which items are to be deleted.
     * @param indexList The list of indexes to be validated.
     * @throws CommandException if any index is out of bounds or if duplicates are found.
     */
    private void validateIndexes(int listSize, List<Index> indexList) throws CommandException {
        boolean hasDuplicate = hasDuplicates(indexList);
        for (Index targetIndex : indexList) {
            if (targetIndex.getZeroBased() >= listSize || hasDuplicate) {
                throw new CommandException(Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX);
            }
        }
    }

    /**
     * Archive deliveries from the inspected person's delivery list based on the provided indexList.
     *
     * @param inspectedPerson The person whose deliveries are to be archived.
     * @param deliveryList The list of deliveries to archive from.
     * @return A list of deliveries that were archived.
     */
    private List<Delivery> archiveDeliveries(Person inspectedPerson, DeliveryList deliveryList) {
        List<Delivery> deliveryToArchiveList = new ArrayList<>();
        List<Delivery> deliveryToAddList = new ArrayList<>();

        for (Index targetIndex : indexList) {
            Delivery deliveryToArchive = deliveryList.asUnmodifiableObservableList().get(targetIndex.getZeroBased());
            Delivery archivedDelivery = createArchivedDelivery(deliveryToArchive);

            if (!deliveryToArchive.isArchived()) {
                inspectedPerson.deleteDelivery(targetIndex);
                deliveryToAddList.add(archivedDelivery);
            }
            deliveryToArchiveList.add(archivedDelivery);
        }

        Collections.reverse(deliveryToArchiveList);
        Collections.reverse(deliveryToAddList);

        for (Delivery deliveryToAdd : deliveryToAddList) {
            inspectedPerson.addDelivery(deliveryToAdd);
        }

        return deliveryToArchiveList;
    }

    /**
     * Creates and returns a {@code Delivery} with the details of {@code toEdit}
     * edited with {@code descriptor}.
     */
    private static Delivery createArchivedDelivery(Delivery toArchive) {
        assert toArchive != null;

        Set<ItemName> items = toArchive.getItems();
        DeliveryId deliveryId = toArchive.getDeliveryId();
        Address address = toArchive.getAddress();
        Cost cost = toArchive.getCost();
        Date date = toArchive.getDate();
        Time time = toArchive.getTime();
        Eta eta = toArchive.getEta();
        Status status = toArchive.getStatus();
        Set<Tag> tags = toArchive.getTags();

        Archive updatedArchive = new Archive(true);

        return new Delivery(deliveryId, items, address, cost, date, time, eta, status, tags, updatedArchive);
    }

    /**
     * Checks if a list of indexes has any duplicates.
     *
     * @param indexList the list of indexes to check
     * @return true if the list has duplicates, false otherwise
     */
    private boolean hasDuplicates(List<Index> indexList) {
        Set<Integer> uniqueIndices = new HashSet<>();
        for (Index index : indexList) {
            if (!uniqueIndices.add(index.getZeroBased())) {
                return true; // duplicate found
            }
        }
        return false; // no duplicates found
    }

    /**
     * Check if the list to be archived are exactly equal
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ArchiveCommand)) {
            return false;
        }

        ArchiveCommand otherArchiveCommand = (ArchiveCommand) other;
        boolean isEqual = true;
        for (int i = 0; i < indexList.size(); i++) {
            Index targetIndex = indexList.get(i);
            Index otherIndex = otherArchiveCommand.indexList.get(i);
            isEqual = isEqual && targetIndex.equals(otherIndex);
        }
        return isEqual;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", indexList != null ? indexList.toString() : "[]")
                .toString();
    }
}
