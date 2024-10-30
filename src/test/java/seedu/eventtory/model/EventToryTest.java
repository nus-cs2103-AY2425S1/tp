package seedu.eventtory.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eventtory.logic.commands.CommandTestUtil.VALID_DATE_BIRTHDAY;
import static seedu.eventtory.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.eventtory.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.eventtory.testutil.Assert.assertThrows;
import static seedu.eventtory.testutil.TypicalEvents.BIRTHDAY;
import static seedu.eventtory.testutil.TypicalEvents.WEDDING;
import static seedu.eventtory.testutil.TypicalVendors.ALICE;
import static seedu.eventtory.testutil.TypicalVendors.BOB;
import static seedu.eventtory.testutil.TypicalVendors.getTypicalEventTory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.eventtory.model.association.Association;
import seedu.eventtory.model.commons.exceptions.AssociationDeleteException;
import seedu.eventtory.model.event.Event;
import seedu.eventtory.model.event.exceptions.DuplicateEventException;
import seedu.eventtory.model.vendor.Vendor;
import seedu.eventtory.model.vendor.exceptions.DuplicateVendorException;
import seedu.eventtory.testutil.EventBuilder;
import seedu.eventtory.testutil.VendorBuilder;

public class EventToryTest {

    private final EventTory eventTory = new EventTory();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), eventTory.getVendorList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eventTory.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyEventTory_replacesData() {
        EventTory newData = getTypicalEventTory();
        eventTory.resetData(newData);
        assertEquals(newData, eventTory);
    }

    @Test
    public void resetData_withDuplicateVendors_throwsDuplicateVendorException() {
        // Two vendors with the same identity fields
        Vendor editedAlice = new VendorBuilder(ALICE).withDescription(VALID_DESCRIPTION_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Vendor> newVendors = Arrays.asList(ALICE, editedAlice);
        EventToryStub newData = new EventToryStub(newVendors);

        assertThrows(DuplicateVendorException.class, () -> eventTory.resetData(newData));
    }

    @Test
    public void hasVendor_nullVendor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eventTory.hasVendor(null));
    }

    @Test
    public void hasVendor_vendorNotInEventTory_returnsFalse() {
        assertFalse(eventTory.hasVendor(ALICE));
    }

    @Test
    public void hasVendor_vendorInEventTory_returnsTrue() {
        eventTory.addVendor(ALICE);
        assertTrue(eventTory.hasVendor(ALICE));
    }

    @Test
    public void hasVendor_vendorWithSameIdentityFieldsInEventTory_returnsTrue() {
        eventTory.addVendor(ALICE);
        Vendor editedAlice = new VendorBuilder(ALICE).withDescription(VALID_DESCRIPTION_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(eventTory.hasVendor(editedAlice));
    }

    @Test
    public void hasEvent_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eventTory.hasEvent(null));
    }

    @Test
    public void hasEvent_eventNotInEventTory_returnsFalse() {
        assertFalse(eventTory.hasEvent(WEDDING));
    }

    @Test
    public void hasEvent_eventInEventTory_returnsTrue() {
        eventTory.addEvent(WEDDING);
        assertTrue(eventTory.hasEvent(WEDDING));
    }

    @Test
    public void hasEvent_eventWithSameIdentityFieldsInEventTory_returnsTrue() {
        Event similarWedding = new EventBuilder(WEDDING).withDate(VALID_DATE_BIRTHDAY).build();
        eventTory.addEvent(WEDDING);
        assertTrue(eventTory.hasEvent(similarWedding));
    }

    // might be redundant has this is already tested in UniqueEventListTest.java
    @Test
    public void addEvent_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eventTory.addEvent(null));
    }

    // might be redundant has this is already tested in UniqueEventListTest.java
    @Test
    public void addEvent_eventInEventTory_throwsDuplicateEventException() {
        eventTory.addEvent(WEDDING);
        assertThrows(DuplicateEventException.class, () -> eventTory.addEvent(WEDDING));
    }

    // might be redundant has this is already tested in UniqueEventListTest.java
    @Test
    public void addEvent_eventWithSameIdentityFieldsInEventTory_throwsDuplicateEventException() {
        Event similarWedding = new EventBuilder(WEDDING).withDate(VALID_DATE_BIRTHDAY).build();
        eventTory.addEvent(WEDDING);
        assertThrows(DuplicateEventException.class, () -> eventTory.addEvent(similarWedding));
    }

    @Test
    public void getVendorList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> eventTory.getVendorList().remove(0));
    }

    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> eventTory.getEventList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = EventTory.class.getCanonicalName()
                + "{vendors=" + eventTory.getVendorList() + ", "
                + "events=" + eventTory.getEventList() + ", "
                + "associations=" + eventTory.getAssociationList()
                + "}";
        assertEquals(expected, eventTory.toString());
    }

    @Test
    public void removeVendor_vendorNotAssociated_success() {
        eventTory.addVendor(ALICE);
        eventTory.removeVendor(ALICE);
        assertFalse(eventTory.hasVendor(ALICE));
    }

    @Test
    public void removeVendor_vendorIsAssociatedWithEvent_throwsAssociationDeleteException() {
        eventTory.addVendor(ALICE);
        eventTory.addEvent(WEDDING);
        eventTory.assignVendorToEvent(ALICE, WEDDING);

        assertThrows(AssociationDeleteException.class, () -> eventTory.removeVendor(ALICE));
    }

    @Test
    public void removeEvent_eventNotAssociated_success() {
        eventTory.addEvent(WEDDING);
        eventTory.removeEvent(WEDDING);
        assertFalse(eventTory.hasEvent(WEDDING));
    }

    @Test
    public void removeEvent_eventIsAssociatedWithVendor_throwsAssociationDeleteException() {
        eventTory.addVendor(ALICE);
        eventTory.addEvent(WEDDING);
        eventTory.assignVendorToEvent(ALICE, WEDDING);

        assertThrows(AssociationDeleteException.class, () -> eventTory.removeEvent(WEDDING));
    }

    @Test
    public void getAssociatedVendors_noAssociations_returnsEmptyList() {
        ObservableList<Vendor> associatedVendors = eventTory.getAssociatedVendors(WEDDING);
        assertEquals(FXCollections.observableArrayList(), associatedVendors);
    }

    @Test
    public void getAssociatedVendors_withAssociations_returnsCorrectVendors() {
        eventTory.addVendor(ALICE);
        eventTory.addVendor(BOB);
        eventTory.addEvent(WEDDING);
        eventTory.assignVendorToEvent(ALICE, WEDDING);
        eventTory.assignVendorToEvent(BOB, WEDDING);

        ObservableList<Vendor> associatedVendors = eventTory.getAssociatedVendors(WEDDING);
        ObservableList<Vendor> expectedVendors = FXCollections.observableArrayList(ALICE, BOB);

        associatedVendors.sort((v1, v2) -> v1.getName().fullName.compareTo(v2.getName().fullName));
        expectedVendors.sort((v1, v2) -> v1.getName().fullName.compareTo(v2.getName().fullName));

        assertEquals(expectedVendors, associatedVendors);
    }

    @Test
    public void getAssociatedEvents_noAssociations_returnsEmptyList() {
        ObservableList<Event> associatedEvents = eventTory.getAssociatedEvents(ALICE);
        assertEquals(FXCollections.observableArrayList(), associatedEvents);
    }

    @Test
    public void getAssociatedEvents_withAssociations_returnsCorrectEvents() {
        eventTory.addVendor(ALICE);
        eventTory.addEvent(WEDDING);
        eventTory.addEvent(BIRTHDAY);
        eventTory.assignVendorToEvent(ALICE, WEDDING);
        eventTory.assignVendorToEvent(ALICE, BIRTHDAY);

        ObservableList<Event> associatedEvents = eventTory.getAssociatedEvents(ALICE);
        ObservableList<Event> expectedEvents = FXCollections.observableArrayList(WEDDING, BIRTHDAY);

        associatedEvents.sort((e1, e2) -> e1.getName().fullName.compareTo(e2.getName().fullName));
        expectedEvents.sort((e1, e2) -> e1.getName().fullName.compareTo(e2.getName().fullName));

        assertEquals(expectedEvents, associatedEvents);
    }

    /**
     * A stub ReadOnlyEventTory whose vendors list can violate interface
     * constraints.
     */
    private static class EventToryStub implements ReadOnlyEventTory {
        private final ObservableList<Vendor> vendors = FXCollections.observableArrayList();
        private final ObservableList<Event> events = FXCollections.observableArrayList();

        EventToryStub(Collection<Vendor> vendors) {
            this.vendors.setAll(vendors);
        }

        @Override
        public ObservableList<Vendor> getVendorList() {
            return vendors;
        }

        @Override
        public ObservableList<Event> getEventList() {
            return events;
        }

        @Override
        public ObservableList<Association> getAssociationList() {
            throw new AssertionError("should not be called");
        }
    }

}
