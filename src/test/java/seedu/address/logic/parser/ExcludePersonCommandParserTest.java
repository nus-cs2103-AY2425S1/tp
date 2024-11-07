package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.searchmode.ExcludePersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;


public class ExcludePersonCommandParserTest {
    @Test
    public void parse_indexPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).build();
        ExcludePersonCommandParser excludePersonCommandParser = new ExcludePersonCommandParser();
        Set<Index> expectedIndex = new HashSet<>();
        expectedIndex.add(Index.fromOneBased(1));
        assertParseSuccess(excludePersonCommandParser, "exclude ci/1",
                new ExcludePersonCommand(expectedIndex));
    }

    @Test
    public void parse_invalidIndex_failure() {
        ExcludePersonCommandParser excludePersonCommandParser = new ExcludePersonCommandParser();
        assertThrows(ParseException.class, () -> excludePersonCommandParser.parse("exclude ci/0"));
    }

}
