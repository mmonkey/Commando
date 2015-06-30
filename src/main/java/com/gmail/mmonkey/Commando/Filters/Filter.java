package com.gmail.mmonkey.Commando.Filters;

import java.util.regex.Pattern;

import org.spongepowered.api.text.Text;

import com.gmail.mmonkey.Commando.Match;

public abstract class Filter {

	public abstract Pattern[] getPatterns();
	
	public abstract Text filter(Match match);
	
}
