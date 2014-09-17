/* 
 * Copyright 2014 Alexander Mackenzie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.alexmack.compound.tag;

import net.alexmack.compound.Compound;

/**
 * Thrown when problems arise while reading/writing {@link Compound}
 * instances using {@link CompoundTag} annotations.
 */
public class CompoundTagException extends RuntimeException {
	
	private static final long serialVersionUID = -4192733068543229762L;
	
	private Compound compound;
	private CompoundTag tag;
	private Object object;
	private Exception internal;
	
	public CompoundTagException(String message, Exception i) {
		this(message, null, null, null, i);
	}
	
	public CompoundTagException(String m, Compound c, CompoundTag t, Object o, Exception i) {
		super(m == null ? "Internal Exception!" : m);
		compound = c;
		tag = t;
		object = o;
		internal = i;
	}
	
	/**
	 * Returns the {@link Compound} being read, if any.
	 */
	public Compound getCompound() {
		return compound;
	}
	
	/**
	 * Returns the {@link CompoundTag} being handled, if any.
	 */
	public CompoundTag getTag() {
		return tag;
	}
	
	/**
	 * Returns the {@link Object} being read or written to.
	 */
	public Object getObject() {
		return object;
	}
	
	/**
	 * Returns the <code>class</code> of the {@link Object} being
	 * read or written to.
	 */
	public Class<? extends Object> getObjectClass() {
		return object == null ? null : object.getClass();
	}
	
	/**
	 * Returns the internal {@link Exception} which caused this
	 * {@link CompoundTagException} to be thrown.
	 */
	public Exception getInternal() {
		return internal;
	}

}
