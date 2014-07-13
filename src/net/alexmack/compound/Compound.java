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
package net.alexmack.compound;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.alexmack.compound.stack.CompoundStack;
import net.alexmack.compound.stack.CompoundStackReverse;

public class Compound implements Cloneable {
	
	private final Map<String, Object> MAP = new HashMap<String, Object>();
	
	/**
	 * {@link UUID} used to reference this {@link Compound} in an instance
	 * of {@link CompoundStack} or {@link CompoundStackReverse}. Not to be
	 * confused with the address field of a {@link CompoundStack.CompoundStackItem}.
	 */
	public final UUID ID = UUID.randomUUID();
	
	/**
	 * Returns the element corresponding to the given identifier.
	 * Will return <code>null</code> if no such element exists, or <code>null</code>
	 * has been specified as the corresponding element.
	 */
	public Object get(String i) {
		synchronized (MAP) {
			return MAP.get(i.toLowerCase());
		}
	}
	
	/**
	 * Returns the element corresponding to the given identifier, with generic type casting.
	 * Will return <code>null</code> if no such element exists, or <code>null</code>
	 * has been specified as the corresponding element.
	 */
	@SuppressWarnings("unchecked")
	public <TYPE> TYPE getCast(String i) {
		final Object VALUE = get(i);
		if (VALUE == null)
			return null;
		return (TYPE) VALUE;
	}
	
	/**
	 * Returns the element corresponding to the given identifier,
	 * or the given default element if no such corresponding value exists.
	 * <br>
	 * This method does <b>not</b>:<br>
	 * <ul>
	 * <li>Change the corresponding element in any way.</li>
	 * <li>Ensure <code>null</code> will not be returned.<br>
	 * <code>null</code> may be returned if it has been specified as the
	 * element to which the given identifier corresponds, or given as the
	 * default element.</li>
	 * </ul>
	 */
	public <TYPE> TYPE getDefault(String i, TYPE d) {
		if (!has(i))
			return d;
		
		return getCast(i);
	}
	
	/**
	 * Returns whether this {@link Compound} contains an element corresponding
	 * to the given identifier. The element itself is not taken into account - a
	 * corresponding <code>null</code> element will still result in this method
	 * returning <code>true</code>.
	 */
	public boolean has(String i){
		synchronized (MAP) {
			return MAP.containsKey(i.toLowerCase());
		}
	}
	
	/**
	 * Removes the element corresponding to the given identifier, if there is one.
	 */
	public void remove(String i){
		synchronized (MAP) {
			MAP.remove(i.toLowerCase());
		}
	}
	
	/**
	 * Sets the element corresponding to the given identifier, regardless of current
	 * status.
	 */
	public Compound set(String i, Object o){
		synchronized (MAP) {
			MAP.put(i.toLowerCase(), o);
		}
		return this;
	}
	
	/**
	 * Returns all elements corresponding to identifiers.
	 */
	public Object[] getAllElements(){
		synchronized (MAP) {
			return MAP.values().toArray(new Object[]{});
		}
	}
	
	/**
	 * Returns all identifiers with corresponding elements.
	 */
	public String[] getAllIdentifiers(){
		synchronized (MAP) {
			return MAP.keySet().toArray(new String[]{});
		}
	}
	
	@Override
	public String toString() {
		return "Compound " + ID.toString();
	}
	
	@Override
	protected Compound clone() {
		final Compound CLONE = new Compound();
		CLONE.MAP.putAll(MAP);
		return CLONE;
	}
		
}
