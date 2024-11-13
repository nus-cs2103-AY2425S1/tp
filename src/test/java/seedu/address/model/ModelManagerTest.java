package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGoods.getTypicalGoodsReceipts;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.goods.Goods;
import seedu.address.model.goods.GoodsCategories;
import seedu.address.model.goodsreceipt.GoodsReceipt;
import seedu.address.model.goodsreceipt.exceptions.IllegalSupplierNameException;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.GoodsBuilder;
import seedu.address.testutil.GoodsReceiptBuilder;
import seedu.address.testutil.PersonBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void deletePerson_supplierHasGoods_removedGoods() {
        GoodsReceipt goodsReceipt = new GoodsReceiptBuilder()
                .withSupplierName(ALICE.getName())
                .build();
        modelManager.addPerson(ALICE);
        modelManager.addGoods(goodsReceipt);
        modelManager.deletePerson(ALICE);
        List<GoodsReceipt> goodsList = modelManager
                .getFilteredGoods(r -> r.isFromSupplier(ALICE.getName()));
        assertEquals(goodsList.size(), 0);
    }

    @Test
    public void deleteGoods_existingGoods_removedGoods() {
        modelManager.addPerson(ALICE);
        Goods apple = new GoodsBuilder()
                .withName("Apple")
                .withGoodsCategory(GoodsCategories.CONSUMABLES)
                .build();
        GoodsReceipt appleReceipt = new GoodsReceiptBuilder()
                .withSupplierName(ALICE.getName())
                .withGoods(apple)
                .build();
        modelManager.addGoods(appleReceipt);
        modelManager.deleteGoods(appleReceipt);
        List<GoodsReceipt> goodsList = modelManager.getGoods().getReceiptList();
        assertEquals(goodsList.size(), 0);
    }

    @Test
    public void findGoodsReceipt_existingGoods_returnGoodsReceipt() {
        modelManager.addPerson(ALICE);
        Goods apple = new GoodsBuilder()
                .withName("Apple")
                .withGoodsCategory(GoodsCategories.CONSUMABLES)
                .build();
        GoodsReceipt appleReceipt = new GoodsReceiptBuilder()
                .withSupplierName(ALICE.getName())
                .withGoods(apple)
                .build();
        modelManager.addGoods(appleReceipt);
        Optional<GoodsReceipt> goodsReceipt = modelManager
                .findGoodsReceipt(appleReceipt::equals);
        assertEquals(goodsReceipt, Optional.of(appleReceipt));
    }

    @Test
    public void findGoodsReceipt_nonExistingGoods_returnNoGoodsReceipt() {
        modelManager.addPerson(ALICE);
        modelManager.addPerson(BOB);
        Goods apple = new GoodsBuilder()
                .withName("Apple")
                .withGoodsCategory(GoodsCategories.CONSUMABLES)
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
        modelManager.addGoods(appleReceipt);
        Optional<GoodsReceipt> goodsReceipt = modelManager
                .findGoodsReceipt(bananaReceipt::equals);
        assertEquals(goodsReceipt, Optional.empty());
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs, getTypicalGoodsReceipts());
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs, getTypicalGoodsReceipts());
        assertEquals(modelManager, modelManagerCopy);

        // same object -> returns true
        assertEquals(modelManager, modelManager);

        // null -> returns false
        assertNotEquals(null, modelManager);

        // different types -> returns false
        assertNotEquals(5, modelManager);

        // different addressBook -> returns false
        assertNotEquals(modelManager, new ModelManager(differentAddressBook, userPrefs, getTypicalGoodsReceipts()));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertNotEquals(modelManager, new ModelManager(addressBook, userPrefs, getTypicalGoodsReceipts()));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertNotEquals(modelManager, new ModelManager(addressBook, differentUserPrefs, getTypicalGoodsReceipts()));
    }

    @Test
    public void setGoods_nullGoods_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGoods(null));
    }

    @Test
    public void setGoods_validReceiptLog_success() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();
        GoodsReceipt goodsReceipt = new GoodsReceiptBuilder()
                .withSupplierName(ALICE.getName())
                .build();
        ReceiptLog receiptLog = new ReceiptLog();
        receiptLog.addReceipt(goodsReceipt);
        modelManager = new ModelManager(addressBook, userPrefs, getTypicalGoodsReceipts());
        List<GoodsReceipt> beforeSetGoodsList = modelManager.getGoods().getReceiptList();
        assertTrue(beforeSetGoodsList.size() > 1);
        modelManager.setGoods(receiptLog);
        List<GoodsReceipt> goodsList = modelManager.getGoods().getReceiptList();
        assertEquals(goodsList.size(), 1);
        assertEquals(goodsList.get(0), goodsReceipt);
    }

    @Test
    public void getGoods_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getGoods().getReceiptList().remove(0));
    }

    @Test
    public void addGoods_nullGoods_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addGoods(null));
    }

    @Test
    public void addGoods_illegalSupplierName_throwsIllegalSupplierName() {
        Person person = new PersonBuilder().build();
        modelManager.addPerson(person);
        GoodsReceipt goodsReceipt = new GoodsReceiptBuilder()
                .withSupplierName(new Name("Invalid Name"))
                .build();

        assertNotEquals(person.getName(), goodsReceipt.getSupplierName());
        assertThrows(IllegalSupplierNameException.class, () -> modelManager.addGoods(goodsReceipt));
    }


    @Test
    public void addGoods_validGoods_addsGoods() {
        Person person = new PersonBuilder().build();
        modelManager.addPerson(person);
        GoodsReceipt goodsReceipt = new GoodsReceiptBuilder()
                .withSupplierName(person.getName())
                .build();

        assertTrue(person.getName().equals(goodsReceipt.getSupplierName()));
        assertDoesNotThrow(() -> modelManager.addGoods(goodsReceipt));
    }

    @Test
    public void getFilteredGoodsWithSupplierName() {
        modelManager.addPerson(ALICE);
        modelManager.addPerson(BOB);
        Goods apple = new GoodsBuilder()
                .withName("Apple")
                .withGoodsCategory(GoodsCategories.CONSUMABLES)
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
        modelManager.addGoods(appleReceipt);
        modelManager.addGoods(bananaReceipt);
        List<GoodsReceipt> goodsList = modelManager.getFilteredGoods(r -> r.isFromSupplier(ALICE.getName()));
        assertEquals(goodsList.size(), 1);
        assertEquals(goodsList.get(0), appleReceipt);
    }

    @Test
    public void getFilteredPersonsWithGoodsCategoryTagsAdded() {
        modelManager.addPerson(ALICE);
        Goods apple = new GoodsBuilder()
                .withName("Apple")
                .withGoodsCategory(GoodsCategories.SPECIALTY)
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
                .withSupplierName(ALICE.getName())
                .withGoods(banana)
                .build();
        modelManager.addGoods(appleReceipt);
        modelManager.addGoods(bananaReceipt);
        modelManager.updateFilteredPersonList(p -> p.equals(ALICE));
        Person alice = modelManager
                .getObservableFilteredPersonsWithGoodsCategoryTagsAdded()
                .stream().findAny().get();
        Set<Tag> expectedTags = Stream
                .of(ALICE.getTags(), List.of(new Tag("SPECIALTY"), new Tag("CONSUMABLES")))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        assertEquals(alice.getTags(), expectedTags);
    }
}
