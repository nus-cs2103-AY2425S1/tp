package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SkillsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Skills(null));
    }

    @Test
    public void constructor_invalidSkills_throwsIllegalArgumentException() {
        String invalidSkills = "Coding,,Communication"; // Invalid format
        assertThrows(IllegalArgumentException.class, () -> new Skills(invalidSkills));
    }

    @Test
    public void constructor_validSkills_success() {
        String validSkills = "Coding, Communication, ProblemSolving";
        Skills skills = new Skills(validSkills);
        assertTrue(skills.value.equals(validSkills));
    }

    @Test
    public void isValidSkillsString() {
        // invalid skills
        assertFalse(Skills.isValidSkillsString("")); // empty string
        assertFalse(Skills.isValidSkillsString(" ")); // spaces only
        assertFalse(Skills.isValidSkillsString("Coding, , Communication")); // double commas
        assertFalse(Skills.isValidSkillsString("A very long skill name exceeding thirty characters")); // too long

        // valid skills
        assertTrue(Skills.isValidSkillsString("Coding")); // single valid skill
        assertTrue(Skills.isValidSkillsString("Coding, Communication")); // two valid skills
        assertTrue(Skills.isValidSkillsString("ProblemSolving, CriticalThinking, Leadership")); // multiple valid skills
    }

    @Test
    public void equals_sameSkills_true() {
        Skills skills1 = new Skills("Coding, Communication");
        Skills skills2 = new Skills("Coding, Communication");
        assertTrue(skills1.equals(skills2));
    }

    @Test
    public void equals_differentSkills_false() {
        Skills skills1 = new Skills("Coding, Communication");
        Skills skills2 = new Skills("Leadership, Teamwork");
        assertFalse(skills1.equals(skills2));
    }

    @Test
    public void hashCode_sameSkills_sameHashCode() {
        Skills skills1 = new Skills("Coding, Communication");
        Skills skills2 = new Skills("Coding, Communication");
        assertTrue(skills1.hashCode() == skills2.hashCode());
    }

    @Test
    public void hashCode_differentSkills_differentHashCode() {
        Skills skills1 = new Skills("Coding, Communication");
        Skills skills2 = new Skills("Leadership, Teamwork");
        assertFalse(skills1.hashCode() == skills2.hashCode());
    }
}
