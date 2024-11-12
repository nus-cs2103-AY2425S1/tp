package seedu.address.logic.commands.lesson;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.ModelStub;

public class AddLessonCommandTest {

    @Test
    public void constructor_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddLessonCommand(null));
    }

    @Test
    public void execute_lessonAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingLessonAdded modelStub = new ModelStubAcceptingLessonAdded();
        Lesson lesson = new LessonBuilder().build();
        CommandResult commandResult = new AddLessonCommand(lesson).execute(modelStub);

        assertEquals(String.format(AddLessonCommand.MESSAGE_SUCCESS, Messages.format(lesson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(lesson), modelStub.lessonsAdded);
    }

    @Test
    public void equals() {
        Lesson jan1stLesson = new LessonBuilder().withDate("2024-01-01").build();
        Lesson feb2ndLesson = new LessonBuilder().withDate("2024-02-02").build();

        AddLessonCommand addJan1stLesson = new AddLessonCommand(jan1stLesson);
        AddLessonCommand addFeb2ndLesson = new AddLessonCommand(feb2ndLesson);

        assertTrue(addJan1stLesson.equals(addJan1stLesson));
        assertTrue(addJan1stLesson.equals(new AddLessonCommand(jan1stLesson)));
        assertFalse(addJan1stLesson.equals(addFeb2ndLesson));
    }

    @Test
    public void getCommandType_returnsCorrectType() {
        Lesson lesson = new LessonBuilder().build();
        AddLessonCommand addLessonCommand = new AddLessonCommand(lesson);

        assertEquals(AddLessonCommand.COMMAND_TYPE, addLessonCommand.getCommandType());
    }

    private class ModelStubAcceptingLessonAdded extends ModelStub {
        final ArrayList<Lesson> lessonsAdded = new ArrayList<>();

        @Override
        public void addLesson(Lesson lesson) {
            requireNonNull(lesson);
            lessonsAdded.add(lesson);
        }

        @Test
        public void getCommandType_returnsCorrectType() {
            Lesson lesson = new LessonBuilder().build();
            AddLessonCommand addLessonCommand = new AddLessonCommand(lesson);

            assertEquals(AddLessonCommand.COMMAND_TYPE, addLessonCommand.getCommandType());
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    @Test
    public void execute_duplicateLesson_throwsCommandException() {
        Lesson lesson = new LessonBuilder().withDate("2024-01-01").withTime("10:00").build();
        AddLessonCommand addLessonCommand = new AddLessonCommand(lesson);

        ModelStubWithDuplicateLesson modelStub = new ModelStubWithDuplicateLesson(lesson);

        assertThrows(CommandException.class, () -> addLessonCommand.execute(modelStub));
    }

    private class ModelStubWithDuplicateLesson extends ModelStub {
        private final Lesson lesson;

        ModelStubWithDuplicateLesson(Lesson lesson) {
            requireNonNull(lesson);
            this.lesson = lesson;
        }

        @Override
        public void addLesson(Lesson lesson) {
            throw new DuplicateLessonException();
        }

        @Override
        public boolean hasLesson(Lesson lesson) {
            requireNonNull(lesson);
            return this.lesson.isSameLesson(lesson);
        }
    }
}
