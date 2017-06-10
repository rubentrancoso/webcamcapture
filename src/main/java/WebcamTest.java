import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

public class WebcamTest extends JFrame implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	private static boolean moveMode = false;
	private static Point location  = new Point(0,0);
	private static Point mousePos = new Point(0,0);

	public WebcamTest(WebcamPanel panel) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
		int taskBarSize = scnMax.bottom;
		this.add(panel);
		this.setResizable(false);
		this.setSize(new Dimension(176, 144));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.pack();
		location.setLocation(screenSize.width - this.getWidth(), screenSize.height - taskBarSize - this.getHeight());
		this.setLocation(location);
		this.setVisible(true);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	public static void main(String[] args) throws InterruptedException {
		Webcam webcam = Webcam.getDefault();
		webcam.setViewSize(new Dimension(176, 144));
		WebcamPanel panel = new WebcamPanel(webcam);
		panel.setMirrored(true);
		new WebcamTest(panel);
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2 && !e.isConsumed()) {
			e.consume();
			if(e.getX() > this.getWidth() - 10 && e.getY() < 10) {
				System.exit(0);
			} else {
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
				int taskBarSize = scnMax.bottom;
				location.setLocation(screenSize.width - this.getWidth(), screenSize.height - taskBarSize - this.getHeight());
				this.setLocation(location);				
			}
		}
		
	}

	public void mouseDragged(MouseEvent e) {
		if (moveMode) {
			this.setLocation(e.getXOnScreen() - (int)mousePos.getX(),e.getYOnScreen() - (int)mousePos.getY());
		}
	}	
	
	public void mousePressed(MouseEvent e) {
		moveMode = true;
		mousePos.setLocation(e.getX(), e.getY());
	}

	public void mouseReleased(MouseEvent e) {
		moveMode = false;
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

}
