package seedu.address.model.policy;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import seedu.address.model.policy.exceptions.DuplicatePolicyTypeException;

/**
 * A class to store a set of {@code Policy}.
 * This class ensures that a single PolicySet cannot have multiple policies with the same {@code PolicyType}.
 * For example, there cannot be two {@code LifePolicy} objects in a PolicySet.
 */
public class PolicySet implements Set<Policy> {
    private static final PolicyType[] policyTypes = PolicyType.getValidPolicyTypes();
    private static final int size = policyTypes.length;
    private final Policy[] policies = new Policy[size];

    /**
     * Constructor for a new {@code PolicySet} with no policies.
     */
    public PolicySet() {}

    /**
     * Constructor for a new {@code PolicySet} initialized with the following policies.
     *
     * @param policies the policies to be added to this {@code PolicySet}.
     * @throws NullPointerException when {@code policies} or a {@code Policy} in policies is null.
     * @throws DuplicatePolicyTypeException when the collection of {@code policies} contains
     *                                      policies of the same {@code PolicyType}.
     */
    public PolicySet(Collection<Policy> policies) {
        requireAllNonNull(policies);
        for (Policy policy : policies) {
            if (!add(policy)) {
                throw new DuplicatePolicyTypeException();
            }
        }
    }

    /**
     * A hash function to map each {@code PolicyType} to an array index in {@code policies}.
     *
     * @param policyType to determine the hash for.
     * @return the index of the {@code policyType} in policies, from 0 to size - 1 inclusive.
     * @throws NullPointerException when {@code policyType} is null.
     */
    private static int hash(PolicyType policyType) {
        requireNonNull(policyType);

        for (int i = 0; i < size; i++) {
            if (policyType == policyTypes[i]) {
                return i;
            }
        }

        assert false : "Unexpected PolicyType called in PolicyMap#hash: " + policyType;
        return -1;
    }

    /**
     * Throws ClassCastException if {@code obj} is not of class {@code PolicyType}.
     *
     * @param obj to be tested.
     * @throws ClassCastException if {@code obj} is not of class {@code PolicyType}.
     */
    private static void requireClassPolicyType(Object obj) {
        if (!(obj instanceof PolicyType)) {
            throw new ClassCastException("Expected a PolicyType instance but received "
                    + (obj == null ? "null" : obj.getClass().getName()) + ".");
        }
    }

    /**
     * Throws ClassCastException if any element in {@code collection} is not of class {@code PolicyType}.
     * This call is equivalent to calling {@code requireClassPolicyType} for each element in {@code collection}.
     *
     * @param collection to be tested.
     * @throws ClassCastException if an element in {@code collection} is not of class {@code PolicyType}.
     */
    private static void requireAllClassPolicyType(Collection<?> collection) {
        collection.stream().forEach(PolicySet::requireClassPolicyType);
    }

    /**
     * Replaces the policy in this set with the same {@code PolicyType} as {@code newPolicy}.
     * only if a policy of the same {@code PolicyType} exists in this set.
     *
     * @param newPolicy the policy to replace the old one.
     * @return the previous policy that had been replaced, or null if there was no policy for the {@code PolicyType}.
     * @throws NullPointerException when {@code newPolicy} is null.
     */
    public Policy replace(Policy newPolicy) {
        requireNonNull(newPolicy);
        int index = hash(newPolicy.getType());
        Policy replaced = policies[index];
        policies[index] = newPolicy;
        return replaced;
    }

    /**
     * Adds the specified {@code policy} to this set if there are no other {@code Policy} with
     * the same associated {@code PolicyType} as indicated by calling {@code policy::getType}.
     * If this set already contains a similar {@code PolicyType}, the call leaves the set unchanged and returns false.
     *
     * @param policy to be added to this set.
     * @return true if this set did not already contain the specified {@code PolicyType} using {@code policy::getType}.
     * @throws NullPointerException when {@code policy} is null.
     */
    @Override
    public boolean add(Policy policy) {
        requireNonNull(policy);

        int index = hash(policy.getType());
        if (policies[index] != null) {
            return false;
        }

        policies[index] = policy;
        return true;
    }

    /**
     * Adds all of the elements in the specified {@code collection} to this set if their associated {@code PolicyType}
     * is not already present in this set.
     * The effect of this call is equivalent to that of calling {@code add} on this set once for each {@code Policy}.
     * Returns true if the set has been changed as a result of the call.
     *
     * @param collection containing elements to be added to this set.
     * @return true if this set has been changed as a result of the call.
     * @throws NullPointerException when {@code collection} or a policy in the collection is null.
     */
    @Override
    public boolean addAll(Collection<? extends Policy> collection) {
        requireAllNonNull(collection);

        boolean isChanged = false;
        for (Policy policy : collection) {
            if (add(policy)) {
                isChanged = true;
            }
        }
        return isChanged;
    }

    @Override
    public void clear() {
        Arrays.fill(policies, null);
    }

