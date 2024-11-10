package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalGoods.getTypicalGoodsReceipts;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;




public class EditCommandIntegrationTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalGoodsReceipts());
    }

    @Test
    public void execute_editPerson_success() {
        Person validPerson = ALICE;
        Person editedPerson = new Person(
                        new Name("John"),
                        validPerson.getPhone(),
                        validPerson.getAddress(),
                        validPerson.getTags());

        EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor(
                Optional.of(new Name("John")),
                Optional.of(validPerson.getPhone()),
                Optional.of(validPerson.getAddress()),
                Optional.of(validPerson.getTags()));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getGoods());
        expectedModel.setPerson(validPerson, editedPerson);

        assertCommandSuccess(new EditCommand(validPerson.getName(), editPersonDescriptor), model,
                String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)),
                expectedModel);
    }
}
