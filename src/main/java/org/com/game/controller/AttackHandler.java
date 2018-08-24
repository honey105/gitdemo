package org.com.game.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.com.character.Character;
import org.com.character.CharacterBodyPart;
import org.com.character.CharacterStatus;
import org.com.character.CharacterType;

public class AttackHandler {

	public static final Random RANDOM = new Random();

	private static Map<CharacterBodyPart, Integer> characterBodyPartAndHealthImpact = new HashMap<>();

	static {
		characterBodyPartAndHealthImpact.put(CharacterBodyPart.Legs, 5);
		characterBodyPartAndHealthImpact.put(CharacterBodyPart.Arms, 10);
		characterBodyPartAndHealthImpact.put(CharacterBodyPart.Head, 15);
		characterBodyPartAndHealthImpact.put(CharacterBodyPart.Heart, 25);
	}

	public static String attack(Character attackedBy, Character attackedTo) {

		StringBuilder messageBuilder = null;
		int characterMinimumAttackingPower = attackedBy.getAttackingMinPower();
		int characterMaximumAttackingPower = attackedBy.getAttackingMaxPower();
		int characterAttackingPower = getAttackingPower(characterMinimumAttackingPower, characterMaximumAttackingPower);
		int weaponDamagingEffect = attackedBy.getWeapon().getDamageEffect();
		CharacterBodyPart bodyPartHitted = CharacterBodyPart.values()[new Random()
				.nextInt(CharacterBodyPart.values().length)];

		int bodyPartHittedImpact = characterBodyPartAndHealthImpact.get(bodyPartHitted);
		int damageEffect = characterAttackingPower + weaponDamagingEffect * attackedBy.getExperience()
				+ bodyPartHittedImpact;
		damageEffect = damageEffect > 100 ? 100 : damageEffect;
		messageBuilder = new StringBuilder(attackedBy.getName()).append(" Attack on ");
		attackedTo.setHealth(attackedTo.getHealth() - damageEffect);

		if (attackedTo.getHealth() <= 0) {
			attackedTo.setCharacterStatus(CharacterStatus.dead);
			attackedTo.setHealth(0);
		}
		messageBuilder.append(attackedTo.getName()).append("'s ").append(bodyPartHitted.toString());

		messageBuilder.append(" and gave ").append(damageEffect).append(" damages ");

		return messageBuilder.toString();
	}

	private static int getAttackingPower(int minPower, int maxPower) {

		int number = RANDOM.nextInt(maxPower - minPower) + minPower;
		return number;
	}

}
