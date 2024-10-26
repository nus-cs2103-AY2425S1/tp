package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.property.TypicalProperties.ALICE;

import org.junit.jupiter.api.Test;

public class PropertyTest {
    @Test
    public void toStringMethod() {
        String expected = Property.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", address=" + ALICE.getAddress() + ", askingPrice=" + ALICE.getAskingPrice()
                + ", propertyType=" + ALICE.getPropertyType() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
