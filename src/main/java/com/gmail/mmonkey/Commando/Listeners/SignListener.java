//package com.gmail.mmonkey.Commando.Listeners;
//
//import com.gmail.mmonkey.Commando.Action;
//import com.gmail.mmonkey.Commando.Commando;
//import com.gmail.mmonkey.Commando.Events.CommandoConvertTextEvent;
//import com.gmail.mmonkey.Commando.Events.TextType;
//import com.gmail.mmonkey.Commando.Filters.CommandFilter;
//import com.gmail.mmonkey.Commando.Formatting.Formatter;
//import com.gmail.mmonkey.Commando.Services.TextConvertingService;
//import com.gmail.mmonkey.Commando.Services.TextParsingService;
//import com.google.common.collect.Iterables;
//import org.spongepowered.api.block.tileentity.TileEntity;
//import org.spongepowered.api.block.tileentity.TileEntityTypes;
//import org.spongepowered.api.event.Listener;
//import org.spongepowered.api.text.Text;
//import org.spongepowered.api.text.action.TextActions;
//import org.spongepowered.api.text.format.TextColors;
//import org.spongepowered.api.text.format.TextStyles;
//import org.spongepowered.api.world.Location;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.List;
//
//public class SignListener {
//
//	private Commando plugin;
//
//	@Listener
//	public void onSignChange(SignChangeEvent event) {
//
//		Object cause = event.getCause().get().getCause();
//		Player player = null;
//		if (cause instanceof Player) {
//			player = (Player) cause;
//		}
//
//		SignData data = event.getNewData();
//		List<Text> lines = data.getLines();
//
//		CommandoConvertTextEvent parseEvent = new CommandoConvertTextEvent(player, TextType.SIGN, lines, new CommandFilter());
//		plugin.getGame().getEventManager().post(parseEvent);
//
//		if (!parseEvent.isCancelled() && !parseEvent.getText().isEmpty()) {
//
//			TextConvertingService service = new TextConvertingService();
//			service.setFilters(parseEvent.getFilters());
//
//			for(int i = 0; i < parseEvent.getText().size(); i++) {
//				service.setText(Iterables.get(parseEvent.getText(), i));
//				data.setLine(i, service.process());
//			}
//
//			event.setNewData(data);
//		}
//
//	}
//
//	@Subscribe
//	public void playerInteractSign(PlayerInteractBlockEvent event) {
//
//		Location block = event.getBlock();
//		Player player = event.getUser();
//
//		if (!event.getInteractionType().equals(EntityInteractionTypes.USE)) {
//			return;
//		}
//
//		if (block.getTileEntity().isPresent()) {
//
//			TileEntity sign = block.getTileEntity().get();
//
//			if (sign.getType() == TileEntityTypes.SIGN) {
//
//				Optional<SignData> data = sign.getOrCreate(SignData.class);
//
//				if (data.isPresent()) {
//
//					List<Text> lines = data.get().getLines();
//					TextParsingService service = new TextParsingService();
//					service.setText(lines);
//					this.processActions(player, service.parse(), lines);
//
//				}
//
//			}
//
//		}
//
//	}
//
//	private void processActions(Player player, List<Action> actions, List<Text> lines) {
//
//		if (actions.isEmpty()) {
//			return;
//		}
//
//		if (actions.size() == 1) {
//
//			this.handleAction(player, Iterables.get(actions, 0));
//
//		} else {
//
//			TextBuilder message = Texts.builder();
//
//			message.append(Formatter.clear(16));
//			message.append(Texts.of(Formatter.fill(26, '/'), Formatter.fill(26, '\\')));
//			message.append(CommandMessageFormatting.NEWLINE_TEXT);
//			message.append(CommandMessageFormatting.NEWLINE_TEXT);
//
//			for(Text line: lines) {
//				message.append(Formatter.center(line, 52));
//				message.append(CommandMessageFormatting.NEWLINE_TEXT);
//			}
//
//			message.append(Texts.of(Formatter.fill(26, '\\'), Formatter.fill(26, '/')));
//			player.sendMessage(message.build());
//
//		}
//
//	}
//
//	private void handleAction(Player player, Action action) {
//
//		switch (action.getType()) {
//
//		case OPEN_URL:
//
//			try {
//
//				TextBuilder message = Texts.builder();
//				message.append(Texts.of(TextColors.WHITE, "Go to: "));
//				message.append(Texts.builder(action.getValue())
//					.onClick(TextActions.openUrl(new URL(action.getValue())))
//					.onHover(TextActions.showText(Texts.of(TextColors.GOLD, "open url")))
//					.color(TextColors.AQUA)
//					.style(TextStyles.UNDERLINE)
//					.build());
//
//				player.sendMessage(message.build());
//
//			} catch (MalformedURLException e) {
//			}
//
//			break;
//
//		case RUN_COMMAND:
//
//			String command = action.getValue();
//
//			if (command.startsWith("/")) {
//				command = command.substring(1);
//			}
//
//			plugin.getGame().getCommandDispatcher().process(player, command);
//			break;
//
//		case SUGGEST_COMMAND:
//
//			String suggestion = action.getValue();
//
//			if (suggestion.startsWith("/")) {
//				suggestion = suggestion.substring(1);
//			}
//
//			Text message = Texts.builder("Command Suggestion")
//				.onClick(TextActions.suggestCommand("/" + suggestion))
//				.color(TextColors.AQUA)
//				.style(TextStyles.UNDERLINE)
//				.build();
//
//			player.sendMessage(message);
//
//			break;
//
//		}
//
//	}
//
//	public SignListener(Commando plugin) {
//		this.plugin = plugin;
//	}
//}
