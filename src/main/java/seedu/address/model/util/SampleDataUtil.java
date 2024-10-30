package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.PawPatrol;
import seedu.address.model.ReadOnlyPawPatrol;
import seedu.address.model.owner.Address;
import seedu.address.model.owner.Email;
import seedu.address.model.owner.IdentificationCardNumber;
import seedu.address.model.owner.Name;
import seedu.address.model.owner.Owner;
import seedu.address.model.owner.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code PawPatrol} with sample data.
 */
public class SampleDataUtil {
    public static Owner[] getSampleOwners() {
        return new Owner[] {
            new Owner(new IdentificationCardNumber("S0000001I"), new Name("Alex Yeoh"), new Phone("87438807"),
                new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40")),
            new Owner(new IdentificationCardNumber("S0000002G"), new Name("Bernice Yu"), new Phone("99272758"),
                new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18")),
            new Owner(new IdentificationCardNumber("S0000003E"), new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04")),
            new Owner(new IdentificationCardNumber("S0000004C"), new Name("David Li"), new Phone("91031282"),
                new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43")),
            new Owner(new IdentificationCardNumber("S0000005A"), new Name("Irfan Ibrahim"), new Phone("92492021"),
                new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35")),
            new Owner(new IdentificationCardNumber("S0000006Z"), new Name("Roy Balakrishnan"), new Phone("92624417"),
                new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"))
        };
    }

    public static ReadOnlyPawPatrol getSamplePawPatrol() {
        PawPatrol sampleAb = new PawPatrol();
        for (Owner sampleOwner : getSampleOwners()) {
            sampleAb.addOwner(sampleOwner);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
