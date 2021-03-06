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
package com.tenio.core.network.http.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.tenio.core.configuration.Path;
import com.tenio.core.configuration.constant.RestMethod;
import com.tenio.core.configuration.constant.TEvent;
import com.tenio.core.entity.element.MessageObject;
import com.tenio.core.event.IEventManager;
import com.tenio.core.network.http.servlet.base.BaseProcessServlet;
import com.tenio.core.network.http.servlet.base.BaseServlet;

/**
 * @author kong
 */
public final class TServlet extends BaseServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1971993446960398293L;

	private final IEventManager __eventManager;

	private ProcessPost __processPost;
	private ProcessPut __processPut;
	private ProcessGet __processGet;
	private ProcessDelete __processDelete;

	public TServlet(IEventManager eventManager, List<Path> paths) {
		__eventManager = eventManager;
		for (var path : paths) {
			switch (path.getMethod()) {
			case POST:
				__processPost = new ProcessPost();
				break;
			case PUT:
				__processPut = new ProcessPut();
				break;
			case GET:
				__processGet = new ProcessGet();
				break;
			case DELETE:
				__processDelete = new ProcessDelete();
				break;
			default:
				break;
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		if (__processGet != null) {
			__processGet.handle(request, response);
		} else {
			__sendUnsupportedMethod(response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		if (__processPost != null) {
			__processPost.handle(request, response);
		} else {
			__sendUnsupportedMethod(response);
		}
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		if (__processPut != null) {
			__processPut.handle(request, response);
		} else {
			__sendUnsupportedMethod(response);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		if (__processDelete != null) {
			__processDelete.handle(request, response);
		} else {
			__sendUnsupportedMethod(response);
		}
	}

	private final class ProcessPost extends BaseProcessServlet {

		@Override
		protected void _handleImpl(HttpServletRequest request, HttpServletResponse response) {
			var check = __eventManager.getExternal().emit(TEvent.HTTP_REQUEST, RestMethod.POST, request, response);
			if (check == null) {
				__eventManager.getExternal().emit(TEvent.HTTP_HANDLER, RestMethod.POST, request, response);
			}
		}

	}

	private final class ProcessPut extends BaseProcessServlet {

		@Override
		protected void _handleImpl(HttpServletRequest request, HttpServletResponse response) {
			var check = __eventManager.getExternal().emit(TEvent.HTTP_REQUEST, RestMethod.PUT, request, response);
			if (check == null) {
				__eventManager.getExternal().emit(TEvent.HTTP_HANDLER, RestMethod.PUT, request, response);
			}
		}

	}

	private final class ProcessGet extends BaseProcessServlet {

		@Override
		protected void _handleImpl(HttpServletRequest request, HttpServletResponse response) {
			var check = __eventManager.getExternal().emit(TEvent.HTTP_REQUEST, RestMethod.GET, request, response);
			if (check == null) {
				__eventManager.getExternal().emit(TEvent.HTTP_HANDLER, RestMethod.GET, request, response);
			}
		}

	}

	private final class ProcessDelete extends BaseProcessServlet {

		@Override
		protected void _handleImpl(HttpServletRequest request, HttpServletResponse response) {
			var check = __eventManager.getExternal().emit(TEvent.HTTP_REQUEST, RestMethod.DELETE, request, response);
			if (check == null) {
				__eventManager.getExternal().emit(TEvent.HTTP_HANDLER, RestMethod.DELETE, request, response);
			}
		}

	}

	@SuppressWarnings("unchecked")
	private void __sendUnsupportedMethod(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		try {
			var json = new JSONObject();
			json.putAll(MessageObject.newInstance().add("status", "failed").add("message", "405Method Not Allowed"));
			response.getWriter().println(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}