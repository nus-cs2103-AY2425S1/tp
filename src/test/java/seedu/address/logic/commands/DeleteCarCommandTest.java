package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.car.Car;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCarCommand}.
 */
public class DeleteCarCommandTest {

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        // Step 1: Create a person with a car
        Person personWithCar = new PersonBuilder().withCar("SGX1234B", "KMHGH4JH3EU073801", "Toyota", "Corolla").build();

        // Step 2: Create a model stub that contains the person
        ModelStubWithPerson modelStub = new ModelStubWithPerson(personWithCar);

        // Step 3: Create the DeleteCarCommand for the first person (index 1)
        DeleteCarCommand deleteCarCommand = new DeleteCarCommand(Index.fromOneBased(1));

        // Step 4: Build the expected message for a successful car deletion
        // The value 1 is passed to the string here as we know that the test only has 1 person inside the test stub.
        Car carToDelete = personWithCar.getCar();
        String expectedMessage = String.format(DeleteCarCommand.MESSAGE_DELETE_CAR_SUCCESS,
                "1", carToDelete.getVrn(), carToDelete.getVin(),
                carToDelete.getCarMake(), carToDelete.getCarModel());

        // Step 5: Execute the command and check for successful deletion
        CommandResult commandResult = deleteCarCommand.execute(modelStub);

        // Step 6: Check the expected result and ensure no car remains
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertTrue(modelStub.getFilteredPersonList().get(0).getCar() == null); // Car should be removed
    }

    @Test
    public void person_hasNoCar_throwsCommandException() {
        // Step 1: Set up a person without a car
        Person personWithoutCar = new PersonBuilder().build();

        // Step 2: Create the model stub that contains the person without a car
        ModelStubWithPerson modelStub = new ModelStubWithPerson(personWithoutCar);

        // Step 3: Create the DeleteCarCommand for the first person (index 1)
        DeleteCarCommand deleteCarCommand = new DeleteCarCommand(Index.fromOneBased(1));

        // Step 4: Expect the exception to be thrown, as the person does not have a car to delete
        assertThrows(CommandException.class,
                DeleteCarCommand.MESSAGE_USER_HAS_NO_CAR, () -> deleteCarCommand.execute(modelStub));
    }

    @Test
    public void execute_userCheckedIn_throwsCommandException() {
        // Step 1: Set up a person who is checked in (isServicing() returns true) and has a car
        Person personCheckedInWithCar = new PersonBuilder()
                .withCar("SGX1234B", "KMHGH4JH3EU073801", "Toyota", "Corolla")
                .build();

        personCheckedInWithCar.setServicing();

        // Step 2: Create the model stub that contains the person checked in with a car
        ModelStubWithPerson modelStub = new ModelStubWithPerson(personCheckedInWithCar);

        // Step 3: Create the DeleteCarCommand for the first person (index 1)
        DeleteCarCommand deleteCarCommand = new DeleteCarCommand(Index.fromOneBased(1));

        // Step 4: Expect the exception to be thrown as the user is checked in and cannot have their car deleted
        assertThrows(CommandException.class,
                DeleteCarCommand.MESSAGE_USER_IS_CHECKED_IN, () -> deleteCarCommand.execute(modelStub));
    }


    /**
     * A default model stub that has all of its unused methods returning an error.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCar(Car car) {
            return false;
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends DeleteCarCommandTest.ModelStub {
        private Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return javafx.collections.FXCollections.observableArrayList(person);
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            requireNonNull(target);
            requireNonNull(editedPerson);

            // Simulate updating the person's car
            if (this.person.isSamePerson(target)) {
                this.person = editedPerson; // Update the person with the edited version (car removed)
            } else {
                throw new AssertionError("Target person not found in the model stub.");
            }
        }
    }

}

