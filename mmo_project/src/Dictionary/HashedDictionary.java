package Dictionary;

import java.util.Iterator;

public class HashedDictionary<K, V> implements DictionaryInterface<K,V>{

    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 5;
    private static final int MAX_CAPACITY = 10000;
    private TableEntry<K,V>[] hashTable;
    private int tableSize;
    private static final int MAX_SIZE = 2 * MAX_CAPACITY;
    private boolean integrityOK = false;
    private static final double MAX_LOAD_FACTOR = 0.5;
    private final TableEntry<K,V> AVAILABLE = new TableEntry<>(null, null);

    public HashedDictionary(int initialCapacity) {
        initialCapacity = checkCapacity(initialCapacity);
        numberOfEntries = 0;

        int tableSize = getNextPrime(initialCapacity);
        checkSize(tableSize);

        TableEntry<K,V>[] temp = (TableEntry<K, V>[])new TableEntry[tableSize];
        hashTable = temp;
        integrityOK = true;
    }

    public void displayHashTable() {

    }

    /**
     * Adds a new entry to this dictionary. If the given search key already
     * exists in the dictionary, replaces the corresponding value.
     *
     * @param key   An object search key of the new entry.
     * @param value An object associated with the search key.
     * @return Either null if the new entry was added to the dictionary
     * or the value that was associated with key if that value
     * was replaced.
     */
    @Override
    public V add(K key, V value) {
        checkIntegrity();
        if ((key == null) || (value == null)) {
            throw new IllegalArgumentException("Cannot put null values into a dictionary.");
        } else {
            V oldValue;
            int index = getHashIndex(key);
            assert(index >= 0) && (index < hashTable.length);

            if ((hashTable[index] == null) || (hashTable[index] == AVAILABLE)) {
                hashTable[index] = new TableEntry<>(key, value);
                numberOfEntries++;
                oldValue = null;
            } else {
                oldValue = hashTable[index].getValue();
                hashTable[index].setValue(value);
            }

            if (isHashTableTooFull()) {
                enlargeHashTable();
            }

            return oldValue;
        }
    }

    /**
     * Removes a specific entry from this dictionary.
     *
     * @param key An object search key of the entry to be removed.
     * @return Either the value that was associated with the search key
     * or null if no such object exists.
     */
    @Override
    public V remove(K key) {
        checkIntegrity();
        V removedValue = null;
        int index = getHashIndex(key);

        if ((hashTable[index] != null) && (hashTable[index] != AVAILABLE)) {
            removedValue = hashTable[index].getValue();
            hashTable[index] = AVAILABLE;
            numberOfEntries--;
        }

        return removedValue;
    }

    /**
     * Retrieves from this dictionary the value associated with a given
     * search key.
     *
     * @param key An object search key of the entry to be retrieved.
     * @return Either the value that is associated with the search key
     * or null if no such object exists.
     */
    @Override
    public V getValue(K key) {
        checkIntegrity();
        V result = null;

        int index = getHashIndex(key);

        if ((hashTable[index] != null) && hashTable[index] != AVAILABLE) {
            result = hashTable[index].getValue();
        }
        return result;
    }

    /**
     * Sees whether a specific entry is in this dictionary.
     *
     * @param key An object search key of the desired entry.
     * @return True if key is associated with an entry in the dictionary.
     */
    @Override
    public boolean contains(K key) {
        return getValue(key) != null;
    }

    /**
     * Creates an iterator that traverses all search keys in this dictionary.
     *
     * @return An iterator that provides sequential access to the search
     * keys in the dictionary.
     */
    @Override
    public Iterator<K> getKeyIterator() {
        return new KeyIterator();
    }

    /**
     * Creates an iterator that traverses all values in this dictionary.
     *
     * @return An iterator that provides sequential access to the values
     * in this dictionary.
     */
    @Override
    public Iterator<V> getValueIterator() {
        return new ValueIterator();
    }

    /**
     * Sees whether this dictionary is empty.
     *
     * @return True if the dictionary is empty.
     */
    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    /**
     * Gets the size of this dictionary.
     *
     * @return The number of entries (key-value pairs) currently
     * in the dictionary.
     */
    @Override
    public int getSize() {
        return numberOfEntries;
    }

    /**
     * Removes all entries from this dictionary.
     */
    @Override
    public void clear() {
        checkIntegrity();
        for (int index = 0; index < hashTable.length; index++) {
            hashTable[index] = null;
        }
        numberOfEntries = 0;
    }

