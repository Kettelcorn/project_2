package CharacterStuff;

import Dictionary.HashedDictionary;

import java.util.ArrayList;

public class CharacterDatabase implements  CharacterDatabaseInterface{
    ArrayList<Character> characterList;
    HashedDictionary<String, Integer> dictionary;

    public CharacterDatabase() {
        characterList = new ArrayList<>();
        dictionary = new HashedDictionary<>(10);
    }

    /**
     * Adds the character to the list, as well as the stored index to the hashed dictionary
     *
     * @param name name of character
     * @param height height of character
     * @param weight weight of character
     * @param moralAlign moral alignment of character
     */
    @Override
    public void addCharacter(String name, int height, int weight, double moralAlign) {
        characterList.add(new Character(name, height, weight, moralAlign));
        dictionary.add(name, characterList.size() - 1);
    }

    /**
     * Removes character from list and dictionary, setting the value of the list entry to null to maintain order
     *
     * @param name name of character to be removed
     */
    @Override
    public void removeCharacter(String name) {
        characterList.set(dictionary.getValue(name), null);
        dictionary.remove(name);
    }

    /**
     * Return character object with given name
     *
     * @param name name of character
     * @return character object with given name
     */
    @Override
    public Character getCharacter(String name) {
        return characterList.get(dictionary.getValue(name));
    }

    /**
     * Returns the hashed dictionary storing character indexes in the list
     *
     * @return hashed dictionary of character indexes
     */
    @Override
    public HashedDictionary<String, Integer> getHashTable() {
        return dictionary;
    }

    /**
     * Prints out all the current characters in the list
     */
    @Override
    public void printList() {
        for (Character character: characterList) {
            if (character != null) {
                System.out.println(character);    
            }
        }
    }
}
