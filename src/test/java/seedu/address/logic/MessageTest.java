package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalDeliveries.APPLES;
import static seedu.address.testutil.TypicalDeliveries.ORANGES;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.delivery.Delivery;
import seedu.address.model.person.Person;
import seedu.address.testutil.DeliveryBuilder;
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

    @Test
    public void testFormatDelivery() {
        Delivery delivery = new DeliveryBuilder(APPLES).build();

        String expectedMessage = "[apples]; 123, Jurong West Ave 6, #08-111, S120300; "
                + "Cost: $100; ETA: 2024-12-31; Date Ordered: 2024-10-16; Time Ordered: 00:00";
        String actualMessage = Messages.format(delivery);

        assertEquals(expectedMessage, actualMessage);
    }

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

    @Test
    public void testFormatDeliveryList() {
        Delivery delivery1 = new DeliveryBuilder(APPLES).build();
        Delivery delivery2 = new DeliveryBuilder(ORANGES).build();

        List<Delivery> deliveryList = Arrays.asList(delivery1, delivery2);

        String expectedMessage = "\n[apples]; 123, Jurong West Ave 6, #08-111, S120300; Cost: $100; "
                + "ETA: 2024-12-31; Date Ordered: 2024-10-16; Time Ordered: 00:00"
                + "\n[oranges]; 123, Jurong West Ave 6, #08-111, S120300; "
                + "Cost: $120; ETA: 2024-11-23; Date Ordered: 2024-10-17; Time Ordered: 00:10";
        String actualMessage = Messages.formatDeliveryList(deliveryList);

        assertEquals(expectedMessage, actualMessage);
    }
}
