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

import java.io.DataInputStream;

/**
 * Reads data from a {@link DataInputStream} for {@link CompoundIO}.
 */
public class CompoundInputStream extends CompoundInput {

	/**
	 * The {@link DataInputStream} being read from.
	 */
	public final DataInputStream STREAM;
	
	public CompoundInputStream(DataInputStream i) {
		STREAM = i;
	}
	
	@Override
	public byte readByte() throws Exception {
		return STREAM.readByte();
	}

	@Override
	public boolean readBoolean() throws Exception {
		return readByte() == 1;
	}

	@Override
	public int readInt() throws Exception {
		return STREAM.readInt();
	}

	@Override
	public long readLong() throws Exception {
		return STREAM.readLong();
	}

	@Override
	public double readDouble() throws Exception {
		return STREAM.readDouble();
	}

	@Override
	public String readString() throws Exception {
		return STREAM.readUTF();
	}

}
