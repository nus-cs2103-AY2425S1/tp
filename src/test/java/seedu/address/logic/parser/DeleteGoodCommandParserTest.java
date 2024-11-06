package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOODS_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteGoodsCommand;
import seedu.address.model.goods.GoodsName;
import seedu.address.model.person.Name;

public class DeleteGoodCommandParserTest {

    private DeleteGoodsCommandParser parser = new DeleteGoodsCommandParser();

    @Test
    public void parse_nonValidSupplierNameAndValidGoodsName_throwsParseException() {
        assertParseFailure(
                parser,
                " n/$$$ gn/abc",
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validSupplierNameAndNonValidGoodsName_throwsParseException() {
        assertParseFailure(
                parser,
                " n/abc gn/$$$",
                GoodsName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_withPreamble_throwsParseException() {
        assertParseFailure(
                parser,
                "abc n/abc gn/abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGoodsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multiplePrefixes_throwsParseException() {
        assertParseFailure(
                parser,
                " n/abc n/abc gn/abc",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
        assertParseFailure(
                parser,
                " n/abc gn/abc gn/abc",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GOODS_NAME));
    }

    @Test
    public void parse_validSupplierNameAndValidGoodsName_returnsDeleteCommand() {
        assertParseSuccess(
                parser,
                " n/abc gn/abc",
                new DeleteGoodsCommand(new Name("abc"), new GoodsName("abc")));
    }
}
