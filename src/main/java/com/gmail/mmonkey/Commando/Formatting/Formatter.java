package com.gmail.mmonkey.Commando.Formatting;

import org.spongepowered.api.text.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Formatter {

	public static final Pattern thirdWidthCharacters = Pattern.compile("[.,;:'\\|!]");
	public static final Pattern spaces = Pattern.compile(" ");
	public static final Pattern fullWidth = Pattern.compile("[^.,;:'\\|! ]");
	
	public static Text clear(int numLines) {
		
		Text.Builder text = Text.builder();
		
		for (int i = 0; i < numLines; i++) {
			text.append(Text.of(System.lineSeparator()));
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

		int length = getCharacterWidth(input.toPlain());
		
		if (length < width) {
			
			int half = (width - length) / 2;
			int padding = half * 3 / 2;
			
			Text.Builder text = Text.builder();
			text.append(Text.of(fill(padding, ' ')));
			text.append(Text.of(input));
			text.append(Text.of(fill(padding, ' ')));
			
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
