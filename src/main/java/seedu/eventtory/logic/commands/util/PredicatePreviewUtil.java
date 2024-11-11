package seedu.eventtory.logic.commands.util;

import javafx.collections.transformation.FilteredList;
import seedu.eventtory.model.Model;
import seedu.eventtory.model.event.Event;
import seedu.eventtory.model.event.EventContainsKeywordsPredicate;
import seedu.eventtory.model.vendor.Vendor;
import seedu.eventtory.model.vendor.VendorContainsKeywordsPredicate;

/**
 * Contains utility methods for filtering vendors and events using predicates.
 */
public class PredicatePreviewUtil {

    /**
     * Filters the vendors in the model using the given predicate.
     * This method does not change the state of the model.
     * @param model the model to filter
     * @param predicate the predicate to filter the vendors
     * @return a filtered list of vendors
     */
    public static FilteredList<Vendor> getPreviewOfFilteredVendors(Model model,
        VendorContainsKeywordsPredicate predicate) {

        FilteredList<Vendor> vendors = new FilteredList<>(model.getFilteredVendorList());
        vendors.setPredicate(predicate);
        return vendors;
    }

    /**
     * Filters the events in the model using the given predicate.
     * This method does not change the state of the model.
     * @param model the model to filter
     * @param predicate the predicate to filter the events
     * @return a filtered list of events
     */
    public static FilteredList<Event> getPreviewOfFilteredEvents(Model model,
        EventContainsKeywordsPredicate predicate) {

        FilteredList<Event> events = new FilteredList<>(model.getFilteredEventList());
        events.setPredicate(predicate);
        return events;
    }

}
