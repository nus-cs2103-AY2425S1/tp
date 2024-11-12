package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ToStringBuilderTest {
    private static class TestNestedClass {}

    @Test
    void toStringBuilder_stringConstructor_returnsStringWithObjectName() {
        String testString = "test";
        ToStringBuilder builder = new ToStringBuilder(testString);
        assertEquals("test{}", builder.toString());
    }

    @Test
    void toStringBuilder_objectConstructor_returnsCanonicalClassName() {
        Object testObject = new Object();
        ToStringBuilder builder = new ToStringBuilder(testObject);
        assertEquals("java.lang.Object{}", builder.toString());
    }

    @Test
    void add_singleFieldAndValue_returnsCorrectlyFormattedString() {
        ToStringBuilder builder = new ToStringBuilder("TestObject");
        builder.add("field1", "value1");
        assertEquals("TestObject{field1=value1}", builder.toString());
    }

    @Test
    void add_multipleFieldsAndValues_returnsCorrectlyFormattedString() {
        ToStringBuilder builder = new ToStringBuilder("TestObject");
        builder.add("field1", "value1")
                .add("field2", 42)
                .add("field3", true);
        assertEquals("TestObject{field1=value1, field2=42, field3=true}", builder.toString());
    }

    @Test
    void add_nullFieldValue_returnsStringWithNullValue() {
        ToStringBuilder builder = new ToStringBuilder("TestObject");
        builder.add("nullField", null);
        assertEquals("TestObject{nullField=null}", builder.toString());
    }

    @Test
    void add_emptyFieldName_returnsStringWithEmptyFieldName() {
        ToStringBuilder builder = new ToStringBuilder("TestObject");
        builder.add("", "value");
        assertEquals("TestObject{=value}", builder.toString());
    }

    @Test
    void add_objectWithCustomToString_returnsStringWithCustomToString() {
        class CustomObject {
            @Override
            public String toString() {
                return "CustomString";
            }
        }

        ToStringBuilder builder = new ToStringBuilder("TestObject");
        builder.add("field", new CustomObject());
        assertEquals("TestObject{field=CustomString}", builder.toString());
    }

    @Test
    void toString_noFieldsAdded_returnsOnlyClassNameAndBraces() {
        ToStringBuilder builder = new ToStringBuilder("TestObject");
        assertEquals("TestObject{}", builder.toString());
    }

    @Test
    void add_chainedMethodCalls_returnsFieldsInCorrectOrder() {
        String result = new ToStringBuilder("TestObject")
                .add("first", 1)
                .add("second", 2)
                .add("third", 3)
                .toString();
        assertEquals("TestObject{first=1, second=2, third=3}", result);
    }

    @Test
    void toStringBuilder_nestedClassConstructor_returnsFullPackagePath() {
        ToStringBuilder builder = new ToStringBuilder(new TestNestedClass());
        String expectedPrefix = "seedu.address.commons.util.ToStringBuilderTest.TestNestedClass";
        assertEquals(expectedPrefix + "{}", builder.toString());
    }
}
