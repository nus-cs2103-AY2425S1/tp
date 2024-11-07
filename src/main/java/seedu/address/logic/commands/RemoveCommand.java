package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.person.Person;
import seedu.address.model.person.Worker;
import seedu.address.model.util.DeliveryAction;
import seedu.address.ui.InspectWindow;

/**
 * Removes a deliveries identified using it's displayed index from the worker.
 */
public class RemoveCommand extends Command {
    public static final String COMMAND_WORD = "remove";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the delivery identified by the index found in the worker's assigned delivery list. "
            + "The delivery will still remain in the client's list of deliveries\n"
            + "Parameter: INDEXES (must be positive integers\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_NOT_IN_INSPECT = "Remove command can only be used in the inspect window";
    public static final String MESSAGE_NOT_EMPLOYEE = "Remove command can only be used when inspecting employees";
    public static final String MESSAGE_SUCCESS = "Removed Delivery for %1$s: %2$s";

    private final List<Index> indexList;
    public RemoveCommand(List<Index> indexList) {
        this.indexList = indexList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!AddressBookParser.getInspect()) {
            throw new CommandException(MESSAGE_NOT_IN_INSPECT);
        } else if (InspectWindow.getInspectedPerson().isClient()) {
            throw new CommandException(MESSAGE_NOT_EMPLOYEE);
        }   else {
            return handleDeliveryRemoval(model);
        }
    }

    public CommandResult handleDeliveryRemoval(Model model) throws CommandException {
        requireNonNull(model);
        Person inspectedPerson = InspectWindow.getInspectedPerson();
        Worker inspectedWorker = inspectedPerson.getWorker();
        List<Delivery> lastShownList= model.getFilteredDeliveryList();
        validateIndexes(lastShownList.size(), indexList);

        List<Delivery> deliveriesToDeleteList = deleteDeliveries(inspectedWorker, lastShownList);

        return new CommandResult(String.format(
                MESSAGE_SUCCESS,
                inspectedPerson.getName(),
                Messages.formatDeliveryList(reverseList(deliveriesToDeleteList))),
                DeliveryAction.REMOVE);
    }

    /**
     * Validates the indexes in the indexList to ensure none are out of bounds or duplicates.
     *
     * @param listSize The size of the list from which items are to be deleted.
     * @param indexList The list of indexes to be validated.
     * @throws CommandException if any index is out of bounds or if duplicates are found.
     */
    private void validateIndexes(int listSize, List<Index> indexList) throws CommandException {
        List<Index> outOfBoundList = getOutOfBoundList(indexList, listSize);
        List<Index> duplicateList = getDuplicateList(indexList);
        String exceptionMessage = "";

        if (!outOfBoundList.isEmpty()) {
            exceptionMessage = String.format(Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX,
                    Messages.formatIndexList(outOfBoundList));
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
     * Deletes deliveries from the inspected person's delivery list based on the provided indexList.
     *
     * @param inspectedWorker The person whose deliveries are to be deleted.
     * @param deliveryList The list of deliveries to delete from.
     * @return A list of deliveries that were deleted.
     */
    private List<Delivery> deleteDeliveries(Worker inspectedWorker, List<Delivery> deliveryList) {
        List<Delivery> deliveryToDeleteList = new ArrayList<>();
        for (Index targetIndex : indexList) {
            Delivery deliveryToDelete = deliveryList.get(targetIndex.getZeroBased());
            deliveryToDeleteList.add(deliveryToDelete);
            inspectedWorker.removeDelivery(deliveryToDelete);
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
}
