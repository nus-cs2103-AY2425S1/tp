package seedu.address.logic.parser.event.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.commands.AddPersonToEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AddPersonToEventParserTest {
    @Test
    public void parse_compulsoryPrefixAbsent_throwsParseException() {
        assertThrows(ParseException.class, () -> new AddPersonToEventParser().parse(" a/1,2"));
        assertThrows(ParseException.class, () -> new AddPersonToEventParser().parse(""));
    }

    @Test
    public void parse_allRolePrefixesAbsent_throwsParseException() {
        assertThrows(ParseException.class, () -> new AddPersonToEventParser().parse(" ei/1"));
    }

    @Test
    public void parse_nonEmptyPreamble_throwsParseException() {
        assertThrows(ParseException.class, () -> new AddPersonToEventParser().parse(" hello ei/1"));
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        assertThrows(ParseException.class, () -> new AddPersonToEventParser().parse(" ei/1 a/1 a/2"));
    }

    @Test
    public void parse_invalidIndices_throwsParseException() {
        assertThrows(ParseException.class, () -> new AddPersonToEventParser().parse(" ei/0 a/1"));
        assertThrows(ParseException.class, () -> new AddPersonToEventParser().parse(" ei/0 ve/2"));
        assertThrows(ParseException.class, () -> new AddPersonToEventParser().parse(" ei/1 s/apple"));
        assertThrows(ParseException.class, () -> new AddPersonToEventParser().parse(" ei/100 ve/1 vo/-3"));
    }

    @Test
    public void parse_correctInputs_success() throws ParseException {
        HashSet<Index> attendees = new HashSet<>();
        HashSet<Index> volunteers = new HashSet<>();
        HashSet<Index> vendors = new HashSet<>();
        HashSet<Index> sponsors = new HashSet<>();
        attendees.add(Index.fromZeroBased(0));
        vendors.add(Index.fromZeroBased(1));
        AddPersonToEventCommand expectedAddPersonToEventCommand = new AddPersonToEventCommand(Index.fromZeroBased(0),
                attendees, volunteers, vendors, sponsors);

        AddPersonToEventCommand actualAddPersonToEventCommand = new AddPersonToEventParser()
                .parse(" ei/1 ve/2 a/1");

        assertEquals(expectedAddPersonToEventCommand, actualAddPersonToEventCommand);
    }
}
