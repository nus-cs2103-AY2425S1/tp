package seedu.address.model.link;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LinkableTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LinkableStub(null));
    }

    @Test
    public void validLinkableIDs() {
        assertTrue(new LinkableStub("12345").getUniqueID().equals("12345"));
        assertTrue(new LinkableStub("abc12345").getUniqueID().equals("abc12345"));
    }

    private class LinkableStub implements Linkable {
        private final String uniqueID;

        public LinkableStub(String uniqueID) {
            requireNonNull(uniqueID);
            this.uniqueID = uniqueID;
        }

        @Override
        public void addLinkedEntity(Linkable entity) {
            assertFalse(false); // method should not be called
        }

        @Override
        public void removeLinkedEntity(Linkable entity) {
            assertFalse(false); // method should not be called
        }

        @Override
        public String getUniqueID() {
            return uniqueID;
        }

        @Override
        public String getInfo() {
            return uniqueID;
        }
    }
}
