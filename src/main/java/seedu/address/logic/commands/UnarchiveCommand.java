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
import seedu.address.model.delivery.Cost;
import seedu.address.model.delivery.Date;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryId;
import seedu.address.model.delivery.Eta;
import seedu.address.model.delivery.ItemName;
import seedu.address.model.delivery.Status;
import seedu.address.model.delivery.Time;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.DeliveryAction;
import seedu.address.ui.InspectWindow;


/**
 * Unarchive a delivery identified using it's displayed index from the address book.
 */

public class UnarchiveCommand extends Command {

    public static final String COMMAND_WORD = "unarchive";

    public static final String MESSAGE_USAGE_PERSON = COMMAND_WORD
            + ": Unarchives the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_USAGE_DELIVERY = COMMAND_WORD
            + ": Unarchives the delivery identified by the index number used in the displayed delivery list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNARCHIVED_DELIVERY_SUCCESS = "Unarchived Delivery for %1$s: %2$s";
    public static final String MESSAGE_UNARCHIVED_PERSON_SUCCESS = "Unarchived Person: %1$s";
    public static final String MESSAGE_CANNOT_UNARCHIVE_FOR_EMPLOYEE = "Cannot unarchive for employee. This currently "
            + "only works for clients";
    private final List<Index> indexList;

    public UnarchiveCommand(List<Index> indexList) {
        this.indexList = indexList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Sort the indexList in descending order
        indexList.sort(Comparator.comparing(Index::getZeroBased));

        if (AddressBookParser.getInspect()) {
            if (InspectWindow.getInspectedPerson().getRole().getValue().equals("employee")) {
                throw new CommandException(MESSAGE_CANNOT_UNARCHIVE_FOR_EMPLOYEE);
            }
            return handleDeliveryUnarchive(model);
        } else {
            return handlePersonUnarchive(model);
        }
    }

    /**
     * Handles the unarchiving of deliveries from the inspected person's delivery list based on the indexList.
     *
     * @param model The model containing the inspected person.
     * @return A CommandResult containing a success message with details of the unarchived deliveries.
     * @throws CommandException if any index in the indexList is out of bounds or if duplicates are found.
     */
    private CommandResult handleDeliveryUnarchive(Model model) throws CommandException {
        requireNonNull(model);
        Person inspectedPerson = InspectWindow.getInspectedPerson();
        List<Delivery> deliveryList = model.getFilteredDeliveryList();
        validateIndexes(inspectedPerson.getDeliveryListSize(), indexList, true);

        List<Delivery> deliveryToArchiveList = unarchiveDeliveries(inspectedPerson, deliveryList);
        Collections.reverse(deliveryToArchiveList);

        return new CommandResult(String.format(
                MESSAGE_UNARCHIVED_DELIVERY_SUCCESS,
                inspectedPerson.getName(),
                Messages.formatDeliveryList(deliveryToArchiveList)),
                DeliveryAction.UNARCHIVE);
    }

    /**
     * Handles the unarchiving of person from the inspected person's delivery list based on the indexList.
     *
     * @param model The model containing the inspected person.
     * @return A CommandResult containing a success message with details of the unarchived person.
     * @throws CommandException if any index in the indexList is out of bounds or if duplicates are found.
     */
    private CommandResult handlePersonUnarchive(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        validateIndexes(lastShownList.size(), indexList, false);

        List<Person> personToUnarchiveList = unarchivePerson(model, lastShownList);

        return new CommandResult(String.format(
                MESSAGE_UNARCHIVED_PERSON_SUCCESS,
                Messages.formatPersonList(personToUnarchiveList),
                DeliveryAction.UNARCHIVE));
    }

    /**
     * Unarchive deliveries from the inspected person's delivery list based on the provided indexList.
     *
     * @param inspectedPerson The person whose deliveries are to be unarchived.
     * @param deliveryList The list of deliveries to archive from.
     * @return A list of deliveries that were archived.
     */
    private List<Delivery> unarchiveDeliveries(Person inspectedPerson, List<Delivery> deliveryList) {
        List<Delivery> deliveryToUnarchiveList = new ArrayList<>();
        for (Index targetIndex : indexList) {
            Delivery deliveryToUnarchive = deliveryList.get(targetIndex.getZeroBased());
            Delivery unarchivedDelivery = createUnarchivedDelivery(deliveryToUnarchive);
            inspectedPerson.deleteDelivery(deliveryToUnarchive);
            inspectedPerson.unarchiveDelivery(unarchivedDelivery);
            deliveryToUnarchiveList.add(unarchivedDelivery);
        }
        Collections.reverse(deliveryToUnarchiveList);
        return deliveryToUnarchiveList;
    }

