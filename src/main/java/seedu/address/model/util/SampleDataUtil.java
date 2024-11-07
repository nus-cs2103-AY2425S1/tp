package seedu.address.model.util;

import java.math.BigInteger;

import seedu.address.model.AgentAssist;
import seedu.address.model.ReadOnlyAgentAssist;
import seedu.address.model.client.Address;
import seedu.address.model.client.Client;
import seedu.address.model.client.Email;
import seedu.address.model.client.Income;
import seedu.address.model.client.Job;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.client.Remark;
import seedu.address.model.status.Status;
import seedu.address.model.tier.Tier;

/**
 * Contains utility methods for populating {@code AgentAssist} with sample data.
 */
public class SampleDataUtil {
    public static Client[] getSampleClients() {
        return new Client[] {
            new Client(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Job("Software Engineer"),
                    new Income(BigInteger.valueOf(19)), new Tier("GOLD"), new Remark("Has a lambo"),
                    new Status("NON_URGENT")),
            new Client(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Job("Prime Minister"),
                    new Income(BigInteger.valueOf(20)), new Tier("SILVER"),
                    new Remark("Father is the prime minister"), new Status("URGENT")),
            new Client(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Job("Designer"),
                    new Income(BigInteger.valueOf(1500)), new Tier("BRONZE"), new Remark(""),
                    new Status("NON_URGENT")),
            new Client(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Job("Accountant"),
                    new Income(BigInteger.valueOf(1200)), new Tier("BRONZE"), new Remark(""),
                    new Status("NA")),
            new Client(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Job("Macs Burger Flipper"),
                    new Income(BigInteger.valueOf(20)), new Tier("NA"), new Remark(""),
                    new Status("URGENT")),
            new Client(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Job("PowerPoint Master"),
                    new Income(BigInteger.valueOf(30)), new Tier("REJECT"), new Remark(""),
                    new Status("NA"))
        };
    }

    public static ReadOnlyAgentAssist getSampleAgentAssist() {
        AgentAssist sampleAb = new AgentAssist();
        for (Client sampleClient : getSampleClients()) {
            sampleAb.addClient(sampleClient);
        }
        return sampleAb;
    }

}
