package tuteez.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tuteez.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static tuteez.logic.commands.CommandTestUtil.assertCommandFailure;
import static tuteez.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tuteez.logic.commands.CommandTestUtil.showPersonAtIndex;
import static tuteez.testutil.TypicalIndexes.INDEX_FIRST_LESSON;
import static tuteez.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static tuteez.testutil.TypicalIndexes.INDEX_SECOND_LESSON;
import static tuteez.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static tuteez.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import tuteez.commons.core.index.Index;
import tuteez.model.Model;
import tuteez.model.ModelManager;
import tuteez.model.UserPrefs;
import tuteez.model.person.lesson.Lesson;

public class DeleteLessonCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(outOfBoundIndex,
                Arrays.asList(INDEX_FIRST_LESSON));

        assertCommandFailure(deleteLessonCommand, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(outOfBoundIndex,
                Arrays.asList(INDEX_FIRST_LESSON));

        assertCommandFailure(deleteLessonCommand, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteLessonCommand deleteLessonCommand1 = new DeleteLessonCommand(INDEX_FIRST_PERSON,
                Arrays.asList(INDEX_FIRST_LESSON));
        DeleteLessonCommand deleteLessonCommand2 = new DeleteLessonCommand(INDEX_SECOND_PERSON,
                Arrays.asList(INDEX_FIRST_LESSON));
        DeleteLessonCommand deleteLessonCommand3 = new DeleteLessonCommand(INDEX_FIRST_PERSON,
                Arrays.asList(INDEX_SECOND_LESSON));

        // same object -> returns true
        assertTrue(deleteLessonCommand1.equals(deleteLessonCommand1));

        // same values -> returns true
        DeleteLessonCommand deleteLessonCommand1Copy = new DeleteLessonCommand(INDEX_FIRST_PERSON,
                Arrays.asList(INDEX_FIRST_LESSON));
        assertTrue(deleteLessonCommand1.equals(deleteLessonCommand1Copy));

        // different types -> returns false
        assertFalse(deleteLessonCommand1.equals(1));

        // null -> returns false
        assertFalse(deleteLessonCommand1.equals(null));

        // different person index -> returns false
        assertFalse(deleteLessonCommand1.equals(deleteLessonCommand2));

        // different lesson indices -> returns false
        assertFalse(deleteLessonCommand1.equals(deleteLessonCommand3));
    }

    private String formatLessonList(List<Lesson> lessons) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lessons.size(); i++) {
            sb.append(i + 1).append(". ").append(lessons.get(i).toString()).append("\n");
        }
        return sb.toString();
    }
}
