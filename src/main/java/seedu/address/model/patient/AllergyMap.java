package seedu.address.model.patient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.AllergySorter;

/**
 * Represents a list of allergies.
 * Guarantees: immutable; is always valid
 * @see Allergy
 * @see AllergySorter
 */
public class AllergyMap {
    //List is used because allergies are usually added, not deleted or edited. So adding should be fast.
    private final Set<Allergy> allergies;

    /**
     * Constructs an empty {@code AllergyList}.
     */
    public AllergyMap() {
        this.allergies = new HashSet<>();
    }

    /**
     * Constructs an {@code AllergyList} with the given allergies.
     * @param allergies
     */
    public AllergyMap(Set<Allergy> allergies) {
        this.allergies = allergies;
    }

    /**
     * Adds an allergy to the list.
     * Sorts the list after adding the allergy.
     * @param Allergy
     */
    public void addAllergy(Allergy allergy) {
        allergies.add(allergy);
    }

    /**
     * Deletes an allergy from the list.
     * @param allergy
     */
    public void deleteAllergy(Allergy allergy) {
        allergies.remove(allergy);
    }

    /**
     * Returns an immutable list of allergies.
     */
    public List<Allergy> getAllergies() {
        List<Allergy> allergiesList = new ArrayList<>(allergies);
        AllergySorter.sortAllergies(allergiesList);
        return Collections.unmodifiableList(allergiesList);
    }

    /**
     * Returns a string representation of the allergies
     * in the form of a list of strings.
     * @return
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
