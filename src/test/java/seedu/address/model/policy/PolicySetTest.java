package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class PolicySetTest {
    final LifePolicy life = new LifePolicy();
    final HealthPolicy health = new HealthPolicy();
    final EducationPolicy education = new EducationPolicy();

    @Test
    public void addMethod() {
        final PolicySet policies = new PolicySet();

        assertTrue(policies.add(life));
        assertTrue(policies.add(health));
        assertTrue(policies.add(education));

        assertFalse(policies.add(life));
        assertFalse(policies.add(health));
        assertFalse(policies.add(education));

        assertThrows(NullPointerException.class, () -> policies.add(null));
    }

    @Test
    public void addAll_goodInputs_success() {
        final PolicySet policies = new PolicySet();
        policies.add(life);

        // collection of duplicates -> return false
        final Set<Policy> duplicatePolicies = new HashSet<>();
        duplicatePolicies.add(life);
        assertFalse(policies.addAll(duplicatePolicies));

        // some duplicate and new policies -> return true;
        final Set<Policy> newPolicies = new HashSet<>();
        newPolicies.add(life);
        newPolicies.add(health);
        assertTrue(policies.addAll(newPolicies));

        // collection of EducationPolicy -> no errors
        final Set<EducationPolicy> educationPolicies = new HashSet<>();
        educationPolicies.add(education);
        assertTrue(policies.addAll(educationPolicies));
    }

    @Test
    public void addAll_nullInputs_throwsNullPointerException() {
        final PolicySet policies = new PolicySet();

        // null collection
        assertThrows(NullPointerException.class, () -> policies.addAll(null));

        // collection with null elements
        final Set<Policy> someNullPolicies = new HashSet<>();
        someNullPolicies.add(life);
        someNullPolicies.add(health);
        someNullPolicies.add(null);
        assertThrows(NullPointerException.class, () -> policies.addAll(someNullPolicies));
    }

    @Test
    public void containsMethod() {
        final PolicySet policies = new PolicySet();

        assertThrows(NullPointerException.class, () -> policies.contains(null));
        assertThrows(ClassCastException.class, () -> policies.contains(new LifePolicy()));

        policies.add(education);
        assertTrue(policies.contains(education.getType()));
        assertFalse(policies.contains(life.getType()));
    }

    @Test
    public void containsAll_goodInputs_success() {
        final PolicySet policies = new PolicySet();
        policies.add(life);
        policies.add(education);

        // collection of all duplicates -> return true
        final Set<PolicyType> duplicatePolicies = new HashSet<>();
        duplicatePolicies.add(life.getType());
        duplicatePolicies.add(education.getType());
        assertTrue(policies.containsAll(duplicatePolicies));

        // some duplicate and new policies -> return false;
        final Set<PolicyType> newPolicies = new HashSet<>();
        newPolicies.add(life.getType());
        newPolicies.add(health.getType());
        assertFalse(policies.containsAll(newPolicies));
    }

    @Test
    public void containsAll_nullInputs_throwsNullPointerException() {
        final PolicySet policies = new PolicySet();

        // null collection
        assertThrows(NullPointerException.class, () -> policies.containsAll(null));

        // collection with null elements
        final Set<PolicyType> someNullPolicies = new HashSet<>();
        someNullPolicies.add(life.getType());
        someNullPolicies.add(health.getType());
        someNullPolicies.add(null);
        assertThrows(NullPointerException.class, () -> policies.containsAll(someNullPolicies));
    }

    @Test
    public void containsAll_wrongClass_throwsClassCastException() {
        final PolicySet policies = new PolicySet();

        final Set<Policy> wrongClassCollection = new HashSet<>();
        wrongClassCollection.add(life);
        assertThrows(ClassCastException.class, () -> policies.containsAll(wrongClassCollection));
    }

    @Test
    public void isEmptyMethod() {
        final PolicySet policies = new PolicySet();

        assertTrue(policies.isEmpty());
        policies.add(health);
        assertFalse(policies.isEmpty());
    }

    @Test
    public void iteratorMethod() {
        final PolicySet policies = new PolicySet();

        assertFalse(policies.iterator().hasNext());

        policies.add(life);
        policies.add(health);
        Iterator<Policy> iterator = policies.iterator();
        assertEquals(life, iterator.next());
        assertEquals(health, iterator.next());
    }

    @Test
    public void removeMethod() {
        final PolicySet policies = new PolicySet();
        policies.add(life);
        policies.add(health);
        policies.add(education);

        assertTrue(policies.remove(life.getType()));
        assertTrue(policies.remove(health.getType()));
        assertTrue(policies.remove(education.getType()));

        assertFalse(policies.remove(life.getType()));
        assertFalse(policies.remove(health.getType()));
        assertFalse(policies.remove(education.getType()));

        assertThrows(NullPointerException.class, () -> policies.remove(null));
    }
    @Test
    public void removeAll_goodInputs_success() {
        final PolicySet policies = new PolicySet();
        policies.add(life);
        policies.add(education);

        // collection of all duplicates -> return true
        final Set<PolicyType> duplicatePolicies = new HashSet<>();
        duplicatePolicies.add(life.getType());
        duplicatePolicies.add(education.getType());
        assertTrue(policies.removeAll(duplicatePolicies));

        // some duplicate and new policies -> return true;
        policies.add(health);
        final Set<PolicyType> newPolicies = new HashSet<>();
        newPolicies.add(life.getType());
        newPolicies.add(health.getType());
        assertTrue(policies.removeAll(newPolicies));

        // collection of no duplicates -> return false
        policies.add(education);
        final Set<PolicyType> noDuplicatePolicies = new HashSet<>();
        noDuplicatePolicies.add(life.getType());
        assertFalse(policies.removeAll(noDuplicatePolicies));
    }


    @Test
    public void removeAll_nullInputs_throwsNullPointerException() {
        final PolicySet policies = new PolicySet();

        // null collection
        assertThrows(NullPointerException.class, () -> policies.removeAll(null));

        // collection with null elements
        final Set<PolicyType> someNullPolicies = new HashSet<>();
        someNullPolicies.add(life.getType());
        someNullPolicies.add(health.getType());
        someNullPolicies.add(null);
        assertThrows(NullPointerException.class, () -> policies.removeAll(someNullPolicies));
    }

    @Test
    public void removeAll_wrongClass_throwsNullPointerException() {
        final PolicySet policies = new PolicySet();

        final Set<Policy> wrongClassCollection = new HashSet<>();
        wrongClassCollection.add(life);
        assertThrows(ClassCastException.class, () -> policies.containsAll(wrongClassCollection));
    }

    @Test
    public void retainAll_throwsUnsupportedOperationException() {
        final PolicySet policies = new PolicySet();
        assertThrows(UnsupportedOperationException.class, () -> policies.retainAll(null));
    }

    @Test
    public void sizeMethod() {
        final PolicySet policies = new PolicySet();

        assertEquals(0, policies.size());
        policies.add(life);
        assertEquals(1, policies.size());
        policies.add(health);
        assertEquals(2, policies.size());
        policies.add(education);
        assertEquals(3, policies.size());
    }

    @Test
    public void toArray_returnObjectArray() {
        final PolicySet policies = new PolicySet();

        Object[] array = new Object[0];
        assertTrue(Arrays.equals(array, policies.toArray()));

        array = new Object[1];
        array[0] = life;
        policies.add(life);
        assertTrue(Arrays.equals(array, policies.toArray()));

        array = new Object[2];
        array[0] = life;
        array[1] = health;
        policies.add(health);
        assertTrue(Arrays.equals(array, policies.toArray()));

        array = new Object[3];
        array[0] = life;
        array[1] = health;
        array[2] = education;
        policies.add(education);
        assertTrue(Arrays.equals(array, policies.toArray()));
    }

    @Test
    public void toArray_returnGenericArray() {
        final PolicySet policies = new PolicySet();

        // checks if method returns an empty array if there are no elements
        Object[] expected = new Object[0];
        assertTrue(Arrays.equals(expected, policies.toArray(new Object[0])));

        // checks if method set the next element to null
        expected = new Object[3];
        expected[0] = life;
        expected[1] = null;
        policies.add(life);
        assertTrue(Arrays.equals(expected, policies.toArray(new Object[3])));

        // checks if method creates a new array if provide array is too small
        expected = new Object[2];
        expected[0] = life;
        expected[1] = health;
        policies.add(health);
        assertTrue(Arrays.equals(expected, policies.toArray(new Object[1])));

        // checks if method returns an array of specified generic type
        Policy[] policyArray = new Policy[2];
        policyArray[0] = life;
        policyArray[1] = health;
        assertTrue(Arrays.equals(policyArray, policies.toArray(new Policy[2])));

        // checks if method throws an exception if Policies cannot be type casted into provided array
        EducationPolicy[] educationArray = new EducationPolicy[3];
        assertThrows(ArrayStoreException.class, () -> policies.toArray(educationArray));
    }

    @Test
    public void equalsMethod() {
        final PolicySet policies = new PolicySet();
        policies.add(life);
        policies.add(health);
        policies.add(education);

        // same object -> returns true
        assertTrue(policies.equals(policies));

        // null -> returns false
        assertFalse(policies.equals(null));

        // same values -> returns true
        final PolicySet samePolicies = new PolicySet();
        samePolicies.add(education);
        samePolicies.add(health);
        samePolicies.add(life);
        assertTrue(policies.equals(samePolicies));

        // different values -> returns false
        final PolicySet differentPolicies = new PolicySet();
        differentPolicies.add(life);
        differentPolicies.add(education);
        assertFalse(policies.equals(differentPolicies));
    }
}
