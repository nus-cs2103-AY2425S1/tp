package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class PersonNameComparatorTest {

    @Test
    public void sortsCorrectly() {
        //initialize sorted list
        ArrayList<Person> sortedPersons = new ArrayList<>();
        sortedPersons.add(ALICE);
        sortedPersons.add(BENSON);
        sortedPersons.add(CARL);
        sortedPersons.add(DANIEL);
        sortedPersons.add(ELLE);

        //initialize unsorted list
        ArrayList<Person> unsortedPersons = new ArrayList<>();
        unsortedPersons.add(DANIEL);
        unsortedPersons.add(BENSON);
        unsortedPersons.add(ALICE);
        unsortedPersons.add(ELLE);
        unsortedPersons.add(CARL);

        //sort unsorted list
        unsortedPersons.sort(new PersonNameComparator());
        assertEquals(sortedPersons, unsortedPersons);
    }
}
