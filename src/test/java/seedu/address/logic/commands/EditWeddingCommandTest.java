package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalWeddings.WEDDING_ONE;
import static seedu.address.testutil.TypicalWeddings.WEDDING_THREE;
import static seedu.address.testutil.TypicalWeddings.WEDDING_TWO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditWeddingCommand.EditWeddingDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.EditWeddingDescriptorBuilder;

public class EditWeddingCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        model.addWedding(WEDDING_ONE);
        model.addWedding(WEDDING_TWO);

    }

    @Test
    public void execute_editWedding_success() {

        Wedding editedWedding = new Wedding(WEDDING_ONE.getWeddingName(), WEDDING_TWO.getWeddingDate());
        EditWeddingDescriptor descriptor = new EditWeddingDescriptorBuilder(editedWedding).build();
        EditWeddingCommand editWeddingCommand = new EditWeddingCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditWeddingCommand.MESSAGE_SUCCESS,
                Messages.formatWedding(editedWedding));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setWedding(model.getFilteredWeddingList().get(0), editedWedding);

        assertCommandSuccess(editWeddingCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_editWeddingWithDuplicateName_failure() {
        Wedding editedWedding = new Wedding(WEDDING_ONE.getWeddingName(), WEDDING_ONE.getWeddingDate());
        EditWeddingDescriptor descriptor = new EditWeddingDescriptorBuilder(editedWedding).build();
        EditWeddingCommand editWeddingCommand = new EditWeddingCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editWeddingCommand, model, EditWeddingCommand.MESSAGE_DUPLICATE_WEDDING);
    }

    @Test
    public void equals() {
        Wedding editedWedding1 = new Wedding(WEDDING_THREE.getWeddingName(), WEDDING_THREE.getWeddingDate());
        EditWeddingDescriptor descriptor1 = new EditWeddingDescriptorBuilder(editedWedding1).build();

        Wedding editedWedding2 = new Wedding(WEDDING_TWO.getWeddingName(), WEDDING_THREE.getWeddingDate());
        EditWeddingDescriptor descriptor2 = new EditWeddingDescriptorBuilder(editedWedding1).build();

        EditWeddingCommand editWeddingCommand1 = new EditWeddingCommand(INDEX_FIRST_PERSON, descriptor1);
        EditWeddingCommand editWeddingCommand2 = new EditWeddingCommand(INDEX_SECOND_PERSON, descriptor2);

        // Same object -> returns true
        assertTrue(editWeddingCommand1.equals(editWeddingCommand1));

        // Same values -> returns true
        EditWeddingCommand editWeddingCommand1Copy = new EditWeddingCommand(INDEX_FIRST_PERSON, descriptor1);
        assertTrue(editWeddingCommand1.equals(editWeddingCommand1Copy));

        // Different types -> returns false
        assertTrue(!editWeddingCommand1.equals(1));

        // Different wedding -> returns false
        assertTrue(!editWeddingCommand1.equals(editWeddingCommand2));


    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditWeddingDescriptor editWeddingDescriptor = new EditWeddingDescriptor();
        EditWeddingCommand editWeddingCommand = new EditWeddingCommand(index, editWeddingDescriptor);
        String expected = EditWeddingCommand.class.getCanonicalName() + "{index=" + index + ", editWeddingDescriptor="
                + editWeddingDescriptor + "}";
        assertEquals(expected, editWeddingCommand.toString());

    }

}
