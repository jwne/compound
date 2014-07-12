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

/**
 * Writes data for {@link CompoundIO}.
 */
public abstract class CompoundOutput {

	public abstract void writeByte(byte b) throws Exception;
	public abstract void writeBoolean(boolean b) throws Exception;
	public abstract void writeInt(int i) throws Exception;
	public abstract void writeLong(long l) throws Exception;
	public abstract void writeDouble(double d) throws Exception;
	public abstract void writeString(String s) throws Exception;
	
}
