package com.gmail.mmonkey.Commando.Events;

import java.util.ArrayList;
import java.util.Collection;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.AbstractEvent;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.text.Text;

import com.gmail.mmonkey.Commando.Filters.Filter;
import com.google.common.base.Optional;

public class CommandoConvertTextEvent extends AbstractEvent implements Cancellable {
	
	private boolean cancelled = false;
	
	private Optional<Player> player;
	private Collection<Text> text = new ArrayList<Text>();
	private Collection<Filter> filters = new ArrayList<Filter>();
	private TextType type;
	
	public boolean isCancelled() {
		return this.cancelled;
	}

	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}
	
	public Optional<Player> getPlayer() {
		return this.player;
	}
	
	public TextType getTextType() {
		return this.type;
	}
	
	public Collection<Text> getText() {
		return this.text;
	}
	
	public void setText(Collection<Text> text) {
		this.text = text;
	}
	
	public Collection<Filter> getFilters() {
		return this.filters;
	}
	
	public void addFilter(Filter filter) {
		this.filters.add(filter);
	}
	
	public void addFilters(Filter... filters) {
		for(Filter filter: filters) {
			this.addFilter(filter);
		}
	}
	
	public void addFilters(Collection<Filter> filters) {
		this.filters.addAll(filters);
	}
	
	public void setFilters(Filter... filters) {
		this.filters.clear();
		this.addFilters(filters);
	}
	
	public void setFilters(Collection<Filter> filters) {
		this.filters = filters;
	}
	
	public CommandoConvertTextEvent(Player player, TextType type, Collection<Text> text, Collection<Filter> filters) {
		this.player = Optional.fromNullable(player);
		this.type = type;
		this.text = text;
		this.filters = filters;
	}
	
	public CommandoConvertTextEvent(Player player, TextType type, Collection<Text> text, Filter... filters) {
		this.player = Optional.fromNullable(player);
		this.type = type;
		this.text = text;
		this.setFilters(filters);
	}

}
