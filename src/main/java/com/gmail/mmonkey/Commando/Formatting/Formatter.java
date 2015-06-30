package com.gmail.mmonkey.Commando.Formatting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextBuilder;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandMessageFormatting;

public class Formatter {

	public static final Pattern thirdWidthCharacters = Pattern.compile("[.,;:'\\|!]");
	public static final Pattern spaces = Pattern.compile(" ");
	public static final Pattern fullWidth = Pattern.compile("[^.,;:'\\|! ]");
	
	public static Text clear(int numLines) {
		
		TextBuilder text = Texts.builder();
		
		for (int i = 0; i < numLines; i++) {
			text.append(CommandMessageFormatting.NEWLINE_TEXT);
		}
		
		return text.build();
		
	}

	public static Text clear() {
		
		return clear(20);
		
	}
	
	public static String fill(int length, char fill) {
		return new String(new char[length]).replace('\0', fill);
	}
	
	public static Text center(Text input, int width) {
		
		int length = getCharacterWidth(Texts.toPlain(input));
		
		if (length < width) {
			
			int half = (width - length) / 2;
			int padding = half * 3 / 2;
			
			TextBuilder text = Texts.builder();
			text.append(Texts.of(fill(padding, ' ')));
			text.append(Texts.of(input));
			text.append(Texts.of(fill(padding, ' ')));
			
			return text.build();
		}
		
		return input;
		
	}
	
	private static int getCharacterWidth(String input) {
		
		Matcher matcher = thirdWidthCharacters.matcher(input);
		
		int thirdWidth = 0;
		while (matcher.find()) {
		    thirdWidth++;
		}
		
		matcher = spaces.matcher(input);
		int spaces = 0;
		while (matcher.find()) {
			spaces++;
		}
		
		matcher = fullWidth.matcher(input);
		int fullWidth = 0;
		while (matcher.find()) {
			fullWidth++;
		}
		
		return (thirdWidth / 3) + (spaces * 2 / 3) + fullWidth;

	}
}
