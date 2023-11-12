package Dictionary;

import java.util.Iterator;

public class HashedDictionary<K, V> implements DictionaryInterface<K,V>{

    private int numberOfEntries;

    public HashedDictionary(int initialCapacity) {
        initialCapacity = checkCapacity(initialCapacity);

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
        return null;
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
        return null;
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
        return null;
    }

    /**
     * Sees whether a specific entry is in this dictionary.
     *
     * @param key An object search key of the desired entry.
     * @return True if key is associated with an entry in the dictionary.
     */
    @Override
    public boolean contains(K key) {
        return false;
    }

    /**
     * Creates an iterator that traverses all search keys in this dictionary.
     *
     * @return An iterator that provides sequential access to the search
     * keys in the dictionary.
     */
    @Override
    public Iterator<K> getKeyIterator() {
        return null;
    }

    /**
     * Creates an iterator that traverses all values in this dictionary.
     *
     * @return An iterator that provides sequential access to the values
     * in this dictionary.
     */
    @Override
    public Iterator<V> getValueIterator() {
        return null;
    }

    /**
     * Sees whether this dictionary is empty.
     *
     * @return True if the dictionary is empty.
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * Gets the size of this dictionary.
     *
     * @return The number of entries (key-value pairs) currently
     * in the dictionary.
     */
    @Override
    public int getSize() {
        return 0;
    }

    /**
     * Removes all entries from this dictionary.
     */
    @Override
    public void clear() {

    }

    private int getHashIndex(K key) {
        return 0;
    }

    private int probe(int index, K key) {
        return 0;
    }

    private int locate(K key) {
        return 0;
    }

    private void enlargeHashTable() {

    }

    private boolean isHashTableTooFull() {
        return false;
    }

    private int getNextPrime(int anInteger) {
        return 0;
    }

    private boolean isPrime(int anInteger) {
        return false;
    }

    private void checkIntegrity() {

    }

    private int checkCapacity(int capacity) {
        return 0;
    }

    private void checkSize(int size) {

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

    protected final class Entry<K, V> {
        private K key;
        private V value;
        private Entry(K searchKey, V dataValue) {
            key = searchKey;
            value = dataValue;
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
