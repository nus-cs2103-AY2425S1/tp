package spleetwaise.transaction.logic.parser;

import static spleetwaise.commons.testutil.Assert.assertThrows;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_DATE;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spleetwaise.address.model.AddressBookModel;
import spleetwaise.address.model.AddressBookModelManager;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.commons.core.index.Index;
import spleetwaise.commons.logic.parser.exceptions.ParseException;
import spleetwaise.commons.model.CommonModelManager;
import spleetwaise.transaction.logic.Messages;
import spleetwaise.transaction.logic.commands.EditCommand;
import spleetwaise.transaction.model.TransactionBookModel;
import spleetwaise.transaction.model.TransactionBookModelManager;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Category;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.testutil.TypicalTransactions;

public class EditCommandParserTest {


    private static final String MESSAGE_INVALID_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);
    private final TransactionBookModel tbModel = new TransactionBookModelManager(
            TypicalTransactions.getTypicalTransactionBook());
    private final AddressBookModel abModel = new AddressBookModelManager(
            TypicalPersons.getTypicalAddressBook());
    private EditCommandParser parser = new EditCommandParser();


    @BeforeEach
    void setUp() {
        CommonModelManager.initialise(abModel, tbModel);
    }

    @Test
    public void parse_missingParts_failure() {
        // blank
        CommandParserTestUtil.assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // random text
        CommandParserTestUtil.assertParseFailure(parser, "jfjf", MESSAGE_INVALID_FORMAT);

        // no fields specified
        CommandParserTestUtil.assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);
        CommandParserTestUtil.assertParseFailure(parser, "1 ", EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_invalidIndex_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "-1 amt/10", MESSAGE_INVALID_FORMAT);
        CommandParserTestUtil.assertParseFailure(parser, "0 amt/10", MESSAGE_INVALID_FORMAT);
        CommandParserTestUtil.assertParseFailure(parser, "1 dfjs", MESSAGE_INVALID_FORMAT);
        CommandParserTestUtil.assertParseFailure(parser, "1 i/ meme", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // single invalid fields
        CommandParserTestUtil.assertParseFailure(
                parser, "1 " + PREFIX_AMOUNT + "notAnAmount", Amount.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(
                parser, "1 " + PREFIX_DESCRIPTION + "", Description.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(
                parser, "1 " + PREFIX_DATE + "notdate", Date.MESSAGE_CONSTRAINTS);
        // some invalid fields
        CommandParserTestUtil.assertParseFailure(
                parser, "1 " + PREFIX_CATEGORY + "test" + " " + PREFIX_AMOUNT + "10.0001",
                Amount.MESSAGE_CONSTRAINTS
        );
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        EditCommand.EditTransactionDescriptor descriptor = new EditCommand.EditTransactionDescriptor();
        descriptor.setAmount(new Amount("100"));
        descriptor.setDescription(new Description("hello"));
        descriptor.setDate(new Date("01012024"));
        descriptor.setCategories(new HashSet<>(Arrays.asList(new Category("TEST"))));
        EditCommand expectedCommand = new EditCommand(Index.fromOneBased(1), descriptor);

        CommandParserTestUtil.assertParseSuccess(
                parser,
                "1 amt/100 desc/hello date/01012024 cat/TEST",
                expectedCommand
        );
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        EditCommand.EditTransactionDescriptor descriptor = new EditCommand.EditTransactionDescriptor();
        descriptor.setDescription(new Description("hello"));
        EditCommand expectedCommand = new EditCommand(Index.fromOneBased(1), descriptor);

        CommandParserTestUtil.assertParseSuccess(
                parser,
                "1 desc/hello",
                expectedCommand
        );
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        assertThrows(ParseException.class, () -> parser.parse("1 amt/10 amt/10"));
    }

    @Test
    public void parse_multipleRepeatedFieldsCat_success() {
        EditCommand.EditTransactionDescriptor descriptor = new EditCommand.EditTransactionDescriptor();
        descriptor.setCategories(new HashSet<>(Arrays.asList(new Category("TEST"), new Category("TEST2"))));
        EditCommand expectedCommand = new EditCommand(Index.fromOneBased(1), descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, "1 cat/TEST cat/TEST2", expectedCommand);
    }

    @Test
    public void parse_emptyFieldCat_clearsCats() {
        EditCommand.EditTransactionDescriptor descriptor = new EditCommand.EditTransactionDescriptor();
        descriptor.setCategories(new HashSet<>());
        EditCommand expectedCommand = new EditCommand(Index.fromOneBased(1), descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, "1 cat/", expectedCommand);
    }


}
