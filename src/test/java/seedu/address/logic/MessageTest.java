package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class MessageTest {

    @Test
    public void testFormatPerson() {
        Person person = new PersonBuilder(ALICE).build();

        String expectedMessage = "Alice Pauline; "
                + "Phone: 94351253; "
                + "Email: alice@example.com; "
                + "Address: 123, Jurong West Ave 6, #08-111, S120300; "
                + "Tags: [friends]";
        String actualMessage = Messages.format(person);

        assertEquals(expectedMessage, actualMessage);
    }

    //Relook at this section after creation of typicalDeliveries
//    @Test
//    public void testFormatDelivery() {
//        Delivery delivery = new Delivery(new ItemName("Laptop"), new Address("123, Some Street"), new Cost("$1000"),
//                LocalDate.of(2023, 10, 15), LocalTime.of(14, 30), new Eta("30 minutes"));
//
//        String expectedMessage = "Laptop; $1000; 2023-10-15; 14:30; 30 minutes";
//        String actualMessage = Messages.format(delivery);
//
//        assertEquals(expectedMessage, actualMessage);
//    }

    @Test
    public void testFormatPersonList() {
        Person person1 = new PersonBuilder(ALICE).build();
        Person person2 = new PersonBuilder(BOB).build();

        List<Person> personList = Arrays.asList(person1, person2);

        String expectedMessage = "\nAlice Pauline; Phone: 94351253; "
                + "Email: alice@example.com; Address: 123, Jurong West Ave 6, #08-111, S120300; Tags: [friends]"
                + "\nBob Choo; Phone: 22222222; "
                + "Email: bob@example.com; Address: Block 123, Bobby Street 3, S120300; Tags: [friend][husband]";
        String actualMessage = Messages.formatPersonList(personList);

        assertEquals(expectedMessage, actualMessage);
    }

    //Relook at this section after creation of typicalDeliveries
//    @Test
//    public void testFormatDeliveryList() {
//        Delivery delivery1 = new Delivery(new ItemName("Laptop"), new Address("123, Some Street"), new Cost("$1000"),
//                LocalDate.of(2023, 10, 15), LocalTime.of(14, 30), new Eta("30 minutes"));
//        Delivery delivery2 = new Delivery(new ItemName("Phone"), new Address("456, Another Street"), new Cost("$500"),
//                LocalDate.of(2023, 10, 16), LocalTime.of(10, 00), new Eta("15 minutes"));
//
//        List<Delivery> deliveryList = Arrays.asList(delivery1, delivery2);
//
//        String expectedMessage = "\nLaptop; $1000; 2023-10-15; 14:30; 30 minutes"
//                + "\nPhone; $500; 2023-10-16; 10:00; 15 minutes";
//        String actualMessage = Messages.formatDeliveryList(deliveryList);
//
//        assertEquals(expectedMessage, actualMessage);
//    }
}
