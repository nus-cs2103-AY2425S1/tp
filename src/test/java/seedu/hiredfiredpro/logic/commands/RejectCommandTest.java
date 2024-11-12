package seedu.hiredfiredpro.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.hiredfiredpro.logic.commands.exceptions.CommandException;
import seedu.hiredfiredpro.model.Model;
import seedu.hiredfiredpro.model.ModelManager;
import seedu.hiredfiredpro.model.person.Email;
import seedu.hiredfiredpro.model.person.InterviewScore;
import seedu.hiredfiredpro.model.person.Job;
import seedu.hiredfiredpro.model.person.Name;
import seedu.hiredfiredpro.model.person.Person;
import seedu.hiredfiredpro.model.person.Phone;
import seedu.hiredfiredpro.model.skill.Skill;
import seedu.hiredfiredpro.model.tag.Tag;
public class RejectCommandTest {

    private Model model = new ModelManager();

    // Valid Input (Change an existing candidate's status to Rejected)
    @Test
    public void execute_personAcceptedByModel_rejectSuccessful() throws Exception {
        Person validPerson = new Person(
                new Name("Amy Bee"),
                new Job("Software Engineer"),
                new Phone("85355255"),
                new Email("amy@gmail.com"),
                new HashSet<>(Set.of(new Skill("python"))),
                new InterviewScore("6"),
                new HashSet<>(Set.of(new Tag("pending")))
        );
        model.addPerson(validPerson);

        RejectCommand rejectCommand = new RejectCommand(validPerson.getName(), validPerson.getJob());
        rejectCommand.execute(model);

        Person validPersonRejected = model.findPersonByNameAndJob(validPerson.getName(), validPerson.getJob());
        // Check the status and tags of the person
        assertEquals("rejected", validPersonRejected.getStatus());
        assertTrue(validPersonRejected.getTags().contains(Person.TAG_REJECTED));
        assertFalse(validPersonRejected.getTags().contains(Person.DEFAULT_TAG_PENDING));
        assertFalse(validPersonRejected.getTags().contains(Person.TAG_HIRED));
    }

    // Invalid Input (Candidate's status is already rejected when executing reject command)
    @Test
    public void execute_personAlreadyRejected_throwsCommandException() {
        Person validPerson = new Person(
                new Name("Amy Bee"),
                new Job("Software Engineer"),
                new Phone("85355255"),
                new Email("amy@gmail.com"),
                new HashSet<>(Set.of(new Skill("python"))),
                new InterviewScore("6"),
                new HashSet<>(Set.of(new Tag("rejected")))
        );
        model.addPerson(validPerson);

        RejectCommand rejectCommand = new RejectCommand(validPerson.getName(), validPerson.getJob());

        assertThrows(CommandException.class, () -> rejectCommand.execute(model),
                "Error: Candidate Amy Bee is already marked as rejected.");
    }

    // Invalid Input (Candidate does not exist in the database)
    @Test
    public void execute_personNotFound_throwsPersonNotFoundException() {
        Person nonExistentPerson = new Person(
                new Name("John Doe"),
                new Job("Software Engineer"),
                new Phone("85355255"),
                new Email("john@gmail.com"),
                new HashSet<>(Set.of(new Skill("python"))),
                new InterviewScore("6"),
                new HashSet<>(Set.of(new Tag("pending")))
        );

        RejectCommand rejectCommand = new RejectCommand(nonExistentPerson.getName(), nonExistentPerson.getJob());

        assertThrows(CommandException.class, () -> rejectCommand.execute(model));
    }

    // Invalid Input (Specified job in input does not match candidate's job in database)
    @Test
    public void execute_personWithDifferentJob_throwsPersonNotFoundException() {
        Person validPerson = new Person(
                new Name("Amy Bee"),
                new Job("Data Analyst"),
                new Phone("85355255"),
                new Email("amy@gmail.com"),
                new HashSet<>(Set.of(new Skill("python"))),
                new InterviewScore("6"),
                new HashSet<>(Set.of(new Tag("pending")))
        );
        model.addPerson(validPerson);

        RejectCommand rejectCommand = new RejectCommand(new Name("Amy Bee"), new Job("Software Engineer"));

        assertThrows(CommandException.class, () -> rejectCommand.execute(model));
    }

    // Valid Input (Change an existing candidate's status to Rejected and the candidate has multiple tags)
    @Test
    public void execute_personWithMultipleTags_rejectSuccessful() throws Exception {
        Person validPerson = new Person(
                new Name("Amy Bee"),
                new Job("Software Engineer"),
                new Phone("85355255"),
                new Email("amy@gmail.com"),
                new HashSet<>(Set.of(new Skill("python"))),
                new InterviewScore("6"),
                new HashSet<>(Set.of(new Tag("pending"), new Tag("interviewed")))
        );
        model.addPerson(validPerson);

        RejectCommand rejectCommand = new RejectCommand(validPerson.getName(), validPerson.getJob());
        rejectCommand.execute(model);

        Person validPersonRejected = model.findPersonByNameAndJob(validPerson.getName(), validPerson.getJob());
        // Check the status and tags of the person
        assertEquals("rejected", validPersonRejected.getStatus());
        assertTrue(validPersonRejected.getTags().contains(Person.TAG_REJECTED));
        assertFalse(validPersonRejected.getTags().contains(Person.DEFAULT_TAG_PENDING));
        assertFalse(validPersonRejected.getTags().contains(Person.TAG_HIRED));
        assertTrue(validPersonRejected.getTags().contains(new Tag("interviewed")));
    }

    // Tests if the test message matches the exception message being thrown
    @Test
    public void execute_jobNotFound_throwsJobNotFoundException() {
        Person validPerson = new Person(
                new Name("Amy Bee"),
                new Job("Software Engineer"),
                new Phone("85355255"),
                new Email("amy@gmail.com"),
                new HashSet<>(Set.of(new Skill("python"))),
                new InterviewScore("6"),
                new HashSet<>(Set.of(new Tag("pending")))
        );
        model.addPerson(validPerson);

        RejectCommand rejectCommand = new RejectCommand(new Name("Amy Bee"), new Job("Data Scientist"));

        assertThrows(CommandException.class, () -> rejectCommand.execute(model),
                "Error: Job not found.");
    }
}
