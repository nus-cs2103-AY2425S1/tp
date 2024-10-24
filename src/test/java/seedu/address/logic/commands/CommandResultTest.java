package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Email;
import seedu.address.model.person.InterviewScore;
import seedu.address.model.person.Job;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.skill.Skill;
import seedu.address.model.tag.Tag;


public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", showHelp=" + commandResult.isShowHelp()
                + ", exit=" + commandResult.isExit() + "}";
        assertEquals(expected, commandResult.toString());
    }

    @Test
    void isView_shouldReturnTrue_whenViewIsSet() {
        CommandResult commandResult = new CommandResult("Feedback", true, null);
        boolean result = commandResult.isView();
        assertTrue(result, "Expected isView() to return true");
    }

    @Test
    void isView_shouldReturnFalse_whenViewIsNotSet() {
        CommandResult commandResult = new CommandResult("Feedback");
        boolean result = commandResult.isView();
        assertFalse(result, "Expected isView() to return false");
    }

    @Test
    void showPerson_shouldReturnPerson_whenViewIsSet() {
        Set<Tag> tags = new HashSet<>();
        Set<Skill> skills = new HashSet<>();
        Person person = new Person(new Name("John"), new Job("Software Engineer"), new Phone("12345678"),
                new Email("helloworld123@gmail.com"), skills, new InterviewScore("5.7"), tags);
        CommandResult commandResult = new CommandResult("Feedback", true, person);
        Person result = commandResult.showPerson();
        assertTrue(person.equals(result),
                "Expected showPerson() to return the same person but got: " + result);
    }


    @Test
    void showPerson_shouldReturnNull_whenViewIsNotSet() {
        CommandResult commandResult = new CommandResult("Feedback", false, null);
        Person result = commandResult.showPerson();
        assertNull(result, "Expected showPerson() to return null when view is not set");
    }

    @Test
    void showPerson_shouldReturnNull_whenNoPersonProvided() {
        CommandResult commandResult = new CommandResult("Feedback", true, null);
        Person result = commandResult.showPerson();
        assertNull(result, "Expected showPerson() to return null when no person is provided");
    }
}
