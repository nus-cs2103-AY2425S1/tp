package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_VEGETARIAN;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import seedu.address.model.order.Order;
import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_VEGETARIAN).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void putOrderTest() {
        Order cake = new Order("Cake");
        Order pizza = new Order("Pizza");
        Order noodle = new Order("Noodle");

        Person p = new PersonBuilder().build();
        p.putOrder(cake);
        p.putOrder(cake);
        p.putOrder(pizza);
        p.putOrder(noodle);
        p.putOrder(noodle);
        p.putOrder(noodle);

        HashMap<Order, Integer> orderFrequency = new HashMap<>();
        orderFrequency.put(cake, 2);
        orderFrequency.put(pizza, 1);
        orderFrequency.put(noodle, 3);

        assertEquals(orderFrequency, p.getOrderFrequency());

        orderFrequency.put(cake, 1);
        assertNotEquals(orderFrequency, p.getOrderFrequency());

        orderFrequency.remove(cake);
        p.removeOrder(cake);
        assertEquals(orderFrequency, p.getOrderFrequency());
    }

    @Test
    public void personBuilderWithOrderFrequencyTest() {
        HashMap<Order, Integer> orders = new HashMap<>();
        orders.put(new Order("cake"), 10);
        Person p = new PersonBuilder().withOrderFrequency(orders).build();
    }

    @Test
    public void removeOrderTest() {
        Order cake = new Order("Cake");
        Order pizza = new Order("Pizza");
        Order noodle = new Order("Noodle");

        Person p = new PersonBuilder().build();
        p.putOrder(cake);
        p.putOrder(cake);
        p.putOrder(pizza);
        p.putOrder(noodle);
        p.putOrder(noodle);
        p.putOrder(noodle);

        HashMap<Order, Integer> orderFrequency = new HashMap<>();
        orderFrequency.put(pizza, 1);
        orderFrequency.put(noodle, 3);

        p.removeOrder(cake);
        assertEquals(orderFrequency, p.getOrderFrequency());
    }

    @Test
    public void compareToTest() {
        Order cake = new Order("Cake");
        Order pizza = new Order("Pizza");

        Person p1 = new PersonBuilder().build();
        Person p2 = new PersonBuilder().build();
        Person p3 = new PersonBuilder().build();
        Person p4 = new PersonBuilder().build();

        p1.putOrder(cake);
        p1.putOrder(cake);
        p1.putOrder(pizza);

        for (int i = 0; i < 2; ++i) {
            p2.putOrder(cake);
            p2.putOrder(pizza);
        }

        p3.putOrder(cake);
        p3.putOrder(pizza);

        for (int i = 0; i < 5; ++i) {
            p4.putOrder(cake);
        }

        ArrayList<Person> list = new ArrayList<>();
        ArrayList<Person> expected = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        expected.add(p2);
        expected.add(p1);
        expected.add(p3);

        Collections.sort(list);
        assertEquals(expected, list);
    }

    @Test
    public void compareToTest2() {
        ArrayList<Person> list = new ArrayList<>();
        ArrayList<Person> expected = new ArrayList<>();
        Order cake = new Order("Cake");
        Order pizza = new Order("Pizza");

        Person p1 = new PersonBuilder().build();
        Person p2 = new PersonBuilder().build();
        Person p3 = new PersonBuilder().build();

        p1.putOrder(cake);
        p2.putOrder(pizza);
        p3.putOrder(cake);
        p3.putOrder(pizza);
        list.add(p1);
        list.add(p2);
        list.add(p3);
        Collections.sort(list);
        assertEquals(p3, list.get(0));
    }

    @Test
    public void hashCodeTest() {
        // This hashCode test is not complete, this only test orderFrequency

        Order cake = new Order("Cake");
        Order pizza = new Order("Pizza");

        Person p1 = new PersonBuilder().build();
        Person p2 = new PersonBuilder().build();
        Person p3 = new PersonBuilder().build();
        p1.putOrder(cake);
        p2.putOrder(pizza);
        p3.putOrder(cake);

        assertNotEquals(p1.hashCode(), p2.hashCode());
        assertEquals(p1.hashCode(), p3.hashCode());
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_VEGETARIAN).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email="
                + ALICE.getEmail()
                + ", address="
                + ALICE.getAddress()
                + ", postalCode="
                + ALICE.getPostalCode()
                + ", tags="
                + ALICE.getTags()
                + ", orders="
                + ALICE.getOrderFrequency()
                + ", isArchived="
                + ALICE.isArchived()
                + "}";
        assertEquals(expected, ALICE.toString());
    }
}
