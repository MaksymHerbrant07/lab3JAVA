package game.battle;

import game.model.Droid;

import java.util.Random;

public class BattleEngine {
    private final Random rnd = new Random();

    public BattleLog duel(Droid A, Droid B) {
        A.reset(); B.reset();
        BattleLog log = new BattleLog();
        log.add("=== DUEL: %s vs %s ===".formatted(A.getName(), B.getName()));

        // хто починає — випадково
        boolean aTurn = rnd.nextBoolean();

        while (A.isAlive() && B.isAlive()) {
            if (aTurn) {
                int dealt = A.attack(B);
                log.add("%s → %s : %d dmg | %s HP=%d".formatted(
                        A.getName(), B.getName(), dealt, B.getName(), B.getHp()));
            } else {
                int dealt = B.attack(A);
                log.add("%s → %s : %d dmg | %s HP=%d".formatted(
                        B.getName(), A.getName(), dealt, A.getName(), A.getHp()));
            }
            aTurn = !aTurn; // чергуємо ходи
        }

        Droid winner = A.isAlive() ? A : B;
        log.add("Переможець: " + winner.getName());
        return log;
    }
}
