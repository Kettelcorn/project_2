package CharacterStuff;

import Dictionary.HashedDictionary;

public interface CharacterDatabaseInterface {
    public void addCharacter(String name, int height, int weight, double moralAlign);
    public void removeCharacter(String name);
    public Character getCharacter(String name);
    public HashedDictionary<String, Integer> getHashTable();
    public void printList();
}
