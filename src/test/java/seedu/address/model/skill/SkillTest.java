package seedu.address.model.skill;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SkillTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Skill(null));
    }

    @Test
    public void constructor_invalidSkillName_throwsIllegalArgumentException() {
        String invalidSkillName = "";
        assertThrows(IllegalArgumentException.class, () -> new Skill(invalidSkillName));
    }

    @Test
    public void isValidSkillName() {
        // null skill name
        assertThrows(NullPointerException.class, () -> Skill.isValidSkillName(null));
    }

    @Test
    public void equals() {
        Skill skill = new Skill("Valid Skill");

        // same values -> returns true
        assertTrue(skill.equals(new Skill("Valid Skill")));

        // same object -> returns true
        assertTrue(skill.equals(skill));

        // different types -> returns false
        assertFalse(skill.equals(5.0f));

        // different values -> returns false
        assertFalse(skill.equals(new Skill("Other Valid Skill")));
    }

}
