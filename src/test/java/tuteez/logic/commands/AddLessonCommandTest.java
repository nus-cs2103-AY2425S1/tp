package tuteez.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tuteez.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static tuteez.logic.commands.CommandTestUtil.assertCommandFailure;
import static tuteez.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tuteez.logic.commands.CommandTestUtil.showPersonAtIndex;
import static tuteez.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static tuteez.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static tuteez.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import tuteez.commons.core.index.Index;
import tuteez.model.AddressBook;
import tuteez.model.Model;
import tuteez.model.ModelManager;
import tuteez.model.UserPrefs;
import tuteez.model.person.Person;
import tuteez.model.person.lesson.Lesson;
import tuteez.testutil.PersonBuilder;

public class AddLessonCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addLesson_success() {
        Person personToAddLesson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String[] lessonsToAdd = {"monday 1000-1200", "wednesday 1400-1600"};
        List<Lesson> lessonList = Arrays.asList(new Lesson(lessonsToAdd[0]), new Lesson(lessonsToAdd[1]));

        Person updatedPerson = new PersonBuilder(personToAddLesson).withLessons(lessonsToAdd).build();

        AddLessonCommand addLessonCommand = new AddLessonCommand(INDEX_FIRST_PERSON, lessonList);

        String expectedMessage = String.format(AddLessonCommand.MESSAGE_SUCCESS,
                personToAddLesson.getName(), formatLessonList(lessonList));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToAddLesson, updatedPerson);

        assertCommandSuccess(addLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        List<Lesson> lessonsToAdd = Arrays.asList(new Lesson("Monday 1000-1200"), new Lesson("Wednesday 1400-1600"));

        AddLessonCommand addLessonCommand = new AddLessonCommand(outOfBoundIndex, lessonsToAdd);

        assertCommandFailure(addLessonCommand, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        List<Lesson> lessonsToAdd = Arrays.asList(new Lesson("Monday 1000-1200"), new Lesson("Wednesday 1400-1600"));

        AddLessonCommand addLessonCommand = new AddLessonCommand(outOfBoundIndex, lessonsToAdd);

        assertCommandFailure(addLessonCommand, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_internalLessonClash_throwsCommandException() {
        List<Lesson> clashingLessons = Arrays.asList(
                new Lesson("monday 1000-1200"),
                new Lesson("monday 1100-1300") // Overlaps with first lesson
        );

        AddLessonCommand addLessonCommand = new AddLessonCommand(INDEX_FIRST_PERSON, clashingLessons);

        assertCommandFailure(addLessonCommand, model,
                AddLessonCommand.MESSAGE_NEW_LESSONS_CLASH + "\n" + formatLessonList(clashingLessons));
    }

    @Test
    public void execute_multipleInternalLessonClashes_throwsCommandException() {
        List<Lesson> clashingLessons = Arrays.asList(
                new Lesson("monday 1000-1200"),
                new Lesson("monday 1100-1300"), // Clashes with first
                new Lesson("wednesday 1400-1600"),
                new Lesson("wednesday 1500-1700") // Clashes with third
        );

        AddLessonCommand addLessonCommand = new AddLessonCommand(INDEX_FIRST_PERSON, clashingLessons);

        assertCommandFailure(addLessonCommand, model,
                AddLessonCommand.MESSAGE_NEW_LESSONS_CLASH + "\n" + formatLessonList(clashingLessons));
    }


    @Test
    public void equals() {
        List<Lesson> lessonsToAdd1 = Arrays.asList(new Lesson("Monday 1000-1200"), new Lesson("Wednesday 1400-1600"));
        List<Lesson> lessonsToAdd2 = Arrays.asList(new Lesson("Tuesday 1000-1200"), new Lesson("Thursday 1400-1600"));

        AddLessonCommand addLessonCommand1 = new AddLessonCommand(INDEX_FIRST_PERSON, lessonsToAdd1);
        AddLessonCommand addLessonCommand2 = new AddLessonCommand(INDEX_SECOND_PERSON, lessonsToAdd1);
        AddLessonCommand addLessonCommand3 = new AddLessonCommand(INDEX_FIRST_PERSON, lessonsToAdd2);

        // same object -> returns true
        assertTrue(addLessonCommand1.equals(addLessonCommand1));

        // same values -> returns true
        AddLessonCommand addLessonCommand1Copy = new AddLessonCommand(INDEX_FIRST_PERSON, lessonsToAdd1);
        assertTrue(addLessonCommand1.equals(addLessonCommand1Copy));

        // different types -> returns false
        assertFalse(addLessonCommand1.equals(1));

        // null -> returns false
        assertFalse(addLessonCommand1.equals(null));

        // different person index -> returns false
        assertFalse(addLessonCommand1.equals(addLessonCommand2));

        // different lessons -> returns false
        assertFalse(addLessonCommand1.equals(addLessonCommand3));
    }

    private String formatLessonList(List<Lesson> lessons) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lessons.size(); i++) {
            sb.append(i + 1).append(". ").append(lessons.get(i).toString()).append("\n");
        }
        return sb.toString();
    }
}
