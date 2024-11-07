package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.CARLMEIER;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getParentOnlyAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.StudentBuilder;

public class BatchUnmarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model parentsOnlyModel = new ModelManager(getParentOnlyAddressBook(), new UserPrefs());

    @Test
    public void execute_unfilteredListWithStudents_success() {
        String expectedMessage = String.format(BatchUnmarkCommand.MESSAGE_BATCH_UNMARK_SUCCESS);
        BatchUnmarkCommand batchUnmarkCommand = new BatchUnmarkCommand();
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Student newAlice = new StudentBuilder().withName("Alice Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
                .withPhone("94351253").withRole("student")
                .withTags("friends").withAttendanceCount("2").build();
        Student newBenson = new StudentBuilder().withName("Benson Meier")
                .withRole("Student").withAttendanceCount("2")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withEmail("johnd@example.com").withPhone("98765432")
                .withTags("owesMoney", "friends").build();
        Student newCarl = new StudentBuilder().withName("Carl Kurz").withPhone("95352563")
                .withRole("Student").withAttendanceCount("2")
                .withEmail("heinz@example.com").withAddress("wall street").build();
        Person newDaniel = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
                .withRole("Parent")
                .withEmail("cornelia@example.com").withAddress("10th street")
                .withTags("friends").build();
        Student newElle = new StudentBuilder().withName("Elle Meyer").withPhone("9482224")
                .withRole("student").withAttendanceCount("2")
                .withEmail("cornelia@example.com").withAddress("michegan ave").build();
        Person newCarlMeier = new PersonBuilder().withName("Carl Meier").withPhone("876452533")
                .withRole("Parent")
                .withEmail("cornelia@example.com").withAddress("10th street").withTags("omg").build();
        Student newFiona = new StudentBuilder().withName("Fiona Kunz").withPhone("9482427")
                .withRole("sTuDenT").withAttendanceCount("2")
                .withEmail("lydia@example.com").withAddress("little tokyo").build();
        Person newGeorge = new PersonBuilder().withName("George Best").withPhone("9482442")
                .withRole("parent")
                .withEmail("anna@example.com").withAddress("4th street").build();
        expectedModel.setPerson(ALICE, newAlice);
        expectedModel.setPerson(BENSON, newBenson);
        expectedModel.setPerson(CARL, newCarl);
        expectedModel.setPerson(DANIEL, newDaniel);
        expectedModel.setPerson(ELLE, newElle);
        expectedModel.setPerson(CARLMEIER, newCarlMeier);
        expectedModel.setPerson(GEORGE, newGeorge);
        expectedModel.setPerson(FIONA, newFiona);

        assertCommandSuccess(batchUnmarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unfilteredListWithoutStudents_throwsCommandException() {
        BatchUnmarkCommand batchUnmarkCommand = new BatchUnmarkCommand();
        assertCommandFailure(batchUnmarkCommand, parentsOnlyModel,
                BatchUnmarkCommand.MESSAGE_BATCH_UNMARK_NO_STUDENT_LIST);
    }

    @Test
    public void execute_filteredListWithStudents_success() {
        String expectedMessage = String.format(BatchUnmarkCommand.MESSAGE_BATCH_UNMARK_SUCCESS);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate("Alice");
        model.updateFilteredPersonList(predicate);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Student newAlice = new StudentBuilder().withName("Alice Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
                .withPhone("94351253").withRole("student")
                .withTags("friends").withAttendanceCount("2").build();
        expectedModel.setPerson(ALICE, newAlice);
        BatchUnmarkCommand batchUnmarkCommand = new BatchUnmarkCommand();
        assertCommandSuccess(batchUnmarkCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_filteredListWithoutStudents_throwsCommandException() {
        Model model = new ModelManager(getParentOnlyAddressBook(), new UserPrefs());
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate("Carl");
        model.updateFilteredPersonList(predicate);
        BatchUnmarkCommand batchUnmarkCommand = new BatchUnmarkCommand();
        assertCommandFailure(batchUnmarkCommand, model,
                BatchUnmarkCommand.MESSAGE_BATCH_UNMARK_NO_STUDENT_LIST);
    }

    @Test
    public void createNewStudentWithUnmarkedAttendance_success() {
        BatchUnmarkCommand batchUnmarkCommand = new BatchUnmarkCommand();
        Student student = new StudentBuilder().withName("Alice Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
                .withPhone("94351253").withRole("student")
                .withTags("fren").withAttendanceCount("3").build();
        Student unmarkedStudent = batchUnmarkCommand.createNewStudentWithUnmarkedAttendance(student);
        Student expectedStudent = new StudentBuilder().withName("Alice Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
                .withPhone("94351253").withRole("student")
                .withTags("fren").withAttendanceCount("2").build();
        assertEquals(expectedStudent, unmarkedStudent);

        Student zeroAttendanceStudent = new StudentBuilder().withName("Paul Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
                .withPhone("94351253").withRole("student")
                .withTags("fren").withAttendanceCount("0").build();
        Student unmarkedZeroAttendanceStudent = batchUnmarkCommand
                .createNewStudentWithUnmarkedAttendance(zeroAttendanceStudent);
        Student expectedZeroAttendanceUnmarkedStudent = new StudentBuilder().withName("Paul Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
                .withPhone("94351253").withRole("student")
                .withTags("fren").withAttendanceCount("0").build();
        assertEquals(expectedZeroAttendanceUnmarkedStudent, unmarkedZeroAttendanceStudent);

    }

    @Test
    public void toStringMethod() {
        String expected = BatchUnmarkCommand.class.getCanonicalName() + "{}";
        BatchUnmarkCommand batchUnmarkCommand = new BatchUnmarkCommand();
        assertEquals(expected, batchUnmarkCommand.toString());
    }
}
