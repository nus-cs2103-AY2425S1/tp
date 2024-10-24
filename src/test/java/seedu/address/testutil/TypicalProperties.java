package seedu.address.testutil;

import seedu.address.model.person.Property;

/**
 * A utility class containing a list of {@code Property} objects to be used in tests.
 */
public class TypicalProperties {

    public static final Property PROPERTY_HDB = new PropertyToBuyBuilder().withHousingType("h")
            .withPostalCode("522522").withUnitNumber("10-01").withPrice("1500000").withActualPrice("1010101").build();
    public static final Property PROPERTY_HDB_WITH_MULTIPLE_TAGS = new PropertyToBuyBuilder().withHousingType("h")
            .withPostalCode("599977").withUnitNumber("17-61").withPrice("5000000").withActualPrice("2000000")
            .withTags("luxury", "private pool").build();
    public static final Property PROPERTY_BTO = new PropertyToBuyBuilder().withHousingType("b")
            .withPostalCode("533255").withUnitNumber("11-21").withPrice("2000000").withActualPrice("1010101").build();
    public static final Property PROPERTY_BTO_WITH_TAG = new PropertyToBuyBuilder().withHousingType("b")
            .withPostalCode("587977").withUnitNumber("16-65").withPrice("6500000").withActualPrice("1010101")
            .withTags("nice scenery").build();
    public static final Property PROPERTY_CONDO_WITH_TAG = new PropertyToBuyBuilder().withHousingType("c")
            .withPostalCode("544488").withUnitNumber("12-33").withPrice("2500000").withTags("spacious")
            .withActualPrice("3000000").build();
    public static final Property PROPERTY_CONDO_WITH_MULTIPLE_TAGS = new PropertyToBuyBuilder().withHousingType("c")
            .withPostalCode("555511").withUnitNumber("13-31").withPrice("3000000").withActualPrice("3200000")
            .withTags("near mrt", "spacious", "5 toilets").build();
    public static final Property PROPERTY_APARTMENT_WITH_MULTIPLE_TAGS = new PropertyToBuyBuilder().withHousingType("a")
            .withPostalCode("566744").withUnitNumber("14-21").withPrice("3500000").withActualPrice("3100000")
            .withTags("5 bedrooms", "corner unit").build();
    public static final Property PROPERTY_APARTMENT_WITH_TAG = new PropertyToBuyBuilder().withHousingType("a")
            .withPostalCode("588977").withUnitNumber("17-60").withPrice("6000000").withActualPrice("7000000")
            .withTags("private lift").build();
    public static final Property PROPERTY_OTHERPROPERTY_WITH_MULTIPLE_TAGS = new PropertyToBuyBuilder()
            .withHousingType("o").withPostalCode("577755").withUnitNumber("15-41").withPrice("4000000")
            .withActualPrice("1010101").withTags("penthouse", "sea view").build();
    public static final Property PROPERTY_OTHERPROPERTY = new PropertyToBuyBuilder().withHousingType("o")
            .withPostalCode("588866").withUnitNumber("16-51").withPrice("4500000").withActualPrice("3000000").build();
}
