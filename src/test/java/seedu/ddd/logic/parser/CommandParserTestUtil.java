package seedu.ddd.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.ddd.testutil.contact.TypicalContactFields.INVALID_CLIENT_ADDRESS;
import static seedu.ddd.testutil.contact.TypicalContactFields.INVALID_CLIENT_EMAIL;
import static seedu.ddd.testutil.contact.TypicalContactFields.INVALID_CLIENT_NAME;
import static seedu.ddd.testutil.contact.TypicalContactFields.INVALID_CLIENT_PHONE;
import static seedu.ddd.testutil.contact.TypicalContactFields.INVALID_TAG;
import static seedu.ddd.testutil.contact.TypicalContactFields.INVALID_VENDOR_ADDRESS;
import static seedu.ddd.testutil.contact.TypicalContactFields.INVALID_VENDOR_EMAIL;
import static seedu.ddd.testutil.contact.TypicalContactFields.INVALID_VENDOR_NAME;
import static seedu.ddd.testutil.contact.TypicalContactFields.INVALID_VENDOR_PHONE;
import static seedu.ddd.testutil.contact.TypicalContactFields.INVALID_VENDOR_SERVICE;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_ADDRESS;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_EMAIL;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_NAME;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_PHONE;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_EDITED_CONTACT_ID;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_EDITED_CONTACT_NAME;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_EDITED_CONTACT_PHONE;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_TAG_1;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_TAG_2;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_ADDRESS;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_EMAIL;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_NAME;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_PHONE;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_SERVICE_1;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.ddd.logic.commands.Command;
import seedu.ddd.logic.parser.exceptions.ParseException;

/**
 * Contains helper methods for testing command parsers.
 */
public class CommandParserTestUtil {

    // Client arguments
    public static final String VALID_CLIENT_NAME_ARGUMENT = PREFIX_NAME + VALID_CLIENT_NAME;
    public static final String VALID_CLIENT_PHONE_ARGUMENT = PREFIX_PHONE + VALID_CLIENT_PHONE;
    public static final String VALID_CLIENT_EMAIL_ARGUMENT = PREFIX_EMAIL + VALID_CLIENT_EMAIL;
    public static final String VALID_CLIENT_ADDRESS_ARGUMENT = PREFIX_ADDRESS + VALID_CLIENT_ADDRESS;
    public static final String INVALID_CLIENT_NAME_ARGUMENT = PREFIX_NAME + INVALID_CLIENT_NAME;
    public static final String INVALID_CLIENT_PHONE_ARGUMENT = PREFIX_PHONE + INVALID_CLIENT_PHONE;
    public static final String INVALID_CLIENT_EMAIL_ARGUMENT = PREFIX_EMAIL + INVALID_CLIENT_EMAIL;
    public static final String INVALID_CLIENT_ADDRESS_ARGUMENT = PREFIX_ADDRESS + INVALID_CLIENT_ADDRESS;

    // Vendor arguments
    public static final String VALID_VENDOR_NAME_ARGUMENT = PREFIX_NAME + VALID_VENDOR_NAME;
    public static final String VALID_VENDOR_PHONE_ARGUMENT = PREFIX_PHONE + VALID_VENDOR_PHONE;
    public static final String VALID_VENDOR_EMAIL_ARGUMENT = PREFIX_EMAIL + VALID_VENDOR_EMAIL;
    public static final String VALID_VENDOR_SERVICE_ARGUMENT = PREFIX_SERVICE + VALID_VENDOR_SERVICE_1;
    public static final String VALID_VENDOR_ADDRESS_ARGUMENT = PREFIX_ADDRESS + VALID_VENDOR_ADDRESS;
    public static final String INVALID_VENDOR_NAME_ARGUMENT = PREFIX_NAME + INVALID_VENDOR_NAME;
    public static final String INVALID_VENDOR_PHONE_ARGUMENT = PREFIX_PHONE + INVALID_VENDOR_PHONE;
    public static final String INVALID_VENDOR_EMAIL_ARGUMENT = PREFIX_EMAIL + INVALID_VENDOR_EMAIL;
    public static final String INVALID_VENDOR_SERVICE_ARGUMENT = PREFIX_SERVICE + INVALID_VENDOR_SERVICE;
    public static final String INVALID_VENDOR_ADDRESS_ARGUMENT = PREFIX_ADDRESS + INVALID_VENDOR_ADDRESS;

    // Common arguments
    public static final String VALID_TAG_ARGUMENT_1 = PREFIX_TAG + VALID_TAG_1;
    public static final String VALID_TAG_ARGUMENT_2 = PREFIX_TAG + VALID_TAG_2;
    public static final String VALID_EMPTY_TAG_ARGUMENT = PREFIX_TAG.toString();
    public static final String INVALID_TAG_ARGUMENT = PREFIX_TAG + INVALID_TAG;
    public static final String INVALID_NON_EMPTY_PREAMBLE = "NonEmptyPreamble";

    // Edited contact arguments
    public static final String VALID_EDITED_CONTACT_NAME_ARGUMENT = PREFIX_NAME + VALID_EDITED_CONTACT_NAME;
    public static final String VALID_EDITED_CONTACT_PHONE_ARGUMENT = PREFIX_PHONE + VALID_EDITED_CONTACT_PHONE;
    public static final String VALID_EDITED_CONTACT_ID_ARGUMENT = PREFIX_ID + VALID_EDITED_CONTACT_ID;

    /**
     * Join arguments into a single space-delimited input string.
     * This is meant to increase readability and to reduce concatenation with +.
     */
    public static String joinArguments(String ... arguments) {
        assert arguments.length > 0;
        return " " + Stream.of(arguments).collect(Collectors.joining(" "));
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertParseSuccess(Parser<? extends Command> parser, String userInput,
                                          Command expectedCommand) {
        try {
            Command command = parser.parse(userInput);
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertParseFailure(Parser<? extends Command> parser, String userInput, String expectedMessage) {
        try {
            parser.parse(userInput);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }
}
