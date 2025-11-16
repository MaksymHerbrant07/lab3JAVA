package game.model;

import java.util.Random;

public abstract class Droid {
    protected final String name;
    protected final int maxHp;
    protected int hp;

    protected final int minDamage;
    protected final int maxDamage;

    private final Random rnd = new Random();

    public Droid(String name, int maxHp, int minDamage, int maxDamage) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Ім'я порожнє");
        if (maxHp <= 0) throw new IllegalArgumentException("HP має бути > 0");
        if (minDamage <= 0 || maxDamage < minDamage)
            throw new IllegalArgumentException("Некоректний діапазон урону");
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getMinDamage() { return minDamage; }
    public int getMaxDamage() { return maxDamage; }
    public boolean isAlive() { return hp > 0; }

    public void reset() { this.hp = maxHp; }

    /** Атака з випадковим уроном у діапазоні [minDamage; maxDamage] */
    public int attack(Droid target) {
        if (!this.isAlive() || !target.isAlive()) return 0;
        int roll = rnd.nextInt(maxDamage - minDamage + 1) + minDamage;
        int dealt = Math.min(roll, target.hp);
        target.hp -= dealt;
        return dealt;
    }

    @Override
    public String toString() {
        return "%s{hp=%d/%d, dmg=%d-%d}".formatted(name, hp, maxHp, minDamage, maxDamage);
    }
}
