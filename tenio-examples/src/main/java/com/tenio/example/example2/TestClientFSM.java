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

import com.tenio.core.entity.element.MessageObject;
import com.tenio.example.client.ISocketListener;
import com.tenio.example.client.TCP;

/**
 * This class is used for receiving messages from server side. Now you can see a
 * miner life with his wife on your logs.
 * 
 * @author kong
 *
 */
public final class TestClientFSM implements ISocketListener {

	/**
	 * The entry point
	 */
	public static void main(String[] args) {
		new TestClientFSM();
	}

	private final TCP __tcp;

	public TestClientFSM() {
		__tcp = new TCP(8032);
		__tcp.receive(this);

		// send a login request to the server
		var message = MessageObject.newInstance();
		message.put("u", "kong");
		__tcp.send(message);
		System.err.println("Login Request -> " + message);

	}

	@Override
	public void onReceivedTCP(MessageObject message) {
		System.out.println(message);
	}

}
