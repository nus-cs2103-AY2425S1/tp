// package seedu.address.logic.commands;

// import static seedu.address.logic.commands.CommandTestUtil.VALID_CLIENT_STATUS_BLACKLISTED;
// import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
// import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
// import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

// import java.util.Random;

// import org.junit.jupiter.api.Test;

// import seedu.address.commons.core.index.Index;
// import seedu.address.logic.Messages;
// import seedu.address.model.AddressBook;
// import seedu.address.model.ArchivedAddressBook;
// import seedu.address.model.Model;
// import seedu.address.model.ModelManager;
// import seedu.address.model.UserPrefs;
// import seedu.address.model.person.Person;
// import seedu.address.testutil.PersonBuilder;

// public class BlacklistCommandTest {

//     private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ArchivedAddressBook());
//     private Random rng = new Random();

//     @Test
//     public void blacklist_firstPerson_success() {
//         Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

//         BlacklistCommand blacklistCommand = new BlacklistCommand(INDEX_FIRST_PERSON);

//         Person blacklistedPerson = new PersonBuilder(firstPerson)
//                 .withClientStatus(VALID_CLIENT_STATUS_BLACKLISTED).build();

//         Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
//                 new ArchivedAddressBook());

//         String expectedMessage = String.format(
//                  BlacklistCommand.MESSAGE_BLACKLIST_PERSON_SUCCESS, Messages.format(blacklistedPerson));

//         expectedModel.setPerson(firstPerson, blacklistedPerson);

//         assertCommandSuccess(blacklistCommand, model, expectedMessage, expectedModel);
//     }
// }
