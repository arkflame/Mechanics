package dev._2lstudios.mechanics.player;

public class GameMechanicsPlayer {
	private boolean magnet = true, cobblestone = false, dirt = false;

	public void setMagnet(boolean magnet) {
		this.magnet = magnet;
	}

	public boolean isMagnet() {
		return magnet;
	}

	public void setCobblestone(final boolean cobblestone) {
		this.cobblestone = cobblestone;
	}

	public boolean isCobblestone() {
		return this.cobblestone;
	}

	public void setDirt(final boolean dirt) {
		this.dirt = dirt;
	}

	public boolean isDirt() {
		return this.dirt;
	}
}
