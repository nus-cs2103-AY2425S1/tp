package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.AbsentDate;
import seedu.address.model.person.AbsentReason;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddAttendanceTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addAttendanceUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        HashMap<AbsentDate, AbsentReason> attendances = new HashMap<>();
        attendances.put(new AbsentDate("10-12-2024"), new AbsentReason("Sick"));
        Person editedPerson = new PersonBuilder(firstPerson)
                .withAttendance(attendances)
                .build();
        AddAttendanceCommand addAttendanceCommand = new AddAttendanceCommand(INDEX_FIRST_PERSON,
                new AbsentDate("10-12-2024"), new AbsentReason("Sick"));
        String expectedMessage = String.format(AddAttendanceCommand.MESSAGE_ADD_ATTENDANCE_SUCCESS,
                Messages.format(editedPerson));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        assertCommandSuccess(addAttendanceCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Creates a new attendance map based on the provided list of absent dates.
     *
     *
     * @param attendances A HashMap containing existing attendances, where the key is an
     *                    AbsentDate and the value is an AbsentReason. This map is used
     *                    to fetch the reasons for the dates present in the absentDateList.
     * @param absentDateList A List of AbsentDate objects. This list should contain the
     *                       dates for which attendance records are needed. The method
     *                       processes all dates in the list starting from the second one.
     * @return A HashMap containing the absent dates (from the provided list, starting
     *         from the second element) as keys and their corresponding absent reasons
     *         as values. If the provided list contains one or no dates, an empty map is
     *         returned.
     */
    public HashMap<AbsentDate, AbsentReason> createAttendances(HashMap<AbsentDate, AbsentReason> attendances,
                                                               List<AbsentDate> absentDateList) {
        HashMap<AbsentDate, AbsentReason> newAttendances = new HashMap<>();
        if (absentDateList.size() <= 1) {
            return newAttendances;
        }

        for (int i = 1; i < absentDateList.size(); i++) {
            newAttendances.put(absentDateList.get(i), attendances.get(absentDateList.get(i)));
        }
        return newAttendances;
    }

    @Test
    public void execute_deleteAttendanceUnfilteredList_success() {
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        HashMap<AbsentDate, AbsentReason> attendances = secondPerson.getAttendances();

        List<AbsentDate> absentDateList = new ArrayList<>(attendances.keySet());
        HashMap<AbsentDate, AbsentReason> newAttendances = createAttendances(attendances, absentDateList);
        Person editedPerson = new PersonBuilder(secondPerson).withAttendance(newAttendances).build();

        AddAttendanceCommand deleteAttendanceCommand = new AddAttendanceCommand(INDEX_SECOND_PERSON,
                absentDateList.get(0), new AbsentReason(""));
        String expectedMessage = String.format(AddAttendanceCommand.MESSAGE_DELETE_ATTENDANCE_SUCCESS,
                Messages.format(editedPerson));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(secondPerson, editedPerson);

        assertCommandSuccess(deleteAttendanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        HashMap<AbsentDate, AbsentReason> attendances = new HashMap<>();
        attendances.put(new AbsentDate("10-12-2024"), new AbsentReason("Sick"));
        Person editedPerson = new PersonBuilder(firstPerson)
                .withAttendance(attendances).build();
        AddAttendanceCommand addAttendanceCommand = new AddAttendanceCommand(INDEX_FIRST_PERSON,
                new AbsentDate("10-12-2024"), new AbsentReason("Sick"));
        String expectedMessage = String.format(AddAttendanceCommand.MESSAGE_ADD_ATTENDANCE_SUCCESS,
                Messages.format(editedPerson));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        assertCommandSuccess(addAttendanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddAttendanceCommand addAttendanceCommand = new AddAttendanceCommand(outOfBoundIndex,
                new AbsentDate("10-12-2024"), new AbsentReason("Sick"));
        assertCommandFailure(addAttendanceCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        AddAttendanceCommand addAttendanceCommand = new AddAttendanceCommand(outOfBoundIndex,
                new AbsentDate("10-12-2024"), new AbsentReason("Sick"));
        assertCommandFailure(addAttendanceCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final AddAttendanceCommand standardCommand = new AddAttendanceCommand(
                INDEX_FIRST_PERSON, new AbsentDate("10-12-2024"), new AbsentReason("Sick"));

        // same values -> returns true
        AddAttendanceCommand commandWithSameValues = new AddAttendanceCommand(
                INDEX_FIRST_PERSON, new AbsentDate("10-12-2024"), new AbsentReason("Sick"));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different person -> returns false
        assertFalse(standardCommand.equals(new AddAttendanceCommand(INDEX_SECOND_PERSON,
                new AbsentDate("10-12-2024"), new AbsentReason("Sick"))));

        // different absent date -> returns false
        assertFalse(standardCommand.equals(new AddAttendanceCommand(INDEX_FIRST_PERSON,
                new AbsentDate("11-12-2024"), new AbsentReason("Sick"))));

        // different absent reason -> returns false
        assertFalse(standardCommand.equals(new AddAttendanceCommand(INDEX_FIRST_PERSON,
                new AbsentDate("10-12-2024"), new AbsentReason("Holiday"))));
    }
}
