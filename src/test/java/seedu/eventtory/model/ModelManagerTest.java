package seedu.eventtory.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eventtory.model.Model.PREDICATE_SHOW_ALL_VENDORS;
import static seedu.eventtory.testutil.Assert.assertThrows;
import static seedu.eventtory.testutil.TypicalEvents.BIRTHDAY;
import static seedu.eventtory.testutil.TypicalEvents.WEDDING;
import static seedu.eventtory.testutil.TypicalVendors.ALICE;
import static seedu.eventtory.testutil.TypicalVendors.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import seedu.eventtory.commons.core.GuiSettings;
import seedu.eventtory.model.association.Association;
import seedu.eventtory.model.event.Event;
import seedu.eventtory.model.event.UniqueEventList;
import seedu.eventtory.model.vendor.NameContainsKeywordsPredicate;
import seedu.eventtory.model.vendor.UniqueVendorList;
import seedu.eventtory.model.vendor.Vendor;
import seedu.eventtory.testutil.EventBuilder;
import seedu.eventtory.testutil.EventToryBuilder;
import seedu.eventtory.testutil.TypicalEvents;
import seedu.eventtory.testutil.TypicalVendors;
import seedu.eventtory.testutil.VendorBuilder;
import seedu.eventtory.ui.UiState;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new EventTory(), new EventTory(modelManager.getEventTory()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setEventToryFilePath(Paths.get("eventtory/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setEventToryFilePath(Paths.get("new/eventtory/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setEventToryFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setEventToryFilePath(null));
    }

    @Test
    public void setEventToryFilePath_validPath_setsEventToryFilePath() {
        Path path = Paths.get("eventtory/book/file/path");
        modelManager.setEventToryFilePath(path);
        assertEquals(path, modelManager.getEventToryFilePath());
    }

    @Test
    public void hasVendor_nullVendor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasVendor(null));
    }

    @Test
    public void hasVendor_vendorNotInEventTory_returnsFalse() {
        assertFalse(modelManager.hasVendor(ALICE));
    }

    @Test
    public void hasVendor_vendorInEventTory_returnsTrue() {
        modelManager.addVendor(ALICE);
        assertTrue(modelManager.hasVendor(ALICE));
    }

    @Test
    public void hasEvent_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasEvent(null));
    }

    @Test
    public void hasEvent_eventNotInEventTory_returnsFalse() {
        assertFalse(modelManager.hasEvent(WEDDING));
    }

    @Test
    public void hasEvent_eventInEventTory_returnsTrue() {
        modelManager.addEvent(WEDDING);
        assertTrue(modelManager.hasEvent(WEDDING));
    }

    @Test
    public void getFilteredVendorList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredVendorList().remove(0));
    }

    @Test
    public void getFilteredEventList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredEventList().remove(0));
    }

    @Test
    public void getUiState_setNewUiState_updateSuccessful() {
        ObjectProperty<UiState> observedState = new SimpleObjectProperty<>();
        modelManager.getUiState().addListener((observable, oldValue, newValue) -> {
            observedState.set(newValue);
        });

        modelManager.setUiState(UiState.EVENT_DETAILS);
        assertEquals(UiState.EVENT_DETAILS, observedState.get());

        modelManager.setUiState(UiState.VENDOR_DETAILS);
        assertEquals(UiState.VENDOR_DETAILS, observedState.get());
    }

    @Test
    public void getViewedEvent_setNewEvent_updateSuccessful() {
        ObjectProperty<Event> observedState = new SimpleObjectProperty<>();
        modelManager.getViewedEvent().addListener((observable, oldValue, newValue) -> {
            observedState.set(newValue);
        });

        Event event1 = new EventBuilder().withName("Event 1").build();
        Event event2 = new EventBuilder().withName("Event 2").build();

        modelManager.viewEvent(event1);
        assertEquals(event1, observedState.get());

        modelManager.viewEvent(event2);
        assertEquals(event2, observedState.get());
    }

    @Test
    public void getViewedVendor_setNewVendor_updateSuccessful() {
        ObjectProperty<Vendor> observedState = new SimpleObjectProperty<>();
        modelManager.getViewedVendor().addListener((observable, oldValue, newValue) -> {
            observedState.set(newValue);
        });

        Vendor vendor1 = new VendorBuilder().withName("Vendor 1").withPhone("123123").withDescription("Vendor 1")
                .build();
        Vendor vendor2 = new VendorBuilder().withName("Vendor 2").withPhone("321321").withDescription("Vendor 2")
                .build();

        modelManager.viewVendor(vendor1);
        assertEquals(vendor1, observedState.get());

        modelManager.viewVendor(vendor2);
        assertEquals(vendor2, observedState.get());
    }

    @Test
    public void getAssociatedVendors_noAssociations_returnsEmptyList() {
        ObservableList<Vendor> associatedVendors = modelManager.getAssociatedVendors(WEDDING);
        assertEquals(FXCollections.observableArrayList(), associatedVendors);
    }

    @Test
    public void getAssociatedVendors_withAssociations_returnsCorrectVendors() {
        modelManager.addVendor(ALICE);
        modelManager.addVendor(BENSON);
        modelManager.addEvent(WEDDING);
        modelManager.assignVendorToEvent(ALICE, WEDDING);
        modelManager.assignVendorToEvent(BENSON, WEDDING);

        ObservableList<Vendor> associatedVendors = modelManager.getAssociatedVendors(WEDDING);
        ObservableList<Vendor> expectedVendors = FXCollections.observableArrayList(BENSON, ALICE);

        associatedVendors.sort((v1, v2) -> v1.getName().fullName.compareTo(v2.getName().fullName));
        expectedVendors.sort((v1, v2) -> v1.getName().fullName.compareTo(v2.getName().fullName));

        assertEquals(expectedVendors, associatedVendors);
    }

    @Test
    public void getAssociatedEvents_noAssociations_returnsEmptyList() {
        ObservableList<Event> associatedEvents = modelManager.getAssociatedEvents(ALICE);
        assertEquals(FXCollections.observableArrayList(), associatedEvents);
    }

    @Test
    public void getAssociatedEvents_withAssociations_returnsCorrectEvents() {
        modelManager.addVendor(ALICE);
        modelManager.addEvent(WEDDING);
        modelManager.addEvent(BIRTHDAY);
        modelManager.assignVendorToEvent(ALICE, WEDDING);
        modelManager.assignVendorToEvent(ALICE, BIRTHDAY);

        ObservableList<Event> associatedEvents = modelManager.getAssociatedEvents(ALICE);
        ObservableList<Event> expectedEvents = FXCollections.observableArrayList(WEDDING, BIRTHDAY);

        associatedEvents.sort((e1, e2) -> e1.getName().fullName.compareTo(e2.getName().fullName));
        expectedEvents.sort((e1, e2) -> e1.getName().fullName.compareTo(e2.getName().fullName));

        assertEquals(expectedEvents, associatedEvents);
    }

    @Test
    public void getAssociation_newAssociation_updateSuccessful() {
        ObjectProperty<Association> observedState = new SimpleObjectProperty<>();
        modelManager.getAssociationList().addListener((ListChangeListener<? super Association>) change -> {
            if (change.next() && change.wasAdded()) {
                observedState.set((Association) change.getAddedSubList().get(0));
            }
        });

        modelManager.assignVendorToEvent(TypicalVendors.AMY, TypicalEvents.BIRTHDAY);

        UniqueVendorList uniqueVendorList = new UniqueVendorList();
        uniqueVendorList.add(TypicalVendors.AMY);

        UniqueEventList uniqueEventList = new UniqueEventList();
        uniqueEventList.add(TypicalEvents.BIRTHDAY);

        Vendor uniqueVendor = null;
        for (Vendor vendor : uniqueVendorList) {
            if (vendor.equals(TypicalVendors.AMY)) {
                uniqueVendor = vendor;
                break;
            }
        }

        Event uniqueEvent = null;
        for (Event event : uniqueEventList) {
            if (event.equals(TypicalEvents.BIRTHDAY)) {
                uniqueEvent = event;
                break;
            }
        }

        Association expectedAssociation = new Association(
                uniqueVendor.getId(),
                uniqueEvent.getId());
        assertEquals(observedState.get(), expectedAssociation);
    }

    @Test
    public void equals() {
        EventTory eventTory = new EventToryBuilder().withVendor(ALICE).withVendor(BENSON).build();
        EventTory differentEventTory = new EventTory();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(eventTory, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(eventTory, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different eventTory -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentEventTory, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredVendorList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(eventTory, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredVendorList(PREDICATE_SHOW_ALL_VENDORS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setEventToryFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(eventTory, differentUserPrefs)));
    }
}

