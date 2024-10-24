package seedu.academyassist.logic.parser;

import static seedu.academyassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.academyassist.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.academyassist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.academyassist.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.academyassist.logic.commands.AddClassCommand;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.model.person.StudentId;
import seedu.academyassist.model.person.Subject;
import seedu.academyassist.testutil.PersonBuilder;

public class AddClassCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClassCommand.MESSAGE_USAGE);

    private AddClassCommandParser parser = new AddClassCommandParser();


    @Test
    public void parse_missingParts_failure() {
        //no studentId specified
        assertParseFailure(parser, "s/Math", MESSAGE_INVALID_FORMAT);

        //no class specified
        assertParseFailure(parser, "S00001", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // exceeds 5 digits
        assertParseFailure(parser, "S100010" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // incorrect Student ID format
        assertParseFailure(parser, "S0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "S10001 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "S10001 j/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "S10001" + "s/SomethingWrong", MESSAGE_INVALID_FORMAT); // invalid subject
    }

    @Test
    public void parse_success() {
        // studentId: S00008, subjects: Math
        Person validPerson = new PersonBuilder().build();
        Set<Subject> subjects = new HashSet<>();
        subjects.add(new Subject("Math"));
        subjects.add(new Subject("Chinese"));
        AddClassCommand addClassCommand = new AddClassCommand(new StudentId("S00008"), subjects);
        assertParseSuccess(parser, "S00008 s/Math s/Chinese", addClassCommand);
    }


}
