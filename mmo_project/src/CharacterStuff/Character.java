package CharacterStuff;

/*
 * The Character class represents a players character in a Massive Multiplayer Online Game (MMOG).
 * It includes name, height, weight, health, and moral alignment.
 **/
public class Character implements CharacterInterface{
    private String name;
    private int height;
    private int weight;
    private int health;
    private double moralAlign;

    /**
    * construcvtor for creating a new instatance of Character
    * @param name nane if charater
    * @param height height of character
    * @para weight weight of character
    * @param moralAlign moral alignment of chracters from 0.0 to 1.0
    * @throws IllegalArgumentException if moral alignment is not in range
    * */
    public Character(String name, int height, int weight, double moralAlign) {
        if (moralAlign < 0.0 || moralAlign > 1.0) {
            throw new IllegalArgumentException("Moral alignment must be greater than 0.0 and less than 1.0");
        }
        setName(name);
        setHeight(height);
        setWeight(weight);
        this.moralAlign = Math.round(moralAlign * 10) / 10.0;
        health = 100;
    }

    /**
     * Returns name of character
     *
     * @return name of character
     */
    public String getName() {
        return name;
    }

    /**
     * Returns height of character
     *
     * @return height of character
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns weight of character
     *
     * @return weight of character
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Returns health of a player
     *
     * @return health of player
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns moral alignment of character
     *
     * @return moral alignment
     */
    public double getMoralAlign() {
        return moralAlign;
    }

    /**
     * Set the name of the character
     *
     * @param name name of character
     */
    public void setName(String name) {
        if (name.equals("") || name == null) {
            throw new IllegalArgumentException("Name must not be empty or null");
        }
        this.name = name;
    }

    /**
     * Set the height of the character
     *
     * @param height height of character
     * @throws IllegalArgumentException if the height is less than or equal to 0.
     */
    public void setHeight(int height) {
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be greater than 0");
        }
        this.height = height;
    }

    /**
     * Set weight of the character
     *
     * @param weight weight of character
     * @throw nIllegalArgumentException ("Weight must be greater than 0");
     */
    public void setWeight(int weight) {
        if (height <= 0) {
            throw new IllegalArgumentException("Weight must be greater than 0");
        }
        this.weight = weight;
    }

    /**
     * Heals the player based on the given amount, if the amount exceeds 100,
     * the players health becomes 100
     *
     * @param healAmount amount to be healed
     * @throws IllegalArgumentException "Must be a positive number"
     */
    public void heal(int healAmount) {
        if (healAmount <= 0) {
            throw new IllegalArgumentException("Must be a positive number");
        }
        health = Math.min(health + healAmount, 100);
    }

    /**
     * Inures the players health by a given amount, if the amount goes past 0,
     * the players health is equal to 0
     *
     * @param injureAmount amount to be injured
     * @throws IllegalArgumentException "Must be a positive number"
     */
    public void injure(int injureAmount) {
        if (injureAmount <= 0) {
            throw new IllegalArgumentException("Must be a positive number");
        }
        health = Math.max(health - injureAmount, 0);
    }

    /**
     * Changes the moral alignment of the character either up or down
     *
     * @param moralAlign the amount to change moral align
     * @throws IllegalArgumentException if the resulting moral alignment is not within the valid range.
     */
    public void change(double moralAlign) {
        if (this.moralAlign + moralAlign < 0.0 || this.moralAlign + moralAlign > 1.0) {
            throw new IllegalArgumentException("Moral alignment must not exceed 1.0 or be below 0.0");
        }
        this.moralAlign += moralAlign;
        this.moralAlign = Math.round(this.moralAlign * 10) / 10.0;
    }

    /**
     * Returns a formated string of all the characters stats
     *
     * @return string of character stats
     */
    @Override
    public String toString() {
        return "Name: " + name + ", Height: " + height + ", Weight: " + weight + ", Health: " +
                health + " Moral Alignment: " + moralAlign;
    }
}
