package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;



public class PersonPredicateBuilderTest {

    @Test
    public void equalsMethod() {
        PersonPredicateBuilder builder1 = new PersonPredicateBuilder()
                .withNameKeywords(Arrays.asList("Alice", "Bob"))
                .withClassIdKeywords(Arrays.asList("1", "2"))
                .withMonthPaidKeywords(List.of("2022-12"))
                .withNotMonthPaidKeywords(List.of("2022-11"))
                .withTagsKeywords(List.of("tag1", "tag2"))
                .withEmailKeywords(Arrays.asList("alice@example.com", "bob@example.com"))
                .withPhoneKeywords(Arrays.asList("12345678", "87654321"))
                .withFeesKeywords(List.of("100", "200"));

        PersonPredicateBuilder builder2 = new PersonPredicateBuilder()
                .withNameKeywords(Arrays.asList("Alice", "Bob"))
                .withClassIdKeywords(Arrays.asList("1", "2"))
                .withMonthPaidKeywords(List.of("2022-12"))
                .withNotMonthPaidKeywords(List.of("2022-11"))
                .withTagsKeywords(List.of("tag1", "tag2"))
                .withEmailKeywords(Arrays.asList("alice@example.com", "bob@example.com"))
                .withPhoneKeywords(Arrays.asList("12345678", "87654321"))
                .withFeesKeywords(List.of("100", "200"));

        PersonPredicateBuilder builder3 = new PersonPredicateBuilder()
                .withNameKeywords(Arrays.asList("Charlie", "David"));

        assertEquals(builder1, builder2);
        assertNotEquals(builder1, builder3);
    }

    @Test
    public void test_build() {
        PersonPredicateBuilder builder = new PersonPredicateBuilder();

        // Test isSetName
        builder.withNameKeywords(Collections.singletonList("Alice"));
        assertTrue(builder.build().test(new PersonBuilder().withName("Alice").build()));


        // Test isSetClassId
        builder.withClassIdKeywords(Collections.singletonList("1"));
        assertTrue(builder.build().test(new PersonBuilder().withClassId("1").build()));


        // Test isSetMonthPaid
        builder.withMonthPaidKeywords(Collections.singletonList("2022-12"));
        assertTrue(builder.build().test(new PersonBuilder().withMonthsPaid("2022-12").build()));


        // Test isSetTag
        builder.withTagsKeywords(Collections.singletonList("tag1"));
        assertTrue(builder.build().test(new PersonBuilder().withTags("tag1").build()));


    }

    @Test
    public void toStringMethod() {
        PersonPredicateBuilder builder = new PersonPredicateBuilder()
                .withNameKeywords(Arrays.asList("Alice", "Bob"))
                .withClassIdKeywords(Arrays.asList("1", "2"))
                .withMonthPaidKeywords(List.of("2022-12"))
                .withNotMonthPaidKeywords(List.of("2022-11"))
                .withTagsKeywords(List.of("tag1", "tag2"))
                .withEmailKeywords(Arrays.asList("alice@example.com", "bob@example.com"))
                .withPhoneKeywords(Arrays.asList("12345678", "87654321"))
                .withFeesKeywords(List.of("100", "200"));

        StringBuilder expectedString = new StringBuilder();
        expectedString.append("seedu.address.model.person.PersonPredicateBuilder{")
                .append("nameKeywords=[Alice, Bob], ")
                .append("classIdKeywords=[1, 2], ")
                .append("monthPaidKeywords=[2022-12], ")
                .append("notMonthPaidKeywords=[2022-11], ")
                .append("tagKeywords=[tag1, tag2], ")
                .append("addressKeywords=[], ")
                .append("phoneKeywords=[12345678, 87654321], ")
                .append("emailKeywords=[alice@example.com, bob@example.com], ")
                .append("feesKeywords=[100, 200], ")
                .append("isSetName=true, ")
                .append("isSetClassId=true, ")
                .append("isSetMonthPaid=true, ")
                .append("isSetNotMonthPaid=true, ")
                .append("isSetTag=true, ")
                .append("isSetAddress=false, ")
                .append("isSetPhone=true, ")
                .append("isSetEmail=true, ")
                .append("isSetFees=true}");

        assertEquals(expectedString.toString(), builder.toString());
    }
}
