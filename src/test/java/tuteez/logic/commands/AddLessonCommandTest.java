package tuteez.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tuteez.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static tuteez.logic.commands.CommandTestUtil.VALID_LESSON_MONDAY;
import static tuteez.logic.commands.CommandTestUtil.VALID_LESSON_THURSDAY;
import static tuteez.logic.commands.CommandTestUtil.VALID_LESSON_TUESDAY;
import static tuteez.logic.commands.CommandTestUtil.VALID_LESSON_WEDNESDAY;
import static tuteez.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static tuteez.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static tuteez.logic.commands.CommandTestUtil.assertCommandFailure;
import static tuteez.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tuteez.logic.commands.CommandTestUtil.showPersonAtIndex;
import static tuteez.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static tuteez.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static tuteez.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
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
    private static final String CLASHING_LESSON_MONDAY = "monday 1030-1045";
    private static final String CLASHING_LESSON_WEDNESDAY = "wednesday 1030-1045";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addLesson_success() {
        Person personToAddLesson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String[] lessonsToAdd = {VALID_LESSON_MONDAY, VALID_LESSON_WEDNESDAY};
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
        List<Lesson> lessonsToAdd = Arrays.asList(new Lesson(VALID_LESSON_MONDAY), new Lesson(VALID_LESSON_WEDNESDAY));

        AddLessonCommand addLessonCommand = new AddLessonCommand(outOfBoundIndex, lessonsToAdd);

        assertCommandFailure(addLessonCommand, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        List<Lesson> lessonsToAdd = Arrays.asList(new Lesson(VALID_LESSON_MONDAY), new Lesson(VALID_LESSON_WEDNESDAY));

        AddLessonCommand addLessonCommand = new AddLessonCommand(outOfBoundIndex, lessonsToAdd);

        assertCommandFailure(addLessonCommand, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_internalLessonClash_throwsCommandException() {
        List<Lesson> clashingLessons = Arrays.asList(
                new Lesson(VALID_LESSON_MONDAY),
                new Lesson(CLASHING_LESSON_MONDAY) // Overlaps with first lesson
        );

        AddLessonCommand addLessonCommand = new AddLessonCommand(INDEX_FIRST_PERSON, clashingLessons);

        assertCommandFailure(addLessonCommand, model,
                AddLessonCommand.MESSAGE_NEW_LESSONS_CLASH + "\n" + formatLessonList(clashingLessons));
    }

    @Test
    public void execute_multipleInternalLessonClashes_throwsCommandException() {
        List<Lesson> clashingLessons = Arrays.asList(
                new Lesson(VALID_LESSON_MONDAY),
                new Lesson(CLASHING_LESSON_MONDAY), // Clashes with first
                new Lesson(VALID_LESSON_WEDNESDAY),
                new Lesson(CLASHING_LESSON_WEDNESDAY) // Clashes with third
        );

        AddLessonCommand addLessonCommand = new AddLessonCommand(INDEX_FIRST_PERSON, clashingLessons);

        assertCommandFailure(addLessonCommand, model,
                AddLessonCommand.MESSAGE_NEW_LESSONS_CLASH + "\n" + formatLessonList(clashingLessons));
    }

    @Test
    public void execute_allLessonsClash_throwsCommandException() {
        // Add person with existing lessons
        Person personWithExistingLessons = new PersonBuilder()
                .withName(VALID_NAME_BOB)
                .withLessons(VALID_LESSON_MONDAY, VALID_LESSON_WEDNESDAY)
                .build();
        model.addPerson(personWithExistingLessons);

        // Try to add clashing lessons
        List<Lesson> clashingLessons = Arrays.asList(
                new Lesson(CLASHING_LESSON_MONDAY),
                new Lesson(CLASHING_LESSON_WEDNESDAY)
        );

        AddLessonCommand addLessonCommand = new AddLessonCommand(INDEX_FIRST_PERSON, clashingLessons);

        String expectedFailureMessage = AddLessonCommand.MESSAGE_ALL_LESSONS_CLASH + "\n"
                + formatClashingLessonList(personWithExistingLessons.getName().toString(),
                Arrays.asList(CLASHING_LESSON_MONDAY, CLASHING_LESSON_WEDNESDAY),
                Arrays.asList(VALID_LESSON_MONDAY, VALID_LESSON_WEDNESDAY));

        assertCommandFailure(addLessonCommand, model, expectedFailureMessage);
    }

    @Test
    public void execute_partialSuccess_returnsCorrectMessage() {
        // Add person with existing lesson
        Person personWithExistingLesson = new PersonBuilder()
                .withName(VALID_NAME_BOB)
                .withLessons(VALID_LESSON_MONDAY)
                .build();
        model.addPerson(personWithExistingLesson);

        Person targetPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // Mix of clashing and non-clashing lessons
        List<Lesson> lessonsToAdd = Arrays.asList(
                new Lesson(CLASHING_LESSON_MONDAY),
                new Lesson(VALID_LESSON_TUESDAY),
                new Lesson(VALID_LESSON_WEDNESDAY)
        );

        AddLessonCommand addLessonCommand = new AddLessonCommand(INDEX_FIRST_PERSON, lessonsToAdd);

        Person updatedPerson = new PersonBuilder(targetPerson)
                .withLessons(VALID_LESSON_TUESDAY, VALID_LESSON_WEDNESDAY)
                .build();

        String expectedSuccessful = formatLessonList(Arrays.asList(
                new Lesson(VALID_LESSON_TUESDAY),
                new Lesson(VALID_LESSON_WEDNESDAY)
        ));

        String expectedFailures = formatClashingLessonList(personWithExistingLesson.getName().toString(),
                Arrays.asList(CLASHING_LESSON_MONDAY),
                Arrays.asList(VALID_LESSON_MONDAY));

        String expectedMessage = String.format(AddLessonCommand.MESSAGE_PARTIAL_SUCCESS,
                expectedSuccessful, expectedFailures);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(targetPerson, updatedPerson);

        assertCommandSuccess(addLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_mixedSuccessAndMultipleClashes_formatsMessageCorrectly() {
        // Add multiple persons with lessons
        Person person1 = new PersonBuilder()
                .withName(VALID_NAME_BOB)
                .withLessons(VALID_LESSON_MONDAY)
                .build();
        Person person2 = new PersonBuilder()
                .withName(VALID_NAME_AMY)
                .withLessons(VALID_LESSON_WEDNESDAY)
                .build();
        model.addPerson(person1);
        model.addPerson(person2);

        Person targetPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        List<Lesson> lessonsToAdd = Arrays.asList(
                new Lesson(CLASHING_LESSON_MONDAY), // Clashes with Bob
                new Lesson(VALID_LESSON_TUESDAY), // Will succeed
                new Lesson(CLASHING_LESSON_WEDNESDAY) // Clashes with Amy
        );

        AddLessonCommand addLessonCommand = new AddLessonCommand(INDEX_FIRST_PERSON, lessonsToAdd);

        Person updatedPerson = new PersonBuilder(targetPerson)
                .withLessons(VALID_LESSON_TUESDAY)
                .build();

        String expectedSuccessful = formatLessonList(Arrays.asList(new Lesson(VALID_LESSON_TUESDAY)));

        List<String> failureMessages = new ArrayList<>();
        failureMessages.add(formatSingleClashMessage(CLASHING_LESSON_MONDAY, VALID_NAME_BOB, VALID_LESSON_MONDAY));
        failureMessages.add(formatSingleClashMessage(CLASHING_LESSON_WEDNESDAY, VALID_NAME_AMY,
                VALID_LESSON_WEDNESDAY));

        String expectedFailures = formatFailureMessages(failureMessages);

        String expectedMessage = String.format(AddLessonCommand.MESSAGE_PARTIAL_SUCCESS,
                expectedSuccessful, expectedFailures);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(targetPerson, updatedPerson);

        assertCommandSuccess(addLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        List<Lesson> lessonsToAdd1 = Arrays.asList(new Lesson(VALID_LESSON_MONDAY), new Lesson(VALID_LESSON_WEDNESDAY));
        List<Lesson> lessonsToAdd2 = Arrays.asList(new Lesson(VALID_LESSON_TUESDAY), new Lesson(VALID_LESSON_THURSDAY));

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

    private String formatClashingLessonList(String personName, List<String> clashingLessons,
                                            List<String> existingLessons) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < clashingLessons.size(); i++) {
            if (i > 0) {
                sb.append("\n");
            }
            sb.append("[").append(clashingLessons.get(i).toUpperCase()).append("]")
                    .append(" - Clashes with:")
                    .append("\n")
                    .append("•").append(personName).append(": [")
                    .append(existingLessons.get(i).toUpperCase()).append("] ")
                    .append("\n");
        }
        return sb.toString();
    }

    private String formatFailureMessages(List<String> failureMessages) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < failureMessages.size(); i++) {
            sb.append(failureMessages.get(i));
            if (i < failureMessages.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private String formatSingleClashMessage(String clashingLesson, String personName, String existingLesson) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(clashingLesson.toUpperCase()).append("]")
                .append(" - Clashes with:\n")
                .append("•").append(personName).append(": ")
                .append("[").append(existingLesson.toUpperCase()).append("] ")
                .append("\n");
        return sb.toString();
    }

}
