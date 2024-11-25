package seedu.hireme.model.internshipapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Role(null));
    }

    @Test
    public void constructor_emptyRole_throwsIllegalArgumentException() {
        String emptyRole = "";
        assertThrows(IllegalArgumentException.class, () -> new Role(emptyRole));
    }

    @Test
    public void constructor_validRole_success() {
        // Alphanumeric characters
        Role role1 = new Role("Software Engineer");
        assertEquals("Software Engineer", role1.toString());

        // Alphanumeric with allowed special character '/'
        Role role2 = new Role("Data Scientist/Engineer");
        assertEquals("Data Scientist/Engineer", role2.toString());

        // Multiple words with spaces
        Role role3 = new Role("Product Manager");
        assertEquals("Product Manager", role3.toString());
    }

    @Test
    public void constructor_invalidRole_throwsIllegalArgumentException() {
        // Special characters not allowed
        assertThrows(IllegalArgumentException.class, () -> new Role("Engineer!"));
        assertThrows(IllegalArgumentException.class, () -> new Role("Data@Analyst"));
        assertThrows(IllegalArgumentException.class, () -> new Role("Manager*Lead"));
        assertThrows(IllegalArgumentException.class, () -> new Role("SWE#1"));
    }

    @Test
    public void equals() {
        Role role = new Role("Valid Role");

        // same values -> returns true
        assertTrue(role.equals(new Role("Valid Role")));

        // same object -> returns true
        assertTrue(role.equals(role));

        // null -> returns false
        assertFalse(role.equals(null));

        // different types -> returns false
        assertFalse(role.equals(5.0f));

        // different values -> returns false
        assertFalse(role.equals(new Role("Other Valid Role")));
    }

    @Test
    public void toStringMethod() {
        Role role = new Role("Valid Role");
        assertEquals("Valid Role", role.toString());
    }

    @Test
    public void hashCode_method() {
        Role role = new Role("Unique Role");
        Role sameRole = new Role("Unique Role");

        // same value -> same hashcode
        assertEquals(role.hashCode(), sameRole.hashCode());

        // different value -> different hashcode
        Role differentRole = new Role("Different Role");
        assertFalse(role.hashCode() == differentRole.hashCode());
    }

    @Test
    public void validRoleFormats() {
        // Testing valid roles with spaces, alphanumeric characters, and slashes
        Role role1 = new Role("Backend Developer");
        assertEquals("Backend Developer", role1.toString());

        Role role2 = new Role("Full Stack Engineer");
        assertEquals("Full Stack Engineer", role2.toString());

        Role role3 = new Role("Business Analyst/Consultant");
        assertEquals("Business Analyst/Consultant", role3.toString());

        Role role4 = new Role("Data Scientist");
        assertEquals("Data Scientist", role4.toString());

        Role role5 = new Role("Engineer III");
        assertEquals("Engineer III", role5.toString());
    }

    @Test
    public void invalidRoleFormats() {
        // Invalid roles with special characters or unexpected formats
        assertThrows(IllegalArgumentException.class, () -> new Role("Lead Engineer*"));
        assertThrows(IllegalArgumentException.class, () -> new Role("Senior Dev!"));
        assertThrows(IllegalArgumentException.class, () -> new Role("Project Manager@Corp"));
        assertThrows(IllegalArgumentException.class, () -> new Role("Intern#1"));
        assertThrows(IllegalArgumentException.class, () -> new Role("QA Tester!"));
    }

    @Test
    public void trimWhitespaceInConstructor() {
        // Leading and trailing whitespace should be trimmed
        Role role = new Role("   Software Engineer   ");
        assertEquals("Software Engineer", role.toString());

        Role role2 = new Role("  Data Analyst  ");
        assertEquals("Data Analyst", role2.toString());
    }

    @Test
    public void caseInsensitiveEquality() {
        Role role1 = new Role("Product Manager");
        Role role2 = new Role("product manager");

        // Same values in different cases -> should return true
        assertTrue(role1.equals(role2));
    }

    @Test
    public void test_roleWithNumbers_success() {
        Role role = new Role("Developer 2");
        assertEquals("Developer 2", role.toString());

        Role role2 = new Role("Engineer Level 3");
        assertEquals("Engineer Level 3", role2.toString());
    }
}
