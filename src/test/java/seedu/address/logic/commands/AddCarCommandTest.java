package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.car.Car;
import seedu.address.model.car.CarMake;
import seedu.address.model.car.CarModel;
import seedu.address.model.car.Vin;
import seedu.address.model.car.Vrn;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddCarCommandTest {

    @Test
    public void addCarCommand_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCarCommand(null, null));
    }

    @Test
    public void execute_addCarToEligiblePerson_success() throws Exception {
        // Create a valid person without a car
        Person validPerson = new PersonBuilder().build();
        Car validCar = new Car(new Vrn("SJH9514P"), new Vin("KMHGH4JH3EU073801"),
                new CarMake("Toyota"), new CarModel("Corolla"));

        // Create a model stub that contains the person
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        modelStub.addPerson(validPerson); // Add person to the stub model

        // Create the AddCarCommand to add the car to the first person
        AddCarCommand addCarCommand = new AddCarCommand(Index.fromOneBased(1), validCar);

        // Build the expected message after adding the car
        // The value 1 is passed to the string here as we know that the test only has 1 person inside the test stub.
        String expectedMessage = String.format(AddCarCommand.MESSAGE_ADD_CAR_SUCCESS,
                "1", validCar.getVrn(), validCar.getVin(),
                validCar.getCarMake(), validCar.getCarModel());

        // Execute the command and capture the result
        CommandResult commandResult = addCarCommand.execute(modelStub);

        assertEquals(String.format(AddCarCommand.MESSAGE_ADD_CAR_SUCCESS, validPerson.getName(), validCar.getVrn()),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void person_alreadyHasCar_throwsCommandException() {

        Person personWithCar = new PersonBuilder().withCar("SGX1234B", "KMHGH4JH3EU073801",
                "Toyota", "Corolla").build();

        Car newCar2 = new Car(new Vrn("SH8942L"), new Vin("KMHGH4JH3EU073802"),
                new CarMake("Honda"), new CarModel("Civic"));
        AddCarCommand addCarCommand = new AddCarCommand(Index.fromOneBased(1), newCar2);
        ModelStubWithPerson modelStub = new ModelStubWithPerson(personWithCar);

        assertThrows(CommandException.class,
                AddCarCommand.MESSAGE_USER_ALREADY_HAS_CAR, () -> addCarCommand.execute(modelStub));
    }

    @Test
    public void execute_carAlreadyExists_throwsCommandException() {
        // Create a person without a car
        Person validPerson = new PersonBuilder().build();

        // Create a car and add it to the model stub
        Car existingCar = new Car(new Vrn("SGX1234B"), new Vin("KMHGH4JH3EU073801"),
                new CarMake("Toyota"), new CarModel("Corolla"));

        // Stub model that contains the person and already has the car
        ModelStubWithPersonAndCar modelStub = new ModelStubWithPersonAndCar(validPerson, existingCar);

        // Attempt to add the same car to the same person
        AddCarCommand addCarCommand = new AddCarCommand(Index.fromOneBased(1), existingCar);

        assertThrows(CommandException.class,
                AddCarCommand.MESSAGE_SAME_CAR_ALREADY_EXISTS, () -> addCarCommand.execute(modelStub));
    }


    /**
     * A default model stub that has all of its unused methods returning an error.
     * Taken from AddClientCommandTest.java
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
    private class ModelStubWithPerson extends AddCarCommandTest.ModelStub {
        private final Person person;

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
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends AddCarCommandTest.ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return javafx.collections.FXCollections.observableArrayList(personsAdded);
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            requireNonNull(target);
            requireNonNull(editedPerson);

            // Find the index of the target person and replace it with the edited person
            int index = personsAdded.indexOf(target);
            if (index == -1) {
                throw new AssertionError("Target person not found in personsAdded list.");
            }

            personsAdded.set(index, editedPerson);
        }
    }

    /**
     * A Model stub that contains a single person and a single car.
     */
    private class ModelStubWithPersonAndCar extends AddCarCommandTest.ModelStub {
        private final Person person;
        private final Car car;

        ModelStubWithPersonAndCar(Person person, Car car) {
            requireNonNull(person);
            requireNonNull(car);
            this.person = person;
            this.car = car;
        }

        @Override
        public boolean hasCar(Car car) {
            requireNonNull(car);
            return this.car.isSameCar(car);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return javafx.collections.FXCollections.observableArrayList(person);
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
