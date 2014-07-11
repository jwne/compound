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

public class CompoundStack {

	public static class CompoundStackItem {
		
		public static final long ADDRESS_ROOT = Long.MAX_VALUE;
		
		public final long ADDRESS;
		public final Compound COMPOUND;
		
		public CompoundStackItem(long a, Compound c) {
			ADDRESS = a;
			COMPOUND = c;
		}
		
	}
	
	private long address = Long.MIN_VALUE;
	
	private final ArrayList<CompoundStackItem> ITEMS = new ArrayList<CompoundStack.CompoundStackItem>();
	private final Map<UUID, CompoundStackItem> ITEMS_UUID = new HashMap<UUID, CompoundStack.CompoundStackItem>();
	
	public long createAddress(){
		return address++;
	}
	
	public long add(Compound c){
		if (ITEMS_UUID.containsKey(c.ID))
			return ITEMS_UUID.get(c.ID).ADDRESS;
		
		final CompoundStackItem ITEM  = new CompoundStackItem(createAddress(), c);
		ITEMS.add(ITEM);
		ITEMS_UUID.put(c.ID, ITEM);
		return ITEM.ADDRESS;
	}
	
	public void addRoot(Compound c){
		final CompoundStackItem ITEM = new CompoundStackItem(CompoundStackItem.ADDRESS_ROOT, c);
		ITEMS_UUID.put(c.ID, ITEM);
	}
	
	public CompoundStackItem nextItem(){
		if (ITEMS.size() == 0)
			return null;
		
		final CompoundStackItem ITEM = ITEMS.get(0);
		ITEMS.remove(0);
		return ITEM;
	}
	
	public boolean nextItemExists(){
		return ITEMS.size() != 0;
	}
	
}
