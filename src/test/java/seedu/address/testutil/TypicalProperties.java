package seedu.address.testutil;

import seedu.address.model.person.Property;

/**
 * A utility class containing a list of {@code Property} objects to be used in tests.
 */
public class TypicalProperties {

    public static final Property PROPERTY_A = new PropertyBuilder().withHousingType("h").withPostalCode("522522")
            .withUnitNumber("10-01").withPrice("1500000").build();
    public static final Property PROPERTY_B = new PropertyBuilder().withHousingType("b").withPostalCode("533255")
            .withUnitNumber("11-21").withPrice("2000000").build();
    public static final Property PROPERTY_C = new PropertyBuilder().withHousingType("c").withPostalCode("544488")
            .withUnitNumber("12-33").withPrice("2500000").withTags("spacious").build();
    public static final Property PROPERTY_D = new PropertyBuilder().withHousingType("c").withPostalCode("555511")
            .withUnitNumber("13-31").withPrice("3000000").withTags("near mrt", "spacious", "5 toilets").build();
    public static final Property PROPERTY_E = new PropertyBuilder().withHousingType("a").withPostalCode("566744")
            .withUnitNumber("14-21").withPrice("3500000").withTags("5 bedrooms", "corner unit").build();
    public static final Property PROPERTY_F = new PropertyBuilder().withHousingType("o").withPostalCode("577755")
            .withUnitNumber("15-41").withPrice("4000000").withTags("penthouse", "sea view").build();
    public static final Property PROPERTY_G = new PropertyBuilder().withHousingType("o").withPostalCode("588866")
            .withUnitNumber("16-51").withPrice("4500000").withTags("duplex", "garden").build();
    public static final Property PROPERTY_H = new PropertyBuilder().withHousingType("h").withPostalCode("599977")
            .withUnitNumber("17-61").withPrice("5000000").withTags("luxury", "private pool").build();
}
