package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.goods.Goods;
import seedu.address.model.goods.GoodsCategories;
import seedu.address.model.goodsreceipt.GoodsReceipt;
import seedu.address.testutil.GoodsBuilder;
import seedu.address.testutil.GoodsReceiptBuilder;

public class HasGoodsCategoryPredicateTest {

    private Model getDefaultModel() {
        Model model = new ModelManager();
        model.addPerson(ALICE);
        model.addPerson(BOB);
        Goods apple = new GoodsBuilder()
                .withName("APPLE")
                .withGoodsCategory(GoodsCategories.LIFESTYLE)
                .build();
        Goods banana = new GoodsBuilder()
                .withName("Banana")
                .withGoodsCategory(GoodsCategories.CONSUMABLES)
                .build();
        GoodsReceipt appleReceipt = new GoodsReceiptBuilder()
                .withSupplierName(ALICE.getName())
                .withGoods(apple)
                .build();
        GoodsReceipt bananaReceipt = new GoodsReceiptBuilder()
                .withSupplierName(BOB.getName())
                .withGoods(banana)
                .build();
        model.addGoods(appleReceipt);
        model.addGoods(bananaReceipt);
        return model;
    }

    @Test
    public void equals() {
        Model firstModel = getDefaultModel();
        Model secondModel = new ModelManager();
        Set<GoodsCategories> firstCategorySet = Set.of(GoodsCategories.CONSUMABLES);
        Set<GoodsCategories> secondCategorySet = Set.of(GoodsCategories.LIFESTYLE, GoodsCategories.SPECIALTY);

        PersonHasGoodsWithCategoriesPredicate firstPredicate =
                new PersonHasGoodsWithCategoriesPredicate(firstModel, firstCategorySet);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        assertEquals(firstPredicate, new PersonHasGoodsWithCategoriesPredicate(firstModel, firstCategorySet));

        // different types -> returns false
        assertNotEquals(1, firstPredicate);

        // null -> returns false
        assertNotEquals(null, firstPredicate);

        // different person -> returns false
        assertNotEquals(firstPredicate, new PersonHasGoodsWithCategoriesPredicate(secondModel, firstCategorySet));
        assertNotEquals(firstPredicate, new PersonHasGoodsWithCategoriesPredicate(firstModel, secondCategorySet));
    }

    @Test
    public void test_hasGoodsWithCategories_returnsTrue() {

        Model model = getDefaultModel();

        // One category
        PersonHasGoodsWithCategoriesPredicate firstPredicate = new PersonHasGoodsWithCategoriesPredicate(
                model, Set.of(GoodsCategories.LIFESTYLE));
        assertTrue(firstPredicate.test(ALICE));

        // Multiple category
        PersonHasGoodsWithCategoriesPredicate secondPredicate = new PersonHasGoodsWithCategoriesPredicate(
                model, Set.of(GoodsCategories.LIFESTYLE, GoodsCategories.CONSUMABLES));
        assertTrue(secondPredicate.test(ALICE));
        assertTrue(secondPredicate.test(BOB));
    }


    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {

        Model model = getDefaultModel();

        // One category
        PersonHasGoodsWithCategoriesPredicate firstPredicate = new PersonHasGoodsWithCategoriesPredicate(
                model, Set.of(GoodsCategories.LIFESTYLE));
        assertFalse(firstPredicate.test(BOB));
        assertFalse(firstPredicate.test(CARL));

        // Multiple category
        PersonHasGoodsWithCategoriesPredicate secondPredicate = new PersonHasGoodsWithCategoriesPredicate(
                model, Set.of(GoodsCategories.LIFESTYLE, GoodsCategories.CONSUMABLES));
        assertFalse(secondPredicate.test(CARL));

        // No categories
        PersonHasGoodsWithCategoriesPredicate thirdPredicate =
                new PersonHasGoodsWithCategoriesPredicate(model, Set.of());
        assertFalse(thirdPredicate.test(ALICE));
        assertFalse(thirdPredicate.test(BOB));
        assertFalse(thirdPredicate.test(CARL));

        // Non-matching category
        PersonHasGoodsWithCategoriesPredicate fourthPredicate =
                new PersonHasGoodsWithCategoriesPredicate(model, Set.of(GoodsCategories.SPECIALTY));
        assertFalse(fourthPredicate.test(ALICE));
        assertFalse(thirdPredicate.test(BOB));
        assertFalse(thirdPredicate.test(CARL));
    }

    @Test
    public void toStringMethod() {
        Model model = getDefaultModel();
        Set<GoodsCategories> categoriesSet = Set.of(
                GoodsCategories.LIFESTYLE, GoodsCategories.CONSUMABLES, GoodsCategories.SPECIALTY);
        PersonHasGoodsWithCategoriesPredicate predicate =
                new PersonHasGoodsWithCategoriesPredicate(model, categoriesSet);

        String expected = PersonHasGoodsWithCategoriesPredicate.class.getCanonicalName()
                + "{model=" + model + ", categoriesSet=" + categoriesSet + "}";
        assertEquals(expected, predicate.toString());
    }
}
