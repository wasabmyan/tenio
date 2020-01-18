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
package com.tenio.engine.physic.common;

import com.tenio.engine.physic.graphic.IRender;
import com.tenio.engine.physic.graphic.Paint;

/**
 * AABB stands for <a href=
 * "https://en.wikipedia.org/wiki/Minimum_bounding_box#Axis-aligned_minimum_bounding_box">Axis-aligned
 * Bounding Box</a>. It is a fairly computationally- and memory-efficient way of
 * representing a volume, typically used to see if two objects might be
 * touching.
 * 
 * @author kong
 *
 */
public class InvertedAABBox2D implements IRender {

	private float __left;
	private float __top;
	private float __right;
	private float __bottom;
	
	public static InvertedAABBox2D newInstance() {
		var invert = new InvertedAABBox2D();
		return invert;
	}
	
	public static InvertedAABBox2D valueOf(float left, float top, float right, float bottom) {
		var invert = new InvertedAABBox2D(left, top, right, bottom);
		return invert;
	}

	private InvertedAABBox2D() {
		__left = 0;
		__top = 0;
		__right = 0;
		__bottom = 0;
	}

	private InvertedAABBox2D(float left, float top, float right, float bottom) {
		__left = left;
		__top = top;
		__right = right;
		__bottom = bottom;
	}

	/**
	 * @param other the other BBox @see {@link InvertedAABBox2D}
	 * @return Returns <code>true</code> if the <code>BBox</code> described by other
	 *         intersects with this one
	 */
	public boolean isOverlappedWith(InvertedAABBox2D other) {
		return !((other.getTop() > getBottom()) || (other.getBottom() < getTop()) || (other.getLeft() > getRight())
				|| (other.getRight() < getLeft()));
	}

	public float getTop() {
		return __top;
	}

	public void setTop(float top) {
		__top = top;
	}

	public float getLeft() {
		return __left;
	}

	public void setLeft(float left) {
		__left = left;
	}

	public float getBottom() {
		return __bottom;
	}

	public void setBottom(float bottom) {
		__bottom = bottom;
	}

	public float getRight() {
		return __right;
	}

	public void setRight(float right) {
		__right = right;
	}

	@Override
	public void render(Paint paint) {
		paint.drawLine(getLeft(), getTop(), getRight(), getTop());
		paint.drawLine(getLeft(), getBottom(), getRight(), getBottom());
		paint.drawLine(getLeft(), getTop(), getLeft(), getBottom());
		paint.drawLine(getRight(), getTop(), getRight(), getBottom());
	}

}
