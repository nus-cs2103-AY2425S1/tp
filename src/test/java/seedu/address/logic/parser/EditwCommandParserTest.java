package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditwCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Venue;

class EditwCommandParserTest {

    private final EditwCommandParser parser = new EditwCommandParser();

    @Test
    void parse_allFieldsPresent_success() throws ParseException {
        String userInput = " w/1 n/UpdatedWedding d/2024-11-01 v/UpdatedVenue";
        EditwCommand command = parser.parse(userInput);

        EditwCommand.EditWeddingDescriptor descriptor = new EditwCommand.EditWeddingDescriptor();
        descriptor.setName(new Name("UpdatedWedding"));
        descriptor.setDate(new Date("2024-11-01"));
        descriptor.setVenue(new Venue("UpdatedVenue"));

        assertEquals(new EditwCommand(Index.fromOneBased(1), descriptor), command);
    }

    @Test
    void parse_optionalFieldsMissing_success() throws ParseException {
        String userInput = " w/1 d/2024-11-01";
        EditwCommand command = parser.parse(userInput);

        EditwCommand.EditWeddingDescriptor descriptor = new EditwCommand.EditWeddingDescriptor();
        descriptor.setDate(new Date("2024-11-01"));

        assertEquals(new EditwCommand(Index.fromOneBased(1), descriptor), command);
    }

    @Test
    void parse_invalidIndex_throwsParseException() {
        String userInput = "w/x n/UpdatedWedding";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    void parse_noFieldsSpecified_throwsParseException() {
        String userInput = "w/1";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    void parse_invalidName_failure() {
        String userInput = "w/1 n/!nvalidName d/2024-11-01 v/UpdatedVenue";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    void parse_invalidDate_failure() {
        String userInput = "w/1 n/UpdatedWedding d/invalid-date v/UpdatedVenue";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    void parse_invalidVenue_failure() {
        String userInput = "w/1 n/UpdatedWedding d/2024-11-01 v/!nvalidVenue";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    void parse_repeatedFields_failure() {
        // Repeated names
        final String repeatedNameInput = "w/1 n/UpdatedWedding n/DuplicateName d/2024-11-01 v/UpdatedVenue";
        assertThrows(ParseException.class, () -> parser.parse(repeatedNameInput));

        // Repeated dates
        final String repeatedDateInput = "w/1 n/UpdatedWedding d/2024-10-31 d/2024-11-01 v/UpdatedVenue";
        assertThrows(ParseException.class, () -> parser.parse(repeatedDateInput));

        // Repeated venues
        final String repeatedVenueInput = "w/1 n/UpdatedWedding d/2024-11-01 v/InitialVenue v/UpdatedVenue";
        assertThrows(ParseException.class, () -> parser.parse(repeatedVenueInput));
    }

    @Test
    void parse_missingIndex_failure() {
        String userInput = "n/UpdatedWedding d/2024-11-01 v/UpdatedVenue";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    void parse_nonEmptyPreamble_failure() {
        String userInput = "bruh w/1 n/UpdatedWedding d/2024-11-01 v/UpdatedVenue";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    void parse_whitespaceOnlyPreamble_success() throws ParseException {
        String userInput = "   w/1 n/UpdatedWedding d/2024-11-01 v/UpdatedVenue";
        EditwCommand command = parser.parse(userInput);

        EditwCommand.EditWeddingDescriptor descriptor = new EditwCommand.EditWeddingDescriptor();
        descriptor.setName(new Name("UpdatedWedding"));
        descriptor.setDate(new Date("2024-11-01"));
        descriptor.setVenue(new Venue("UpdatedVenue"));

        assertEquals(new EditwCommand(Index.fromOneBased(1), descriptor), command);
    }

    @Test
    void parse_emptyInput_failure() {
        String userInput = "";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    void parse_requiredFieldsMissing_failure() {
        String userInput = " n/UpdatedWedding d/2024-11-01";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    void parse_unrecognizedPrefixes_failure() {
        // Input includes an unrecognized prefix 'x/' which should result in a ParseException.
        String userInput = "w/1 n/UpdatedWedding d/2024-11-01 x/unknown v/UpdatedVenue";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}
