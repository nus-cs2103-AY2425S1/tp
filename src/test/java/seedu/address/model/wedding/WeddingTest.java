package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.PersonId;

public class WeddingTest {
    private PersonId[] ids;
    private List<PersonId> assignees;
    private Wedding wedding;
    private Wedding otherWedding;

    @BeforeEach
    public void setUp() {
        ids = new PersonId[]{new PersonId()};
        assignees = List.of(ids);
        wedding = new Wedding(new WeddingName("Name"), "Date", assignees);
        otherWedding = new Wedding(new WeddingName("Other"), "Date");
    }
    @Test
    public void isSameWedding_sameObject_true() {
        boolean result = wedding.isSameWedding(wedding);
        assertTrue(result);
    }

    @Test
    public void isSameWedding_diffObject_false() {
        boolean result = wedding.isSameWedding(otherWedding);
        assertFalse(result);
    }

    @Test
    public void equals() {
        //same object
        assertTrue(wedding.equals(wedding));

        //different name
        assertFalse(wedding.equals(otherWedding));

        //null
        assertFalse(wedding.equals(null));

        //different type
        assertFalse(wedding.equals(5));

        //different object, same name, date and assignees
        Wedding equalWedding = new Wedding(new WeddingName("Name"), "Date", assignees);
        assertTrue(wedding.equals(equalWedding));

        //different object, different name, same date and assignees
        Wedding diffName = new Wedding(new WeddingName("Diff"), "Date", assignees);
        assertFalse(wedding.equals(diffName));

        //different object, same name, different date, same assignees
        Wedding diffDate = new Wedding(new WeddingName("Name"), "diffDate", assignees);
        assertFalse(wedding.equals(diffDate));

        //different object, same name, same date, different assignees
        Wedding diffAssignees = new Wedding(new WeddingName("Name"), "Date");
        assertFalse(wedding.equals(diffAssignees));
    }
}
