package org.com.game.state;

public enum GameLevel {

	First("1", "This is first level"), Second("2", "This is second level");

	private String levelName;
	private String desciption;

	private GameLevel(String levelName, String levelDesc) {
		this.levelName = levelName;
		this.desciption = levelDesc;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getDesciption() {
		return desciption;
	}

	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}

}
