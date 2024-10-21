package seedu.address.testutil;

import static seedu.address.testutil.TypicalOwners.getTypicalOwners;
import static seedu.address.testutil.TypicalPets.getTypicalPets;

import seedu.address.model.AddressBook;
import seedu.address.model.owner.Owner;
import seedu.address.model.pet.Pet;

/**
 * A utility class containing a list of {@code Owner} and {@code Pet} objects to be used in tests.
 */
public class TypicalPawPatrol {

    /**
     * Returns an {@code AddressBook} with all the typical owners and pets.
     */
    public static AddressBook getTypicalPawPatrol() {
        AddressBook ab = new AddressBook();
        for (Owner owner : getTypicalOwners()) {
            ab.addOwner(owner);
        }
        for (Pet pet : getTypicalPets()) {
            ab.addPet(pet);
        }
        return ab;
    }
}
