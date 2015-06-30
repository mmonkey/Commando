package com.gmail.mmonkey.Commando.Services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextBuilder;
import org.spongepowered.api.text.Texts;

import com.gmail.mmonkey.Commando.Match;
import com.gmail.mmonkey.Commando.Filters.Filter;

public class TextConvertingService {
	
	private static final Pattern titleBefore = Pattern.compile("\\((.+?)\\)\\[([^]]+)\\]");
	private static final Pattern titleAfter = Pattern.compile("\\[([^]]+)\\]\\((.+?)\\)");
	private Collection<Filter> filters = Collections.emptyList();
	private Text text = null;
	private String raw = null;
	
	public void setText(Text text) {
		this.text = text;
		this.raw = Texts.toPlain(text);
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
	
	public Text process() {
		
		if (this.text == null) {
			return Texts.of("");
		}
		
		if (this.filters.isEmpty()) {
			return this.text;
		}
		
		List<Match> matches = new ArrayList<Match>();
		
		for(Filter filter:filters) {
			for(Pattern pattern:filter.getPatterns()) {
				
				Matcher matcher = pattern.matcher(this.raw);
				
				while (matcher.find()) {
					
					String title = "";
					int start = -1;
					int end = -1;
					
					String pre = this.raw.substring(0, matcher.end());
					Matcher preMatch = titleBefore.matcher(pre);
					if (preMatch.find()) {
						if (preMatch.end() == matcher.end()) {
							title = preMatch.group(1);
							start = preMatch.start();
						}
					}
					
					String post = this.raw.substring(matcher.start(), this.raw.length());
					Matcher postMatch = titleAfter.matcher(post);
					if (postMatch.find()) {
						if (postMatch.start() == 0) {
							title = postMatch.group(2);
							end = postMatch.end() + matcher.start();
						}
					}
					
					Match match = new Match();
					match.setTitle(title);
					match.setContent(matcher.group(1));
					match.setStart((start > -1) ? start : matcher.start());
					match.setEnd((end > -1) ? end : matcher.end());
					match.setFilter(filter);

					matches.add(match);
				}
			}
		}
		
		return processMatches(matches);
	}
	
	private Text processMatches(List<Match> matches) {
		
		String original = this.raw;
		
		if (!matches.isEmpty()) {
			
			TextBuilder builder = Texts.builder();
			StringBuilder piece = new StringBuilder();
			
			for (int i = 0; i < original.length(); i++) {
				
				int index = i;
				for (Match match:matches) {
					if (i == match.getStart()) {
						builder.append(Texts.of(piece.toString()));
						piece = new StringBuilder();
						builder.append(match.getFilter().filter(match));
						i = match.getEnd() - 1;
					}
				}
				
				if (i == index) {
					piece.append(original.charAt(index));
				}
				
				if (index == (original.length() - 1)) {
					builder.append(Texts.of(piece.toString()));
				}
				
			}
			
			return builder.build();
		
		}
		
		return Texts.of(original);
		
	}
	
	public TextConvertingService() {
	}
}
