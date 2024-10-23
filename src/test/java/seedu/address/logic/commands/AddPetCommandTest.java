package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPets.BELLA;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PawPatrol;
import seedu.address.model.ReadOnlyPawPatrol;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.link.Link;
import seedu.address.model.owner.Owner;
import seedu.address.model.person.Person;
import seedu.address.model.pet.Pet;
import seedu.address.testutil.PetBuilder;

public class AddPetCommandTest {

    @Test
    public void constructor_nullPet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPetCommand(null));
    }

    @Test
    public void execute_petAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPetAdded petModelStub = new ModelStubAcceptingPetAdded();
        Pet validPet = new PetBuilder().build();

        CommandResult commandResult = new AddPetCommand(validPet).execute(petModelStub);

        assertEquals(String.format(AddPetCommand.MESSAGE_SUCCESS, Messages.format(validPet)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPet), petModelStub.petsAdded);
    }

    @Test
    public void execute_duplicatePet_throwsCommandException() {
        Pet validPet = new PetBuilder().build();
        AddPetCommand addPetCommand = new AddPetCommand(validPet);
        PetModelStub petModelStub = new AddPetCommandTest.ModelStubWithPet(validPet);

        assertThrows(CommandException.class,
                AddPetCommand.MESSAGE_DUPLICATE_PET, () -> addPetCommand.execute(petModelStub));
    }

    @Test
    public void equals() {
        Pet bella = new PetBuilder().withName("Bella").build();
        Pet fluffy = new PetBuilder().withName("Fluffy").build();
        AddPetCommand addBellaCommand = new AddPetCommand(bella);
        AddPetCommand addFluffyCommand = new AddPetCommand(fluffy);

        // same object -> returns true
        assertTrue(addBellaCommand.equals(addBellaCommand));

        // same values -> returns true
        AddPetCommand addBellaCommandCopy = new AddPetCommand(bella);
        assertTrue(addBellaCommand.equals(addBellaCommandCopy));

        // different types -> returns false
        assertFalse(addBellaCommand.equals(1));

        // null -> returns false
        assertFalse(addBellaCommand.equals(null));

        // different pet -> returns false
        assertFalse(addBellaCommand.equals(addFluffyCommand));
    }

    @Test
    public void toStringMethod() {
        AddPetCommand addPetCommand = new AddPetCommand(BELLA);
        String expected = AddPetCommand.class.getCanonicalName() + "{toAdd=" + BELLA + "}";
        assertEquals(expected, addPetCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class PetModelStub implements Model {
        @Override
        public void addPet(Pet pet) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {

        }

        @Override
        public void setOwner(Owner target, Owner editedOwner) {

        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {

        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            return null;
        }

        @Override
        public GuiSettings getGuiSettings() {
            return null;
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {

        }

        @Override
        public Path getPawPatrolFilePath() {
            return null;
        }

        @Override
        public void setPawPatrolPath(Path pawPatrolFilePath) {

        }

        @Override
        public void setPawPatrol(ReadOnlyPawPatrol pawPatrol) {

        }

        @Override
        public ReadOnlyPawPatrol getPawPatrol() {
            return null;
        }

        @Override
        public boolean hasPerson(Person person) {
            return false;
        }

        @Override
        public boolean hasOwner(Owner owner) {
            return false;
        }

        @Override
        public boolean hasPet(Pet pet) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasLink(Link link) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {

        }

        @Override
        public void deleteOwner(Owner target) {

        }

        @Override
        public void deletePet(Pet target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteLink(Link link) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteLinksWithId(String id) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {

        }

        @Override
        public void addOwner(Owner owner) {

        }

        @Override
        public void sortOwners() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortPets() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addLink(Link link) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPet(Pet target, Pet editedPet) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return null;
        }

        @Override
        public ObservableList<Owner> getFilteredOwnerList() {
            return null;
        }

        @Override
        public ObservableList<Pet> getFilteredPetList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Link> getFilteredLinkList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {

        }

        @Override
        public void updateFilteredOwnerList(Predicate<Owner> predicate) {

        }

        @Override
        public void updateFilteredPetList(Predicate<Pet> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPetAdded extends AddPetCommandTest.PetModelStub {
        final ArrayList<Pet> petsAdded = new ArrayList<>();

        @Override
        public boolean hasPet(Pet pet) {
            requireNonNull(pet);
            return petsAdded.stream().anyMatch(pet::isSamePet);
        }

        @Override
        public void addPet(Pet pet) {
            requireNonNull(pet);
            petsAdded.add(pet);
        }

        @Override
        public ReadOnlyPawPatrol getPawPatrol() {
            return new PawPatrol();
        }
    }

    /**
     * A Model stub that contains a single pet.
     */
    private class ModelStubWithPet extends AddPetCommandTest.PetModelStub {
        private final Pet pet;

        ModelStubWithPet(Pet pet) {
            requireNonNull(pet);
            this.pet = pet;
        }

        @Override
        public boolean hasPet(Pet pet) {
            requireNonNull(pet);
            return this.pet.isSamePet(pet);
        }
    }
}
