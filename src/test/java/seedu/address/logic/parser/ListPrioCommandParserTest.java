package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.ListPrioCommand;
import seedu.address.model.person.Priority;
import seedu.address.model.person.PriorityMatchesPredicate;


public class ListPrioCommandParserTest {
    private final ListPrioCommandParser parser = new ListPrioCommandParser();

    @Test
    public void parse_fieldsValid_success() {
        // for command "listPrio !/HIGH"
        Priority highPriority = new Priority("HIGH");
        PriorityMatchesPredicate highPriorityPredicate = new PriorityMatchesPredicate(highPriority);
        ListPrioCommand expectedListHighPriorityCommand = new ListPrioCommand(highPriorityPredicate);
        assertParseSuccess(parser, " !/HIGH", expectedListHighPriorityCommand);

        // for command "listPrio !/MEDIUM"
        Priority mediumPriority = new Priority("MEDIUM");
        PriorityMatchesPredicate mediumPriorityPredicate = new PriorityMatchesPredicate(mediumPriority);
        ListPrioCommand expectedListMediumPriorityCommand = new ListPrioCommand(mediumPriorityPredicate);
        assertParseSuccess(parser, " !/MEDIUM", expectedListMediumPriorityCommand);

        // for command "listPrio !/LOW"
        Priority lowPriority = new Priority("LOW");
        PriorityMatchesPredicate lowPriorityPredicate = new PriorityMatchesPredicate(lowPriority);
        ListPrioCommand expectedListLowPriorityCommand = new ListPrioCommand(lowPriorityPredicate);
        assertParseSuccess(parser, " !/lOW", expectedListLowPriorityCommand);

        // for command "listPrio !/NONE"
        Priority noPriority = new Priority("NONE");
        PriorityMatchesPredicate noPriorityPredicate = new PriorityMatchesPredicate(noPriority);
        ListPrioCommand expectedListNoPriorityCommand = new ListPrioCommand(noPriorityPredicate);
        assertParseSuccess(parser, " !/NONE", expectedListNoPriorityCommand);

    }

    @Test
    public void parse_fieldsInvalid_failure() {
        // invalid Priority
        assertParseFailure(parser, " !/INVALIDPRIORITY", Priority.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, NRIC_DESC_AMY, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListPrioCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_compulsoryFieldsMissing_failure() {
        // missing Priority
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListPrioCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleParameter_failure() {
        // multiple Priority
        assertParseFailure(parser, " !/HIGH !/MEDIUM",
                Messages.MESSAGE_DUPLICATE_FIELDS + PREFIX_PRIORITY);
    }
}
