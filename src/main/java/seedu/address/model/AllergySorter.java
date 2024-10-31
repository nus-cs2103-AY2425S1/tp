package seedu.address.model;

import java.util.Collections;
import java.util.List;

import seedu.address.model.patient.Allergy;

/**
 * Contains utility methods for sorting allergies.
 * Sorts allergies by alphabetical order.
 * @see Allergy
 */
public class AllergySorter {
    public static void sortAllergies(List<Allergy> allergies) {
        Collections.sort(allergies, Allergy.NAME_COMPARATOR);
    }
}
