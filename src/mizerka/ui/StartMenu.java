package mizerka.ui;

import mizerka.GameContext;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

public class StartMenu extends WindowBase
{
    List<JTextField> players;

    @Override
    protected void initialize() {
        setSize(400, 300);
        super.initialize();

        players = new ArrayList<>(3);

        addPlayers();
        addStartButton();

        initialized = true;
    }

    private void addPlayers() {
        for (int i = 0; i < 3; i++) {
            addPlayerLabel(20, 10 + 50 * i, 340, 20, i + 1);
            addPlayerTextField(20, 30 + 50 * i, 340, 30);
        }
    }

    private void addPlayerLabel(int x, int y, int width, int height, int playerIndex) {
        JLabel label = new JLabel("Gracz " + playerIndex);
        label.setBounds(x, y, width, height);
        panel.add(label);
    }

    private void addPlayerTextField(int x, int y, int width, int height) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, width, height);
        players.add(textField);
        panel.add(textField);
    }

    private void addStartButton() {
        JButton startButton = new JButton("Rozpocznij grę!");
        startButton.setBounds(20, 180, 340, 60);
        startButton.addActionListener(e -> onStart());
        panel.add(startButton);
    }

    private void onStart() {

        List<String> playerNames = new ArrayList<>(3);
        for (JTextField player : players) {
            String name = player.getText();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Upewnij się, że wszyscy gracze mają nazwę!");
                return;
            }
            playerNames.add(name);
        }

        GameContext context = GameContext.getInstance();
        context.initializePlayers(playerNames);

        hide();
        new GameModeSelector().show();
    }
}
