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

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

/**
 * Writes data to a <code>byte</code> array for {@link CompoundIO}.
 */
public class CompoundOutputBytes extends CompoundOutputStream {

	private final ByteArrayOutputStream BYTESTREAM;
	
	public CompoundOutputBytes() {
		this(new ByteArrayOutputStream());
	}
	
	private CompoundOutputBytes(ByteArrayOutputStream b) {
		super(new DataOutputStream(b));
		BYTESTREAM = b;
	}
	
	public byte[] bytes() {
		return BYTESTREAM.toByteArray();
	}

}
