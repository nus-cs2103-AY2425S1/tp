package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.searchmode.SearchModeSearchCommand;
import seedu.address.logic.parser.SearchModeSearchCommandParser;
import seedu.address.model.Model;
import seedu.address.model.person.FieldContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonIsRolePredicate;
import seedu.address.model.role.Role;
import seedu.address.model.role.RoleHandler;
import seedu.address.model.role.exceptions.InvalidRoleException;

import java.util.*;
import java.util.function.Predicate;

public class SearchModeSearchCommandParserTest {
    //    public static final String VALID_NAME_AMY = "Amy Bee";
    //    public static final String VALID_NAME_BOB = "Bob Choo";
    //    public static final String VALID_PHONE_AMY = "11111111";
    //    public static final String VALID_PHONE_BOB = "22222222";
    //    public static final String VALID_EMAIL_AMY = "amy@example.com";
    //    public static final String VALID_EMAIL_BOB = "bob@example.com";
    //    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    //    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    //    public static final String VALID_TELEGRAM_AMY = "amybee";
    //    public static final String VALID_TELEGRAM_BOB = "bobby";

    private SearchModeSearchCommandParser parser = new SearchModeSearchCommandParser();
    @Test
    public void parse_allFieldsPresent_success() {
        List<Role> roleList = new ArrayList<>();
        try {
            roleList = Collections.singletonList(RoleHandler.getRole("vendor"));

        } catch (InvalidRoleException e) {
            e.printStackTrace();
        }

        Predicate<Person> namePredicate = new FieldContainsKeywordsPredicate<>(
                Collections.singletonList(VALID_NAME_AMY),
                Person::getName);
        Predicate<Person> phonePredicate = new FieldContainsKeywordsPredicate<>(
                Collections.singletonList(VALID_PHONE_AMY),
                Person::getPhone);
        Predicate<Person> rolePredicate = new PersonIsRolePredicate(roleList);
        Predicate<Person> addressPredicate = new FieldContainsKeywordsPredicate<>(
                Collections.singletonList(VALID_ADDRESS_AMY),
                Person::getAddress);
        Predicate<Person> emailPredicate = new FieldContainsKeywordsPredicate<>(
                Collections.singletonList(VALID_EMAIL_AMY),
                Person::getEmail);
        Predicate<Person> telegramPredicate = new FieldContainsKeywordsPredicate<>(
                Collections.singletonList(VALID_TELEGRAM_AMY),
                Person::getTelegramUsername);


        Set<Predicate<Person>> predicates = new HashSet<>();
        predicates.add(namePredicate);
        predicates.add(phonePredicate);
        predicates.add(rolePredicate);
        predicates.add(addressPredicate);
        predicates.add(emailPredicate);
        predicates.add(telegramPredicate);


        SearchModeSearchCommand expectedCommand = new SearchModeSearchCommand(predicates);

        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY +
                EMAIL_DESC_AMY + ADDRESS_DESC_AMY + TELEGRAM_DESC_AMY + ROLE_DESC_VENDOR, expectedCommand);
    }

    @Test
    public void parse_someFieldsPresent_success() {
        try {
            List<Role> roleList = Collections.singletonList(RoleHandler.getRole("vendor"));
            Predicate<Person> predicate = new FieldContainsKeywordsPredicate<>(
                    Collections.singletonList(VALID_NAME_AMY),
                    Person::getName)
                    .and(new FieldContainsKeywordsPredicate<>(
                            Collections.singletonList(VALID_PHONE_AMY), Person::getPhone))
                    .and(new PersonIsRolePredicate(roleList));

            SearchModeSearchCommand expectedCommand = new SearchModeSearchCommand(predicate);

            assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + ROLE_DESC_VENDOR, expectedCommand);
        } catch (InvalidRoleException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void parse_noFieldsPresent_success() {
        Predicate<Person> predicate = Model.PREDICATE_SHOW_ALL_PERSONS;
        SearchModeSearchCommand expectedCommand = new SearchModeSearchCommand(predicate);

        assertParseSuccess(parser, "", expectedCommand);
    }
}