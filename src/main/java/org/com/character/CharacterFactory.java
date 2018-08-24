package org.com.character;

public class CharacterFactory {

	public static Character createCharacter(CharacterType type) {

		switch (type) {
		case StreetFighter: {
			Character streetFighter = new StreetFighter();
			return streetFighter;
		}
		case Knight: {
			Character knight = new Knight();
			return knight;
		}

		case Warrior: {
			Character warrior = new Warrior();
			return warrior;
		}

		case Enemy: {
			Character enemy = new Enemy();
			return enemy;
		}

		default: {
			return null;
		}

		}

	}

}
