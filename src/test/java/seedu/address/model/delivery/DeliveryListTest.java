package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Address;

public class DeliveryListTest {

    private static final Delivery VALID_DELIVERY_1 = new Delivery(new DeliveryId("1"),
            new HashSet<>(Arrays.asList(new ItemName("Monitor"))), new Address("311, Clementi Ave 2, #02-25, S120300"),
            new Cost("$1000"), new Date("2000-04-04"), new Time("06:00:00"),
            new Eta("2020-02-02"), new Status("not delivered"), new Archive("true"));

    private static final Delivery VALID_DELIVERY_2 = new Delivery(new DeliveryId("2"),
            new HashSet<>(Arrays.asList(new ItemName("Apples"))), new Address("311, Clementi Ave 2, #01-25, S120300"),
            new Cost("$300"), new Date("2010-05-05"), new Time("09:05:12"),
            new Eta("1990-04-04"), new Status("delivered"), new Archive("false"));

    private static final Delivery VALID_DELIVERY_3 = new Delivery(new DeliveryId("3"),
            new HashSet<>(Arrays.asList(new ItemName("bananas"))), new Address("Zimbabwe Ave 2, #02-25, S120300"),
            new Cost("$600"), new Date("2020-07-07"), new Time("14:06:17"),
            new Eta("2025-01-01"), new Status("not delivered"), new Archive("false"));

    private static final Delivery VALID_DELIVERY_4 = new Delivery(new DeliveryId("4"),
            new HashSet<>(Arrays.asList(new ItemName("carrots"))), new Address("NUS Ave 2, #01-01, S987654"),
            new Cost("$20"), new Date("2000-04-04"), new Time("18:09:24"),
            new Eta("1970-04-04"), new Status("delivered"), new Archive("true"));

    private static final DeliveryList VALID_DELIVERY_LIST = new DeliveryList();
    private static final DeliveryList VALID_DELIVERY_LIST_ID_UNSORTED = new DeliveryList();
    private static final DeliveryList VALID_DELIVERY_LIST_ADDRESS_UNSORTED = new DeliveryList();
    private static final DeliveryList VALID_DELIVERY_LIST_COST_UNSORTED = new DeliveryList();
    private static final DeliveryList VALID_DELIVERY_LIST_DATE_UNSORTED = new DeliveryList();
    private static final DeliveryList VALID_DELIVERY_LIST_ETA_UNSORTED = new DeliveryList();
    private static final DeliveryList VALID_DELIVERY_LIST_STATUS_UNSORTED = new DeliveryList();

    private static final DeliveryList VALID_DELIVERY_LIST_ID_SORTED = new DeliveryList();
    private static final DeliveryList VALID_DELIVERY_LIST_ADDRESS_SORTED = new DeliveryList();
    private static final DeliveryList VALID_DELIVERY_LIST_COST_SORTED = new DeliveryList();
    private static final DeliveryList VALID_DELIVERY_LIST_DATE_SORTED = new DeliveryList();
    private static final DeliveryList VALID_DELIVERY_LIST_ETA_SORTED = new DeliveryList();
    private static final DeliveryList VALID_DELIVERY_LIST_STATUS_SORTED = new DeliveryList();

