package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.testutil.PersonBuilder;

import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.commands.RemarkCommand.*;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

public class RemarkCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validRemarks() {
        final Remark remark = new Remark("Some remark");

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withRemark(remark.toString()).build();

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        String expectedMessage = String.format(MESSAGE_ADD_REMARK_SUCCESS, Messages.format(editedPerson));

        assertCommandSuccess(new RemarkCommand(INDEX_FIRST_PERSON, remark), model,
                expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyRemark() {
        final Remark remark = new Remark("");

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withRemark(remark.toString()).build();

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        String expectedMessage = String.format(MESSAGE_DELETE_REMARK_SUCCESS, Messages.format(editedPerson));

        assertCommandSuccess(new RemarkCommand(INDEX_FIRST_PERSON, remark), model,
                expectedMessage, expectedModel);
    }


}