    /**
     * Unarchives persons from the model based on the provided indexList.
     *
     * @param model The model containing the filtered person list.
     * @param lastShownList The list of persons to delete from.
     * @return A list of persons that were deleted.
     */
    private List<Person> unarchivePerson(Model model, List<Person> lastShownList) {
        List<Person> personToUnarchiveList = new ArrayList<>();

        for (Index targetIndex : indexList) {
            Person personToUnarchive = lastShownList.get(targetIndex.getZeroBased());
            Person unarchivedPerson = createUnarchivedPerson(personToUnarchive);

            if (personToUnarchive.isArchived()) {
                model.deletePerson(personToUnarchive);
                model.addPerson(unarchivedPerson);
            }
            personToUnarchiveList.add(unarchivedPerson);
        }

        return personToUnarchiveList;
    }

    /**
     * Creates and returns a {@code Delivery} with the details of {@code toUnarchive}.
     */
    private static Delivery createUnarchivedDelivery(Delivery toUnarchive) {
        assert toUnarchive != null;

        Set<ItemName> items = toUnarchive.getItems();
        DeliveryId deliveryId = toUnarchive.getDeliveryId();
        Address address = toUnarchive.getAddress();
        Cost cost = toUnarchive.getCost();
        Date date = toUnarchive.getDate();
        Time time = toUnarchive.getTime();
        Eta eta = toUnarchive.getEta();
        Status status = toUnarchive.getStatus();
        Set<Tag> tags = toUnarchive.getTags();

        seedu.address.model.delivery.Archive updatedArchive = new seedu.address.model.delivery.Archive(false);

        return new Delivery(deliveryId, items, address, cost, date, time, eta, status, tags, updatedArchive);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code toUnarchive}
     * with {@code archive} set to false.
     */
    private static Person createUnarchivedPerson(Person toUnarchive) {
        assert toUnarchive != null;

        Name name = toUnarchive.getName();
        Phone phone = toUnarchive.getPhone();
        Email email = toUnarchive.getEmail();
        Role role = toUnarchive.getRole();
        Address address = toUnarchive.getAddress();
        Set<Tag> tags = toUnarchive.getTags();

        seedu.address.model.person.Archive updatedArchive = new seedu.address.model.person.Archive(false);

        return new Person(name, phone, email, role, address, tags, updatedArchive);
    }

    /**
     * Validates the indexes in the indexList to ensure none are out of bounds or duplicates.
     *
     * @param listSize The size of the list from which items are to be deleted.
     * @param indexList The list of indexes to be validated.
     * @throws CommandException if any index is out of bounds or if duplicates are found.
     */
    private void validateIndexes(int listSize, List<Index> indexList, boolean isDelivery) throws CommandException {
        List<Index> outOfBoundList = getOutOfBoundList(indexList, listSize);
        List<Index> duplicateList = getDuplicateList(indexList);
        String exceptionMessage = "";

        if (!outOfBoundList.isEmpty()) {
            if (isDelivery) {
                exceptionMessage = String.format(Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX,
                        Messages.formatIndexList(outOfBoundList));
            } else {
                exceptionMessage = String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                        Messages.formatIndexList(outOfBoundList));
            }
        }

        if (!duplicateList.isEmpty()) {
            if (!exceptionMessage.isEmpty()) {
                exceptionMessage = exceptionMessage + "\n";
            }
            exceptionMessage = exceptionMessage + String.format(Messages.MESSAGE_INVALID_DUPLICATED_INDEX,
                    Messages.formatIndexList(duplicateList));
        }

        if (!outOfBoundList.isEmpty() || !duplicateList.isEmpty()) {
            throw new CommandException(exceptionMessage);
        }
    }

    /**
     * Returns a list of indexes containing duplicates.
     *
     * @param indexList the list of indexes to check
     * @return a list containing duplicates, an empty list otherwise
     */
    private List<Index> getDuplicateList(List<Index> indexList) {
        List<Index> duplicateList = new ArrayList<>();
        Set<Integer> uniqueIndices = new HashSet<>();
        for (Index index : indexList) {
            if (!uniqueIndices.add(index.getZeroBased()) && !duplicateList.contains(index)) {
                duplicateList.add(index);
            }
        }
        duplicateList.sort(Comparator.comparingInt(Index::getOneBased));
        return duplicateList;
    }

    /**
     * Returns a list of out-of-bound indexes.
     *
     * @param indexList the list of indexes to check
     * @return list of index containing out-of-bound indexes
     */
    private List<Index> getOutOfBoundList(List<Index> indexList, int listSize) {
        List<Index> invalidIndexList = new ArrayList<>();
        for (Index index : indexList) {
            if (invalidIndexList.contains(index)) {
                continue;
            }

            if (index.getZeroBased() >= listSize || index.getZeroBased() < 0) {
                invalidIndexList.add(index);
            }
        }
        invalidIndexList.sort(Comparator.comparingInt(Index::getOneBased));
        return invalidIndexList;
    }

    /**
     * Check if the list to be unarchived are exactly equal
     *
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnarchiveCommand)) {
            return false;
        }

        UnarchiveCommand otherUnarchiveCommand = (UnarchiveCommand) other;
        boolean isEqual = true;
        for (int i = 0; i < indexList.size(); i++) {
            Index targetIndex = indexList.get(i);
            Index otherIndex = otherUnarchiveCommand.indexList.get(i);
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
