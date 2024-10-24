package seedu.hireme.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Contains tests for the {@code Version} class.
 */
public class VersionTest {

    /**
     * Tests that valid version strings are parsed correctly by the {@code Version} class.
     * Verifies that the major, minor, patch numbers, and early access flag are parsed as expected.
     */
    @Test
    public void versionParsing_acceptableVersionString_parsedVersionCorrectly() {
        verifyVersionParsedCorrectly("V0.0.0ea", 0, 0, 0, true);
        verifyVersionParsedCorrectly("V3.10.2", 3, 10, 2, false);
        verifyVersionParsedCorrectly("V100.100.100ea", 100, 100, 100, true);
    }

    /**
     * Tests that invalid version strings throw an {@code IllegalArgumentException}.
     */
    @Test
    public void versionParsing_wrongVersionString_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Version.fromString("This is not a version string"));
    }

    /**
     * Tests that the {@code Version} constructor correctly assigns the major, minor, patch values,
     * and the early access flag.
     */
    @Test
    public void versionConstructor_correctParameter_valueAsExpected() {
        Version version = new Version(19, 10, 20, true);

        assertEquals(19, version.getMajor());
        assertEquals(10, version.getMinor());
        assertEquals(20, version.getPatch());
        assertEquals(true, version.isEarlyAccess());
    }

    /**
     * Tests the {@code toString} method of the {@code Version} class.
     * Verifies that the string representation matches the expected format.
     */
    @Test
    public void versionToString_validVersion_correctStringRepresentation() {
        Version version = new Version(0, 0, 0, true);
        assertEquals("V0.0.0ea", version.toString());

        version = new Version(4, 10, 5, false);
        assertEquals("V4.10.5", version.toString());

        version = new Version(100, 100, 100, true);
        assertEquals("V100.100.100ea", version.toString());
    }

    /**
     * Tests the {@code compareTo} method of the {@code Version} class.
     * Verifies that the comparison between versions behaves correctly according to the version numbers.
     */
    @Test
    public void versionComparable_validVersion_compareToIsCorrect() {
        Version one;
        Version another;

        // Tests equality
        one = new Version(0, 0, 0, true);
        another = new Version(0, 0, 0, true);
        assertTrue(one.compareTo(another) == 0);

        one = new Version(11, 12, 13, false);
        another = new Version(11, 12, 13, false);
        assertTrue(one.compareTo(another) == 0);

        // Tests different patch
        one = new Version(0, 0, 5, false);
        another = new Version(0, 0, 0, false);
        assertTrue(one.compareTo(another) > 0);

        // Tests different minor
        one = new Version(0, 0, 0, false);
        another = new Version(0, 5, 0, false);
        assertTrue(one.compareTo(another) < 0);

        // Tests different major
        one = new Version(10, 0, 0, true);
        another = new Version(0, 0, 0, true);
        assertTrue(one.compareTo(another) > 0);

        // Tests high major vs low minor
        one = new Version(10, 0, 0, true);
        another = new Version(0, 1, 0, true);
        assertTrue(one.compareTo(another) > 0);

        // Tests high patch vs low minor
        one = new Version(0, 0, 10, false);
        another = new Version(0, 1, 0, false);
        assertTrue(one.compareTo(another) < 0);

        // Tests same major minor different patch
        one = new Version(2, 15, 0, false);
        another = new Version(2, 15, 5, false);
        assertTrue(one.compareTo(another) < 0);

        // Tests early access vs not early access on same version number
        one = new Version(2, 15, 0, true);
        another = new Version(2, 15, 0, false);
        assertTrue(one.compareTo(another) < 0);

        // Tests early access lower version vs not early access higher version compare by version number first
        one = new Version(2, 15, 0, true);
        another = new Version(2, 15, 5, false);
        assertTrue(one.compareTo(another) < 0);

        // Tests early access higher version vs not early access lower version compare by version number first
        one = new Version(2, 15, 0, false);
        another = new Version(2, 15, 5, true);
        assertTrue(one.compareTo(another) < 0);
    }

    /**
     * Tests that the {@code hashCode} method generates the correct hash code for the {@code Version} class.
     */
    @Test
    public void versionComparable_validVersion_hashCodeIsCorrect() {
        Version version = new Version(100, 100, 100, true);
        assertEquals(100100100, version.hashCode());

        version = new Version(10, 10, 10, false);
        assertEquals(1010010010, version.hashCode());
    }

    /**
     * Tests that the {@code equals} method correctly compares two {@code Version} objects for equality.
     */
    @Test
    public void versionComparable_validVersion_equalIsCorrect() {
        Version one;
        Version another;

        one = new Version(0, 0, 0, false);
        another = new Version(0, 0, 0, false);
        assertTrue(one.equals(another));

        one = new Version(100, 191, 275, true);
        another = new Version(100, 191, 275, true);
        assertTrue(one.equals(another));
    }

    /**
     * Verifies that the {@code Version} is parsed correctly from a string representation.
     *
     * @param versionString The version string to be parsed.
     * @param major         The expected major version.
     * @param minor         The expected minor version.
     * @param patch         The expected patch version.
     * @param isEarlyAccess Indicates if the version is an early access version.
     */
    private void verifyVersionParsedCorrectly(String versionString,
                                              int major, int minor, int patch, boolean isEarlyAccess) {
        assertEquals(new Version(major, minor, patch, isEarlyAccess), Version.fromString(versionString));
    }
}
