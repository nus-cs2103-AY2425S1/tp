package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Address;
import seedu.address.model.person.Tutee;
import seedu.address.model.person.Tutor;
import seedu.address.testutil.TuteeBuilder;
import seedu.address.testutil.TutorBuilder;

public class AddLessonCommandTest {
    //private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();
    @Test
    public void execute_validParamsUnfilteredList_success() {
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        Tutor tutor = (Tutor) getTypicalPersons().get(0);
        Tutee tutee = (Tutee) getTypicalPersons().get(3);

        model = new ModelManager(new AddressBook(expectedModel.getAddressBook()), new UserPrefs());

        Lesson lesson = new Lesson(tutor, tutee);
        AddLessonCommand addLessonCommand = new AddLessonCommand(Index.fromZeroBased(0),
                Index.fromZeroBased(3));
        String expectedMessage = String.format(AddLessonCommand.MESSAGE_ADD_LESSON_SUCCESS, Messages.format(lesson));
        expectedModel.addLesson(lesson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(addLessonCommand, model, commandHistory, expectedMessage, expectedModel);
    }
}

