package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class CollectionUtilTest {
    @Test
    public void requireAllNonNullVarargs() {
        // no arguments
        assertNullPointerExceptionNotThrown();

        // any non-empty argument list
        assertNullPointerExceptionNotThrown(new Object(), new Object());
        assertNullPointerExceptionNotThrown("test");
        assertNullPointerExceptionNotThrown("");

        // argument lists with just one null at the beginning
        assertNullPointerExceptionThrown((Object) null);
        assertNullPointerExceptionThrown(null, "", new Object());
        assertNullPointerExceptionThrown(null, new Object(), new Object());

        // argument lists with nulls in the middle
        assertNullPointerExceptionThrown(new Object(), null, null, "test");
        assertNullPointerExceptionThrown("", null, new Object());

        // argument lists with one null as the last argument
        assertNullPointerExceptionThrown("", new Object(), null);
        assertNullPointerExceptionThrown(new Object(), new Object(), null);

        // null reference
        assertNullPointerExceptionThrown((Object[]) null);

        // confirms nulls inside lists in the argument list are not considered
        List<Object> containingNull = Arrays.asList((Object) null);
        assertNullPointerExceptionNotThrown(containingNull, new Object());
    }

    @Test
    public void requireAllNonNullCollection() {
        // lists containing nulls in the front
        assertNullPointerExceptionThrown(Arrays.asList((Object) null));
        assertNullPointerExceptionThrown(Arrays.asList(null, new Object(), ""));

        // lists containing nulls in the middle
        assertNullPointerExceptionThrown(Arrays.asList("spam", null, new Object()));
        assertNullPointerExceptionThrown(Arrays.asList("spam", null, "eggs", null, new Object()));

        // lists containing nulls at the end
        assertNullPointerExceptionThrown(Arrays.asList("spam", new Object(), null));
        assertNullPointerExceptionThrown(Arrays.asList(new Object(), null));

        // null reference
        assertNullPointerExceptionThrown((Collection<Object>) null);

        // empty list
        assertNullPointerExceptionNotThrown(Collections.emptyList());

        // list with all non-null elements
        assertNullPointerExceptionNotThrown(Arrays.asList(new Object(), "ham", Integer.valueOf(1)));
        assertNullPointerExceptionNotThrown(Arrays.asList(new Object()));

        // confirms nulls inside nested lists are not considered
        List<Object> containingNull = Arrays.asList((Object) null);
        assertNullPointerExceptionNotThrown(Arrays.asList(containingNull, new Object()));
    }

    @Test
    public void requireAllNonNullMap() {
        // null reference
        assertNullPointerExceptionThrown((Map<?, ?>) null);

        // Map containing nulls
        assertNullPointerExceptionThrown(new HashMap<>() {{
                put(null, null);
            }});

        assertNullPointerExceptionThrown(new HashMap<>() {{
                put(1, null);
            }});

        assertNullPointerExceptionThrown(new HashMap<>() {{
                put(null, 1);
            }});

        assertNullPointerExceptionThrown(new HashMap<>() {{
                put(1, null);
            }});

        // empty Map
        assertNullPointerExceptionNotThrown(Map.of());

        // valid entries
        assertNullPointerExceptionNotThrown(Map.of(1, 2, 3, 4));
    }

    @Test
    public void isAnyNonNull() {
        assertFalse(CollectionUtil.isAnyNonNull());
        assertFalse(CollectionUtil.isAnyNonNull((Object) null));
        assertFalse(CollectionUtil.isAnyNonNull((Object[]) null));
        assertTrue(CollectionUtil.isAnyNonNull(new Object()));
        assertTrue(CollectionUtil.isAnyNonNull(new Object(), null));
    }

    @Test
    public void areOfSameSize() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.areOfSameSize(new Object[]{}, null);
        });
        assertTrue(CollectionUtil.areOfSameSize());
        assertTrue(CollectionUtil.areOfSameSize(
                new Object[]{null, 1, "Hello", null, 3.14},
                new Integer[]{1, 2, 3, 4, 5}
        ));
        assertFalse(CollectionUtil.areOfSameSize(
                new Object[]{1, 2, 3},
                new Integer[]{2, 3}
        ));
        assertTrue(CollectionUtil.areOfSameSize(
                new Object[]{1, 2, 3}
        ));
        assertTrue(CollectionUtil.areOfSameSize(
                new Object[]{}
        ));
        assertTrue(CollectionUtil.areOfSameSize(
                new Object[]{},
                new String[]{}
        ));
    }

    /**
     * Asserts that {@code CollectionUtil#requireAllNonNull(Object...)} throw {@code NullPointerException}
     * if {@code objects} or any element of {@code objects} is null.
     */
    private void assertNullPointerExceptionThrown(Object... objects) {
        assertThrows(NullPointerException.class, () -> requireAllNonNull(objects));
    }

    /**
     * Asserts that {@code CollectionUtil#requireAllNonNull(Map<?, ?>)} throw {@code NullPointerException}
     * if {@code map} or any element of {@code map} is null.
     */
    private void assertNullPointerExceptionThrown(Map<?, ?> map) {
        assertThrows(NullPointerException.class, () -> requireAllNonNull(map));
    }

    /**
     * Asserts that {@code CollectionUtil#requireAllNonNull(Collection<?>)} throw {@code NullPointerException}
     * if {@code collection} or any element of {@code collection} is null.
     */
    private void assertNullPointerExceptionThrown(Collection<?> collection) {
        assertThrows(NullPointerException.class, () -> requireAllNonNull(collection));
    }

    private void assertNullPointerExceptionNotThrown(Object... objects) {
        requireAllNonNull(objects);
    }

    private void assertNullPointerExceptionNotThrown(Collection<?> collection) {
        requireAllNonNull(collection);
    }
}
