package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import engine.Game;
import engine.GameListener;
import exceptions.CannotAttackException;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughManaException;
import exceptions.NotSummonedException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;
import model.cards.Card;
import model.cards.minions.Icehowl;
import model.cards.minions.Minion;
import model.cards.spells.*;
import model.heroes.*;

public class Controller implements ActionListener, GameListener, MouseListener,
		MouseMotionListener, KeyListener {

	private MainMenuView MainMenu;
	private HeroSelectView HeroSelect;
	private GameView GameView;

	private JButton SelectedHero;
	private JButton SelectedHerotemp;
	private JButton SelectedHerotemp2;

	private Hero h1;
	private Hero h2;
	private Game game;

	boolean paused = false;
	JLabel temp;
	boolean carddisplayed;

	JButton attacker;
	JButton target;
	Minion attackerm;
	Minion targetm;
	Hero attackerh;
	Hero targeth;
	LeechingSpell Leeching;
	MinionTargetSpell MinionTarget;
	HeroTargetSpell HeroTarget;
	boolean success = false;

	boolean powermage = false;
	boolean powerpriest = false;

	Timer timer;
	Clip clip2;

	public Controller() {

		HeroSelect = new HeroSelectView(this);
		HeroSelect.setVisible(false);
		MainMenu = new MainMenuView(this);
		MainMenu.setVisible(true);

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
			e.printStackTrace();
		}
	}

	public void playSoundextra(String filepath) {
		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(new File(filepath).getAbsoluteFile());
			clip2 = AudioSystem.getClip();
			clip2.open(audioInputStream);
			clip2.start();

		} catch (UnsupportedAudioFileException | IOException
				| LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (clip2 != null)
			clip2.stop();

		if (e.getSource().getClass().getSimpleName()
				.equalsIgnoreCase("JButton")) {
			JButton b = (JButton) (e.getSource());
			if (b.getActionCommand().equals("Exit")) {
				playSound("sounds/buttonclick.wav");
				System.exit(0);

			} else if (b.getActionCommand().contains("Start Match")) {

				playSound("sounds/buttonclick.wav");

				HeroSelect.setVisible(true);
				MainMenu.setVisible(false);

			} else if (b.getActionCommand().equals("Minimize")) {
				playSound("sounds/buttonclick.wav");
				GameView.windowframe.setState(JFrame.ICONIFIED);

			} else if (b.getActionCommand().contains("Back")) {
				playSound("sounds/buttonclick.wav");
				if (GameView != null && GameView.windowframe.isVisible()) {
					GameView.windowframe.setVisible(false);
				}
				MainMenu.setVisible(true);
				HeroSelect.setVisible(false);
				HeroSelect.Select.setText("Select First Hero");
				HeroSelect.Select.setActionCommand("Select First Hero");
				HeroSelect.frame.removeAll();
				HeroSelect.down.removeAll();
				HeroSelect.centerVs.removeAll();
				HeroSelect.down.add(HeroSelect.Select);
				HeroSelect.frame.add(HeroSelect.up);
				HeroSelect.frame.add(HeroSelect.center);
				HeroSelect.frame.add(HeroSelect.down);

				if (SelectedHero != null) {
					SelectedHero.setOpaque(false);
					SelectedHero = null;
				}
				if (SelectedHerotemp != null) {
					SelectedHerotemp.setOpaque(false);
					SelectedHerotemp = null;
				}
				if (SelectedHerotemp2 != null) {
					SelectedHerotemp2.setOpaque(false);
					SelectedHerotemp2 = null;
				}

				if (b.getActionCommand().contains("2")) {
					try {
						AudioInputStream audioInputStream = AudioSystem
								.getAudioInputStream(new File(
										"sounds/Main_Title.wav")
										.getAbsoluteFile());
						MainMenu.clip = AudioSystem.getClip();
						MainMenu.clip.open(audioInputStream);
						MainMenu.clip.loop(Clip.LOOP_CONTINUOUSLY);
						Thread.sleep(100);

					} catch (Exception e55) {
						e55.printStackTrace();
					}

					GameView.clip.stop();
				}

			} else if (b.getActionCommand().equals("Mage")) {

				playSoundextra("sounds/Jaina Proudmoore.wav");
				if (SelectedHero == null) {
					b.setOpaque(true);
					SelectedHero = b;
				} else {
					SelectedHero.setOpaque(false);
					b.setOpaque(true);
					SelectedHero = b;
				}

			} else if (b.getActionCommand().equals("Hunter")) {

				playSoundextra("sounds/Rexxar.wav");
				if (SelectedHero == null) {
					b.setOpaque(true);
					SelectedHero = b;
				} else {
					SelectedHero.setOpaque(false);
					b.setOpaque(true);
					SelectedHero = b;
				}
			} else if (b.getActionCommand().equals("Paladin")) {

				playSoundextra("sounds/Uther LightBringer.wav");
				if (SelectedHero == null) {
					b.setOpaque(true);
					SelectedHero = b;
				} else {
					SelectedHero.setOpaque(false);
					b.setOpaque(true);
					SelectedHero = b;
				}
			} else if (b.getActionCommand().equals("Priest")) {

				playSoundextra("sounds/Anduin Wyrnn.wav");
				if (SelectedHero == null) {
					b.setOpaque(true);
					SelectedHero = b;
				} else {
					SelectedHero.setOpaque(false);
					b.setOpaque(true);
					SelectedHero = b;
				}
			} else if (b.getActionCommand().equals("Warlock")) {
				playSoundextra("sounds/Gul'dan.wav");
				if (SelectedHero == null) {
					b.setOpaque(true);
					SelectedHero = b;
				} else {
					SelectedHero.setOpaque(false);
					b.setOpaque(true);
					SelectedHero = b;
				}
			}

			else if (b.getActionCommand().equals("Select First Hero")) {
				playSound("sounds/buttonclick.wav");
				if (SelectedHero != null) {
					b.setText("Select Second Hero");
					b.setActionCommand("Select Second Hero");
					SelectedHerotemp = SelectedHero;
					SelectedHero.setOpaque(false);
					SelectedHero = null;
				}
			} else if (b.getActionCommand().equals("Select Second Hero")) {
				playSound("sounds/buttonclick.wav");
				if (SelectedHero != null) {
					SelectedHerotemp2 = SelectedHero;
					HeroSelect.frame.remove(HeroSelect.center);
					HeroSelect.frame.remove(HeroSelect.down);
					HeroSelect.down.remove(HeroSelect.Select);
					HeroSelect.down.add(HeroSelect.Play);
					HeroSelect.frame.add(HeroSelect.centerVs);
					HeroSelect.frame.add(HeroSelect.down);

					JLabel ht1 = new JLabel(SelectedHerotemp.getIcon());
					ht1.setSize(SelectedHerotemp.getSize());
					HeroSelect.centerVs.add(ht1);

					ImageIcon vspicicon = new ImageIcon("images/vspic.png");
					Image vsimg = vspicicon.getImage().getScaledInstance(
							ht1.getIcon().getIconWidth(),
							ht1.getIcon().getIconHeight() - 50,
							java.awt.Image.SCALE_SMOOTH);
					vspicicon = new ImageIcon(vsimg);

					JLabel vspic = new JLabel(vspicicon);
					vspic.setOpaque(false);
					HeroSelect.centerVs.add(vspic);
					JLabel ht2 = new JLabel(SelectedHerotemp2.getIcon());
					ht2.setSize(SelectedHerotemp2.getSize());
					HeroSelect.centerVs.add(ht2);

				}

			} else if (b.getActionCommand().contains("Play")) {
				playSound("sounds/buttonclick.wav");
				if (game != null) {
					GameView.getClip().close();
					GameView.windowframe.setVisible(false);
					GameView.windowframe = null;
					GameView = null;
					game = null;
				}

				HeroSelect.setVisible(false);

				try {
					playgame();
				} catch (FullHandException | IOException
						| CloneNotSupportedException e1) {
					e1.printStackTrace();
				}
				MainMenu.getClip().stop();
				// if (b.getActionCommand().equals("Play")) {
				// playSound("sounds/" + game.getCurrentHero().getName()
				// + ".wav");
				// try {
				// Thread.sleep(1000);
				// } catch (InterruptedException e1) {
				// e1.printStackTrace();
				// }
				// playSound("sounds/Vs.wav");
				// try {
				// Thread.sleep(1000);
				// } catch (InterruptedException e1) {
				// e1.printStackTrace();
				// }
				// playSound("sounds/" + game.getOpponent().getName() + ".wav");
				// }

			} else if (b.getActionCommand().equals("End turn")) {
				playSound("sounds/buttonclick.wav");

				try {
					if (attacker != null)
						attacker.setOpaque(false);
					attacker = null;
					attackerh = null;
					attackerm = null;

					if (target != null)
						target.setOpaque(false);
					target = null;
					targeth = null;
					targetm = null;

					Leeching = null;
					MinionTarget = null;
					HeroTarget = null;

					powermage = false;
					powerpriest = false;

					GameView.bubbled.setVisible(false);
					GameView.bubbleu.setVisible(false);
					GameView.burned.setVisible(false);
					if (timer != null)
						timer.stop();

					GameView.switchView();

					game.getCurrentHero().endTurn();
					GameView.backhandneedchange = true;
					GameView.fronthandneedchange = true;

					GameView.refreshView();

					if (attacker != null)
						attacker.setOpaque(false);
					attacker = null;
					attackerh = null;
					attackerm = null;
					if (target != null)
						target.setOpaque(false);
					target = null;
					targeth = null;
					targetm = null;
					Leeching = null;
					MinionTarget = null;
					HeroTarget = null;

				} catch (FullHandException e2) {
					playSoundextra("sounds/"
							+ game.getCurrentHero().getClass().getSimpleName()
							+ " fullhand.wav");
					burndisplay(e2);
				} catch (CloneNotSupportedException e3) {

					JOptionPane.showMessageDialog(GameView,
							"opala clone not suported");
				}

			} else if (b.getActionCommand().length() > 4
					&& b.getActionCommand().substring(0, 4).equals("hero")
					&& attacker != null) {

				if (b.getActionCommand().equals("hero1")) {
					targeth = GameView.herofirst;
					target = b;
				} else {
					targeth = GameView.herosecond;
					target = b;
				}

				if (HeroTarget != null || powermage == true
						|| powerpriest == true) {
					try {

						heroattack();

						GameView.fronthandneedchange = true;
						GameView.backhandneedchange = false;

						GameView.refreshView();

						GameView.bubbled.setVisible(false);
						GameView.bubbleu.setVisible(false);
						GameView.burned.setVisible(false);
						if (timer != null)
							timer.stop();
						
						success = true;
					} catch (NotYourTurnException e2) {
						attacker.setOpaque(false);
						attacker = null;
						attackerh = null;
						attackerm = null;

						if (target != null)
							target.setOpaque(false);
						target = null;
						targeth = null;
						targetm = null;

						Leeching = null;
						MinionTarget = null;
						HeroTarget = null;

						powermage = false;
						powerpriest = false;

						GameView.bubbleu.setText(e2.getMessage());
						GameView.bubbleu.setVisible(true);

						if (timer != null)
							timer.stop();
						timer = new Timer(4000, this);
						timer.start();
					}

					catch (NotEnoughManaException | InvalidTargetException
							| HeroPowerAlreadyUsedException | FullHandException
							| FullFieldException | CloneNotSupportedException e4) {
						if (e4.getClass().getSimpleName()
								.equals("NotEnoughManaException"))
							playSoundextra("sounds/"
									+ game.getCurrentHero().getClass()
											.getSimpleName()
									+ " notenoughmana.wav");
						if (e4.getClass().getSimpleName()
								.equals("InvalidTargetException"))
							playSoundextra("sounds/"
									+ game.getCurrentHero().getClass()
											.getSimpleName()
									+ " invalidtarget.wav");
						if (e4.getClass().getSimpleName()
								.equals("HeroPowerAlreadyUsedException"))
							playSoundextra("sounds/"
									+ game.getCurrentHero().getClass()
											.getSimpleName()
									+ " heroattacked.wav");
						if (e4.getClass().getSimpleName()
								.equals("FullHandException"))
							playSoundextra("sounds/"
									+ game.getCurrentHero().getClass()
											.getSimpleName() + " fullHand.wav");
						if (e4.getClass().getSimpleName()
								.equals("FullFieldException"))
							playSoundextra("sounds/"
									+ game.getCurrentHero().getClass()
											.getSimpleName() + " fullfield.wav");

						attacker.setOpaque(false);
						attacker = null;
						attackerh = null;
						attackerm = null;

						if (target != null)
							target.setOpaque(false);
						target = null;
						targeth = null;
						targetm = null;

						Leeching = null;
						MinionTarget = null;
						HeroTarget = null;

						powermage = false;
						powerpriest = false;

						GameView.bubbled.setText(e4.getMessage());
						GameView.bubbled.setVisible(true);

						if (timer != null)
							timer.stop();
						timer = new Timer(4000, this);
						timer.start();
					}
					if (success){
						playSoundextra("sounds/Spell.wav");
						success = false;
					}

				}

				else if (attacker.getActionCommand().length() > 7
						&& attacker.getActionCommand().substring(1, 7)
								.equals("played")) {
					try {
						minionattackh();

						GameView.fronthandneedchange = true;
						GameView.backhandneedchange = false;

						GameView.refreshView();

						GameView.bubbled.setVisible(false);
						GameView.bubbleu.setVisible(false);
						GameView.burned.setVisible(false);
						if (timer != null)
							timer.stop();
						success = true;

					} catch (NotYourTurnException e7) {
						attacker.setOpaque(false);
						attacker = null;
						attackerh = null;
						attackerm = null;

						if (target != null)
							target.setOpaque(false);
						target = null;
						targeth = null;
						targetm = null;

						Leeching = null;
						MinionTarget = null;
						HeroTarget = null;

						GameView.bubbleu.setText(e7.getMessage());
						GameView.bubbleu.setVisible(true);

						if (timer != null)
							timer.stop();
						timer = new Timer(4000, this);
						timer.start();
					}

					catch (CannotAttackException | TauntBypassException
							| InvalidTargetException | NotSummonedException e5) {

						if (e5.getClass().getSimpleName()
								.equals("CannotAttackException"))
							if (game.getCurrentHero()
									.getField()
									.get(Integer.parseInt(String
											.valueOf(attacker
													.getActionCommand().charAt(
															7)))).isSleeping())
								playSoundextra("sounds/"
										+ game.getCurrentHero().getClass()
												.getSimpleName()
										+ " sleeping.wav");
							else
								playSound("sounds/"
										+ game.getCurrentHero().getClass()
												.getSimpleName()
										+ " minionattacked.wav");
						if (e5.getClass().getSimpleName()
								.equals("TauntBypassException"))
							playSoundextra("sounds/"
									+ game.getCurrentHero().getClass()
											.getSimpleName() + " taunt.wav");
						if (e5.getClass().getSimpleName()
								.equals("InvalidTargetException"))
							playSoundextra("sounds/"
									+ game.getCurrentHero().getClass()
											.getSimpleName()
									+ " invalid target.wav");

						attacker.setOpaque(false);
						attacker = null;
						attackerh = null;
						attackerm = null;

						if (target != null)
							target.setOpaque(false);
						target = null;
						targeth = null;
						targetm = null;

						Leeching = null;
						MinionTarget = null;
						HeroTarget = null;

						GameView.bubbled.setText(e5.getMessage());
						GameView.bubbled.setVisible(true);

						if (timer != null)
							timer.stop();
						timer = new Timer(4000, this);
						timer.start();
					}
					if(success){
						playSoundextra("sounds/Minion attack.wav");
						success = false;
					}
				}

			} else if (b.getActionCommand().equals("Pause")) {
				playSoundextra("sounds/pausemenuopen.wav");
				// GameView.endgame(h1);
				GameView.windowframe.setVisible(true);
				GameView.setVisible(false);
			} else if (b.getActionCommand().equals("Resume")) {
				playSoundextra("sounds/pausemenuclose.wav");
				GameView.clip.start();
				paused = false;
				GameView.windowframe.dispose();
				GameView.windowframe.setVisible(false);
				GameView.setVisible(true);
			}

			// --------------------------------------------------------------------------------//

			if (b == attacker) {
				attacker.setOpaque(false);
				attacker = null;
				attackerh = null;
				attackerm = null;

				if (target != null)
					target.setOpaque(false);
				target = null;
				targeth = null;
				targetm = null;

				Leeching = null;
				MinionTarget = null;
				HeroTarget = null;

			}

			else if (b.getActionCommand().equals("Hero Power1")
					&& !GameView.herofirst.getClass().getSimpleName()
							.equals("Mage")
					&& !GameView.herofirst.getClass().getSimpleName()
							.equals("Priest")) {
				try {

					GameView.herofirst.useHeroPower();

					GameView.bubbled.setVisible(false);
					GameView.bubbleu.setVisible(false);
					GameView.burned.setVisible(false);
					if (timer != null)
						timer.stop();

				}

				catch (NotYourTurnException e2) {

					GameView.bubbleu.setText(e2.getMessage());
					GameView.bubbleu.setVisible(true);

					if (timer != null)
						timer.stop();
					timer = new Timer(4000, this);
					timer.start();

				}

				catch (NotEnoughManaException | HeroPowerAlreadyUsedException
						| FullFieldException | CloneNotSupportedException e1) {
					if (e1.getClass().getSimpleName()
							.equals("NotEnoughManaException"))
						playSoundextra("sounds/"
								+ game.getCurrentHero().getClass()
										.getSimpleName() + " notenoughmana.wav");
					if (e1.getClass().getSimpleName()
							.equals("HeroPowerAlreadyUsedException"))
						playSoundextra("sounds/"
								+ game.getCurrentHero().getClass()
										.getSimpleName() + " heroattacked.wav");
					if (e1.getClass().getSimpleName()
							.equals("FullFieldException"))
						playSoundextra("sounds/"
								+ game.getCurrentHero().getClass()
										.getSimpleName() + " fullfield.wav");

					GameView.bubbled.setText(e1.getMessage());
					GameView.bubbled.setVisible(true);

					if (timer != null)
						timer.stop();
					timer = new Timer(4000, this);
					timer.start();

				} catch (FullHandException e2) {
					playSoundextra("sounds/"
							+ game.getCurrentHero().getClass().getSimpleName()
							+ " fullhand.wav");
					burndisplay(e2);
				}
				GameView.fronthandneedchange = true;
				GameView.backhandneedchange = false;

				GameView.refreshView();

			} else if (b.getActionCommand().equals("Hero Power1")
					&& GameView.herofirst.getClass().getSimpleName()
							.equals("Mage")) {
				attacker = b;
				b.setBackground(Color.GREEN);
				b.setOpaque(true);
				attackerh = GameView.herofirst;
				powermage = true;

				GameView.bubbled.setVisible(false);
				GameView.bubbleu.setVisible(false);
				GameView.burned.setVisible(false);
				if (timer != null)
					timer.stop();

			} else if (b.getActionCommand().equals("Hero Power1")
					&& GameView.herofirst.getClass().getSimpleName()
							.equals("Priest")) {
				attacker = b;
				b.setOpaque(true);
				b.setBackground(Color.GREEN);
				attackerh = GameView.herofirst;
				powerpriest = true;

				GameView.bubbled.setVisible(false);
				GameView.bubbleu.setVisible(false);
				GameView.burned.setVisible(false);
				if (timer != null)
					timer.stop();

			}

			else if (b.getActionCommand().equals("Hero Power2")
					&& !GameView.herosecond.getClass().getSimpleName()
							.equals("Mage")
					&& !GameView.herosecond.getClass().getSimpleName()
							.equals("Priest")) {
				try {
					GameView.herosecond.useHeroPower();

					GameView.bubbled.setVisible(false);
					GameView.bubbleu.setVisible(false);
					GameView.burned.setVisible(false);
					if (timer != null)
						timer.stop();

				} catch (NotYourTurnException e2) {

					GameView.bubbleu.setText(e2.getMessage());
					GameView.bubbleu.setVisible(true);

					if (timer != null)
						timer.stop();
					timer = new Timer(4000, this);
					timer.start();

				}

				catch (NotEnoughManaException | HeroPowerAlreadyUsedException
						| FullFieldException | CloneNotSupportedException e1) {
					if (e1.getClass().getSimpleName()
							.equals("NotEnoughManaException"))
						playSoundextra("sounds/"
								+ game.getCurrentHero().getClass()
										.getSimpleName() + " notenoughmana.wav");
					if (e1.getClass().getSimpleName()
							.equals("HeroPowerAlreadyUsedException"))
						playSoundextra("sounds/"
								+ game.getCurrentHero().getClass()
										.getSimpleName() + " heroattacked.wav");
					if (e1.getClass().getSimpleName()
							.equals("FullFieldException"))
						playSoundextra("sounds/"
								+ game.getCurrentHero().getClass()
										.getSimpleName() + " fullfield.wav");

					GameView.bubbled.setText(e1.getMessage());
					GameView.bubbled.setVisible(true);

					if (timer != null)
						timer.stop();
					timer = new Timer(4000, this);
					timer.start();

				} catch (FullHandException e2) {
					playSoundextra("sounds/"
							+ game.getCurrentHero().getClass().getSimpleName()
							+ " fullhand.wav");
					burndisplay(e2);
				}
				GameView.fronthandneedchange = true;
				GameView.backhandneedchange = false;

				GameView.refreshView();

			}

			else if (b.getActionCommand().equals("Hero Power2")
					&& GameView.herosecond.getClass().getSimpleName()
							.equals("Mage")) {
				attacker = b;
				b.setBackground(Color.GREEN);
				b.setOpaque(true);
				attackerh = GameView.herosecond;
				powermage = true;

				GameView.bubbled.setVisible(false);
				GameView.bubbleu.setVisible(false);
				GameView.burned.setVisible(false);
				if (timer != null)
					timer.stop();

			}

			else if (b.getActionCommand().equals("Hero Power2")
					&& GameView.herosecond.getClass().getSimpleName()
							.equals("Priest")) {
				attacker = b;
				b.setBackground(Color.GREEN);
				attackerh = GameView.herosecond;
				b.setOpaque(true);
				powerpriest = true;

				GameView.bubbled.setVisible(false);
				GameView.bubbleu.setVisible(false);
				GameView.burned.setVisible(false);
				if (timer != null)
					timer.stop();

			}

			else if (b.getActionCommand().length() > 5
					&& b.getActionCommand().length() > 5
					&& b.getActionCommand().substring(0, 5).equals("1card")
					&& attacker == null) {

				if (GameView.herofirst
						.getHand()
						.get(Integer.parseInt(String.valueOf(b
								.getActionCommand().charAt(5)))).getClass()
						.getSimpleName().equals("Minion")) {
					try {
						GameView.herofirst
								.playMinion((Minion) GameView.herofirst
										.getHand()
										.get(Integer.parseInt(String.valueOf(b
												.getActionCommand().charAt(5)))));
						GameView.display.removeAll();

						GameView.fronthandneedchange = true;
						GameView.backhandneedchange = false;

						GameView.refreshView();
						playSoundextra("sounds/cardfromhand.wav");
						GameView.bubbled.setVisible(false);
						GameView.bubbleu.setVisible(false);
						GameView.burned.setVisible(false);
						if (timer != null)
							timer.stop();

					} catch (NotYourTurnException e2) {
						GameView.bubbleu.setText(e2.getMessage());
						GameView.bubbleu.setVisible(true);

						if (timer != null)
							timer.stop();
						timer = new Timer(4000, this);
						timer.start();

					} catch (NotEnoughManaException | FullFieldException e1) {
						if (e1.getClass().getSimpleName()
								.equals("NotEnoughManaException"))
							playSoundextra("sounds/"
									+ game.getCurrentHero().getClass()
											.getSimpleName()
									+ " notenoughmana.wav");
						if (e1.getClass().getSimpleName()
								.equals("FullFieldException"))
							playSoundextra("sounds/"
									+ game.getCurrentHero().getClass()
											.getSimpleName() + " fullfield.wav");

						GameView.bubbled.setText(e1.getMessage());
						GameView.bubbled.setVisible(true);

						if (timer != null)
							timer.stop();

						timer = new Timer(4000, this);
						timer.start();

					}
				} else if (GameView.herofirst
						.getHand()
						.get(Integer.parseInt(String.valueOf(b
								.getActionCommand().charAt(5)))).getClass()
						.getSimpleName().equals("Icehowl")) {
					try {
						GameView.herofirst
								.playMinion((Minion) GameView.herofirst
										.getHand()
										.get(Integer.parseInt(String.valueOf(b
												.getActionCommand().charAt(5)))));
						GameView.display.removeAll();

						GameView.fronthandneedchange = true;
						GameView.backhandneedchange = false;

						GameView.refreshView();
						playSoundextra("sounds/cardfromhand.wav");
						GameView.bubbled.setVisible(false);
						GameView.bubbleu.setVisible(false);
						GameView.burned.setVisible(false);
						if (timer != null)
							timer.stop();

					} catch (NotYourTurnException e2) {
						GameView.bubbleu.setText(e2.getMessage());
						GameView.bubbleu.setVisible(true);

						if (timer != null)
							timer.stop();
						timer = new Timer(4000, this);
						timer.start();

					} catch (NotEnoughManaException | FullFieldException e1) {
						if (e1.getClass().getSimpleName()
								.equals("NotEnoughManaException"))
							playSoundextra("sounds/"
									+ game.getCurrentHero().getClass()
											.getSimpleName()
									+ " notenoughmana.wav");
						if (e1.getClass().getSimpleName()
								.equals("FullFieldException"))
							playSoundextra("sounds/"
									+ game.getCurrentHero().getClass()
											.getSimpleName() + " fullfield.wav");

						GameView.bubbled.setText(e1.getMessage());
						GameView.bubbled.setVisible(true);

						if (timer != null)
							timer.stop();
						timer = new Timer(4000, this);
						timer.start();

					}
				} else if (GameView.herofirst
						.getHand()
						.get(Integer.parseInt(String.valueOf(b
								.getActionCommand().charAt(5)))).getClass()
						.getInterfaces()[0].getSimpleName()
						.equals("FieldSpell")) {
					try {
						GameView.herofirst
								.castSpell((FieldSpell) GameView.herofirst
										.getHand()
										.get(Integer.parseInt(String.valueOf(b
												.getActionCommand().charAt(5)))));
						GameView.display.removeAll();
						GameView.fronthandneedchange = true;
						GameView.backhandneedchange = false;

						GameView.refreshView();

						GameView.bubbled.setVisible(false);
						GameView.bubbleu.setVisible(false);
						GameView.burned.setVisible(false);
						if (timer != null)
							timer.stop();
						success = true;

					} catch (NotYourTurnException e2) {
						GameView.bubbleu.setText(e2.getMessage());
						GameView.bubbleu.setVisible(true);

						if (timer != null)
							timer.stop();
						timer = new Timer(4000, this);
						timer.start();

					}

					catch (NotEnoughManaException e1) {
						playSoundextra("sounds/"
								+ game.getCurrentHero().getClass()
										.getSimpleName() + " notenoughmana.wav");

						GameView.bubbled.setText(e1.getMessage());
						GameView.bubbled.setVisible(true);

						if (timer != null)
							timer.stop();
						timer = new Timer(4000, this);
						timer.start();

					}
					if (success){
						playSound("sounds/Spell.wav");
						success = false;
					}
				}
				
				else if (GameView.herofirst
						.getHand()
						.get(Integer.parseInt(String.valueOf(b
								.getActionCommand().charAt(5)))).getClass()
						.getInterfaces()[0].getSimpleName().equals("AOESpell")) {

					try {
						GameView.herofirst
								.castSpell(
										(AOESpell) GameView.herofirst
												.getHand()
												.get(Integer.parseInt(String
														.valueOf(b
																.getActionCommand()
																.charAt(5)))),
										GameView.herosecond.getField());
						GameView.display.removeAll();
						GameView.fronthandneedchange = true;
						GameView.backhandneedchange = false;

						GameView.refreshView();

						GameView.bubbled.setVisible(false);
						GameView.bubbleu.setVisible(false);
						GameView.burned.setVisible(false);
						if (timer != null)
							timer.stop();
						success = true;

					} catch (NotYourTurnException e2) {
						GameView.bubbleu.setText(e2.getMessage());
						GameView.bubbleu.setVisible(true);

						if (timer != null)
							timer.stop();
						timer = new Timer(4000, this);
						timer.start();

					}

					catch (NotEnoughManaException e1) {
						playSoundextra("sounds/"
								+ game.getCurrentHero().getClass()
										.getSimpleName() + " notenoughmana.wav");
						GameView.bubbled.setText(e1.getMessage());
						GameView.bubbled.setVisible(true);

						if (timer != null)
							timer.stop();
						timer = new Timer(4000, this);
						timer.start();

					}
					if (success){
						playSound("sounds/Spell.wav");
						success = false;
					}
				} else if (GameView.herofirst
						.getHand()
						.get(Integer.parseInt(String.valueOf(b
								.getActionCommand().charAt(5)))).getClass()
						.getInterfaces()[0].getSimpleName().equals(
						"LeechingSpell")) {

					attacker = b;
					attacker.setBackground(Color.GREEN);
					attacker.setOpaque(true);
					attackerh = GameView.herofirst;

					Leeching = (LeechingSpell) GameView.herofirst.getHand()
							.get(Integer.parseInt(String.valueOf(b
									.getActionCommand().charAt(5))));

					GameView.bubbled.setVisible(false);
					GameView.bubbleu.setVisible(false);
					GameView.burned.setVisible(false);
					if (timer != null)
						timer.stop();
				}

				else if (GameView.herofirst
						.getHand()
						.get(Integer.parseInt(String.valueOf(b
								.getActionCommand().charAt(5)))).getClass()
						.getInterfaces()[0].getSimpleName().equals(
						"MinionTargetSpell")) {
					Leeching = null;
					attacker = b;
					attacker.setBackground(Color.GREEN);
					attacker.setOpaque(true);
					attackerh = GameView.herofirst;

					MinionTarget = (MinionTargetSpell) GameView.herofirst
							.getHand().get(
									Integer.parseInt(String.valueOf(b
											.getActionCommand().charAt(5))));

					if (GameView.herofirst
							.getHand()
							.get(Integer.parseInt(String.valueOf(b
									.getActionCommand().charAt(5)))).getClass()
							.getInterfaces().length > 1
							&& GameView.herofirst
									.getHand()
									.get(Integer.parseInt(String.valueOf(b
											.getActionCommand().charAt(5))))
									.getClass().getInterfaces()[1]
									.getSimpleName().equals("HeroTargetSpell")) {

						HeroTarget = (HeroTargetSpell) GameView.herofirst
								.getHand()
								.get(Integer.parseInt(String.valueOf(b
										.getActionCommand().charAt(5))));

					}

					GameView.bubbled.setVisible(false);
					GameView.bubbleu.setVisible(false);
					GameView.burned.setVisible(false);
					if (timer != null)
						timer.stop();

				}

			}

			else if (b.getActionCommand().length() > 5
					&& b.getActionCommand().substring(0, 5).equals("2card")
					&& attacker == null) {

				if (GameView.herosecond
						.getHand()
						.get(Integer.parseInt(String.valueOf(b
								.getActionCommand().charAt(5)))).getClass()
						.getSimpleName().equals("Minion")) {
					try {
						GameView.herosecond
								.playMinion((Minion) GameView.herosecond
										.getHand()
										.get(Integer.parseInt(String.valueOf(b
												.getActionCommand().charAt(5)))));
						GameView.display.removeAll();

						GameView.fronthandneedchange = true;
						GameView.backhandneedchange = false;

						GameView.refreshView();
						playSoundextra("sounds/cardfromhand.wav");
						GameView.bubbled.setVisible(false);
						GameView.bubbleu.setVisible(false);
						GameView.burned.setVisible(false);
						if (timer != null)
							timer.stop();

					} catch (NotYourTurnException e3) {
						GameView.bubbleu.setText(e3.getMessage());
						GameView.bubbleu.setVisible(true);

						if (timer != null)
							timer.stop();
						timer = new Timer(4000, this);
						timer.start();

					} catch (NotEnoughManaException | FullFieldException e1) {
						if (e1.getClass().getSimpleName()
								.equals("NotEnoughManaException"))
							playSoundextra("sounds/"
									+ game.getCurrentHero().getClass()
											.getSimpleName()
									+ " notenoughmana.wav");
						if (e1.getClass().getSimpleName()
								.equals("FullFieldException"))
							playSoundextra("sounds/"
									+ game.getCurrentHero().getClass()
											.getSimpleName() + " fullfield.wav");
						GameView.bubbled.setText(e1.getMessage());
						GameView.bubbled.setVisible(true);

						if (timer != null)
							timer.stop();
						timer = new Timer(4000, this);
						timer.start();

					}
				}

				else if (GameView.herosecond
						.getHand()
						.get(Integer.parseInt(String.valueOf(b
								.getActionCommand().charAt(5)))).getClass()
						.getSimpleName().equals("Icehowl")) {
					try {
						GameView.herosecond
								.playMinion((Minion) GameView.herosecond
										.getHand()
										.get(Integer.parseInt(String.valueOf(b
												.getActionCommand().charAt(5)))));
						GameView.display.removeAll();

						GameView.fronthandneedchange = true;
						GameView.backhandneedchange = false;

						GameView.refreshView();
						playSoundextra("sounds/cardfromhand.wav");
						GameView.bubbled.setVisible(false);
						GameView.bubbleu.setVisible(false);
						GameView.burned.setVisible(false);
						if (timer != null)
							timer.stop();

					} catch (NotYourTurnException e3) {
						GameView.bubbleu.setText(e3.getMessage());
						GameView.bubbleu.setVisible(true);

						if (timer != null)
							timer.stop();
						timer = new Timer(4000, this);
						timer.start();

					} catch (NotEnoughManaException | FullFieldException e1) {
						if (e1.getClass().getSimpleName()
								.equals("NotEnoughManaException"))
							playSoundextra("sounds/"
									+ game.getCurrentHero().getClass()
											.getSimpleName()
									+ " notenoughmana.wav");
						if (e1.getClass().getSimpleName()
								.equals("FullFieldException"))
							playSoundextra("sounds/"
									+ game.getCurrentHero().getClass()
											.getSimpleName() + " fullfield.wav");

						GameView.bubbled.setText(e1.getMessage());
						GameView.bubbled.setVisible(true);

						if (timer != null)
							timer.stop();
						timer = new Timer(4000, this);
						timer.start();

					}
				} else if (GameView.herosecond
						.getHand()
						.get(Integer.parseInt(String.valueOf(b
								.getActionCommand().charAt(5)))).getClass()
						.getInterfaces()[0].getSimpleName()
						.equals("FieldSpell")) {
					try {
						GameView.herosecond
								.castSpell((FieldSpell) GameView.herosecond
										.getHand()
										.get(Integer.parseInt(String.valueOf(b
												.getActionCommand().charAt(5)))));
						GameView.display.removeAll();
						GameView.fronthandneedchange = true;
						GameView.backhandneedchange = false;

						GameView.refreshView();

						GameView.bubbled.setVisible(false);
						GameView.bubbleu.setVisible(false);
						GameView.burned.setVisible(false);
						if (timer != null)
							timer.stop();
						success = true;

					} catch (NotYourTurnException e2) {
						GameView.bubbleu.setText(e2.getMessage());
						GameView.bubbleu.setVisible(true);

						if (timer != null)
							timer.stop();
						timer = new Timer(4000, this);
						timer.start();
					}

					catch (NotEnoughManaException e1) {
						playSoundextra("sounds/"
								+ game.getCurrentHero().getClass()
										.getSimpleName() + " notenoughmana.wav");
						GameView.bubbled.setText(e1.getMessage());
						GameView.bubbled.setVisible(true);

						if (timer != null)
							timer.stop();
						timer = new Timer(4000, this);
						timer.start();

					}
					if (success){
						playSound("sounds/Spell.wav");
						success = false;
					}
				} else if (GameView.herosecond
						.getHand()
						.get(Integer.parseInt(String.valueOf(b
								.getActionCommand().charAt(5)))).getClass()
						.getInterfaces()[0].getSimpleName().equals("AOESpell")) {

					try {
						GameView.herosecond
								.castSpell(
										(AOESpell) GameView.herosecond
												.getHand()
												.get(Integer.parseInt(String
														.valueOf(b
																.getActionCommand()
																.charAt(5)))),
										GameView.herofirst.getField());

						GameView.display.removeAll();

						GameView.fronthandneedchange = true;
						GameView.backhandneedchange = false;

						GameView.refreshView();

						GameView.bubbled.setVisible(false);
						GameView.bubbleu.setVisible(false);
						GameView.burned.setVisible(false);
						if (timer != null)
							timer.stop();
						success = true;

					} catch (NotYourTurnException e2) {
						GameView.bubbleu.setText(e2.getMessage());
						GameView.bubbleu.setVisible(true);

						if (timer != null)
							timer.stop();
						timer = new Timer(4000, this);
						timer.start();
					}

					catch (NotEnoughManaException e1) {
						playSoundextra("sounds/"
								+ game.getCurrentHero().getClass()
										.getSimpleName() + " notenoughmana.wav");
						GameView.bubbled.setText(e1.getMessage());
						GameView.bubbled.setVisible(true);

						if (timer != null)
							timer.stop();
						timer = new Timer(4000, this);
						timer.start();

					}
					if (success) {
						playSound("sounds/Spell.wav");
						success = false;
					}

				} else if (GameView.herosecond
						.getHand()
						.get(Integer.parseInt(String.valueOf(b
								.getActionCommand().charAt(5)))).getClass()
						.getInterfaces()[0].getSimpleName().equals(
						"LeechingSpell")) {

					attacker = b;
					attacker.setBackground(Color.GREEN);
					attacker.setOpaque(true);
					attackerh = GameView.herosecond;

					Leeching = (LeechingSpell) GameView.herosecond.getHand()
							.get(Integer.parseInt(String.valueOf(b
									.getActionCommand().charAt(5))));

					GameView.bubbled.setVisible(false);
					GameView.bubbleu.setVisible(false);
					GameView.burned.setVisible(false);
					if (timer != null)
						timer.stop();
				}

				else if (GameView.herosecond
						.getHand()
						.get(Integer.parseInt(String.valueOf(b
								.getActionCommand().charAt(5)))).getClass()
						.getInterfaces()[0].getSimpleName().equals(
						"MinionTargetSpell")) {

					Leeching = null;
					attacker = b;
					attacker.setBackground(Color.GREEN);
					attacker.setOpaque(true);
					attackerh = GameView.herosecond;

					MinionTarget = (MinionTargetSpell) GameView.herosecond
							.getHand().get(
									Integer.parseInt(String.valueOf(b
											.getActionCommand().charAt(5))));

					if (GameView.herosecond
							.getHand()
							.get(Integer.parseInt(String.valueOf(b
									.getActionCommand().charAt(5)))).getClass()
							.getInterfaces().length > 1
							&& GameView.herosecond
									.getHand()
									.get(Integer.parseInt(String.valueOf(b
											.getActionCommand().charAt(5))))
									.getClass().getInterfaces()[1]
									.getSimpleName().equals("HeroTargetSpell")) {

						HeroTarget = (HeroTargetSpell) GameView.herosecond
								.getHand()
								.get(Integer.parseInt(String.valueOf(b
										.getActionCommand().charAt(5))));
					}

					GameView.bubbled.setVisible(false);
					GameView.bubbleu.setVisible(false);
					GameView.burned.setVisible(false);
					if (timer != null)
						timer.stop();
				}
			}

			else if (b.getActionCommand().length() > 7
					&& b.getActionCommand().length() > 7
					&& b.getActionCommand().substring(1, 7).equals("played")) {

				GameView.bubbled.setVisible(false);
				GameView.bubbleu.setVisible(false);
				GameView.burned.setVisible(false);
				if (timer != null)
					timer.stop();

				if (attacker == null) {
					attacker = b;
					attacker.setBackground(Color.GREEN);
					attacker.setOpaque(true);
				} else if (attacker != null && attackerh == null) {
					target = b;
					try {
						minionattackm();

						GameView.fronthandneedchange = true;
						GameView.backhandneedchange = false;

						GameView.refreshView();

						GameView.bubbled.setVisible(false);
						GameView.bubbleu.setVisible(false);
						GameView.burned.setVisible(false);
						if (timer != null)
							timer.stop();
						success = true;

					} catch (NotYourTurnException e2) {
						attacker.setOpaque(false);
						attacker = null;
						attackerh = null;
						attackerm = null;

						target.setOpaque(false);
						target = null;
						targeth = null;
						targetm = null;

						Leeching = null;
						MinionTarget = null;
						HeroTarget = null;

						powermage = false;
						powerpriest = false;

						GameView.bubbleu.setText(e2.getMessage());
						GameView.bubbleu.setVisible(true);

						if (timer != null)
							timer.stop();
						timer = new Timer(4000, this);
						timer.start();

					}

					catch (CannotAttackException | TauntBypassException
							| InvalidTargetException | NotSummonedException e1) {
						if (e1.getClass().getSimpleName()
								.equals("CannotAttackException"))
							if (game.getCurrentHero()
									.getField()
									.get(Integer.parseInt(String
											.valueOf(attacker
													.getActionCommand().charAt(
															7)))).isSleeping())
								playSoundextra("sounds/"
										+ game.getCurrentHero().getClass()
												.getSimpleName()
										+ " sleeping.wav");
							else
								playSound("sounds/"
										+ game.getCurrentHero().getClass()
												.getSimpleName()
										+ " minionattacked.wav");
						if (e1.getClass().getSimpleName()
								.equals("TauntBypassException"))
							playSoundextra("sounds/"
									+ game.getCurrentHero().getClass()
											.getSimpleName() + " taunt.wav");
						if (e1.getClass().getSimpleName()
								.equals("InvalidTargetException"))
							playSoundextra("sounds/"
									+ game.getCurrentHero().getClass()
											.getSimpleName()
									+ " invalidtarget.wav");
						attacker.setOpaque(false);
						attacker = null;
						attackerh = null;
						attackerm = null;

						target.setOpaque(false);
						target = null;
						targeth = null;
						targetm = null;

						Leeching = null;
						MinionTarget = null;
						HeroTarget = null;

						powermage = false;
						powerpriest = false;

						GameView.bubbled.setText(e1.getMessage());
						GameView.bubbled.setVisible(true);

						if (timer != null)
							timer.stop();
						timer = new Timer(4000, this);
						timer.start();

					}
					if (success){
						playSoundextra("sounds/Minion attack.wav");
						success = false;
					}

				}

				else if (attackerh != null) {
					target = b;
					try {
						heroattack();

						GameView.fronthandneedchange = true;
						GameView.backhandneedchange = true;

						GameView.refreshView();
						success = true;
					}

					catch (NotYourTurnException e2) {
						attacker.setOpaque(false);
						attacker = null;
						attackerh = null;
						attackerm = null;

						target.setOpaque(false);
						target = null;
						targeth = null;
						targetm = null;

						Leeching = null;
						MinionTarget = null;
						HeroTarget = null;

						powermage = false;
						powerpriest = false;

						GameView.bubbleu.setText(e2.getMessage());
						GameView.bubbleu.setVisible(true);

						if (timer != null)
							timer.stop();
						timer = new Timer(4000, this);
						timer.start();

					}

					catch (NotEnoughManaException | InvalidTargetException
							| HeroPowerAlreadyUsedException | FullHandException
							| FullFieldException | CloneNotSupportedException e1) {
						if (e1.getClass().getSimpleName()
								.equals("NotEnoughManaException"))
							playSoundextra("sounds/"
									+ game.getCurrentHero().getClass()
											.getSimpleName()
									+ " notenoughmana.wav");
						if (e1.getClass().getSimpleName()
								.equals("InvalidTargetException"))
							playSoundextra("sounds/"
									+ game.getCurrentHero().getClass()
											.getSimpleName()
									+ " invalidtarget.wav");
						if (e1.getClass().getSimpleName()
								.equals("HeroPowerAlreadyUsedException"))
							playSoundextra("sounds/"
									+ game.getCurrentHero().getClass()
											.getSimpleName()
									+ " heroattacked.wav");
						if (e1.getClass().getSimpleName()
								.equals("FullHandException"))
							playSoundextra("sounds/"
									+ game.getCurrentHero().getClass()
											.getSimpleName() + " fullhand.wav");
						if (e1.getClass().getSimpleName()
								.equals("FullFieldException"))
							playSoundextra("sounds/"
									+ game.getCurrentHero().getClass()
											.getSimpleName() + " fullfield.wav");

						attacker.setOpaque(false);
						attacker = null;
						attackerh = null;
						attackerm = null;

						target.setOpaque(false);
						target = null;
						targeth = null;
						targetm = null;

						Leeching = null;
						MinionTarget = null;
						HeroTarget = null;

						powermage = false;
						powerpriest = false;

						GameView.bubbled.setText(e1.getMessage());
						GameView.bubbled.setVisible(true);

						if (timer != null)
							timer.stop();
						timer = new Timer(4000, this);
						timer.start();

					}
					if (success){
						playSoundextra("sounds/heroattack.wav");
						success = false;
					}
				}

			}

		} else if (GameView.bubbled.isVisible() || GameView.bubbleu.isVisible()
				|| GameView.burned.isVisible()) {
			GameView.bubbled.setVisible(false);
			GameView.bubbleu.setVisible(false);
			GameView.burned.setVisible(false);
			timer.stop();

		}

		HeroSelect.revalidate();
		HeroSelect.repaint();

	}

	public void playgame() throws IOException, CloneNotSupportedException,
			FullHandException {
		switch (SelectedHerotemp.getActionCommand()) {
		case "Mage":
			h1 = new Mage();
			break;
		case "Hunter":
			h1 = new Hunter();
			break;
		case "Paladin":
			h1 = new Paladin();
			break;
		case "Priest":
			h1 = new Priest();
			break;
		case "Warlock":
			h1 = new Warlock();
			break;
		}
		switch (SelectedHerotemp2.getActionCommand()) {
		case "Mage":
			h2 = new Mage();
			break;
		case "Hunter":
			h2 = new Hunter();
			break;
		case "Paladin":
			h2 = new Paladin();
			break;
		case "Priest":
			h2 = new Priest();
			break;
		case "Warlock":
			h2 = new Warlock();
			break;
		}

		game = new Game(h1, h2);
		game.setListener(this);
		GameView = new GameView(this);
		GameView.setVisible(true);

	}

	public Game getGame() {
		return game;
	}

	public static void main(String[] args) {
		new Controller();
	}

	@Override
	public void onGameOver() {
		GameView.Gameover();
		GameView.clip.stop();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {

		JButton b = (JButton) (e.getSource());

		if (b.getActionCommand().equals("Hero Power1")
				&& GameView.turn % 2 != 0) {

			ImageIcon power1icon = new ImageIcon("images/"
					+ GameView.herofirst.getClass().getSimpleName()
					+ "powerbig.png");
			Image power1img = power1icon.getImage().getScaledInstance(
					GameView.getWidth() * 140 / 1382,
					GameView.getHeight() * 216 / 744,
					java.awt.Image.SCALE_SMOOTH);
			power1icon = new ImageIcon(power1img);
			GameView.power1.setIcon(power1icon);
			GameView.power1.setBounds(GameView.getWidth() * 1100 / 1920,
					GameView.getHeight() * 700 / 1080,
					GameView.getWidth() * 140 / 1382,
					GameView.getHeight() * 216 / 744);

		} else if (b.getActionCommand().equals("Hero Power2")
				&& GameView.turn % 2 != 0) {

			ImageIcon power2icon = new ImageIcon("images/"
					+ GameView.herosecond.getClass().getSimpleName()
					+ "powerbig.png");
			Image power2img = power2icon.getImage().getScaledInstance(
					GameView.getWidth() * 140 / 1382,
					GameView.getHeight() * 216 / 744,
					java.awt.Image.SCALE_SMOOTH);
			power2icon = new ImageIcon(power2img);
			GameView.power2.setIcon(power2icon);
			GameView.power2.setBounds(GameView.getWidth() * 1100 / 1920,
					GameView.getHeight() * 110 / 1080,
					GameView.getWidth() * 140 / 1382,
					GameView.getHeight() * 216 / 744);
		} else if (b.getActionCommand().equals("Hero Power1")
				&& GameView.turn % 2 == 0) {

			ImageIcon power1icon = new ImageIcon("images/"
					+ GameView.herofirst.getClass().getSimpleName()
					+ "powerbig.png");
			Image power1img = power1icon.getImage().getScaledInstance(
					GameView.getWidth() * 140 / 1382,
					GameView.getHeight() * 216 / 744,
					java.awt.Image.SCALE_SMOOTH);
			power1icon = new ImageIcon(power1img);
			GameView.power1.setIcon(power1icon);
			GameView.power1.setBounds(GameView.getWidth() * 1100 / 1920,
					GameView.getHeight() * 110 / 1080,
					GameView.getWidth() * 140 / 1382,
					GameView.getHeight() * 216 / 744);

		} else if (b.getActionCommand().equals("Hero Power2")
				&& GameView.turn % 2 == 0) {

			ImageIcon power2icon = new ImageIcon("images/"
					+ GameView.herosecond.getClass().getSimpleName()
					+ "powerbig.png");
			Image power2img = power2icon.getImage().getScaledInstance(
					GameView.getWidth() * 140 / 1382,
					GameView.getHeight() * 216 / 744,
					java.awt.Image.SCALE_SMOOTH);
			power2icon = new ImageIcon(power2img);
			GameView.power2.setIcon(power2icon);
			GameView.power2.setBounds(GameView.getWidth() * 1100 / 1920,
					GameView.getHeight() * 700 / 1080,
					GameView.getWidth() * 140 / 1382,
					GameView.getHeight() * 216 / 744);
		}

		else if (b.getActionCommand().length() > 5
				&& b.getActionCommand().length() > 5
				&& b.getActionCommand().substring(0, 5).equals("1card")
				&& GameView.turn % 2 != 0) {

			ImageIcon icon;
			Card card = getCurrCard(b.getActionCommand().substring(7),
					GameView.herofirst);
			if (b.getActionCommand().substring(7).equals("Shadow Word: Death"))
				icon = new ImageIcon("images/cards/card Shadow Word Death.png");
			else

				icon = new ImageIcon("images/cards/card "
						+ b.getActionCommand().substring(7) + ".png");
			Image iconimg = icon.getImage().getScaledInstance(
					200 * GameView.getWidth() / 1366,
					276 * GameView.getHeight() / 768,
					java.awt.Image.SCALE_SMOOTH);
			icon = new ImageIcon(iconimg);
			JLabel temp = new JLabel(icon);
			temp.setBounds(0, 0, 200 * GameView.getWidth() / 1366,
					276 * GameView.getHeight() / 768);

			if ((card.getClass() == Minion.class)
					|| (card.getClass() == Icehowl.class)) {
				JLabel attack = new JLabel(((Minion) card).getAttack() + "");
				attack.setForeground(Color.WHITE);
				if (((Minion) card).getAttack() > 9)
					attack.setBounds(25 * GameView.getWidth() / 1920,
							608 * GameView.getHeight() / 1920,
							50 * GameView.getWidth() / 1920,
							50 * GameView.getHeight() / 1920);
				else
					attack.setBounds(36 * GameView.getWidth() / 1920,
							609 * GameView.getHeight() / 1920,
							50 * GameView.getWidth() / 1920,
							50 * GameView.getHeight() / 1920);
				attack.setFont(new Font("Arial", Font.BOLD, 35 * GameView
						.getWidth() / 1920));
				temp.add(attack);

				JLabel health = new JLabel(((Minion) card).getCurrentHP() + "");
				health.setForeground(Color.WHITE);
				health.setFont(new Font("Arial", Font.BOLD, 35 * GameView
						.getWidth() / 1920));
				if (((Minion) card).getCurrentHP() > 9)
					health.setBounds(222 * GameView.getWidth() / 1920,
							610 * GameView.getHeight() / 1920,
							50 * GameView.getWidth() / 1920,
							50 * GameView.getHeight() / 1920);
				else
					health.setBounds(232 * GameView.getWidth() / 1920,
							609 * GameView.getHeight() / 1920,
							50 * GameView.getWidth() / 1920,
							50 * GameView.getHeight() / 1920);
				temp.add(health);
			}

			JLabel manacost = new JLabel(card.getManaCost() + "");
			manacost.setForeground(Color.WHITE);
			if (card.getManaCost() > 9) {
				manacost.setBounds(12 * GameView.getWidth() / 1920,
						50 * GameView.getHeight() / 1920,
						100 * GameView.getWidth() / 1920,
						100 * GameView.getHeight() / 1920);
				manacost.setFont(new Font("Arial", Font.BOLD, 50 * GameView
						.getWidth() / 1920));
			} else {
				manacost.setBounds(23 * GameView.getWidth() / 1920,
						50 * GameView.getHeight() / 1920,
						100 * GameView.getWidth() / 1920,
						100 * GameView.getHeight() / 1920);
				manacost.setFont(new Font("Arial", Font.BOLD, 65 * GameView
						.getWidth() / 1920));
			}
			temp.add(manacost);

			GameView.display.add(temp);

			GameView.revalidate();
			GameView.repaint();

		} else if (b.getActionCommand().length() > 5
				&& b.getActionCommand().length() > 5
				&& b.getActionCommand().substring(0, 5).equals("2card")
				&& GameView.turn % 2 == 0) {

			ImageIcon icon;
			Card card = getCurrCard(b.getActionCommand().substring(7),
					GameView.herosecond);
			if (b.getActionCommand().substring(7).equals("Shadow Word: Death"))
				icon = new ImageIcon("images/cards/card Shadow Word Death.png");
			else

				icon = new ImageIcon("images/cards/card "
						+ b.getActionCommand().substring(7) + ".png");
			Image iconimg = icon.getImage().getScaledInstance(
					200 * GameView.getWidth() / 1366,
					276 * GameView.getHeight() / 768,
					java.awt.Image.SCALE_SMOOTH);
			icon = new ImageIcon(iconimg);
			JLabel temp = new JLabel(icon);
			temp.setBounds(0, 0, 200 * GameView.getWidth() / 1366,
					276 * GameView.getHeight() / 768);

			if ((card.getClass() == Minion.class)
					|| (card.getClass() == Icehowl.class)) {
				JLabel attack = new JLabel(((Minion) card).getAttack() + "");
				attack.setForeground(Color.WHITE);
				if (((Minion) card).getAttack() > 9)
					attack.setBounds(25 * GameView.getWidth() / 1920,
							608 * GameView.getHeight() / 1920,
							50 * GameView.getWidth() / 1920,
							50 * GameView.getHeight() / 1920);
				else
					attack.setBounds(36 * GameView.getWidth() / 1920,
							609 * GameView.getHeight() / 1920,
							50 * GameView.getWidth() / 1920,
							50 * GameView.getHeight() / 1920);
				attack.setFont(new Font("Arial", Font.BOLD, 35 * GameView
						.getWidth() / 1920));
				temp.add(attack);

				JLabel health = new JLabel(((Minion) card).getCurrentHP() + "");
				health.setForeground(Color.WHITE);
				health.setFont(new Font("Arial", Font.BOLD, 35 * GameView
						.getWidth() / 1920));
				if (((Minion) card).getCurrentHP() > 9)
					health.setBounds(222 * GameView.getWidth() / 1920,
							610 * GameView.getHeight() / 1920,
							50 * GameView.getWidth() / 1920,
							50 * GameView.getHeight() / 1920);
				else
					health.setBounds(232 * GameView.getWidth() / 1920,
							609 * GameView.getHeight() / 1920,
							50 * GameView.getWidth() / 1920,
							50 * GameView.getHeight() / 1920);
				temp.add(health);
			}

			JLabel manacost = new JLabel(card.getManaCost() + "");
			manacost.setForeground(Color.WHITE);
			if (card.getManaCost() > 9) {
				manacost.setBounds(12 * GameView.getWidth() / 1920,
						50 * GameView.getHeight() / 1920,
						100 * GameView.getWidth() / 1920,
						100 * GameView.getHeight() / 1920);
				manacost.setFont(new Font("Arial", Font.BOLD, 50 * GameView
						.getWidth() / 1920));
			} else {
				manacost.setBounds(23 * GameView.getWidth() / 1920,
						50 * GameView.getHeight() / 1920,
						100 * GameView.getWidth() / 1920,
						100 * GameView.getHeight() / 1920);
				manacost.setFont(new Font("Arial", Font.BOLD, 65 * GameView
						.getWidth() / 1920));
			}
			temp.add(manacost);

			GameView.display.add(temp);

			GameView.revalidate();
			GameView.repaint();

		}

	}

	@Override
	public void mouseExited(MouseEvent e) {

		JButton b = (JButton) (e.getSource());

		if (b.getActionCommand().equals("Hero Power1")
				&& GameView.turn % 2 != 0) {

			ImageIcon power1icon = new ImageIcon("images/"
					+ GameView.herofirst.getClass().getSimpleName()
					+ "powersmall.png");
			Image power1img = power1icon.getImage().getScaledInstance(
					GameView.getWidth() * 100 / 1382,
					GameView.getHeight() * 100 / 744,
					java.awt.Image.SCALE_SMOOTH);
			power1icon = new ImageIcon(power1img);
			GameView.power1.setIcon(power1icon);
			GameView.power1.setBounds(GameView.getWidth() * 1134 / 1920,
					GameView.getHeight() * 780 / 1080,
					GameView.getWidth() * 100 / 1382,
					GameView.getHeight() * 100 / 744);

		} else if (b.getActionCommand().equals("Hero Power2")
				&& GameView.turn % 2 != 0) {

			ImageIcon power2icon = new ImageIcon("images/"
					+ GameView.herosecond.getClass().getSimpleName()
					+ "powersmall.png");
			Image power2img = power2icon.getImage().getScaledInstance(
					GameView.getWidth() * 100 / 1382,
					GameView.getHeight() * 100 / 744,
					java.awt.Image.SCALE_SMOOTH);
			power2icon = new ImageIcon(power2img);
			GameView.power2.setIcon(power2icon);
			GameView.power2.setBounds(GameView.getWidth() * 1134 / 1920,
					GameView.getHeight() * 150 / 1080,
					GameView.getWidth() * 100 / 1382,
					GameView.getHeight() * 100 / 744);

		}

		else if (b.getActionCommand().equals("Hero Power1")
				&& GameView.turn % 2 == 0) {

			ImageIcon power1icon = new ImageIcon("images/"
					+ GameView.herofirst.getClass().getSimpleName()
					+ "powersmall.png");
			Image power1img = power1icon.getImage().getScaledInstance(
					GameView.getWidth() * 100 / 1382,
					GameView.getHeight() * 100 / 744,
					java.awt.Image.SCALE_SMOOTH);
			power1icon = new ImageIcon(power1img);
			GameView.power1.setIcon(power1icon);
			GameView.power1.setBounds(GameView.getWidth() * 1134 / 1920,
					GameView.getHeight() * 150 / 1080,
					GameView.getWidth() * 100 / 1382,
					GameView.getHeight() * 100 / 744);

		} else if (b.getActionCommand().equals("Hero Power2")
				&& GameView.turn % 2 == 0) {

			ImageIcon power2icon = new ImageIcon("images/"
					+ GameView.herosecond.getClass().getSimpleName()
					+ "powersmall.png");
			Image power2img = power2icon.getImage().getScaledInstance(
					GameView.getWidth() * 100 / 1382,
					GameView.getHeight() * 100 / 744,
					java.awt.Image.SCALE_SMOOTH);
			power2icon = new ImageIcon(power2img);
			GameView.power2.setIcon(power2icon);
			GameView.power2.setBounds(GameView.getWidth() * 1134 / 1920,
					GameView.getHeight() * 780 / 1080,
					GameView.getWidth() * 100 / 1382,
					GameView.getHeight() * 100 / 744);

		} else if (b.getActionCommand().length() > 5
				&& b.getActionCommand().substring(1, 5).equals("card")) {

			GameView.display.removeAll();
			GameView.revalidate();
			GameView.repaint();

		}

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

	}

	public void minionattackm() throws CannotAttackException,
			NotYourTurnException, TauntBypassException, InvalidTargetException,
			NotSummonedException {

		if ((attacker.getActionCommand().charAt(0) + "").equals("1"))
			attackerm = GameView.herofirst.getField().get(
					Integer.parseInt(String.valueOf(attacker.getActionCommand()
							.charAt(7))));
		else if ((attacker.getActionCommand().charAt(0) + "").equals("2"))
			attackerm = GameView.herosecond.getField().get(
					Integer.parseInt(String.valueOf(attacker.getActionCommand()
							.charAt(7))));

		if ((target.getActionCommand().charAt(0) + "").equals("1"))
			targetm = GameView.herofirst.getField().get(
					Integer.parseInt(String.valueOf(target.getActionCommand()
							.charAt(7))));
		else if ((target.getActionCommand().charAt(0) + "").equals("2"))
			targetm = GameView.herosecond.getField().get(
					Integer.parseInt(String.valueOf(target.getActionCommand()
							.charAt(7))));

		if ((attacker.getActionCommand().charAt(0) + "").equals("1"))
			GameView.herofirst.attackWithMinion(attackerm, targetm);
		else
			GameView.herosecond.attackWithMinion(attackerm, targetm);

		attacker.setOpaque(false);
		attacker = null;
		attackerh = null;
		attackerm = null;

		target.setOpaque(false);
		target = null;
		targeth = null;
		targetm = null;

		Leeching = null;
		MinionTarget = null;
		HeroTarget = null;

		powermage = false;
		powerpriest = false;

	}

	public void minionattackh() throws CannotAttackException,
			NotYourTurnException, TauntBypassException, InvalidTargetException,
			NotSummonedException {

		attacker.setOpaque(false);
		target.setOpaque(false);

		if ((attacker.getActionCommand().charAt(0) + "").equals("1")) {
			attackerm = GameView.herofirst.getField().get(
					Integer.parseInt(String.valueOf(attacker.getActionCommand()
							.charAt(7))));
			attackerh = GameView.herofirst;
		} else if ((attacker.getActionCommand().charAt(0) + "").equals("2")) {
			attackerm = GameView.herosecond.getField().get(
					Integer.parseInt(String.valueOf(attacker.getActionCommand()
							.charAt(7))));
			attackerh = GameView.herosecond;
		}

		attackerh.attackWithMinion(attackerm, targeth);

		attacker.setOpaque(false);
		attacker = null;
		attackerh = null;
		attackerm = null;

		target.setOpaque(false);
		target = null;
		targeth = null;
		targetm = null;

		Leeching = null;
		MinionTarget = null;
		HeroTarget = null;

		powermage = false;
		powerpriest = false;
	}

	public void heroattack() throws NotYourTurnException,
			NotEnoughManaException, InvalidTargetException,
			HeroPowerAlreadyUsedException, FullHandException,
			FullFieldException, CloneNotSupportedException {

		attacker.setOpaque(false);
		target.setOpaque(false);

		if (target.getActionCommand().equalsIgnoreCase("hero1"))
			targeth = GameView.herofirst;
		else if (target.getActionCommand().equalsIgnoreCase("hero2"))
			targeth = GameView.herosecond;
		else if ((target.getActionCommand().charAt(0) + "").equals("1"))
			targetm = GameView.herofirst.getField().get(
					Integer.parseInt(String.valueOf(target.getActionCommand()
							.charAt(7))));
		else if ((target.getActionCommand().charAt(0) + "").equals("2"))
			targetm = GameView.herosecond.getField().get(
					Integer.parseInt(String.valueOf(target.getActionCommand()
							.charAt(7))));

		if (Leeching != null) {
			attackerh.castSpell(Leeching, targetm);
		} else if (MinionTarget != null && targetm != null) {
			attackerh.castSpell(MinionTarget, targetm);
		} else if (HeroTarget != null && targeth != null) {
			attackerh.castSpell(HeroTarget, targeth);
		} else if (powermage == true) {
			if (targeth != null)
				((Mage) attackerh).useHeroPower(targeth);
			else if (targetm != null)
				((Mage) attackerh).useHeroPower(targetm);

		} else if (powerpriest == true) {
			if (targeth != null)
				((Priest) attackerh).useHeroPower(targeth);
			else if (targetm != null)
				((Priest) attackerh).useHeroPower(targetm);

		}

		attacker = null;
		attackerh = null;
		attackerm = null;

		target = null;
		targeth = null;
		targetm = null;

		Leeching = null;
		MinionTarget = null;
		HeroTarget = null;

		powermage = false;
		powerpriest = false;

	}

	public Card getCurrCard(String name, Hero hero) {

		for (Card card : hero.getHand()) {
			if (card.getName().equalsIgnoreCase(name))
				return card;
		}

		return null;
	}

	@Override
	public void keyPressed(KeyEvent key) {
		if (key.getKeyCode() == KeyEvent.VK_ESCAPE && paused == false
				&& GameView.isVisible()) {
			playSoundextra("sounds/pausemenuopen.wav");
			GameView.windowframe.setVisible(true);
			GameView.setVisible(false);
			paused = true;
			GameView.clip.stop();
		} else if (key.getKeyCode() == KeyEvent.VK_ESCAPE && paused == true) {
			playSoundextra("sounds/pausemenuclose.wav");
			GameView.windowframe.setVisible(false);
			GameView.windowframe.dispose();
			GameView.setVisible(true);
			paused = false;
			GameView.clip.start();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// System.out.print("Released");

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// System.out.print("Typed");

	}

	public void burndisplay(FullHandException e2) {
		GameView.burned.removeAll();
		ImageIcon burnedicon;

		if (e2.getBurned().getName().equalsIgnoreCase("Shadow Word: Death"))
			burnedicon = new ImageIcon(
					"images/cards/card Shadow Word Death.png");
		else
			burnedicon = new ImageIcon("images/cards/card "
					+ e2.getBurned().getName() + ".png");

		Image burnedimage = burnedicon.getImage().getScaledInstance(
				125 * GameView.getWidth() / 1000,
				100 * GameView.getHeight() / 402, java.awt.Image.SCALE_SMOOTH);
		burnedicon = new ImageIcon(burnedimage);

		JLabel burnedlabel = new JLabel(burnedicon);
		burnedlabel.setPreferredSize(new Dimension(
				125 * GameView.getWidth() / 1000,
				100 * GameView.getHeight() / 402));

		if (e2.getBurned().getClass().getSimpleName().equals("Minion")
				|| e2.getBurned().getClass().getSimpleName().equals("Icehowl")) {

			JLabel attack = new JLabel(((Minion) e2.getBurned()).getAttack()
					+ "");
			attack.setForeground(Color.WHITE);

			if (((Minion) e2.getBurned()).getAttack() > 9) {

				attack.setBounds(21 * GameView.getWidth() / 1554,
						152 * GameView.getHeight() / 714,
						20 * GameView.getWidth() / 1554,
						20 * GameView.getHeight() / 714);
				attack.setFont(new Font("Arial", Font.BOLD, 17 * GameView
						.getWidth() / 1554));
			} else {
				attack.setBounds(25 * GameView.getWidth() / 1554,
						153 * GameView.getHeight() / 714,
						20 * GameView.getWidth() / 1554,
						20 * GameView.getHeight() / 714);

				attack.setFont(new Font("Arial", Font.BOLD, 19 * GameView
						.getWidth() / 1554));
			}
			burnedlabel.add(attack);

			JLabel health = new JLabel(((Minion) e2.getBurned()).getCurrentHP()
					+ "");
			health.setForeground(Color.WHITE);

			if (((Minion) e2.getBurned()).getCurrentHP() > 9) {
				health.setBounds(156 * GameView.getWidth() / 1554,
						152 * GameView.getHeight() / 714,
						20 * GameView.getWidth() / 1554,
						20 * GameView.getHeight() / 714);
				health.setFont(new Font("Arial", Font.BOLD, 17 * GameView
						.getWidth() / 1554));
			} else {
				health.setBounds(162 * GameView.getWidth() / 1554,
						153 * GameView.getHeight() / 714,
						20 * GameView.getWidth() / 1554,
						20 * GameView.getHeight() / 714);

				health.setFont(new Font("Arial", Font.BOLD, 19 * GameView
						.getWidth() / 1554));

			}
			burnedlabel.add(health);
		}
		JLabel manacost = new JLabel(e2.getBurned().getManaCost() + "");
		manacost.setForeground(Color.WHITE);

		if (e2.getBurned().getManaCost() > 9) {
			manacost.setBounds(11 * GameView.getWidth() / 1354,
					5 * GameView.getHeight() / 514,
					30 * GameView.getWidth() / 1354,
					30 * GameView.getHeight() / 514);
			manacost.setFont(new Font("Arial", Font.BOLD, 18 * GameView
					.getWidth() / 1054));
		} else {
			manacost.setBounds(17 * GameView.getWidth() / 1354,
					9 * GameView.getHeight() / 514,
					20 * GameView.getWidth() / 1354,
					20 * GameView.getHeight() / 514);

			manacost.setFont(new Font("Arial", Font.BOLD, 20 * GameView
					.getWidth() / 1054));
		}
		burnedlabel.add(manacost);

		GameView.bubbled.setText(e2.getMessage());
		GameView.bubbled.setVisible(true);

		if (timer != null)
			timer.stop();
		timer = new Timer(4000, this);
		timer.start();

		ImageIcon flameicon = new ImageIcon("images/flame.png");

		Image flameimg = flameicon.getImage().getScaledInstance(
				125 * GameView.getWidth() / 1000,
				100 * GameView.getHeight() / 402, java.awt.Image.SCALE_SMOOTH);
		flameicon = new ImageIcon(flameimg);
		JLabel flame = new JLabel(flameicon);
		flame.setOpaque(false);
		flame.setBackground(Color.BLACK);
		flame.setBounds(0, 0, 125 * GameView.getWidth() / 1000,
				100 * GameView.getHeight() / 402);

		burnedlabel.setLayout(null);

		burnedlabel.add(flame);

		GameView.burned.add(burnedlabel);

		GameView.burned.setVisible(true);

		GameView.backhandneedchange = true;
		GameView.fronthandneedchange = true;

		GameView.refreshView();
	}

}
