package seedu.address.testutil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import seedu.address.model.goods.Date;
import seedu.address.model.goods.Goods;
import seedu.address.model.goods.GoodsCategories;
import seedu.address.model.goods.GoodsName;
import seedu.address.model.person.Name;

/**
 * Utility class for the test case involving GoodsUtils
 */
public class GoodsTestUtil {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    private static final DateTimeFormatter PATTERN_READ = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private static final String DATETIME_VALID_PROCUREMENT = LocalDateTime.now().format(PATTERN_READ);
    private static final String DATETIME_VALID_ARRIVAL = LocalDateTime.now().plusDays(1).format(PATTERN_READ);

    /**
     * Returns a dummy list of goods for test cases.
     */
    public static ArrayList<Goods> getDummyGoodsList() {
        Goods goods1 = new Goods(new GoodsName("Milk Bread"), 1, 5.22, GoodsCategories.CONSUMABLES,
                new Date(DATETIME_VALID_PROCUREMENT), new Date(DATETIME_VALID_ARRIVAL), false, new Name("Alex Yeoh"));
        Goods goods2 = new Goods(new GoodsName("Potato Chips"), 5, 3.5, GoodsCategories.CONSUMABLES,
                new Date(DATETIME_VALID_PROCUREMENT), new Date(DATETIME_VALID_ARRIVAL), false, new Name("Bernice Yu"));
        Goods goods3 = new Goods(new GoodsName("Chocolate Bars"), 10, 2.0, GoodsCategories.CONSUMABLES,
                new Date(DATETIME_VALID_PROCUREMENT), new Date(DATETIME_VALID_ARRIVAL), false, new Name("Alex Yeoh"));
        Goods goods4 = new Goods(new GoodsName("LEGO Toy"), 1, 100.0, GoodsCategories.SPECIALTY,
                new Date(DATETIME_VALID_PROCUREMENT), new Date(DATETIME_VALID_ARRIVAL), false, new Name("Bernice Yu"));

        ArrayList<Goods> goodsList = new ArrayList<>();
        goodsList.add(goods1);
        goodsList.add(goods2);
        goodsList.add(goods3);
        goodsList.add(goods4);

        return goodsList;
    }
}
