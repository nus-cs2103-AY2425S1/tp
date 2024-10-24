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
    public void constructor_invalidSkill_throwsIllegalArgumentException() {
        String invalidSkill = "";
        assertThrows(IllegalArgumentException.class, () -> new Skill(invalidSkill));
    }

    @Test
    public void isValidSkillName() {
        // null skill
        assertThrows(AssertionError.class, () -> Skill.isValidSkill(null));

        // invalid skills
        assertFalse(Skill.isValidSkill("")); // sempty string
        assertFalse(Skill.isValidSkill(" ")); // spaces only
        assertFalse(Skill.isValidSkill("software engineer")); // spaces within skill
        assertFalse(Skill.isValidSkill("ui/ux")); // non alphanumeric characters

        // valid skills
        assertTrue(Skill.isValidSkill("a")); // exactly 1 character
        assertTrue(Skill.isValidSkill("swe")); // alphabets only
        assertTrue(Skill.isValidSkill("softwareEngineer")); // Mixed case
        assertTrue(Skill.isValidSkill("123")); // numbers only
        assertTrue(Skill.isValidSkill("web3")); // alphanumeric
        assertTrue(Skill.isValidSkill(
                "softwareInTestSeniorEngineerForWeb3andSaasDevelopment")); // long skill
    }

    @Test
    public void equals() {
        Skill skill = new Skill("swe");

        // same values -> returns true
        assertTrue(skill.equals(new Skill("swe")));

        // same object -> returns true
        assertTrue(skill.equals(skill));

        // null -> returns false
        assertFalse(skill.equals(null));

        // different types -> returns false
        assertFalse(skill.equals(5.0f));

        // different values -> returns false
        assertFalse(skill.equals(new Skill("backend")));
    }

}
