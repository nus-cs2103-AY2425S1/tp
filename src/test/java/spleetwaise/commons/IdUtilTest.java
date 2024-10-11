package spleetwaise.commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
        assertNotEquals(IdUtil.TEST_ID, IdUtil.getId());
    }

}
