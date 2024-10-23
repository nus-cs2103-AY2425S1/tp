package tutorease.address.testutil;

import static tutorease.address.logic.commands.CommandTestUtil.VALID_ADDRESS_CHICK;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_ADDRESS_MEG;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_EMAIL_CHICK;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_EMAIL_MEG;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_NAME_CHICK;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_NAME_MEG;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_PHONE_CHICK;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_PHONE_MEG;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_ROLE_CHICK;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_ROLE_MEG;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_TAG_MENTOR;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_TAG_SUPPORTIVE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tutorease.address.model.person.Guardian;
import tutorease.address.model.person.Person;

/**
 * A utility class containing a list of {@code Guardians} objects to be used in tests.
 */
public class TypicalGuardians {
    public static final Guardian ALEX = new GuardianBuilder().withName("Alex Thompson")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alex.thompson@example.com")
            .withPhone("94351253").withRole("Guardian")
            .withTags("parent", "supportive").build();

    public static final Guardian BRADLEY = new GuardianBuilder().withName("Bradley Cooper")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("bradley.cooper@example.com").withPhone("98765432").withRole("Guardian")
            .withTags("mentor", "familyFriend").build();

    public static final Guardian CHARLOTTE = new GuardianBuilder().withName("Charlotte Green").withPhone("95352563")
            .withEmail("charlotte.green@example.com").withAddress("Wall Street").withRole("Guardian").build();

    public static final Guardian DAVID = new GuardianBuilder().withName("David Johnson").withPhone("87652533")
            .withEmail("david.johnson@example.com")
            .withAddress("10th Street").withRole("Guardian").withTags("mentor", "trustworthy").build();

    public static final Guardian EMILY = new GuardianBuilder().withName("Emily Watson").withPhone("9482224")
            .withEmail("emily.watson@example.com").withAddress("Michigan Ave").withRole("Guardian").build();

    public static final Guardian FELICIA = new GuardianBuilder().withName("Felicia Tan").withPhone("9482427")
            .withEmail("felicia.tan@example.com").withAddress("Little Tokyo").withRole("Guardian").build();

    public static final Guardian GARY = new GuardianBuilder().withName("Gary Lee").withPhone("9482442")
            .withEmail("gary.lee@example.com").withAddress("4th Street").withRole("Guardian").build();

    // Manually added
    public static final Guardian HENRY = new GuardianBuilder().withName("Henry Adams").withPhone("8482424")
            .withEmail("henry.adams@example.com").withAddress("Little India").withRole("Guardian").build();

    public static final Guardian ISABEL = new GuardianBuilder().withName("Isabel Flores").withPhone("8482131")
            .withEmail("isabel.flores@example.com").withAddress("Chicago Ave").withRole("Guardian").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Guardian MEG = new GuardianBuilder().withName(VALID_NAME_MEG).withPhone(VALID_PHONE_MEG)
            .withEmail(VALID_EMAIL_MEG).withAddress(VALID_ADDRESS_MEG)
            .withRole(VALID_ROLE_MEG).withTags(VALID_TAG_SUPPORTIVE).build();

    public static final Guardian CHICK = new GuardianBuilder().withName(VALID_NAME_CHICK).withPhone(VALID_PHONE_CHICK)
            .withEmail(VALID_EMAIL_CHICK).withAddress(VALID_ADDRESS_CHICK)
            .withRole(VALID_ROLE_CHICK).withTags(VALID_TAG_MENTOR, VALID_TAG_SUPPORTIVE).build();

    public static final String KEYWORD_MATCHING_JOHNSON = "Johnson"; // A keyword that matches JOHNSON

    private TypicalGuardians() {} // prevents instantiation

    public static List<Person> getTypicalGuardians() {
        return new ArrayList<>(Arrays.asList(ALEX, BRADLEY, CHARLOTTE, DAVID, EMILY, FELICIA, GARY));
    }

}
