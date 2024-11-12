package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditScheduleCommand;
import seedu.address.logic.commands.EditScheduleCommand.EditScheduleDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

public class EditScheduleCommandParserTest {

    private EditScheduleCommandParser parser = new EditScheduleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        // Correctly formatted input string
        String userInput = "1 " + PREFIX_NAME + "Meeting " + PREFIX_DATE + "01-10-2024 " + PREFIX_TIME + "1400 "
                + PREFIX_CONTACT + "1";

        EditScheduleDescriptor descriptor = new EditScheduleDescriptor();
        descriptor.setName(new Name("Meeting"));
        descriptor.setDate(LocalDate.parse("01-10-2024", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        descriptor.setTime(LocalTime.parse("1400", DateTimeFormatter.ofPattern("HHmm")));
        descriptor.setContactIndex(List.of(Index.fromOneBased(1)));

        EditScheduleCommand expectedCommand = new EditScheduleCommand(Index.fromOneBased(1), descriptor);

        EditScheduleCommand actualCommand = parser.parse(userInput);
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_partialFieldsPresent_success() throws Exception {
        // Partial fields provided
        String userInput = "1 " + PREFIX_NAME + "Meeting " + PREFIX_DATE + "01-10-2024";

        EditScheduleDescriptor descriptor = new EditScheduleDescriptor();
        descriptor.setName(new Name("Meeting"));
        descriptor.setDate(LocalDate.parse("01-10-2024", DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        EditScheduleCommand expectedCommand = new EditScheduleCommand(Index.fromOneBased(1), descriptor);

        EditScheduleCommand actualCommand = parser.parse(userInput);
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_missingField_failure() {
        // Missing name
        String userInput = "1 " + PREFIX_NAME + "Lunch" + PREFIX_TIME + "1400 " + PREFIX_CONTACT + "1";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidIndex_failure() {
        // Invalid person index
        String userInput = "a " + PREFIX_NAME + "Meeting " + PREFIX_DATE + "01-10-2024 " + PREFIX_TIME + "1400 "
                + PREFIX_CONTACT + "1";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidDate_failure() {
        // Invalid date format
        String userInput = "1 " + PREFIX_NAME + "Meeting " + PREFIX_DATE + "2024-10-01 " + PREFIX_TIME + "1400 "
                + PREFIX_CONTACT + "1";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidTime_failure() {
        // Invalid time format
        String userInput = "1 " + PREFIX_NAME + "Meeting " + PREFIX_DATE + "01-10-2024 " + PREFIX_TIME + "2 PM "
                + PREFIX_CONTACT + "1";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_noFieldsEdited_failure() {
        // No fields are provided to edit
        String userInput = "1";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}
