package CharacterStuff;
/**
 * The CharacterInterface defines the interface for managing character
 * actions.
 */
public interface CharacterInterface {
    /**
     * Increases the character's health by a specified amount.
     *
     * @param healAmount The amount to increase the character's health.
     */
    public void heal(int healAmount);

    /**
     * decreases the characters health by a specified amount
     *
     * @param injureAmount the amount the decrease the characters health
     * */
    public void injure(int injureAmount);

    /**
     * changes the characters moral alignment
     *
     * @param moralAlign percentaged by which the change the moralAlignment
     * */
    public void change(double moralAlign);
}
