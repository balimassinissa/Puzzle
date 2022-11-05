package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controler.AbstractControler;
import utils.ObserverModel;

public class Puzzle extends JFrame implements ObserverModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int level;// the level is initialized to 1
	private String imageName;
	private FileReader f;
	private String[] imageList;
	private JPanel container = new JPanel();
	private JLabel ecran = new JLabel();
	JPanel chiffre = new JPanel();
	private AbstractControler controler;
	JButton[] tab_button;
	Map<JButton, Integer> buttonMap = new HashMap<JButton, Integer>();// this map contains the button and his valu in
																		// the grid
	public Puzzle(AbstractControler controler)
			throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		this.controler = controler;
		try {
			f = new FileReader("./ressources/in.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		level = 1;
		imageName = "";
		chiffre.setLayout(new GridLayout(3,3));
		this.setSize(920, 620);
		this.setTitle("Puzzle");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		loadImageList();
		initComposant();
		this.addKeyListener(new KeyListenerClass());
		this.setVisible(true);
		this.requestFocus();
	}

	private void initComposant() {
		//set window component
		Font police = new Font("Arial", Font.ITALIC, 16);
		ecran = new JLabel("Veuillez faire un deplacement");
		ecran.setFont(police);
		ecran.setHorizontalAlignment(JLabel.CENTER);
		ecran.setPreferredSize(new Dimension(300, 30));
		chiffre.setPreferredSize(new Dimension(640, 480));
		JPanel settings = new JPanel();
		settings.setPreferredSize(new Dimension(190, 300));
		//reset button
		JButton reset = new JButton("Recommencer");
		reset.setPreferredSize(new Dimension(180, 60));
		reset.addActionListener(new ResetListner());
		settings.add(reset);
		// change difficulty button
		JButton changeDifficulty = new JButton("changer le niveau");
		changeDifficulty.setPreferredSize(new Dimension(180, 60));
		changeDifficulty.addActionListener(new changeDifficultyListner());
		settings.add(changeDifficulty);
		//initalise buttons
		tab_button = new JButton[this.controler.getnbColumns() * this.controler.getnbLines()];
		for (int i = 0; i < tab_button.length; i++) {
			tab_button[i] = new JButton();
			chiffre.add(tab_button[i]);
		}
		initialiseImages();
		JPanel panEcran = new JPanel();
		//set display screen
		panEcran.setPreferredSize(new Dimension(320, 40));
		panEcran.add(ecran);
		panEcran.setBorder(BorderFactory.createLineBorder(Color.black));
		//add component
		container.add(panEcran, BorderLayout.NORTH);
		container.add(chiffre, BorderLayout.CENTER);
		container.add(settings, BorderLayout.EAST);
		this.setContentPane(container);
		loop();
	}
	/**
	 * update the display screen
	 */
	public void updateModel() {
		this.ecran.setText(controler.getScreenMessage());
	}
	/**
	 * update images
	 */
	public void updateModel(int value) {
		tab_button[controler.getCoordinate(value).getX()*controler.getnbColumns()+controler.getCoordinate(value).getY()].setIcon(null);
		BufferedImage bf = null ;
		try {
			bf = ImageIO.read(new File("./ressources/"+imageName+".png"));
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		buttonMap.put(tab_button[controler.getEmptyPosition().getX()*controler.getnbColumns()+controler.getEmptyPosition().getY()], value);
		buttonMap.put(tab_button[controler.getCoordinate(value).getX()*controler.getnbColumns()+controler.getCoordinate(value).getY()], -1);
		int x  = (value-1)/this.controler.getnbColumns();
		int y = (value - x*this.controler.getnbLines())-1;
		Dimension dim = new Dimension(chiffre.getWidth()/this.controler.getnbColumns(),chiffre.getHeight()/this.controler.getnbLines()) ;
		tab_button[controler.getEmptyPosition().getX()*controler.getnbColumns()+controler.getEmptyPosition().getY()].setIcon(new ImageIcon(bf.getSubimage(y*dim.width,x*dim.height , dim.width, dim.height)));
	}
	/**
	 * 
	 * key listener
	 */
	public class KeyListenerClass implements KeyListener {

		public KeyListenerClass() {

		}

		public void keyPressed(KeyEvent e) {
			final int keyCode = e.getKeyCode();
			switch (keyCode) {
			case KeyEvent.VK_UP:
				toUp();
				break;
			case KeyEvent.VK_DOWN:
				toDown();
				break;
			case KeyEvent.VK_RIGHT:
				toRight();
				break;
			case KeyEvent.VK_LEFT:
				toLeft();
				break;
			default:
				ecran.setText("tapez sur une fleche");
			}
		}
		public void keyReleased(KeyEvent arg0) {
			
		}

		public void keyTyped(KeyEvent arg0) {
			
		}
	}
	/**
	 * 
	 * button listener
	 *
	 */
	class ChiffreListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton button = ((JButton) e.getSource());
			int value = buttonMap.get(button);
			controler.play(value);
			loop();
			requestFocus();
		}
	}
	/**
	 * 
	 * reset button listener
	 *
	 */
	class ResetListner implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			reset();
		}
	}
	/**
	 * 
	 * change difficulty button listener
	 *
	 */
	class changeDifficultyListner implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new ZDialog(null, "Listes des niveaux", true);
		}
	}
	/**
	 * 
	 * JDialog
	 *
	 */
	public class ZDialog extends JDialog {
		/**
		* 
		*/
		private static final long serialVersionUID = 1L;

		public ZDialog(JFrame parent, String title, boolean modal) {

			super(parent, title, modal);

			this.setSize(300, 400);

			JLabel label = new JLabel("Veulliez choisir un niveaux");
			label.setPreferredSize(new Dimension(100, 100));
			label.setHorizontalAlignment(JLabel.CENTER);

			JPanel buttonContainer = new JPanel();
			buttonContainer.setPreferredSize(new Dimension(200, 200));

			JButton buttonLevel1 = new JButton("Facile : 3x3");
			buttonLevel1.setPreferredSize(new Dimension(180, 60));
			buttonLevel1.addActionListener(new ChangeLevelListener(1));// level 1
			buttonContainer.add(buttonLevel1);

			JButton buttonLevel2 = new JButton("Moyen : 4x4");
			buttonLevel2.setPreferredSize(new Dimension(180, 60));
			buttonLevel2.addActionListener(new ChangeLevelListener(2));// level 2
			buttonContainer.add(buttonLevel2);

			JButton buttonLevel3 = new JButton("Difficile : 5x5");
			buttonLevel3.setPreferredSize(new Dimension(180, 60));
			buttonLevel3.addActionListener(new ChangeLevelListener(3));// level 3
			buttonContainer.add(buttonLevel3);

			this.add(label, BorderLayout.NORTH);
			this.add(buttonContainer, BorderLayout.CENTER);
			this.setLocationRelativeTo(null);

			this.setResizable(false);

			this.setVisible(true);

		}
	}
	/**
	 * 
	 * change level button listener
	 *
	 */
	class ChangeLevelListener implements ActionListener {
		int level;

		public ChangeLevelListener(int level) {
			this.level = level;
		}

		public void actionPerformed(ActionEvent e) {
			changeLevel(level);
		}
	}
	/**
	 * 
	 * @param level
	 * change the level difficulty
	 */
	public void changeLevel(int level) {
		switch (level) {
		case 1:
			this.controler.setnbLines(3);
			this.controler.setnbColumns(3);
			this.controler.setnbMoveCreation(20);
			chiffre.setLayout(new GridLayout(3,3));
			this.level = 1;
			reinit();
			break;
		case 2:
			this.controler.setnbLines(4);
			this.controler.setnbColumns(4);
			this.controler.setnbMoveCreation(40);
			chiffre.setLayout(new GridLayout(4,4));
			this.level = 2;
			reinit();
			break;
		case 3:
			this.controler.setnbLines(5);
			this.controler.setnbColumns(5);
			this.controler.setnbMoveCreation(80);
			chiffre.setLayout(new GridLayout(5,5));
			this.level = 3;
			reinit();
			break;
		}
	}
	/**
	 * reinit the buttons
	 */
	public void reinit() {
		chiffre.removeAll();
		tab_button = new JButton[this.controler.getnbColumns() * this.controler.getnbLines()];
		for (int i = 0; i < tab_button.length; i++) {
			tab_button[i] = new JButton();
			chiffre.add(tab_button[i]);
		}
		reset();
		initialiseImages();
	}
	/**
	 * initialise buttons images
	 */
	public void initialiseImages() {
		Dimension dim =  new Dimension(chiffre.getPreferredSize().width/this.controler.getnbColumns(),chiffre.getPreferredSize().height/this.controler.getnbLines()) ;
		int selectedImage = new Random().nextInt(imageList.length);
		imageName = imageList[selectedImage];
		
		BufferedImage bf = null ;
		try {
			bf = ImageIO.read(new File("./ressources/"+imageName+".png"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		int k = 0;
		for (int i = 0; i < this.controler.getnbLines(); i++) {
			for (int j = 0; j < this.controler.getnbColumns(); j++) {
				if (this.controler.getGate()[i][j] == -1) {
					tab_button[k].setIcon(null);
					buttonMap.put(tab_button[k], -1);
				}

				else {
					int x  = (this.controler.getGate()[i][j]-1)/this.controler.getnbColumns();
					int y = (this.controler.getGate()[i][j] - x*this.controler.getnbLines())-1;
					tab_button[k].setIcon(new ImageIcon(bf.getSubimage(y*dim.width,x*dim.height , dim.width, dim.height)));
					buttonMap.put(tab_button[k], this.controler.getGate()[i][j]);
					

				}
				tab_button[k].addActionListener(new ChiffreListener());
				k++;
			}
		}
	}
	/**
	 * reset game
	 */
	public void reset() {
		controler.reset();
		initialiseImages();
	}
	/**
	 * play the up value
	 */
	public void toUp() {
		int value = controler.getUpValue();
		controler.play(value);
		loop();
	}
	/**
	 * play the left value
	 */
	public void toLeft() {
		int value = controler.getLeftValue();
		controler.play(value);	
		loop();
	}
	/**
	 * play the right value
	 */
	public void toRight() {
		int value = controler.getRightValue();
		controler.play(value);
		loop();
	}
	/**
	 * play the down value
	 */
	public void toDown() {
		int value = controler.getDownValue();
		controler.play(value);
		loop();
	}

	/**
	 * load the name of images which are stocked in file in.txt
	 */
	public void loadImageList() {
		BufferedReader lecteurAvecBuffer = new BufferedReader(f);
		String line;
		int nbP = 0;
		try {
			if ((line = lecteurAvecBuffer.readLine()) != null)
				nbP = Integer.parseInt(line);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int i = 0;
		imageList = new String[nbP];
		try {
			while (((line = lecteurAvecBuffer.readLine()) != null) && i < nbP) {
				imageList[i] = line;
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * verify if the game is resolved
	 */
	public void loop() {
		if (controler.isResolved()) {
			JOptionPane.showMessageDialog(null, "Bravo ! vous avez résolu le puzzle", "Félicitations",
					JOptionPane.INFORMATION_MESSAGE);
			reset();
		}
	}
}
