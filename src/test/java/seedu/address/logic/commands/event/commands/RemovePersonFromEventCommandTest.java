package seedu.address.logic.commands.event.commands;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManager;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;



public class RemovePersonFromEventCommandTest {

    /**
    * TypicalEvents have the following Events, displayin in order:
    * public static final Event TECH_CONFERENCE = new EventBuilder().withName("Tech Conference 2024")
    *             .withAttendees(getPersonSet(TypicalPersons.ALICE, TypicalPersons.FIONA))
    *             .withVendors(getPersonSet(TypicalPersons.DANIEL))
    *             .withSponsors(getPersonSet(TypicalPersons.BENSON, TypicalPersons.CARL))
    *             .withVolunteers(getPersonSet(TypicalPersons.ELLE))
    *             .build();
    *
    *     public static final Event ART_EXHIBITION = new EventBuilder().withName("Art Exhibition 2024")
    *             .withAttendees(getPersonSet(TypicalPersons.BOB))
    *             .withVendors(getPersonSet(TypicalPersons.AMY, TypicalPersons.GEORGE))
    *             .withSponsors(getPersonSet(TypicalPersons.BENSON))
    *             .withVolunteers(getPersonSet(TypicalPersons.HOON, TypicalPersons.IDA))
    *             .build();
    *
    *     // Manually added events
    *     public static final Event SPORTS_FESTIVAL = new EventBuilder().withName("Sports Festival")
    *             .withAttendees(getPersonSet(TypicalPersons.ALICE))
    *             .withVendors(getPersonSet(TypicalPersons.DANIEL))
    *             .withSponsors(getPersonSet(TypicalPersons.BENSON))
    *             .withVolunteers(getPersonSet(TypicalPersons.ELLE))
    *             .build();
    *
    *
    *
    * Persons used and index in TypicalPersons
    * ALICE = 0
    * BENSON = 1
    * CARL = 2
    * DANIEL = 3
    * ELLE = 4
    * FIONA = 5
    * GEORGE = 6
     *
     * Unused Persons Not in TypicalPersons:
     * HOON = 7
     *
    */
    private Model model;

