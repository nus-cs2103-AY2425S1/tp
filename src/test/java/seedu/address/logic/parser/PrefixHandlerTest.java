package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.prefix.PrefixHandler;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;



public class PrefixHandlerTest {

    private PrefixHandler prefixHandler = new PrefixHandler();

    @Test
    public void getArgumentType_invalidArgument() {
        String userInput = "";
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_NAME, PREFIX_ROLE,
                        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);
        String actualResult = prefixHandler.getArgumentType(argMultimap);

        assertEquals("DEFAULT", actualResult);

    }



    @Test
    public void getArgumentType_validName() {
        String userInput = NAME_DESC_AMY;
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_NAME, PREFIX_ROLE,
                        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);
        String actualResult = prefixHandler.getArgumentType(argMultimap);

        assertEquals("NAME", actualResult);

    }

    @Test
    public void getArgumentType_validEmail() {
        String userInput = EMAIL_DESC_AMY;
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_NAME, PREFIX_ROLE,
                        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);
        String actualResult = prefixHandler.getArgumentType(argMultimap);

        assertEquals("EMAIL", actualResult);

    }

    @Test
    public void getArgumentType_validRole() {
        String userInput = ROLE_DESC_AMY;
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_NAME, PREFIX_ROLE,
                        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);
        String actualResult = prefixHandler.getArgumentType(argMultimap);

        assertEquals("ROLE", actualResult);

    }


    @Test
    public void getArgumentType_validPhoneNumber() {
        String userInput = PHONE_DESC_AMY;
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_NAME, PREFIX_ROLE,
                        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);
        String actualResult = prefixHandler.getArgumentType(argMultimap);

        assertEquals("PHONE", actualResult);

    }

    @Test
    public void getArgumentType_validAddress() {
        String userInput = ADDRESS_DESC_AMY;
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_NAME, PREFIX_ROLE,
                        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);
        String actualResult = prefixHandler.getArgumentType(argMultimap);

        assertEquals("ADDRESS", actualResult);

    }

    @Test
    public void getArgumentType_validTags() {
        String userInput = TAG_DESC_FRIEND;
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_NAME, PREFIX_ROLE,
                        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);
        String actualResult = prefixHandler.getArgumentType(argMultimap);

        assertEquals("TAG", actualResult);

    }

    @Test
    public void getArgumentType_validIndex() {
        String userInput = "1";
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_NAME, PREFIX_ROLE,
                        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);
        String actualResult = prefixHandler.getArgumentType(argMultimap);

        assertEquals("INDEX", actualResult);

    }
    @Test
    public void getTypeOfAttribute_validName() {
        String userInput = VALID_NAME_AMY;
        Name name = new Name(userInput);
        String actualResult = prefixHandler.getTypeOfAttribute(name);

        assertEquals("NAME", actualResult);

    }

    @Test
    public void getTypeOfAttribute_validEmail() {
        String userInput = VALID_EMAIL_AMY;
        Email email = new Email(userInput);
        String actualResult = prefixHandler.getTypeOfAttribute(email);

        assertEquals("EMAIL", actualResult);

    }

    @Test
    public void getTypeOfAttribute_validAddress() {
        String userInput = VALID_ADDRESS_AMY;
        Address address = new Address(userInput);
        String actualResult = prefixHandler.getTypeOfAttribute(address);

        assertEquals("ADDRESS", actualResult);

    }


    @Test
    public void getTypeOfAttribute_validTags() {
        String userInput = VALID_TAG_FRIEND;
        Tag tag = new Tag(userInput);
        Set<Tag> tags = new HashSet<>();
        tags.add(tag);
        String actualResult = prefixHandler.getTypeOfAttribute(tags);

        assertEquals("TAG", actualResult);

    }

    @Test
    public void getTypeOfAttribute_validRole() {
        String userInput = VALID_ROLE_AMY;
        Role role = new Role(userInput);
        String actualResult = prefixHandler.getTypeOfAttribute(role);

        assertEquals("ROLE", actualResult);

    }

    @Test
    public void getTypeOfAttribute_validPhoneNumber() {
        String userInput = VALID_PHONE_AMY;
        Phone phone = new Phone(userInput);
        String actualResult = prefixHandler.getTypeOfAttribute(phone);

        assertEquals("PHONE", actualResult);

    }

    @Test
    public void getTypeOfAttribute_invalidInput() {
        String userInput = VALID_PHONE_AMY;
        String actualResult = prefixHandler.getTypeOfAttribute(userInput);

        assertEquals("ERROR", actualResult);

    }

    @Test
    public void findPersonByAttribute_validPhone() {
        String userInput = "94351253";
        Phone phone = new Phone(userInput);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        List<Person> list = model.getFilteredPersonList();
        List<Person> actualResult = prefixHandler.findPersonByAttribute(list, phone);
        List<Person> expectedResult = new ArrayList<>();
        expectedResult.add(ALICE);
        assertEquals(expectedResult, actualResult);

    }

    @Test
    public void findPersonByAttribute_validName() {
        String userInput = "Alice Pauline";
        Name name = new Name(userInput);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        List<Person> list = model.getFilteredPersonList();
        List<Person> actualResult = prefixHandler.findPersonByAttribute(list, name);
        List<Person> expectedResult = new ArrayList<>();
        expectedResult.add(ALICE);
        assertEquals(expectedResult, actualResult);

    }

    @Test
    public void findPersonByAttribute_validEmail() {
        String userInput = "alice@example.com";
        Email email = new Email(userInput);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        List<Person> list = model.getFilteredPersonList();
        List<Person> actualResult = prefixHandler.findPersonByAttribute(list, email);
        List<Person> expectedResult = new ArrayList<>();
        expectedResult.add(ALICE);
        assertEquals(expectedResult, actualResult);

    }


    @Test
    public void findPersonByAttribute_validAddress() {
        String userInput = "123, Jurong West Ave 6, #08-111";
        Address address = new Address(userInput);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        List<Person> list = model.getFilteredPersonList();
        List<Person> actualResult = prefixHandler.findPersonByAttribute(list, address);
        List<Person> expectedResult = new ArrayList<>();
        expectedResult.add(ALICE);
        assertEquals(expectedResult, actualResult);

    }



    @Test
    public void findPersonByAttribute_validTag() {
        String userInput1 = "friends";
        Tag tag1 = new Tag(userInput1);
        Set<Tag> tags = new HashSet<>();
        tags.add(tag1);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        List<Person> list = model.getFilteredPersonList();
        List<Person> actualResult = prefixHandler.findPersonByAttribute(list, tags);
        List<Person> expectedResult = new ArrayList<>();
        expectedResult.add(ALICE);
        expectedResult.add(BENSON);
        expectedResult.add(DANIEL);
        assertEquals(expectedResult, actualResult);

    }

    @Test
    public void findPersonByAttribute_validRole() {
        String userInput = "student";
        Role role = new Role(userInput);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        List<Person> list = model.getFilteredPersonList();
        List<Person> actualResult = prefixHandler.findPersonByAttribute(list, role);
        List<Person> expectedResult = new ArrayList<>();
        expectedResult.add(ALICE);
        expectedResult.add(BENSON);
        expectedResult.add(CARL);
        expectedResult.add(DANIEL);
        expectedResult.add(ELLE);
        expectedResult.add(FIONA);
        assertEquals(expectedResult, actualResult);

    }


    @Test
    public void findPersonByAttribute_invalidInput() {
        String userInput = "";
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        List<Person> list = model.getFilteredPersonList();
        List<Person> actualResult = prefixHandler.findPersonByAttribute(list, userInput);
        List<Person> expectedResult = new ArrayList<>();
        assertEquals(expectedResult, actualResult);

    }

}
