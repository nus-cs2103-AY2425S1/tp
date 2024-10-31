package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.UnenrollCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TutorialBuilder;

class UnenrollCommandIntegrationTest {

    private UnenrollCommandParser unenrollCommandParser;
    private Model model;
    private Participation participation;

    @BeforeEach
    void setUp() throws CommandException {
        unenrollCommandParser = new UnenrollCommandParser();

        // Initialize Model with predefined data and enroll the student
        model = new ModelManager();
        Person student = new PersonBuilder().withName("John Doe").build();
        Tutorial tutorial = new TutorialBuilder().withSubject("Physics").build();

        model.addPerson(student);
        model.createTutorial(tutorial);

        participation = new Participation(student, tutorial);
        model.addParticipation(participation); // Pre-enroll the student
    }

    @Test
    void parseAndExecute_validInput_unenrollsStudent() throws ParseException, CommandException {
        // Prepare input to unenroll John Doe from Physics tutorial
        String input = "1 tut/Physics";

        // Parse and execute the UnenrollCommand
        UnenrollCommand command = unenrollCommandParser.parse(input);
        CommandResult result = command.execute(model);

        // Assertions
        assertEquals("John Doe(student) no longer enrolled in Physics(tutorial)", result.getFeedbackToUser());

        // Verify the participation was removed from the model
        assertFalse(model.hasParticipation(participation),
                "Model should not contain the removed participation");
    }

    @Test
    void parseAndExecute_invalidIndex_throwsCommandException() {
        // Input with an invalid index (out of bounds)
        String input = "5 tut/Physics";

        assertThrows(CommandException.class, () -> {
            UnenrollCommand command = unenrollCommandParser.parse(input);
            command.execute(model);
        });
    }

    @Test
    void parseAndExecute_invalidTutorial_throwsCommandException() {
        // Input with a non-existent tutorial subject
        String input = "1 tut/Chemistry";

        assertThrows(CommandException.class, () -> {
            UnenrollCommand command = unenrollCommandParser.parse(input);
            command.execute(model);
        });
    }

    @Test
    void parseAndExecute_noParticipation_throwsCommandException() throws ParseException, CommandException {
        // Prepare input to unenroll John Doe from Physics tutorial
        String input = "1 tut/Physics";

        // First, unenroll the student successfully
        UnenrollCommand command = unenrollCommandParser.parse(input);
        command.execute(model);

        // Try to unenroll the same student again, expecting a CommandException
        assertThrows(CommandException.class, () -> {
            UnenrollCommand duplicateCommand = unenrollCommandParser.parse(input);
            duplicateCommand.execute(model);
        });
    }
}

