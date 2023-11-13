package CharacterStuff;

public class Character {
    private String name;
    private int height;
    private int weight;
    private int health;
    private double moralAlign;

    public Character(String name, int height, int weight, double moralAlign) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.moralAlign = moralAlign;
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
     * Heals the player based on the given amount, if the amount exceeds 100,
     * the players health becomes 100
     *
     * @param healAmount amount to be healed
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
     */
    public void change(double moralAlign) {
        if (this.moralAlign + moralAlign < 0.0 || this.moralAlign + moralAlign > 1.0) {
            throw new IllegalArgumentException("Moral alignment must not exceed 1.0 or be below 0.0");
        }
        this.moralAlign += moralAlign;
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
