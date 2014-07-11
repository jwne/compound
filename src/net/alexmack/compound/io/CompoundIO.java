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
package net.alexmack.compound.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import net.alexmack.compound.Compound;
import net.alexmack.compound.io.operator.CompoundInput;
import net.alexmack.compound.io.operator.CompoundInputStream;
import net.alexmack.compound.io.operator.CompoundOutput;
import net.alexmack.compound.io.operator.CompoundOutputStream;
import net.alexmack.compound.stack.CompoundStack;
import net.alexmack.compound.stack.CompoundStack.CompoundStackItem;
import net.alexmack.compound.stack.CompoundStackReverse;

public class CompoundIO {
	
	public static final int RADIX = 32;
	
	public static void write(final Compound COMPOUND, final File FILE) throws Exception {
		write(COMPOUND, new CompoundOutputStream(new DataOutputStream(new FileOutputStream(FILE))));
	}
	
	public static void write(final Compound COMPOUND, final CompoundOutput OUTPUT) throws Exception {
		final CompoundStack STACK = new CompoundStack();
		STACK.addRoot(COMPOUND);
		writeStacked(COMPOUND, OUTPUT, STACK);
		
		while (STACK.nextItemExists()){
			OUTPUT.writeBoolean(false);
			final CompoundStackItem ITEM = STACK.nextItem();
			OUTPUT.writeLong(ITEM.ADDRESS);
			writeStacked(ITEM.COMPOUND, OUTPUT, STACK);
		}
		
		OUTPUT.writeBoolean(true);
	}
	
	private static void writeStacked(final Compound COMPOUND, final CompoundOutput OUTPUT, final CompoundStack STACK) throws Exception {
		final String[] IDENTIFIERS = COMPOUND.getAllIdentifiers();
		OUTPUT.writeInt(IDENTIFIERS.length);
		for (final String I : IDENTIFIERS){
			final Object VALUE = COMPOUND.get(I);
			final CompoundType TYPE = CompoundType.get(VALUE);
			OUTPUT.writeString(I);
			OUTPUT.writeByte(TYPE.ID);
			
			if (TYPE == CompoundType.COMPOUND)
				OUTPUT.writeLong(STACK.add((Compound) VALUE));
			else if (TYPE.isValid())
				TYPE.IO.write(OUTPUT, VALUE);
		}
	}
	
	public static Compound read(final File FILE) throws Exception {
		return read(new CompoundInputStream(new DataInputStream(new FileInputStream(FILE))));
	}

	public static Compound read(final CompoundInput INPUT) throws Exception {
		final Compound COMPOUND = new Compound();
		final CompoundStackReverse STACK = new CompoundStackReverse(COMPOUND);
		readInto(COMPOUND, INPUT, STACK);
		
		while (!INPUT.readBoolean()){
			final long ADDRESS = INPUT.readLong();
			readInto(STACK.get(Long.valueOf(ADDRESS)), INPUT, STACK);
		}
		
		return COMPOUND;
	}
	
	public static Compound readNull(final File FILE) {
		try {
			return read(FILE);
		}catch (Exception e) {
			return null;
		}
	}
	
	public static Compound readNull(final CompoundInput INPUT) {
		try {
			return read(INPUT);
		}catch (Exception e) {
			return null;
		}
	}
	
	private static void readInto(final Compound COMPOUND, final CompoundInput INPUT, final CompoundStackReverse STACK) throws Exception {
		final int SIZE = INPUT.readInt();
		for (int i = 0; i != SIZE; i++){
			final String IDENTIFIER = INPUT.readString();
			final CompoundType TYPE = CompoundType.get(INPUT.readByte());
			
			if (TYPE == CompoundType.COMPOUND)
				COMPOUND.set(IDENTIFIER, STACK.get(Long.valueOf(INPUT.readLong())));
			else if (TYPE.isValid())
				COMPOUND.set(IDENTIFIER, TYPE.IO.read(INPUT));
		}
	}
	
}
