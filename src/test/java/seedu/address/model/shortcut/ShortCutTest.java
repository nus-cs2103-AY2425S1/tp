package seedu.address.model.shortcut;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class ShortCutTest {

    @Test
    public void equals_sameObject_returnsTrue() {
        Alias alias = new Alias("v");
        FullTagName fullTagName = new FullTagName("Vegan");
        ShortCut shortCut = new ShortCut(alias, fullTagName);

        // Check if the same object is equal to itself
        assertEquals(shortCut, shortCut);
    }

    @Test
    public void equals_differentObjectSameFields_returnsTrue() {
        Alias alias1 = new Alias("v");
        FullTagName fullTagName1 = new FullTagName("Vegan");
        ShortCut shortCut1 = new ShortCut(alias1, fullTagName1);

        Alias alias2 = new Alias("v");
        FullTagName fullTagName2 = new FullTagName("Vegan");
        ShortCut shortCut2 = new ShortCut(alias2, fullTagName2);

        // Check if two objects with the same fields are considered equal
        assertEquals(shortCut1, shortCut2);
    }

    @Test
    public void equals_differentAlias_returnsFalse() {
        Alias alias1 = new Alias("v");
        FullTagName fullTagName1 = new FullTagName("Vegan");
        ShortCut shortCut1 = new ShortCut(alias1, fullTagName1);

        Alias alias2 = new Alias("vg");
        FullTagName fullTagName2 = new FullTagName("Vegan");
        ShortCut shortCut2 = new ShortCut(alias2, fullTagName2);

        // Check if objects with different aliases are not considered equal
        assertNotEquals(shortCut1, shortCut2);
    }

    @Test
    public void equals_differentTagName_returnsFalse() {
        Alias alias1 = new Alias("v");
        FullTagName fullTagName1 = new FullTagName("Vegan");
        ShortCut shortCut1 = new ShortCut(alias1, fullTagName1);

        Alias alias2 = new Alias("v");
        FullTagName fullTagName2 = new FullTagName("Vegetarian");
        ShortCut shortCut2 = new ShortCut(alias2, fullTagName2);

        // Check if objects with different full tag names are not considered equal
        assertNotEquals(shortCut1, shortCut2);
    }

    @Test
    public void hashCode_sameObjectSameFields_returnsSameHashCode() {
        Alias alias1 = new Alias("v");
        FullTagName fullTagName1 = new FullTagName("Vegan");
        ShortCut shortCut1 = new ShortCut(alias1, fullTagName1);

        Alias alias2 = new Alias("v");
        FullTagName fullTagName2 = new FullTagName("Vegan");
        ShortCut shortCut2 = new ShortCut(alias2, fullTagName2);

        // Check if two objects with the same fields have the same hash code
        assertEquals(shortCut1.hashCode(), shortCut2.hashCode());
    }

    @Test
    public void hashCode_differentAlias_returnsDifferentHashCode() {
        Alias alias1 = new Alias("v");
        FullTagName fullTagName1 = new FullTagName("Vegan");
        ShortCut shortCut1 = new ShortCut(alias1, fullTagName1);

        Alias alias2 = new Alias("vg");
        FullTagName fullTagName2 = new FullTagName("Vegan");
        ShortCut shortCut2 = new ShortCut(alias2, fullTagName2);

        // Check if two objects with different aliases have different hash codes
        assertNotEquals(shortCut1.hashCode(), shortCut2.hashCode());
    }

    @Test
    public void hashCode_differentFullTagName_returnsDifferentHashCode() {
        Alias alias1 = new Alias("v");
        FullTagName fullTagName1 = new FullTagName("Vegan");
        ShortCut shortCut1 = new ShortCut(alias1, fullTagName1);

        Alias alias2 = new Alias("v");
        FullTagName fullTagName2 = new FullTagName("Vegetarian");
        ShortCut shortCut2 = new ShortCut(alias2, fullTagName2);

        // Check if two objects with different full tag names have different hash codes
        assertNotEquals(shortCut1.hashCode(), shortCut2.hashCode());
    }
    @Test
    public void equals_differentObject() {
        ShortCut shortcut1 = new ShortCut(new Alias("v"), new FullTagName("Vegan"));
        assertNotEquals(shortcut1, new Alias("v"));
    }
}
