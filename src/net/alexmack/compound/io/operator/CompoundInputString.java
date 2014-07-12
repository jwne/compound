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
package net.alexmack.compound.io.operator;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import net.alexmack.compound.io.CompoundIO;

/**
 * Reads data from a formatted {@link String} for {@link CompoundIO}.
 */
public class CompoundInputString extends CompoundInput {

	private final Iterator<String> ITERATOR;
	
	public CompoundInputString(String s) {
		ITERATOR = new ArrayList<String>(Arrays.asList(s.split(CompoundOutputString.SEPARATOR))).iterator();
	}
	
	@Override
	public byte readByte() throws Exception {
		return (byte) readInt();
	}

	@Override
	public boolean readBoolean() throws Exception {
		return readByte() == 1;
	}

	@Override
	public int readInt() throws Exception {
		return (int) readLong();
	}

	@Override
	public long readLong() throws Exception {
		return Long.parseLong(readString(), CompoundIO.RADIX);
	}

	@Override
	public double readDouble() throws Exception {
		return Double.parseDouble(readString());
	}

	@Override
	public String readString() throws Exception {
		return URLDecoder.decode(ITERATOR.next(), CompoundOutputString.ENCODING);
	}

}
