package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Meeting;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for MeetingsCommand.
 */
public class MeetingsCommandTest {

    private static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withRole("mUdder").withMajor("bza")
            .withPhone("94351253")
            .withTags("friends").build();

    private static final Person ALICE_COPY = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withRole("mUdder").withMajor("bza")
            .withPhone("94351253")
            .withTags("friends").build();

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsEmpty_success() {
        assertCommandSuccess(new MeetingsCommand(), model, MeetingsCommand.MESSAGE_NO_MEETINGS, expectedModel);
    }

    @Test
    public void execute_listIsNotEmpty_success() throws CommandException {
        Name name = new Name("A Valid Name");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime startTime = LocalDateTime.parse("30-07-2024 11:00", formatter);
        LocalDateTime endTime = LocalDateTime.parse("30-07-2024 12:00", formatter);
        String location = "A Valid Location";

        model.addMeeting(ALICE, new Meeting(name, startTime, endTime, location));
        expectedModel.addMeeting(ALICE_COPY, new Meeting(name, startTime, endTime, location));

        CommandResult actualCommandResult = new CommandResult(new MeetingsCommand().execute(model).getFeedbackToUser());
        CommandResult expectedCommandResult = new CommandResult(new MeetingsCommand()
                .execute(expectedModel).getFeedbackToUser());
        assertEquals(actualCommandResult, expectedCommandResult);
    }
}
