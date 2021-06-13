package GUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

@SuppressWarnings({ "serial", "unused" })
public class MainMenuView extends JFrame {

	 Clip clip;

	public MainMenuView(Controller listener) {

		super();
		ImageIcon gameicon = new ImageIcon("images/icon.png");
		this.setIconImage(gameicon.getImage());
		this.dispose();
		this.setUndecorated(true); // change this to true in the end
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setTitle("HearthStone");
		this.setVisible(true);
		ImageIcon img = new ImageIcon("images/background1.jpg");
		Image newimg = img.getImage().getScaledInstance(this.getWidth(),
				this.getHeight(), java.awt.Image.SCALE_SMOOTH);
		img = new ImageIcon(newimg);

		this.setContentPane(new JLabel(img));
		

		this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("images/hand.png").getImage(), new Point(0, 0),
				"custom cursor"));

		int ButtonsWidth = this.getWidth() / 7;
		int ButtonsHeight = this.getHeight() / 12;
		int ButtonsCenterWidth = this.getWidth() / 2 - ButtonsWidth / 2;
		int ButtonsStartHeight = this.getHeight() / 2 + ButtonsHeight + 40;

		JButton StartAMatch = createButton("Start Match", ButtonsCenterWidth,
				ButtonsStartHeight, ButtonsWidth, ButtonsHeight);
		StartAMatch.addActionListener(listener);
		this.add(StartAMatch);

		JButton Exit = createButton("Exit", ButtonsCenterWidth,
				ButtonsStartHeight + ButtonsHeight + 20, ButtonsWidth,
				ButtonsHeight);
		Exit.addActionListener(listener);
		this.add(Exit);
		

		JLabel copyright = new JLabel("©Blizzard Entertainment, Inc.");
		copyright.setBounds(this.getWidth() *887/1920,
				this.getHeight()*100/108, this.getWidth() *145/1920,
				this.getWidth() * 10 / 1920);
		this.add(copyright);
		copyright.setFont(new Font("Arial", Font.BOLD,
				this.getWidth() * 10 / 1920));
		copyright.setForeground(Color.DARK_GRAY);

		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(new File("sounds/Main_Title.wav")
							.getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			Thread.sleep(100);

		} catch (Exception e) {
			e.printStackTrace();
		}

		this.revalidate();
		this.repaint();

	}

	public JButton createButton(String name, int x, int y, int w, int h) {
		JButton a = new JButton(name);

		a.setHorizontalTextPosition(JButton.CENTER);
		a.setVerticalTextPosition(JButton.CENTER);
		a.setActionCommand(name);
		a.setBackground(Color.ORANGE);
		a.setBounds(x, y, w, h);
		a.setOpaque(false);
		a.setFont(new Font("Arial", Font.BOLD, w / 7));

		ImageIcon icon = new ImageIcon("images/button.png");

		Image newimg = icon.getImage().getScaledInstance(w, h,
				java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);
		a.setIcon(icon);

		a.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("images/down.png").getImage(), new Point(0, 0),
				"custom cursor"));

		return a;
	}

	public Clip getClip() {
		return clip;
	}

	public static void main(String[] args) {

	}

}
