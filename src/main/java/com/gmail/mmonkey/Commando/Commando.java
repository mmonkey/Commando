package com.gmail.mmonkey.Commando;

import com.gmail.mmonkey.Commando.Listeners.ChatListener;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Game;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

import java.io.File;

@Plugin(id = Commando.ID, name = Commando.NAME, version = Commando.VERSION)
public class Commando {
	
	public static final String NAME = "Commando";
	public static final String ID = "Commando";
	public static final String VERSION = "0.0.2-2.1";

	/**
	 * Commando
	 */
	private static Commando instance;

	@Inject
	private Game game;

	@Inject
	private static Logger logger;
	
	@Inject
	@ConfigDir(sharedRoot = false)
	private File configDir;

	/**
	 * @return Commando
	 */
	public static Commando getInstance() {
		return instance;
	}
	
	public Game getGame() {
		return this.game;
	}
	
	public static Logger getLogger() {
		return logger;
	}
	
	@Listener
	public void onPreInit(GamePreInitializationEvent event) {

		Commando.instance = this;
		Commando.logger = LoggerFactory.getLogger(Commando.NAME);
		
		getLogger().info(String.format("Starting up %s v%s.", Commando.NAME, Commando.VERSION));
			
		if (!this.configDir.isDirectory()) {
			this.configDir.mkdirs();
		}
			
	}
	
	@Listener
	public void onInit(GameInitializationEvent event) {
		
		game.getEventManager().registerListeners(this, new ChatListener(this));
//		game.getEventManager().registerListeners(this, new SignListener(this));
	
	}

}
