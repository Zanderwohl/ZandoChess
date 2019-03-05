
package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputAdapter;

import gameLogic.Board;
import movement.Touple;

/**
 * A window which runs at 60 fps and displays the board to the user.
 */
public class UserView {
	
	Board board;
	FlatBoard flatBoard;
	
	JFrame frame;
	JScrollPane scrollPane;
	JPanel buttonPanel;
	JLabel highlightLabel;
	static ResourceBundle language;
	
	private MouseMovement mouseMovement = new MouseMovement();
	
	/**
	 * Listens for if the quit button has been pressed.
	 */
	ActionListener quitListener = new ActionListener(){
	  public void actionPerformed(ActionEvent e){
	    System.exit(0); //replace this.
	  }
	};
	
	/**
	 * Listens for if the undo button has been pressed.
	 */
	ActionListener undoListener = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			System.out.println("Undo " + e.getSource());
		}
		
	};
	
	/**
	 * Listens for if the scale of the board has been changed.
	 */
	ChangeListener scaleListener = new ChangeListener() {

		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			flatBoard.setSquareSize(source.getValue());
			flatBoard.repaint();
			//setWindowSize();
		}
		
	};
	
	//private int buttonHeight;
	
	//private Touple highlighted;
	
	/**
	 * The constructor needs no arguments. However, this object must have initialize() called on it.
	 * @param currentLocale 
	 */
	public UserView(Locale currentLocale) {
		language = ResourceBundle.getBundle("lang.names", currentLocale);
	}
	
	/**
	 * Gets a localized String for the text field specified.
	 * @param text The text field you want the localized String of.
	 * @return The localized String.
	 */
	public static String getText(String text) {
		return language.getString(text);
	}
	
	/**
	 * Initialize the board after creation. Needs a board to create a graphical representation of that board.
	 * @param b The board to be represented.
	 * @param buttonHeight How tall the buttons appear.
	 */
	public void initialize(Board b, int buttonHeight) {
		board = b;
		flatBoard = new FlatBoard(b);
		//this.buttonHeight = buttonHeight;
	}
	
	/**
	 * Set the size of the squares inside the board displayed on this UserView.
	 * @param size The size, generally in pixels.
	 */
	public void setSquareSize(int size) {
		flatBoard.setSquareSize(size);
	}
	
	/**
	 * Creates and displays the graphical window. Additionally, adds required listeners.
	 * Rewrite to be shorter.
	 */
	public void createWindow() {
		//GridLayout gui = new GridLayout(3,3);
		
		frame = new JFrame();
		
		flatBoard.addMouseListener(mouseMovement);
	    flatBoard.addMouseMotionListener(mouseMovement);
        
		setIcon("icon.png");
	   
		frame.setVisible(true);
		
		
		scrollPane=new JScrollPane(flatBoard, 
				   ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,  
				   ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		ControlBarFactory factory = new ControlBarFactory(language);
		factory.addQuitListener(quitListener);
		factory.addUndoListener(undoListener);
		factory.addScaleListener(scaleListener);
		buttonPanel = factory.getPanel();
		highlightLabel = factory.getHighlightLabel();
		
		frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		frame.setTitle(language.getString("title"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
        
        setWindowSize();
        frame.setVisible(true);
		
	}
	

	
	/**
	 * Sets whether the window is automatically maximized or not, based on board size.
	 */
	private void setWindowSize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		if(flatBoard.getHeight() > ( height - 200) || flatBoard.getWidth() > (width)) {
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		
		frame.setMinimumSize(new Dimension(flatBoard.getWidth() /*+ flatBoard.getSquareSize()*/, flatBoard.getHeight() + buttonPanel.getHeight()));
		//buttonPanel.setMinimumSize(new Dimension(10,10));
		frame.pack();
		//buttonPanel.setPreferredSize(new Dimension(100,buttonHeight));
	}
	
	/**
	 * Sets the icon for the frame, as long as the icon is in the root of the resources folder.
	 * Automatically handles problems of missing files, or problems with the file itself.
	 * @param icon The name of the icon image.
	 */
	private void setIcon(String icon) {
		try {
			URL url = getClass().getResource("/" + icon);
			//System.out.println(url);
			frame.setIconImage(ImageIO.read(url));
		} catch (FileNotFoundException e) {
			System.err.println("Icon for window not found:");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Some error has occured trying to load icon:");
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates animations on the graphical component. Requires time since last frame be provided, for animation speed.
	 * @param delta The time since the last frame in miliseconds.
	 */
	public void update(double delta) {
		//all time-based actions must be multiplied by delta.
	}
	
	/**
	 * Simply repaints the entire board, to update it.
	 */
	public void render() {
		flatBoard.repaint();
	}
	
	/*
	private Image getIcon(String path) {
		System.out.println(path);
		URL iconURL = new URL(path);
		System.out.println(iconURL);
        //iconURL is null when not found
        return new ImageIcon(iconURL).getImage();
		
		
	}*/
	
	/**
	 * Renders games at a fixed 60 fps.
	 */
	public void gameLoop() {
		boolean running = true;
		int lastFpsTime = 0;
		@SuppressWarnings("unused")
		int fps = 0;
		
		long lastLoopTime = System.nanoTime();
		final int TARGET_FPS = 60;
		final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
		
		while(running) {
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			double delta = updateLength / ((double)OPTIMAL_TIME);
			
			//framerate counter update
			lastFpsTime += updateLength;
			fps++;
			
			//update FPS counter every 1/fpsMult seconds.
			//AKA fpsMult times per second
			int fpsMult = 1;
			if(lastFpsTime >= (1000000000 / fpsMult)) {
				//System.out.println("FPS: " + (fps*fpsMult));
				//TestRandomSpace();
				lastFpsTime = 0;
				fps = 0;
			}
			
			update(delta);
			
			render();
			
			
			try {
				Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME) / 1000000 );
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch(IllegalArgumentException e) {
				System.out.println("Time is negative, no sleeping.");
			}
			
			
		}
		
		
	}
	
	/**
	 * Remove later. Outputs random empty coordinate to console every frame counter update.
	 */
	public void TestRandomSpace() {
		Touple t = flatBoard.getBoard().randomEmptySpace();
		System.out.println(t.getX() + t.getY());
	}
	
	/**
	 * Listens for user interaction, like selection of squares, or hovering over squares.
	 * @author Alexander
	 *
	 */
	private class MouseMovement extends MouseInputAdapter {
		/**
		 * When the mouse moves, the flatboard is notified to update the highlighted square.
		 * @param MouseEvent e The location of the mouse.
		 */
	    public void mouseMoved(MouseEvent e) {
	    	flatBoard.setHighlightedSquare(e.getX(), e.getY());
	    	updateHighlightText(flatBoard.getHighlightedSquare());
	    	//System.out.println(e.getX() + " " + e.getY() + " " + flatBoard.getHighlightedSquare().getX() + " " + flatBoard.getHighlightedSquare().getY());
	    }
	    
	    public void updateHighlightText(Touple square) {
	    	if(square == null) {
	    		highlightLabel.setText("");
	    	} else {
	    		if(board.spaceEmpty(square)) {
		    		highlightLabel.setText("");
		    	} else {
		    		String color = board.getPiece(square).getColor();
		    		color = color.substring(0, 1).toUpperCase() + color.substring(1);
		    		String piece = board.getPiece(square).getName();
		    		piece = piece.substring(0, 1).toUpperCase() + piece.substring(1);
		    		highlightLabel.setText(color + " " + piece);
		    	}
	    	}
	    	
	    	
	    }
	    
	    /**
	     * When the mouse is released, the flatBoard is notified that it has been clicked and where.
	     * @param MouseEvent e The location of the click.
	     */
		public void mouseReleased(MouseEvent e) {
	    	flatBoard.registerClick(e.getX(), e.getY());
	    	//System.out.println(e.getX() + " " + e.getY());
	    }
	}
	
	

	/**
	 * Gets the flatBoard stored in this class.
	 * @return the flatBoard in this UserView component.
	 */
	public FlatBoard getFlatBoard() {
		return flatBoard;
	}
	
	/**
	 * Tells the displayed board that the square has been deselected.
	 */
	public void deselectSquare() {
		flatBoard.registerClick(0, 0);
		flatBoard.setClickedSquare(-1, -1);
	}
	
	/**
	 * Gets the square which has been clicked previously.
	 * @return A Touple of that space.
	 */
	public Touple getClickedSquare() {
		return flatBoard.getClickedSquare();
	}
}
