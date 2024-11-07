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
import seedu.address.model.delivery.Delivery;
import seedu.address.model.person.Person;
import seedu.address.model.util.DeliveryAction;
import seedu.address.ui.InspectWindow;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the contacts identified by the indexes found in the displayed person list.\n"
            + "Parameters: INDEXES (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_USAGE_DELIVERY = COMMAND_WORD
            + ": Deletes the deliveries identified by the indexes found in the displayed delivery list.\n"
            + "Parameters: INDEXES (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 2";
    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    public static final String MESSAGE_DELETE_DELIVERY_SUCCESS = "Deleted Delivery for %1$s: %2$s";

    private final List<Index> indexList;

    public DeleteCommand(List<Index> indexList) {
        this.indexList = indexList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Sort the indexList in descending order
        indexList.sort(Comparator.comparing(Index::getZeroBased).reversed());

        if (!AddressBookParser.getInspect()) {
            return handlePersonDeletion(model);
        } else {
            return handleDeliveryDeletion(model);
        }
    }

    /**
     * Handles the deletion of persons from the model based on the provided indexList.
     *
     * @param model The model containing the filtered person list.
     * @return A CommandResult containing a success message with details of the deleted persons.
     * @throws CommandException if any index in the indexList is out of bounds or if duplicates are found.
     */
    private CommandResult handlePersonDeletion(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        validateIndexes(lastShownList.size(), indexList, false);

        List<Person> personToDeleteList = deletePersons(model, lastShownList);

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.formatPersonList(reverseList(personToDeleteList))));
    }

    /**
     * Handles the deletion of deliveries from the inspected person's delivery list based on the indexList.
     *
     * @param model The model containing the inspected person.
     * @return A CommandResult containing a success message with details of the deleted deliveries.
     * @throws CommandException if any index in the indexList is out of bounds or if duplicates are found.
     */
    private CommandResult handleDeliveryDeletion(Model model) throws CommandException {
        requireNonNull(model);
        Person inspectedPerson = InspectWindow.getInspectedPerson();
        List<Delivery> deliveryList = inspectedPerson.getUnmodifiableDeliveryList();
        validateIndexes(deliveryList.size(), indexList, true);

        List<Delivery> deliveryToDeleteList = deleteDeliveries(inspectedPerson, deliveryList, model);

        return new CommandResult(String.format(
                MESSAGE_DELETE_DELIVERY_SUCCESS,
                inspectedPerson.getName(),
                Messages.formatDeliveryList(reverseList(deliveryToDeleteList))),
                DeliveryAction.DELETE);
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
     * Deletes persons from the model based on the provided indexList.
     *
     * @param model The model containing the filtered person list.
     * @param lastShownList The list of persons to delete from.
     * @return A list of persons that were deleted.
     */
    private List<Person> deletePersons(Model model, List<Person> lastShownList) {
        List<Person> personToDeleteList = new ArrayList<>();
        for (Index targetIndex : indexList) {
            Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
            personToDeleteList.add(personToDelete);
            model.deletePerson(personToDelete);
        }
        return personToDeleteList;
    }

    /**
     * Deletes deliveries from the inspected person's delivery list based on the provided indexList.
     *
     * @param inspectedPerson The person whose deliveries are to be deleted.
     * @param deliveryList The list of deliveries to delete from.
     * @return A list of deliveries that were deleted.
     */
    private List<Delivery> deleteDeliveries(Person inspectedPerson, List<Delivery> deliveryList, Model model) {
        List<Delivery> deliveryToDeleteList = new ArrayList<>();
        for (Index targetIndex : indexList) {
            Delivery deliveryToDelete = deliveryList.get(targetIndex.getZeroBased());
            deliveryToDeleteList.add(deliveryToDelete);
            inspectedPerson.deleteDelivery(targetIndex);
        }
        return deliveryToDeleteList;
    }

    /**
     * Reverses the order of the provided list.
     *
     * @param <T> The type of the elements in the list.
     * @param list The list to be reversed.
     * @return A reversed copy of the provided list.
     */
    private <T> List<T> reverseList(List<T> list) {
        List<T> reversedList = new ArrayList<>(list);
        Collections.reverse(reversedList);
        return reversedList;
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
     * Check if the list to be deleted are exactly equal
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
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        boolean output = true;
        for (int i = 0; i < indexList.size(); i++) {
            Index targetIndex = indexList.get(i);
            Index otherIndex = otherDeleteCommand.indexList.get(i);
            output = output && targetIndex.equals(otherIndex);
        }
        return output;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", indexList != null ? indexList.toString() : "[]")
                .toString();
    }

}
