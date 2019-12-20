/*
The MIT License

Copyright (c) 2016-2019 kong <congcoi123@gmail.com>

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
package com.tenio.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tenio.entities.AbstractPlayer;
import com.tenio.entities.AbstractRoom;
import com.tenio.entities.manager.IPlayerManager;
import com.tenio.entities.manager.IRoomManager;
import com.tenio.logger.AbstractLogger;
import com.tenio.network.Connection;

/**
 * This class provides you a necessary interface for managing players.
 * 
 * @see {@link IPlayerManager}
 * 
 * @author kong
 * 
 */
public final class PlayerApi extends AbstractLogger {

	/**
	 * @see {@link IPlayerManager}
	 */
	private IPlayerManager __playerManager;
	/**
	 * @see {@link IRoomManager}
	 */
	private IRoomManager __roomManager;

	public PlayerApi(IPlayerManager playerManager, IRoomManager roomManager) {
		__playerManager = playerManager;
		__roomManager = roomManager;
	}

	/**
	 * @see IPlayerManager#contain(String)
	 */
	public boolean contain(final String userName) {
		return __playerManager.contain(userName);
	}

	/**
	 * @see IPlayerManager#get(String)
	 */
	public AbstractPlayer get(final String userName) {
		return __playerManager.get(userName);
	}

	/**
	 * @see IPlayerManager#count()
	 */
	public int count() {
		return __playerManager.count();
	}

	/**
	 * @see IPlayerManager#countPlayers()
	 */
	public int countPlayers() {
		return __playerManager.countPlayers();
	}

	/**
	 * @see IPlayerManager#gets()
	 */
	public Map<String, AbstractPlayer> gets() {
		return __playerManager.gets();
	}

	/**
	 * @see IPlayerManager#add(AbstractPlayer, Connection)
	 */
	public void login(final AbstractPlayer player, final Connection connection) {
		__playerManager.add(player, connection);
	}

	/**
	 * @see IPlayerManager#add(AbstractPlayer)
	 */
	public void login(final AbstractPlayer player) {
		__playerManager.add(player);
	}

	/**
	 * @see IRoomManager#playerJoinRoom(AbstractRoom, AbstractPlayer)
	 */
	public void playerJoinRoom(final AbstractRoom room, final AbstractPlayer player) {
		__roomManager.playerJoinRoom(room, player);
	}

	/**
	 * @see IRoomManager#playerLeaveRoom(AbstractPlayer, boolean)
	 */
	public void playerLeaveRoom(final AbstractPlayer player, final boolean force) {
		__roomManager.playerLeaveRoom(player, force);
	}

	/**
	 * @see #logOut(AbstractPlayer)
	 */
	public void logOut(final String userName) {
		logOut(get(userName));
	}

	/**
	 * @see IPlayerManager#remove(AbstractPlayer)
	 */
	public void logOut(final AbstractPlayer player) {
		__playerManager.remove(player);
	}

	/**
	 * @return Returns all players' information data
	 */
	public List<List<Object>> getAllPlayerBaseInfos() {
		List<List<Object>> list = new ArrayList<List<Object>>();
		gets().values().forEach((player) -> {
			List<Object> data = new ArrayList<Object>();
			data.add(player.getName());
			if (player.getRoom() != null) {
				data.add(player.getRoom().getName());
			} else {
				data.add("NULL ROOM");
			}
			data.add(player.getState());
			list.add(data);
		});
		return list;
	}

	/**
	 * @see #count()
	 */
	public int getCCU() {
		return count();
	}

}
