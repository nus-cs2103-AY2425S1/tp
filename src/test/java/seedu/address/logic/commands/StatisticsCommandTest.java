package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.statistics.AddressBookStatistics;

public class StatisticsCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_typicalAddressBookUnFilteredList_success() {
        AddressBook addressBookOfCurrentModel = (AddressBook) model.getAddressBook();
        AddressBookStatistics addressBookStatistics = addressBookOfCurrentModel.getStatistics();
        String formattedStatistics = ParserUtil.parseAddressBookStatistics(addressBookStatistics);
        assertCommandSuccess(new StatisticsCommand(), model,
                StatisticsCommand.MESSAGE_SUCCESS + formattedStatistics, expectedModel);
    }

    @Test
    public void execute_emptyAddressBook_success() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
        expectedModel = new ModelManager(new AddressBook(), new UserPrefs());

        AddressBook addressBookOfCurrentModel = (AddressBook) model.getAddressBook();
        AddressBookStatistics emptyStatistics = addressBookOfCurrentModel.getStatistics();
        assertNotNull(emptyStatistics, "Statistics should be initialized even if AddressBook is empty.");
        String formattedStatistics = ParserUtil.parseAddressBookStatistics(emptyStatistics);

        assertCommandSuccess(new StatisticsCommand(), model,
                StatisticsCommand.MESSAGE_SUCCESS + formattedStatistics, expectedModel);
    }

    @Test
    public void execute_typicalAddressBookFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        AddressBook addressBookOfCurrentModel = (AddressBook) model.getAddressBook();
        AddressBookStatistics addressBookStatistics = addressBookOfCurrentModel.getStatistics();
        String formattedStatistics = ParserUtil.parseAddressBookStatistics(addressBookStatistics);

        assertCommandSuccess(new StatisticsCommand(), model,
                StatisticsCommand.MESSAGE_SUCCESS + formattedStatistics, expectedModel);
    }

}

