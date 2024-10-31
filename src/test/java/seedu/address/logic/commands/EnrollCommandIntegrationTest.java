package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.EnrollCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TutorialBuilder;


class EnrollCommandIntegrationTest {

    private EnrollCommandParser enrollCommandParser;
    private Model model;

    @BeforeEach
    void setUp() {
        enrollCommandParser = new EnrollCommandParser();

        // Initialize Model with predefined data
        model = new ModelManager();
        Person student = new PersonBuilder().withName("John Doe").build();
        Tutorial tutorial = new TutorialBuilder().withSubject("Physics").build();

        model.addPerson(student);
        model.createTutorial(tutorial);
    }

    @Test
    void parseAndExecute_validInput_enrollsStudent() throws ParseException, CommandException {
        // Prepare input to enroll John Doe into Physics tutorial
        String input = "1 tut/Physics";

        // Parse and execute the EnrollCommand
        EnrollCommand command = enrollCommandParser.parse(input);
        CommandResult result = command.execute(model);

        // Assertions
        assertEquals("John Doe(student) enrolled in Physics(tutorial)", result.getFeedbackToUser());

        // Verify the participation was added to the model
        Participation participation =
                new Participation(model.getFilteredPersonList().get(0), model.getFilteredTutorialList().get(0));
        assertTrue(model.hasParticipation(participation), "Model should contain the added participation");
    }

    @Test
    void parseAndExecute_invalidIndex_throwsCommandException() {
        // Input with an invalid index (out of bounds)
        String input = "5 tut/Physics";

        assertThrows(CommandException.class, () -> {
            EnrollCommand command = enrollCommandParser.parse(input);
            command.execute(model);
        });
    }

    @Test
    void parseAndExecute_invalidTutorial_throwsCommandException() {
        // Input with a non-existent tutorial subject
        String input = "1 tut/Chemistry";

        assertThrows(CommandException.class, () -> {
            EnrollCommand command = enrollCommandParser.parse(input);
            command.execute(model);
        });
    }

    @Test
    void parseAndExecute_duplicateParticipation_throwsCommandException() throws CommandException, ParseException {
        // Prepare input to enroll John Doe into Physics tutorial
        String input = "1 tut/Physics";

        // Execute once to enroll
        EnrollCommand command = enrollCommandParser.parse(input);
        command.execute(model);

        // Attempt to enroll again, expecting a duplicate error
        assertThrows(CommandException.class, () -> {
            EnrollCommand duplicateCommand = enrollCommandParser.parse(input);
            duplicateCommand.execute(model);
        });
    }
}

