package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListGoodsCommand;
import seedu.address.model.goods.GoodsCategories;
import seedu.address.model.goodsreceipt.CategoryPredicate;
import seedu.address.model.goodsreceipt.GoodsNamePredicate;
import seedu.address.model.goodsreceipt.GoodsReceipt;

public class ListGoodsCommandParserTest {
    private final ListGoodsCommandParser parser = new ListGoodsCommandParser();
    private final Predicate<GoodsReceipt> dummyPredicate = ListGoodsCommandParser.DUMMY_PREDICATE;
    private final Predicate<GoodsReceipt> categoryPredicate = new CategoryPredicate(GoodsCategories.CONSUMABLES);
    private final Predicate<GoodsReceipt> goodsNamePredicate = new GoodsNamePredicate("bread");

    @Test
    public void parse_validArgs_returnsListGoodsCommand() {
        // Valid input with only category keyword args
        ListGoodsCommand expectedListGoodsCommand = new ListGoodsCommand(categoryPredicate);
        assertParseSuccess(parser, "viewgoods c/CONSUMABLES", expectedListGoodsCommand);

        // Valid input with only goodsName keyword args
        ListGoodsCommand expectedListGoodsCommand2 = new ListGoodsCommand(goodsNamePredicate);
        // assertParseSuccess(parser, "viewgoods gn/bread", expectedListGoodsCommand2);
    }

    @Test
    public void parse_noArgs_returnListGoodsCommand() {
        // Valid input with no args
        ListGoodsCommand expectedListGoodsCommand = new ListGoodsCommand(dummyPredicate);
        assertParseSuccess(parser, "viewgoods", expectedListGoodsCommand);
    }
}
