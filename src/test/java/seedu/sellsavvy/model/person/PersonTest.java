package seedu.sellsavvy.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.logic.commands.personcommands.PersonCommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.sellsavvy.logic.commands.personcommands.PersonCommandTestUtil.VALID_EMAIL_BOB;
import static seedu.sellsavvy.logic.commands.personcommands.PersonCommandTestUtil.VALID_NAME_BOB;
import static seedu.sellsavvy.logic.commands.personcommands.PersonCommandTestUtil.VALID_PHONE_BOB;
import static seedu.sellsavvy.logic.commands.personcommands.PersonCommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.sellsavvy.testutil.Assert.assertThrows;
import static seedu.sellsavvy.testutil.TypicalPersons.ALICE;
import static seedu.sellsavvy.testutil.TypicalPersons.BOB;
import static seedu.sellsavvy.testutil.TypicalPersons.HOON;
import static seedu.sellsavvy.testutil.TypicalPersons.IDA;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.transformation.FilteredList;
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.order.Status;
import seedu.sellsavvy.model.order.StatusEqualsKeywordPredicate;
import seedu.sellsavvy.testutil.PersonBuilder;

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
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
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
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void getFilteredOrderList() {
        // identical person -> same order list
        FilteredList<Order> expectedOrders = HOON.getFilteredOrderList();
        assertEquals(expectedOrders, HOON.getFilteredOrderList());

        // different order list
        expectedOrders = IDA.getFilteredOrderList();
        assertNotEquals(expectedOrders, HOON.getFilteredOrderList());
    }

    @Test
    public void getOrderPredicate() {
        // same unfiltered order list predicate
        Predicate<? super Order> expectedPredicate = HOON.getOrderPredicate();
        assertEquals(expectedPredicate, IDA.getOrderPredicate());

        // different filtered order list predicate
        IDA.updateFilteredOrderList(new StatusEqualsKeywordPredicate(Status.COMPLETED));
        assertNotEquals(expectedPredicate, IDA.getOrderPredicate());

        // same filtered order list predicate
        HOON.updateFilteredOrderList(new StatusEqualsKeywordPredicate(Status.PENDING));
        expectedPredicate = HOON.getOrderPredicate();
        IDA.updateFilteredOrderList(new StatusEqualsKeywordPredicate(Status.PENDING));
        assertEquals(expectedPredicate, IDA.getOrderPredicate());
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress() + ", tags=" + ALICE.getTags()
                + ", orders=" + ALICE.getOrderList() + "}";
        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void getFilteredOrderList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> ALICE.getFilteredOrderList().remove(0));
    }
}
