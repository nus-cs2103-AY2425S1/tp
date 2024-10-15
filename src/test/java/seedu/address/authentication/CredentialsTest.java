package seedu.address.authentication;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CredentialsTest {
    @Test
    public void CredentialsTest() {
        Credentials testing = new Credentials("src/test/java/seedu/address/authentication/test.txt");
        assertTrue(testing.findUser("test1", "password"));
        assertTrue(testing.findUser("funnyfella", "heeheehaha@123"));
        assertFalse(testing.findUser("johndee", "password"));
    }
}
