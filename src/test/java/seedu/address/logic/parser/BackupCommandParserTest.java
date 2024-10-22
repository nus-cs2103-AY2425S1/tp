//package seedu.address.logic.parser;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.logic.commands.BackupCommand;
//import seedu.address.logic.parser.exceptions.ParseException;
//
///**
// * Contains unit tests for {@code BackupCommandParser}.
// */
//public class BackupCommandParserTest {
//
//    private final BackupCommandParser parser = new BackupCommandParser();
//
//    @Test
//    public void parse_emptyArgs_returnsBackupCommand() throws Exception {
//        // No arguments provided
//        BackupCommand command = parser.parse("");
//        assertTrue(command instanceof BackupCommand, "Command should be an instance of BackupCommand.");
//    }
//
//    @Test
//    public void parse_whitespaceArgs_returnsBackupCommand() throws Exception {
//        // Only whitespace provided
//        BackupCommand command = parser.parse("   ");
//        assertTrue(command instanceof BackupCommand, "Command should be an instance of BackupCommand.");
//    }
//
//    @Test
//    public void parse_argumentsProvided_throwsParseException() {
//        // Unexpected arguments provided
//        assertThrows(ParseException.class, () -> parser.parse("/some/invalid/path"),
//                "Expected ParseException for providing unexpected arguments.");
//    }
//
//    @Test
//    public void parse_nullArgs_throwsNullPointerException() {
//        // Null argument provided
//        assertThrows(NullPointerException.class, () -> parser.parse(null),
//                "Expected NullPointerException for null input.");
//    }
//}
