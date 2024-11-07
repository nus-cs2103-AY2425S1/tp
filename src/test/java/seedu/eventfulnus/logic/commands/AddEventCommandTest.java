package seedu.eventfulnus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import javafx.util.Pair;
import seedu.eventfulnus.logic.Messages;
import seedu.eventfulnus.logic.commands.exceptions.CommandException;
import seedu.eventfulnus.model.Model;
import seedu.eventfulnus.model.ModelManager;
import seedu.eventfulnus.model.UserPrefs;
import seedu.eventfulnus.model.event.Event;
import seedu.eventfulnus.model.event.Venue;
import seedu.eventfulnus.model.person.Person;
import seedu.eventfulnus.model.person.role.Faculty;
import seedu.eventfulnus.model.person.role.athlete.Sport;
import seedu.eventfulnus.testutil.TypicalAddressBook;


public class AddEventCommandTest {

    @Test
    void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        Model modelStub = new ModelManager();
        Set<Person> participants = new HashSet<>();
        Event validEvent = new Event(Sport.CHESS,
                new Pair<>(Faculty.COM, Faculty.NUSC),
                new Venue("Stadium"),
                LocalDateTime.parse("2021 03 01 1000", Event.DATE_TIME_PARSE_FORMATTER),
                participants);

        CommandResult commandResult = new AddEventCommand(validEvent).execute(modelStub);

        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, Messages.formatEvent(validEvent)),
                commandResult.getFeedbackToUser());
        assertTrue(modelStub.hasEvent(validEvent));
    }

    @Test
    void execute_duplicateEvent_throwsCommandException() {
        Set<Person> participants = new HashSet<>();
        Event validEvent = new Event(Sport.CHESS,
                new Pair<>(Faculty.COM, Faculty.NUSC),
                new Venue("Stadium"),
                LocalDateTime.parse("2024 09 20 1000", Event.DATE_TIME_PARSE_FORMATTER),
                participants);
        AddEventCommand addEventCommand = new AddEventCommand(validEvent);
        Model modelStub = new ModelManager(TypicalAddressBook.getTypicalAddressBook(), new UserPrefs());

        assertThrows(CommandException.class, () -> addEventCommand.execute(modelStub),
                AddEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    void equals_sameObject_returnsTrue() {
        Set<Person> participants = new HashSet<>();
        Event event = new Event(Sport.CHESS,
                new Pair<>(Faculty.COM, Faculty.NUSC),
                new Venue("Stadium"),
                LocalDateTime.parse("2021 03 01 1000", Event.DATE_TIME_PARSE_FORMATTER),
                participants);
        AddEventCommand addEventCommand = new AddEventCommand(event);
        assertEquals(addEventCommand, addEventCommand);
    }

    @Test
    void equals_differentTypes_returnsFalse() {
        Set<Person> participants = new HashSet<>();
        Event event = new Event(Sport.CHESS,
                new Pair<>(Faculty.COM, Faculty.NUSC),
                new Venue("Stadium"),
                LocalDateTime.parse("2021 03 01 1000", Event.DATE_TIME_PARSE_FORMATTER),
                participants);
        AddEventCommand addEventCommand = new AddEventCommand(event);
        assertNotEquals(1, addEventCommand);
    }

    @Test
    void equals_null_returnsFalse() {
        Set<Person> participants = new HashSet<>();
        Event event = new Event(Sport.CHESS,
                new Pair<>(Faculty.COM, Faculty.NUSC),
                new Venue("Stadium"),
                LocalDateTime.parse("2021 03 01 1000", Event.DATE_TIME_PARSE_FORMATTER),
                participants);
        AddEventCommand addEventCommand = new AddEventCommand(event);
        assertNotEquals(null, addEventCommand);
    }

    @Test
    void equals_differentEvent_returnsFalse() {
        Set<Person> participants1 = new HashSet<>();
        Set<Person> participants2 = new HashSet<>();
        AddEventCommand addEventCommand1 = new AddEventCommand(new Event(Sport.CHESS,
                new Pair<>(Faculty.COM, Faculty.NUSC),
                new Venue("Stadium"),
                LocalDateTime.parse("2021 03 01 1000", Event.DATE_TIME_PARSE_FORMATTER),
                participants1));
        AddEventCommand addEventCommand2 = new AddEventCommand(new Event(Sport.TABLE_TENNIS,
                new Pair<>(Faculty.LAW, Faculty.BIZ),
                new Venue("Court"),
                LocalDateTime.parse("2021 03 01 1000", Event.DATE_TIME_PARSE_FORMATTER),
                participants2));
        assertNotEquals(addEventCommand1, addEventCommand2);
    }
}
