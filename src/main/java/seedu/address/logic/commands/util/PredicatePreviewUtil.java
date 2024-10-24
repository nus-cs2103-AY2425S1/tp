package seedu.address.logic.commands.util;

import javafx.collections.transformation.FilteredList;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;
import seedu.address.model.vendor.NameContainsKeywordsPredicate;
import seedu.address.model.vendor.Vendor;

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
    public static FilteredList<Vendor> getPreviewofFilteredVendors(Model model,
        NameContainsKeywordsPredicate predicate) {

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
    public static FilteredList<Event> getPreviewofFilteredEvents(Model model,
        EventNameContainsKeywordsPredicate predicate) {

        FilteredList<Event> events = new FilteredList<>(model.getFilteredEventList());
        events.setPredicate(predicate);
        return events;
    }

}
