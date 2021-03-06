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
package com.tenio.engine.heartbeat;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.concurrent.GuardedBy;

import com.tenio.common.logger.AbstractLogger;
import com.tenio.engine.exception.HeartbeatNotFoundException;
import com.tenio.engine.message.IMessage;

/**
 * The Java ExecutorService is a construct that allows you to pass a task to be
 * executed by a thread asynchronously. The executor service creates and
 * maintains a reusable pool of threads for executing submitted tasks. This
 * class helps you create and manage your HeartBeats. See:
 * {@link AbstractHeartBeat}
 * 
 * @see IHeartBeatManager
 * 
 * @author kong
 *
 */
public final class HeartBeatManager extends AbstractLogger implements IHeartBeatManager {

	@GuardedBy("this")
	private final Map<String, Future<Void>> __pool = new HashMap<String, Future<Void>>();
	/**
	 * A Set is used as the container for the delayed messages because of the
	 * benefit of automatic sorting and avoidance of duplicates. Messages are sorted
	 * by their dispatch time. @see {@link HMessage}
	 */
	@GuardedBy("this")
	private final Map<String, TreeSet<HMessage>> __listeners = new HashMap<String, TreeSet<HMessage>>();
	private ExecutorService __executorService;

	@Override
	public void initialize(final int maxHeartbeat) throws Exception {
		__executorService = Executors.newFixedThreadPool(maxHeartbeat);
		info("INITIALIZE HEART BEAT", buildgen(maxHeartbeat));
	}

	@Override
	public synchronized void create(final String id, final AbstractHeartBeat heartbeat) {
		try {
			info("CREATE HEART BEAT", buildgen("id: ", id));
			// Add the listener
			var listener = new TreeSet<HMessage>();
			heartbeat.setMessageListener(listener);
			__listeners.put(id, listener);
			// Start the heart-beat
			var future = __executorService.submit(heartbeat);
			__pool.put(id, future);
		} catch (Exception e) {
			error(e, "id: ", id);
		}
	}

	@Override
	public synchronized void dispose(final String id) {
		try {
			if (!__pool.containsKey(id)) {
				throw new HeartbeatNotFoundException();
			}

			var future = __pool.get(id);
			if (future == null) {
				throw new NullPointerException();
			}

			future.cancel(true);
			__pool.remove(id);

			info("DISPOSE HEART BEAT", buildgen(id));

			// Remove the listener
			__listeners.get(id).clear();
			__listeners.remove(id);

			future = null;

		} catch (Exception e) {
			error(e, "id: ", id);
		}
	}

	@Override
	public synchronized boolean contains(final String id) {
		return __pool.containsKey(id);
	}

	@Override
	public synchronized void clear() {
		if (__executorService != null) {
			__executorService.shutdownNow();
		}
		__executorService = null;
		__pool.clear();
	}

	@Override
	public void sendMessage(String id, IMessage message, double delayTime) {
		var container = HMessage.newInstance(message, delayTime);
		__listeners.get(id).add(container);
	}

	@Override
	public void sendMessage(String id, IMessage message) {
		sendMessage(id, message, 0);
	}

}
