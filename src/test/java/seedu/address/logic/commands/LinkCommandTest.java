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

public class LinkCommandTest {

    @Test
    public void constructor_nullLink_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LinkCommand(INDEX_FIRST_OWNER, null));
        assertThrows(NullPointerException.class, () -> new LinkCommand(null, new HashSet<Index>()));
        assertThrows(NullPointerException.class, () -> new LinkCommand(null, null));

        // At least one pet index must be provided
        assertThrows(IllegalArgumentException.class, () -> new LinkCommand(INDEX_FIRST_OWNER,
            new HashSet<Index>()));
    }

    @Test
    public void execute_linkAcceptedByModel_addSuccess() throws Exception {
        Owner validOwner = new OwnerBuilder().build();
        Pet validPet = new PetBuilder().build();
        Link link = new Link(validOwner, validPet);

        ModelStubAcceptingLinkAdded modelStub = new ModelStubAcceptingLinkAdded(validOwner, validPet);

        Set<Index> linkIndexes = new HashSet<>(Arrays.asList(INDEX_FIRST_PET));
        CommandResult commandResult = new LinkCommand(INDEX_FIRST_OWNER, linkIndexes).execute(modelStub);

        assertEquals(String.format(LinkCommand.MESSAGE_SUCCESS, linkIndexes.size(), Messages.format(validOwner)),
            commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(link), modelStub.linksAdded);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Owner validOwner = new OwnerBuilder().build();
        Pet validPet = new PetBuilder().build();
        Link link = new Link(validOwner, validPet);

        ModelStub modelStub = new ModelStubWithExistingLink(validOwner, validPet, link);

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_OWNER_DISPLAYED_INDEX, () ->
            new LinkCommand(INDEX_THIRD_OWNER, new HashSet<>(Arrays.asList(INDEX_FIRST_PET))).execute(modelStub));

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PET_DISPLAYED_INDEX, () ->
            new LinkCommand(INDEX_FIRST_OWNER, new HashSet<>(Arrays.asList(INDEX_THIRD_PET))).execute(modelStub));
    }

    @Test
    public void execute_duplicateLink_throwsCommandException() {
        Owner validOwner = new OwnerBuilder().build();
        Pet validPet = new PetBuilder().build();
        Link link = new Link(validOwner, validPet);

        ModelStub modelStub = new ModelStubWithExistingLink(validOwner, validPet, link);

        assertThrows(CommandException.class,
            LinkCommand.MESSAGE_DUPLICATE_LINK, () ->
            new LinkCommand(INDEX_FIRST_OWNER, new HashSet<>(Arrays.asList(INDEX_FIRST_PET)))
            .execute(modelStub)
        );
    }

    @Test
    public void equals() {
        LinkCommand linkCommandA = new LinkCommand(INDEX_FIRST_OWNER, new HashSet<>(Arrays.asList(INDEX_FIRST_PET)));
        LinkCommand linkCommandB = new LinkCommand(INDEX_FIRST_OWNER, new HashSet<>(Arrays.asList(INDEX_FIRST_PET)));
        LinkCommand linkCommandC = new LinkCommand(INDEX_FIRST_OWNER, new HashSet<>(Arrays.asList(INDEX_SECOND_PET)));
        LinkCommand linkCommandD = new LinkCommand(INDEX_SECOND_OWNER, new HashSet<>(Arrays.asList(INDEX_FIRST_PET)));

        // same object -> returns true
        assertTrue(linkCommandA.equals(linkCommandA));

        // same values -> returns true
        assertTrue(linkCommandA.equals(linkCommandB));

        // different types -> returns false
        assertFalse(linkCommandA.equals(1));

        // null -> returns false
        assertFalse(linkCommandA.equals(null));

        // different link targets -> returns false
        assertFalse(linkCommandA.equals(linkCommandC));
        assertFalse(linkCommandA.equals(linkCommandD));
        assertFalse(linkCommandC.equals(linkCommandD));
    }

    @Test
    public void toStringMethod() {
        LinkCommand linkCommand = new LinkCommand(INDEX_FIRST_OWNER, new HashSet<>(Arrays.asList(INDEX_FIRST_PET)));
        String expected = LinkCommand.class.getCanonicalName() + "{ownerIndex=" + INDEX_FIRST_OWNER.getOneBased()
            + ", petIndexes=[" + INDEX_FIRST_PET.getOneBased() + "]}";
        assertEquals(expected, linkCommand.toString());
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

    /**
     * A Model stub that always accept the link being added.
     */
    private class ModelStubAcceptingLinkAdded extends ModelStub {
        final ArrayList<Link> linksAdded = new ArrayList<>();
        private final Owner owner;
        private final Pet pet;

        ModelStubAcceptingLinkAdded(Owner owner, Pet pet) {
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
            requireNonNull(link);
            return linksAdded.stream().anyMatch(link::equals);
        }

        @Override
        public void addLink(Link link) {
            requireNonNull(link);
            linksAdded.add(link);
        }
    }
}
