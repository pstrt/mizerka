package mizerka.ui;

import mizerka.GameContext;
import mizerka.GameMode;
import mizerka.Round;

import javax.swing.*;

public class GameModeSelector extends WindowBase
{
    private JComboBox<String> selector;

    @Override
    protected void initialize() {
        setSize(300, 300);
        super.initialize();

        addLabel();
        addSelector();
        addStartButton();

        initialized = true;
    }

    private void addLabel() {
        JLabel label = new JLabel("Wybiera gracz: " + GameContext.getInstance().getNextPlayer().getName());
        label.setBounds(10, 10, 260, 20);
        panel.add(label);
    }

    private void addSelector() {
        selector = new JComboBox<>();
        selector.setBounds(10, 40, 260, 20);

        for (GameMode mode : GameContext.getInstance().getNextPlayer().getRemainingModes()) {
            selector.addItem(mode.toString());
        }

        panel.add(selector);
    }

    private void addStartButton() {
        JButton startButton = new JButton("Wybierz");
        startButton.setBounds(10, 180, 260, 60);
        startButton.addActionListener(e -> onStart());
        panel.add(startButton);
    }

    private void onStart() {
        GameMode mode = GameMode.valueOf(selector.getSelectedItem().toString());
        if (Round.start(mode)) {
            new RoundWindow().show();
            hide();
        }
    }
}
