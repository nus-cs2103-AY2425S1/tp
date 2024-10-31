package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDeliveries.APPLE;
import static seedu.address.testutil.TypicalDeliveries.BREAD;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.delivery.exceptions.DeliveryNotFoundException;
import seedu.address.model.delivery.exceptions.DuplicateDeliveryException;

public class UniqueDeliveryListTest {

    private final UniqueDeliveryList uniqueDeliveryList = new UniqueDeliveryList();

    @Test
    public void contains_nullDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.contains(null));
    }

    @Test
    public void contains_deliveryNotInList_returnsFalse() {
        assertFalse(uniqueDeliveryList.contains(APPLE));
    }

    @Test
    public void contains_deliveryInList_returnsTrue() {
        uniqueDeliveryList.add(BREAD);
        assertTrue(uniqueDeliveryList.contains(BREAD));
    }

    @Test
    public void add_nullSupplier_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.add(null));
    }

    @Test
    public void add_duplicateDelivery_throwsDuplicateDeliveryException() {
        uniqueDeliveryList.add(APPLE);
        assertThrows(DuplicateDeliveryException.class, () -> uniqueDeliveryList.add(APPLE));
    }

    @Test
    public void setDelivery_nullTargetDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.setDelivery(null, APPLE));
    }

    @Test
    public void setDelivery_nullEditedDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.setDelivery(BREAD, null));
    }

    @Test
    public void setDelivery_targetDeliveryNotInList_throwsSupplierNotFoundException() {
        assertThrows(DeliveryNotFoundException.class, () -> uniqueDeliveryList.setDelivery(APPLE, APPLE));
    }

    @Test
    public void setDelivery_editedDeliveryIsSameDelivery_success() {
        uniqueDeliveryList.add(APPLE);
        uniqueDeliveryList.setDelivery(APPLE, APPLE);
        UniqueDeliveryList expectedUniqueDeliveryList = new UniqueDeliveryList();
        expectedUniqueDeliveryList.add(APPLE);
        assertEquals(expectedUniqueDeliveryList, uniqueDeliveryList);
    }

    @Test
    public void setDelivery_editedDeliveryHasDifferentIdentity_success() {
        uniqueDeliveryList.add(APPLE);
        uniqueDeliveryList.setDelivery(APPLE, BREAD);
        UniqueDeliveryList expectedUniqueDeliveryList = new UniqueDeliveryList();
        expectedUniqueDeliveryList.add(BREAD);
        assertEquals(expectedUniqueDeliveryList, uniqueDeliveryList);
    }

    @Test
    public void remove_nullDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.remove(null));
    }

    @Test
    public void remove_deliveryDoesNotExist_throwsDeliveryNotFoundException() {
        assertThrows(DeliveryNotFoundException.class, () -> uniqueDeliveryList.remove(APPLE));
    }

    @Test
    public void remove_existingDelivery_removesDelivery() {
        uniqueDeliveryList.add(APPLE);
        uniqueDeliveryList.remove(APPLE);
        UniqueDeliveryList expectedUniqueDeliveryList = new UniqueDeliveryList();
        assertEquals(expectedUniqueDeliveryList, uniqueDeliveryList);
    }

    @Test
    public void setDeliveries_nullUniqueDeliveryList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.setDeliveries((UniqueDeliveryList) null));
    }

    @Test
    public void setDeliveries_uniqueDeliveryList_replacesOwnListWithProvidedUniqueDeliveryList() {
        uniqueDeliveryList.add(APPLE);
        UniqueDeliveryList expectedUniqueDeliveryList = new UniqueDeliveryList();
        expectedUniqueDeliveryList.add(BREAD);
        uniqueDeliveryList.setDeliveries(expectedUniqueDeliveryList);
        assertEquals(expectedUniqueDeliveryList, uniqueDeliveryList);
    }

    @Test
    public void setDeliveries_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.setDeliveries((List<Delivery>) null));
    }

    @Test
    public void setDeliveries_list_replacesOwnListWithProvidedList() {
        uniqueDeliveryList.add(APPLE);
        List<Delivery> deliveryList = Collections.singletonList(BREAD);
        uniqueDeliveryList.setDeliveries(deliveryList);
        UniqueDeliveryList expectedUniqueDeliveryList = new UniqueDeliveryList();
        expectedUniqueDeliveryList.add(BREAD);
        assertEquals(expectedUniqueDeliveryList, uniqueDeliveryList);
    }

    @Test
    public void setDelivery_listWithDuplicateDeliveries_throwsDuplicateDeliveyException() {
        List<Delivery> listWithDuplicateDeliveries = Arrays.asList(APPLE, APPLE);
        assertThrows(DuplicateDeliveryException.class, () -> uniqueDeliveryList
                .setDeliveries(listWithDuplicateDeliveries));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueDeliveryList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueDeliveryList.asUnmodifiableObservableList().toString(), uniqueDeliveryList.toString());
    }
}
