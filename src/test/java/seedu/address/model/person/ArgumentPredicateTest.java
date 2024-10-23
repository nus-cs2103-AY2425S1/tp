package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class ArgumentPredicateTest {
    @Test
    public void equals() {
        ArgumentPredicate firstArgumentPredicate = new ArgumentPredicate(ALICE);
        ArgumentPredicate secondArgumentPredicate = new ArgumentPredicate(BOB);

        // same object -> returns true
        assertTrue(firstArgumentPredicate.equals(firstArgumentPredicate));

        // same values -> returns true
        ArgumentPredicate firstPredicateCopy = new ArgumentPredicate(ALICE);
        assertTrue(firstArgumentPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstArgumentPredicate.equals(1));

        // null -> returns false
        assertFalse(firstArgumentPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstArgumentPredicate.equals(secondArgumentPredicate));
    }

    @Test
    public void test_personContainsKeywords_returnsTrue() {
        // Only name parameter
        Person testNamePerson = new PersonBuilder().withName("Bob")
                .withPhone("__No_Phone__").withEmail("__No_Email__")
                .withAddress("__No_Address__")
                .withProjectStatus("__No_Project_Status__")
                .withPaymentStatus("__No_Payment_Status__").withClientStatus("__No_Client_Status__")
                .withDeadline("__No_Deadline__").build();
        ArgumentPredicate namePredicate = new ArgumentPredicate(testNamePerson);
        assertTrue(namePredicate.test(new PersonBuilder().withName(VALID_NAME_BOB).build()));

        // Only phone parameter
        Person testPhonePerson = new PersonBuilder().withName("__No_Name__")
                .withPhone("9876").withEmail("__No_Email__")
                .withAddress("__No_Address__")
                .withProjectStatus("__No_Project_Status__")
                .withPaymentStatus("__No_Payment_Status__").withClientStatus("__No_Client_Status__")
                .withDeadline("__No_Deadline__").build();
        ArgumentPredicate phonePredicate = new ArgumentPredicate(testPhonePerson);
        assertTrue(phonePredicate.test(new PersonBuilder().withPhone("9876").build()));

        // Only email parameter
        Person testEmailPerson = new PersonBuilder().withName("__No_Name__")
                .withPhone("__No_Phone__").withEmail("test@gmail.com")
                .withAddress("__No_Address__")
                .withProjectStatus("__No_Project_Status__")
                .withPaymentStatus("__No_Payment_Status__").withClientStatus("__No_Client_Status__")
                .withDeadline("__No_Deadline__").build();
        ArgumentPredicate emailPredicate = new ArgumentPredicate(testEmailPerson);
        assertTrue(emailPredicate.test(new PersonBuilder().withEmail("test@gmail.com").build()));

        // Only address parameter
        Person testAddressPerson = new PersonBuilder().withName("__No_Name__")
                .withPhone("__No_Phone__").withEmail("__No_Email__")
                .withAddress("Blk 17, Clown street")
                .withProjectStatus("__No_Project_Status__")
                .withPaymentStatus("__No_Payment_Status__").withClientStatus("__No_Client_Status__")
                .withDeadline("__No_Deadline__").build();
        ArgumentPredicate addressPredicate = new ArgumentPredicate(testAddressPerson);
        assertTrue(addressPredicate.test(new PersonBuilder().withAddress("Blk 17, Clown street").build()));

        // Only project status parameter
        Person testProjectStatusPerson = new PersonBuilder().withName("__No_Name__")
                .withPhone("__No_Phone__").withEmail("__No_Email__")
                .withAddress("__No_Address__")
                .withProjectStatus("completed")
                .withPaymentStatus("__No_Payment_Status__").withClientStatus("__No_Client_Status__")
                .withDeadline("__No_Deadline__").build();
        ArgumentPredicate projectStatusPredicate = new ArgumentPredicate(testProjectStatusPerson);
        assertTrue(projectStatusPredicate.test(new PersonBuilder().withProjectStatus("completed").build()));

        // Only payment status parameter
        Person testPaymentStatusPerson = new PersonBuilder().withName("__No_Name__")
                .withPhone("__No_Phone__").withEmail("__No_Email__")
                .withAddress("__No_Address__")
                .withProjectStatus("__No_Project_Status__")
                .withPaymentStatus("paid").withClientStatus("__No_Client_Status__")
                .withDeadline("__No_Deadline__").build();
        ArgumentPredicate paymentStatusPredicate = new ArgumentPredicate(testPaymentStatusPerson);
        assertTrue(paymentStatusPredicate.test(new PersonBuilder().withPaymentStatus("paid").build()));

        // Only client status parameter
        Person testClientStatusPerson = new PersonBuilder().withName("__No_Name__")
                .withPhone("__No_Phone__").withEmail("__No_Email__")
                .withAddress("__No_Address__")
                .withProjectStatus("__No_Project_Status__")
                .withPaymentStatus("__No_Payment_Status__").withClientStatus("active")
                .withDeadline("__No_Deadline__").build();
        ArgumentPredicate clientStatusPredicate = new ArgumentPredicate(testClientStatusPerson);
        assertTrue(clientStatusPredicate.test(new PersonBuilder().withPaymentStatus("paid").build()));

        // Only deadline parameter
        Person testDeadlinePerson = new PersonBuilder().withName("__No_Name__")
                .withPhone("__No_Phone__").withEmail("__No_Email__")
                .withAddress("__No_Address__")
                .withProjectStatus("__No_Project_Status__")
                .withPaymentStatus("__No_Payment_Status__").withClientStatus("__No_Client_Status__")
                .withDeadline("20-10-2002").build();
        ArgumentPredicate deadlinePredicate = new ArgumentPredicate(testDeadlinePerson);
        assertTrue(deadlinePredicate.test(new PersonBuilder().withDeadline("20-10-2002").build()));

        // Only tag parameter
        Person testTagPerson = new PersonBuilder().withName("__No_Name__")
                .withPhone("__No_Phone__").withEmail("__No_Email__")
                .withAddress("__No_Address__").withTags(new String[] {"friend"})
                .withProjectStatus("__No_Project_Status__")
                .withPaymentStatus("__No_Payment_Status__").withClientStatus("__No_Client_Status__")
                .withDeadline("__No_Deadline__").build();
        ArgumentPredicate tagPredicate = new ArgumentPredicate(testTagPerson);
        assertTrue(tagPredicate.test(new PersonBuilder().withTags(new String[] {"friend", "family"}).build()));

        // Multiple parameters
        Person testMultipleParametersPerson = new PersonBuilder().withName("Luk")
                .withPhone("__No_Phone__").withEmail("__No_Email__")
                .withAddress("__No_Address__").withTags(new String[] {"friend"})
                .withProjectStatus("__No_Project_Status__")
                .withPaymentStatus("__No_Payment_Status__").withClientStatus("__No_Client_Status__")
                .withDeadline("__No_Deadline__").build();
        ArgumentPredicate multipleParametersPredicate = new ArgumentPredicate(testMultipleParametersPerson);
        assertTrue(multipleParametersPredicate.test(new PersonBuilder().withName("Luke Chambers")
                .withTags(new String[] {"friend", "family"}).build()));
    }

    @Test
    public void test_personContainWrongKeywords_returnsFalse() {
        // Match name and tags, but does not match phone
        Person testMultipleParametersFailPerson = new PersonBuilder().withName("Luk")
                .withPhone("9999").withEmail("__No_Email__")
                .withAddress("__No_Address__").withTags(new String[] {"friend"})
                .withProjectStatus("__No_Project_Status__")
                .withPaymentStatus("__No_Payment_Status__").withClientStatus("__No_Client_Status__")
                .withDeadline("__No_Deadline__").build();
        ArgumentPredicate multipleParametersFailPredicate = new ArgumentPredicate(testMultipleParametersFailPerson);
        assertFalse(multipleParametersFailPredicate.test(new PersonBuilder().withName("Luke").withPhone("12345")
                .withTags(new String[] {"friend", "family"}).build()));
    }

    @Test
    public void toStringMethod() {
        ArgumentPredicate predicate = new ArgumentPredicate(BOB);
        String expected = ArgumentPredicate.class.getCanonicalName() + "{person=" + BOB + "}";
        assertEquals(expected, predicate.toString());
    }
}
