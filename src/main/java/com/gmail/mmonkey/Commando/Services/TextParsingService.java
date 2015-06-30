package com.gmail.mmonkey.Commando.Services;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.ClickAction;

import com.gmail.mmonkey.Commando.Action;
import com.gmail.mmonkey.Commando.ActionType;

public class TextParsingService {

	private List<Text> text;
	
	public List<Text> getText() {
		return this.text;
	}
	
	public void setText(List<Text> text) {
		this.text = text;
	}
	
	public List<Text> getParts(List<Text> fullText) {
		
		List<Text> parts = new ArrayList<Text>();
		this.getChildren(parts, fullText);
		
		return parts;
		
	}
	
	public void getChildren(List<Text> parts, List<Text> children) {
		
		for(Text child: children) {
			parts.add(child);
		
			if(!child.getChildren().isEmpty()) {
				this.getChildren(parts, child.getChildren());
			}
		}

	}
	
	public List<Action> parse() {
		
		List<Text> parts = this.getParts(this.text);
		List<Action> actions = new ArrayList<Action>();

		for (Text part: parts) {
			Action action = parseClickAction(part);
			
			if (action != null) {
				actions.add(action);
			}
		}
		
		return actions;

	}
	
	public Action parseClickAction(Text line) {
		
		if (line.getClickAction().isPresent()) {
			
			ClickAction<?> clickAction = line.getClickAction().get();
			
			if (clickAction instanceof ClickAction.OpenUrl) {
				return new Action(ActionType.OPEN_URL, clickAction.getResult().toString());
			}
			
			if (clickAction instanceof ClickAction.RunCommand) {
				return new Action(ActionType.RUN_COMMAND, clickAction.getResult().toString());
			}
			
			if (clickAction instanceof ClickAction.SuggestCommand) {
				return new Action(ActionType.SUGGEST_COMMAND, clickAction.getResult().toString());
			}
		
		}
		
		return null;
	}
	
	public TextParsingService() {
	}
	
}
