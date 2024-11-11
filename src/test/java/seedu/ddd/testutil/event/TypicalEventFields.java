package seedu.ddd.testutil.event;

import static seedu.ddd.model.util.SampleDataUtil.SAMPLE_EVENT_DATE;
import static seedu.ddd.model.util.SampleDataUtil.SAMPLE_EVENT_DESCRIPTION;
import static seedu.ddd.model.util.SampleDataUtil.SAMPLE_EVENT_ID;
import static seedu.ddd.model.util.SampleDataUtil.SAMPLE_EVENT_NAME;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_ID_FOUR;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_ID_ONE;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_ID_THREE;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_ID_TWO;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_ID;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_ID_FOUR;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_ID_ONE;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_ID_THREE;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_ID_TWO;
import static seedu.ddd.testutil.contact.TypicalContacts.VALID_CLIENT;
import static seedu.ddd.testutil.contact.TypicalContacts.VALID_VENDOR;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.ddd.model.common.Id;
import seedu.ddd.model.common.Name;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.model.event.common.Date;
import seedu.ddd.model.event.common.Description;

/**
 *  A utility class containing a list of {@code Event} field strings and objects to be used in tests.
 */
public class TypicalEventFields {

    // Strings
    public static final String VALID_EVENT_NAME_1 = SAMPLE_EVENT_NAME;
    public static final String VALID_EVENT_NAME_2 = "Wedding A";
    public static final String VALID_EVENT_NAME_3 = "Wedding B";
    public static final String VALID_EVENT_DESCRIPTION_1 = SAMPLE_EVENT_DESCRIPTION;
    public static final String VALID_EVENT_DESCRIPTION_2 = "Afterparty";
    public static final String VALID_EVENT_DATE = SAMPLE_EVENT_DATE;
    public static final String VALID_EVENT_CLIENT_ID_ONE = "0";
    public static final String VALID_EVENT_CLIENT_ID_TWO = "2";
    public static final String VALID_EVENT_CLIENT_ID_THREE = "4";
    public static final String VALID_EVENT_CLIENT_ID_FOUR = "6";

    public static final String VALID_EVENT_VENDOR_ID_ONE = "1";
    public static final String VALID_EVENT_VENDOR_ID_TWO = "3";
    public static final String VALID_EVENT_VENDOR_ID_THREE = "5";
    public static final String VALID_EVENT_VENDOR_ID_FOUR = "7";
    public static final String VALID_EVENT_ID = SAMPLE_EVENT_ID;
    public static final String INVALID_EVENT_NAME = "!@#$%^";
    public static final String INVALID_EVENT_DESC = "";

    public static final String INVALID_EVENT_DATE = "2000-01-011";
    public static final String INVALID_EVENT_CLIENT = "-1";
    public static final String INVALID_EVENT_VENDOR = "-1";
    public static final String INVALID_EVENT_ID = "-1";
    // Objects
    public static final Name DEFAULT_EVENT_NAME = new Name(VALID_EVENT_NAME_1);
    public static final Description DEFAULT_EVENT_DESCRIPTION = new Description(VALID_EVENT_DESCRIPTION_1);
    public static final Date DEFAULT_EVENT_DATE = new Date(VALID_EVENT_DATE);
    public static final Id DEFAULT_EVENT_ID = new Id(VALID_EVENT_ID);

    public static final List<Client> DEFAULT_EVENT_CLIENT_LIST = List.of(VALID_CLIENT);
    public static final List<Vendor> DEFAULT_EVENT_VENDOR_LIST = List.of(VALID_VENDOR);
    public static final Set<Client> DEFAULT_EVENT_CLIENT_SET = new HashSet<>(DEFAULT_EVENT_CLIENT_LIST);
    public static final Set<Client> DEFAULT_EVENT_VENDOR_SET = new HashSet<>(DEFAULT_EVENT_CLIENT_LIST);

    public static final Set<Id> DEFAULT_EVENT_CLIENT_CONTACT_ID_SET_SINGLE =
            Stream.of(VALID_CLIENT_ID_ONE)
            .map(Id::new)
            .collect(Collectors.toSet());
    public static final Set<Id> DEFAULT_EVENT_VENDOR_CONTACT_ID_SET_SINGLE =
            Stream.of(VALID_VENDOR_ID)
            .map(Id::new)
            .collect(Collectors.toSet());

    public static final Set<Id> DEFAULT_EVENT_CLIENT_CONTACT_ID_SET_MULTIPLE =
            Stream.of(VALID_CLIENT_ID_ONE, VALID_CLIENT_ID_TWO, VALID_CLIENT_ID_THREE, VALID_CLIENT_ID_FOUR)
            .map(Id::new)
            .collect(Collectors.toSet());
    public static final Set<Id> DEFAULT_EVENT_VENDOR_CONTACT_ID_SET_MULTIPLE =
            Stream.of(VALID_VENDOR_ID_ONE, VALID_VENDOR_ID_TWO, VALID_VENDOR_ID_THREE, VALID_VENDOR_ID_FOUR)
            .map(Id::new)
            .collect(Collectors.toSet());

}
