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
package com.tenio.core.configuration;

import com.tenio.core.configuration.constant.RestMethod;

/**
 * @author kong
 */
public final class Path {

	private String __name;
	private String __description;
	private int __version;
	private RestMethod __method;
	private String __uri;

	public Path(String name, RestMethod method, String uri, String description, int version) {
		__name = name;
		__method = method;
		__uri = uri;
		__description = description;
		__version = version;
	}

	public String getName() {
		return __name;
	}

	public RestMethod getMethod() {
		return __method;
	}

	public String getUri() {
		return __uri;
	}

	public String getDescription() {
		return __description;
	}

	public int getVersion() {
		return __version;
	}

}
