//package seedu.address.logic.commands;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.logic.commands.exceptions.CommandException;
//import seedu.address.model.Model;
//import seedu.address.model.ModelManager;
//import seedu.address.model.UserPrefs;
//import seedu.address.model.event.Event;
//import seedu.address.model.event.EventName;
//import seedu.address.testutil.TypicalAddressBook;
//
//public class AddEventCommandTest {
//    @Test
//    void execute_eventAcceptedByModel_addSuccessful() {
//        Model modelStub = new ModelManager();
//        Event validEvent = new Event(new EventName("IFG"));
//
//        CommandResult commandResult = new AddEventCommand(validEvent).execute(modelStub);
//
//        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), commandResult.getFeedbackToUser());
//        assertTrue(modelStub.hasEvent(validEvent));
//    }
//
//    @Test
//    void execute_duplicateEvent_throwsCommandException() {
//        Event validEvent = new Event(new EventName("IFG"));
//        AddEventCommand addEventCommand = new AddEventCommand(validEvent);
//        Model modelStub = new ModelManager(TypicalAddressBook.getTypicalAddressBook(), new UserPrefs());
//
//        assertThrows(CommandException.class, () -> addEventCommand.execute(modelStub),
//                AddEventCommand.MESSAGE_DUPLICATE_EVENT);
//    }
//
//    @Test
//    void equals_sameObject_returnsTrue() {
//        Event event = new Event(new EventName("IFG"));
//        AddEventCommand addEventCommand = new AddEventCommand(event);
//        assertEquals(addEventCommand, addEventCommand);
//    }
//
//    @Test
//    void equals_differentTypes_returnsFalse() {
//        Event event = new Event(new EventName("IFG"));
//        AddEventCommand addEventCommand = new AddEventCommand(event);
//        assertNotEquals(1, addEventCommand);
//    }
//
//    @Test
//    void equals_null_returnsFalse() {
//        Event event = new Event(new EventName("IFG"));
//        AddEventCommand addEventCommand = new AddEventCommand(event);
//        assertNotEquals(null, addEventCommand);
//    }
//
//    @Test
//    void equals_differentEvent_returnsFalse() {
//        AddEventCommand addEventCommand1 = new AddEventCommand(new Event(new EventName("IFG")));
//        AddEventCommand addEventCommand2 = new AddEventCommand(new Event(new EventName("SUNIG")));
//        assertNotEquals(addEventCommand1, addEventCommand2);
//    }
//}
