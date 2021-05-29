package eu.pixelstube.cloud.bootstrap.bungeecord.reconnect;

/*

  » eu.kyrocloud.plugin.reconnect

  » Methode/Class coded by Haizoooon#6495
  » This Class/Source cannot be modified without permission.
  » Please refrain from recoding
  » Questions may be asked in Discord

  » Package coded at: 25.03.2021 / 21:18

 */

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ReconnectHandler;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ReconnectHandlerImpl implements ReconnectHandler {

    @Override
    public ServerInfo getServer(ProxiedPlayer proxiedPlayer) {
        return ProxyServer.getInstance().getServerInfo("fallback");
    }

    @Override
    public void setServer(ProxiedPlayer proxiedPlayer) {

    }

    @Override
    public void save() {

    }

    @Override
    public void close() {

    }
}
