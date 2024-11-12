package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDED_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASKING_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.buyer.EditCommand.EditBuyerDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.meetup.EditCommand.EditMeetUpDescriptor;
import seedu.address.logic.commands.property.EditCommand.EditPropertyDescriptor;
import seedu.address.model.BuyerList;
import seedu.address.model.Model;
import seedu.address.model.buyer.Buyer;
import seedu.address.model.buyer.NameContainsKeywordsPredicate;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.meetup.MeetUpContainsKeywordsPredicate;
import seedu.address.model.property.AddressContainsKeywordsPredicate;
import seedu.address.model.property.Property;
import seedu.address.testutil.buyer.EditBuyerDescriptorBuilder;
import seedu.address.testutil.meetup.EditMeetUpDescriptorBuilder;
import seedu.address.testutil.property.EditPropertyDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_BUDGET_AMY = "1,230,000";
    public static final String VALID_BUDGET_BOB = "600,230";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_MEETUP_SUBJECT_PITCH = "Sales Pitch";
    public static final String VALID_MEETUP_SUBJECT_NETWORKING = "Networking Session";
    public static final String VALID_MEETUP_INFO_PITCH = "Pitching property at Bukit Timah.";
    public static final String VALID_MEETUP_INFO_NETWORKING = "Networking with real estate agents.";
    public static final String VALID_MEETUP_FROM_PITCH = "2024-09-11 12:00";
    public static final String VALID_MEETUP_FROM_NETWORKING = "2024-10-12 17:30";
    public static final String VALID_MEETUP_TO_PITCH = "2024-09-11 12:59";
    public static final String VALID_MEETUP_TO_NETWORKING = "2024-10-12 19:45";
    public static final String VALID_MEETUP_ADDED_PERSON_ALEX = "Alex Yeoh";
    public static final String VALID_MEETUP_ADDED_PERSON_BETTY = "Betty Ho";
    public static final String VALID_LANDLORD_NAME_ALAN = "Alan Wong";
    public static final String VALID_LANDLORD_NAME_BRENDA = "Brenda Lee";
    public static final String VALID_PHONE_ALAN = "11111111";
    public static final String VALID_PHONE_BRENDA = "22222222";
    public static final String VALID_ASKING_PRICE_ALAN = "1200000";
    public static final String VALID_ASKING_PRICE_BRENDA = "600000";
    public static final String VALID_PROPERTY_TYPE_ALAN = "HDB";
    public static final String VALID_PROPERTY_TYPE_BRENDA = "Terrace";
    public static final String VALID_ADDRESS_ALAN = "Woodlands Avenue Blk 102 #02-02";
    public static final String VALID_ADDRESS_BRENDA = "Bukit Timah Rd #01-01";
    public static final String VALID_FIND_MEETUP_SUBJECT = "Alice Bob";
    public static final String VALID_FIND_BUYER_NAME = "Alice Bob";
    public static final String VALID_FIND_ADDRESS_NAME = "Jurong Clementi";
    public static final String VALID_FIND_LANDLORD_NAME = "Jerald James";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NAME_DESC_FIND_ALICE_BOB = " " + PREFIX_NAME + VALID_FIND_BUYER_NAME;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String BUDGET_DESC_AMY = " " + PREFIX_BUDGET + VALID_BUDGET_AMY;
    public static final String BUDGET_DESC_BOB = " " + PREFIX_BUDGET + VALID_BUDGET_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String ADDRESS_FIND_DESC = " " + PREFIX_ADDRESS + VALID_FIND_ADDRESS_NAME;
    public static final String LANDLORD_FIND_DESC = " " + PREFIX_NAME + VALID_FIND_LANDLORD_NAME;
    public static final String MEETUP_SUBJECT_DESC_PITCH = " " + PREFIX_SUBJECT + VALID_MEETUP_SUBJECT_PITCH;
    public static final String MEETUP_SUBJECT_DESC_NETWORKING = " " + PREFIX_SUBJECT + VALID_MEETUP_SUBJECT_NETWORKING;
    public static final String MEETUP_INFO_DESC_PITCH = " " + PREFIX_INFO + VALID_MEETUP_INFO_PITCH;
    public static final String MEETUP_INFO_DESC_NETWORKING = " " + PREFIX_INFO + VALID_MEETUP_INFO_NETWORKING;
    public static final String MEETUP_FROM_DESC_PITCH = " " + PREFIX_FROM + VALID_MEETUP_FROM_PITCH;
    public static final String MEETUP_FROM_DESC_NETWORKING = " " + PREFIX_FROM + VALID_MEETUP_FROM_NETWORKING;
    public static final String MEETUP_TO_DESC_PITCH = " " + PREFIX_TO + VALID_MEETUP_TO_PITCH;
    public static final String MEETUP_TO_DESC_NETWORKING = " " + PREFIX_TO + VALID_MEETUP_TO_NETWORKING;
    public static final String MEETUP_ADDED_BUYER_DESC_PITCH = " " + PREFIX_ADDED_BUYER
            + VALID_MEETUP_ADDED_PERSON_ALEX;
    public static final String MEETUP_ADDED_BUYER_DESC_NETWORKING = " " + " " + PREFIX_ADDED_BUYER
            + VALID_MEETUP_ADDED_PERSON_BETTY;
    public static final String MEETUP_FIND_DESC = " " + PREFIX_SUBJECT + VALID_FIND_MEETUP_SUBJECT;
    public static final String LANDLORD_NAME_DESC_ALAN = " " + PREFIX_NAME + VALID_LANDLORD_NAME_ALAN;
    public static final String LANDLORD_NAME_DESC_BRENDA = " " + PREFIX_NAME + VALID_LANDLORD_NAME_BRENDA;
    public static final String PHONE_DESC_ALAN = " " + PREFIX_PHONE + VALID_PHONE_ALAN;
    public static final String PHONE_DESC_BRENDA = " " + PREFIX_PHONE + VALID_PHONE_BRENDA;
    public static final String ADDRESS_DESC_ALAN = " " + PREFIX_ADDRESS + VALID_ADDRESS_ALAN;
    public static final String ADDRESS_DESC_BRENDA = " " + PREFIX_ADDRESS + VALID_ADDRESS_BRENDA;
    public static final String ASKING_PRICE_DESC_ALAN = " " + PREFIX_ASKING_PRICE + VALID_ASKING_PRICE_ALAN;
    public static final String ASKING_PRICE_DESC_BRENDA = " " + PREFIX_ASKING_PRICE + VALID_ASKING_PRICE_BRENDA;
    public static final String PROPERTY_TYPE_DESC_ALAN = " " + PREFIX_TYPE + VALID_PROPERTY_TYPE_ALAN;
    public static final String PROPERTY_TYPE_DESC_BRENDA = " " + PREFIX_TYPE + VALID_PROPERTY_TYPE_BRENDA;
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_BUYER_FIND_DESC = " " + PREFIX_NAME + "Jankos*(("; // '*' and '(' not allowed
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_BUDGET_DESC = " " + PREFIX_BUDGET
            + "-1"; // negative integer not allowed for budget
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_MEETUP_SUBJECT_DESC = " " + PREFIX_SUBJECT // '!', '%' not allowed in names
            + "Sales Pitch!%";
    public static final String INVALID_PROPERTY_FIND_DESC = " " + PREFIX_NAME + "James^"; // '^' not allowed in names
    public static final String MULTIPLE_PROPERTY_FIND_DESC = " " + PREFIX_ADDRESS + "Jurong Clementi "
            + PREFIX_NAME + "Ron"; // multiple prefit not allowed
    public static final String INVALID_MEETUP_FROM_DESC = " " + PREFIX_FROM + "01/02/2025 1800"; // Not proper format
    public static final String INVALID_MEETUP_TO_DESC = " " + PREFIX_TO + "01/05/2025 1940"; // Not proper format
    public static final String INVALID_MEETUP_INFO_DESC = " " + PREFIX_INFO; // Empty info not allowed
    public static final String INVALID_MEETUP_ADDED_BUYER_DESC = " " + PREFIX_ADDED_BUYER + "S@m"; // '@' not allowed
    public static final String INVALID_MEETUP_FIND_DESC = " " + PREFIX_SUBJECT + "@fternoon"; // '@' not allowed
    public static final String INVALID_MEETUP_FIND_PREFIX_DESC = " " + PREFIX_PHONE + "Afternoon";
    public static final String INVALID_LANDLORD_NAME_DESC = " " + PREFIX_NAME + "Whitt@cker"; // '@' not allowed
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // Empty address not allowed
    public static final String INVALID_ASKING_PRICE_DESC = " " + PREFIX_ASKING_PRICE
            + "-1"; // negative integer not allowed for asking price
    public static final String INVALID_PROPERTY_TYPE_DESC = " " + PREFIX_TYPE; // Empty property type not allowed
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditBuyerDescriptor DESC_AMY;
    public static final EditBuyerDescriptor DESC_BOB;
    public static final EditMeetUpDescriptor DESC_PITCH_MEETUP;
    public static final EditMeetUpDescriptor DESC_NETWORKING_MEETUP;
    public static final EditPropertyDescriptor DESC_ALAN;
    public static final EditPropertyDescriptor DESC_BRENDA;

    static {
        DESC_AMY = new EditBuyerDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withBudget(VALID_BUDGET_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditBuyerDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withBudget(VALID_BUDGET_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        DESC_PITCH_MEETUP = new EditMeetUpDescriptorBuilder().withSubject(VALID_MEETUP_SUBJECT_PITCH)
                .withInfo(VALID_MEETUP_INFO_PITCH).withFrom(VALID_MEETUP_FROM_PITCH)
                .withTo(VALID_MEETUP_TO_PITCH).build();
        DESC_NETWORKING_MEETUP = new EditMeetUpDescriptorBuilder().withSubject(VALID_MEETUP_SUBJECT_NETWORKING)
                .withInfo(VALID_MEETUP_INFO_NETWORKING).withFrom(VALID_MEETUP_FROM_NETWORKING)
                .withTo(VALID_MEETUP_TO_NETWORKING).build();
        DESC_ALAN = new EditPropertyDescriptorBuilder().withLandlordName(VALID_LANDLORD_NAME_ALAN)
                .withPhone(VALID_PHONE_ALAN).withAddress(VALID_ADDRESS_ALAN).withAskingPrice(VALID_ASKING_PRICE_ALAN)
                .withPropertyType(VALID_PROPERTY_TYPE_ALAN).build();
        DESC_BRENDA = new EditPropertyDescriptorBuilder().withLandlordName(VALID_LANDLORD_NAME_BRENDA)
                .withPhone(VALID_PHONE_BRENDA).withAddress(VALID_ADDRESS_BRENDA)
                .withAskingPrice(VALID_ASKING_PRICE_BRENDA).withPropertyType(VALID_PROPERTY_TYPE_BRENDA).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the buyer list, filtered buyer list and selected buyer in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        BuyerList expectedBuyerList = new BuyerList(actualModel.getBuyerList());
        List<Buyer> expectedFilteredList = new ArrayList<>(actualModel.getFilteredBuyerList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedBuyerList, actualModel.getBuyerList());
        assertEquals(expectedFilteredList, actualModel.getFilteredBuyerList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the buyer at the given {@code targetIndex} in the
     * {@code model}'s buyer list.
     */
    public static void showBuyerAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredBuyerList().size());

        Buyer buyer = model.getFilteredBuyerList().get(targetIndex.getZeroBased());
        final String[] splitName = buyer.getName().fullName.split("\\s+");
        model.updateFilteredBuyerList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredBuyerList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the meet-up at the given {@code targetIndex} in the
     * {@code model}'s meet-up list.
     */
    public static void showMeetUpAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredMeetUpList().size());

        MeetUp meetUp = model.getFilteredMeetUpList().get(targetIndex.getZeroBased());
        final String[] splitName = meetUp.getSubject().meetUpFullSubject.split("\\s+");
        model.updateFilteredMeetUpList(new MeetUpContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredMeetUpList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the property at the given {@code targetIndex} in the
     * {@code model}'s property list.
     */
    public static void showPropertyAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPropertyList().size());

        Property property = model.getFilteredPropertyList().get(targetIndex.getZeroBased());
        final String[] splitAddress = property.getAddress().value.split("\\s+");
        model.updateFilteredPropertyList(new AddressContainsKeywordsPredicate(Arrays.asList(splitAddress[0])));

        assertEquals(1, model.getFilteredPropertyList().size());
    }
}
