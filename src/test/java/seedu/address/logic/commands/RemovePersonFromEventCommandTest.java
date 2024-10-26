package seedu.address.logic.commands;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.contact.commands.ClearCommand;
import seedu.address.logic.commands.contact.commands.EditCommand;
import seedu.address.logic.commands.contact.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.event.commands.RemovePersonFromEventCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventList;
import seedu.address.model.event.EventManager;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

public class RemovePersonFromEventCommandTest {

    /** TypicalEvents have the following Events, displayin in order:
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
    /**
     * Persons used and index in TypicalPersons
     * ALICE = 0
     * BENSON = 1
     * CARL = 2
     * DANIEL = 3
     * ELLE = 4
     * FIONA = 5
     * GEORGE = 6
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
        RemovePersonFromEventCommand removePersonFromEventCommand = new RemovePersonFromEventCommand
                (Index.fromOneBased(1), INDEX_FIRST_PERSON);
        removePersonFromEventCommand.execute(model, eventManager);
        //check no. of people in TECH_CONFERENCE
        Event techConference = eventManager.getEventList().get(0);
        assertEquals(techConference.getAllPersons().size(), 5);

    }

    @Test
    public void execute_invalidEventIndex_throwsCommandException() {
        EventManager eventManager = model.getEventManager();
        RemovePersonFromEventCommand removePersonFromEventCommand = new RemovePersonFromEventCommand
                (Index.fromOneBased(3), INDEX_FIRST_PERSON);
        assertCommandFailure(removePersonFromEventCommand, model, RemovePersonFromEventCommand.MESSAGE_EVENT_NOT_FOUND);
    }



}
