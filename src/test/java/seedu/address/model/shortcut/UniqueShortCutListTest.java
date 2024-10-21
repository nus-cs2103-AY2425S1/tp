package seedu.address.model.shortcut;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.shortcut.exceptions.DuplicateShortCutException;
import seedu.address.model.shortcut.exceptions.ShortCutNotFoundException;

public class UniqueShortCutListTest {

    private UniqueShortCutList uniqueShortCutList;
    private ShortCut veganShortCut;
    private ShortCut vegetarianShortCut;

    @BeforeEach
    public void setUp() {
        uniqueShortCutList = new UniqueShortCutList();
        veganShortCut = new ShortCut(new Alias("v"), new FullTagName("Vegan"));
        vegetarianShortCut = new ShortCut(new Alias("vg"), new FullTagName("Vegetarian"));
    }

    @Test
    public void add_shortcut_success() {
        uniqueShortCutList.add(veganShortCut);
        assertTrue(uniqueShortCutList.contains(veganShortCut));
    }
    @Test
    void containsAlias_aliasNotInList_returnsFalse() {
        // Alias not in list
        Alias aliasToCheck = new Alias("v");
        assertFalse(uniqueShortCutList.containsAlias(aliasToCheck));
    }

    @Test
    void containsAlias_aliasInList_returnsTrue() {
        // Add shortcut1 to the list
        uniqueShortCutList.add(veganShortCut);

        // Alias v should now be in the list
        Alias aliasToCheck = new Alias("v");
        assertTrue(uniqueShortCutList.containsAlias(aliasToCheck));
    }

    @Test
    void containsAlias_differentAlias_returnsFalse() {
        // Add shortcut1 to the list
        uniqueShortCutList.add(veganShortCut);

        // Alias vg is not in the list, should return false
        Alias aliasToCheck = new Alias("vg");
        assertFalse(uniqueShortCutList.containsAlias(aliasToCheck));
    }

    @Test
    void containsAlias_multipleShortcuts_correctAliasCheck() {
        // Add multiple shortcuts
        uniqueShortCutList.add(veganShortCut);
        uniqueShortCutList.add(vegetarianShortCut);

        // Alias v should be in the list
        Alias aliasToCheckV = new Alias("v");
        assertTrue(uniqueShortCutList.containsAlias(aliasToCheckV));

        // Alias vg should be in the list
        Alias aliasToCheckVG = new Alias("vg");
        assertTrue(uniqueShortCutList.containsAlias(aliasToCheckVG));
    }

    @Test
    public void add_duplicateShortcut_throwsDuplicateShortCutException() {
        uniqueShortCutList.add(veganShortCut);
        assertThrows(DuplicateShortCutException.class, () -> uniqueShortCutList.add(veganShortCut));
    }

    @Test
    public void remove_existingShortcut_success() {
        uniqueShortCutList.add(veganShortCut);
        uniqueShortCutList.remove(veganShortCut);
        assertFalse(uniqueShortCutList.contains(veganShortCut));
    }

    @Test
    public void remove_nonExistingShortcut_throwsShortCutNotFoundException() {
        assertThrows(ShortCutNotFoundException.class, () -> uniqueShortCutList.remove(veganShortCut));
    }

    @Test
    public void setShortCuts_uniqueShortCuts_success() {
        List<ShortCut> newShortCuts = Arrays.asList(veganShortCut, vegetarianShortCut);
        uniqueShortCutList.setShortCuts(newShortCuts);
        assertEquals(newShortCuts, uniqueShortCutList.asUnmodifiableObservableList());
    }

    @Test
    public void setShortCuts_duplicateShortCuts_throwsDuplicateShortCutException() {
        List<ShortCut> duplicateShortCuts = Arrays.asList(veganShortCut, veganShortCut);
        assertThrows(DuplicateShortCutException.class, () -> uniqueShortCutList.setShortCuts(duplicateShortCuts));
    }

    @Test
    public void setShortCutsFromList_uniqueShortCuts_success() {
        UniqueShortCutList replacement = new UniqueShortCutList();
        replacement.add(veganShortCut);
        uniqueShortCutList.setShortCuts(replacement);
        assertEquals(replacement.asUnmodifiableObservableList(), uniqueShortCutList.asUnmodifiableObservableList());
    }

    @Test
    public void contains_shortcutInList_returnsTrue() {
        uniqueShortCutList.add(veganShortCut);
        assertTrue(uniqueShortCutList.contains(veganShortCut));
    }

    @Test
    public void contains_shortcutNotInList_returnsFalse() {
        assertFalse(uniqueShortCutList.contains(veganShortCut));
    }

    @Test
    public void equals_sameList_returnsTrue() {
        uniqueShortCutList.add(veganShortCut);
        UniqueShortCutList anotherList = new UniqueShortCutList();
        anotherList.add(veganShortCut);
        assertEquals(uniqueShortCutList, anotherList);
    }

    @Test
    public void equals_differentList_returnsFalse() {
        uniqueShortCutList.add(veganShortCut);
        UniqueShortCutList anotherList = new UniqueShortCutList();
        anotherList.add(vegetarianShortCut);
        assertNotEquals(uniqueShortCutList, anotherList);
    }

    @Test
    public void hashCode_sameList_sameHashCode() {
        uniqueShortCutList.add(veganShortCut);
        UniqueShortCutList anotherList = new UniqueShortCutList();
        anotherList.add(veganShortCut);
        assertEquals(uniqueShortCutList.hashCode(), anotherList.hashCode());
    }

    @Test
    public void hashCode_differentList_differentHashCode() {
        uniqueShortCutList.add(veganShortCut);
        UniqueShortCutList anotherList = new UniqueShortCutList();
        anotherList.add(vegetarianShortCut);
        assertNotEquals(uniqueShortCutList.hashCode(), anotherList.hashCode());
    }

    @Test
    public void toString_producesCorrectString() {
        uniqueShortCutList.add(veganShortCut);
        assertEquals("[" + veganShortCut.toString() + "]", uniqueShortCutList.toString());
    }
}
