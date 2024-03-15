package mizerka;

import java.util.ArrayList;
import java.util.List;

public class Player
{
    private final List<GameMode> playedModes;
    private final String name;

    public Player(String name) {
        this.name = name;
        this.playedModes = new ArrayList<>(GameMode.values().length);
    }

    public String getName() {
        return name;
    }

    public List<GameMode> getRemainingModes() {
        List<GameMode> gameModes = new ArrayList<>(List.of(GameMode.values()));
        gameModes.removeAll(playedModes);
        return gameModes;
    }

    public int getFinalPoints() {
        int points = 0;
        for (Round round : GameContext.getInstance().getRounds()) {
            points += round.getPoints(this);
        }
        return points;
    }

    public void onFinished(Round round) {
        playedModes.add(round.getGameMode());
    }

    public boolean validateGameMode(GameMode gameMode) {
        return !playedModes.contains(gameMode);
    }
}
