package com.gmail.mmonkey.Commando.Filters;

import com.gmail.mmonkey.Commando.Match;
import org.spongepowered.api.text.Text;

import java.util.regex.Pattern;

public abstract class Filter {

	public abstract Pattern[] getPatterns();
	
	public abstract Text filter(Match match);
	
}
