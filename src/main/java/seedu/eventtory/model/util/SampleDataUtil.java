package seedu.eventtory.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.eventtory.model.EventTory;
import seedu.eventtory.model.ReadOnlyEventTory;
import seedu.eventtory.model.commons.name.Name;
import seedu.eventtory.model.commons.tag.Tag;
import seedu.eventtory.model.event.Date;
import seedu.eventtory.model.event.Event;
import seedu.eventtory.model.vendor.Description;
import seedu.eventtory.model.vendor.Phone;
import seedu.eventtory.model.vendor.Vendor;

/**
 * Contains utility methods for populating {@code EventTory} with sample data.
 */
public class SampleDataUtil {
    public static Vendor[] getSampleVendors() {
        return new Vendor[] {
            new Vendor(new Name("Headless Bakers"), new Phone("87438807"),
                new Description("Awesome pastries and cakes!"),
                getTagSet("pastry", "bakeshop")),
            new Vendor(new Name("The Green Grocer"), new Phone("98889900"),
                new Description("Fresh organic produce delivered weekly."),
                getTagSet("groceries", "organic")),
            new Vendor(new Name("Techie Gadget Store"), new Phone("93939393"),
                new Description("Latest tech gadgets and accessories."),
                getTagSet("electronics", "gadgets")),
            new Vendor(new Name("Sunshine Florist"), new Phone("91321234"),
                new Description("Beautiful flowers for every occasion."),
                getTagSet("florist", "gifts")),
            new Vendor(new Name("Urban Threads Boutique"), new Phone("91234567"),
                new Description("Fashion-forward clothing and accessories."),
                getTagSet("fashion", "clothing")),
            new Vendor(new Name("Noodle Master"), new Phone("92223344"),
                new Description("Traditional noodles and Asian street food."),
                getTagSet("food", "noodles")),
            new Vendor(new Name("The Book Nook"), new Phone("98765432"),
                new Description("A cozy bookstore with rare finds."),
                getTagSet("books", "store")),
            new Vendor(new Name("Crispy Corner"), new Phone("92451234"),
                new Description("Delicious crispy snacks and fried food."),
                getTagSet("snacks", "food")),
            new Vendor(new Name("The Sound Wave"), new Phone("95321234"),
                new Description("Audio equipment and accessories for music lovers."),
                getTagSet("electronics", "audio")),
            new Vendor(new Name("Fluff & Stuff Pet Shop"), new Phone("92451298"),
                new Description("Everything for your furry friends."),
                getTagSet("pets", "store")),
            new Vendor(new Name("Glamour Nails"), new Phone("93332211"),
                new Description("Nail care and beauty services."),
                getTagSet("beauty", "nails")),
            new Vendor(new Name("Sushi Express"), new Phone("96112233"),
                new Description("Fresh sushi rolls and Japanese delicacies."),
                getTagSet("food", "sushi")),
            new Vendor(new Name("Artisan Spice Market"), new Phone("97778899"),
                new Description("Exotic spices and seasonings from around the world."),
                getTagSet("spices", "market")),
            new Vendor(new Name("Cycle City"), new Phone("94321123"),
                new Description("Bicycles and accessories for cycling enthusiasts."),
                getTagSet("sports", "bicycles"))
        };
    }

    public static Event[] getSampleEvents() {
        return new Event[] {
            new Event(new Name("Pastry Party 2024"), new Date("2024-11-11"), getTagSet("pastry", "dessert", "friends")),
            new Event(new Name("Farmers' Market Fiesta"), new Date("2024-11-05"), getTagSet("groceries", "organic")),
            new Event(new Name("Tech Expo 2024"), new Date("2024-11-20"), getTagSet("technology", "gadgets", "expo")),
            new Event(new Name("Flower Power Workshop"), new Date("2024-11-15"), getTagSet("florist", "gifts")),
            new Event(new Name("Fashion Showcase 2024"), new Date("2024-12-01"), getTagSet("fashion", "clothing")),
            new Event(new Name("Noodle Night Out"), new Date("2024-11-12"), getTagSet("food", "noodles", "dinner"))
        };
    }

    public static int[][] getSampleAssociations() {
        return new int[][] {
            {0, 0},
            {3, 0},
            {7, 0},
            {12, 1},
            {1, 1},
            {2, 2},
            {3, 2},
            {3, 3},
            {9, 3},
            {0, 4},
            {3, 4},
            {4, 4},
            {10, 4},
            {5, 5},
            {13, 5}
        };
    }

    public static ReadOnlyEventTory getSampleEventTory() {
        EventTory sampleEt = new EventTory();

        Vendor[] sampleVendors = getSampleVendors();
        for (Vendor sampleVendor: sampleVendors) {
            sampleEt.addVendor(sampleVendor);
        }

        Event[] sampleEvents = getSampleEvents();
        for (Event sampleEvent : sampleEvents) {
            sampleEt.addEvent(sampleEvent);
        }

        int[][] associations = getSampleAssociations();
        for (int[] association : associations) {
            sampleEt.assignVendorToEvent(sampleVendors[association[0]], sampleEvents[association[1]]);
        }

        return sampleEt;
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
