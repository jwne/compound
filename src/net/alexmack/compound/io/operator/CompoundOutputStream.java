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

import java.io.DataOutputStream;

public class CompoundOutputStream extends CompoundOutput {

	public final DataOutputStream STREAM;
	
	public CompoundOutputStream(DataOutputStream s) {
		STREAM = s;
	}
	
	@Override
	public void writeByte(byte b) throws Exception {
		STREAM.writeByte(b);
	}

	@Override
	public void writeBoolean(boolean b) throws Exception {
		writeByte(b ? (byte)1 : (byte)0);
	}

	@Override
	public void writeInt(int i) throws Exception {
		STREAM.writeInt(i);
	}

	@Override
	public void writeLong(long l) throws Exception {
		STREAM.writeLong(l);
	}

	@Override
	public void writeDouble(double d) throws Exception {
		STREAM.writeDouble(d);
	}

	@Override
	public void writeString(String s) throws Exception {
		STREAM.writeUTF(s);
	}

}
