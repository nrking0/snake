
import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;

public class SnakeRunner {

  public static void main(String[] args) {
    JFrame f = new JFrame("Snake");
    SnakePanel p = new SnakePanel();
    f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    f.addWindowListener(new WindowAdapter(){
      @Override
      public void windowClosing(WindowEvent e){
        p.pause();
        int result = JOptionPane.showConfirmDialog(f, "Are you sure you want to exit the game?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
        if(result == JOptionPane.YES_OPTION){
          System.exit(0);
        } else {
          p.resume();
        }
      }
    });
    p.setLayout(null);
    f.add(p);
    f.pack();
    f.setResizable(false);
    f.setVisible(true);
    p.setFocusable(true);
    p.requestFocusInWindow();
    p.run();
  }
}
