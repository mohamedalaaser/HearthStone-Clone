package model.cards.spells;

import model.cards.Rarity;
import model.cards.minions.Minion;
import model.heroes.Hero;

public class Pyroblast extends Spell implements MinionTargetSpell, HeroTargetSpell {
	public Pyroblast() {
		super("Pyroblast", 10, Rarity.EPIC);
	}

	@Override
	public void performAction(Minion m) {
		if (m.isDivine())
			m.setDivine(false);
		else
			m.setCurrentHP(m.getCurrentHP() - 10);

	}

	@Override
	public void performAction(Hero h) {
		h.setCurrentHP(h.getCurrentHP() - 10);

	}

}
