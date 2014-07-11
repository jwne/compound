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

import java.util.HashMap;
import java.util.Map;

import net.alexmack.compound.Compound;
import net.alexmack.compound.stack.CompoundStack.CompoundStackItem;

public class CompoundStackReverse {

	/**
	 * SCompound objects mapped by stack address.
	 */
	private final Map<Long, Compound> STACK = new HashMap<Long, Compound>();
	private final Compound ROOT;
	
	public CompoundStackReverse(Compound r){
		ROOT = r;
	}
	
	/**
	 * Get the SCompound assigned to the given address.<br>
	 * If the address has not yet been called, an empty
	 * SCompound will be created and then returned.
	 */
	public Compound get(Long a){
		if (a.longValue() == CompoundStackItem.ADDRESS_ROOT)
			return ROOT;
		
		if (!STACK.containsKey(a))
			STACK.put(a, new Compound());
		
		return STACK.get(a);
	}
	
}
