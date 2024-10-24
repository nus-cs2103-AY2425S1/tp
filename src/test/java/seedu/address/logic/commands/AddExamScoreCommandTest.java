package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXAM_FINAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXAM_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXAM_QUIZ;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXAM_SCORE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXAM_SCORE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.exam.Exam;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddExamScoreCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addExamScore_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withExamScore(VALID_EXAM_MIDTERM, VALID_EXAM_SCORE_AMY)
                .build();
        AddExamScoreCommand addExamScoreCommand = new AddExamScoreCommand(INDEX_FIRST_PERSON,
                new Exam(VALID_EXAM_MIDTERM), VALID_EXAM_SCORE_AMY);
        String expectedMessage = String.format(AddExamScoreCommand.MESSAGE_ADDEXAMSCORE_SUCCESS,
                Messages.format(editedPerson));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        assertCommandSuccess(addExamScoreCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddExamScoreCommand addExamScoreCommand = new AddExamScoreCommand(outOfBoundIndex, new Exam(VALID_EXAM_MIDTERM),
                VALID_EXAM_SCORE_AMY);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () -> addExamScoreCommand
                .execute(model));
    }

    @Test
    public void execute_examNotFound_throwsCommandException() {
        AddExamScoreCommand addExamScoreCommand = new AddExamScoreCommand(INDEX_FIRST_PERSON,
                new Exam(VALID_EXAM_QUIZ), VALID_EXAM_SCORE_AMY);
        assertThrows(CommandException.class, AddExamScoreCommand.MESSAGE_EXAM_NOT_FOUND, () -> addExamScoreCommand
                .execute(model));
    }

    @Test
    public void equals() {
        final AddExamScoreCommand standardCommand = new AddExamScoreCommand(INDEX_FIRST_PERSON,
                new Exam(VALID_EXAM_MIDTERM), VALID_EXAM_SCORE_AMY);

        // same values -> returns true
        assertTrue(standardCommand.equals(new AddExamScoreCommand(INDEX_FIRST_PERSON,
                new Exam(VALID_EXAM_MIDTERM), VALID_EXAM_SCORE_AMY)));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddExamScoreCommand(INDEX_SECOND_PERSON,
                new Exam(VALID_EXAM_MIDTERM), VALID_EXAM_SCORE_AMY)));

        // different exam -> returns false
        assertFalse(standardCommand.equals(new AddExamScoreCommand(INDEX_FIRST_PERSON,
                new Exam(VALID_EXAM_FINAL), VALID_EXAM_SCORE_AMY)));

        // different exam score -> returns false
        assertFalse(standardCommand.equals(new AddExamScoreCommand(INDEX_FIRST_PERSON,
                new Exam(VALID_EXAM_MIDTERM), VALID_EXAM_SCORE_BOB)));
    }
}