    /**
     * Throws an exception if {@code obj} is not an instance of {@code PolicyType}.
     * Returns whether this set contains a {@code Policy} associated with the specified {@code PolicyType}
     * as indicated by typecasting {@code (PolicyType) obj}.
     *
     * @param obj whose presence in this set is to be tested.
     * @return true if this set contains a {@code Policy} associated with the specified {@code PolicyType}
     *         as indicated by typecasting {@code (PolicyType) obj}.
     * @throws NullPointerException when {@code obj} is null.
     * @throws ClassCastException when {@code obj} is not an instance of {@code PolicyType}.
     */
    @Override
    public boolean contains(Object obj) {
        requireNonNull(obj);
        requireClassPolicyType(obj);

        PolicyType policyType = (PolicyType) obj;
        return policies[hash(policyType)] != null;
    }

    /**
     * Throws an exception if {@code collection} contains elements that are not an instance of {@code PolicyType}.
     * Returns true if this set contains a {@code Policy} for each {@code PolicyType} in the {@code collection}.
     * The effect of this call is equivalent to that of calling {@code contains} for each element
     * in {@code collection} once.
     *
     * @param collection to be checked for containment in this set.
     * @return true if this set contains a {@code Policy} for each {@code PolicyType} in the {@code collection}.
     * @throws NullPointerException when {@code collection} or an element in the collection is null.
     * @throws ClassCastException when there exists one or more elements in {@code collection} that is not an instance
     *                            of {@code PolicyType}.
     */
    @Override
    public boolean containsAll(Collection<?> collection) {
        requireAllNonNull(collection);
        requireAllClassPolicyType(collection);

        for (Object obj : collection) {
            if (!contains(obj)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        for (Policy policy : policies) {
            if (policy != null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator<Policy> iterator() {
        List<Policy> policyList = new ArrayList<>();
        for (Policy policy : policies) {
            if (policy != null) {
                policyList.add(policy);
            }
        }
        return policyList.iterator();
    }

    /**
     * Throws an exception if {@code obj} is not an instance of {@code PolicyType}.
     * Removes the {@code Policy} associated with the {@code PolicyType} as indicated
     * by typecasting {@code (PolicyType) obj}.
     * Returns whether this set has been changed as a result of the call.
     *
     * @param obj whose associated {@code Policy} is to be removed.
     * @return true if this set had a {@code Policy} associated with the {@code PolicyType}
     *         as indicated by typecasting {@code (PolicyType) obj}.
     * @throws NullPointerException when {@code obj} is null.
     * @throws ClassCastException when {@code obj} is not an instance of {@code PolicyType}.
     */
    @Override
    public boolean remove(Object obj) {
        requireNonNull(obj);
        requireClassPolicyType(obj);

        PolicyType policyType = (PolicyType) obj;
        int index = hash(policyType);
        if (policies[index] == null) {
            return false;
        }
        policies[index] = null;
        return true;
    }

    /**
     * Throws an exception if {@code collection} contains elements that are not an instance of {@code PolicyType}.
     * Removes from this set every {@code Policy} with respect to each {@code PolicyType} in {@code collection}.
     * Returns true if this set has been changed as a result of the call.
     * The effect of this call is equivalent to that of calling {@code remove} for each element
     * in {@code collection} once.
     *
     * @param collection containing the {@code PolicyTypes} to be removed from this set.
     * @return true if this set has been changed as a result of the call.
     * @throws NullPointerException when {@code collection} or an element in the collection is null.
     * @throws ClassCastException when there exists one or more elements in {@code collection} that is not an instance
     *                            of {@code PolicyType}.
     */
    @Override
    public boolean removeAll(Collection<?> collection) {
        requireAllNonNull(collection);
        requireAllClassPolicyType(collection);

        boolean isChanged = false;
        for (Object obj : collection) {
            if (remove(obj)) {
                isChanged = true;
            }
        }
        return isChanged;
    }

    /**
     * @throws UnsupportedOperationException as PolicySet does not support this method.
     */
    @Override
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException("PolicySet does not support the retainAll method.");
    }

    @Override
    public int size() {
        int count = 0;
        for (Policy policy : policies) {
            if (policy != null) {
                count++;
            }
        }
        return count;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size()];
        int i = 0;
        for (Policy policy : policies) {
            if (policy != null) {
                assert i < array.length : "Index exceeds the array length";
                array[i] = policy;
                i++;
            }
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        int count = size();

        // If the given array is smaller, create a new one of the same type and size.
        if (a.length < count) {
            // Safe cast assuming user has provided a compatible array type.
            @SuppressWarnings("unchecked")
            T[] array = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), count);
            a = array;
        }

        int i = 0;
        for (Policy policy : policies) {
            if (policy != null) {
                // Safe cast assuming user has provided a compatible array type.
                @SuppressWarnings("unchecked")
                T temp = (T) policy;
                a[i] = temp;
                i++;
            }
        }

        // If the given array is larger, set the next element to null (as per contract).
        if (a.length > count) {
            a[count] = null;
        }

        return a;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        int count = 1;
        for (Policy policy : policies) {
            if (policy != null) {
                result.append(count + ". " + policy + "\n");
                count++;
            }
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PolicySet)) {
            return false;
        }

        PolicySet otherPolicySet = (PolicySet) other;
        return Arrays.equals(policies, otherPolicySet.policies);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(policies);
    }
}
