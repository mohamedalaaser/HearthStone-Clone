package model.cards.spells;

import exceptions.InvalidTargetException;
import model.cards.Rarity;
import model.cards.minions.Minion;

public class ShadowWordDeath extends Spell implements MinionTargetSpell {

	public ShadowWordDeath() {
		super("Shadow Word: Death", 3, Rarity.BASIC);

	}

	@Override
	public void performAction(Minion m) throws InvalidTargetException {
		if (m.getAttack() < 5)
			throw new InvalidTargetException("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Choose a minion with<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5 or more attack</html>");
		m.setCurrentHP(0);

	}

}
