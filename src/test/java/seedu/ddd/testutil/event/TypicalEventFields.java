package seedu.ddd.testutil.event;

import static seedu.ddd.model.util.SampleDataUtil.SAMPLE_EVENT_DATE;
import static seedu.ddd.model.util.SampleDataUtil.SAMPLE_EVENT_DESCRIPTION;
import static seedu.ddd.model.util.SampleDataUtil.SAMPLE_EVENT_ID;
import static seedu.ddd.model.util.SampleDataUtil.SAMPLE_EVENT_NAME;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_ID;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_ID;
import static seedu.ddd.testutil.contact.TypicalContacts.VALID_CLIENT;
import static seedu.ddd.testutil.contact.TypicalContacts.VALID_VENDOR;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.ddd.model.common.Name;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.ContactId;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.model.event.common.Date;
import seedu.ddd.model.event.common.Description;
import seedu.ddd.model.event.common.EventId;
/**
 *  A utility class containing a list of {@code Event} field strings and objects to be used in tests.
 */
public class TypicalEventFields {

    // Strings
    public static final String VALID_EVENT_NAME = SAMPLE_EVENT_NAME;
    public static final String VALID_EVENT_DESCRIPTION_1 = SAMPLE_EVENT_DESCRIPTION;
    public static final String VALID_EVENT_DESCRIPTION_2 = "Afterparty";
    public static final String VALID_EVENT_DATE = SAMPLE_EVENT_DATE;
    public static final String VALID_EVENT_CLIENT_CONTACT_ID = "1";
    public static final String VALID_EVENT_ID = SAMPLE_EVENT_ID;
    public static final String INVALID_EVENT_DESCRIPTION = "";
    public static final String INVALID_EVENT_ID = "-1";

    // Objects
    public static final Name DEFAULT_EVENT_NAME = new Name(VALID_EVENT_NAME);
    public static final Description DEFAULT_EVENT_DESCRIPTION = new Description(VALID_EVENT_DESCRIPTION_1);
    public static final Date DEFAULT_EVENT_DATE = new Date(VALID_EVENT_DATE);
    public static final EventId DEFAULT_EVENT_ID = new EventId(VALID_EVENT_ID);

    public static final List<Client> DEFAULT_EVENT_CLIENT_LIST = List.of(VALID_CLIENT);
    public static final List<Vendor> DEFAULT_EVENT_VENDOR_LIST = List.of(VALID_VENDOR);
    public static final Set<Client> DEFAULT_EVENT_CLIENT_SET = new HashSet<>(DEFAULT_EVENT_CLIENT_LIST);
    public static final Set<Client> DEFAULT_EVENT_VENDOR_SET = new HashSet<>(DEFAULT_EVENT_CLIENT_LIST);

    public static final Set<ContactId> DEFAULT_EVENT_CLIENT_CONTACT_ID_SET = Stream.of(VALID_CLIENT_ID)
            .map(ContactId::new)
            .collect(Collectors.toSet());
    public static final Set<ContactId> DEFAULT_EVENT_VENDOR_CONTACT_ID_SET = Stream.of(VALID_VENDOR_ID)
            .map(ContactId::new)
            .collect(Collectors.toSet());

}
