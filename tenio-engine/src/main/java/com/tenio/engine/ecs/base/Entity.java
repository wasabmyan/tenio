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
package com.tenio.engine.ecs.base;

import com.tenio.common.logger.AbstractLogger;
import com.tenio.common.pool.IElementPool;
import com.tenio.engine.exception.ComponentIsNotExistedException;
import com.tenio.engine.exception.DuplicatedComponentException;

/**
 * An entity is something that exists in your game world. Again, an entity is
 * little more than a list of components. Because they are so simple, most
 * implementations won't define an entity as a concrete piece of data. Instead,
 * an entity is a unique ID, and all components that make up an entity will be
 * tagged with that ID.
 * 
 * @see IEntity
 * 
 * @author kong
 **/
public class Entity extends AbstractLogger implements IEntity {

	private IElementPool<IComponent>[] __componentPools = null;
	private IComponent[] __components = null;
	private ContextInfo __contextInfo = null;
	private String __id = null;

	@Override
	public void setId(String id) {
		__id = id;
	}

	@Override
	public String getId() {
		return __id;
	}

	@Override
	public void setContextInfo(ContextInfo contextInfo) {
		if (__contextInfo == null) {
			__contextInfo = contextInfo;
		}
		if (__components == null) {
			__components = new IComponent[contextInfo.getNumberComponents()];
		}
	}

	@Override
	public void setComponentPools(IElementPool<IComponent>[] componentPools) {
		if (__componentPools == null) {
			__componentPools = componentPools;
		}
	}

	@Override
	public IElementPool<IComponent>[] getComponentPools() {
		return __componentPools;
	}

	@Override
	public ContextInfo getContextInfo() {
		return __contextInfo;
	}

	@Override
	public void setComponent(int index, IComponent component) {
		if (hasComponent(index)) {
			var e = new DuplicatedComponentException();
			error(e, "index: ", index);
			throw e;
		}

		__components[index] = component;
	}

	@Override
	public void removeComponent(int index) {
		if (!hasComponent(index)) {
			var e = new ComponentIsNotExistedException();
			error(e, "index: ", index);
			throw e;
		}

		__replaceComponentInternal(index, null);
	}

	@Override
	public void replaceComponent(int index, IComponent component) {
		if (hasComponent(index)) {
			__replaceComponentInternal(index, component);
		} else {
			if (component != null) {
				setComponent(index, component);
			}
		}
	}

	private void __replaceComponentInternal(int index, IComponent replacement) {
		IComponent previousComponent = __components[index];

		if (replacement != previousComponent) {
			__components[index] = replacement;
		}
	}

	@Override
	public IComponent getComponent(int index) {
		return __components[index];
	}

	@Override
	public IComponent[] getComponents() {
		return __components;
	}

	@Override
	public boolean hasComponent(int index) {
		if (index < __components.length) {
			return __components[index] != null;
		} else {
			return false;
		}
	}

	@Override
	public boolean hasComponents(int... indices) {
		for (int index : indices) {
			if (__components[index] == null) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean hasAnyComponent(int... indices) {
		for (int index : indices) {
			if (__components[index] != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void removeAllComponents() {
		for (int i = 0; i < __components.length; i++) {
			if (__components[i] != null) {
				replaceComponent(i, null);
			}
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass())
			return false;
		return this == (Entity) o;

	}

	@Override
	public int hashCode() {
		return __id.hashCode();
	}

	@Override
	public void reset() {
		removeAllComponents();
	}

}
