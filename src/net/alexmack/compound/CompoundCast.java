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

public class CompoundCast {

	public static int asInt(Object o) {
		if (o instanceof Byte) {
			return ((Byte) o).intValue();
		}else if (o instanceof Short){
			return ((Short) o).intValue();
		}else if (o instanceof Integer){
			return ((Integer) o).intValue();
		}
		throw new RuntimeException(o.getClass().getName() + " cannot be cast to Integer!");
	}
	
	public static long asLong(Object o) {
		if (o instanceof Long) {
			return ((Long) o).longValue();
		}else{
			return asInt(o);
		}
	}
	
	public static double asDouble(Object o) {
		if (o instanceof Double) {
			return ((Double) o).doubleValue();
		}else if (o instanceof Float){
			return ((Float) o).doubleValue();
		}else{
			return asInt(o);
		}
	}
	
	public static boolean asBoolean(Object o) {
		if (o instanceof Boolean) {
			return ((Boolean) o).booleanValue();
		}else if (o instanceof String) {
			return Boolean.parseBoolean((String) o);
		}
		throw new RuntimeException(o.getClass().getName() + " cannot be cast to Boolean!");
	}
	
}
