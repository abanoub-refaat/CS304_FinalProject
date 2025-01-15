import com.sun.opengl.util.FPSAnimator;
import java.awt.*;
import javax.swing.*;
import javax.media.opengl.*;
import java.awt.image.BufferedImage;

public class Main extends JFrame {
    FPSAnimator animator;
    public static void main(String[] args) {
        final Main app = new Main();
        login  login=new login();
    }

    private final MainGLEventListener listener;


    public Main() {
        //set the JFrame title
        super("Whack-A-Rabbit Game");
        BufferedImage transparentImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Game.mainMusic.playMusic();
        // Create a transparent cursor
        Cursor transparentCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                transparentImage, new Point(0, 0), "blank cursor");
        //kill the process when the JFrame is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GLCanvas glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(new MainGLEventListener());
        glcanvas.setCursor(transparentCursor);
        listener = new MainGLEventListener();
        glcanvas.addGLEventListener(listener);

        // Mouse Listeners
        glcanvas.addMouseListener(listener);
        glcanvas.addMouseMotionListener(listener);

        // Key Listeners
        glcanvas.addKeyListener(listener);
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        glcanvas.setFocusable(true);

        // Animator initialisation
        animator = new FPSAnimator(glcanvas, 20);

        // add the GLCanvas just like we would any Component
        add(glcanvas, BorderLayout.CENTER);
        setSize(900, 700);
        animator.start();

        //center the JFrame on the screen
        setFocusable(true);
        centerWindow();
        setVisible(true);
    }

    public void centerWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();

        if (frameSize.width > screenSize.width) frameSize.width = screenSize.width;
        if (frameSize.height > screenSize.height) frameSize.height = screenSize.height;

        this.setLocation(
                (screenSize.width - frameSize.width) >> 1,
                (screenSize.height - frameSize.height) >> 1
        );
    }
}