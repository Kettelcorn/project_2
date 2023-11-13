/*
* Jackson Kettle, Mika Kitazumi
* Nov 2023
* Project 2 -  Project 2: Hashing Project - MMORPG Database
* For this project, we making a Massive Multiplayer Online Game (MMOG).
* Using a combonation of the Hash Dictionary, we will store characters and their
* attributes. We will be using quadratic probing. The Charater class represents
* atributes while the datavase handles operators. UML diagram in files and in
* the main scenarios for removing, adding, and modifying characters.
* */
import CharacterStuff.CharacterDatabase;
import org.w3c.dom.CharacterData;

public class Main {
    public static void main(String[] args) {
        CharacterDatabase characterDatabase = new CharacterDatabase();
        characterDatabase.addCharacter("FB", 5, 10, 1.0);
        characterDatabase.addCharacter("Ea", 100, 200, 0.0);
        characterDatabase.addCharacter("Daegon", 50, 67, 0.s5);
        characterDatabase.addCharacter("Gandalf", 20, 20, 0.7);
        characterDatabase.getHashTable().displayHashTable();

        characterDatabase.removeCharacter("Daegon");
        characterDatabase.getHashTable().displayHashTable();
        characterDatabase.printList();

        System.out.println("Gandalf before change");
        System.out.println(characterDatabase.getCharacter("Gandalf"));
        System.out.println();
        characterDatabase.getCharacter("Gandalf").change(-0.5);
        characterDatabase.getCharacter("Gandalf").injure(60);
        characterDatabase.getCharacter("Gandalf").heal(20);
        System.out.println("Gandalf after change");
        System.out.println(characterDatabase.getCharacter("Gandalf"));
    }
}
