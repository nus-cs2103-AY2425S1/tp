package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.ArgumentPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // Person with 1 parameter
        Person testPerson = new PersonBuilder().withName(VALID_NAME_BOB)
                .withPhone("__No_Phone__").withEmail("__No_Email__")
                .withAddress("__No_Address__")
                .withProjectStatus("__No_Project_Status__")
                .withPaymentStatus("__No_Payment_Status__").withClientStatus("__No_Client_Status__")
                .withDeadline("__No_Deadline__").build();
        FindCommand expectedFindCommand =
                new FindCommand(new ArgumentPredicate(testPerson));
        assertParseSuccess(parser, NAME_DESC_BOB, expectedFindCommand);

        // Person with multiple parameters
        Person testPersonMultiple = new PersonBuilder().withName(VALID_NAME_BOB)
                .withPhone("__No_Phone__").withEmail(VALID_EMAIL_BOB)
                .withAddress("__No_Address__")
                .withProjectStatus("__No_Project_Status__")
                .withPaymentStatus("__No_Payment_Status__").withClientStatus("__No_Client_Status__")
                .withDeadline("__No_Deadline__").build();
        FindCommand expectedSecondFindCommand =
                new FindCommand(new ArgumentPredicate(testPersonMultiple));
        assertParseSuccess(parser, NAME_DESC_BOB + EMAIL_DESC_BOB, expectedSecondFindCommand);
    }

}
