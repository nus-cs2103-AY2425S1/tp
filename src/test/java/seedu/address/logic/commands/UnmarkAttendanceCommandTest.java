package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_TELEGRAM;
import static seedu.address.logic.Messages.MESSAGE_NONMEMBER_ATTENDANCE;
import static seedu.address.logic.commands.AttendanceMarkingCommand.displayMembers;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.UnmarkAttendanceCommand.MESSAGE_CONTAIN_UNMARKED_MEMBER;
import static seedu.address.logic.commands.UnmarkAttendanceCommand.MESSAGE_UNMARK_MEMBER_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.person.Person;
import seedu.address.model.person.Telegram;
import seedu.address.model.role.Member;
import seedu.address.testutil.PersonBuilder;

public class UnmarkAttendanceCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_successfulUnmarkAttendance() throws Exception {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        Attendance attendance = new Attendance("2024-10-21");
        Person validPerson1 = new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(),
                ALICE.getTelegram(), Set.of(new Member()), Set.of(attendance), ALICE.getFavouriteStatus());
        Person validPerson2 = new Person(BENSON.getName(), BENSON.getPhone(), BENSON.getEmail(),
                BENSON.getTelegram(), Set.of(new Member()), Set.of(attendance), BENSON.getFavouriteStatus());
        model.setPerson(ALICE, validPerson1);
        model.setPerson(BENSON, validPerson2);

        UnmarkAttendanceCommand command = new UnmarkAttendanceCommand(
                List.of(ALICE.getTelegram(), BENSON.getTelegram()), attendance);

        Person unmarkedPerson1 = new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(),
                ALICE.getTelegram(), Set.of(new Member()), new HashSet<>(), ALICE.getFavouriteStatus());
        Person unmarkedPerson2 = new Person(BENSON.getName(), BENSON.getPhone(), BENSON.getEmail(),
                BENSON.getTelegram(), Set.of(new Member()), new HashSet<>(), BENSON.getFavouriteStatus());
        expectedModel.setPerson(ALICE, unmarkedPerson1);
        expectedModel.setPerson(BENSON, unmarkedPerson2);

        List<Person> membersAttendanceUnmarkSuccess = List.of(ALICE, BENSON);

        String expectedMessage = String.format(String.format(MESSAGE_UNMARK_MEMBER_SUCCESS,
                attendance, displayMembers(membersAttendanceUnmarkSuccess)));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistTelegramAttendance_throwsCommandException() {
        Attendance attendance = new Attendance("2024-10-21");
        UnmarkAttendanceCommand command = new UnmarkAttendanceCommand(List.of(BOB.getTelegram()), attendance);
        String expectedMessage = String.format(MESSAGE_INVALID_TELEGRAM, List.of(BOB.getTelegram()));
        assertThrows(CommandException.class, expectedMessage, () -> command.execute(model));
    }

    @Test
    public void execute_nonMemberAttendance_throwsCommandException() {
        Attendance attendance = new Attendance("2024-10-21");
        Person memberBenson = new PersonBuilder().withTelegram(BENSON.getTelegram().value)
                .isMember(false).withName(BENSON.getName().fullName).withAttendance(attendance).build();
        model.setPerson(BENSON, memberBenson);
        MarkAttendanceCommand command = new MarkAttendanceCommand(List.of(BENSON.getTelegram()), attendance);

        assertThrows(CommandException.class, MESSAGE_NONMEMBER_ATTENDANCE, () -> command.execute(model));
    }

    @Test
    public void execute_duplicateAttendance() throws Exception {
        Attendance attendance = new Attendance("2024-10-21");
        Person unmarkedPerson = new PersonBuilder().withTelegram(ALICE.getTelegram().value)
                .isMember(true).withName(ALICE.getName().fullName).build();

        List<Person> membersAttendanceUnmarked = List.of(unmarkedPerson);
        model.setPerson(ALICE, unmarkedPerson);

        UnmarkAttendanceCommand command = new UnmarkAttendanceCommand(Arrays.asList(ALICE.getTelegram()), attendance);

        CommandResult result = command.execute(model);

        List<String> alreadyUnarkedList = displayMembers(membersAttendanceUnmarked);
        assertEquals(String.format(MESSAGE_CONTAIN_UNMARKED_MEMBER, alreadyUnarkedList),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_unmarkDuplicateAttendance() throws Exception {
        Attendance attendance = new Attendance("2024-10-21");
        Person unmarkedPerson = new PersonBuilder().withTelegram(ALICE.getTelegram().value)
                .isMember(true).withName(ALICE.getName().fullName).build();
        Person markedPerson = new PersonBuilder().withTelegram(BENSON.getTelegram().value)
                .isMember(true).withName(BENSON.getName().fullName).withAttendance(attendance).build();

        List<Person> membersAttendanceUnmarked = List.of(unmarkedPerson);
        model.setPerson(ALICE, unmarkedPerson);
        model.setPerson(BENSON, markedPerson);

        UnmarkAttendanceCommand command = new UnmarkAttendanceCommand(
                Arrays.asList(ALICE.getTelegram(), BENSON.getTelegram()), attendance);

        CommandResult result = command.execute(model);

        List<String> alreadyUnmarkedList = displayMembers(membersAttendanceUnmarked);
        List<String> unmarkSuccessList = displayMembers(List.of(BENSON));
        String expectedMessage = String.format(
                MESSAGE_UNMARK_MEMBER_SUCCESS, attendance, unmarkSuccessList)
                + String.format(MESSAGE_CONTAIN_UNMARKED_MEMBER, alreadyUnmarkedList);

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void equals() {
        Telegram telegramAlice = new Telegram("aliceTelegram");
        Telegram telegramBob = new Telegram("bobTelegram");
        Attendance attendance = new Attendance("2024-10-21");

        UnmarkAttendanceCommand commandAlice = new UnmarkAttendanceCommand(Arrays.asList(telegramAlice), attendance);
        UnmarkAttendanceCommand commandBob = new UnmarkAttendanceCommand(Arrays.asList(telegramBob), attendance);

        // same object -> returns true
        assertTrue(commandAlice.equals(commandAlice));

        // same values -> returns true
        UnmarkAttendanceCommand commandAliceCopy = new UnmarkAttendanceCommand(
                Arrays.asList(telegramAlice), attendance);
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
        UnmarkAttendanceCommand command = new UnmarkAttendanceCommand(Arrays.asList(telegramAlice), attendance);
        String expected = UnmarkAttendanceCommand.class.getCanonicalName()
                + "{telegrams=[aliceTelegram], attendance=" + attendance + "}";
        assertEquals(expected, command.toString());
    }
}