    @Test
    public void containsDelivery_nullDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> VALID_DELIVERY_LIST.contains(null));
    }

    @Test
    public void containsDelivery_validDelivery_success() {
        VALID_DELIVERY_LIST.add(VALID_DELIVERY_1);
        assertTrue(() -> VALID_DELIVERY_LIST.contains(VALID_DELIVERY_1));
    }

    @Test
    public void containsDelivery_validDelivery_failure() {
        assertFalse(() -> VALID_DELIVERY_LIST.contains(VALID_DELIVERY_1));
    }

    @Test
    public void addDelivery_nullDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> VALID_DELIVERY_LIST.add(null));
    }

    @Test
    public void setDelivery_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> VALID_DELIVERY_LIST.setDelivery(null, null));
    }

    @Test
    public void removeDelivery_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> VALID_DELIVERY_LIST.remove(null));
    }

    @Test
    public void setDeliveries_nullDeliveryList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> VALID_DELIVERY_LIST.setDeliveries((DeliveryList) null));
        assertThrows(NullPointerException.class, () -> VALID_DELIVERY_LIST.setDeliveries((List<Delivery>) null));
    }

    @Test
    public void sortById_success() {
        VALID_DELIVERY_LIST_ID_SORTED.add(VALID_DELIVERY_2);
        VALID_DELIVERY_LIST_ID_SORTED.add(VALID_DELIVERY_3);
        VALID_DELIVERY_LIST_ID_SORTED.add(VALID_DELIVERY_1);
        VALID_DELIVERY_LIST_ID_SORTED.add(VALID_DELIVERY_4);

        VALID_DELIVERY_LIST_ID_UNSORTED.add(VALID_DELIVERY_4);
        VALID_DELIVERY_LIST_ID_UNSORTED.add(VALID_DELIVERY_2);
        VALID_DELIVERY_LIST_ID_UNSORTED.add(VALID_DELIVERY_3);
        VALID_DELIVERY_LIST_ID_UNSORTED.add(VALID_DELIVERY_1);
        VALID_DELIVERY_LIST_ID_UNSORTED.sortById();

        System.out.println(VALID_DELIVERY_LIST_ID_UNSORTED);

        assertArrayEquals(VALID_DELIVERY_LIST_ID_SORTED.asUnmodifiableObservableList().toArray(),
                VALID_DELIVERY_LIST_ID_UNSORTED.asUnmodifiableObservableList().toArray());
    }

    @Test
    public void sortByAddress_success() {
        VALID_DELIVERY_LIST_ADDRESS_SORTED.add(VALID_DELIVERY_2);
        VALID_DELIVERY_LIST_ADDRESS_SORTED.add(VALID_DELIVERY_3);
        VALID_DELIVERY_LIST_ADDRESS_SORTED.add(VALID_DELIVERY_1);
        VALID_DELIVERY_LIST_ADDRESS_SORTED.add(VALID_DELIVERY_4);

        VALID_DELIVERY_LIST_ADDRESS_UNSORTED.add(VALID_DELIVERY_1);
        VALID_DELIVERY_LIST_ADDRESS_UNSORTED.add(VALID_DELIVERY_2);
        VALID_DELIVERY_LIST_ADDRESS_UNSORTED.add(VALID_DELIVERY_3);
        VALID_DELIVERY_LIST_ADDRESS_UNSORTED.add(VALID_DELIVERY_4);
        VALID_DELIVERY_LIST_ADDRESS_UNSORTED.sortByAddress();

        assertArrayEquals(VALID_DELIVERY_LIST_ADDRESS_SORTED.asUnmodifiableObservableList().toArray(),
                VALID_DELIVERY_LIST_ADDRESS_UNSORTED.asUnmodifiableObservableList().toArray());
    }

    @Test
    public void sortByCost_success() {
        VALID_DELIVERY_LIST_COST_SORTED.add(VALID_DELIVERY_2);
        VALID_DELIVERY_LIST_COST_SORTED.add(VALID_DELIVERY_3);
        VALID_DELIVERY_LIST_COST_SORTED.add(VALID_DELIVERY_4);
        VALID_DELIVERY_LIST_COST_SORTED.add(VALID_DELIVERY_1);

        VALID_DELIVERY_LIST_COST_UNSORTED.add(VALID_DELIVERY_1);
        VALID_DELIVERY_LIST_COST_UNSORTED.add(VALID_DELIVERY_2);
        VALID_DELIVERY_LIST_COST_UNSORTED.add(VALID_DELIVERY_3);
        VALID_DELIVERY_LIST_COST_UNSORTED.add(VALID_DELIVERY_4);
        VALID_DELIVERY_LIST_COST_UNSORTED.sortByCost();

        assertArrayEquals(VALID_DELIVERY_LIST_COST_SORTED.asUnmodifiableObservableList().toArray(),
                VALID_DELIVERY_LIST_COST_UNSORTED.asUnmodifiableObservableList().toArray());
    }

    @Test
    public void sortByDate_success() {
        VALID_DELIVERY_LIST_DATE_SORTED.add(VALID_DELIVERY_2);
        VALID_DELIVERY_LIST_DATE_SORTED.add(VALID_DELIVERY_3);
        VALID_DELIVERY_LIST_DATE_SORTED.add(VALID_DELIVERY_1);
        VALID_DELIVERY_LIST_DATE_SORTED.add(VALID_DELIVERY_4);

        VALID_DELIVERY_LIST_DATE_UNSORTED.add(VALID_DELIVERY_1);
        VALID_DELIVERY_LIST_DATE_UNSORTED.add(VALID_DELIVERY_2);
        VALID_DELIVERY_LIST_DATE_UNSORTED.add(VALID_DELIVERY_4);
        VALID_DELIVERY_LIST_DATE_UNSORTED.add(VALID_DELIVERY_3);
        VALID_DELIVERY_LIST_DATE_UNSORTED.sortByDate();

        assertArrayEquals(VALID_DELIVERY_LIST_DATE_SORTED.asUnmodifiableObservableList().toArray(),
                VALID_DELIVERY_LIST_DATE_UNSORTED.asUnmodifiableObservableList().toArray());
    }

    @Test
    public void sortByEta_success() {
        VALID_DELIVERY_LIST_ETA_SORTED.add(VALID_DELIVERY_2);
        VALID_DELIVERY_LIST_ETA_SORTED.add(VALID_DELIVERY_3);
        VALID_DELIVERY_LIST_ETA_SORTED.add(VALID_DELIVERY_4);
        VALID_DELIVERY_LIST_ETA_SORTED.add(VALID_DELIVERY_1);


        VALID_DELIVERY_LIST_ETA_UNSORTED.add(VALID_DELIVERY_1);
        VALID_DELIVERY_LIST_ETA_UNSORTED.add(VALID_DELIVERY_2);
        VALID_DELIVERY_LIST_ETA_UNSORTED.add(VALID_DELIVERY_3);
        VALID_DELIVERY_LIST_ETA_UNSORTED.add(VALID_DELIVERY_4);
        VALID_DELIVERY_LIST_ETA_UNSORTED.sortByEta();

        assertArrayEquals(VALID_DELIVERY_LIST_ETA_SORTED.asUnmodifiableObservableList().toArray(),
                VALID_DELIVERY_LIST_ETA_UNSORTED.asUnmodifiableObservableList().toArray());
    }

    @Test
    public void sortByStatus_success() {
        VALID_DELIVERY_LIST_STATUS_SORTED.add(VALID_DELIVERY_3);
        VALID_DELIVERY_LIST_STATUS_SORTED.add(VALID_DELIVERY_2);
        VALID_DELIVERY_LIST_STATUS_SORTED.add(VALID_DELIVERY_1);
        VALID_DELIVERY_LIST_STATUS_SORTED.add(VALID_DELIVERY_4);

        VALID_DELIVERY_LIST_STATUS_UNSORTED.add(VALID_DELIVERY_1);
        VALID_DELIVERY_LIST_STATUS_UNSORTED.add(VALID_DELIVERY_2);
        VALID_DELIVERY_LIST_STATUS_UNSORTED.add(VALID_DELIVERY_3);
        VALID_DELIVERY_LIST_STATUS_UNSORTED.add(VALID_DELIVERY_4);
        VALID_DELIVERY_LIST_STATUS_UNSORTED.sortByStatus();

        assertArrayEquals(VALID_DELIVERY_LIST_STATUS_SORTED.asUnmodifiableObservableList().toArray(),
                VALID_DELIVERY_LIST_STATUS_UNSORTED.asUnmodifiableObservableList().toArray());
    }
}
