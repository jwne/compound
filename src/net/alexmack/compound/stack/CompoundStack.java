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
package net.alexmack.compound.stack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.alexmack.compound.Compound;

/**
 * Contains {@link CompoundStackItem} instances, each mapped to
 * a unique address, which is used to correctly handle recursive
 * elements and allow linear writing with {@link CompoundIO}.
 */
public class CompoundStack {

	/**
	 * Represents a single {@link Compound} in a {@link CompoundStack}.
	 */
	public static class CompoundStackItem {
		
		public static final long ADDRESS_ROOT = Long.MAX_VALUE;
		
		public final long ADDRESS;
		public final Compound COMPOUND;
		
		public CompoundStackItem(long a, Compound c) {
			ADDRESS = a;
			COMPOUND = c;
		}
		
	}
	
	/**
	 * Last address assigned to a {@link CompoundStackItem}
	 * in this {@link CompoundStack}.
	 */
	private long address = Long.MIN_VALUE;
	
	private final ArrayList<CompoundStackItem> ITEMS = new ArrayList<CompoundStack.CompoundStackItem>();
	private final Map<UUID, CompoundStackItem> ITEMS_UUID = new HashMap<UUID, CompoundStack.CompoundStackItem>();
	
	/**
	 * Creates a unique address to be used by a {@link CompoundStackItem}
	 * in this {@link CompoundStack}. Addresses are only unique to the
	 * instance by which they were generated.
	 */
	public long createAddress(){
		return address++;
	}
	
	/**
	 * Adds the given {@link Compound} as a {@link CompoundStackItem} to this
	 * {@link CompoundStack} and returns the unique address of the created
	 * {@link CompoundStackItem}.
	 */
	public long add(Compound c){
		if (ITEMS_UUID.containsKey(c.ID))
			return ITEMS_UUID.get(c.ID).ADDRESS;
		
		final CompoundStackItem ITEM  = new CompoundStackItem(createAddress(), c);
		ITEMS.add(ITEM);
		ITEMS_UUID.put(c.ID, ITEM);
		return ITEM.ADDRESS;
	}
	
	/**
	 * Adds the given {@link Compound} as a {@link CompoundStackItem} to this
	 * {@link CompoundStack} as the root.
	 */
	public void addRoot(Compound c){
		final CompoundStackItem ITEM = new CompoundStackItem(CompoundStackItem.ADDRESS_ROOT, c);
		ITEMS_UUID.put(c.ID, ITEM);
	}
	
	/**
	 * Returns the next {@link CompoundStackItem} to be processed,
	 * or <code>null</code> if there are none remaining.
	 */
	public CompoundStackItem nextItem(){
		if (ITEMS.size() == 0)
			return null;
		
		final CompoundStackItem ITEM = ITEMS.get(0);
		ITEMS.remove(0);
		return ITEM;
	}
	
	/**
	 * Returns whether {@link nextItem()} will return a valid value
	 * when called.
	 */
	public boolean nextItemExists(){
		return ITEMS.size() != 0;
	}
	
}
