package spleetwaise.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IdUtilTest {

    @Test
    public void getId_deterministic() {
        IdUtil.setDeterminate(true);
        assertEquals(IdUtil.TEST_ID, IdUtil.getId());
        IdUtil.setDeterminate(false);
    }

    @Test
    public void getId_nonDeterministic() {
        IdUtil.setDeterminate(false);
        assertNotEquals(IdUtil.TEST_ID, IdUtil.getId());
    }

    @Test
    public void isValidId_validId_returnsTrue() {
        assertTrue(IdUtil.isValidId(IdUtil.TEST_ID));
    }

    @Test
    public void isValidId_invalidId_returnsFalse() {
        assertFalse(IdUtil.isValidId("invalid-uuid-format"));
    }
}
