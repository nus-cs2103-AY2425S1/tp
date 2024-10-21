package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalProperties.ALICE;

import org.junit.jupiter.api.Test;

public class PropertyTest {
    @Test
    public void toStringMethod() {
        String expected = Property.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", location=" + ALICE.getLocation() + ", askingPrice=" + ALICE.getAskingPrice()
                + ", propertyType=" + ALICE.getPropertyType() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
