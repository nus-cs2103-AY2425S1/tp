package seedu.sellsavvy.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.sellsavvy.model.AddressBook;
import seedu.sellsavvy.model.ReadOnlyAddressBook;
import seedu.sellsavvy.model.order.Date;
import seedu.sellsavvy.model.order.Item;
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.order.OrderList;
import seedu.sellsavvy.model.order.Quantity;
import seedu.sellsavvy.model.order.Status;
import seedu.sellsavvy.model.person.Address;
import seedu.sellsavvy.model.person.Email;
import seedu.sellsavvy.model.person.Name;
import seedu.sellsavvy.model.person.Person;
import seedu.sellsavvy.model.person.Phone;
import seedu.sellsavvy.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    private static final Order ABACUS = new Order(new Item("Abacus"), new Quantity("2"),
            new Date("10-10-2025"), Status.PENDING);
    private static final Order BLOCKS = new Order(new Item("Blocks"), new Quantity("101"),
            new Date("05-05-2025"), Status.COMPLETED);
    private static final Order CAMERA = new Order(new Item("Camera with spare lens"), new Quantity("1"),
            new Date("03-03-2025"), Status.PENDING);
    private static final Order DAGGER = new Order(new Item("Damascus daggers"), new Quantity("5"),
            new Date("12-12-2025"), Status.PENDING);

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"), getOrderList(ABACUS, BLOCKS)),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends"), getOrderList()),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"), getOrderList()),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family"), getOrderList(ABACUS, BLOCKS, CAMERA, DAGGER)),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates"), getOrderList(ABACUS)),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"), getOrderList(ABACUS))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns an {@code OrderList} containing the list of orders given.
     */
    public static OrderList getOrderList(Order... orders) {
        OrderList orderList = new OrderList();
        List<Order> listOfOrders = Arrays.stream(orders).collect(Collectors.toList());
        orderList.setOrders(listOfOrders);
        return orderList;
    }

}
