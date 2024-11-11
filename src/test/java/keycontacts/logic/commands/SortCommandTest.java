package keycontacts.logic.commands;

import static keycontacts.testutil.TypicalStudents.getTypicalStudentDirectory;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import keycontacts.model.Model;
import keycontacts.model.ModelManager;
import keycontacts.model.UserPrefs;
import keycontacts.model.student.Student;
import keycontacts.model.student.StudentComparator;
import keycontacts.model.student.StudentComparatorByField;

public class SortCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalStudentDirectory(), new UserPrefs());
    }

    @Test
    public void execute_sortByNameAscending_success() {
        StudentComparatorByField nameComparator = StudentComparator
                .getComparatorForName(new StudentComparator.SortOrder("ASC"));
        StudentComparator comparator = new StudentComparator();
        comparator.addComparator(nameComparator);
        SortCommand sortCommand = new SortCommand(comparator);
        CommandResult result = sortCommand.execute(model);

        assertEquals(SortCommand.MESSAGE_SUCCESS + comparator.getSortDescription(), result.getFeedbackToUser());
        List<Student> sortedStudents = model.getStudentList();
        assertEquals("Alice Pauline", sortedStudents.get(0).getName().fullName);
        assertEquals("Benson Meier", sortedStudents.get(1).getName().fullName);
        assertEquals("Carl Kurz", sortedStudents.get(2).getName().fullName);
    }

    @Test
    public void execute_sortByNameDescending_success() {
        StudentComparatorByField nameComparator = StudentComparator
                .getComparatorForName(new StudentComparator.SortOrder("DESC"));
        StudentComparator comparator = new StudentComparator();
        comparator.addComparator(nameComparator);
        SortCommand sortCommand = new SortCommand(comparator);
        CommandResult result = sortCommand.execute(model);

        assertEquals(SortCommand.MESSAGE_SUCCESS + comparator.getSortDescription(), result.getFeedbackToUser());
        List<Student> sortedStudents = model.getStudentList();
        assertEquals("George Best", sortedStudents.get(0).getName().fullName);
        assertEquals("Fiona Kunz", sortedStudents.get(1).getName().fullName);
        assertEquals("Elle Meyer", sortedStudents.get(2).getName().fullName);
    }

    @Test
    public void execute_sortByAddressAscending_success() {
        StudentComparatorByField addressComparator = StudentComparator
                .getComparatorForAddress(new StudentComparator.SortOrder("ASC"));
        StudentComparator comparator = new StudentComparator();
        comparator.addComparator(addressComparator);
        SortCommand sortCommand = new SortCommand(comparator);
        CommandResult result = sortCommand.execute(model);

        assertEquals(SortCommand.MESSAGE_SUCCESS + comparator.getSortDescription(), result.getFeedbackToUser());
        List<Student> sortedStudents = model.getStudentList();
        assertEquals("10th street", sortedStudents.get(0).getAddress().value);
        assertEquals("123, Jurong West Ave 6, #08-111", sortedStudents.get(1).getAddress().value);
        assertEquals("311, Clementi Ave 2, #02-25", sortedStudents.get(2).getAddress().value);
    }

    @Test
    public void execute_sortByPhoneNumberAscending_success() {
        StudentComparatorByField phoneComparator = StudentComparator
                .getComparatorForPhone(new StudentComparator.SortOrder("ASC"));
        StudentComparator comparator = new StudentComparator();
        comparator.addComparator(phoneComparator);
        SortCommand sortCommand = new SortCommand(comparator);
        CommandResult result = sortCommand.execute(model);

        assertEquals(SortCommand.MESSAGE_SUCCESS + comparator.getSortDescription(), result.getFeedbackToUser());
        List<Student> sortedStudents = model.getStudentList();
        assertEquals("87652533", sortedStudents.get(0).getPhone().value);
        assertEquals("94351253", sortedStudents.get(1).getPhone().value);
        assertEquals("9482224", sortedStudents.get(2).getPhone().value);
    }

    @Test
    public void execute_sortByGradeLevelDescending_success() {
        StudentComparatorByField gradeComparator = StudentComparator
                .getComparatorForGradeLevel(new StudentComparator.SortOrder("DESC"));
        StudentComparator comparator = new StudentComparator();
        comparator.addComparator(gradeComparator);
        SortCommand sortCommand = new SortCommand(comparator);
        CommandResult result = sortCommand.execute(model);

        assertEquals(SortCommand.MESSAGE_SUCCESS + comparator.getSortDescription(), result.getFeedbackToUser());
        List<Student> sortedStudents = model.getStudentList();
        assertEquals("RSL 3", sortedStudents.get(0).getGradeLevel().value);
        assertEquals("RCM 2", sortedStudents.get(1).getGradeLevel().value);
        assertEquals("LCM 3", sortedStudents.get(2).getGradeLevel().value);
    }

    @Test
    public void execute_nullComparator_doesNotThrowException() {
        assertDoesNotThrow(() -> new SortCommand(null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        StudentComparatorByField nameComparator = StudentComparator
                .getComparatorForName(new StudentComparator.SortOrder("ASC"));
        StudentComparator comparator = new StudentComparator();
        comparator.addComparator(nameComparator);
        SortCommand sortCommand = new SortCommand(comparator);
        assertThrows(NullPointerException.class, () -> sortCommand.execute(null));
    }
}
