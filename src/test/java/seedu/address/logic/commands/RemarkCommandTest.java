package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS;
import static seedu.address.logic.commands.RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS;
import static seedu.address.logic.commands.RemarkCommand.MESSAGE_EDIT_REMARK_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.testutil.PersonBuilder;

// Solution below inspired from https://se-education.org/guides/tutorials/ab3AddRemark.html
public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validAddRemark() {
        final Remark remark = new Remark("Some remark");

        Person firstPerson = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withRemark(remark.toString()).build();

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        String expectedMessage = String.format(MESSAGE_ADD_REMARK_SUCCESS, Messages.format(editedPerson));

        assertCommandSuccess(new RemarkCommand(INDEX_THIRD_PERSON, remark), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validEditRemark() {
        final Remark remark = new Remark("Some remark");

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withRemark(remark.toString()).build();

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        String expectedMessage = String.format(MESSAGE_EDIT_REMARK_SUCCESS, Messages.format(editedPerson));

        assertCommandSuccess(new RemarkCommand(INDEX_FIRST_PERSON, remark), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyRemark() {
        final Remark remark = new Remark("");

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withRemark(remark.toString()).build();

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        String expectedMessage = String.format(MESSAGE_DELETE_REMARK_SUCCESS, Messages.format(editedPerson));

        assertCommandSuccess(new RemarkCommand(INDEX_FIRST_PERSON, remark), model, expectedMessage, expectedModel);
    }

}
