package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    public void role_enumValues_success() {
        Role[] expectedRoles = {Role.BUYER, Role.SELLER};
        assertEquals(expectedRoles.length, Role.values().length);
        assertEquals(Role.BUYER, Role.values()[0]);
        assertEquals(Role.SELLER, Role.values()[1]);
    }

    @Test
    public void role_valueOf_success() {
        assertEquals(Role.BUYER, Role.valueOf("BUYER"));
        assertEquals(Role.SELLER, Role.valueOf("SELLER"));
    }

    @Test
    public void role_invalidInputForValueOf_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Role.valueOf("INVALID_ROLE"));
    }

    @Test
    public void role_toString_success() {
        assertEquals("BUYER", Role.BUYER.toString());
        assertEquals("SELLER", Role.SELLER.toString());
    }
}
