package seedu.address.model.pet;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.link.Linkable;
import seedu.address.model.tag.Tag;

/**
 * Represents a Pet in PawPatrol.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Pet implements Linkable {
    private static final String ID_PREFIX = "p";

    // Identity fields
    private final String uniqueId;
    private final Name name;
    private final Species species;
    private final Breed breed;
    private final Age age;
    private final Sex sex;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Pet(Name name, Species species, Breed breed, Age age, Sex sex, Set<Tag> modelTags) {
        requireAllNonNull(name, species, breed, age, sex);
        this.uniqueId = UUID.randomUUID().toString();
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.age = age;
        this.sex = sex;
        this.tags.addAll(tags);
    }

    /**
     * Overloaded constructor to load uniqueIds that are saved in the storage.
     */
    public Pet(String uniqueId, Name name, Species species, Breed breed, Age age, Sex sex, Set<Tag> modelTags) {
        requireAllNonNull(name, species, breed, age, sex);
        this.uniqueId = uniqueId;
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.age = age;
        this.sex = sex;
        this.tags.addAll(tags);
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
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both pets have the same ID.
     * This defines a weaker notion of equality between two pets.
     */
    public boolean isSamePet(Pet otherPet) {
        if (otherPet == this) {
            return true;
        }

        return otherPet != null && otherPet.equals(this);
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
        return name.equals(otherPet.getName())
                && species.equals(otherPet.getSpecies())
                && breed.equals(otherPet.getBreed())
                && age.equals(otherPet.getAge())
                && sex.equals(otherPet.getSex());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, species, breed, age, sex);
    }

    @Override
    public String getUniqueID() {
        return uniqueId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("uniqueId", uniqueId)
                .add("name", name)
                .add("species", species)
                .add("breed", breed)
                .add("age", age)
                .add("sex", sex)
                .toString();
    }
}
