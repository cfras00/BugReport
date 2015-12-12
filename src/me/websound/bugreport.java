package me.websound;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

public class bugreport extends JavaPlugin {

	
	public Permission playerPermission1 = new Permission("delbugs.allow");
	public Permission playerPermission = new Permission("listbugs.allow");
	@Override
	public void onEnable() {
		getLogger().info("BugzReport plugin enabled! Rembember this plugin is in beta mode.");
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player player = (Player) sender;
		
		FileConfiguration config = this.getConfig();
		
		//Reporting a bug
		if (cmd.getName().equalsIgnoreCase("reportbug") && sender instanceof Player) {
			
			if (args.length >= 1) {
				
				String bugMessage = "";
				
				for (String arg : args) {
					bugMessage = bugMessage + arg + " ";
				}
				
				//Handling arguments
				if (!config.contains(player.getName().toLowerCase())) {
					config.set(player.getName().toLowerCase(), bugMessage);
					player.sendMessage(ChatColor.GREEN + "Bug Report Submitted succesfully!");
					saveConfig();
				} else player.sendMessage(ChatColor.RED + "You have already submitted a report! please wait for a admin to review the report." );
				
			} else player.sendMessage(ChatColor.RED + "Enter Your bug report after the /bug command!");
			
			return true;
			
		}
		
		//Viewing all bugs
		if (cmd.getName().equalsIgnoreCase("listbugs") && sender instanceof Player) {
	
			if (player.isOp()) {
			
				//Iterating through each bug report in config
				for (String key : config.getKeys(false)) {
					player.sendMessage(ChatColor.YELLOW + key + ": " + config.getString(key));
				}
				
				if (config.getKeys(false).isEmpty()) {
					player.sendMessage(ChatColor.GREEN + "No bugs have been reported!");
				}
				
			} else player.sendMessage(ChatColor.RED + "You must be opped or have permission to view bugs");
			
			return true;
		} 
		//Deleting a bug
		if (cmd.getName().equalsIgnoreCase("delbug") && sender instanceof Player) {
			
			if (args.length == 1 && player.isOp()) {
				
				if (config.contains(args[0].toLowerCase())) {
					
					config.set(args[0].toLowerCase(), null);
					saveConfig();
					player.sendMessage(ChatColor.GREEN + "Report sccessfully removed!");
					
					
				} else player.sendMessage(ChatColor.RED + "Player not found");
					
					
				}else player.sendMessage(ChatColor.RED + "Inncorrect arguments / invalid permission!");

			
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("bugzdev") && sender instanceof Player) {
			player.sendMessage(ChatColor.GOLD + "BugzReport " + ChatColor.RED + "plugin Developed by:" + ChatColor.GREEN + " __II__");
		 return true;
		}
		
		return false;	
		
	}
	
}