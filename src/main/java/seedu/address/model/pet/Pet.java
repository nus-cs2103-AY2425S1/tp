package seedu.address.model.pet;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Pet in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Pet {

    // Identity fields
    private final Name name;
    private final Species species;
    private final Breed breed;
    private final Age age;
    private final Sex sex;

    /**
     * Every field must be present and not null.
     */
    public Pet(Name name, Species species, Breed breed, Age age, Sex sex) {
        requireAllNonNull(name, species, breed, age, sex);
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.age = age;
        this.sex = sex;
    }

    public Name getName() {
        return name;
    }

    public Species getSpecies() {
        return species;
    }

    public Breed getBreed() {
        return breed;
    }

    public Age getAge() {
        return age;
    }

    public Sex getSex() {
        return sex;
    }


    /**
     * Returns true if both pets have the same ID.
     * This defines a weaker notion of equality between two pets.
     */
    public boolean isSamePet(Pet otherPet) {
        if (otherPet == this) {
            return true;
        }

        return otherPet != null
                && otherPet.getName().equals(getName());
    }

    /**
     * Returns true if both pets have the same identity.
     * This defines a stronger notion of equality between two pets.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Pet)) {
            return false;
        }

        Pet otherPet = (Pet) other;
        return name.equals(otherPet.name)
                && species.equals(otherPet.species)
                && breed.equals(otherPet.breed)
                && age.equals(otherPet.age)
                && sex.equals(otherPet.sex);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, species, breed, age, sex);
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
