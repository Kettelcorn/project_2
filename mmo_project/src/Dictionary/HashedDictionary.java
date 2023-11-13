package Dictionary;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A hash table-based implementation of the DictionaryInterface.
 *
 * @param <K> The type of keys in the dictionary.
 * @param <V> The type of values in the dictionary.
 */
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

    /**
     * Constructs a new HashedDictionary with the specified initial capacity.
     *
     * @param initialCapacity The initial capacity of the hash table.
     */
    public HashedDictionary(int initialCapacity) {
        initialCapacity = checkCapacity(initialCapacity);
        numberOfEntries = 0;

        int tableSize = getNextPrime(initialCapacity);
        checkSize(tableSize);

        TableEntry<K,V>[] temp = (TableEntry<K, V>[])new TableEntry[tableSize];
        hashTable = temp;
        integrityOK = true;
    }

    /**
     * Displays the hash table, printing either null, available, or the key value pair
     */
    public void displayHashTable() {
        checkIntegrity();
        System.out.println("Hashed Dictionary: ");
        // Runtime: O(N)
        // Loops through each element in hash table and prints it out
        for (int index = 0; index < hashTable.length; index++) {
            System.out.print(index + ": ");
            if (hashTable[index] == null) {
                System.out.print("null");
            } else if (hashTable[index] == AVAILABLE) {
                System.out.print("available");
            } else {
                System.out.print(hashTable[index].getKey() + " " + hashTable[index].getValue());
            }
            System.out.println();
        }
        System.out.println();
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
            //Runtime: O(n), due to getHashIndex being called
            System.out.println("Hash Index for " + key + ": " + key.hashCode() % hashTable.length);
            V oldValue;
            //Finds next available index and adds entry or updates new value
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
            //Runtime: O(n) because you need to rehash each element
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
        //Runtime: O(n), due to calling getHashIndex();
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

        //Runtime: O(n) due to calling getHashIndex
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
        // Runtime: O(n) due to calling get value
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
        // Runtime: O(n) because is iterates through each element in the hash table
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
        //Runtime: O(n) because worst case, quadratic probing could go through whole hash table
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
        //Runtime: O(n), because worst case it could search through whole hash table
        boolean found = false;
        int availableIndex = -1;
        int increment = 1;

        // continue to quadratic probe until finds an open or same index
        while (!found && (hashTable[index] != null)) {
            if ((hashTable[index] != null) && (hashTable[index] != AVAILABLE)) {
                // if found, mark as found and leave loop
                if (key.equals(hashTable[index].getKey())) {
                    found = true;
                } else {
                    index = (index + (increment * increment)) % hashTable.length;
                    increment++;
                }
            }
            // if slot is null or available, you found the right slot
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
     * Not clear how to implement
     */
    private void locate() {
        throw new UnsupportedOperationException("No support for method");
    }

    /**
     * Creates new hash table with a larger length and re-hashes all the key value pairs
     */
    private void enlargeHashTable() {
        //Runtime: O(n), where n is the new size of the hash table
        // doubles the size of the hash table
        TableEntry<K,V>[] oldTable = hashTable;
        int oldSize = hashTable.length;
        int newSize = getNextPrime(oldSize + oldSize);
        checkSize(newSize);

        TableEntry<K,V>[] tempTable = (TableEntry<K,V>[])new TableEntry[newSize];
        hashTable = tempTable;
        numberOfEntries = 0;

        // re hash all of the elements in the original hash table to the new one
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
        //Runtime: O(sqrt(n)) because this is the time complexity for finding the next prime number
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

        // logic for checking if number is prime, returning the boolean if it is or not
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
        private  int currentIndex;
        private int numberLeft;
        private KeyIterator() {
            currentIndex = 0;
            numberLeft = numberOfEntries;
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
            return numberLeft > 0;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public K next() {
            K result = null;

            if (hasNext()) {
                while ((hashTable[currentIndex] == null) || hashTable[currentIndex] == AVAILABLE) {
                    currentIndex++;
                }
                result = hashTable[currentIndex].getKey();
                numberLeft--;
                currentIndex++;
            }
            else {
                throw new NoSuchElementException();
            }
            return result;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class ValueIterator implements Iterator<V> {
        private int currentIndex;
        private int numberLeft;
        private ValueIterator() {
            currentIndex = 0;
            numberLeft = numberOfEntries;
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
            return numberLeft > 0;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public V next() {
            V result = null;

            if (hasNext()) {
                while ((hashTable[currentIndex] == null) || hashTable[currentIndex] == AVAILABLE) {
                    currentIndex++;
                }
                result = hashTable[currentIndex].getValue();
                numberLeft--;
                currentIndex++;
            } else {
                throw new NoSuchElementException();
            }
            return result;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    protected final class TableEntry<K, V> {
        private K key;
        private V value;
        private TableEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Returns key for table entry
         *
         * @return key
         */
        private K getKey() {
            return key;
        }

        /**
         * Returns value for table entry
         *
         * @return value
         */
        private V getValue() {
            return value;
        }

        /**
         *  Sets the value for the given table entry
         *
         * @param newValue value to be set
         */
        private void setValue(V newValue) {
            value = newValue;
        }
    }

}
