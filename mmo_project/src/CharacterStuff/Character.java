package CharacterStuff;

public class Character {
    private String name;
    private int height;
    private int weight;
    private double moralAlign;

    public Character(String name, int height, int weight, double moralAlign) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.moralAlign = moralAlign;
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
     * Returns moral alignment of character
     *
     * @return moral alignment
     */
    public double getMoralAlign() {
        return moralAlign;
    }
}
