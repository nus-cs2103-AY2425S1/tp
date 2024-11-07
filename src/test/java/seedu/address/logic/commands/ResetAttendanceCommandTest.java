package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.getParentOnlyAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.AttendanceCount;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.testutil.StudentBuilder;

public class ResetAttendanceCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private Model parentsOnlyModel = new ModelManager(getParentOnlyAddressBook(), new UserPrefs());


    @Test
    public void execute_unfilteredListWithStudents_success() {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        for (Person p : expectedModel.getFilteredPersonList()) {
            Person personWithResetAttendance = new Person(p.getName(), p.getRole(),
                    p.getPhone(), p.getEmail(), p.getAddress(), p.getTags(), new AttendanceCount("0"));
            expectedModel.setPerson(p, personWithResetAttendance);
        }
        ResetAttendanceCommand resetAttendanceCommandCommand = new ResetAttendanceCommand();

        String expectedMessage = String.format(ResetAttendanceCommand.MESSAGE_RESET_ATTENDANCE_SUCCESS,
                "Alice Pauline, Benson Meier, "
                + "Carl Kurz, Elle Meyer, Fiona Kunz");

        assertCommandSuccess(resetAttendanceCommandCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_unfilteredListWithoutStudents_throwsCommandException() {
        BatchMarkCommand batchMarkCommand = new BatchMarkCommand();
        assertCommandFailure(batchMarkCommand, parentsOnlyModel, ResetAttendanceCommand.MESSAGE_BATCH_MARK_NO_STUDENT_LIST);
    }

    @Test
    public void execute_filteredListWithStudents_success() {
        String expectedMessage = String.format(ResetAttendanceCommand.MESSAGE_RESET_ATTENDANCE_SUCCESS,
                "Alice Pauline");
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate("Alice");
        model.updateFilteredPersonList(predicate);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Student newAlice = new StudentBuilder().withName("Alice Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
                .withPhone("94351253").withRole("student")
                .withTags("friends").withAttendanceCount("0").build();
        expectedModel.setPerson(ALICE, newAlice);
        ResetAttendanceCommand resetAttendanceCommandCommand = new ResetAttendanceCommand();
        assertCommandSuccess(resetAttendanceCommandCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_filteredListWithoutStudents_throwsCommandException() {
        Model model = new ModelManager(getParentOnlyAddressBook(), new UserPrefs());
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate("Carl");
        model.updateFilteredPersonList(predicate);
        ResetAttendanceCommand resetAttendanceCommand = new ResetAttendanceCommand();
        assertCommandFailure(resetAttendanceCommand, model, ResetAttendanceCommand.MESSAGE_BATCH_MARK_NO_STUDENT_LIST);
    }

    @Test
    public void createNewPersonWithZeroAttendance_success() {
        Student newAmy = ResetAttendanceCommand.createNewStudentWithZeroAttendance(AMY);
        assertNotEquals(new AttendanceCount("0"), AMY.getAttendanceCount());
        assertEquals(new AttendanceCount("0"), newAmy.getAttendanceCount());

    }
}
