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

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.StudentBuilder;



public class BatchMarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model parentsOnlyModel = new ModelManager(getParentOnlyAddressBook(), new UserPrefs());

    @Test
    public void execute_unfilteredListWithStudents_success() {
        String expectedMessage = String.format(BatchMarkCommand.MESSAGE_BATCH_MARK_SUCCESS,
                "Alice Pauline, Benson Meier, "
                + "Carl Kurz, Elle Meyer, Fiona Kunz");
        BatchMarkCommand batchMarkCommand = new BatchMarkCommand();
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Student newAlice = new StudentBuilder().withName("Alice Pauline").withSex("F")
                .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
                .withPhone("94351253").withRole("student")
                .withTags("friends").withAttendanceCount("4").build();
        Student newBenson = new StudentBuilder().withName("Benson Meier").withSex("M")
                .withRole("Student").withAttendanceCount("4")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withEmail("johnd@example.com").withPhone("98765432")
                .withTags("owesMoney", "friends").build();
        Student newCarl = new StudentBuilder().withName("Carl Kurz").withSex("M").withPhone("95352563")
                .withRole("Student").withAttendanceCount("4")
                .withEmail("heinz@example.com").withAddress("wall street").build();
        Person newDaniel = new PersonBuilder().withName("Daniel Meier").withSex("M").withPhone("87652533")
                .withRole("Parent")
                .withEmail("cornelia@example.com").withAddress("10th street")
                .withTags("friends").build();
        Student newElle = new StudentBuilder().withName("Elle Meyer").withSex("F").withPhone("94822244")
                .withRole("student").withAttendanceCount("4")
                .withEmail("cornelia@example.com").withAddress("michegan ave").build();
        Person newCarlMeier = new PersonBuilder().withName("Carl Meier").withSex("M").withPhone("87645253")
                .withRole("Parent")
                .withEmail("cornelia@example.com").withAddress("10th street").withTags("omg").build();
        Student newFiona = new StudentBuilder().withName("Fiona Kunz").withSex("F").withPhone("94824272")
                .withRole("sTuDenT").withAttendanceCount("4")
                .withEmail("lydia@example.com").withAddress("little tokyo").build();
        Person newGeorge = new PersonBuilder().withName("George Best").withSex("M").withPhone("94822442")
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

        assertCommandSuccess(batchMarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unfilteredListWithoutStudents_throwsCommandException() {
        BatchMarkCommand batchMarkCommand = new BatchMarkCommand();
        assertCommandFailure(batchMarkCommand, parentsOnlyModel, BatchMarkCommand.MESSAGE_BATCH_MARK_NO_STUDENT_LIST);
    }

    @Test
    public void execute_filteredListWithStudents_success() {
        String expectedMessage = String.format(BatchMarkCommand.MESSAGE_BATCH_MARK_SUCCESS, "Alice Pauline");
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate("Alice");
        model.updateFilteredPersonList(predicate);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Student newAlice = new StudentBuilder().withName("Alice Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
                .withPhone("94351253").withRole("student")
                .withTags("friends").withAttendanceCount("4").build();
        expectedModel.setPerson(ALICE, newAlice);
        BatchMarkCommand batchMarkCommand = new BatchMarkCommand();
        assertCommandSuccess(batchMarkCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_filteredListWithoutStudents_throwsCommandException() {
        Model model = new ModelManager(getParentOnlyAddressBook(), new UserPrefs());
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate("Carl");
        model.updateFilteredPersonList(predicate);
        BatchMarkCommand batchMarkCommand = new BatchMarkCommand();
        assertCommandFailure(batchMarkCommand, model, BatchMarkCommand.MESSAGE_BATCH_MARK_NO_STUDENT_LIST);
    }

    @Test
    public void createNewStudentWithMarkedAttendance_success() {
        BatchMarkCommand batchMarkCommand = new BatchMarkCommand();
        Student student = new StudentBuilder().withName("Alice Pauline").withSex("F")
                .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
                .withPhone("94351253").withRole("student")
                .withTags("fren").withAttendanceCount("3").build();
        Student markedStudent = batchMarkCommand.createNewStudentWithMarkedAttendance(student);
        Student expectedStudent = new StudentBuilder().withName("Alice Pauline").withSex("F")
                .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
                .withPhone("94351253").withRole("student")
                .withTags("fren").withAttendanceCount("4").build();
        assertEquals(expectedStudent, markedStudent);

    }

    @Test
    public void testFormatMarkedStudents_multipleStudents_returnsFormattedString() throws Exception {
        String expectedString = "Alice Pauline, Benson Meier";
        List<Student> students = new ArrayList();
        students.add(ALICE);
        students.add(BENSON);
        assertEquals(expectedString, BatchMarkCommand.formatMarkedStudents(students));
    }

    @Test
    public void toStringMethod() {
        String expected = BatchMarkCommand.class.getCanonicalName() + "{}";
        BatchMarkCommand batchMarkCommand = new BatchMarkCommand();
        assertEquals(expected, batchMarkCommand.toString());
    }
}
