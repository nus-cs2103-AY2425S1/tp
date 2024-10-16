package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RoleTypeTest {
    @Test
    public void toStringTest() {
        assertEquals("Student", RoleType.STUDENT.toString());
        assertEquals("Tutor", RoleType.TUTOR.toString());
        assertEquals("Professor", RoleType.PROFESSOR.toString());
    }

    @Test
    public void isValidRoleTypeKeyword() {
        assertTrue(RoleType.isValidRoleTypeKeyword(""));
        assertTrue(RoleType.isValidRoleTypeKeyword("Student"));
        assertTrue(RoleType.isValidRoleTypeKeyword("STUDENT"));
        assertTrue(RoleType.isValidRoleTypeKeyword("Tutor"));
        assertTrue(RoleType.isValidRoleTypeKeyword("TUTOR"));
        assertTrue(RoleType.isValidRoleTypeKeyword("TA"));
        assertTrue(RoleType.isValidRoleTypeKeyword("ta"));
        assertTrue(RoleType.isValidRoleTypeKeyword("Professor"));
        assertTrue(RoleType.isValidRoleTypeKeyword("professor"));
        assertTrue(RoleType.isValidRoleTypeKeyword("PROF"));
        assertTrue(RoleType.isValidRoleTypeKeyword("Prof"));
        assertTrue(RoleType.isValidRoleTypeKeyword("prof"));
    }
}
