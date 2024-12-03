package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.StudentBuilder;

public class DeleteAllStudentsCommandTest {

    private DeleteAllStudentsCommand command = new DeleteAllStudentsCommand();

    @Test
    public void execute_deleteSingleStudent_success() {
        Student validStudent = new StudentBuilder().withName("John Ng").withStudentNumber("A0123456L").build();

        ModelStubWithStudent modelStub = new ModelStubWithStudent(validStudent);

        CommandResult commandResult = command.execute(modelStub);

        assertEquals(DeleteAllStudentsCommand.MESSAGE_DELETE_STUDENT_SUCCESS, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_deleteMultipleStudents_success() {
        Student validStudent1 = new StudentBuilder().withName("John Ng").withStudentNumber("A1234567X").build();
        Student validStudent2 = new StudentBuilder().withName("May Ng Zi Wei").withStudentNumber("A0123456Y").build();
        Student validStudent3 = new StudentBuilder().withName("Lynn Han").withStudentNumber("A9876543Z").build();
        Student validStudent4 = new StudentBuilder().withName("Richard").withStudentNumber("A1111111B").build();

        ModelStubWithStudent modelStub = new ModelStubWithStudent(validStudent1, validStudent2,
                validStudent3, validStudent4);

        CommandResult commandResult = command.execute(modelStub);

        assertEquals(DeleteAllStudentsCommand.MESSAGE_DELETE_STUDENT_SUCCESS, commandResult.getFeedbackToUser());
    }

    @Test
    public void undo() {
        Student validStudent1 = new StudentBuilder().withName("John Ng").withStudentNumber("A1234567X").build();
        Student validStudent2 = new StudentBuilder().withName("May Ng Zi Wei").withStudentNumber("A0123456Y").build();
        Student validStudent3 = new StudentBuilder().withName("Lynn Han").withStudentNumber("A9876543Z").build();
        Student validStudent4 = new StudentBuilder().withName("Richard").withStudentNumber("A1111111B").build();
        ModelStubWithStudent modelStub = new ModelStubWithStudent(validStudent1, validStudent2,
                validStudent3, validStudent4);
        command.execute(modelStub);
        command.undo(modelStub);

        ModelStubWithStudent expectedModelStub = new ModelStubWithStudent(validStudent1, validStudent2,
                validStudent3, validStudent4);
        assertEquals(expectedModelStub.getFilteredStudentList(), modelStub.getFilteredStudentList());
    }

    /**
     * A Model stub that contains a single student.
     */
    private class ModelStubWithStudent extends ModelStub {

        private final ObservableList<Student> students = FXCollections.observableArrayList();

        ModelStubWithStudent(Student... students) {
            this.students.addAll(students);
        }

        @Override
        public Student getStudentByName(Name name) {
            requireNonNull(name);
            return students.stream()
                    .filter(student -> student.getName().equals(name))
                    .findFirst()
                    .orElse(null); // Return null if no student is found with this name
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            return students;
        }

        @Override
        public void addStudent(Student student) {
            requireNonNull(student);
            students.add(student);
        }

        @Override
        public boolean hasStudent(Student student) {
            requireNonNull(student);
            return students.stream().anyMatch(student::isSameStudent);
        }

        @Override
        public int deleteStudent(Student target) {
            requireNonNull(target);
            students.remove(target);
            return 1;
        }

        @Override
        public ObservableList<Student> deleteAllStudents() {
            ObservableList<Student> deletedStudents = FXCollections.observableArrayList(students);
            students.clear();
            return deletedStudents;
        }

        @Override
        public void replaceStudentList(ObservableList<Student> studentList) {
            students.setAll(studentList);
        }
    }
}
