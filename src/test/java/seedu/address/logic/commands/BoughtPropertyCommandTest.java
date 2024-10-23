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
import seedu.address.testutil.PropertyToBuyBuilder;

import java.util.Optional;

public class BoughtPropertyCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//    private final Person testPerson = new PersonBuilder(BILL).build();
    private final Property testProperty = new PropertyToBuyBuilder().build();
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
            .getNumberOfPropertiesBought());

    private final Index differentPropertyIndex = Index.fromOneBased(model
            .getFilteredPersonList()
            .get(differentPersonIndex.getZeroBased())
            .getNumberOfPropertiesBought());

    private final Property property = model
            .getFilteredPersonList()
            .get(personIndex.getZeroBased())
            .getListOfPropertiesBought()
            .get(propertyIndex.getZeroBased());
    private final Property differentProperty = model
            .getFilteredPersonList()
            .get(differentPersonIndex.getZeroBased())
            .getListOfPropertiesBought()
            .get(differentPropertyIndex.getZeroBased());


    private final Optional<Price> actualPrice = Optional.ofNullable(model
            .getFilteredPersonList()
            .get(personIndex.getZeroBased())
            .getListOfPropertiesBought()
            .get(propertyIndex.getZeroBased())
            .getPrice());
    private final Optional<Price> differentActualPrice = Optional.ofNullable(model
            .getFilteredPersonList()
            .get(differentPersonIndex.getZeroBased())
            .getListOfPropertiesBought()
            .get(differentPropertyIndex.getZeroBased())
            .getActualPrice());

    @Test
    public void execute_validModel_success() throws Exception {
        BoughtPropertyCommand BoughtCommand = new BoughtPropertyCommand(personIndex, propertyIndex, actualPrice);

        Property updatedProperty = ALICE.getBoughtProperty(personIndex, actualPrice);
        String expectedMessage = String.format(BoughtPropertyCommand.MESSAGE_SUCCESS,
                Messages.formatProperty(updatedProperty));

        Person expectedPerson = new PersonBuilder(ALICE).withPropertyBought(updatedProperty).withBuyProperty().build();

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(ALICE, expectedPerson);

        assertCommandSuccess(BoughtCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void equals() {
        BoughtPropertyCommand standardCommand = new BoughtPropertyCommand(
                personIndex, propertyIndex, actualPrice);
        BoughtPropertyCommand commandWithDifferentPersonIndex = new BoughtPropertyCommand(
                differentPersonIndex, propertyIndex, actualPrice);
        BoughtPropertyCommand commandWithDifferentPropertyIndex = new BoughtPropertyCommand(
                personIndex, differentPropertyIndex, actualPrice);
        BoughtPropertyCommand commandWithDifferentActualPrice = new BoughtPropertyCommand(
                personIndex, propertyIndex, differentActualPrice);

        assertEquals(standardCommand, standardCommand);

        assertEquals(standardCommand, new BoughtPropertyCommand(personIndex, propertyIndex, actualPrice));

        assertNotEquals(standardCommand, commandWithDifferentPersonIndex);

        assertNotEquals(standardCommand, commandWithDifferentPropertyIndex);

        assertNotEquals(standardCommand, commandWithDifferentActualPrice);
    }

    @Test
    public void execute_invalidPropertyIndex() throws CommandException {
        BoughtPropertyCommand commandWithOutOfBoundsIndex = new BoughtPropertyCommand(personIndex,
                outOfBoundsIndex, actualPrice);
        assertThrows(CommandException.class, () -> commandWithOutOfBoundsIndex.execute(model),
                Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndex() throws CommandException {
        BoughtPropertyCommand commandWithOutOfBoundsIndex = new BoughtPropertyCommand(outOfBoundsIndex,
                propertyIndex, actualPrice);
        assertThrows(CommandException.class, () -> commandWithOutOfBoundsIndex.execute(model),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

}
