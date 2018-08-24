package org.com.game.controller;

public class MenuController {

	public static String mainMenuItems[] = { "\t 1. Launch New Game ", "\t 2. Load Existing Game ",
			"\t 3. Don't Want to Play " };

	public static String gameMenuItems[] = { "\t 1. Meet Mission Master ",
			"\t 2. Start Mission", "\t 3. Save Game State ",
			"\t 4. Go Back "};

	public static String createCharacterMenuItems[] = { "\t 1. Create Warrior ", "\t 2. << Go Back  "};
	
	public static String fightMenuItems [] = {"\t 1. Attack ", "\t 2. Back "};
	
	public static String missionMenuItems [] = {"\t 1. East ", "\t 2. West ", "\t 3. North ", "\t 4. Back"};

	public static void renderMenuItems(String menuItems[]) {
		for (String menuItem : menuItems) {
			System.out.println();
			System.out.println(menuItem);
		}
		System.out.println();
		System.out.println("What do you want to do ? ");
	}

}
