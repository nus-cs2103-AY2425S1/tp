package seedu.ddd.testutil.contact;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static seedu.ddd.model.util.SampleDataUtil.SAMPLE_CLIENT_ADDRESS;
import static seedu.ddd.model.util.SampleDataUtil.SAMPLE_CLIENT_EMAIL;
import static seedu.ddd.model.util.SampleDataUtil.SAMPLE_CLIENT_ID;
import static seedu.ddd.model.util.SampleDataUtil.SAMPLE_CLIENT_NAME;
import static seedu.ddd.model.util.SampleDataUtil.SAMPLE_CLIENT_PHONE;
import static seedu.ddd.model.util.SampleDataUtil.SAMPLE_TAG_1;
import static seedu.ddd.model.util.SampleDataUtil.SAMPLE_TAG_2;
import static seedu.ddd.model.util.SampleDataUtil.SAMPLE_VENDOR_ADDRESS;
import static seedu.ddd.model.util.SampleDataUtil.SAMPLE_VENDOR_EMAIL;
import static seedu.ddd.model.util.SampleDataUtil.SAMPLE_VENDOR_ID;
import static seedu.ddd.model.util.SampleDataUtil.SAMPLE_VENDOR_NAME;
import static seedu.ddd.model.util.SampleDataUtil.SAMPLE_VENDOR_PHONE;
import static seedu.ddd.model.util.SampleDataUtil.SAMPLE_VENDOR_SERVICE;

import seedu.ddd.model.common.Name;
import seedu.ddd.model.common.Tag;
import seedu.ddd.model.contact.common.Address;
import seedu.ddd.model.contact.common.Email;
import seedu.ddd.model.contact.common.Phone;
import seedu.ddd.model.contact.common.ContactId;
import seedu.ddd.model.contact.vendor.Service;

public class TypicalContactFields {

    // Strings

    // Client fields
    public static final String VALID_CLIENT_NAME = SAMPLE_CLIENT_NAME;
    public static final String VALID_CLIENT_PHONE = SAMPLE_CLIENT_PHONE;
    public static final String VALID_CLIENT_EMAIL = SAMPLE_CLIENT_EMAIL;
    public static final String VALID_CLIENT_ADDRESS = SAMPLE_CLIENT_ADDRESS;
    public static final String VALID_CLIENT_ID = SAMPLE_CLIENT_ID;
    public static final String INVALID_CLIENT_NAME = "!@#$%^";
    public static final String INVALID_CLIENT_PHONE = "abc";
    public static final String INVALID_CLIENT_EMAIL = "abc";
    public static final String INVALID_CLIENT_ADDRESS = "";
    public static final String INVALID_CLIENT_ID = "-1";

    // Vendor fields
    public static final String VALID_VENDOR_NAME = SAMPLE_VENDOR_NAME;
    public static final String VALID_VENDOR_PHONE = SAMPLE_VENDOR_PHONE;
    public static final String VALID_VENDOR_EMAIL = SAMPLE_VENDOR_EMAIL;
    public static final String VALID_VENDOR_ADDRESS = SAMPLE_VENDOR_ADDRESS;
    public static final String VALID_VENDOR_SERVICE_1 = SAMPLE_VENDOR_SERVICE;
    public static final String VALID_VENDOR_SERVICE_2 = "music";
    public static final String VALID_VENDOR_ID = SAMPLE_VENDOR_ID;
    public static final String INVALID_VENDOR_NAME = "!@#$%^";
    public static final String INVALID_VENDOR_PHONE = "abc";
    public static final String INVALID_VENDOR_EMAIL = "abc";
    public static final String INVALID_VENDOR_ADDRESS = "";
    public static final String INVALID_VENDOR_SERVICE = "";
    public static final String INVALID_VENDOR_ID = "-1";

    // Common contact fields
    public static final String VALID_TAG_1 = SAMPLE_TAG_1;
    public static final String VALID_TAG_2 = SAMPLE_TAG_2;
    public static final String VALID_TAG_3 = "family-friendly";
    public static final String INVALID_TAG = "*";

    // Edited contact fields
    public static final String VALID_EDITED_CONTACT_NAME = "John Smith";
    public static final String VALID_EDITED_CONTACT_PHONE = "11111111";
    public static final String VALID_EDITED_CONTACT_ID = "123";

    // Objects

    // Client field objects
    public static final Name DEFAULT_CLIENT_NAME = new Name(VALID_CLIENT_NAME);
    public static final Phone DEFAULT_CLIENT_PHONE = new Phone(VALID_CLIENT_PHONE);
    public static final Email DEFAULT_CLIENT_EMAIL = new Email(VALID_CLIENT_EMAIL);
    public static final Address DEFAULT_CLIENT_ADDRESS = new Address(VALID_CLIENT_ADDRESS);
    public static final ContactId DEFAULT_CLIENT_ID = new ContactId(VALID_CLIENT_ID);
    public static final Set<Tag> DEFAULT_CLIENT_TAGS = Stream.of(VALID_TAG_1)
            .map(Tag::new)
            .collect(Collectors.toSet());
        
    // Vendor field objects
    public static final Name DEFAULT_VENDOR_NAME = new Name(VALID_VENDOR_NAME);
    public static final Phone DEFAULT_VENDOR_PHONE = new Phone(VALID_VENDOR_PHONE);
    public static final Email DEFAULT_VENDOR_EMAIL = new Email(VALID_VENDOR_EMAIL);
    public static final Address DEFAULT_VENDOR_ADDRESS = new Address(VALID_VENDOR_ADDRESS);
    public static final Service DEFAULT_VENDOR_SERVICE = new Service(VALID_VENDOR_SERVICE_1);
    public static final ContactId DEFAULT_VENDOR_ID = new ContactId(VALID_VENDOR_ID);
    public static final Set<Tag> DEFAULT_VENDOR_TAGS = Stream.of(VALID_TAG_1, VALID_TAG_2)
            .map(Tag::new)
            .collect(Collectors.toSet());

}
