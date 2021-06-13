package engine;

import java.io.IOException;

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
import model.cards.minions.Minion;
import model.heroes.Hero;
import model.heroes.HeroListener;
import model.heroes.Mage;

public class Game implements ActionValidator, HeroListener {
	private Hero firstHero;
	private Hero secondHero;
	private Hero currentHero;
	private Hero opponent;

	private GameListener listener;

	public Game(Hero p1, Hero p2) throws FullHandException,
			CloneNotSupportedException {
		firstHero = p1;
		secondHero = p2;
		firstHero.setListener(this);
		secondHero.setListener(this);
		firstHero.setValidator(this);
		secondHero.setValidator(this);
		int coin = (int) (Math.random() * 2);
		currentHero = coin == 0 ? firstHero : secondHero;
		opponent = currentHero == firstHero ? secondHero : firstHero;
		currentHero.setCurrentManaCrystals(1);
		currentHero.setTotalManaCrystals(1);
			
		

		for (int i = 0; i < 3; i++) {
			currentHero.drawCard();
		}
		for (int i = 0; i < 4; i++) {
			opponent.drawCard();
		}
	}

	@Override
	public void validateTurn(Hero user) throws NotYourTurnException {
		if (user == opponent)
			throw new NotYourTurnException(
					"<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;You can not do any action <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; in your opponent's turn</html>");
	}

	public void validateAttack(Minion a, Minion t) throws TauntBypassException,
			InvalidTargetException, NotSummonedException, CannotAttackException {
		if (a.getAttack() <= 0)
			throw new CannotAttackException("This minion Cannot Attack");
		if (a.isSleeping())
			throw new CannotAttackException(
					"<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Give this minion a turn <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; to get ready");
		if (a.isAttacked())
			throw new CannotAttackException("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;This minion has already<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;attacked");
		if (!currentHero.getField().contains(a))
			throw new NotSummonedException(
					"You can not attack with a minion that has not been summoned yet"); // will not show
		if (currentHero.getField().contains(t))
			throw new InvalidTargetException(
					"<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;You can not attack a <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; friendly minion</html>");
		if (!opponent.getField().contains(t))
			throw new NotSummonedException(
					"You can not attack a minion that your opponent has not summoned yet"); // will not show
		if (!t.isTaunt()) {
			for (int i = 0; i < opponent.getField().size(); i++) {
				if (opponent.getField().get(i).isTaunt())
					throw new TauntBypassException(
							"<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A minion with taunt is <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;in the way</html>");
			}

		}

	}

	public void validateAttack(Minion m, Hero t) throws TauntBypassException,
			NotSummonedException, InvalidTargetException, CannotAttackException {
		if (m.getAttack() <= 0)
			throw new CannotAttackException("This minion Cannot Attack");
		if (m.isSleeping())
			throw new CannotAttackException(
					"<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Give this minion a turn <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; to get ready");
		if (m.isAttacked())
			throw new CannotAttackException("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;This minion has already<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;attacked");
		if (!currentHero.getField().contains(m))
			throw new NotSummonedException(
					"You can not attack with a minion that has not been summoned yet");// will not show
		if (t.getField().contains(m))
			throw new InvalidTargetException(
					"<html>&nbsp;&nbsp;&nbsp;You can not attack yourself<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;with your minions</html>");
		for (int i = 0; i < opponent.getField().size(); i++) {
			if (opponent.getField().get(i).isTaunt())
				throw new TauntBypassException(
						"<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A minion with taunt is <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;in the way</html>");
		}
	}

	public void validateManaCost(Card c) throws NotEnoughManaException {
		if (currentHero.getCurrentManaCrystals() < c.getManaCost())
			throw new NotEnoughManaException("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;I don't have enough mana !!</html>");
	}

	public void validatePlayingMinion(Minion m) throws FullFieldException {
		if (currentHero.getField().size() == 7)
			throw new FullFieldException("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No space for this minion</html>");
	}

	public void validateUsingHeroPower(Hero h) throws NotEnoughManaException,
			HeroPowerAlreadyUsedException {
		if (h.getCurrentManaCrystals() < 2)
			throw new NotEnoughManaException("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;I don't have enough mana !!</html>");
		if (h.isHeroPowerUsed())
			throw new HeroPowerAlreadyUsedException(
					"<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; I already used my hero power</html>");
	}

	@Override
	public void onHeroDeath() {

		listener.onGameOver();

	}

	@Override
	public void damageOpponent(int amount) {

		opponent.setCurrentHP(opponent.getCurrentHP() - amount);
	}

	public Hero getCurrentHero() {
		return currentHero;
	}

	public void setListener(GameListener listener) {
		this.listener = listener;
	}

	@Override
	public void endTurn() throws FullHandException, CloneNotSupportedException {
		Hero temp = currentHero;
		currentHero = opponent;
		opponent = temp;
		currentHero
				.setTotalManaCrystals(currentHero.getTotalManaCrystals() + 1);
		currentHero.setCurrentManaCrystals(currentHero.getTotalManaCrystals());
		currentHero.setHeroPowerUsed(false);
		for (Minion m : currentHero.getField()) {
			m.setAttacked(false);
			m.setSleeping(false);
		}
		currentHero.drawCard();

	}

	public Hero getOpponent() {
		return opponent;
	}

	public static void main(String[] args) {
		try {
			Hero h1 = new Mage();
			Hero h2 = new Mage();
			Game g = new Game(h1, h2);

			g.currentHero.endTurn();
			g.currentHero.endTurn();
			g.currentHero.endTurn();

			try {
				((Mage) (g.getCurrentHero())).useHeroPower(h2);
				System.out.println(h1.getCurrentHP());
				System.out.println(h2.getCurrentHP());
			} catch (NotEnoughManaException | HeroPowerAlreadyUsedException
					| NotYourTurnException | FullFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		} catch (IOException | CloneNotSupportedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FullHandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
