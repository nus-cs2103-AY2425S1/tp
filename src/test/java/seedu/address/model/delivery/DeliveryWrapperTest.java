package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDeliveries.APPLE;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TypicalDeliveryWrappers;

public class DeliveryWrapperTest {
    @Test
    public void constructor_nullValues_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeliveryWrapper(null, null));
    }

    @Test
    public void validValues() {
        DeliveryWrapper wrapper = new DeliveryWrapper(APPLE, new SupplierIndex("1"));
        assertEquals(APPLE, wrapper.getDelivery());
        assertEquals(new SupplierIndex("1"), wrapper.getSupplierIndex());
    }

    @Test
    public void setDeliverySupplier_nullSupplier_throwsNullPointerException() {
        DeliveryWrapper wrapper = TypicalDeliveryWrappers.getNullWrapper();
        assertThrows(NullPointerException.class, () -> wrapper.setDeliverySupplier(null));
    }

    @Test
    public void equals() {
        DeliveryWrapper wrapperApple = TypicalDeliveryWrappers.APPLE_WRAPPER;
        DeliveryWrapper wrapperBread = TypicalDeliveryWrappers.BREAD_WRAPPER;
        DeliveryWrapper wrapperAppleCopy = new DeliveryWrapper(APPLE, new SupplierIndex("1"));

        // same values -> returns true
        assertTrue(wrapperApple.equals(wrapperAppleCopy));

        // same object -> returns true
        assertTrue(wrapperApple.equals(wrapperApple));

        // null -> returns false
        assertFalse(wrapperApple.equals(null));

        // different type -> returns false
        assertFalse(wrapperApple.equals("Acdef"));

        // different delivery -> returns false
        assertFalse(wrapperApple.equals(wrapperBread));

        // different supplier index -> returns false
        DeliveryWrapper wrapperAppleDifferentIndex = new DeliveryWrapper(APPLE, new SupplierIndex("1"));
        assertFalse(APPLE.equals(wrapperAppleDifferentIndex));
    }
}