    private Event validEvent;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventManager(), new UserPrefs());

    }


    @Test
    public void execute_validUnfilteredList_success() throws Exception {
        EventManager eventManager = model.getEventManager();

        assertEquals(eventManager.getEventList().get(0).getAllPersons().size(), 6);
        // Remove ALICE from TECH_CONFERENCE
        RemovePersonFromEventCommand removePersonFromEventCommand = new RemovePersonFromEventCommand(
                Index.fromOneBased(1), INDEX_FIRST_PERSON);
        removePersonFromEventCommand.execute(model, eventManager);
        //check no. of people in TECH_CONFERENCE
        Event techConference = eventManager.getEventList().get(0);
        assertEquals(techConference.getAllPersons().size(), 5);

    }

    @Test
    public void execute_invalidEventIndex_throwsCommandException() {
        RemovePersonFromEventCommand removePersonFromEventCommand = new RemovePersonFromEventCommand(
                Index.fromOneBased(9), INDEX_FIRST_PERSON);
        assertCommandFailure(removePersonFromEventCommand, model, RemovePersonFromEventCommand.MESSAGE_EVENT_NOT_FOUND);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        RemovePersonFromEventCommand removePersonFromEventCommand = new RemovePersonFromEventCommand(
                Index.fromOneBased(1), Index.fromOneBased(7));
        assertCommandFailure(removePersonFromEventCommand, model,
                RemovePersonFromEventCommand.MESSAGE_PERSON_NOT_FOUND);
    }

    @Test
    public void execute_personNotInEvent_throwsCommandException() {
        model.addPerson(TypicalPersons.HOON);
        RemovePersonFromEventCommand removePersonFromEventCommand = new RemovePersonFromEventCommand(
                Index.fromOneBased(1), Index.fromOneBased(7));
        assertCommandFailure(removePersonFromEventCommand, model,
                RemovePersonFromEventCommand.MESSAGE_PERSON_NOT_FOUND);
    }

    @Test
    public void execute_personOutOfIndex_throwsCommandException() {
        RemovePersonFromEventCommand removePersonFromEventCommand = new RemovePersonFromEventCommand(
                Index.fromOneBased(1), Index.fromOneBased(99));
        assertCommandFailure(removePersonFromEventCommand, model,
                RemovePersonFromEventCommand.MESSAGE_PERSON_NOT_FOUND);
    }





    @Test
    public void equals_sameCommand_returnsTrue() {
        RemovePersonFromEventCommand removePersonFromEventCommand = new RemovePersonFromEventCommand(
                Index.fromOneBased(21), INDEX_FIRST_PERSON);
        assertEquals(removePersonFromEventCommand, removePersonFromEventCommand);
    }

    @Test
    public void equals_sameCommandDifferentIndex_returnsFalse() {
        RemovePersonFromEventCommand removePersonFromEventCommand = new RemovePersonFromEventCommand(
                Index.fromOneBased(1), INDEX_FIRST_PERSON);
        RemovePersonFromEventCommand removePersonFromEventCommand2 = new RemovePersonFromEventCommand(
                Index.fromOneBased(1), Index.fromOneBased(2));
        assertNotEquals(removePersonFromEventCommand, removePersonFromEventCommand2);
    }

    @Test
    public void execute_lastPredicateNotRelated() {
        EventManager eventManager = model.getEventManager();
        assertEquals(eventManager.getEventList().get(0).getAllPersons().size(), 6);
        // Remove ALICE from TECH_CONFERENCE
        RemovePersonFromEventCommand removePersonFromEventCommand = new RemovePersonFromEventCommand(
                Index.fromOneBased(1), INDEX_FIRST_PERSON);
        model.updateFilteredPersonList(p -> p.getName().fullName.contains("Alice"));

        // filtered list should have length 1 (only alice)
        assertEquals(model.getFilteredPersonList().size(), 1);
        try {
            removePersonFromEventCommand.execute(model, eventManager);
        } catch (Exception e) {
            // do nothing
        }
        //check no. of people in TECH_CONFERENCE
        Event techConference = eventManager.getEventList().get(0);
        assertEquals(5, techConference.getAllPersons().size());
        //check no. of people in ART_EXHIBITION
        Event artExhibition = eventManager.getEventList().get(1);
        assertEquals(6, artExhibition.getAllPersons().size());
        //check that displayed list is the same
        assertEquals(model.getFilteredPersonList().size(), 1);
    }

    @Test
    public void execute_lastPredicateRelated() {
        EventManager eventManager = model.getEventManager();
        assertEquals(eventManager.getEventList().get(0).getAllPersons().size(), 6);
        // Remove ALICE from TECH_CONFERENCE
        RemovePersonFromEventCommand removePersonFromEventCommand = new RemovePersonFromEventCommand(
                Index.fromOneBased(1), INDEX_FIRST_PERSON);
        model.updateFilteredPersonList(eventManager.getPersonInEventPredicate(eventManager.getEventList().get(0)));

        // filtered list should have length
        assertEquals(model.getFilteredPersonList().size(), 6);
        try {
            removePersonFromEventCommand.execute(model, eventManager);
        } catch (Exception e) {
            // do nothing
        }
        //check no. of people in TECH_CONFERENCE
        Event techConference = eventManager.getEventList().get(0);
        assertEquals(5, techConference.getAllPersons().size());
        //check no. of people in ART_EXHIBITION
        Event artExhibition = eventManager.getEventList().get(1);
        assertEquals(6, artExhibition.getAllPersons().size());
        //check that displayed list was updated
        assertEquals(model.getFilteredPersonList().size(), 5);
    }



    @Test
    public void execute_multipleRoles() throws Exception {
        EventManager eventManager = model.getEventManager();
        Person testMan = new PersonBuilder().withRoles("attendee", "vendor").build();
        RemovePersonFromEventCommand removePersonFromEventCommand = new RemovePersonFromEventCommand(
                Index.fromOneBased(1), INDEX_FIRST_PERSON); //remove Alice

        eventManager.getEventList().get(0).addPerson(testMan, "vendor");
        eventManager.getEventList().get(0).addPerson(testMan, "attendee");
        model.addPerson(testMan);
        assertEquals(eventManager.getEventList().get(0).getAllPersons().size(), 7);
        RemovePersonFromEventCommand removePersonFromEventCommand2 = new RemovePersonFromEventCommand(
                Index.fromOneBased(1),
                Index.fromZeroBased(model.getFilteredPersonList().size() - 1));
        removePersonFromEventCommand2.execute(model, eventManager);
        //check no. of people in TECH_CONFERENCE
        Event techConference = eventManager.getEventList().get(0);
        assertEquals(techConference.getAllPersons().size(), 6);
        assert(!techConference.hasPerson(testMan));
    }


}
