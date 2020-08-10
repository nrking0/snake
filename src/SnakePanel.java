
import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class SnakePanel extends JPanel implements KeyListener {

    final private int WIDTH = 400;
    final private int HEIGHT = 400;
    Board snake;
    protected String state;
    private String direction;
    private boolean enemyHit;
    private int speed;
    BufferedImage titleScreen;
    Image scaledTitleScreen;
    BufferedImage endScreen;
    Image scaledEndScreen;
    private double score;
    private int printedScore;
    private ScoreBoard leaderBoard;
    private JButton save;
    private JButton dontSave;
    private JButton playAgain;
    private JButton mainMenu;
    private JButton start;
    private String pausedDir;

    public SnakePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.GREEN);
        addKeyListener(this);
        snake = new Board(WIDTH / 20, HEIGHT / 20);
        state = "BEGINNING";
        direction = "STOP";
        enemyHit = false;
        speed = 200;
        score = 0.0;
        leaderBoard = new ScoreBoard();
        try {
            titleScreen = ImageIO.read(new File("TitleScreen.jpg"));
            endScreen = ImageIO.read(new File("GameOver.jpg"));
        } catch (IOException e) {
            System.out.println(e);
        }
        scaledTitleScreen = titleScreen.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        scaledEndScreen = endScreen.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);

        save = new JButton("Save Score");
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Please enter your name");
                if (name != null) {
                    int duplicate = leaderBoard.checkDuplicate(name);
                    if (duplicate == -1) {
                        leaderBoard.addScore(new Score(name, printedScore));
                        state = "LEADERBOARD";
                    } else {

                    }
                }
            }
        });
        this.add(save);
        save.setBounds(105, 370, 75, 20);
        save.setVisible(false);

        dontSave = new JButton("Don't Save Score");
        dontSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                state = "LEADERBOARD";
            }
        });
        this.add(dontSave);
        dontSave.setBounds(190, 370, 110, 20);
        dontSave.setVisible(false);

        playAgain = new JButton("Play again");
        playAgain.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                state = "MOVING";
            }
        });
        this.add(playAgain);
        playAgain.setBounds(130, 370, 75, 20);
        playAgain.setVisible(false);

        mainMenu = new JButton("Main Menu");
        mainMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                state = "BEGINNING";
            }
        });
        this.add(mainMenu);
        mainMenu.setBounds(210, 370, 75, 20);
        mainMenu.setVisible(false);

        start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                state = "MOVING";
            }
        });
        this.add(start);
        start.setBounds(175, 360, 50, 20);
        start.setVisible(true);
    }

    public void run() {
        while (true) {
            update();
            repaint();
            delay(speed);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        if (state.equals("BEGINNING")) {
            g.drawImage(scaledTitleScreen, 0, 0, null);
        } else if (state.equals("MOVING")) {
            g.setColor(Color.RED);
            g.fillOval(snake.getEnemyMarker().getxPos() * 20, snake.getEnemyMarker().getyPos() * 20, 20, 20);
            g.setColor(Color.WHITE);
            for (Marker a : snake.getList()) {
                g.fillRect(a.getxPos() * 20, a.getyPos() * 20, 20, 20);
            }
        } else if (state.equals("END")) {
            g.drawImage(scaledEndScreen, 0, 0, null);
            g.drawString("Your score was: " + String.valueOf(printedScore), 145, 360);

        } else if (state.equals("LEADERBOARD")) {
            Font firstFont = new Font("Sans", Font.BOLD, 40);
            g.setFont(firstFont);
            g.drawString("Leaderboard:", 70, 40);
            int i = 1;
            int yValue = 70;
            Font font = new Font("Sans", Font.PLAIN, 20);
            g.setFont(font);
            for (Score a : leaderBoard.getList()) {
                if (i <= 10) {
                    g.drawString(i + ". " + a.getName() + ": " + a.getScore(), 150, yValue);
                    i++;
                    yValue += 25;
                }
            }
        }
    }

    public void update() {
        if (state.equals("BEGINNING")) {
            start.setVisible(true);
            mainMenu.setVisible(false);
            playAgain.setVisible(false);
        } else if (state.equals("MOVING")) {
            start.setVisible(false);
            playAgain.setVisible(false);
            mainMenu.setVisible(false);
            if (snake.getList().get(0).getxPos() == snake.getEnemyMarker().getxPos()
                    && snake.getList().get(0).getyPos() == snake.getEnemyMarker().getyPos()) {
                enemyHit = true;
                score += 200.0 / speed;
            }
            snake.move(direction, enemyHit);
            if (enemyHit) {
                if (speed > 50)
                    speed -= 5;
                enemyHit = false;
                snake.setEnemyMarker();
            }
            if (snake.checkRules()) {
                this.state = "END";
            }
        } else if (state.equals("END")) {
            snake = new Board(WIDTH / 20, HEIGHT / 20);
            direction = "STOP";
            enemyHit = false;
            speed = 200;
            printedScore = (int) score;
            save.setVisible(true);
            dontSave.setVisible(true);
        } else if (state.equals("LEADERBOARD")) {
            score = 0;
            leaderBoard.sort();
            save.setVisible(false);
            dontSave.setVisible(false);
            playAgain.setVisible(true);
            mainMenu.setVisible(true);
        }
    }

    public void delay(int n) {
        try {
            Thread.sleep(n);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                if (state.equals("MOVING"))
                    this.direction = "UP";
                break;
            case KeyEvent.VK_DOWN:
                if (state.equals("MOVING"))
                    this.direction = "DOWN";
                break;
            case KeyEvent.VK_LEFT:
                if (state.equals("MOVING"))
                    this.direction = "LEFT";
                break;
            case KeyEvent.VK_RIGHT:
                if (state.equals("MOVING"))
                    this.direction = "RIGHT";
                break;
             //case KeyEvent.VK_Q:
             //if(state.equals("MOVING")) this.state = "END";
            // break;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void pause() {
        pausedDir = direction;
        this.direction = "STOP";
    }

    public void resume() {
        this.direction = pausedDir;
    }

}