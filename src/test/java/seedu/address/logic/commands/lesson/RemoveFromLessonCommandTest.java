package seedu.address.logic.commands.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.datetime.Date;
import seedu.address.model.datetime.Time;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.StudentLessonInfo;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.StudentBuilder;

public class RemoveFromLessonCommandTest {

    private final Index validIndex = Index.fromOneBased(1);
    private final List<Name> studentNames = List.of(new Name("Alex Yeoh"), new Name("Harry Ng"));

    @Test
    public void execute_removeStudentsFromLesson_success() throws Exception {
        ModelStubWithLesson modelStub = new ModelStubWithLesson();
        RemoveFromLessonCommand command = new RemoveFromLessonCommand(validIndex, studentNames);

        CommandResult result = command.execute(modelStub);

        Lesson lesson = modelStub.getFilteredLessonList().get(0);
        // Use lesson's date and time to format the success message
        String expectedMessage = String.format(RemoveFromLessonCommand.MESSAGE_REMOVE_FROM_LESSON_SUCCESS,
            lesson.getDate().getValue(), lesson.getTime().getValue());

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidLessonIndex_throwsCommandException() {
        ModelStubWithLesson modelStub = new ModelStubWithLesson();
        Index invalidIndex = Index.fromOneBased(5); // Out of bound index
        RemoveFromLessonCommand command = new RemoveFromLessonCommand(invalidIndex, studentNames);

        assertThrows(CommandException.class, () -> command.execute(modelStub));
    }

    @Test
    public void execute_studentNotFound_throwsCommandException() {
        ModelStubWithLesson modelStub = new ModelStubWithLesson();
        List<Name> invalidStudentNames = List.of(new Name("Non Existent"));

        RemoveFromLessonCommand command = new RemoveFromLessonCommand(validIndex, invalidStudentNames);

        assertThrows(CommandException.class, () -> command.execute(modelStub),
            RemoveFromLessonCommand.MESSAGE_STUDENT_NOT_FOUND);
    }

    // Model stub that contains a lesson and can return students by name
    private class ModelStubWithLesson extends ModelStub {

        private final Student student1 = new StudentBuilder().withName("Alex Yeoh").build();
        private final Student student2 = new StudentBuilder().withName("Harry Ng").build();
        private final Lesson lesson = new Lesson(
                new Date("2024-10-20"),
                new Time("14:00"),
                List.of(new StudentLessonInfo(student1, true, 1),
                        new StudentLessonInfo(student2, false, 0)));

        private final ArrayList<Lesson> lessons;

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
        public java.util.Optional<Student> findStudentByName(Name name) {
            for (Student student : lesson.getStudents()) {
                if (student.getName().equals(name)) {
                    return java.util.Optional.of(student);
                }
            }
            return java.util.Optional.empty();
        }
    }
}
