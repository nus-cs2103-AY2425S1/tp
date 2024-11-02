package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.AJUNWEI;
import static seedu.address.testutil.TypicalPersons.BJUNKANG;
import static seedu.address.testutil.TypicalPersons.CJUNYU;
import static seedu.address.testutil.TypicalPersons.DJUNHONG;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class PersonRoleComparatorTest {

    @Test
    public void sortsCorrectly() {
        //initialize sorted list
        ArrayList<Person> sortedPersons = new ArrayList<>();
        sortedPersons.add(AJUNWEI);
        sortedPersons.add(BJUNKANG);
        sortedPersons.add(CJUNYU);
        sortedPersons.add(DJUNHONG);

        //initialize unsorted list
        ArrayList<Person> unsortedPersons = new ArrayList<>();
        unsortedPersons.add(DJUNHONG);
        unsortedPersons.add(BJUNKANG);
        unsortedPersons.add(AJUNWEI);
        unsortedPersons.add(CJUNYU);

        //sort unsorted list
        unsortedPersons.sort(new PersonRoleComparator());
        assertEquals(sortedPersons, unsortedPersons);
    }
}
