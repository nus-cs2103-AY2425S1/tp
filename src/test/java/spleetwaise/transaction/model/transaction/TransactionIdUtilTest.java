package spleetwaise.transaction.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class TransactionIdUtilTest {

    @Test
    public void getUuid_isDeterminate_shouldReturnFixedUuid() {
        TransactionIdUtil.setDeterminate(true);
        assertEquals("test-uuid", TransactionIdUtil.getUuid());
        TransactionIdUtil.setDeterminate(false);
    }

    @Test
    public void getUuid_isNotDeterminate_shouldReturnRandomUuid() {
        TransactionIdUtil.setDeterminate(false);
        assertNotEquals("test-uuid", TransactionIdUtil.getUuid());
    }
}