    /**
     * Return the index that the given key will be stored at in the has table
     *
     * @param key key to be stored in hash table
     * @return the index that key will be stored at
     */
    private int getHashIndex(K key) {
        int hashIndex = key.hashCode() % hashTable.length;

        if (hashIndex < 0) {
            hashIndex = hashIndex + hashTable.length;
        }

        hashIndex = probe(hashIndex, key);
        return hashIndex;
    }

    /**
     * Uses quadratic probing to either find where the key value is stored, or to find the index where it will be
     * stored
     *
     * @param index index to starting probing from
     * @param key key to insert into hash table
     * @return index for key to be stored at
     */
    private int probe(int index, K key) {
        boolean found = false;
        int availableIndex = -1;
        int increment = 1;

        while (!found && (hashTable[index] != null)) {
            if ((hashTable[index] != null) && (hashTable[index] != AVAILABLE)) {
                if (key.equals(hashTable[index].getKey())) {
                    found = true;
                } else {
                    index = (index + increment * increment) % hashTable.length;
                    increment++;
                }
            }
            else {
                if (availableIndex == -1) {
                    availableIndex = index;
                }
                index = (index + increment * increment) % hashTable.length;
                increment++;
            }
        }

        if (found || availableIndex == -1) {
            return index;
        } else {
            return availableIndex;
        }
    }

    /**
     * Creates new hash table with a larger length and re-hashes all the key value pairs
     */
    private void enlargeHashTable() {
        TableEntry<K,V>[] oldTable = hashTable;
        int oldSize = hashTable.length;
        int newSize = getNextPrime(oldSize + oldSize);
        checkSize(newSize);

        TableEntry<K,V>[] tempTable = (TableEntry<K,V>[])new TableEntry[newSize];
        hashTable = tempTable;
        numberOfEntries = 0;

        for (int index = 0; index < oldSize; index++) {
            if ((oldTable[index] != null) && (oldTable[index] != AVAILABLE)) {
                add(oldTable[index].getKey(), oldTable[index].getValue());
            }
        }
    }

    /**
     * Returns true if hash table size is grater than the load factor
     * @return true if size is too large
     */
    private boolean isHashTableTooFull() {
        return numberOfEntries > MAX_LOAD_FACTOR * hashTable.length;
    }

    /**
     * Return a prime number greater than given number and less than max size
     *
     * @return a valid prime number
     */
    private int getNextPrime(int number) {
        if (number % 2 == 0) {
            number++;
        }

        while (!isPrime(number)) {
            number = number + 2;
        }
        return number;
    }

    /**
     * Tests if the given number is prime
     *
     * @param number number to be tested
     * @return true/false if number is prime
     */
    private boolean isPrime(int number) {
        boolean result;
        boolean done = false;

        if ((number == 1) || (number % 2 == 0)) {
            result = false;
        } else if ((number == 2) || (number == 3)) {
            result = true;
        } else {
            assert (number % 2 != 0) && (number >= 5);
            result = true;
            for (int divisor = 3; !done && (divisor * divisor <= number); divisor = divisor + 2) {
                if (number % divisor == 0) {
                    result = false;
                    done = true;
                }
            }
        }
        return result;
    }

    /**
     * Throw an exception if the integrity is not ok
     */
    private void checkIntegrity() {
        if (!integrityOK) {
            throw new SecurityException("HashedDictionary object is corrupt.");
        }
    }

    /**
     * Checks that the capacity is the correct size, and not larger that maximum
     *
     * @param capacity requested size of hash table
     * @return the set size of hash table
     */
    private int checkCapacity(int capacity) {
        if (capacity < DEFAULT_CAPACITY) {
            capacity = DEFAULT_CAPACITY;
        } else if (capacity > MAX_CAPACITY) {
            throw new IllegalStateException("You can not create a dictionary with a capacity larger than" +
                    MAX_CAPACITY);
        }
        return capacity;
    }

    /**
     * Throw exception if hash table is too large
     *
     * @param size size of hash table to be checked
     */
    private void checkSize(int size) {
        if (size > MAX_SIZE) {
            throw new IllegalStateException("Dictionary has become too large.");
        }
    }

    private class KeyIterator implements Iterator<K> {
        private KeyIterator() {

        }
        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return false;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public K next() {
            return null;
        }

        public void remove() {

        }
    }

    private class ValueIterator implements Iterator<V> {
        private ValueIterator() {

        }
        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return false;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public V next() {
            return null;
        }

        public void remove() {

        }
    }

    protected final class TableEntry<K, V> {
        private K key;
        private V value;
        private TableEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        private K getKey() {
            return key;
        }

        private V getValue() {
            return value;
        }

        private void setValue(V newValue) {
            value = newValue;
        }
    }

}
