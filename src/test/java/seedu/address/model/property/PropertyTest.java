package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.property.TypicalProperties.ALAN;
import static seedu.address.testutil.property.TypicalProperties.ALICE;

import org.junit.jupiter.api.Test;

public class PropertyTest {
    @Test
    public void toStringMethod() {
        String expected = Property.class.getCanonicalName() + "{name=" + ALICE.getLandlordName() + ", phone=" + ALICE.getPhone()
                + ", address=" + ALICE.getAddress() + ", askingPrice=" + ALICE.getAskingPrice()
                + ", propertyType=" + ALICE.getPropertyType() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
