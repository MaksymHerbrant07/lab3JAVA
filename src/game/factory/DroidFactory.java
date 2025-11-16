package game.factory;

import game.model.AgilityDroid;
import game.model.Droid;
import game.model.StrengthDroid;
import game.model.UniversalDroid;

public class DroidFactory {
    public Droid create(String type, String name) {
        String t = type.toLowerCase();
        return switch (t) {
            case "strength"  -> new StrengthDroid(name);
            case "agility"   -> new AgilityDroid(name);
            case "universal" -> new UniversalDroid(name);
            default -> throw new IllegalArgumentException("Невідомий тип: " + type);
        };
    }
}
