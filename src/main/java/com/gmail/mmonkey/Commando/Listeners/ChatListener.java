package com.gmail.mmonkey.Commando.Listeners;

import com.gmail.mmonkey.Commando.Commando;
import com.gmail.mmonkey.Commando.Events.CommandoConvertTextEvent;
import com.gmail.mmonkey.Commando.Events.TextType;
import com.gmail.mmonkey.Commando.Filters.CommandFilter;
import com.gmail.mmonkey.Commando.Services.TextConvertingService;
import com.google.common.collect.Iterables;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.Collection;

public class ChatListener {
	
	private Commando plugin;

	@Listener
	public void onMessage(MessageChannelEvent.Chat event) {
		
		Collection<Text> text = new ArrayList<Text>();
		text.add(event.getRawMessage());

		event.getCause();
		
		CommandoConvertTextEvent parseEvent = new CommandoConvertTextEvent(TextType.PLAYER_MESSAGE, text, new CommandFilter());
		plugin.getGame().getEventManager().post(parseEvent);
		
		if (!parseEvent.isCancelled()) {
			
			TextConvertingService service = new TextConvertingService();
			service.setFilters(parseEvent.getFilters());
			service.setText(Iterables.get(parseEvent.getText(), 0));

			event.setMessage(service.process());
		}
		
	}
	
	public ChatListener(Commando plugin) {
		this.plugin = plugin;
	}
	

}
