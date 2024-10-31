package seedu.address.logic.commands.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;

public class DeleteLessonCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_success() {
        // Check if the filtered lesson list is non-empty before accessing elements
        if (model.getFilteredLessonList().isEmpty()) {
            throw new AssertionError("The lesson list is empty, test setup might be incorrect.");
        }

        Index firstIndex = Index.fromOneBased(1);
        Lesson lessonToDelete = model.getFilteredLessonList().get(firstIndex.getZeroBased());
        Set<Index> firstIndexSet = new HashSet<>();
        firstIndexSet.add(firstIndex);
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(firstIndexSet);

        String expectedMessage = String.format(DeleteLessonCommand.MESSAGE_DELETE_LESSON_SUCCESS,
                Messages.format(lessonToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteLesson(lessonToDelete);

        assertCommandSuccess(deleteLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleValidIndex_success() {
        // Verify that the filtered lesson list contains enough elements before accessing them
        if (model.getFilteredLessonList().size() < 3) {
            throw new AssertionError("The lesson list has fewer than three elements, test setup might be incorrect.");
        }

        ArrayList<Lesson> lessonsToDelete = new ArrayList<>();
        Index firstIndex = Index.fromOneBased(1);
        Index thirdIndex = Index.fromOneBased(3);
        lessonsToDelete.add(model.getFilteredLessonList().get(firstIndex.getZeroBased()));
        lessonsToDelete.add(model.getFilteredLessonList().get(thirdIndex.getZeroBased()));

        Set<Index> indexSet = new HashSet<>();
        indexSet.add(firstIndex);
        indexSet.add(thirdIndex);
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(indexSet);

        String formattedDeletedLessons = lessonsToDelete.stream()
                .map(Messages::format)
                .collect(Collectors.joining("\n"));
        String expectedMessage = String.format(DeleteLessonCommand.MESSAGE_DELETE_LESSON_SUCCESS,
                formattedDeletedLessons);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        lessonsToDelete.forEach(expectedModel::deleteLesson);

        assertCommandSuccess(deleteLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allInvalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLessonList().size() + 1);
        Index outOfBoundIndex2 = Index.fromOneBased(model.getFilteredLessonList().size() + 2);
        Set<Index> outOfBoundIndexSet = new HashSet<>();
        outOfBoundIndexSet.add(outOfBoundIndex);
        outOfBoundIndexSet.add(outOfBoundIndex2);
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(outOfBoundIndexSet);

        String formattedOutOfBoundIndices = outOfBoundIndex.getOneBased() + ", " + outOfBoundIndex2.getOneBased();

        assertCommandFailure(deleteLessonCommand, model, String.format(Messages.MESSAGE_INVALID_INDEX_SHOWN,
                formattedOutOfBoundIndices));
    }

    @Test
    public void execute_someInvalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLessonList().size() + 1);
        Index outOfBoundIndex2 = Index.fromOneBased(model.getFilteredLessonList().size() + 2);

        Set<Index> outOfBoundIndexSet = new HashSet<>();
        outOfBoundIndexSet.add(outOfBoundIndex);
        outOfBoundIndexSet.add(Index.fromOneBased(1));
        outOfBoundIndexSet.add(outOfBoundIndex2);

        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(outOfBoundIndexSet);

        String formattedOutOfBoundIndices = outOfBoundIndex.getOneBased() + ", " + outOfBoundIndex2.getOneBased();

        assertCommandFailure(deleteLessonCommand, model, String.format(Messages.MESSAGE_INVALID_INDEX_SHOWN,
                formattedOutOfBoundIndices));
    }

    @Test
    public void execute_oneOfOneInvalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLessonList().size() + 1);
        Set<Index> outOfBoundIndexSet = new HashSet<>();
        outOfBoundIndexSet.add(outOfBoundIndex);
        outOfBoundIndexSet.add(Index.fromOneBased(1));
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(outOfBoundIndexSet);

        assertCommandFailure(deleteLessonCommand, model, String.format(Messages.MESSAGE_INVALID_INDEX_SHOWN,
                String.valueOf(outOfBoundIndex.getOneBased())));
    }

    @Test
    public void equals() {
        // create commands
        Index firstIndex = Index.fromOneBased(1);
        Index secondIndex = Index.fromOneBased(2);
        Set<Index> firstIndexSet = new HashSet<>();
        firstIndexSet.add(firstIndex);
        Set<Index> secondIndexSet = new HashSet<>();
        secondIndexSet.add(secondIndex);
        DeleteLessonCommand deleteFirstCommand = new DeleteLessonCommand(firstIndexSet);
        DeleteLessonCommand deleteSecondCommand = new DeleteLessonCommand(secondIndexSet);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        Set<Index> firstIndexSetCopy = new HashSet<>();
        firstIndexSetCopy.add(firstIndex);
        DeleteLessonCommand deleteFirstCommandCopy = new DeleteLessonCommand(firstIndexSetCopy);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different lesson -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        Set<Index> targetIndexSet = new HashSet<>();
        targetIndexSet.add(targetIndex);
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(targetIndexSet);
        String expected = DeleteLessonCommand.class.getCanonicalName() + "{targetIndices=" + targetIndexSet + "}";
        assertEquals(expected, deleteLessonCommand.toString());
    }

    @Test
    public void getCommandTypeMethod() {
        Index targetIndex = Index.fromOneBased(1);
        Set<Index> targetIndexSet = new HashSet<>();
        targetIndexSet.add(targetIndex);
        DeleteLessonCommand deleteCommand = new DeleteLessonCommand(targetIndexSet);
        assertEquals(deleteCommand.getCommandType(), CommandType.DELETELESSON);
    }
}
