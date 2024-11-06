package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventDuration;
import seedu.address.model.event.EventName;

public class EditEventCommandParserTest {

    private final EditEventCommandParser parser = new EditEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        String userInput = "1 "
                + PREFIX_EVENT_NAME + "Updated Meeting "
                + PREFIX_EVENT_DESCRIPTION + "Updated description "
                + PREFIX_EVENT_START_DATE + "2024-10-02 "
                + PREFIX_EVENT_END_DATE + "2024-10-11";

        EditEventDescriptor descriptor = new EditEventDescriptor();
        descriptor.setName(new EventName("Updated Meeting"));
        descriptor.setDescription(new EventDescription("Updated description"));
        descriptor.setDuration(new EventDuration(
                LocalDate.of(2024, 10, 2), LocalDate.of(2024, 10, 11)));

        EditEventCommand expectedCommand = new EditEventCommand(1, descriptor);

        EditEventCommand resultCommand = parser.parse(userInput);

        assertEquals(expectedCommand, resultCommand);
    }

    @Test
    public void parse_optionalFieldsMissing_success() throws Exception {
        String userInput = "1 " + PREFIX_EVENT_NAME + "Updated Meeting";

        EditEventDescriptor descriptor = new EditEventDescriptor();
        descriptor.setName(new EventName("Updated Meeting"));

        EditEventCommand expectedCommand = new EditEventCommand(1, descriptor);

        EditEventCommand resultCommand = parser.parse(userInput);

        assertEquals(expectedCommand, resultCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() {
        String userInput = "1";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidEventId_failure() {
        String userInput = "a " + PREFIX_EVENT_NAME + "Updated Meeting";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid date format
        String userInput = "1 "
                + PREFIX_EVENT_NAME + "Updated Meeting "
                + PREFIX_EVENT_START_DATE + "invalid-date "
                + PREFIX_EVENT_END_DATE + "2024-10-11";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}
