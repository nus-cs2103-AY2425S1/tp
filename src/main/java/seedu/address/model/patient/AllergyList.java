package seedu.address.model.patient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.AllergySorter;
import seedu.address.model.patient.exceptions.AllergyAlreadyExistsException;
import seedu.address.model.patient.exceptions.AllergyNotFoundException;

/**
 * Represents a list of allergies.
 * Guarantees: immutable; is always valid
 * @see Allergy
 * @see AllergySorter
 */
public class AllergyList {
    private final Set<Allergy> allergies;

    /**
     * Constructs an empty {@code AllergyList}.
     */
    public AllergyList() {
        this.allergies = new HashSet<>();
    }

    /**
     * Constructs an {@code AllergyList} with the given allergies.
     */
    public AllergyList(Set<Allergy> allergies) {
        this.allergies = allergies;
    }

    /**
     * Adds an allergy to the list.
     * Sorts the list after adding the allergy.
     */
    public void addAllergy(Allergy allergy) {
        allergies.add(allergy);
    }

    /**
     * Deletes an allergy from the list.
     */
    public void deleteAllergy(Allergy allergy) {
        allergies.remove(allergy);
    }

    /**
     * Checks if an allergy is present for removal in the list.
     */
    public void checkAllergyPresentForRemoval(Allergy allergy) {
        if (!allergies.contains(allergy)) {
            throw new AllergyNotFoundException();
        }
    }

    /**
     * Checks if an allergy is present in the list.
     */
    public void checkAllergyAlreadyExists(Allergy allergy) {
        if (allergies.contains(allergy)) {
            throw new AllergyAlreadyExistsException();
        }
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
