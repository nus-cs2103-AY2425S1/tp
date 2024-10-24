package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindSupplierCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.CompanyContainsKeywordPredicate;
import seedu.address.model.person.predicates.NameContainsPredicate;
import seedu.address.model.person.predicates.ProductContainsKeywordPredicate;

public class FindSupplierCommandParserTest {
    private final FindSupplierCommandParser parser = new FindSupplierCommandParser();

    @Test
    public void parse_validArgs_returnsFindSupplierCommand1() throws ParseException {

        String input = " " + PREFIX_NAME + "Linkes " + PREFIX_COMPANY + "NUS " + PREFIX_PRODUCT + "Iphone";

        List<Predicate<Person>> expectedPredicate = new ArrayList<>();

        expectedPredicate.add(new NameContainsPredicate("Linkes"));
        expectedPredicate.add(new CompanyContainsKeywordPredicate("NUS"));
        expectedPredicate.add(new ProductContainsKeywordPredicate("Iphone"));

        FindSupplierCommand expectedCommand = new FindSupplierCommand(expectedPredicate);
        FindSupplierCommand actualCommand = parser.parse(input);
        assertTrue(expectedCommand.equals(actualCommand));
    }

    @Test
    public void parse_validArgs_returnsFindSupplierCommand2() throws ParseException {

        String input = " " + PREFIX_NAME + "Linkes ";

        List<Predicate<Person>> expectedPredicate = new ArrayList<>();

        expectedPredicate.add(new NameContainsPredicate("Linkes"));

        FindSupplierCommand expectedCommand = new FindSupplierCommand(expectedPredicate);
        FindSupplierCommand actualCommand = parser.parse(input);
        assertTrue(expectedCommand.equals(actualCommand));
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        String input = " " + PREFIX_NAME + "Alice " + PREFIX_NAME + "Bob";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_noKeyword_throwsParseException() {
        String input = " " + PREFIX_NAME + " " + PREFIX_COMPANY + "NUS" + PREFIX_PRODUCT + " ";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_noPrefixes_throwsParseException() {
        // No Prefix given
        String input = "test input string";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

}
