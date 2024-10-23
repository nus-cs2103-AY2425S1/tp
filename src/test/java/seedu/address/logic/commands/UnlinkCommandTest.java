package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OWNER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PET;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_OWNER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PET;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_OWNER;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PET;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyPawPatrol;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.link.Link;
import seedu.address.model.owner.Owner;
import seedu.address.model.person.Person;
import seedu.address.model.pet.Pet;
import seedu.address.testutil.OwnerBuilder;
import seedu.address.testutil.PetBuilder;

public class UnlinkCommandTest {

    @Test
    public void constructor_nullUnlink_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnlinkCommand(null, new HashSet<Index>()));
        assertThrows(NullPointerException.class, () -> new UnlinkCommand(INDEX_FIRST_OWNER, null));
        assertThrows(NullPointerException.class, () -> new UnlinkCommand(null, null));

        // At least one pet index must be provided
        assertThrows(IllegalArgumentException.class, () -> new UnlinkCommand(INDEX_FIRST_OWNER,
                new HashSet<Index>()));
    }

    @Test
    public void execute_unlinkAcceptedByModel_success() throws Exception {
        Owner validOwner = new OwnerBuilder().build();
        Pet validPet = new PetBuilder().build();

        ModelStubAcceptingUnlinks modelStub = new ModelStubAcceptingUnlinks(validOwner, validPet);

        Set<Index> unlinkIndexes = new HashSet<>(Arrays.asList(INDEX_FIRST_PET));
        CommandResult commandResult = new UnlinkCommand(INDEX_FIRST_OWNER, unlinkIndexes).execute(modelStub);

        assertEquals(String.format(UnlinkCommand.MESSAGE_SUCCESS, unlinkIndexes.size(), Messages.format(validOwner)),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Owner validOwner = new OwnerBuilder().build();
        Pet validPet = new PetBuilder().build();
        Link link = new Link(validOwner, validPet);

        ModelStubWithExistingLink modelStub = new ModelStubWithExistingLink(validOwner, validPet, link);

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_OWNER_DISPLAYED_INDEX, () ->
                new UnlinkCommand(INDEX_THIRD_OWNER, new HashSet<>(Arrays.asList(INDEX_FIRST_PET))).execute(modelStub));

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PET_DISPLAYED_INDEX, () ->
                new UnlinkCommand(INDEX_FIRST_OWNER, new HashSet<>(Arrays.asList(INDEX_THIRD_PET))).execute(modelStub));
    }

    @Test
    public void execute_noExistingLink_throwsCommandException() {
        Owner validOwner = new OwnerBuilder().build();
        Pet validPet = new PetBuilder().build();

        ModelStubWithNoExistingLinks modelStub = new ModelStubWithNoExistingLinks(validOwner, validPet);

        assertThrows(CommandException.class, UnlinkCommand.MESSAGE_LINK_NOT_FOUND, () ->
                new UnlinkCommand(INDEX_FIRST_OWNER, new HashSet<>(Arrays.asList(INDEX_FIRST_PET))).execute(modelStub));
    }

    @Test
    public void equals() {
        UnlinkCommand unlinkCommandA = new UnlinkCommand(INDEX_FIRST_OWNER,
                new HashSet<>(Arrays.asList(INDEX_FIRST_PET)));
        UnlinkCommand unlinkCommandB = new UnlinkCommand(INDEX_FIRST_OWNER,
                new HashSet<>(Arrays.asList(INDEX_FIRST_PET)));
        UnlinkCommand unlinkCommandC = new UnlinkCommand(INDEX_FIRST_OWNER,
                new HashSet<>(Arrays.asList(INDEX_SECOND_PET)));
        UnlinkCommand unlinkCommandD = new UnlinkCommand(INDEX_SECOND_OWNER,
                new HashSet<>(Arrays.asList(INDEX_FIRST_PET)));

        // same object -> returns true
        assertTrue(unlinkCommandA.equals(unlinkCommandA));

        // same values -> returns true
        assertTrue(unlinkCommandA.equals(unlinkCommandB));

        // different types -> returns false
        assertFalse(unlinkCommandA.equals(1));

        // null -> returns false
        assertFalse(unlinkCommandA.equals(null));

        // different unlink targets -> returns false
        assertFalse(unlinkCommandA.equals(unlinkCommandC));
        assertFalse(unlinkCommandA.equals(unlinkCommandD));
        assertFalse(unlinkCommandC.equals(unlinkCommandD));
    }

    @Test
    public void toStringMethod() {
        UnlinkCommand unlinkCommand = new UnlinkCommand(INDEX_FIRST_OWNER,
                new HashSet<>(Arrays.asList(INDEX_FIRST_PET)));
        String expected = UnlinkCommand.class.getCanonicalName() + "{ownerIndex=" + INDEX_FIRST_OWNER.getOneBased()
                + ", petIndexes=[" + INDEX_FIRST_PET.getOneBased() + "]}";
        assertEquals(expected, unlinkCommand.toString());
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
     * A Model stub that contains an existing link for duplicate testing.
     */
    private class ModelStubWithExistingLink extends ModelStub {
        private final Link link;
        private final Owner owner;
        private final Pet pet;

        ModelStubWithExistingLink(Owner owner, Pet pet, Link link) {
            requireAllNonNull(owner, pet, link);
            this.owner = owner;
            this.pet = pet;
            this.link = link;
        }

        @Override
        public ObservableList<Owner> getFilteredOwnerList() {
            ObservableList<Owner> ownerList = FXCollections.observableArrayList();
            ownerList.add(owner);
            return ownerList;
        }

        @Override
        public ObservableList<Pet> getFilteredPetList() {
            ObservableList<Pet> petList = FXCollections.observableArrayList();
            petList.add(pet);
            return petList;
        }

        @Override
        public boolean hasLink(Link link) {
            return this.link.equals(link);
        }

    }

    private class ModelStubAcceptingUnlinks extends ModelStub {
        final ArrayList<Link> linksAdded = new ArrayList<>();
        private final Owner owner;
        private final Pet pet;

        ModelStubAcceptingUnlinks(Owner owner, Pet pet) {
            requireAllNonNull(owner, pet);
            this.owner = owner;
            this.pet = pet;
            Link link = new Link(owner, pet);
            linksAdded.add(link);
        }

        @Override
        public ObservableList<Owner> getFilteredOwnerList() {
            ObservableList<Owner> ownerList = FXCollections.observableArrayList();
            ownerList.add(owner);
            return ownerList;
        }

        @Override
        public ObservableList<Pet> getFilteredPetList() {
            ObservableList<Pet> petList = FXCollections.observableArrayList();
            petList.add(pet);
            return petList;
        }

        @Override
        public boolean hasLink(Link link) {
            return linksAdded.stream().anyMatch(link::equals);
        }

        @Override
        public void deleteLink(Link link) {
            requireNonNull(link);
            linksAdded.remove(link);
        }
    }

    private class ModelStubWithNoExistingLinks extends ModelStub {
        final ArrayList<Link> linksAdded = new ArrayList<>();
        private final Owner owner;
        private final Pet pet;

        ModelStubWithNoExistingLinks(Owner owner, Pet pet) {
            requireAllNonNull(owner, pet);
            this.owner = owner;
            this.pet = pet;
        }

        @Override
        public ObservableList<Owner> getFilteredOwnerList() {
            ObservableList<Owner> ownerList = FXCollections.observableArrayList();
            ownerList.add(owner);
            return ownerList;
        }

        @Override
        public ObservableList<Pet> getFilteredPetList() {
            ObservableList<Pet> petList = FXCollections.observableArrayList();
            petList.add(pet);
            return petList;
        }

        @Override
        public boolean hasLink(Link link) {
            return linksAdded.stream().anyMatch(link::equals);
        }

        @Override
        public void deleteLink(Link link) {
            requireNonNull(link);
            linksAdded.remove(link);
        }
    }
}
