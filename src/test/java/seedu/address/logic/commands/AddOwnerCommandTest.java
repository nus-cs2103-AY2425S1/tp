package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOwners.ALICE;

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
import seedu.address.testutil.OwnerBuilder;

public class AddOwnerCommandTest {

    @Test
    public void constructor_nullOwner_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddOwnerCommand(null));
    }

    @Test
    public void execute_ownerAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingOwnerAdded modelStub = new ModelStubAcceptingOwnerAdded();
        Owner validOwner = new OwnerBuilder().build();

        CommandResult commandResult = new AddOwnerCommand(validOwner).execute(modelStub);

        assertEquals(String.format(AddOwnerCommand.MESSAGE_SUCCESS, Messages.format(validOwner)),
            commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validOwner), modelStub.ownersAdded);
    }

    @Test
    public void execute_duplicateOwner_throwsCommandException() {
        Owner validOwner = new OwnerBuilder().build();
        AddOwnerCommand addOwnerCommand = new AddOwnerCommand(validOwner);
        ModelStub modelStub = new ModelStubWithOwner(validOwner);

        assertThrows(CommandException.class,
            AddOwnerCommand.MESSAGE_DUPLICATE_OWNER, () -> addOwnerCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Owner alice = new OwnerBuilder().withName("Alice").build();
        Owner bob = new OwnerBuilder().withName("Bob").build();
        AddOwnerCommand addAliceCommand = new AddOwnerCommand(alice);
        AddOwnerCommand addBobCommand = new AddOwnerCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddOwnerCommand addAliceCommandCopy = new AddOwnerCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddOwnerCommand addCommand = new AddOwnerCommand(ALICE);
        String expected = AddOwnerCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
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
        public Path getPawPatrolFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPawPatrolPath(Path pawPatrolFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addOwner(Owner owner) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPet(Pet pet) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addLink(Link link) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPawPatrol(ReadOnlyPawPatrol pawPatrol) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyPawPatrol getPawPatrol() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasOwner(Owner owner) {
            throw new AssertionError("This method should not be called.");
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
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteOwner(Owner target) {
            throw new AssertionError("This method should not be called.");
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
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setOwner(Owner target, Owner editedOwner) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPet(Pet target, Pet editedPet) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Owner> getFilteredOwnerList() {
            throw new AssertionError("This method should not be called.");
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
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredOwnerList(Predicate<Owner> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPetList(Predicate<Pet> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithOwner extends ModelStub {
        private final Owner owner;

        ModelStubWithOwner(Owner owner) {
            requireNonNull(owner);
            this.owner = owner;
        }

        @Override
        public boolean hasOwner(Owner owner) {
            requireNonNull(owner);
            return this.owner.isSameOwner(owner);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingOwnerAdded extends ModelStub {
        final ArrayList<Owner> ownersAdded = new ArrayList<>();

        @Override
        public boolean hasOwner(Owner owner) {
            requireNonNull(owner);
            return ownersAdded.stream().anyMatch(owner::isSameOwner);
        }

        @Override
        public void addOwner(Owner owner) {
            requireNonNull(owner);
            ownersAdded.add(owner);
        }

        @Override
        public ReadOnlyPawPatrol getPawPatrol() {
            return new PawPatrol();
        }
    }

}
