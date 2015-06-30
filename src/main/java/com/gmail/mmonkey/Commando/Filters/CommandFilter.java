package com.gmail.mmonkey.Commando.Filters;

import java.util.regex.Pattern;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import com.gmail.mmonkey.Commando.Match;

public class CommandFilter extends Filter {

	@Override
	public Pattern[] getPatterns() {
		return new Pattern[] {Pattern.compile("\\[command:([^]]+)\\]"), Pattern.compile("\\[c:([^]]+)\\]")};
	}

	@Override
	public Text filter(Match match) {
		
		String command = match.getContent();
		
		if (command.startsWith("/")) {
			command = command.substring(1);
		}
		
		String[] words = command.split(" ");
		String preview = (words.length > 0) ? words[0] : "";
		
		if (command.length() > 0 && preview.length() > 0) {
			return getCommandAction(match.getTitle(), command, preview);
		}
		
		return Texts.of(match.getContent());
	}
	
	private Text getCommandAction(String title, String command, String preview) {
		String showText = (title.length() > 0) ? title : preview;
		return Texts.builder(showText)
			.onClick(TextActions.runCommand("/" + command))
			.onHover(TextActions.showText(Texts.of(TextColors.GOLD, "/" + command)))
			.color(TextColors.AQUA)
			.style(TextStyles.UNDERLINE)
			.build();
	}
	
	public CommandFilter() {
	}

}
