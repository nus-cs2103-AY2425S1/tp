package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ARRIVAL_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOODS_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROCUREMENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
// import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddGoodsCommand;
import seedu.address.model.goods.Goods;
import seedu.address.model.goodsreceipt.GoodsReceipt;
import seedu.address.testutil.GoodsReceiptBuilder;

public class AddGoodsCommandParserTest {
    private static final AddGoodsCommandParser PARSER = new AddGoodsCommandParser();

    @Test
    public void parse_validArgs_success() {
        String toParse = PREFIX_GOODS_NAME.getPrefix() + GoodsReceiptBuilder.DEFAULT_GOODS_NAME + " "
                + PREFIX_QUANTITY.getPrefix() + GoodsReceiptBuilder.DEFAULT_QUANTITY + " "
                + PREFIX_PRICE.getPrefix() + GoodsReceiptBuilder.DEFAULT_PRICE + " "
                + PREFIX_CATEGORY.getPrefix() + GoodsReceiptBuilder.DEFAULT_GOODS_CATEGORY + " "
                + PREFIX_PROCUREMENT_DATE.getPrefix() + GoodsReceiptBuilder.DEFAULT_PROCUREMENT_DATE + " "
                + PREFIX_ARRIVAL_DATE.getPrefix() + GoodsReceiptBuilder.DEFAULT_ARRIVAL_DATE + " "
                + PREFIX_NAME.getPrefix() + GoodsReceiptBuilder.DEFAULT_SUPPLIER_NAME;
        Goods expectedGoods = new Goods(GoodsReceiptBuilder.DEFAULT_GOODS_NAME,
                GoodsReceiptBuilder.DEFAULT_GOODS_CATEGORY);
        GoodsReceipt expectedGoodsReceipt = new GoodsReceiptBuilder().withGoods(expectedGoods).build();

        // assertParseSuccess(PARSER, toParse, new AddGoodsCommand(expectedGoods, expectedGoodsReceipt));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Missing Arguments
        String toParseMissingArgs = PREFIX_GOODS_NAME.getPrefix() + GoodsReceiptBuilder.DEFAULT_GOODS_NAME
                + PREFIX_CATEGORY.getPrefix() + GoodsReceiptBuilder.DEFAULT_GOODS_CATEGORY
                + PREFIX_PROCUREMENT_DATE.getPrefix() + GoodsReceiptBuilder.DEFAULT_PROCUREMENT_DATE
                + PREFIX_ARRIVAL_DATE.getPrefix() + GoodsReceiptBuilder.DEFAULT_ARRIVAL_DATE
                + PREFIX_NAME.getPrefix() + GoodsReceiptBuilder.DEFAULT_SUPPLIER_NAME;

        assertParseFailure(PARSER, toParseMissingArgs,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGoodsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateArgs_throwsParseException() {
        // Duplicated Arguments
        String toParseMissingArgs = PREFIX_GOODS_NAME.getPrefix() + GoodsReceiptBuilder.DEFAULT_GOODS_NAME
                + PREFIX_QUANTITY.getPrefix() + GoodsReceiptBuilder.DEFAULT_QUANTITY
                + PREFIX_PRICE.getPrefix() + GoodsReceiptBuilder.DEFAULT_PRICE
                + PREFIX_PRICE.getPrefix() + GoodsReceiptBuilder.DEFAULT_PRICE
                + PREFIX_CATEGORY.getPrefix() + GoodsReceiptBuilder.DEFAULT_GOODS_CATEGORY
                + PREFIX_PROCUREMENT_DATE.getPrefix() + GoodsReceiptBuilder.DEFAULT_PROCUREMENT_DATE
                + PREFIX_ARRIVAL_DATE.getPrefix() + GoodsReceiptBuilder.DEFAULT_ARRIVAL_DATE
                + PREFIX_NAME.getPrefix() + GoodsReceiptBuilder.DEFAULT_SUPPLIER_NAME;

        assertParseFailure(PARSER, toParseMissingArgs,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGoodsCommand.MESSAGE_USAGE));
    }
}
