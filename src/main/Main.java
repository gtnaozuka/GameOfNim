package main;

import gui.GUI;
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        int input = 0;
        try {
            do {
                input = Integer.valueOf(JOptionPane.showInputDialog(null,
                        "Número de linhas: ", "Game of Nim", JOptionPane.QUESTION_MESSAGE));
            } while (input <= 0);
        } catch (NumberFormatException ex) {
            System.exit(0);
        }

        final int rows = input;
        styleGUI();
        java.awt.EventQueue.invokeLater(() -> {
            GUI gui = new GUI(rows);
            gui.create();
            gui.setVisible(true);
        });
    }

    private static void styleGUI() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
