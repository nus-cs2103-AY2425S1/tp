package seedu.address.model.patient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.AllergySorter;

/**
 * Represents a set of allergies.
 * @see Allergy
 * @see AllergySorter
 */
public class AllergyMap {
    //Set is used because staff searches for allergens that might be used during screenings
    private final Set<Allergy> allergies;

    /**
     * Constructs an empty {@code AllergySet}.
     */
    public AllergyMap() {
        this.allergies = new HashSet<>();
    }

    /**
     * Constructs an {@code AllergySet} with the given allergies.
     */
    public AllergyMap(Set<Allergy> allergies) {
        this.allergies = allergies;
    }

    /**
     * Adds an allergy to the allergy set.
     */
    public void addAllergy(Allergy allergy) {
        allergies.add(allergy);
    }

    /**
     * Deletes an allergy from the set.
     * @param allergy
     */
    public void deleteAllergy(Allergy allergy) {
        allergies.remove(allergy);
    }

    /**
     * Returns an immutable list of allergies sorted by alphabetical order.
     */
    public List<Allergy> getAllergies() {
        List<Allergy> allergiesList = new ArrayList<>(allergies);
        AllergySorter.sortAllergies(allergiesList);
        return Collections.unmodifiableList(allergiesList);
    }

    /**
     * Returns a string representation of all allergies sorted by alphabetical order
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Allergy allergy : getAllergies()) {
            sb.append(allergy.toString()).append("\n");
        }
        return sb.toString();
    }
}
