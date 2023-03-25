package fr.maxlego08.menu.button;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import fr.maxlego08.menu.api.action.Action;
import fr.maxlego08.menu.api.button.PerformButton;

public abstract class ZPerformButton extends ZSlotButton implements PerformButton{

	private List<String> commands;

	private List<String> consoleCommands;
	private List<String> consoleRightCommands;
	private List<String> consoleLeftCommands;

	private List<String> consolePermissionCommands;
	private String consolePermission;

	private List<Action> actions;

	@Override
	public List<String> getCommands() {
		return this.commands;
	}

	@Override
	public List<String> getConsoleCommands() {
		return this.consoleCommands;
	}

	@Override
	public List<String> getConsolePermissionCommands() {
		return this.consolePermissionCommands;
	}

	@Override
	public String getConsolePermission() {
		return this.consolePermission;
	}

	@Override
	public List<String> getConsoleRightCommands() {
		return this.consoleRightCommands;
	}

	@Override
	public List<String> getConsoleLeftCommands() {
		return this.consoleLeftCommands;
	}

	@Override
	public void execute(Player player, ClickType type) {

		if (type.equals(ClickType.RIGHT)) {
			this.execute(player, player, this.consoleRightCommands);
		}

		if (type.equals(ClickType.LEFT)) {
			this.execute(player, player, this.consoleLeftCommands);
		}

		this.execute(player, player, this.commands);
		this.execute(Bukkit.getConsoleSender(), player, this.consoleCommands);

		if (this.consolePermission == null || player.hasPermission(this.consolePermission)) {
			this.execute(Bukkit.getConsoleSender(), player, this.consolePermissionCommands);
		}

		this.actions.stream().filter(action -> action.isClick(type)).forEach(action -> action.execute(player, type));
	}

	/**
	 * Allows you to execute a list of commands
	 * 
	 * @param executor
	 * @param player
	 * @param strings
	 */
	private void execute(CommandSender executor, Player player, List<String> strings) {
		strings.forEach(command -> {
			command = command.replace("%player%", player.getName());
			Bukkit.dispatchCommand(executor, papi(command, player));
		});
	}

	@Override
	public List<Action> getActions() {
		return this.actions;
	}

	/**
	 * @param commands
	 *            the commands to set
	 */
	public void setCommands(List<String> commands) {
		this.commands = commands;
	}

	/**
	 * @param consoleCommands
	 *            the consoleCommands to set
	 */
	public void setConsoleCommands(List<String> consoleCommands) {
		this.consoleCommands = consoleCommands;
	}

	/**
	 * @param consoleRightCommands
	 *            the consoleRightCommands to set
	 */
	public void setConsoleRightCommands(List<String> consoleRightCommands) {
		this.consoleRightCommands = consoleRightCommands;
	}

	/**
	 * @param consoleLeftCommands
	 *            the consoleLeftCommands to set
	 */
	public void setConsoleLeftCommands(List<String> consoleLeftCommands) {
		this.consoleLeftCommands = consoleLeftCommands;
	}

	/**
	 * @param consolePermissionCommands
	 *            the consolePermissionCommands to set
	 */
	public void setConsolePermissionCommands(List<String> consolePermissionCommands) {
		this.consolePermissionCommands = consolePermissionCommands;
	}

	/**
	 * @param consolePermission
	 *            the consolePermission to set
	 */
	public void setConsolePermission(String consolePermission) {
		this.consolePermission = consolePermission;
	}

	/**
	 * @param actions
	 *            the actions to set
	 */
	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

}