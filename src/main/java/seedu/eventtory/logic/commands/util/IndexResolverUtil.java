package seedu.eventtory.logic.commands.util;

import java.util.List;

import seedu.eventtory.commons.core.index.Index;
import seedu.eventtory.logic.Messages;
import seedu.eventtory.logic.commands.exceptions.CommandException;
import seedu.eventtory.model.Model;
import seedu.eventtory.model.event.Event;
import seedu.eventtory.model.vendor.Vendor;
import seedu.eventtory.ui.UiState;

/**
 * Utility class to resolve indexes to their respective objects based on the current state of the UI.
 */
public class IndexResolverUtil {

    /**
     * Resolves the index to a vendor object.
     * @param model the current model
     * @param vendorIndex the index of the vendor (one-based)
     * @return the vendor object
     * @throws CommandException if the index is invalid
     */
    public static Vendor resolveVendor(Model model, Index vendorIndex) throws CommandException {
        List<Vendor> mainVendorList = model.getFilteredVendorList();

        int index = vendorIndex.getZeroBased();

        if (model.getUiState().get() == UiState.EVENT_DETAILS) {
            Event viewedEvent = model.getViewedEvent().getValue();
            assert viewedEvent != null : "Event should not be null";

            List<Vendor> associatedVendorList = model.getAssociatedVendors(viewedEvent);

            try {
                return getFromTwoLists(index, mainVendorList, associatedVendorList);
            } catch (IndexOutOfBoundsException e) {
                throw new CommandException(Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX);
            }
        } else if (index < mainVendorList.size()) {
            return mainVendorList.get(index);
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX);
        }
    }

    /**
     * Resolves the index to an event object.
     * @param model the current model
     * @param eventIndex the index of the event (one-based)
     * @return the event object
     * @throws CommandException if the index is invalid
     */
    public static Event resolveEvent(Model model, Index eventIndex) throws CommandException {
        List<Event> mainEventList = model.getFilteredEventList();

        int index = eventIndex.getZeroBased();

        if (model.getUiState().get() == UiState.VENDOR_DETAILS) {
            Vendor viewedVendor = model.getViewedVendor().getValue();
            assert viewedVendor != null : "Vendor should not be null";

            List<Event> associatedEventList = model.getAssociatedEvents(viewedVendor);

            try {
                return getFromTwoLists(index, mainEventList, associatedEventList);
            } catch (IndexOutOfBoundsException e) {
                throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
            }
        } else if (index < mainEventList.size()) {
            return mainEventList.get(index);
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }
    }

    /**
     * Retrieve an item from two lists, where the second list is accessed if the index overflows from the first list.
     * Index are assumed to be zero-based and non-negative.
     */
    private static <T> T getFromTwoLists(int index, List<T> list1, List<T> list2) throws IndexOutOfBoundsException {
        assert index >= 0 : "Index should be non-negative";

        if (index < list1.size()) {
            return list1.get(index);
        } else {
            int overflowIndex = index - list1.size();

            if (overflowIndex < list2.size()) {
                return list2.get(overflowIndex);
            } else {
                throw new IndexOutOfBoundsException();
            }
        }
    }
}
