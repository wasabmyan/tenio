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
package com.tenio.example.example2;

import com.tenio.core.AbstractApp;
import com.tenio.core.configuration.constant.TEvent;
import com.tenio.core.extension.AbstractExtensionHandler;
import com.tenio.core.extension.IExtension;
import com.tenio.engine.heartbeat.HeartBeatManager;
import com.tenio.example.example2.entity.Inspector;
import com.tenio.example.server.Configuration;

/**
 * This class is used to send to clients all the daily life of our miner and his
 * wife
 * 
 * @author kong
 *
 */
public final class TestServerFSM extends AbstractApp {

	/**
	 * The entry point
	 */
	public static void main(String[] args) {
		var game = new TestServerFSM();
		game.start();
	}

	@Override
	public IExtension getExtension() {
		return new Extenstion();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Configuration getConfiguration() {
		return new Configuration("TenIOConfig.xml");
	}

	/**
	 * Your own logic handler
	 */
	private final class Extenstion extends AbstractExtensionHandler implements IExtension {

		@Override
		public void initialize() {

			_on(TEvent.CONNECTION_SUCCESS, args -> {
				var connection = _getConnection(args[0]);
				var message = _getMessageObject(args[1]);

				info("CONNECTION", connection.getAddress());

				// allow a connection login to the server
				String username = message.getString("u");
				_playerApi.login(new Inspector(username), connection);

				return null;
			});

			_on(TEvent.DISCONNECT_CONNECTION, args -> {
				var connection = _getConnection(args[0]);

				info("DISCONNECT CONNECTION", connection.getAddress());

				return null;
			});

			_on(TEvent.PLAYER_IN_SUCCESS, args -> {
				// Login successful
				var player = this.<Inspector>_getPlayer(args[0]);

				info("PLAYER IN", player.getName());

				return null;
			});

			_on(TEvent.PLAYER_TIMEOUT, args -> {
				var player = this.<Inspector>_getPlayer(args[0]);

				info("PLAYER TIMEOUT", player.getName());

				return null;
			});

			_on(TEvent.DISCONNECT_PLAYER, args -> {
				var player = this.<Inspector>_getPlayer(args[0]);

				info("DISCONNECT PLAYER", player.getName());

				return null;
			});
			
			// create a life-cycle first
			var hearbeatManager = new HeartBeatManager();
			try {
				hearbeatManager.initialize(1);
				hearbeatManager.create("daily-life", new LifeCycle());
			} catch (Exception e) {
				error(e, "daily-life");
			}

		}

	}

}
