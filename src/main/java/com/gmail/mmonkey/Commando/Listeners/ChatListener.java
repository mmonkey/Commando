package com.gmail.mmonkey.Commando.Listeners;

import java.util.ArrayList;
import java.util.Collection;

import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerChatEvent;
import org.spongepowered.api.text.Text;

import com.gmail.mmonkey.Commando.Commando;
import com.gmail.mmonkey.Commando.Events.CommandoConvertTextEvent;
import com.gmail.mmonkey.Commando.Events.TextType;
import com.gmail.mmonkey.Commando.Filters.CommandFilter;
import com.gmail.mmonkey.Commando.Services.TextConvertingService;
import com.google.common.collect.Iterables;

public class ChatListener {
	
	private Commando plugin;

	@Subscribe
	public void onMessage(PlayerChatEvent event) {
		
		Collection<Text> text = new ArrayList<Text>();
		text.add(event.getMessage());
		
		CommandoConvertTextEvent parseEvent = new CommandoConvertTextEvent(event.getSource(), TextType.PLAYER_MESSAGE, text, new CommandFilter());
		plugin.getGame().getEventManager().post(parseEvent);
		
		if (!parseEvent.isCancelled()) {
			
			TextConvertingService service = new TextConvertingService();
			service.setFilters(parseEvent.getFilters());
			service.setText(Iterables.get(parseEvent.getText(), 0));
			
			event.setNewMessage(service.process());
			
		}
		
	}
	
	public ChatListener(Commando plugin) {
		this.plugin = plugin;
	}
	

}
