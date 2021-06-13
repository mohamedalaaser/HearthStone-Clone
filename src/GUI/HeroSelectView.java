package GUI;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import model.heroes.Hero;

@SuppressWarnings({ "serial", "unused" })
public class HeroSelectView extends JFrame {

	JButton Select;
	JPanel up;
	JPanel center;
	JLabel frame;
	JPanel down;
	JButton Play;
	JPanel centerVs;

	public HeroSelectView(Controller listener) {
		super();
		this.dispose();
		this.setUndecorated(true); // change this to true in the end
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setTitle("HearthStone");
		this.setVisible(true);
		ImageIcon img = new ImageIcon("images/backgroundmenu.png");
		Image newimg = img.getImage().getScaledInstance(this.getWidth(),
				this.getHeight(), java.awt.Image.SCALE_SMOOTH);
		img = new ImageIcon(newimg);
		frame = new JLabel(img);
		this.add(frame);
		frame.setLayout(new GridLayout(0, 1));

		ImageIcon gameicon = new ImageIcon("images/icon.png");
		this.setIconImage(gameicon.getImage());

		this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("images/hand.png").getImage(), new Point(0, 0),
				"custom cursor"));

		// ------------for back button--------------//

		up = new JPanel();
		up.setPreferredSize(new Dimension(this.getWidth(), this.getHeight() / 3));
		up.setOpaque(false);
		up.setLayout(null);
		frame.add(up);
		up.setBackground(Color.BLACK);

		JButton Back = new JButton("< Back");
		Back.setActionCommand("Back");
		Back.setHorizontalTextPosition(JButton.CENTER);
		Back.setVerticalTextPosition(JButton.CENTER);
		Back.addActionListener(listener);
		Back.setBackground(Color.ORANGE);
		Back.setFont(new Font("Arial", Font.BOLD, this.getWidth() / 12 / 7));
		ImageIcon icon = new ImageIcon("images/button.png");
		Image newimg2 = icon.getImage().getScaledInstance(this.getWidth() / 12,
				this.getHeight() / 17, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg2);
		Back.setIcon(icon);
		Back.setOpaque(false);
		Back.setBounds(0, 0, this.getWidth() / 12, this.getHeight() / 17);
		Back.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("images/down.png").getImage(), new Point(0, 0),
				"custom cursor"));
		up.add(Back);

		center = new JPanel();
		center.setPreferredSize(new Dimension(this.getWidth(),
				this.getHeight() / 3));
		center.setOpaque(false);
		center.setBackground(Color.DARK_GRAY);
		center.setLayout(new GridBagLayout());
		frame.add(center);

		int width = this.getWidth();
		int height = this.getHeight();

		JButton Mage = addheroes("Mage", width, height);
		Mage.addActionListener(listener);
		center.add(Mage);

		JButton Hunter = addheroes("Hunter", width, height);
		Hunter.addActionListener(listener);
		center.add(Hunter);

		JButton Paladin = addheroes("Paladin", width, height);
		Paladin.addActionListener(listener);
		center.add(Paladin);

		JButton Priest = addheroes("Priest", width, height);
		Priest.addActionListener(listener);
		center.add(Priest);

		JButton Warlock = addheroes("Warlock", width, height);
		Warlock.addActionListener(listener);
		center.add(Warlock);

		down = new JPanel();
		down.setBackground(Color.BLUE);
		down.setOpaque(false);
		down.setPreferredSize(new Dimension(this.getWidth(),
				this.getHeight() / 3));
		down.setLayout(new GridBagLayout());
		frame.add(down);

		Select = new JButton("Select First Hero");

		Select.setHorizontalTextPosition(JButton.CENTER);
		Select.setVerticalTextPosition(JButton.CENTER);
		Select.setActionCommand("Select First Hero");
		Select.setBackground(Color.ORANGE);
		Select.setOpaque(false);
		Select.setFont(new Font("Arial", Font.BOLD, this.getWidth() / 49));
		Select.setPreferredSize(new Dimension(this.getWidth() / 4, this
				.getHeight() / 12));
		ImageIcon icon1 = new ImageIcon("images/button.png");

		Image newimg1 = icon1.getImage().getScaledInstance(this.getWidth() / 4,
				this.getHeight() / 12, java.awt.Image.SCALE_SMOOTH);
		icon1 = new ImageIcon(newimg1);
		Select.setIcon(icon1);
		Select.addActionListener(listener);
		Select.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("images/down.png").getImage(), new Point(0, 0),
				"custom cursor"));
		down.add(Select);

		Play = new JButton();
		Play.addActionListener(listener);
		Play.setActionCommand("Play");
		Play.setOpaque(false);
		Play.setPreferredSize(new Dimension(this.getWidth() / 4, this
				.getHeight() / 12));
		ImageIcon icon3 = new ImageIcon("images/playbutton.png");

		Image newimg3 = icon3.getImage().getScaledInstance(this.getWidth() / 4,
				this.getHeight() / 12, java.awt.Image.SCALE_SMOOTH);
		icon3 = new ImageIcon(newimg3);
		Play.setIcon(icon3);
		Play.setBackground(Color.ORANGE);
		Play.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("images/down.png").getImage(), new Point(0, 0),
				"custom cursor"));

		centerVs = new JPanel();
		centerVs.setPreferredSize(new Dimension(this.getWidth(), this
				.getHeight() / 3));
		centerVs.setOpaque(false);
		centerVs.setBackground(Color.DARK_GRAY);
		centerVs.setLayout(new GridBagLayout());

		this.revalidate();
		this.repaint();

	}

	public JButton addheroes(String name, int width, int height) {
		JButton a = new JButton();

		a.setActionCommand(name);
		a.setBackground(Color.ORANGE);
		a.setPreferredSize(new Dimension(width / 6, height / 3));
		ImageIcon iconmage = new ImageIcon("images/" + name + ".png");
		Image newimgmage = iconmage.getImage().getScaledInstance(width / 6,
				height / 3, java.awt.Image.SCALE_SMOOTH);
		iconmage = new ImageIcon(newimgmage);
		a.setIcon(iconmage);
		a.setOpaque(false);
		a.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("images/down.png").getImage(), new Point(0, 0),
				"custom cursor"));
		return a;
	}

}
