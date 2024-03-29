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

/**
 * Contains {@link Compound} instances, each mapped to a unique
 * address. Used by {@link CompoundIO} to correctly read linear
 * {@link Compound} data and handle recursive elements.<br><br>
 * Essentially reverses the effect of a {@link CompoundStack}.
 */
public class CompoundStackReverse {

	/**
	 * {@link Compound} instances mapped by address.
	 */
	private final Map<Long, Compound> STACK = new HashMap<Long, Compound>();
	private final Compound ROOT;
	
	public CompoundStackReverse(Compound r){
		ROOT = r;
	}
	
	/**
	 * Returns the {@link Compound} which has been assigned the given
	 * address. If no such {@link Compound} has been called yet, one
	 * will be created and then populated by {@link CompoundIO} later.
	 */
	public Compound get(Long a){
		if (a.longValue() == CompoundStackItem.ADDRESS_ROOT)
			return ROOT;
		
		if (!STACK.containsKey(a))
			STACK.put(a, new Compound());
		
		return STACK.get(a);
	}
	
}
