import gui.RecordWindow;
import org.jnativehook.GlobalScreen;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RobotListener {
    private static Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());

    public static void main(String[] args) {
        logger.setLevel(Level.OFF);
        new RecordWindow().setVisible(true);
    }
}