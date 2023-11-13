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
    @Override
    public void addCharacter(String name, int height, int weight, double moralAlign) {

    }

    @Override
    public void removeCharacter(String name) {

    }

    @Override
    public Character getCharacter(String name) {
        return null;
    }

    @Override
    public HashedDictionary<String, Integer> getHashTable() {
        return null;
    }

    @Override
    public void printList() {

    }
}
