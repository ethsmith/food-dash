package dev.ethans.fooddash.state.base;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.title.Title;
import net.minikloon.fsmgasm.State;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class GameState extends State implements Listener {
    protected final JavaPlugin plugin; // this could be your game's "main" class

    protected final Set<Listener> listeners = new HashSet<>();
    protected final Set<BukkitTask> tasks = new HashSet<>();

    public GameState(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public final void start() {
        super.start();
        register(this);
    }

    @Override
    public final void end() {
        super.end();
        if (!super.getEnded())
            return;
        listeners.forEach(HandlerList::unregisterAll);
        tasks.forEach(BukkitTask::cancel);
        listeners.clear();
        tasks.clear();
    }

    public final Collection<? extends Player> getPlayers() {
        return plugin.getServer().getOnlinePlayers();
    }

    protected final void broadcast(TextComponent message) {
        getPlayers().forEach(p -> p.sendMessage(message));
    }

    protected final void broadcastTitle(TextComponent title, TextComponent subtitle) {
        getPlayers().forEach(p -> p.showTitle(Title.title(title, subtitle)));
    }

    protected void register(Listener listener) {
        listeners.add(listener);
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    protected void schedule(Runnable runnable, long delay) {
        BukkitTask task = plugin.getServer().getScheduler().runTaskLater(plugin, runnable, delay);
        tasks.add(task);
    }

    protected void scheduleRepeating(Runnable runnable, long delay, long interval) {
        BukkitTask task = plugin.getServer().getScheduler().runTaskTimer(plugin, runnable, delay, interval);
        tasks.add(task);
    }
}