package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTRACT_END_DATE_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PromoteCommand;
import seedu.address.model.person.ContractEndDate;

public class PromoteCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, PromoteCommand.MESSAGE_USAGE);

    private PromoteCommandParser parser = new PromoteCommandParser();

    @Test
    public void parse_validIndexAndContractEndDate_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String date = VALID_CONTRACT_END_DATE_BOB;
        ContractEndDate contractEndDate = ContractEndDate.of(date);
        String userInput = targetIndex.getOneBased() + " " + date;
        PromoteCommand expectedCommand = new PromoteCommand(targetIndex, contractEndDate);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidArguments_failure() {
        // negative index
        assertParseFailure(parser, "-5 " + VALID_CONTRACT_END_DATE_BOB, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0 " + VALID_CONTRACT_END_DATE_BOB, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as index and valid contract end date
        assertParseFailure(parser, "1somerandomstring " + VALID_CONTRACT_END_DATE_BOB, MESSAGE_INVALID_FORMAT);

        // valid index and invalid contract end date
        assertParseFailure(parser, "1 2024", MESSAGE_INVALID_FORMAT);
    }
}
