package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.BookingIsOnDate;
import seedu.address.model.person.Person;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.Assert;
import seedu.address.testutil.PersonBuilder;

public class BookingsCommandTest {
    private Model model;
    private Model expectedModel;
    private List<Person> personList;

    @BeforeEach
    public void setUp() throws IOException {
        // Set up the storage manager
        Path tempFolder = Paths.get(System.getProperty("java.io.tmpdir"));
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(tempFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(tempFolder.resolve("userPrefs.json"));
        StorageManager storage =
                new StorageManager(addressBookStorage, userPrefsStorage);

        // Initialize the model with typical data
        model = new ModelManager(new AddressBook(), new UserPrefs(), storage);

        // Set up the storage manager
        JsonAddressBookStorage addressBookExpectedStorage =
                new JsonAddressBookStorage(tempFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsExpectedStorage =
                new JsonUserPrefsStorage(tempFolder.resolve("userPrefs.json"));
        StorageManager expectedStorage =
                new StorageManager(addressBookExpectedStorage, userPrefsExpectedStorage);

        // Initialize the model with typical data
        expectedModel = new ModelManager(new AddressBook(), new UserPrefs(), expectedStorage);

        // According to message constraints for dates dd/MM/yyyy HH:mm
        String dateTime1 = "01/02/2024 00:00";
        Person alice = new PersonBuilder(ALICE).withAppointment(dateTime1).build();

        String dateTime2 = "01/02/2024 03:00";
        Person benson = new PersonBuilder(BENSON).withAppointment(dateTime2).build();

        String dateTime3 = "02/02/2024 05:00";
        Person carl = new PersonBuilder(CARL).withAppointment(dateTime3).build();

        String dateTime4 = "01/02/2024 23:59";
        Person daniel = new PersonBuilder(DANIEL).withAppointment(dateTime4).build();

        String dateTime5 = "02/02/2024 07:00";
        Person elle = new PersonBuilder(ELLE).withAppointment(dateTime5).build();

        model.addPerson(alice);
        model.addPerson(benson);
        model.addPerson(carl);
        model.addPerson(daniel);
        model.addPerson(elle);

        expectedModel.addPerson(alice);
        expectedModel.addPerson(benson);
        expectedModel.addPerson(carl);
        expectedModel.addPerson(daniel);
        expectedModel.addPerson(elle);


        // Initialize the personList for further tests
        personList = model.getFilteredPersonList(); // Retrieve the list of persons
    }

    @Test
    public void constructor_nullDate_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new BookingsCommand(null));
    }

    @Test
    public void execute_getBookings_listsCorrectPersons() throws Exception {
        LocalDate date = LocalDate.of(2024, 2, 1);
        String formattedDate = date.format(ParserUtil.ENGLISH_FORMAT);

        BookingsCommand bookingsCommand = new BookingsCommand(date);

        // Act
        CommandResult result = bookingsCommand.execute(model);

        BookingIsOnDate bookingIsOnDate = new BookingIsOnDate(date);

        expectedModel.updateFilteredPersonList(bookingIsOnDate);

        String expectedMessage = String.format(Messages.MESSAGE_NUMBER_OF_BOOKINGS, 3, formattedDate);
        // Assert
        assertCommandSuccess(bookingsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_getBookingsAnotherDate_listsCorrectPersons() throws Exception {
        LocalDate date = LocalDate.of(2024, 2, 2);
        String formattedDate = date.format(ParserUtil.ENGLISH_FORMAT);

        BookingsCommand bookingsCommand = new BookingsCommand(date);

        // Act
        CommandResult result = bookingsCommand.execute(model);

        BookingIsOnDate bookingIsOnDate = new BookingIsOnDate(date);

        expectedModel.updateFilteredPersonList(bookingIsOnDate);

        String expectedMessage = String.format(Messages.MESSAGE_NUMBER_OF_BOOKINGS, 2, formattedDate);
        // Assert
        assertCommandSuccess(bookingsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noMatchingBooking_showsMessage() throws Exception {
        LocalDate date = LocalDate.of(2024, 3, 1);
        String formattedDate = date.format(ParserUtil.ENGLISH_FORMAT);

        BookingsCommand bookingsCommand = new BookingsCommand(date);

        // Act
        CommandResult result = bookingsCommand.execute(model);

        BookingIsOnDate bookingIsOnDate = new BookingIsOnDate(date);

        expectedModel.updateFilteredPersonList(bookingIsOnDate);

        String expectedMessage = String.format(Messages.MESSAGE_NUMBER_OF_BOOKINGS, 0, formattedDate);
        // Assert
        assertCommandSuccess(bookingsCommand, model, expectedMessage, expectedModel);
        assertFalse(model.getFilteredPersonList().size() > 0);
    }

    @Test
    public void equals() {
        LocalDate date1 = LocalDate.of(2024, 1, 1);
        LocalDate date2 = LocalDate.of(2024, 2, 1);

        BookingsCommand bookingsCommand1 = new BookingsCommand(date1);
        BookingsCommand bookingsCommand2 = new BookingsCommand(date2);

        // same object -> returns true
        assertTrue(bookingsCommand1.equals(bookingsCommand1));

        // same values -> returns true
        BookingsCommand bookingsCommand1Copy = new BookingsCommand(date1);
        assertTrue(bookingsCommand1Copy.equals(bookingsCommand1));

        // different types -> returns false
        assertFalse(bookingsCommand1.equals(1));

        // null -> returns false
        assertFalse(bookingsCommand1.equals(null));

        // different person -> returns false
        assertFalse(bookingsCommand1.equals(bookingsCommand2));
    }

    @Test
    public void toStringMethod() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        BookingsCommand bookingsCommand = new BookingsCommand(date);
        String expected = BookingsCommand.class.getCanonicalName() + "{date=" + date + "}";
        assertEquals(expected, bookingsCommand.toString());
    }


}
