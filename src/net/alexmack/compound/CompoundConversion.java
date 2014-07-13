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

/**
 * Provides various methods to convert standard data structures
 * to {@link Compound}s and vice versa.
 */
public class CompoundConversion {

	private static final String ARRAY_SIZE = "s";
	
	/**
	 * Constructs a {@link Compound} representing the given array.
	 */
	public static Compound toCompound(Object[] array) {
		Compound compound = new Compound();
		compound.set(ARRAY_SIZE, array.length);
		for (int i = 0; i != array.length; i++)
			compound.set(Integer.toString(i), array[i]);
		return compound;
	}
	
	/**
	 * Returns the array represented by the given {@link Compound}.
	 */
	public static Object[] toArray(Compound compound) {
		Object[] array = new Object[compound.getDefault(ARRAY_SIZE, 0)];
		for (int i = 0; i != array.length; i++)
			array[i] = compound.get(Integer.toString(i));
		return array;
	}
	
}
