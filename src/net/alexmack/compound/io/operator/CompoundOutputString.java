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

import java.net.URLEncoder;

import net.alexmack.compound.io.CompoundIO;

/**
 * Writes data to a formatted {@link String} for {@link CompoundIO}.
 */
public class CompoundOutputString extends CompoundOutput {

	protected static final String SEPARATOR = "/";
	protected static final String ENCODING = "UTF-8";
	
	private final StringBuilder STRING;
	
	private boolean first = true;
	
	public CompoundOutputString() {
		STRING = new StringBuilder();
	}
	
	@Override
	public void writeByte(byte b) throws Exception {
		writeInt(b);
	}

	@Override
	public void writeBoolean(boolean b) throws Exception {
		writeByte(b ? (byte)1 : (byte)0);
	}

	@Override
	public void writeInt(int i) throws Exception {
		writeLong(i);
	}

	@Override
	public void writeLong(long l) throws Exception {
		writeString(Long.toString(l, CompoundIO.RADIX));
	}

	@Override
	public void writeDouble(double d) throws Exception {
		writeString(Double.toString(d));
	}

	@Override
	public void writeString(String s) throws Exception {
		if (!first) {
			STRING.append(SEPARATOR);
		}
		STRING.append(URLEncoder.encode(s, ENCODING));
		first = false;
	}
	
	public String string() {
		return STRING.toString();
	}

}
