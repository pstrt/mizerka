package mizerka;

import java.util.ArrayList;
import java.util.List;

public class GameContext
{
    private static GameContext instance;
    private final List<Player> players = new ArrayList<>(3);
    private final List<Round> rounds = new ArrayList<>(18);
    private Round currentRound;

    public static GameContext getInstance() {
        if (instance == null) {
            instance = new GameContext();
        }

        return instance;
    }

    public void initializePlayers(List<String> playerNames) {
        for (String playerName : playerNames) {
            players.add(new Player(playerName));
        }
    }

    public void setRound(Round round) {
        rounds.add(round);
        currentRound = round;
    }

    public Round getCurrentRound() {
        return currentRound;
    }

    public int getIndexOfCurrentRound() {
        return rounds.indexOf(currentRound);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public boolean isEndGame() {
        return rounds.size() == 18;
    }

    public Player getWinner() {
        if (!isEndGame()) {
            return null;
        }

        Player winner = players.get(0);
        for (Player player : players) {
            if (player.getFinalPoints() > winner.getFinalPoints()) {
                winner = player;
            }
        }

        return winner;
    }

    public Player getNextPlayer() {
        int currentRoundIndex = rounds.indexOf(currentRound);
        int nextPlayerIndex = (currentRoundIndex + 1) % players.size();
        return players.get(nextPlayerIndex);
    }
}
