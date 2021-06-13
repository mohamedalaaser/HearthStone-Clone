package GUI;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import model.cards.Card;
import model.cards.Rarity;
import model.heroes.Hero;
import engine.Game;
import model.cards.minions.*;
import model.cards.spells.Spell;

import javax.swing.TransferHandler;
import javax.swing.border.TitledBorder;

@SuppressWarnings({ "serial", "unused" })
public class GameView extends JFrame {

	 Clip clip;
	private JLabel frame;
	JLayeredPane HeroUp;
	JLayeredPane HeroDown;
	private Game game;
	Hero herofirst; // the hero that played the first turn and will be placed
					// down first
	Hero herosecond; // the hero that started second
	Hero current;
	private JLabel deck1Label;
	private JLabel deck2Label;
	private JButton endturn;
	private JLabel mana1Label;
	private JLabel mana2Label;
	private JLabel hp1Label;
	private JLabel hp2Label;
	private JButton hero1pic;
	private JButton hero2pic;
	JButton power1;
	JButton power1big;
	JButton power2;
	JButton power2big;
	JLayeredPane layerpane;
	JFrame windowframe;
	JFrame gameover;

	JLayeredPane cardsfirst;
	JLayeredPane cardssecond;

	JPanel display;

	JPanel fieldcardsfirst;
	JPanel fieldcardssecond;

	boolean backhandneedchange;
	boolean fronthandneedchange;

	private Controller listener;
	int turn = 1;

