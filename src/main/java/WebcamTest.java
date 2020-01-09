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
	private static Point location = new Point(0, 0);
	private static Point mousePos = new Point(0, 0);
	private static Dimension camSize = new Dimension(176, 144);
	private static int gripSize = 20;

	public WebcamTest(WebcamPanel panel, double factor) {
		if (factor <= 0) {
			factor = 1;
		}
		this.buildPanel(panel);
		int width = (int) (this.getWidth() * factor);
		int height = (int) (this.getHeight() * factor);
		this.setSize(width, height);
		this.setLocation();
	}

	public WebcamTest(WebcamPanel panel) {
		this.buildPanel(panel);
		this.setLocation();
	}

	private void buildPanel(WebcamPanel panel) {
		this.add(panel);
		this.setResizable(false);
//		this.setSize(new Dimension(176, 144));
//		this.setSize(new Dimension(100, 100));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.pack();
		this.setVisible(true);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	private void setLocation() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
		int taskBarSize = scnMax.bottom;
		location.setLocation(screenSize.width - this.getWidth(), screenSize.height - taskBarSize - this.getHeight());
		this.setLocation(location);
	}

	public static void main(String[] args) throws InterruptedException 
	{
		Webcam webcam = Webcam.getDefault();
		webcam.setViewSize(camSize);
		WebcamPanel panel = new WebcamPanel(webcam);
		panel.setMirrored(true);
		new WebcamTest(panel, .8);
	}

	public void mouseClicked(MouseEvent e) 
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
		int taskBarSize = scnMax.bottom;
		
		if (e.getClickCount() == 2 && !e.isConsumed()) 
		{
			e.consume();
			if (e.getY() < gripSize && e.getX() > this.getWidth() - gripSize) 
			{
				location.setLocation(screenSize.width - this.getWidth(), 0);
				this.setLocation(location);
			} 
			if (e.getY() < gripSize && e.getX() < gripSize) 
			{
				location.setLocation(0, 0);
				this.setLocation(location);
			} 
			if (e.getY() > this.getHeight() - gripSize && e.getX() < gripSize) 
			{
				location.setLocation(0, screenSize.height - this.getHeight() - taskBarSize);
				this.setLocation(location);
			} 
			if (e.getY() > this.getHeight() - gripSize && e.getX() > this.getWidth() - gripSize ) 
			{
				location.setLocation(screenSize.width - this.getWidth(), screenSize.height - this.getHeight() - taskBarSize);
				this.setLocation(location);
			} 
		}

	}

	public void mouseDragged(MouseEvent e) {
		if (moveMode) {
			this.setLocation(e.getXOnScreen() - (int) mousePos.getX(), e.getYOnScreen() - (int) mousePos.getY());
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
