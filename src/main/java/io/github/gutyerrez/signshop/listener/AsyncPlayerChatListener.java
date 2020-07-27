package io.github.gutyerrez.signshop.listener;

import com.google.common.collect.Maps;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Map;
import java.util.function.Consumer;

/**
 * @author SrGutyerrez
 */
public class AsyncPlayerChatListener implements Listener {

    private static Map<Player, Consumer<AsyncPlayerChatEvent>> CONSUMERS = Maps.newHashMap();

    @EventHandler
    public void on(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        Consumer<AsyncPlayerChatEvent> consumer = AsyncPlayerChatListener.CONSUMERS.remove(
                player
        );

        if (consumer != null) {
            consumer.accept(event);
        }
    }

    public static void on(Player player, Consumer<AsyncPlayerChatEvent> event) {
        AsyncPlayerChatListener.CONSUMERS.put(player, event);
    }

}
