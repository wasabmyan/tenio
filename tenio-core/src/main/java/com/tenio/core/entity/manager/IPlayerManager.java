/*
The MIT License

Copyright (c) 2016-2020 kong <congcoi123@gmail.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/
package com.tenio.core.entity.manager;

import java.util.Map;

import com.tenio.core.api.PlayerApi;
import com.tenio.core.entity.AbstractPlayer;
import com.tenio.core.network.Connection;

/**
 * Manage all your players ({@link AbstractPlayer}) on the server. It is a
 * singleton pattern class, which can be called anywhere. But it's better that
 * you use the {@link PlayerApi} interface for easy management.
 * 
 * @author kong
 * 
 */
public interface IPlayerManager extends IManager {

	/**
	 * @return the number of all current players' instance (include NPC or BOT)
	 */
	int count();

	/**
	 * @return the number of all current players that have connections (without NPC
	 *         or BOT)
	 */
	int countPlayers();

	/**
	 * @return all current players
	 */
	Map<String, AbstractPlayer> gets();

	/**
	 * Remove all players.
	 */
	public void clear();

	/**
	 * Determine if the player has existed or not.
	 * 
	 * @param name the player's name (unique ID)
	 * @return <b>true</b> if the player has existed, <b>false</b> otherwise
	 */
	boolean contain(final String name);

	/**
	 * Retrieve a player by the player's name.
	 * 
	 * @param name the player's name (unique ID)
	 * @return the player's instance if that player has existed, <b>null</b>
	 *         otherwise
	 */
	AbstractPlayer get(final String name);

	/**
	 * Add a new player to your server (this player was upgraded from one
	 * connection).
	 * 
	 * @param player     that is created from your server, see:
	 *                   {@link AbstractPlayer}
	 * @param connection the main corresponding connection, see: {@link Connection}
	 */
	void add(final AbstractPlayer player, final Connection connection);

	/**
	 * Add a new player to your server (this player is known as one NCP or a BOT)
	 * without a attached connection.
	 * 
	 * @param player that is created from your server, see: {@link AbstractPlayer}
	 */
	void add(final AbstractPlayer player);

	/**
	 * Remove a player from your server.
	 * 
	 * @param player that is removed, see {@link AbstractPlayer}
	 */
	void remove(final AbstractPlayer player);

	/**
	 * When a player is disconnected, all the related connections need to be deleted
	 * too.
	 * 
	 * @param player the corresponding player, see {@link AbstractPlayer}
	 */
	void removeAllConnections(final AbstractPlayer player);

	/**
	 * Make sure one player is removed from this management (as well as your
	 * server). It is used when you don't want your player can re-connect with any
	 * interruption's reason.
	 * 
	 * @param player that is removed, see {@link AbstractPlayer}
	 */
	void clean(final AbstractPlayer player);

}
