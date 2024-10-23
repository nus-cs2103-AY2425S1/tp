package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.EnumUtil.inEnum;

import org.junit.jupiter.api.Test;

public class EnumUtilTest {
    enum TestEnum {
        TEST1,
        TEST2,
        TEST3,
    }
    @Test
    public void inEnumTest() {
        assertTrue(inEnum("test1", TestEnum.class));
        assertTrue(inEnum("test2", TestEnum.class));
        assertTrue(inEnum("test3", TestEnum.class));
        assertFalse(inEnum("test4", TestEnum.class));
    }

}
