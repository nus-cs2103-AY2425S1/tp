package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BILL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Price;
import seedu.address.model.person.Property;
import seedu.address.testutil.PersonBuilder;

public class SoldPropertyCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Index outOfBoundsIndex = Index.fromOneBased(100000);

    private final Index personIndex = Index.fromZeroBased(model
            .getFilteredPersonList()
            .indexOf(ALICE));
    private final Index differentPersonIndex = Index.fromZeroBased(model
            .getFilteredPersonList()
            .indexOf(BILL));
    private final Index propertyIndex = Index.fromOneBased(model
            .getFilteredPersonList()
            .get(personIndex.getZeroBased())
            .getNumberOfPropertiesSold());

    private final Index differentPropertyIndex = Index.fromOneBased(model
            .getFilteredPersonList()
            .get(differentPersonIndex.getZeroBased())
            .getNumberOfPropertiesSold());

    private final Price actualPrice = model
            .getFilteredPersonList()
            .get(personIndex.getZeroBased())
            .getListOfPropertiesBought()
            .get(propertyIndex.getZeroBased())
            .getActualPrice();
    private final Price differentActualPrice = model
            .getFilteredPersonList()
            .get(differentPersonIndex.getZeroBased())
            .getListOfPropertiesBought()
            .get(differentPropertyIndex.getZeroBased())
            .getActualPrice();

    @Test
    public void execute_validModel_success() throws Exception {
        SoldPropertyCommand soldCommand = new SoldPropertyCommand(personIndex, propertyIndex, actualPrice);

        Property updatedProperty = ALICE.getSoldProperty(personIndex, actualPrice);
        String expectedMessage = String.format(SoldPropertyCommand.MESSAGE_SUCCESS,
                Messages.formatProperty(updatedProperty));

        Person expectedPerson = new PersonBuilder(ALICE).withPropertySold(updatedProperty).withSellProperty().build();

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(ALICE, expectedPerson);

        assertCommandSuccess(soldCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void equals() {
        SoldPropertyCommand standardCommand = new SoldPropertyCommand(
                personIndex, propertyIndex, actualPrice);
        SoldPropertyCommand commandWithDifferentPersonIndex = new SoldPropertyCommand(
                differentPersonIndex, propertyIndex, actualPrice);
        SoldPropertyCommand commandWithDifferentPropertyIndex = new SoldPropertyCommand(
                personIndex, differentPropertyIndex, actualPrice);
        SoldPropertyCommand commandWithDifferentActualPrice = new SoldPropertyCommand(
                personIndex, propertyIndex, differentActualPrice);

        assertEquals(standardCommand, standardCommand);

        assertEquals(standardCommand, new SoldPropertyCommand(personIndex, propertyIndex, actualPrice));

        assertNotEquals(standardCommand, commandWithDifferentPersonIndex);

        assertNotEquals(standardCommand, commandWithDifferentPropertyIndex);

        assertNotEquals(standardCommand, commandWithDifferentActualPrice);
    }

    @Test
    public void execute_invalidPropertyIndex() throws CommandException {
        SoldPropertyCommand commandWithOutOfBoundsIndex = new SoldPropertyCommand(personIndex,
                outOfBoundsIndex, actualPrice);
        assertThrows(CommandException.class, () -> commandWithOutOfBoundsIndex.execute(model),
                Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndex() throws CommandException {
        SoldPropertyCommand commandWithOutOfBoundsIndex = new SoldPropertyCommand(outOfBoundsIndex,
                propertyIndex, actualPrice);
        assertThrows(CommandException.class, () -> commandWithOutOfBoundsIndex.execute(model),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

}
