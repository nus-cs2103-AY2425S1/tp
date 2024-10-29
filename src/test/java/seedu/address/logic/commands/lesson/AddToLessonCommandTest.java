package seedu.address.logic.commands.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.StudentBuilder;

public class AddToLessonCommandTest {

    private final Index validIndex = Index.fromOneBased(1);
    private final List<Name> studentNames = List.of(new Name("John Doe"), new Name("Harry Ng"));
    private final List<Name> duplicateStudentNames = List.of(new Name("John Doe"), new Name("John Doe"));

    @Test
    public void execute_addStudentsToLesson_success() throws Exception {
        ModelStubWithLesson modelStub = new ModelStubWithLesson();
        AddToLessonCommand command = new AddToLessonCommand(validIndex, studentNames);

        CommandResult result = command.execute(modelStub);
        Lesson lesson = modelStub.getFilteredLessonList().get(0);

        // After execution, the lesson should have the students added
        assertEquals(2, lesson.getStudents().size());
        assertEquals("John Doe", lesson.getStudents().get(0).getName().fullName);
        assertEquals("Harry Ng", lesson.getStudents().get(1).getName().fullName);
    }

    @Test
    public void execute_invalidLessonIndex_throwsCommandException() {
        ModelStubWithLesson modelStub = new ModelStubWithLesson();
        Index invalidIndex = Index.fromOneBased(5);
        AddToLessonCommand command = new AddToLessonCommand(invalidIndex, studentNames);

        assertThrows(CommandException.class, () -> command.execute(modelStub));
    }

    @Test
    public void execute_studentNotFound_throwsCommandException() {
        ModelStubWithLesson modelStub = new ModelStubWithLesson();
        List<Name> invalidStudentNames = List.of(new Name("Non Existent"));

        AddToLessonCommand command = new AddToLessonCommand(validIndex, invalidStudentNames);

        assertThrows(CommandException.class, () -> command.execute(modelStub));
    }

    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        ModelStubWithLesson modelStub = new ModelStubWithLesson();
        AddToLessonCommand command = new AddToLessonCommand(validIndex, duplicateStudentNames);

        assertThrows(CommandException.class, () -> command.execute(modelStub));
    }

    // Model stub that contains a lesson and can return students by name
    private class ModelStubWithLesson extends ModelStub {

        private final Lesson lesson = new Lesson(
                new seedu.address.model.consultation.Date("2024-10-20"),
                new seedu.address.model.consultation.Time("14:00"));

        private ArrayList<Lesson> lessons;

        ModelStubWithLesson() {
            this.lessons = new ArrayList<>();
            this.lessons.add(lesson);
        }

        @Override
        public void setLesson(Lesson target, Lesson editedLesson) {
            int index = this.lessons.indexOf(target);
            this.lessons.set(index, editedLesson);
        }

        @Override
        public ObservableList<Lesson> getFilteredLessonList() {
            return FXCollections.observableArrayList(lessons);
        }

        @Override
        public Optional<Student> findStudentByName(Name name) {
            // Use StudentBuilder to create valid students
            Student johnDoe = new StudentBuilder()
                    .withName("John Doe")
                    .withPhone("91234567")
                    .withEmail("johndoe@example.com")
                    .withCourses("CS2103T")
                    .build();

            Student harryNg = new StudentBuilder()
                    .withName("Harry Ng")
                    .withPhone("98765432")
                    .withEmail("harryng@example.com")
                    .withCourses("CS2101")
                    .build();

            if (johnDoe.getName().equals(name)) {
                return Optional.of(johnDoe);
            } else if (harryNg.getName().equals(name)) {
                return Optional.of(harryNg);
            }

            return Optional.empty();
        }
    }
}
