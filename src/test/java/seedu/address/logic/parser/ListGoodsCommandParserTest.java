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
    private final Predicate<GoodsReceipt> goodsNamePredicate = new GoodsNamePredicate("Bread");

    @Test
    public void parse_validArgs_returnsListGoodsCommand() {
        // valid input with no args
        ListGoodsCommand expectedListGoodsCommand = new ListGoodsCommand(dummyPredicate);
        assertParseSuccess(parser, "view", expectedListGoodsCommand);

        // valid input with only category keyword args
        ListGoodsCommand expectedListGoodsCommand2 = new ListGoodsCommand(categoryPredicate);
        assertParseSuccess(parser, "view c/CONSUMABLES", expectedListGoodsCommand2);

        // valid input with multiple keyword args
        // TODO: Expand this test
        assertDoesNotThrow(() -> parser.parse("view gn/Bread c/CONSUMABLES"));
    }
}
