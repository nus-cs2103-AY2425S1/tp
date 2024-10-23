package hallpointer.address.model.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

/**
 * Tests whether the SampleDataUtil's data remain valid after changes elsewhere.
 */
public class SampleDataUtilTest {

    @Test
    public void getSampleMembers_success() {
        assertDoesNotThrow(SampleDataUtil::getSampleMembers);
    }

    @Test
    public void getSampleAddressBook_success() {
        assertDoesNotThrow(SampleDataUtil::getSampleAddressBook);
    }
}
