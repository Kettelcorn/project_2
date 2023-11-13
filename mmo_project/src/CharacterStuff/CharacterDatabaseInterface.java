package CharacterStuff;

import Dictionary.HashedDictionary;
/**
 * The CharacterDatabaseInterface defines the interface for managing characters
 * MMOG database.
 */
public interface CharacterDatabaseInterface {
    /**
     * Adds a new character to the database.
     *
     * @param name       The name of the character.
     * @param height     The height of the character.
     * @param weight     The weight of the character.
     * @param moralAlign The moral alignment of the character.
     */
    public void addCharacter(String name, int height, int weight, double moralAlign);

    /**
     * Removes a character from the database.
     *
     * @param name The name of the character to be removed.
     */
    public void removeCharacter(String name);

    /**
     * Retrieves a character from the database based on the name.
     *
     * @param name The name of the character.
     * @return The character object with the given name.
     */
    public Character getCharacter(String name);

    /**
     * Gets the hashed dictionary storing character indexes in the database.
     *
     * @return The hashed dictionary of character indexes.
     */
    public HashedDictionary<String, Integer> getHashTable();

    /**
     * Prints the list of characters in the database.
     */
    public void printList();
}
