package spleetwaise.transaction.logic.parser;

import static spleetwaise.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_DATE;
import static spleetwaise.transaction.logic.parser.CommandParserTestUtil.assertParseFailure;
import static spleetwaise.transaction.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static spleetwaise.transaction.model.transaction.Date.getNowDate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import spleetwaise.address.commons.core.index.Index;
import spleetwaise.transaction.logic.commands.AddCommand;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Category;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;

public class AddCommandParserTest {

    private static final Index testIndex = Index.fromOneBased(1);
    private static final Amount testAmount = new Amount("1.23");
    private static final Description testDescription = new Description("description");
    private static final Date testDate = new Date(getNowDate());
    private static final Set<Category> testCategories = new HashSet<>(List.of(new Category("FOOD")));

    private final AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput =
                " per/1 amt/1.23 desc/description " + PREFIX_CATEGORY + "FOOD";
        assertParseSuccess(parser, userInput, new AddCommand(testIndex, testAmount, testDescription,
                testDate, testCategories
        ));
    }

    @Test
    public void parse_withOptionalDateField_success() {
        String userInput =
                " per/1 amt/1.23 desc/description " + PREFIX_DATE + getNowDate() + " " + PREFIX_CATEGORY + "FOOD";
        assertParseSuccess(parser, userInput, new AddCommand(testIndex, testAmount, testDescription,
                testDate, testCategories
        ));
    }

    @Test
    public void parse_invalidIndex_exceptionThrown() {
        String userInput1 = " per/0 amt/1.23 desc/description date/01012024";
        assertParseFailure(parser, userInput1, ParserUtil.MESSAGE_INVALID_INDEX);

        String userInput2 = " per/-1 amt/1.23 desc/description date/01012024";
        assertParseFailure(parser, userInput2, ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidAmount_exceptionThrown() {
        String userInput = " per/1 amt/1.234 desc/description date/01012024";
        assertParseFailure(parser, userInput, Amount.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidDescription_exceptionThrown() {
        String userInput = " per/1 amt/1.23 desc/ date/01012024";
        assertParseFailure(parser, userInput, Description.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidDate_exceptionThrown() {
        String userInput = " per/1 amt/1.23 desc/test date/2024";
        assertParseFailure(parser, userInput, Date.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_missingParam_exceptionThrown() {
        String userInput = " per/1 desc/test";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}

