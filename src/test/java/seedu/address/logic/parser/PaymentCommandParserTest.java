package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.PaymentCommand;
import seedu.address.model.person.Name;

public class PaymentCommandParserTest {

    private PaymentCommandParser parser = new PaymentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // Test with "paid" status
        assertParseSuccess(parser, "John Doe "
                        + PREFIX_DATE + "2024-10-24 1000 "
                        + PREFIX_PAID + "paid",
                new PaymentCommand(new Name("John Doe"), "2024-10-24 1000", true));

        // Test with "unpaid" status
        assertParseSuccess(parser, "John Doe "
                        + PREFIX_DATE + "2024-10-24 1000 "
                        + PREFIX_PAID + "unpaid",
                new PaymentCommand(new Name("John Doe"), "2024-10-24 1000", false));

        // Test with boolean "true"
        assertParseSuccess(parser, "John Doe "
                        + PREFIX_DATE + "2024-10-24 1000 "
                        + PREFIX_PAID + "true",
                new PaymentCommand(new Name("John Doe"), "2024-10-24 1000", true));

        // Test with boolean "false"
        assertParseSuccess(parser, "John Doe "
                        + PREFIX_DATE + "2024-10-24 1000 "
                        + PREFIX_PAID + "false",
                new PaymentCommand(new Name("John Doe"), "2024-10-24 1000", false));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaymentCommand.MESSAGE_USAGE);

        // missing name
        assertParseFailure(parser,
                PREFIX_DATE + "2024-10-24 1000 "
                        + PREFIX_PAID + "paid",
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, "John Doe "
                        + PREFIX_PAID + "paid",
                expectedMessage);

        // missing paid status prefix
        assertParseFailure(parser, "John Doe "
                        + PREFIX_DATE + "2024-10-24 1000",
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid date format
        assertParseFailure(parser, "John Doe "
                        + PREFIX_DATE + "24-10-2024 1000 "
                        + PREFIX_PAID + "paid",
                MESSAGE_INVALID_DATE_FORMAT);

        // invalid time format
        assertParseFailure(parser, "John Doe "
                        + PREFIX_DATE + "2024-10-24 25:00 "
                        + PREFIX_PAID + "paid",
                MESSAGE_INVALID_DATE_FORMAT);

        // invalid paid status
        assertParseFailure(parser, "John Doe "
                        + PREFIX_DATE + "2024-10-24 1000 "
                        + PREFIX_PAID + "maybe",
                PaymentCommand.MESSAGE_PAYMENT_STATUS_INVALID);
    }
}
