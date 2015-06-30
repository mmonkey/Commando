package com.gmail.mmonkey.Commando;

import java.io.File;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.state.InitializationEvent;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.config.ConfigDir;

import com.gmail.mmonkey.Commando.Listeners.ChatListener;
import com.gmail.mmonkey.Commando.Listeners.SignListener;
import com.google.common.base.Optional;
import com.google.inject.Inject;

@Plugin(id = Commando.ID, name = Commando.NAME, version = Commando.VERSION)
public class Commando {
	
	public static final String NAME = "Commando";
	public static final String ID = "Commando";
	public static final String VERSION = "0.0.1-2.1";
	
	private Game game;
	private Optional<PluginContainer> pluginContainer;
	private static Logger logger;
	
	@Inject
	@ConfigDir(sharedRoot = false)
	private File configDir;
	
	public Game getGame() {
		return this.game;
	}
	
	public Optional<PluginContainer> getPluginContainer() {
		return this.pluginContainer;
	}
	
	public static Logger getLogger() {
		return logger;
	}
	
	@Subscribe
	public void onPreInit(PreInitializationEvent event) {
		
		this.game = event.getGame();
		this.pluginContainer = game.getPluginManager().getPlugin(Commando.NAME);
		Commando.logger = game.getPluginManager().getLogger(pluginContainer.get());
		
		getLogger().info(String.format("Starting up %s v%s.", Commando.NAME, Commando.VERSION));
			
		if (!this.configDir.isDirectory()) {
			this.configDir.mkdirs();
		}
			
	}
	
	@Subscribe
	public void onInit(InitializationEvent event) {
		
		game.getEventManager().register(this, new ChatListener(this));
		game.getEventManager().register(this, new SignListener(this));
	
	}

}
