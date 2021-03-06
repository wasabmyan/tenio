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
package com.tenio.common.configuration.constant;

import com.tenio.common.pool.IElementPool;

/**
 * All base constants' values for the server are defined here. This class should
 * not be modified.
 * 
 * @author kong
 * 
 */
public final class CommonConstants {

	private CommonConstants() {
	}

	public static final String LOGO[] = new String[] { 
			"********************************************************",
			"**                                                    **",
			"**                                ■■■                 **",
			"**                              ■   ■■                **",
			"**                                  ■■■               **",
			"**                       ■■■■■■    ■■■                **",
			"**                    ■■■■■■■■■■■■■■■                 **",
			"**                 ■■■■■            ■■                **",
			"**               ■■                  ■                **",
			"**              ■■■    ■■  ■■     ■■■  ■  ■■■         **",
			"**         ■■■■■■■    ■■■■■ ■■   ■■■■■  ■   ■■        **",
			"**       ■■    ■■■   ■ ■■■■  ■     ■■■  ■ ■   ■■      **",
			"**      ■■     ■■■■     ■■  ■■         ■  ■ ■  ■      **",
			"**      ■■  ■  ■■■■   ■    ■■      ■■■■  ■■■   ■      **",
			"**      ■■  ■■ ■■■■■    ■■■              ■■   ■■      **",
			"**       ■■     ■■■                     ■■■  ■■       **",
			"**        ■■■■■■■■■      ■               ■■           **",
			"**           ■■  ■■       ■■  ■■         ■            **",
			"**                ■                                   **",
			"**                 ■■                                 **",
			"**                    ■■                              **",
			"**                       ■■■■■■■■                     **",
			"**                                                    **",
			"**                                                    **",
			"**                       TenIO                        **",
			"**            Copyright (c) 2016-2020, Kong           **",
			"**                All rights reserved.                **",
			"**                                                    **",
			"********************************************************" 
	};

	/**
	 * The number of elements in a bulk those created for the first time.
	 * 
	 * @see IElementPool
	 */
	public static final int BASE_ELEMENT_POOL = 32;

	/**
	 * When the desired number of elements exceeded the first configuration. The new
	 * number of elements will be added.
	 * 
	 * @see IElementPool
	 */
	public static final int ADD_ELEMENT_POOL = 10;

}
