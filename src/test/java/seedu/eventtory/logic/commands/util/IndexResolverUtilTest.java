package seedu.eventtory.logic.commands.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.eventtory.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.eventtory.testutil.TypicalIndexes.INDEX_FIRST_VENDOR;
import static seedu.eventtory.testutil.TypicalIndexes.INDEX_LAST_EVENT;
import static seedu.eventtory.testutil.TypicalIndexes.INDEX_LAST_VENDOR;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.eventtory.commons.core.index.Index;
import seedu.eventtory.logic.Messages;
import seedu.eventtory.logic.commands.exceptions.CommandException;
import seedu.eventtory.model.Model;
import seedu.eventtory.model.ModelManager;
import seedu.eventtory.model.UserPrefs;
import seedu.eventtory.model.event.Event;
import seedu.eventtory.model.vendor.Vendor;
import seedu.eventtory.testutil.TypicalEvents;
import seedu.eventtory.testutil.TypicalVendors;
import seedu.eventtory.testutil.TypicalVendorsEventsCombined;
import seedu.eventtory.ui.UiState;

public class IndexResolverUtilTest {
    private static final Index INDEX_OVERFLOW_VENDOR = Index.fromOneBased(
        INDEX_LAST_EVENT.getOneBased() + 1);
    private static final Index INDEX_OVERFLOW_EVENT = Index.fromOneBased(
        INDEX_LAST_EVENT.getOneBased() + 1);
    private Model model;
    private Vendor vendor1 = TypicalVendors.ALICE;
    private Vendor vendor2 = TypicalVendors.BENSON;
    private Vendor vendor7 = TypicalVendors.GEORGE;
    private Event event1 = TypicalEvents.ALICE;
    private Event event7 = TypicalEvents.GEORGE;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalVendorsEventsCombined.getTypicalEventTory(), new UserPrefs());
    }

    @Test
    public void testResolveVendor_mainListIndexFirst_valid() throws CommandException {
        model.setUiState(UiState.DEFAULT);

        assertEquals(vendor1, IndexResolverUtil.resolveVendor(model, INDEX_FIRST_VENDOR));
    }

    @Test
    public void testResolveVendor_mainListIndexLast_valid() throws CommandException {
        model.setUiState(UiState.DEFAULT);

        assertEquals(vendor7, IndexResolverUtil.resolveVendor(model, INDEX_LAST_VENDOR));
    }

    @Test
    public void testResolveVendor_eventDetailsMainListIndex_valid() throws CommandException {
        // View event with no associated vendors
        model.viewEvent(event7);

        assertEquals(vendor1, IndexResolverUtil.resolveVendor(model, INDEX_FIRST_VENDOR));
    }

    @Test
    public void testResolveVendor_eventDetailsIndexOverflowToAssociatedList_valid() throws CommandException {
        // View event with one associated vendor
        model.assignVendorToEvent(vendor2, event1);
        model.viewEvent(event1);

        // Overflow to associated list
        assertEquals(vendor2, IndexResolverUtil.resolveVendor(model, INDEX_LAST_VENDOR));
    }

    @Test
    public void testResolveVendor_mainListIndex_invalid() {
        model.setUiState(UiState.DEFAULT);

        CommandException exception = assertThrows(CommandException.class, () -> {
            IndexResolverUtil.resolveVendor(model, INDEX_OVERFLOW_VENDOR);
        });
        assertEquals(Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX, exception.getMessage());
    }

    @Test
    public void testResolveVendor_eventDetailsIndex_invalidOverflow() {
        // View event with one associated vendor
        model.assignVendorToEvent(vendor2, event1);
        model.viewEvent(event1);

        // Expect CommandException for index exceeding both lists
        CommandException exception = assertThrows(CommandException.class, () -> {
            IndexResolverUtil.resolveVendor(model, INDEX_OVERFLOW_VENDOR);
        });
        assertEquals(Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX, exception.getMessage());
    }

    @Test
    public void testResolveEvent_mainListIndexFirst_valid() throws CommandException {
        model.setUiState(UiState.DEFAULT);

        assertEquals(event1, IndexResolverUtil.resolveEvent(model, INDEX_FIRST_EVENT));
    }

    @Test
    public void testResolveEvent_mainListIndexLast_valid() throws CommandException {
        model.setUiState(UiState.DEFAULT);

        assertEquals(event7, IndexResolverUtil.resolveEvent(model, INDEX_LAST_EVENT));
    }

    @Test
    public void testResolveEvent_vendorDetailsMainListIndex_valid() throws CommandException {
        // View vendor with no associated events
        model.viewVendor(vendor7);

        assertEquals(event1, IndexResolverUtil.resolveEvent(model, INDEX_FIRST_EVENT));
    }

    @Test
    public void testResolveEvent_vendorDetailsIndexOverflowToAssociatedList_valid() throws CommandException {
        model.assignVendorToEvent(vendor2, event1);

        model.viewVendor(vendor2);

        // Overflow to associated list
        assertEquals(event1, IndexResolverUtil.resolveEvent(model, INDEX_LAST_EVENT));
    }

    @Test
    public void testResolveEvent_mainListIndex_invalid() {
        model.setUiState(UiState.DEFAULT);

        CommandException exception = assertThrows(CommandException.class, () -> {
            IndexResolverUtil.resolveEvent(model, INDEX_OVERFLOW_EVENT);
        });
        assertEquals(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX, exception.getMessage());
    }

    @Test
    public void testResolveEvent_vendorDetailsIndex_invalidOverflow() {
        // View vendor with one associated event
        model.assignVendorToEvent(vendor2, event1);
        model.viewVendor(vendor2);

        // Expect CommandException for index exceeding both lists
        CommandException exception = assertThrows(CommandException.class, () -> {
            IndexResolverUtil.resolveEvent(model, INDEX_OVERFLOW_EVENT);
        });
        assertEquals(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX, exception.getMessage());
    }
}