	JLabel bubbled;
	JLabel bubbleu;
	JPanel burned;

	
	
	
	public GameView(Controller listener) {
		
		super();
		current = herofirst;
		ImageIcon gameicon = new ImageIcon("images/icon.png");
		this.setIconImage(gameicon.getImage());
		this.listener = listener;
		addKeyListener(listener);

		this.dispose();
		this.setUndecorated(true); // change this to true in the end
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setTitle("HearthStone");
		this.setVisible(true);

		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(new File("sounds/OnARoll.wav")
							.getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			Thread.sleep(100);

		} catch (Exception e) {
			e.printStackTrace();
		}

		layerpane = new JLayeredPane();
		layerpane.setPreferredSize(new Dimension(this.getWidth(), this
				.getHeight()));
		layerpane.setLayout(null);
		layerpane.addKeyListener(listener);
		layerpane.setFocusable(true);
		this.add(layerpane);

		ImageIcon img = new ImageIcon("images/field.jpg");
		Image newimg = img.getImage().getScaledInstance(this.getWidth(),
				this.getHeight(), java.awt.Image.SCALE_SMOOTH);
		img = new ImageIcon(newimg);
		frame = new JLabel(img);
		frame.setBounds(0, 0, this.getWidth(), this.getHeight());
		frame.addKeyListener(listener);
		frame.setFocusable(true);
		layerpane.add(frame, 0);

		frame.setLayout(new GridLayout(0, 1));


		this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("images/hand.png").getImage(), new Point(0, 0),
				"custom cursor"));

		HeroUp = new JLayeredPane();
		HeroDown = new JLayeredPane();
		HeroUp.setBounds(0, 0, this.getWidth(), this.getHeight() / 2);
		HeroDown.setBounds(0, this.getHeight() / 2, this.getWidth(),
				this.getHeight() / 2);
		HeroUp.setBackground(Color.RED);
		HeroDown.setBackground(Color.BLACK);
		HeroUp.setOpaque(false);
		HeroDown.setOpaque(false);
		HeroUp.setLayout(null);
		HeroDown.setLayout(null);

		HeroUp.addKeyListener(listener);
		HeroUp.setFocusable(true);
		HeroDown.addKeyListener(listener);
		HeroDown.setFocusable(true);
		layerpane.add(HeroUp, new Integer(1));
		layerpane.add(HeroDown, new Integer(1));
		game = listener.getGame();

		herofirst = game.getCurrentHero();
		herosecond = game.getOpponent();

		ImageIcon hero1icon = new ImageIcon("images/"
				+ herofirst.getClass().getSimpleName() + "hp.png");
		Image hero1img = hero1icon.getImage().getScaledInstance(
				this.getWidth() * 190 / 1382, this.getHeight() * 170 / 744,
				java.awt.Image.SCALE_SMOOTH);
		hero1icon = new ImageIcon(hero1img);

		hero1pic = new JButton(hero1icon);
		hero1pic.addKeyListener(listener);
		hero1pic.setFocusable(true);
		hero1pic.setBounds(this.getWidth() * 612 / 1382,
				this.getHeight() * 141 / 744, this.getWidth() * 190 / 1382,
				this.getHeight() * 170 / 744);
		hero1pic.setIcon(hero1icon);
		hero1pic.setBackground(Color.GREEN);
		hero1pic.setOpaque(false);
		hero1pic.setActionCommand("hero1");
		// hero1pic.addMouseListener(listener);
		// hero1pic.addMouseMotionListener(listener);
		hero1pic.setBorder(BorderFactory.createEmptyBorder());
		hero1pic.setLayout(null);
		hero1pic.addActionListener(listener);
		HeroDown.add(hero1pic, new Integer(4));

		ImageIcon hero2icon = new ImageIcon("images/"
				+ herosecond.getClass().getSimpleName() + "hp.png");
		Image hero2img = hero2icon.getImage().getScaledInstance(
				this.getWidth() * 190 / 1382, this.getHeight() * 170 / 744,
				java.awt.Image.SCALE_SMOOTH);
		hero2icon = new ImageIcon(hero2img);

		hero2pic = new JButton(hero2icon);
		hero2pic.addKeyListener(listener);
		hero2pic.setFocusable(true);
		hero2pic.setBounds(this.getWidth() * 613 / 1382,
				this.getHeight() * 87 / 744, this.getWidth() * 190 / 1382,
				this.getHeight() * 170 / 744);
		hero2pic.setIcon(hero2icon);
		hero2pic.setBackground(Color.GREEN);
		hero2pic.setOpaque(false);
		hero2pic.setActionCommand("hero2");
		// hero1pic.addMouseListener(listener);
		// hero1pic.addMouseMotionListener(listener);
		hero2pic.setBorder(BorderFactory.createEmptyBorder());
		hero2pic.setLayout(null);
		hero2pic.addActionListener(listener);
		HeroUp.add(hero2pic, new Integer(4));

		String mana1 = herofirst.getCurrentManaCrystals() + "/"
				+ herofirst.getTotalManaCrystals();
		mana1Label = new JLabel(mana1);
		mana1Label.setBounds(this.getWidth() * 1343 / 1920,
				this.getHeight() * 413 / 1080, this.getWidth() * 100 / 1920,
				this.getHeight() * 100 / 1080);
		mana1Label.addKeyListener(listener);
		mana1Label.setFocusable(true);
		HeroDown.add(mana1Label);
		mana1Label.setFont(new Font("Arial", Font.BOLD,
				this.getWidth() * 25 / 1920));
		mana1Label.setForeground(Color.WHITE);

		String mana2 = herosecond.getCurrentManaCrystals() + "/"
				+ herosecond.getTotalManaCrystals();
		mana2Label = new JLabel(mana2);
		mana2Label.addKeyListener(listener);
		mana2Label.setFocusable(true);
		mana2Label.setBounds(this.getWidth() * 1312 / 1920,
				this.getHeight() * 40 / 1080, this.getWidth() * 100 / 1920,
				this.getHeight() * 100 / 1080);
		HeroUp.add(mana2Label);
		mana2Label.setFont(new Font("Arial", Font.BOLD,
				this.getWidth() * 25 / 1920));
		mana2Label.setForeground(Color.WHITE);

		String deck1 = herofirst.getDeck().size() + "";
		deck1Label = new JLabel(deck1);
		deck1Label.setBounds(this.getWidth() * 1860 / 1920,
				this.getHeight() * 50 / 1080, this.getWidth() * 100 / 1920,
				this.getHeight() * 100 / 1080);
		deck1Label.addKeyListener(listener);
		deck1Label.setFocusable(true);
		HeroDown.add(deck1Label, new Integer(4));
		deck1Label.setFont(new Font("Arial", Font.BOLD,
				this.getWidth() * 25 / 1920));
		deck1Label.setForeground(Color.WHITE);

		String deck2 = herosecond.getDeck().size() + "";
		deck2Label = new JLabel(deck2);
		deck1Label.addKeyListener(listener);
		deck2Label.setFocusable(true);
		deck2Label.setBounds(this.getWidth() * 1860 / 1920,
				this.getHeight() * 300 / 1080, this.getWidth() * 100 / 1920,
				this.getHeight() * 100 / 1080);
		HeroUp.add(deck2Label, new Integer(4));
		deck2Label.setFont(new Font("Arial", Font.BOLD,
				this.getWidth() * 25 / 1920));
		deck2Label.setForeground(Color.WHITE);

		hp1Label = new JLabel(herofirst.getCurrentHP() + "");
		hp1Label.setFont(new Font("Arial", Font.BOLD,
				this.getWidth() * 26 / 1920));
		hp1Label.setForeground(Color.WHITE);
		hp1Label.setBounds(this.getWidth() * 213 / 1920,
				this.getHeight() * 112 / 1080, this.getWidth() * 100 / 1920,
				this.getHeight() * 100 / 1080);
		hp1Label.addKeyListener(listener);
		hp1Label.setFocusable(true);
		hero1pic.add(hp1Label);

		hp2Label = new JLabel(herosecond.getCurrentHP() + "");
		hp2Label.setFont(new Font("Arial", Font.BOLD,
				this.getWidth() * 26 / 1920));
		hp2Label.addKeyListener(listener);
		hp2Label.setFocusable(true);
		hp2Label.setForeground(Color.WHITE);
		hp2Label.setBounds(this.getWidth() * 213 / 1920,
				this.getHeight() * 112 / 1080, this.getWidth() * 100 / 1920,
				this.getHeight() * 100 / 1080);
		hero2pic.add(hp2Label);

		ImageIcon endturnicon = new ImageIcon("images/endturnbutton.png");
		Image endturnimg = endturnicon.getImage().getScaledInstance(
				this.getWidth() * 215 / 1920, this.getHeight() * 115 / 1464,
				java.awt.Image.SCALE_SMOOTH);
		endturnicon = new ImageIcon(endturnimg);
		endturn = new JButton();
		endturn.setText("End turn");
		endturn.setActionCommand("End turn");
		endturn.addActionListener(listener);
		endturn.setHorizontalTextPosition(JButton.CENTER);
		endturn.setVerticalTextPosition(JButton.CENTER);
		endturn.setFont(new Font("Arial", Font.BOLD,
				this.getWidth() * 28 / 1920));
		endturn.setForeground(Color.WHITE);
		endturn.setIcon(endturnicon);
		endturn.setBounds(this.getWidth() * 1650 / 1920,
				this.getHeight() * 476 / 1080, this.getWidth() * 215 / 1920,
				this.getHeight() * 115 / 1464);
		endturn.setBackground(Color.ORANGE);
		endturn.setOpaque(false);
		endturn.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("images/down.png").getImage(), new Point(0, 0),
				"custom cursor"));
		endturn.addKeyListener(listener);
		endturn.setFocusable(true);
		layerpane.add(endturn, new Integer(2));

		power1 = new JButton();
		power1.setBorder(BorderFactory.createEmptyBorder());
		power1.setActionCommand("Hero Power1");
		ImageIcon power1icon = new ImageIcon("images/"
				+ herofirst.getClass().getSimpleName() + "powersmall.png");
		Image power1img = power1icon.getImage().getScaledInstance(
				this.getWidth() * 100 / 1382, this.getHeight() * 100 / 744,
				java.awt.Image.SCALE_SMOOTH);
		power1icon = new ImageIcon(power1img);
		power1.setIcon(power1icon);
		power1.setBackground(Color.BLACK);
		power1.setOpaque(false);
		power1.setBounds(this.getWidth() * 1134 / 1920,
				this.getHeight() * 780 / 1080, this.getWidth() * 100 / 1382,
				this.getHeight() * 100 / 744);
		power1.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("images/down.png").getImage(), new Point(0, 0),
				"custom cursor"));
		power1.addKeyListener(listener);
		power1.setFocusable(true);
		layerpane.add(power1, new Integer(6));
		power1.addMouseListener(listener);
		power1.addActionListener(listener);

		power2 = new JButton();
		power2.setBorder(BorderFactory.createEmptyBorder());
		power2.setActionCommand("Hero Power2");
		ImageIcon power2icon = new ImageIcon("images/"
				+ herosecond.getClass().getSimpleName() + "powersmall.png");
		Image power2img = power2icon.getImage().getScaledInstance(
				this.getWidth() * 100 / 1382, this.getHeight() * 100 / 744,
				java.awt.Image.SCALE_SMOOTH);
		power2icon = new ImageIcon(power2img);
		power2.setIcon(power2icon);
		power2.setBackground(Color.BLACK);
		power2.setOpaque(false);
		power2.setBounds(this.getWidth() * 1134 / 1920,
				this.getHeight() * 150 / 1080, this.getWidth() * 100 / 1382,
				this.getHeight() * 100 / 744);
		power2.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("images/down.png").getImage(), new Point(0, 0),
				"custom cursor"));
		power2.addKeyListener(listener);
		power2.setFocusable(true);
		layerpane.add(power2, new Integer(6));
		power2.addMouseListener(listener);
		power2.addActionListener(listener);

		cardsfirst = new JLayeredPane();
		cardsfirst = getHeroHand(herofirst);
		cardsfirst.addKeyListener(listener);
		cardsfirst.setFocusable(true);
		HeroDown.add(cardsfirst, new Integer(5));

		cardssecond = new JLayeredPane();
		cardssecond = getHeroHandback(herosecond);
		cardssecond.addKeyListener(listener);
		cardssecond.setFocusable(true);
		HeroUp.add(cardssecond, new Integer(5));

		display = new JPanel();
		display.setBounds(594 * this.getWidth() / 1366,
				400 * this.getHeight() / 768, 200 * this.getWidth() / 1366,
				276 * this.getHeight() / 768);
		display.setBackground(Color.RED);
		display.setLayout(null);
		display.setOpaque(false);
		display.addKeyListener(listener);
		display.setFocusable(true);
		layerpane.add(display, new Integer(5));

		fieldcardsfirst = new JPanel();
		fieldcardsfirst.setBackground(Color.BLACK);
		fieldcardsfirst.setOpaque(false);
		fieldcardsfirst.setLayout(new GridBagLayout());
		fieldcardsfirst.addKeyListener(listener);
		fieldcardsfirst.setFocusable(true);
		getfieldminionsfirst(fieldcardsfirst, herofirst);
		layerpane.add(fieldcardsfirst, new Integer(4));

		fieldcardssecond = new JPanel();
		fieldcardssecond.setBackground(Color.BLACK);
		fieldcardssecond.setOpaque(false);
		fieldcardssecond.setLayout(new GridBagLayout());
		fieldcardssecond.addKeyListener(listener);
		fieldcardssecond.setFocusable(true);
		getfieldminionssecond(fieldcardssecond, herosecond);
		layerpane.add(fieldcardssecond, new Integer(4));

		fieldcardssecond.setBounds(8 * this.getWidth() / 1366,
				240 * this.getHeight() / 768, this.getWidth(),
				125 * this.getHeight() / 768);
		fieldcardsfirst.setBounds(8 * this.getWidth() / 1366,
				368 * this.getHeight() / 768, this.getWidth(),
				125 * this.getHeight() / 768);

		backhandneedchange = false;
		fronthandneedchange = false;

		JPanel top = new JPanel();
		top.setPreferredSize(new Dimension(this.getWidth(),
				this.getHeight() / 3));
		top.setOpaque(false);
		top.setLayout(null);
		top.addKeyListener(listener);
		top.setFocusable(true);
		frame.add(top);

		// fe taree2a tanya?

		// JButton Pause = new JButton("Pause");
		JButton Pause = new JButton();
		Pause.setActionCommand("Pause");
		// Pause.setHorizontalTextPosition(JButton.CENTER);
		// Pause.setVerticalTextPosition(JButton.CENTER);
		Pause.addActionListener(listener);
		// Pause.setFont(new Font("Arial", Font.BOLD, this.getWidth() / 84));
		// ImageIcon icon = new ImageIcon("images/button.png");
		ImageIcon icon = new ImageIcon("images/pause.png");
		// Image iconImage = icon.getImage().getScaledInstance(
		// this.getWidth() / 12, this.getHeight() / 17,
		// java.awt.Image.SCALE_SMOOTH);
		Image iconImage = icon.getImage().getScaledInstance(
				this.getHeight() / 12, this.getHeight() / 12,
				java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(iconImage);
		Pause.setIcon(icon);
		Pause.setOpaque(false);
		// Pause.setBounds(0, 0, this.getWidth() / 12, this.getHeight() / 17);
		Pause.setBounds(this.getHeight() / 120, this.getHeight() / 120,
				this.getHeight() / 15, this.getHeight() / 15);
		Pause.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("images/down.png").getImage(), new Point(0, 0),
				"custom cursor"));
		Pause.addKeyListener(listener);
		Pause.setFocusable(true);
		top.add(Pause);

		setPauseWindow();

		ImageIcon bubbledicon = new ImageIcon("images/speech.png");
		Image bubbledimg = bubbledicon.getImage().getScaledInstance(
				350 * this.getWidth() / 1920, 200 * this.getHeight() / 1080,
				java.awt.Image.SCALE_SMOOTH);
		bubbledicon = new ImageIcon(bubbledimg);
		bubbled = new JLabel();
		bubbled.setIcon(bubbledicon);
		bubbled.setBounds(990 * this.getWidth() / 1920,
				590 * this.getHeight() / 1080, 350 * this.getWidth() / 1920,
				200 * this.getHeight() / 1080);
		bubbled.setText("ASASSASA");
		bubbled.setHorizontalTextPosition(JLabel.CENTER);
		bubbled.setVerticalTextPosition(JLabel.CENTER);
		bubbled.setFont(new Font("Arial", Font.BOLD,
				17 * this.getWidth() / 1920));

		layerpane.add(bubbled, new Integer(25));
		bubbled.setVisible(false);

		bubbleu = new JLabel();
		bubbleu.setIcon(bubbledicon);
		bubbleu.setBounds(1050 * this.getWidth() / 1920,
				30 * this.getHeight() / 1080, 350 * this.getWidth() / 1920,
				200 * this.getHeight() / 1080);
		bubbleu.setText("ASASSASA");
		bubbleu.setHorizontalTextPosition(JLabel.CENTER);
		bubbleu.setVerticalTextPosition(JLabel.CENTER);
		bubbleu.setFont(new Font("Arial", Font.BOLD,
				17 * this.getWidth() / 1920));
		layerpane.add(bubbleu, new Integer(25));
		bubbleu.setVisible(false);

		burned = new JPanel();
		burned.setBounds(850 * this.getWidth() / 1920,
				360 * this.getHeight() / 1080, 125 * this.getWidth() / 1000,
				105 * this.getHeight() / 402);
		burned.setBackground(Color.BLACK);
		burned.setOpaque(false);
		burned.setVisible(false);
		layerpane.add(burned, new Integer(26));

		// 960
		// 1920*1080
	}

	public Clip getClip() {
		return clip;
	}

	public void refreshView() {
		String mana1 = herofirst.getCurrentManaCrystals() + "/"
				+ herofirst.getTotalManaCrystals();
		mana1Label.setText(mana1);
		String deck1 = herofirst.getDeck().size() + "";
		deck1Label.setText(deck1);

		String mana2 = herosecond.getCurrentManaCrystals() + "/"
				+ herosecond.getTotalManaCrystals();
		mana2Label.setText(mana2);
		String deck2 = herosecond.getDeck().size() + "";
		deck2Label.setText(deck2);

		if (turn % 2 == 0) {
			if (herosecond.getTotalManaCrystals() < 10 && herosecond.getCurrentManaCrystals() < 10)
				mana2Label.setBounds(this.getWidth() * 1343 / 1920,
						this.getHeight() * 413 / 1080,
						this.getWidth() * 100 / 1920,
						this.getHeight() * 100 / 1080);
			else if (herosecond.getTotalManaCrystals() == 10 && herosecond.getCurrentManaCrystals() == 10)
				mana2Label.setBounds(this.getWidth() * 1328 / 1920,
						this.getHeight() * 413 / 1080,
						this.getWidth() * 100 / 1920,
						this.getHeight() * 100 / 1080);
			else
				mana2Label.setBounds(this.getWidth() * 1330 / 1920,
						this.getHeight() * 413 / 1080,
						this.getWidth() * 100 / 1920,
						this.getHeight() * 100 / 1080);
			
			if (herofirst.getTotalManaCrystals() < 10 && herofirst.getCurrentManaCrystals() < 10)
				mana1Label.setBounds(this.getWidth() * 1314 / 1920,
						this.getHeight() * 40 / 1080,
						this.getWidth() * 100 / 1920,
						this.getHeight() * 100 / 1080);
			else if (herofirst.getTotalManaCrystals() == 10 && herofirst.getCurrentManaCrystals() == 10)
				mana1Label.setBounds(this.getWidth() * 1299 / 1920,
						this.getHeight() * 40 / 1080,
						this.getWidth() * 100 / 1920,
						this.getHeight() * 100 / 1080);
			else
				mana1Label.setBounds(this.getWidth() * 1301 / 1920,
						this.getHeight() * 40 / 1080,
						this.getWidth() * 100 / 1920,
						this.getHeight() * 100 / 1080);
		} else {
			if (herofirst.getTotalManaCrystals() < 10 && herofirst.getCurrentManaCrystals() < 10)
				mana1Label.setBounds(this.getWidth() * 1343 / 1920,
						this.getHeight() * 413 / 1080,
						this.getWidth() * 100 / 1920,
						this.getHeight() * 100 / 1080);
			else if (herofirst.getTotalManaCrystals() == 10 && herofirst.getCurrentManaCrystals() == 10)
				mana1Label.setBounds(this.getWidth() * 1328 / 1920,
						this.getHeight() * 413 / 1080,
						this.getWidth() * 100 / 1920,
						this.getHeight() * 100 / 1080);
			else
				mana1Label.setBounds(this.getWidth() * 1330 / 1920,
						this.getHeight() * 413 / 1080,
						this.getWidth() * 100 / 1920,
						this.getHeight() * 100 / 1080);

			if (herosecond.getTotalManaCrystals() < 10 && herosecond.getCurrentManaCrystals() < 10)
				mana2Label.setBounds(this.getWidth() * 1314 / 1920,
						this.getHeight() * 40 / 1080,
						this.getWidth() * 100 / 1920,
						this.getHeight() * 100 / 1080);
			else if (herosecond.getTotalManaCrystals() == 10 && herosecond.getCurrentManaCrystals() == 10)
				mana2Label.setBounds(this.getWidth() * 1299 / 1920,
						this.getHeight() * 40 / 1080,
						this.getWidth() * 100 / 1920,
						this.getHeight() * 100 / 1080);
			else
				mana2Label.setBounds(this.getWidth() * 1301 / 1920,
						this.getHeight() * 40 / 1080,
						this.getWidth() * 100 / 1920,
						this.getHeight() * 100 / 1080);
		}

		hp1Label.setText(herofirst.getCurrentHP() + "");

		if (herofirst.getCurrentHP() > 9) {
			hp1Label.setBounds(this.getWidth() * 213 / 1920,
					this.getHeight() * 112 / 1080,
					this.getWidth() * 100 / 1920, this.getHeight() * 100 / 1080);
		} else {
			hp1Label.setBounds(this.getWidth() * 220 / 1920,
					this.getHeight() * 112 / 1080,
					this.getWidth() * 100 / 1920, this.getHeight() * 100 / 1080);
		}
		hp2Label.setText(herosecond.getCurrentHP() + "");
		if (herosecond.getCurrentHP() > 9) {
			hp2Label.setBounds(this.getWidth() * 213 / 1920,
					this.getHeight() * 112 / 1080,
					this.getWidth() * 100 / 1920, this.getHeight() * 100 / 1080);
		} else {
			hp2Label.setBounds(this.getWidth() * 220 / 1920,
					this.getHeight() * 112 / 1080,
					this.getWidth() * 100 / 1920, this.getHeight() * 100 / 1080);
		}
		HeroDown.remove(cardsfirst);
		HeroUp.remove(cardssecond);

		if (turn % 2 != 0) {
			if (fronthandneedchange == true)
				cardsfirst = getHeroHand(herofirst);
			if (backhandneedchange == true)
				cardssecond = getHeroHandback(herosecond);
		} else {
			if (fronthandneedchange == true)
				cardsfirst = getHeroHand(herosecond);
			if (backhandneedchange == true)
				cardssecond = getHeroHandback(herofirst);
		}
		HeroDown.add(cardsfirst, new Integer(5));
		HeroUp.add(cardssecond, new Integer(5));

		fronthandneedchange = false;
		backhandneedchange = false;

		getfieldminionsfirst(fieldcardsfirst, herofirst);
		getfieldminionssecond(fieldcardssecond, herosecond);

		this.revalidate();
		this.repaint();

	}

	public void switchView() {


		if (turn % 2 != 0) {
			// meaning the down player is initially the first player to play
			
			HeroUp.removeAll();
			HeroDown.removeAll();
			// we need to add the second player down for next turn (which will become even after incrementing)
			HeroDown.add(hero2pic);
			hero2pic.setBounds(this.getWidth() * 612 / 1382,
					this.getHeight() * 141 / 744, this.getWidth() * 190 / 1382,
					this.getHeight() * 170 / 744);

			HeroUp.add(hero1pic);
			hero1pic.setBounds(this.getWidth() * 613 / 1382,
					this.getHeight() * 87 / 744, this.getWidth() * 190 / 1382,
					this.getHeight() * 170 / 744);
			
			HeroDown.add(mana2Label);
			if (herosecond.getTotalManaCrystals() < 10 && herosecond.getCurrentManaCrystals() < 10)
				mana2Label.setBounds(this.getWidth() * 1343 / 1920,
						this.getHeight() * 413 / 1080,
						this.getWidth() * 100 / 1920,
						this.getHeight() * 100 / 1080);
			else if (herosecond.getTotalManaCrystals() == 10 && herosecond.getCurrentManaCrystals() == 10)
				mana2Label.setBounds(this.getWidth() * 1328 / 1920,
						this.getHeight() * 413 / 1080,
						this.getWidth() * 100 / 1920,
						this.getHeight() * 100 / 1080);
			else
				mana2Label.setBounds(this.getWidth() * 1330 / 1920,
						this.getHeight() * 413 / 1080,
						this.getWidth() * 100 / 1920,
						this.getHeight() * 100 / 1080);

			HeroUp.add(mana1Label);
			
			if (herofirst.getTotalManaCrystals() < 10 && herofirst.getCurrentManaCrystals() < 10)
				mana1Label.setBounds(this.getWidth() * 1314 / 1920,
						this.getHeight() * 40 / 1080,
						this.getWidth() * 100 / 1920,
						this.getHeight() * 100 / 1080);
			else if (herofirst.getTotalManaCrystals() == 10 && herofirst.getCurrentManaCrystals() == 10)
				mana1Label.setBounds(this.getWidth() * 1299 / 1920,
						this.getHeight() * 40 / 1080,
						this.getWidth() * 100 / 1920,
						this.getHeight() * 100 / 1080);
			else
				mana1Label.setBounds(this.getWidth() * 1301 / 1920,
						this.getHeight() * 40 / 1080,
						this.getWidth() * 100 / 1920,
						this.getHeight() * 100 / 1080);

			layerpane.add(power1, new Integer(6));
			power1.setBounds(this.getWidth() * 1134 / 1920,
					this.getHeight() * 150 / 1080,
					this.getWidth() * 100 / 1382, this.getHeight() * 100 / 744);

			layerpane.add(power2, new Integer(6));
			power2.setBounds(this.getWidth() * 1134 / 1920,
					this.getHeight() * 780 / 1080,
					this.getWidth() * 100 / 1382, this.getHeight() * 100 / 744);

			HeroDown.add(deck2Label);
			deck2Label.setBounds(this.getWidth() * 1860 / 1920,
					this.getHeight() * 50 / 1080, this.getWidth() * 100 / 1920,
					this.getHeight() * 100 / 1080);

			HeroUp.add(deck1Label);
			deck1Label
					.setBounds(this.getWidth() * 1860 / 1920,
							this.getHeight() * 300 / 1080,
							this.getWidth() * 100 / 1920,
							this.getHeight() * 100 / 1080);

			fieldcardsfirst.setBounds(8 * this.getWidth() / 1366,
					240 * this.getHeight() / 768, this.getWidth(),
					125 * this.getHeight() / 768);
			fieldcardssecond.setBounds(8 * this.getWidth() / 1366,
					368 * this.getHeight() / 768, this.getWidth(),
					125 * this.getHeight() / 768);

		} else {
			
			HeroUp.removeAll();
			HeroDown.removeAll();

			HeroDown.add(hero1pic);
			hero1pic.setBounds(this.getWidth() * 612 / 1382,
					this.getHeight() * 141 / 744, this.getWidth() * 190 / 1382,
					this.getHeight() * 170 / 744);

			HeroUp.add(hero2pic);
			hero2pic.setBounds(this.getWidth() * 613 / 1382,
					this.getHeight() * 87 / 744, this.getWidth() * 190 / 1382,
					this.getHeight() * 170 / 744);

			if (herofirst.getTotalManaCrystals() < 10 && herofirst.getCurrentManaCrystals() < 10)
				mana1Label.setBounds(this.getWidth() * 1343 / 1920,
						this.getHeight() * 413 / 1080,
						this.getWidth() * 100 / 1920,
						this.getHeight() * 100 / 1080);
			else if (herofirst.getTotalManaCrystals() == 10 && herofirst.getCurrentManaCrystals() == 10)
				mana1Label.setBounds(this.getWidth() * 1328 / 1920,
						this.getHeight() * 413 / 1080,
						this.getWidth() * 100 / 1920,
						this.getHeight() * 100 / 1080);
			else
				mana1Label.setBounds(this.getWidth() * 1330 / 1920,
						this.getHeight() * 413 / 1080,
						this.getWidth() * 100 / 1920,
						this.getHeight() * 100 / 1080);

			HeroDown.add(mana1Label);
			
			if (herosecond.getTotalManaCrystals() < 10 && herosecond.getCurrentManaCrystals() < 10)
				mana2Label.setBounds(this.getWidth() * 1314 / 1920,
						this.getHeight() * 40 / 1080,
						this.getWidth() * 100 / 1920,
						this.getHeight() * 100 / 1080);
			else if (herosecond.getTotalManaCrystals() == 10 && herosecond.getCurrentManaCrystals() == 10)
				mana2Label.setBounds(this.getWidth() * 1299 / 1920,
						this.getHeight() * 40 / 1080,
						this.getWidth() * 100 / 1920,
						this.getHeight() * 100 / 1080);
			else
				mana2Label.setBounds(this.getWidth() * 1301 / 1920,
						this.getHeight() * 40 / 1080,
						this.getWidth() * 100 / 1920,
						this.getHeight() * 100 / 1080);

			HeroUp.add(mana2Label);

			layerpane.add(power2, new Integer(6));
			power2.setBounds(this.getWidth() * 1134 / 1920,
					this.getHeight() * 150 / 1080,
					this.getWidth() * 100 / 1382, this.getHeight() * 100 / 744);

			layerpane.add(power1, new Integer(6));
			power1.setBounds(this.getWidth() * 1134 / 1920,
					this.getHeight() * 780 / 1080,
					this.getWidth() * 100 / 1382, this.getHeight() * 100 / 744);

			HeroUp.add(deck2Label);
			deck2Label
					.setBounds(this.getWidth() * 1860 / 1920,
							this.getHeight() * 300 / 1080,
							this.getWidth() * 100 / 1920,
							this.getHeight() * 100 / 1080);

			HeroDown.add(deck1Label);
			deck1Label.setBounds(this.getWidth() * 1860 / 1920,
					this.getHeight() * 50 / 1080, this.getWidth() * 100 / 1920,
					this.getHeight() * 100 / 1080);

			fieldcardsfirst.setBounds(8 * this.getWidth() / 1366,
					368 * this.getHeight() / 768, this.getWidth(),
					125 * this.getHeight() / 768);
			fieldcardssecond.setBounds(8 * this.getWidth() / 1366,
					240 * this.getHeight() / 768, this.getWidth(),
					125 * this.getHeight() / 768);

		}

		turn++;
	}
	
	public JLayeredPane getHeroHand(Hero hero) {

		JLayeredPane cards = new JLayeredPane();
		cards.addKeyListener(listener);
		cards.setFocusable(true);
		ArrayList<Card> Hand = hero.getHand();
		JButton minion;
		ImageIcon minionIcon;
		Image minionImage;

		for (int i = 0; i < Hand.size(); i++) {
			minion = new JButton();
			minion.addKeyListener(listener);
			minion.setFocusable(true);
			minion.setBounds(((i + 1) * 100 - 60 * i) * this.getWidth() / 1366,
					0, 100 * this.getWidth() / 1366,
					100 * this.getHeight() / 768);
			cards.setBounds((543 - i * 20) * this.getWidth() / 1366,
					300 * this.getHeight() / 768, 700 * this.getWidth() / 1366,
					100 * this.getHeight() / 768);
			if (hero == herofirst)
				minion.setActionCommand("1card" + i + " "
						+ Hand.get(i).getName());
			else
				minion.setActionCommand("2card" + i + " "
						+ Hand.get(i).getName());

			minionIcon = getImageIcon(Hand.get(i));
			minionImage = minionIcon.getImage().getScaledInstance(
					100 * this.getWidth() / 1366, 100 * this.getHeight() / 768,
					java.awt.Image.SCALE_SMOOTH);
			minionIcon = new ImageIcon(minionImage);
			minion.setIcon(minionIcon);
			minion.addMouseListener(listener);
			minion.addMouseMotionListener(listener);
			minion.addActionListener(listener);
			minion.setOpaque(false);
			minion.setBorder(BorderFactory.createEmptyBorder());
			minion.setBackground(Color.RED);
			minion.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
					new ImageIcon("images/down.png").getImage(),
					new Point(0, 0), "custom cursor"));

			minion.setLayout(null);

			JLabel manacost = new JLabel(hero.getHand().get(i).getManaCost()
					+ "");
			manacost.setForeground(Color.WHITE);

			if (hero.getHand().get(i).getManaCost() > 9)
				manacost.setBounds(11 * this.getWidth() / 1920,
						10 * this.getHeight() / 1080,
						20 * this.getWidth() / 1920,
						20 * this.getHeight() / 1080);
			else
				manacost.setBounds(17 * this.getWidth() / 1920,
						10 * this.getHeight() / 1080,
						20 * this.getWidth() / 1920,
						20 * this.getHeight() / 1080);

			manacost.setFont(new Font("Arial", Font.BOLD,
					17 * this.getWidth() / 1920));
			minion.add(manacost);
			cards.addKeyListener(listener);
			cards.setFocusable(true);
			cards.add(minion, new Integer(i));

		}
		return cards;
	}

	public JLayeredPane getHeroHandback(Hero hero) {

		JLayeredPane cards = new JLayeredPane();
		cards.addKeyListener(listener);
		cards.setFocusable(true);
		ArrayList<Card> Hand = hero.getHand();
		JButton minion;

		ImageIcon minionIcon;
		Image minionImage;

		for (int i = 0; i < Hand.size(); i++) {
			minion = new JButton();
			minion.addKeyListener(listener);
			minion.setFocusable(true);
			minion.setBounds(((i + 1) * 100 - 65 * i) * this.getWidth() / 1366,
					0, 100 * this.getWidth() / 1366,
					100 * this.getHeight() / 768);
			cards.setBounds((560 - i * 20) * this.getWidth() / 1366,
					-25 * this.getHeight() / 768, 700 * this.getWidth() / 1366,
					100 * this.getHeight() / 768);
			// if(hero==herofirst)
			// minion.setActionCommand("1card"+i+" " +Hand.get(i).getName());
			// else
			// minion.setActionCommand("2card"+i +" " +Hand.get(i).getName());

			minionIcon = new ImageIcon("images/cardback.png");
			minionImage = minionIcon.getImage().getScaledInstance(
					100 * this.getWidth() / 1366, 100 * this.getHeight() / 768,
					java.awt.Image.SCALE_SMOOTH);
			minionIcon = new ImageIcon(minionImage);
			minion.setIcon(minionIcon);
			// minion.addMouseListener(listener);
			// minion.addMouseMotionListener(listener);
			minion.setOpaque(false);
			minion.setBorder(BorderFactory.createEmptyBorder());
			minion.setBackground(Color.RED);
			minion.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
					new ImageIcon("images/down.png").getImage(),
					new Point(0, 0), "custom cursor"));

			cards.add(minion, new Integer(i));

		}
		return cards;

	}

	public void getfieldminionsfirst(JPanel field, Hero hero) {

		field.removeAll();
		ImageIcon fieldminionicon;
		field.addKeyListener(listener);
		field.setFocusable(true);
		for (int i = 0; i < hero.getField().size(); i++) {

			JButton fieldminion = new JButton();
			fieldminion.addKeyListener(listener);
			fieldminion.setFocusable(true);
			fieldminion
					.setPreferredSize(new Dimension(
							125 * this.getWidth() / 1366, 125 * this
									.getHeight() / 768));
			fieldminion.setActionCommand("1played" + i + " "
					+ hero.getField().get(i).getName());

			fieldminionicon = getImageIcon(hero.getField().get(i));

			Image fieldminionImage = fieldminionicon.getImage()
					.getScaledInstance(125 * this.getWidth() / 1366,
							125 * this.getHeight() / 768,
							java.awt.Image.SCALE_SMOOTH);
			fieldminionicon = new ImageIcon(fieldminionImage);
			fieldminion.setIcon(fieldminionicon);
			fieldminion.setBackground(Color.GREEN);
			fieldminion.setOpaque(false);
			fieldminion.setBorder(BorderFactory.createEmptyBorder());
			fieldminion.addMouseListener(listener);
			fieldminion.addMouseMotionListener(listener);
			fieldminion.addActionListener(listener);
			fieldminion.setLayout(null);

			JLabel attack = new JLabel(hero.getField().get(i).getAttack() + "");
			attack.setForeground(Color.WHITE);

			if (hero.getField().get(i).getAttack() > 9)
				attack.setBounds(17 * this.getWidth() / 1920,
						151 * this.getHeight() / 1080,
						20 * this.getWidth() / 1920,
						20 * this.getHeight() / 1080);
			else
				attack.setBounds(23 * this.getWidth() / 1920,
						151 * this.getHeight() / 1080,
						20 * this.getWidth() / 1920,
						20 * this.getHeight() / 1080);

			attack.setFont(new Font("Arial", Font.BOLD,
					17 * this.getWidth() / 1920));
			fieldminion.add(attack);

			JLabel health = new JLabel(hero.getField().get(i).getCurrentHP()
					+ "");
			health.setForeground(Color.WHITE);

			if (hero.getField().get(i).getCurrentHP() > 9)
				health.setBounds(140 * this.getWidth() / 1920,
						150 * this.getHeight() / 1080,
						20 * this.getWidth() / 1920,
						20 * this.getHeight() / 1080);
			else
				health.setBounds(146 * this.getWidth() / 1920,
						150 * this.getHeight() / 1080,
						20 * this.getWidth() / 1920,
						20 * this.getHeight() / 1080);

			health.setFont(new Font("Arial", Font.BOLD,
					17 * this.getWidth() / 1920));
			fieldminion.add(health);

			JLabel manacost = new JLabel(hero.getField().get(i).getManaCost()
					+ "");
			manacost.setForeground(Color.WHITE);

			if (hero.getField().get(i).getManaCost() > 9)
				manacost.setBounds(15 * this.getWidth() / 1920,
						16 * this.getHeight() / 1080,
						20 * this.getWidth() / 1920,
						20 * this.getHeight() / 1080);
			else
				manacost.setBounds(21 * this.getWidth() / 1920,
						16 * this.getHeight() / 1080,
						20 * this.getWidth() / 1920,
						20 * this.getHeight() / 1080);

			manacost.setFont(new Font("Arial", Font.BOLD,
					17 * this.getWidth() / 1920));

			fieldminion.add(manacost);

			if (hero.getField().get(i).sleeping) {

				ImageIcon sleepingicon = new ImageIcon("images/sleepingz.png");
				Image sleepingimage = sleepingicon.getImage()
						.getScaledInstance(30 * this.getWidth() / 1366,
								30 * this.getHeight() / 768,
								java.awt.Image.SCALE_SMOOTH);
				sleepingicon = new ImageIcon(sleepingimage);
				JLabel sleeping = new JLabel(sleepingicon);
				sleeping.setBounds(120 * this.getWidth() / 1920, 0,
						40 * this.getWidth() / 1920,
						40 * this.getHeight() / 1080);
				fieldminion.add(sleeping);

			}

			if (hero.getField().get(i).isDivine()) {
				ImageIcon divineicon = new ImageIcon("images/cards/divine.png");
				Image divineimage = divineicon.getImage().getScaledInstance(
						125 * this.getWidth() / 1366,
						125 * this.getHeight() / 768,
						java.awt.Image.SCALE_SMOOTH);
				divineicon = new ImageIcon(divineimage);
				JLabel divine = new JLabel(divineicon);
				divine.setBounds(0, 0, 125 * this.getWidth() / 1366,
						125 * this.getHeight() / 768);
				fieldminion.add(divine);
			}
			if ((hero.getField().get(i).isAttacked()) && turn % 2 != 0) {
				ImageIcon attackedicon;
				if (hero.getField().get(i).getRarity().equals(Rarity.LEGENDARY))
					attackedicon = new ImageIcon(
							"images/cards/attacked legendary.png");
				else
					attackedicon = new ImageIcon("images/cards/attacked.png");
				Image attackedimage = attackedicon.getImage()
						.getScaledInstance(125 * this.getWidth() / 1366,
								125 * this.getHeight() / 768,
								java.awt.Image.SCALE_SMOOTH);
				attackedicon = new ImageIcon(attackedimage);
				JLabel attacked = new JLabel(attackedicon);
				attacked.setBounds(0, 0, 125 * this.getWidth() / 1366,
						125 * this.getHeight() / 768);
				fieldminion.add(attacked);
			}
			if (hero.getField().get(i).isTaunt()) {
				ImageIcon taunticon;
				if (hero.getField().get(i).getRarity().equals(Rarity.LEGENDARY))
					taunticon = new ImageIcon(
							"images/cards/taunt legendary.png");
				else
					taunticon = new ImageIcon("images/cards/taunt.png");
				Image tauntimage = taunticon.getImage().getScaledInstance(
						145 * this.getWidth() / 1366,
						140 * this.getHeight() / 768,
						java.awt.Image.SCALE_SMOOTH);
				taunticon = new ImageIcon(tauntimage);
				JLabel taunt = new JLabel(taunticon);
				taunt.setBounds(-14, -10, 145 * this.getWidth() / 1366,
						140 * this.getHeight() / 768);
				fieldminion.add(taunt);
			}

			field.add(fieldminion);
		}

	}

	public void getfieldminionssecond(JPanel field, Hero hero) {

		field.removeAll();
		ImageIcon fieldminionicon;
		field.addKeyListener(listener);
		field.setFocusable(true);
		for (int i = 0; i < hero.getField().size(); i++) {

			JButton fieldminion = new JButton();
			fieldminion.addKeyListener(listener);
			fieldminion.setFocusable(true);
			fieldminion
					.setPreferredSize(new Dimension(
							125 * this.getWidth() / 1366, 125 * this
									.getHeight() / 768));
			fieldminion.setActionCommand("2played" + i + " "
					+ hero.getField().get(i).getName());

			fieldminionicon = getImageIcon(hero.getField().get(i));

			Image fieldminionImage = fieldminionicon.getImage()
					.getScaledInstance(125 * this.getWidth() / 1366,
							125 * this.getHeight() / 768,
							java.awt.Image.SCALE_SMOOTH);
			fieldminionicon = new ImageIcon(fieldminionImage);

			fieldminion.setIcon(fieldminionicon);
			fieldminion.setBackground(Color.GREEN);
			fieldminion.setOpaque(false);
			fieldminion.setBorder(BorderFactory.createEmptyBorder());
			fieldminion.setLayout(null);
			fieldminion.addMouseListener(listener);
			fieldminion.addMouseMotionListener(listener);
			fieldminion.addActionListener(listener);
			
			JLabel attack = new JLabel(hero.getField().get(i).getAttack() + "");
			attack.setForeground(Color.WHITE);

			if (hero.getField().get(i).getAttack() > 9)
				attack.setBounds(17 * this.getWidth() / 1920,
						151 * this.getHeight() / 1080,
						20 * this.getWidth() / 1920,
						20 * this.getHeight() / 1080);
			else
				attack.setBounds(23 * this.getWidth() / 1920,
						151 * this.getHeight() / 1080,
						20 * this.getWidth() / 1920,
						20 * this.getHeight() / 1080);

			attack.setFont(new Font("Arial", Font.BOLD,
					17 * this.getWidth() / 1920));
			fieldminion.add(attack);

			JLabel health = new JLabel(hero.getField().get(i).getCurrentHP()
					+ "");
			health.setForeground(Color.WHITE);

			if (hero.getField().get(i).getCurrentHP() > 9)
				health.setBounds(140 * this.getWidth() / 1920,
						150 * this.getHeight() / 1080,
						20 * this.getWidth() / 1920,
						20 * this.getHeight() / 1080);
			else
				health.setBounds(146 * this.getWidth() / 1920,
						150 * this.getHeight() / 1080,
						20 * this.getWidth() / 1920,
						20 * this.getHeight() / 1080);

			health.setFont(new Font("Arial", Font.BOLD,
					17 * this.getWidth() / 1920));
			fieldminion.add(health);

			JLabel manacost = new JLabel(hero.getField().get(i).getManaCost()
					+ "");
			manacost.setForeground(Color.WHITE);

			if (hero.getField().get(i).getManaCost() > 9)
				manacost.setBounds(15 * this.getWidth() / 1920,
						16 * this.getHeight() / 1080,
						20 * this.getWidth() / 1920,
						20 * this.getHeight() / 1080);
			else
				manacost.setBounds(21 * this.getWidth() / 1920,
						16 * this.getHeight() / 1080,
						20 * this.getWidth() / 1920,
						20 * this.getHeight() / 1080);

			manacost.setFont(new Font("Arial", Font.BOLD,
					17 * this.getWidth() / 1920));

			fieldminion.add(manacost);
			if (hero.getField().get(i).sleeping) {

				ImageIcon sleepingicon = new ImageIcon("images/sleepingz.png");
				Image sleepingimage = sleepingicon.getImage()
						.getScaledInstance(30 * this.getWidth() / 1366,
								30 * this.getHeight() / 768,
								java.awt.Image.SCALE_SMOOTH);
				sleepingicon = new ImageIcon(sleepingimage);
				JLabel sleeping = new JLabel(sleepingicon);
				sleeping.setBounds(120, 0, 40, 40);
				fieldminion.add(sleeping);

			}
			if (hero.getField().get(i).isDivine()) {
				ImageIcon divineicon = new ImageIcon("images/cards/divine.png");
				Image divineimage = divineicon.getImage().getScaledInstance(
						125 * this.getWidth() / 1366,
						125 * this.getHeight() / 768,
						java.awt.Image.SCALE_SMOOTH);
				divineicon = new ImageIcon(divineimage);
				JLabel divine = new JLabel(divineicon);

				divine.setBounds(0, 0, 125 * this.getWidth() / 1366,
						125 * this.getHeight() / 768);
				fieldminion.add(divine);
			}
			if ((hero.getField().get(i).isAttacked()) && turn % 2 == 0) {
				ImageIcon attackedicon;
				if (hero.getField().get(i).getRarity().equals(Rarity.LEGENDARY))
					attackedicon = new ImageIcon(
							"images/cards/attacked legendary.png");
				else
					attackedicon = new ImageIcon("images/cards/attacked.png");
				Image attackedimage = attackedicon.getImage()
						.getScaledInstance(125 * this.getWidth() / 1366,
								125 * this.getHeight() / 768,
								java.awt.Image.SCALE_SMOOTH);
				attackedicon = new ImageIcon(attackedimage);
				JLabel attacked = new JLabel(attackedicon);
				attacked.setBounds(0, 0, 125 * this.getWidth() / 1366,
						125 * this.getHeight() / 768);
				fieldminion.add(attacked);
			}
			if (hero.getField().get(i).isTaunt()) {
				ImageIcon taunticon;
				if (hero.getField().get(i).getRarity().equals(Rarity.LEGENDARY))
					taunticon = new ImageIcon(
							"images/cards/taunt legendary.png");
				else
					taunticon = new ImageIcon("images/cards/taunt.png");
				Image tauntimage = taunticon.getImage().getScaledInstance(
						145 * this.getWidth() / 1366,
						140 * this.getHeight() / 768,
						java.awt.Image.SCALE_SMOOTH);
				taunticon = new ImageIcon(tauntimage);
				JLabel taunt = new JLabel(taunticon);
				taunt.setBounds(-14, -10, 145 * this.getWidth() / 1366,
						140 * this.getHeight() / 768);
				fieldminion.add(taunt);
			}

			field.add(fieldminion);
		}

	}

	public ImageIcon getImageIcon(Card card) {
		if (card.getName().equalsIgnoreCase("Shadow Word: Death"))
			return new ImageIcon("images/cards/card Shadow Word Death.png");
		else
			return new ImageIcon("images/cards/card " + card.getName() + ".png");
	}

	public JButton createButton(String name) {
		JButton a = new JButton(name);

		a.setHorizontalTextPosition(JButton.CENTER);
		a.setVerticalTextPosition(JButton.CENTER);
		a.setActionCommand(name);
		a.setBackground(Color.ORANGE);
		a.setOpaque(false);
		a.setFont(new Font("Arial", Font.BOLD, this.getWidth() / 7));

		ImageIcon icon = new ImageIcon("images/button.png");

		Image newimg = icon.getImage().getScaledInstance(
				360 * this.getWidth() / 1920, 90 * this.getHeight() / 1080,
				java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);
		a.setIcon(icon);

		a.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("images/down.png").getImage(), new Point(0, 0),
				"custom cursor"));

		return a;
	}

	public void setPauseWindow() {
		windowframe = new JFrame();
		windowframe.addKeyListener(listener);
		windowframe.setFocusable(true);
		windowframe.setVisible(false);
		windowframe.setUndecorated(true);
		ImageIcon gameicon = new ImageIcon("images/icon.png");
		windowframe.setIconImage(gameicon.getImage());
		windowframe.setBounds(0, 0, this.getWidth(), this.getHeight());
		windowframe.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("images/hand.png").getImage(), new Point(0, 0),
				"custom cursor"));

		ImageIcon back = new ImageIcon("images/pause menu.png");
		Image newback = back.getImage().getScaledInstance(this.getWidth(),
				this.getHeight(), java.awt.Image.SCALE_SMOOTH);
		back = new ImageIcon(newback);
		JLabel backadded = new JLabel(back);
		backadded.addKeyListener(listener);
		backadded.setFocusable(true);
		windowframe.setContentPane(backadded);

		// JPanel PauseWindow = new JPanel(new BorderLayout());
		// PauseWindow.setPreferredSize(new Dimension(this.getWidth(), this
		// .getHeight()));
		// PauseWindow.setOpaque(false);

		int Width = 360 * this.getWidth() / 1920;
		int Height = 90 * this.getHeight() / 1080;
		int PositionWidth = this.getWidth() / 2 - Width / 2;
		int PositionHeight = this.getHeight() / 2 - Height / 2;

		

		// music??

		// JButton Play = createButton("Play music");
		// Play.setBounds(PositionWidth + Width / 8, PositionHeight - 2
		// * (90 * this.getHeight() / 1080), Width / 4, Height);
		// Play.setActionCommand("Play");
		// Play.addActionListener(listener);
		// Play.setBackground(Color.ORANGE);
		// Play.setOpaque(false);
		// ImageIcon playicon = new ImageIcon("images/play.png");
		// Image newplayicon = playicon.getImage().getScaledInstance(Width / 4,
		// Width / 4, java.awt.Image.SCALE_SMOOTH);
		// playicon = new ImageIcon(newplayicon);
		// Play.setIcon(playicon);
		// Play.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
		// new ImageIcon("images/down.png").getImage(), new Point(0, 0),
		// "custom cursor"));
		// windowframe.add(Play);
		//
		// JButton Stop = createButton("Stop music");
		// Stop.setBounds(PositionWidth + 5 * Width / 8, PositionHeight - 2
		// * (90 * this.getHeight() / 1080), Width / 4, Height);
		// Stop.setActionCommand("Stop");
		// Stop.addActionListener(listener);
		// Stop.setBackground(Color.ORANGE);
		// Stop.setOpaque(false);
		// ImageIcon stopicon = new ImageIcon("images/stop.png");
		// Image newstopicon = stopicon.getImage().getScaledInstance(Width / 4,
		// Width / 4, java.awt.Image.SCALE_SMOOTH);
		// stopicon = new ImageIcon(newstopicon);
		// Stop.setIcon(stopicon);
		//
		// Stop.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
		// new ImageIcon("images/down.png").getImage(), new Point(0, 0),
		// "custom cursor"));
		// windowframe.add(Stop);

		// Resume
		JButton Resume = createButton("Resume");
		Resume.setFont(new Font("Arial", Font.BOLD, this.getWidth() / 60));
		Resume.setBounds(PositionWidth,
				PositionHeight - 2 * (90 * this.getHeight() / 1080), Width,
				Height);
		Resume.setActionCommand("Resume");
		Resume.addActionListener(listener);
		Resume.addKeyListener(listener);
		Resume.setFocusable(true);
		windowframe.add(Resume);

		// Main Menu
		JButton MainMenu = createButton("Main Menu");
		MainMenu.setFont(new Font("Arial", Font.BOLD, this.getWidth() / 60));
		MainMenu.setBounds(PositionWidth, PositionHeight, Width, Height);
		MainMenu.setActionCommand("Back 2");
		MainMenu.addActionListener(listener);
		MainMenu.addKeyListener(listener);
		MainMenu.setFocusable(true);
		windowframe.add(MainMenu);

		// New Game
		JButton StartNewGame = createButton("Restart Game");
		StartNewGame
				.setFont(new Font("Arial", Font.BOLD, this.getWidth() / 60));
		StartNewGame.setBounds(PositionWidth, PositionHeight
				- (90 * this.getHeight() / 1080), Width, Height);
		StartNewGame.setActionCommand("Play 2");
		StartNewGame.addActionListener(listener);
		StartNewGame.addKeyListener(listener);
		StartNewGame.setFocusable(true);
		windowframe.add(StartNewGame);
		
		
		// minimize
				JButton Minimize = createButton("Minimize");
				Minimize.setFont(new Font("Arial", Font.BOLD, this.getWidth() / 60));
				Minimize.setBounds(PositionWidth,
						PositionHeight + (90 * this.getHeight() / 1080), Width, Height);
				
				Minimize.setActionCommand("Minimize");
				Minimize.addActionListener(listener);
				Minimize.addKeyListener(listener);
				Minimize.setFocusable(true);
				windowframe.add(Minimize);

		// Exit
		JButton Exit = createButton("Exit");
		Exit.setFont(new Font("Arial", Font.BOLD, this.getWidth() / 60));
		Exit.setActionCommand("Exit");
		Exit.addActionListener(listener);
		Exit.setBounds(PositionWidth,
				PositionHeight + 2 * (90 * this.getHeight() / 1080), Width,
				Height);
		Exit.addKeyListener(listener);
		Exit.setFocusable(true);
		windowframe.add(Exit);
	}
	
	public void playSound(String filepath) {
		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(new File(filepath).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();

		} catch (UnsupportedAudioFileException | IOException
				| LineUnavailableException e) {
			e.printStackTrace();}
		}
	
	public void Gameover() {
		JFrame exitframe = new JFrame();
		exitframe.dispose();
		exitframe.setUndecorated(true);
		exitframe.setLayout(new BorderLayout());
		exitframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		exitframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
		exitframe.setTitle("HearthStone");
		Hero winnerh;
		if (herofirst.getCurrentHP() == 0)
			winnerh = herosecond;
		else
			winnerh = herofirst;
		Hero loserh;
		if(winnerh == herofirst)
			loserh = herosecond;
		else
			loserh = herofirst;
		playSound("sounds/"
				+ loserh.getClass()
						.getSimpleName() + " death.wav");
		JLayeredPane exitpane = new JLayeredPane();
		exitpane.setPreferredSize(new Dimension(this.getWidth(), this
				.getHeight()));
		exitpane.setLayout(null);
		exitpane.addKeyListener(listener);
		exitpane.setFocusable(true);
		exitframe.add(exitpane);
		Rectangle rec = new Rectangle(0, 0, getWidth(), getHeight());
		Robot robot;
		Image exitimage;
		ImageIcon temp = new ImageIcon("images/pause menu.png");
		exitimage = temp.getImage().getScaledInstance(this.getWidth(),
				this.getHeight(), java.awt.Image.SCALE_SMOOTH);

		ImageIcon Exiticon = new ImageIcon(exitimage);
		JLabel exitlabel = new JLabel(Exiticon);
		exitlabel.setBounds(0, 0, getWidth(), getHeight());
		exitpane.add(exitlabel, 0);
		
		ImageIcon Winnericon = new ImageIcon("images/"
				+ winnerh.getClass().getSimpleName() + " winner 2.png");
		Image WinnerImage = Winnericon.getImage().getScaledInstance(
				600 * getWidth() / 1920, 600 * getHeight() / 1080,
				java.awt.Image.SCALE_SMOOTH);
		Winnericon = new ImageIcon(WinnerImage);
		JLabel winner = new JLabel(Winnericon);
		winner.setBounds(660 * getWidth() / 1920, 240 * getHeight() / 1080,
				600 * getWidth() / 1920, 600 * getHeight() / 1080);
		exitpane.add(winner, new Integer(3));
		JButton Back = new JButton("Main Menu");
		Back.setActionCommand("Back 2");
		Back.setHorizontalTextPosition(JButton.CENTER);
		Back.setVerticalTextPosition(JButton.CENTER);
		Back.addActionListener(listener);
		Back.setBackground(Color.ORANGE);
		Back.setFont(new Font("Arial", Font.BOLD, getWidth() / 12 / 7));
		ImageIcon icon = new ImageIcon("images/button.png");
		Image newimg2 = icon.getImage().getScaledInstance(getWidth() / 12,
				getHeight() / 17, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg2);
		Back.setIcon(icon);
		Back.setOpaque(false);
		Back.setBounds(0, 0, getWidth() / 12, getHeight() / 17);
		Back.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("images/down.png").getImage(), new Point(0, 0),
				"custom cursor"));
		exitpane.add(Back, new Integer(4));
		ImageIcon gameicon = new ImageIcon("images/icon.png");
		exitframe.setIconImage(gameicon.getImage());
		exitframe.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("images/hand.png").getImage(), new Point(0, 0),
				"custom cursor"));
		revalidate();
		repaint();
		setVisible(false);
		
		exitframe.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Controller();	
	}

}
