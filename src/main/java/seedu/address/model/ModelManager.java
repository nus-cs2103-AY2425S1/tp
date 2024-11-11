package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.link.Link;
import seedu.address.model.owner.Owner;
import seedu.address.model.pet.Pet;

/**
 * Represents the in-memory model of PawPatrol data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final PawPatrol pawPatrol;
    private final UserPrefs userPrefs;
    private final FilteredList<Owner> filteredOwners;
    private final FilteredList<Pet> filteredPets;

    private final FilteredList<Link> filteredLinks;

    /**
     * Initializes a ModelManager with the given pawPatrol and userPrefs.
     */
    public ModelManager(ReadOnlyPawPatrol pawPatrol, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(pawPatrol, userPrefs);

        logger.fine("Initializing with PawPatrol: " + pawPatrol + " and user prefs " + userPrefs);

        this.pawPatrol = new PawPatrol(pawPatrol);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredOwners = new FilteredList<>(this.pawPatrol.getOwnerList());
        filteredPets = new FilteredList<>(this.pawPatrol.getPetList());
        filteredLinks = new FilteredList<>(this.pawPatrol.getLinkList());
    }

    public ModelManager() {
        this(new PawPatrol(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getPawPatrolFilePath() {
        return userPrefs.getPawPatrolFilePath();
    }

    @Override
    public void setPawPatrolPath(Path pawPatrolFilePath) {
        requireNonNull(pawPatrolFilePath);
        userPrefs.setPawPatrolFilePath(pawPatrolFilePath);
    }

    //=========== PawPatrol ================================================================================

    @Override
    public void setPawPatrol(ReadOnlyPawPatrol pawPatrol) {
        this.pawPatrol.resetData(pawPatrol);
    }

    @Override
    public ReadOnlyPawPatrol getPawPatrol() {
        return pawPatrol;
    }

    @Override
    public boolean hasOwner(Owner owner) {
        requireNonNull(owner);
        return pawPatrol.hasOwner(owner);
    }

    @Override
    public boolean hasPet(Pet pet) {
        requireNonNull(pet);
        return pawPatrol.hasPet(pet);
    }

    @Override
    public boolean hasLink(Link link) {
        requireNonNull(link);
        return pawPatrol.hasLink(link);
    }

    @Override
    public void deleteOwner(Owner target) {
        pawPatrol.removeOwner(target);
    }

    @Override
    public void deletePet(Pet target) {
        pawPatrol.removePet(target);
    }

    @Override
    public void sortPets() {
        pawPatrol.sortPets();
    }

    @Override
    public void sortOwners() {
        pawPatrol.sortOwners();
    }

    @Override
    public void deleteLink(Link link) {
        pawPatrol.removeLink(link);
    }

    @Override
    public void addOwner(Owner owner) {
        pawPatrol.addOwner(owner);
    }

    @Override
    public void addPet(Pet pet) {
        pawPatrol.addPet(pet);
    }

    @Override
    public void addLink(Link link) {
        pawPatrol.addLink(link);
    }

    @Override
    public void setOwner(Owner target, Owner editedOwner) {
        requireAllNonNull(target, editedOwner);

        pawPatrol.setOwner(target, editedOwner);
    }

    @Override
    public void setPet(Pet target, Pet editedPet) {
        requireAllNonNull(target, editedPet);

        pawPatrol.setPet(target, editedPet);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Owner} backed by the internal list of
     * {@code versionedPawPatrol}
     */
    @Override
    public ObservableList<Owner> getFilteredOwnerList() {
        return filteredOwners;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Pet} backed by the internal list of
     * {@code versionedPawPatrol}
     */
    @Override
    public ObservableList<Pet> getFilteredPetList() {
        return filteredPets;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Pet} backed by the internal list of
     * {@code versionedPawPatrol}
     */
    @Override
    public ObservableList<Link> getFilteredLinkList() {
        return filteredLinks;
    }

    /**
     * Returns current owner predicate
     */
    @Override
    public Predicate<? super Owner> getCurrentOwnerPredicate() {
        if (filteredOwners.getPredicate() == null) {
            return PREDICATE_SHOW_ALL_OWNERS;
        }
        return filteredOwners.getPredicate();
    }

    /**
     * Returns current pet predicate
     */
    @Override
    public Predicate<? super Pet> getCurrentPetPredicate() {
        if (filteredPets.getPredicate() == null) {
            return PREDICATE_SHOW_ALL_PETS;
        }
        return filteredPets.getPredicate();
    }

    /**
     * Returns an unmodifiable view of the list of {@code Owner} backed by the internal list of
     * {@code versionedPawPatrol}
     */
    @Override
    public void updateFilteredOwnerList(Predicate<? super Owner> predicate) {
        requireNonNull(predicate);
        filteredOwners.setPredicate(predicate);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Pet} backed by the internal list of
     * {@code versionedPawPatrol}
     */
    @Override
    public void updateFilteredPetList(Predicate<? super Pet> predicate) {
        requireNonNull(predicate);
        filteredPets.setPredicate(predicate);
    }

    @Override
    public void deleteLinksWithId(String id) {
        pawPatrol.deleteLinksWithId(id);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;

        return pawPatrol.equals(otherModelManager.pawPatrol)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredOwners.equals(otherModelManager.filteredOwners)
                && filteredPets.equals(otherModelManager.filteredPets);
    }

}
