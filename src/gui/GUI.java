package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUI extends JFrame {

    private final int rows, cols;
    private final boolean[][] sushiMatrix;
    private final JButton[] btnRows;
    private JButton btnNewGame, btnPCMove;

    private int sushis;

    private static final ImageIcon ICON = new ImageIcon("./sushi.png");
    private static final Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize();

    public GUI(int rows) {
        super("Game of Nim");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        this.rows = rows;
        this.cols = 2 * rows - 1;
        this.sushiMatrix = new boolean[this.rows][this.cols];
        this.btnRows = new JButton[rows];
    }

    public void create() {
        sushis = (1 + cols) * rows / 2;

        for (int i = 0; i < rows; i++) {
            createSushis(i);
            createBtnRows(i);
        }

        btnNewGame = new JButton("Novo Jogo");
        btnNewGame.addActionListener((ActionEvent e) -> {
            btnNewGameAction(e);
        });
        btnPCMove = new JButton("PC");
        btnPCMove.addActionListener((ActionEvent e) -> {
            btnPCMoveAction(e);
        });

        updateContentPane();
    }

    private void createSushis(int row) {
        int quantity = 2 * row + 1;
        int space = (cols - quantity) / 2;
        for (int j = space; j < space + quantity; j++) {
            sushiMatrix[row][j] = true;
        }
    }

    private void createBtnRows(final int row) {
        btnRows[row] = new JButton("Linha " + (row + 1));
        btnRows[row].addActionListener((ActionEvent e) -> {
            btnRowsAction(e, row);
        });
    }

    private void btnNewGameAction(ActionEvent e) {
        sushis = (1 + cols) * rows / 2;

        for (int i = 0; i < rows; i++) {
            createSushis(i);
            btnRows[i].setEnabled(true);
        }

        btnPCMove.setEnabled(true);

        updateContentPane();
    }

    private void btnRowsAction(ActionEvent e, int row) {
        btnPCMove.setEnabled(true);
        for (int i = 0; i < rows; i++) {
            if (i != row) {
                btnRows[i].setEnabled(false);
            }
        }
        removeOneSushi(row);

        if (sushis == 0) {
            this.setVisible(false);
            JOptionPane.showMessageDialog(null, "Ganhador: PC", "Game of Nim",
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        } else {
            updateContentPane();
        }
    }

    private void btnPCMoveAction(ActionEvent e) {
        btnPCMove.setEnabled(false);
        for (int i = 0; i < rows; i++) {
            int quantity = 2 * i + 1;
            int space = (cols - quantity) / 2;
            for (int j = space; j < space + quantity; j++) {
                if (sushiMatrix[i][j]) {
                    btnRows[i].setEnabled(true);
                    break;
                }
            }
        }

        if (sushis == 0) {
            this.setVisible(false);
            JOptionPane.showMessageDialog(null, "Ganhador: Player", "Game of Nim",
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        } else {
            updateContentPane();
        }
    }

    private void updateContentPane() {
        JPanel sushiPanel = new JPanel(new GridLayout(rows, cols + 1));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (sushiMatrix[i][j]) {
                    sushiPanel.add(new JLabel(ICON));
                } else {
                    sushiPanel.add(new JPanel());
                }
            }
            sushiPanel.add(btnRows[i]);
        }

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(btnNewGame);
        buttonsPanel.add(btnPCMove);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(sushiPanel, BorderLayout.NORTH);
        contentPane.add(buttonsPanel, BorderLayout.SOUTH);

        this.setContentPane(contentPane);
        this.pack();
        this.setLocation((SCREENSIZE.width - this.getWidth()) / 2,
                (SCREENSIZE.height - this.getHeight()) / 2);
    }

    private void removeOneSushi(int row) {
        int quantity = 2 * row + 1;
        int space = (cols - quantity) / 2;
        for (int j = space; j < space + quantity; j++) {
            if (sushiMatrix[row][j]) {
                sushiMatrix[row][j] = false;
                sushis--;
                if (j == space + quantity - 1) {
                    btnRows[row].setEnabled(false);
                }
                break;
            }
        }
    }

}
