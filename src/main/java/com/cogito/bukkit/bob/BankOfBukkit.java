
package com.cogito.bukkit.bob;

import java.io.File;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * Miscellaneous administrative commands
 *
 * @author Cogito
 */
public class BankOfBukkit extends JavaPlugin {

    private static String currencySymbol;
    private Map<Player, PlayerAccount> playerAccounts;
    private Map<Account, Teller> tellers;
    private Map<Teller, Thread> tellerThreads;

    public BankOfBukkit(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader) {
        super(pluginLoader, instance, desc, folder, plugin, cLoader);
        currencySymbol = "$";
    }

    public void onDisable() {
        //PluginManager pm = getServer().getPluginManager();
        
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled!" );
    }

    public void onEnable() {
        //PluginManager pm = getServer().getPluginManager();
        
        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }

    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        String[] trimmedArgs = args;
        String commandName = command.getName().toLowerCase();
        
        Player player = null;
        if (sender.isPlayer()) {
            player = (Player)sender;
        } else if (args.length > 0){
            // Assume first parameter of args is a player and remove it. null if player not found. 
            player = getServer().getPlayer(args[0]);
            trimmedArgs = new String[args.length-1];
            for(int i = 1; i < args.length; i++)
                trimmedArgs[i-1] = args[i];
        }

        if (commandName.equals("bob")) {
            return parseCommands(sender, player, trimmedArgs);
        }
        return false;
    }
    
    public boolean parseCommands(CommandSender sender, Player player, String[] args) {
        if (args.length > 0) {
            String dialogue = args[0];
            if (dialogue.equalsIgnoreCase("account")) {
                
            } else if (dialogue.equalsIgnoreCase("transfer")) {
                
            } else if (dialogue.equalsIgnoreCase("help")) {
                if(args.length > 1){
                    printHelp(sender, args[1]);
                } else {
                    printHelp(sender);
                }
            } else { return false; }
        } else { return false; }
        
        return true;
    }
    
    private void printHelp(CommandSender sender) {
        printHelpHeader(sender);
    }
    
    private void printHelp(CommandSender sender, String dialogue) {
        printHelpHeader(sender);
        sender.sendMessage(
            "BOB is a banking institute designed to be flexible and helpful.\n"+
            "We provide very basic services such as account creation and money transfers.\n"+
            "Other providers may be able to help you with your specific needs.\n"+
            "To get started with BOB, type \\bob account. BOB loves diamonds,\n"+
            "so if you need cash we can always exchange them for you."
        );
    }

    private void printHelpHeader(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "== BOB Help ==");
        if(sender.isPlayer()){
            Player player = (Player)sender;
            if(hasAccount(player)){
                double balance = getBalance(player);
                player.sendMessage((balance >= 0?ChatColor.GREEN:ChatColor.RED) + "You have "+currencySymbol+balance+" in your account.");
            } else {
                player.sendMessage(ChatColor.RED + "You have not yet opened an account with BOB.");
             }
        }
    }

    private double getBalance(Player player) {
        return hasAccount(player)?playerAccounts.get(player).getBalance():0;
    }

    private boolean hasAccount(Player player) {
        return playerAccounts.containsKey(player);
    }
    
    private boolean newAccount(Player player) {
        
        return false;
    }
    

    
    static boolean memberOf(Player player, Account account){
        return false;
        
    }
    
    public Teller getTeller(Account account){
        Teller teller;
        Thread tellerThread;
        if (tellers.containsKey(account)){
            teller = tellers.get(account);
        } else {
            teller = new Teller(account);
            tellers.put(account, teller);
        }
        if (tellerThreads.containsKey(teller)){
            tellerThread = tellerThreads.get(teller);
            if(!tellerThread.isAlive()){
                tellerThreads.remove(teller);
                tellerThread = new Thread(teller);
                tellerThread.start();
                tellerThreads.put(teller, tellerThread);
            }
        } else {
            tellerThread = new Thread(teller);
            tellerThread.start();
            tellerThreads.put(teller, tellerThread);
        }
        return teller;
    }
}
