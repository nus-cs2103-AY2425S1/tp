package seedu.address.logic.commands.event.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.ART_EXHIBITION;
import static seedu.address.testutil.TypicalEvents.TECH_CONFERENCE;
import static seedu.address.testutil.TypicalEvents.TEST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManager;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.EventBuilder;

public class AddPersonToEventCommandTest {
    private Model model;
    private EventManager eventManager;
    private HashSet<Index> attendees;
    private HashSet<Index> volunteers;
    private HashSet<Index> vendors;
    private HashSet<Index> sponsors;

    @BeforeEach
    void setup() {
        this.attendees = new HashSet<>();
        this.volunteers = new HashSet<>();
        this.vendors = new HashSet<>();
        this.sponsors = new HashSet<>();

        ReadOnlyAddressBook readOnlyAddressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON)
                .withPerson(DANIEL).withPerson(ELLE).build();
        EventManager eventManager = new EventManager();
        eventManager.addEvent(TECH_CONFERENCE);
        eventManager.addEvent(ART_EXHIBITION);
        eventManager.addEvent(new EventBuilder().withName("Test Event").build());

        this.model = new ModelManager(readOnlyAddressBook, eventManager, new UserPrefs());
        this.eventManager = eventManager;
    }
    @Test
    public void constructor_nullField_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPersonToEventCommand(null, null,
                null, null, null));
    }

    @Test
    public void execute_nullField_throwsNullPointerException() {
        AddPersonToEventCommand addPersonToEventCommand = new
                AddPersonToEventCommand(Index.fromZeroBased(0),
                attendees, volunteers, vendors, sponsors);
        assertThrows(NullPointerException.class, () -> addPersonToEventCommand.execute(null,
                null));
    }

    @Test
    public void execute_invalidContactIndex_throwsCommandException() {
        attendees.add(INDEX_FIRST_PERSON);
        sponsors.add(Index.fromZeroBased(4)); //invalid index

        AddPersonToEventCommand addPersonToEventCommand = new
                AddPersonToEventCommand(Index.fromZeroBased(0),
                attendees, volunteers, vendors, sponsors);
        assertThrows(CommandException.class, () -> addPersonToEventCommand.execute(this.model,
                this.eventManager));
    }

    @Test
    public void execute_invalidEventIndex_throwsCommandException() {
        attendees.add(INDEX_FIRST_PERSON);
        sponsors.add(Index.fromZeroBased(0));

        AddPersonToEventCommand addPersonToEventCommand = new
                AddPersonToEventCommand(Index.fromZeroBased(2), //invalid index
                attendees, volunteers, vendors, sponsors);
        assertThrows(CommandException.class, () -> addPersonToEventCommand.execute(this.model,
                this.eventManager));
    }

    @Test
    public void execute_correctInputs_contactsAddedToEventSuccessfully() throws CommandException {
        attendees.add(Index.fromZeroBased(0));
        volunteers.add(Index.fromZeroBased(3));
        vendors.add(Index.fromZeroBased(2));
        sponsors.add(Index.fromZeroBased(1));

        AddPersonToEventCommand addPersonToEventCommand = new
                AddPersonToEventCommand(Index.fromZeroBased(2),
                attendees, volunteers, vendors, sponsors);
        Event expectedEvent = TEST_EVENT;
        addPersonToEventCommand.execute(this.model, this.eventManager);
        Event actualEvent = this.eventManager.getEventList().get(2);
        assertEquals(expectedEvent, actualEvent);
    }

    @Test
    public void execute_incorrectRoleOfContactAdded_throwsCommandException() {
        volunteers.add(Index.fromZeroBased(0));

        AddPersonToEventCommand addPersonToEventCommand = new
                AddPersonToEventCommand(Index.fromZeroBased(2),
                attendees, volunteers, vendors, sponsors);

        assertThrows(CommandException.class, () -> addPersonToEventCommand.execute(this.model,
                this.eventManager));
    }

    @Test
    public void execute_duplicateContactAddedToSameRole_throwsCommandException() {
        attendees.add(Index.fromZeroBased(0));

        AddPersonToEventCommand addPersonToEventCommand = new
                AddPersonToEventCommand(Index.fromZeroBased(0),
                attendees, volunteers, vendors, sponsors);

        assertThrows(CommandException.class, () -> addPersonToEventCommand.execute(this.model,
                this.eventManager));
    }

    @Test
    public void execute_correctInputs_correctCommandResultMsg() throws CommandException {
        vendors.add(Index.fromZeroBased(2));

        AddPersonToEventCommand addPersonToEventCommand = new
                AddPersonToEventCommand(Index.fromZeroBased(2),
                attendees, volunteers, vendors, sponsors);

        CommandResult expectedResult = new CommandResult("Contacts added to Test Event successfully: \n"
                + "Vendor(s): Daniel Meier");
        CommandResult actualResult = addPersonToEventCommand.execute(this.model, this.eventManager);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testToString() {
        vendors.add(Index.fromZeroBased(2));

        AddPersonToEventCommand addPersonToEventCommand = new
                AddPersonToEventCommand(Index.fromZeroBased(2),
                attendees, volunteers, vendors, sponsors);

        String expectedString = "seedu.address.logic.commands.event.commands.AddPersonToEventCommand{eventIndex="
                + "seedu.address.commons.core.index.Index{zeroBasedIndex=2}, attendees=[], volunteers=[], vendors="
                + "[seedu.address.commons.core.index.Index{zeroBasedIndex=2}], sponsors=[]}";
        String actualString = addPersonToEventCommand.toString();

        assertEquals(expectedString, actualString);
    }

    @Test
    public void testEquals() {
        vendors.add(Index.fromZeroBased(2));

        AddPersonToEventCommand addPersonToEventCommand = new
                AddPersonToEventCommand(Index.fromZeroBased(2),
                attendees, volunteers, vendors, sponsors);

        AddPersonToEventCommand anotherAddPersonToEventCommand = new
                AddPersonToEventCommand(Index.fromZeroBased(2),
                attendees, volunteers, vendors, sponsors);

        AddPersonToEventCommand differentAddPersonToEventCommand = new
                AddPersonToEventCommand(Index.fromZeroBased(0),
                attendees, volunteers, vendors, sponsors);

        assertTrue(addPersonToEventCommand.equals(addPersonToEventCommand));
        assertTrue(addPersonToEventCommand.equals(anotherAddPersonToEventCommand));
        assertFalse(addPersonToEventCommand.equals(differentAddPersonToEventCommand));
    }
}
