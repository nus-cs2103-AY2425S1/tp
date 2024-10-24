package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.pet.Age;
import seedu.address.model.pet.Breed;
import seedu.address.model.pet.Name;
import seedu.address.model.pet.Pet;
import seedu.address.model.pet.Sex;
import seedu.address.model.pet.Species;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing pet in the address book.
 */
public class EditPetCommand extends EditCommand<Pet> {
    public static final String COMMAND_WORD = "edit p";

    public static final String MESSAGE_EDIT_PET_SUCCESS = "Edited Pet: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PET = "This pet already exists in the address book.";

    private final EditPetDescriptor editPetDescriptor;

    /**
     * Constructs an EditPetCommand to edit the specified pet.
     *
     * @param index Index of the pet in the filtered pet list to edit.
     * @param editPetDescriptor Details to edit the pet with.
     */
    public EditPetCommand(Index index, EditPetDescriptor editPetDescriptor) {
        super(index);
        requireNonNull(editPetDescriptor);

        this.editPetDescriptor = new EditPetDescriptor(editPetDescriptor);
    }

    /**
     * Executes the edit pet command.
     *
     * @param model The model in which the command should operate.
     * @return The result of the command execution.
     * @throws CommandException If the index is invalid or the edited pet is a duplicate.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Pet> lastShownList = model.getFilteredPetList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PET_DISPLAYED_INDEX);
        }

        Pet petToEdit = lastShownList.get(index.getZeroBased());
        Pet editedPet = createEditedPet(petToEdit, editPetDescriptor);

        if (!petToEdit.isSamePet(editedPet) && model.hasPet(editedPet)) {
            throw new CommandException(MESSAGE_DUPLICATE_PET);
        }

        model.setPet(petToEdit, editedPet);
        model.updateFilteredPetList(Model.PREDICATE_SHOW_ALL_PETS);
        return new CommandResult(String.format(MESSAGE_EDIT_PET_SUCCESS, Messages.format(editedPet)));
    }

    /**
     * Creates and returns a {@code Pet} with the details of {@code petToEdit}
     * edited with {@code editPetDescriptor}.
     */
    private static Pet createEditedPet(Pet petToEdit, EditPetDescriptor editPetDescriptor) {
        assert petToEdit != null;

        String uniqueId = petToEdit.getUniqueID();
        Name updatedName = editPetDescriptor.getName().orElse(petToEdit.getName());
        Species updatedSpecies = editPetDescriptor.getSpecies().orElse(petToEdit.getSpecies());
        Breed updatedBreed = editPetDescriptor.getBreed().orElse(petToEdit.getBreed());
        Age updatedAge = editPetDescriptor.getAge().orElse(petToEdit.getAge());
        Sex updatedSex = editPetDescriptor.getSex().orElse(petToEdit.getSex());
        Set<Tag> updatedTags = editPetDescriptor.getTags().orElse(petToEdit.getTags());

        return new Pet(uniqueId, updatedName, updatedSpecies, updatedBreed, updatedAge, updatedSex, updatedTags);
    }

    /**
     * Executes the edit pet command.
     *
     * @return The result of the command execution.
     * @throws CommandException If the index is invalid or the edited pet is a duplicate.
     */
    public static class EditPetDescriptor {
        private Name name;
        private Species species;
        private Breed breed;
        private Age age;
        private Sex sex;
        private Set<Tag> tags;

        public EditPetDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPetDescriptor(EditPetDescriptor toCopy) {
            setName(toCopy.name);
            setSpecies(toCopy.species);
            setBreed(toCopy.breed);
            setAge(toCopy.age);
            setSex(toCopy.sex);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, species, breed, age, sex, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setSpecies(Species species) {
            this.species = species;
        }

        public Optional<Species> getSpecies() {
            return Optional.ofNullable(species);
        }

        public void setBreed(Breed breed) {
            this.breed = breed;
        }

        public Optional<Breed> getBreed() {
            return Optional.ofNullable(breed);
        }

        public void setAge(Age age) {
            this.age = age;
        }

        public Optional<Age> getAge() {
            return Optional.ofNullable(age);
        }

        public void setSex(Sex sex) {
            this.sex = sex;
        }

        public Optional<Sex> getSex() {
            return Optional.ofNullable(sex);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPetDescriptor)) {
                return false;
            }

            EditPetDescriptor otherEditPetDescriptor = (EditPetDescriptor) other;
            return Objects.equals(name, otherEditPetDescriptor.name)
                    && Objects.equals(species, otherEditPetDescriptor.species)
                    && Objects.equals(breed, otherEditPetDescriptor.breed)
                    && Objects.equals(age, otherEditPetDescriptor.age)
                    && Objects.equals(sex, otherEditPetDescriptor.sex);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("species", species)
                    .add("breed", breed)
                    .add("age", age)
                    .add("sex", sex)
                    .toString();
        }
    }
}
