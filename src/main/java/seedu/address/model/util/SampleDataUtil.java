package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.Prudy;
import seedu.address.model.ReadOnlyPrudy;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.ClaimList;
import seedu.address.model.claim.ClaimStatus;
import seedu.address.model.client.Address;
import seedu.address.model.client.Client;
import seedu.address.model.client.Email;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.policy.CoverageAmount;
import seedu.address.model.policy.EducationPolicy;
import seedu.address.model.policy.ExpiryDate;
import seedu.address.model.policy.HealthPolicy;
import seedu.address.model.policy.LifePolicy;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicySet;
import seedu.address.model.policy.PremiumAmount;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Prudy} with sample data.
 */
public class SampleDataUtil {

    public static Client[] getSampleClients() {
        ClaimList healthClaim = new ClaimList();
        healthClaim.add(new Claim(ClaimStatus.PENDING, "surgery"));
        return new Client[] {
            new Client(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"), getPolicySet(new LifePolicy(), new HealthPolicy(), new EducationPolicy())),
            new Client(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends"), getPolicySet(new HealthPolicy(new PremiumAmount(100),
                    new CoverageAmount(10000), new ExpiryDate("12/12/2024"), healthClaim))),
            new Client(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"), getPolicySet()),
            new Client(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family"), getPolicySet()),
            new Client(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates"), getPolicySet()),
            new Client(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"), getPolicySet())
        };
    }

    public static ReadOnlyPrudy getSamplePrudy() {
        Prudy samplePrudy = new Prudy();
        for (Client sampleClient : getSampleClients()) {
            samplePrudy.addClient(sampleClient);
        }
        return samplePrudy;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a PolicySet made up of the list of policies.
     */
    public static PolicySet getPolicySet(Policy... policies) {
        if (policies == null) {
            return new PolicySet();
        }

        PolicySet policySet = new PolicySet(Arrays.asList(policies));
        return policySet;
    }

}
