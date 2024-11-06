package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyReceiptLog;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ReceiptLog;
import seedu.address.model.goods.Goods;
import seedu.address.model.goodsreceipt.GoodsReceipt;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalGoods;
import seedu.address.testutil.TypicalGoodsReceipts;

/**
 * Contains unit tests for the {@code AddGoodsCommand}.
 */
public class AddGoodsCommandTest {
    private static final Goods GOODS_TYPICAL = TypicalGoods.APPLE;
    private static final GoodsReceipt GOODSRECEIPT_ALICE = TypicalGoodsReceipts.ALICE_RECEIPT;
    private static final GoodsReceipt GOODSRECEIPT_BOB = TypicalGoodsReceipts.BOB_RECEIPT;

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddGoodsCommand(null, null));
    }

    @Test
    public void execute_goodsAccepted_success() throws CommandException {
        ModelStubAcceptingGoodsAdded successModelStub = new ModelStubAcceptingGoodsAdded();
        CommandResult result = new AddGoodsCommand(GOODS_TYPICAL, GOODSRECEIPT_ALICE).execute(successModelStub);

        assertEquals(String.format(AddGoodsCommand.MESSAGE_SUCCESS, GOODS_TYPICAL),
                result.getFeedbackToUser());
    }

    @Test
    public void equals() {
        AddGoodsCommand addGoodsAliceCommand = new AddGoodsCommand(GOODS_TYPICAL, GOODSRECEIPT_ALICE);
        AddGoodsCommand addGoodsBobCommand = new AddGoodsCommand(GOODS_TYPICAL, GOODSRECEIPT_BOB);

        // same object -> returns true
        assertTrue(addGoodsAliceCommand.equals(addGoodsAliceCommand));

        // same values -> returns true
        AddGoodsCommand addGoodsAliceCommandCopy = new AddGoodsCommand(GOODS_TYPICAL, GOODSRECEIPT_ALICE);
        assertTrue(addGoodsAliceCommand.equals(addGoodsAliceCommandCopy));

        // different types -> returns false
        assertFalse(addGoodsAliceCommand.equals(1.00));

        // null -> returns false
        assertFalse(addGoodsAliceCommand.equals(null));

        // different supplier -> returns false
        assertFalse(addGoodsAliceCommand.equals(addGoodsBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddGoodsCommand addGoodsCommand = new AddGoodsCommand(GOODS_TYPICAL, GOODSRECEIPT_ALICE);
        String expected = AddGoodsCommand.class.getCanonicalName() + "{toAdd=" + GOODS_TYPICAL + "}";
        assertEquals(expected, addGoodsCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Person> findPerson(Predicate<Person> predicate) {
            return Optional.empty();
        }

        @Override
        public List<Person> getPersonList() {
            return List.of();
        }

        @Override
        public void setGoods(ReadOnlyReceiptLog goodsReceipts) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyReceiptLog getGoods() {
            return new ReceiptLog();
        }

        @Override
        public void addGoods(GoodsReceipt goodsReceipt) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteGoods(GoodsReceipt goodsReceipt) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<GoodsReceipt> getFilteredReceiptsList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredReceiptsList(Predicate<GoodsReceipt> gr) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<GoodsReceipt> getFilteredGoods(Predicate<GoodsReceipt> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getFilteredGoodsQuantityStatistics() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public double getFilteredGoodsCostStatistics() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<GoodsReceipt> findGoodsReceipt(Predicate<GoodsReceipt> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getObservableFilteredPersonsWithGoodsCategoryTagsAdded() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that always accepts the goods added.
     */
    private class ModelStubAcceptingGoodsAdded extends ModelStub {
        final ArrayList<GoodsReceipt> goodsAdded = new ArrayList<>();

        @Override
        public void addGoods(GoodsReceipt goodsReceipt) {
            requireNonNull(goodsReceipt);
            goodsAdded.add(goodsReceipt);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
