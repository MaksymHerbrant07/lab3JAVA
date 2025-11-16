package game.battle;

import game.model.Droid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TeamBattleEngine {
    private final Random rnd = new Random();

    public BattleLog fight(List<Droid> teamA, List<Droid> teamB) {
        teamA.forEach(Droid::reset);
        teamB.forEach(Droid::reset);

        BattleLog log = new BattleLog();
        log.add("=== TEAM FIGHT: A vs B ===");

        while (alive(teamA) && alive(teamB)) {
            // випадково, чий хід — A чи B
            boolean aTurn = rnd.nextBoolean();
            List<Droid> atkTeam = aTurn ? teamA : teamB;
            List<Droid> defTeam = aTurn ? teamB : teamA;

            Droid attacker = randomAlive(atkTeam);
            Droid defender = randomAlive(defTeam);
            if (attacker == null || defender == null) break;

            int dealt = attacker.attack(defender);
            log.add("[%s] %s → %s : %d dmg | %s HP=%d".formatted(
                    aTurn ? "A" : "B",
                    attacker.getName(), defender.getName(),
                    dealt, defender.getName(), defender.getHp()));
        }

        log.add("Перемогла команда: " + (alive(teamA) ? "A" : "B"));
        return log;
    }

    private boolean alive(List<Droid> team) {
        for (Droid d : team) if (d.isAlive()) return true;
        return false;
    }

    private Droid randomAlive(List<Droid> team) {
        List<Droid> alive = new ArrayList<>();
        for (Droid d : team) if (d.isAlive()) alive.add(d);
        if (alive.isEmpty()) return null;
        return alive.get(rnd.nextInt(alive.size()));
    }
}
