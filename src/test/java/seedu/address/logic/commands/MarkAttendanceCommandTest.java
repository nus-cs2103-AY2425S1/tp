package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_TELEGRAM;
import static seedu.address.logic.Messages.MESSAGE_NONMEMBER_ATTENDANCE;
import static seedu.address.logic.commands.AttendanceMarkingCommand.displayMembers;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.MarkAttendanceCommand.MESSAGE_CONTAIN_MARKED_MEMBER;
import static seedu.address.logic.commands.MarkAttendanceCommand.MESSAGE_MARK_MEMBER_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.person.Person;
import seedu.address.model.person.Telegram;
import seedu.address.testutil.PersonBuilder;

public class MarkAttendanceCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_successfulMarkAttendance() throws Exception {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        Attendance attendance = new Attendance("2024-10-21");
        Person validPerson1 = new PersonBuilder().withTelegram(ALICE.getTelegram().value)
                .isMember(true).withName(ALICE.getName().fullName).build();
        Person validPerson2 = new PersonBuilder().withTelegram(BENSON.getTelegram().value)
                .isMember(true).withName(BENSON.getName().fullName).build();
        model.setPerson(ALICE, validPerson1);
        model.setPerson(BENSON, validPerson2);

        MarkAttendanceCommand command = new MarkAttendanceCommand(
                List.of(ALICE.getTelegram(), BENSON.getTelegram()), attendance);

        Person markedPerson1 = new PersonBuilder().withTelegram(ALICE.getTelegram().value)
                .isMember(true).withName(ALICE.getName().fullName).withAttendance(attendance).build();
        Person markedPerson2 = new PersonBuilder().withTelegram(BENSON.getTelegram().value)
                .isMember(true).withName(BENSON.getName().fullName).withAttendance(attendance).build();

        List<Person> membersAttendanceMarkSuccess = List.of(markedPerson1, markedPerson2);
        expectedModel.setPerson(ALICE, markedPerson1);
        expectedModel.setPerson(BENSON, markedPerson2);

        String expectedMessage = String.format(
                String.format(MESSAGE_MARK_MEMBER_SUCCESS, attendance, displayMembers(membersAttendanceMarkSuccess)));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistTelegramAttendance_throwsCommandException() {
        Attendance attendance = new Attendance("2024-10-21");
        MarkAttendanceCommand command = new MarkAttendanceCommand(List.of(BOB.getTelegram()), attendance);
        String expectedMessage = String.format(MESSAGE_INVALID_TELEGRAM, List.of(BOB.getTelegram()));
        assertThrows(CommandException.class, expectedMessage, () -> command.execute(model));
    }

    @Test
    public void execute_nonMemberAttendance_throwsCommandException() {
        Attendance attendance = new Attendance("2024-10-21");
        MarkAttendanceCommand command = new MarkAttendanceCommand(List.of(BENSON.getTelegram()), attendance);

        assertThrows(CommandException.class, MESSAGE_NONMEMBER_ATTENDANCE, () -> command.execute(model));
    }

    @Test
    public void execute_duplicateAttendance() throws Exception {
        Attendance attendance = new Attendance("2024-10-21");
        Person markedPerson = new PersonBuilder().withTelegram(ALICE.getTelegram().value)
                .isMember(true).withName(ALICE.getName().fullName).withAttendance(attendance).build();

        model.setPerson(ALICE, markedPerson);

        MarkAttendanceCommand command = new MarkAttendanceCommand(Arrays.asList(ALICE.getTelegram()), attendance);

        CommandResult result = command.execute(model);
        List<Person> membersAttendanceMarked = new ArrayList<>();
        membersAttendanceMarked.add(ALICE);

        List<String> alreadyMarkedList = displayMembers(membersAttendanceMarked);
        assertEquals(String.format(MESSAGE_CONTAIN_MARKED_MEMBER, alreadyMarkedList),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_partialDuplicateAttendance() throws Exception {
        Attendance attendance = new Attendance("2024-10-21");
        Person markedPerson = new PersonBuilder().withTelegram(ALICE.getTelegram().value)
                .isMember(true).withName(ALICE.getName().fullName).withAttendance(attendance).build();

        Person unmarkedPerson = new PersonBuilder().withTelegram(BENSON.getTelegram().value)
                .isMember(true).withName(BENSON.getName().fullName).build();

        model.setPerson(ALICE, markedPerson);
        model.setPerson(BENSON, unmarkedPerson);

        MarkAttendanceCommand command = new MarkAttendanceCommand(
                Arrays.asList(ALICE.getTelegram(), BENSON.getTelegram()), attendance);

        CommandResult result = command.execute(model);
        List<Person> membersAttendanceMarked = List.of(ALICE);
        List<Person> membersMarkSuccess = List.of(BENSON);

        List<String> alreadyMarkedList = displayMembers(membersAttendanceMarked);
        String expectedMessage = String.format(
                MESSAGE_MARK_MEMBER_SUCCESS, attendance, displayMembers(membersMarkSuccess))
                + String.format(MESSAGE_CONTAIN_MARKED_MEMBER, alreadyMarkedList);

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void equals() {
        Telegram telegramAlice = new Telegram("aliceTelegram");
        Telegram telegramBob = new Telegram("bobTelegram");
        Attendance attendance = new Attendance("2024-10-21");

        MarkAttendanceCommand commandAlice = new MarkAttendanceCommand(Arrays.asList(telegramAlice), attendance);
        MarkAttendanceCommand commandBob = new MarkAttendanceCommand(Arrays.asList(telegramBob), attendance);

        // same object -> returns true
        assertTrue(commandAlice.equals(commandAlice));

        // same values -> returns true
        MarkAttendanceCommand commandAliceCopy = new MarkAttendanceCommand(Arrays.asList(telegramAlice), attendance);
        assertTrue(commandAlice.equals(commandAliceCopy));

        // different types -> returns false
        assertFalse(commandAlice.equals(1));

        // null -> returns false
        assertFalse(commandAlice.equals(null));

        // different telegrams -> returns false
        assertFalse(commandAlice.equals(commandBob));
    }

    @Test
    public void toStringMethod() {
        Telegram telegramAlice = new Telegram("aliceTelegram");
        Attendance attendance = new Attendance("2024-10-21");
        MarkAttendanceCommand command = new MarkAttendanceCommand(Arrays.asList(telegramAlice), attendance);
        String expected = MarkAttendanceCommand.class.getCanonicalName()
                + "{telegrams=[aliceTelegram], attendance=" + attendance + "}";
        assertEquals(expected, command.toString());
    }
}
