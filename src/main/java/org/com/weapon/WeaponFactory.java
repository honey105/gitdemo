package org.com.weapon;

public class WeaponFactory {

	public static Weapon createWeapon(WeaponType type) {

		switch (type) {
		case Knife: {
			Weapon knife = new Knife();
			return knife;
		}

		case Sword: {
			Weapon sword = new Sword();
			return sword;
		}

		case HandGun: {
			Weapon handGun = new HandGun();
			return handGun;
		}

		case Rifle: {

			Weapon rifle = new Rifle();
			return rifle;
		}

		case BowAndArrow: {

			Weapon bowAndArrow = new BowAndArrow();
			return bowAndArrow;
		}

		default: {
			return null;
		}

		}

	}

}
