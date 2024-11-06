package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.JEVAN;
import static seedu.address.testutil.TypicalPersons.KEVIN;
import static seedu.address.testutil.TypicalPersons.LILY;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class PersonDateReverseComparatorTest {

    @Test
    public void compareTo_unsortedList_sortsCorrectly() {
        //initialize sorted list
        ArrayList<Person> sortedPersons = new ArrayList<>();
        sortedPersons.add(LILY);
        sortedPersons.add(KEVIN);
        sortedPersons.add(JEVAN);
        sortedPersons.add(ALICE);

        //initialize unsorted list
        ArrayList<Person> unsortedPersons = new ArrayList<>();
        unsortedPersons.add(LILY);
        unsortedPersons.add(JEVAN);
        unsortedPersons.add(ALICE);
        unsortedPersons.add(KEVIN);

        //sort unsorted list
        unsortedPersons.sort(new PersonDateReverseComparator());
        assertEquals(sortedPersons, unsortedPersons);
    }
}
