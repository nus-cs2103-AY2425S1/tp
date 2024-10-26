package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CAR_VIN_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAR_VRN_A;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookSomeWithCars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;


/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private Model modelWithCar;

    @BeforeEach
    public void setUp() {
        modelWithCar = new ModelManager(getTypicalAddressBookSomeWithCars(), new UserPrefs());
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddClientCommand(validPerson), model,
                Messages.formatSuccessMessage(validPerson, AddClientCommand.MESSAGE_SUCCESS),
                        expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddClientCommand(personInList), model,
                AddClientCommand.MESSAGE_DUPLICATE_PERSON);
    }

    // Integration test for adding a person with a car

    @Test
    public void execute_personWithCar_success() {
        Person validPerson = new PersonBuilder()
                .withCar("SH8942L", "11111111111111111", "Toyota", "Corolla").build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddClientCommand(validPerson), model,
                Messages.formatSuccessMessage(validPerson, AddClientCommand.MESSAGE_SUCCESS),
                        expectedModel);
    }

    @Test
    public void execute_personWithCarDuplicate_throwsCommandException() {
        // One Person in the list has a car with the same VIN
        Person personWithCar = new PersonBuilder()
                .withCar("SH8942L", VALID_CAR_VIN_A, "Toyota", "Corolla").build();
        assertCommandFailure(new AddClientCommand(personWithCar), modelWithCar,
                "Car already exists in MATER.");

        // One Person in the list has a car with the same VRN
        personWithCar = new PersonBuilder()
                .withCar(VALID_CAR_VRN_A, "33333333333333333", "Toyota", "Corolla").build();
        assertCommandFailure(new AddClientCommand(personWithCar), modelWithCar,
                "Car already exists in MATER.");
    }

}
