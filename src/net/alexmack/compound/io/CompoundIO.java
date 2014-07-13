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
import net.alexmack.compound.io.operator.CompoundInputString;
import net.alexmack.compound.io.operator.CompoundOutput;
import net.alexmack.compound.io.operator.CompoundOutputStream;
import net.alexmack.compound.io.operator.CompoundOutputString;
import net.alexmack.compound.stack.CompoundStack;
import net.alexmack.compound.stack.CompoundStack.CompoundStackItem;
import net.alexmack.compound.stack.CompoundStackReverse;

/**
 * Provies methods for reading/writing {@link Compound} instances.
 */
public class CompoundIO {
	
	/**
	 * Radix to be used when representing data in a non-10 base.
	 */
	public static final int RADIX = 32;
	
	/**
	 * Returns the given {@link Compound} written as a formatted {@link String}.
	 */
	public static String write(final Compound COMPOUND) throws Exception {
		final CompoundOutputString STRING = new CompoundOutputString();
		write(COMPOUND, STRING);
		return STRING.string();
	}
	
	/**
	 * Writes the given {@link Compound} to the given {@link File}.
	 */
	public static void write(final Compound COMPOUND, final File FILE) throws Exception {
		write(COMPOUND, new DataOutputStream(new FileOutputStream(FILE)));
	}
	
	/**
	 * Writes the given {@link Compound} to the given {@link DataOutputStream}.
	 */
	public static void write(final Compound COMPOUND, final DataOutputStream STREAM) throws Exception {
		write(COMPOUND, new CompoundOutputStream(STREAM));
	}
	
	/**
	 * Writes the given {@link Compound} to the given {@link CompoundOutput}.
	 */
	public static void write(final Compound COMPOUND, final CompoundOutput OUTPUT) throws Exception {
		// Create the stack to be used.
		final CompoundStack STACK = new CompoundStack();
		// Define the stack root.
		STACK.addRoot(COMPOUND);
		// Write the root Compound.
		writeStacked(COMPOUND, OUTPUT, STACK);
		
		// Keep writing Compounds until the stack is empty.
		while (STACK.nextItemExists()) {
			// Indicates there are is at least one more Compound.
			OUTPUT.writeBoolean(false);
			final CompoundStackItem ITEM = STACK.nextItem();
			// Write the address assigned to the Compound being written.
			OUTPUT.writeLong(ITEM.ADDRESS);
			// Write the Compound.
			writeStacked(ITEM.COMPOUND, OUTPUT, STACK);
		}
		// Indicates all Compounds have been written.
		OUTPUT.writeBoolean(true);
	}
	
	/**
	 * Writes the given {@link Compound} to the given {@link CompoundOutput}, adding
	 * any {@link Compound} elements to the given {@link CompoundStack} and instead
	 * writing the assigned address.
	 */
	private static void writeStacked(final Compound COMPOUND, final CompoundOutput OUTPUT, final CompoundStack STACK) throws Exception {
		final String[] IDENTIFIERS = COMPOUND.getAllIdentifiers();
		OUTPUT.writeInt(IDENTIFIERS.length);
		for (final String I : IDENTIFIERS) {
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
	
	/**
	 * Reads a {@link Compound} from the given {@link String} using
	 * {@link CompoundInputString}.
	 */
	public static Compound read(final String STRING) throws Exception {
		return read(new CompoundInputString(STRING));
	}
	
	/**
	 * Reads a {@link Compound} from the given {@link File}.
	 */
	public static Compound read(final File FILE) throws Exception {
		return read(new DataInputStream(new FileInputStream(FILE)));
	}
	
	/**
	 * Reads a {@link Compound} from the given {@link DataInputStream}.
	 */
	public static Compound read(final DataInputStream STREAM) throws Exception {
		return read(new CompoundInputStream(STREAM));
	}

	/**
	 * Reads a {@link Compound} from the given {@link CompoundInput}.
	 */
	public static Compound read(final CompoundInput INPUT) throws Exception {
		// Create the root Compound.
		final Compound COMPOUND = new Compound();
		// Create the stack to be used.
		final CompoundStackReverse STACK = new CompoundStackReverse(COMPOUND);
		// Populate the root Compound.
		readInto(COMPOUND, INPUT, STACK);
		
		// Keep reading until there are no more Compounds to be read.
		while (!INPUT.readBoolean()) {
			// Read the address to be populated.
			final long ADDRESS = INPUT.readLong();
			// Populate the specified Compound.
			readInto(STACK.get(Long.valueOf(ADDRESS)), INPUT, STACK);
		}
		
		return COMPOUND;
	}
	
	/**
	 * Reads data from the given {@link CompoundInput} into the given {@link Compound} using
	 * the given {@link CompoundStackReverse} to read {@link Compound} elements.
	 */
	private static void readInto(final Compound COMPOUND, final CompoundInput INPUT, final CompoundStackReverse STACK) throws Exception {
		final int SIZE = INPUT.readInt();
		for (int i = 0; i != SIZE; i++) {
			final String IDENTIFIER = INPUT.readString();
			final CompoundType TYPE = CompoundType.get(INPUT.readByte());
			
			if (TYPE == CompoundType.COMPOUND)
				COMPOUND.set(IDENTIFIER, STACK.get(Long.valueOf(INPUT.readLong())));
			else if (TYPE.isValid())
				COMPOUND.set(IDENTIFIER, TYPE.IO.read(INPUT));
		}
	}
	
	/**
	 * Reads a {@link Compound} from the given {@link String} using
	 * {@link CompoundInputString}. Any thrown {@link Exception}s are
	 * caught and <code>null</code> is returned instead.
	 */
	public static Compound readNull(final String STRING) {
		try {
			return read(STRING);
		}catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Reads a {@link Compound} from the given {@link File}.
	 * Any thrown {@link Exception}s are caught and <code>null</code>
	 * is returned instead.
	 */
	public static Compound readNull(final File FILE) {
		try {
			return read(FILE);
		}catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Reads a {@link Compound} from the given {@link DataInputStream}.
	 * Any thrown {@link Exception}s are caught and <code>null</code>
	 * is returned instead.
	 */
	public static Compound readNull(final DataInputStream STREAM) {
		try {
			return read(STREAM);
		}catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Reads a {@link Compound} from the given {@link CompoundInput}.
	 * Any thrown {@link Exception}s are caught and <oce>null</code>
	 * is returned instead.
	 */
	public static Compound readNull(final CompoundInput INPUT) {
		try {
			return read(INPUT);
		}catch (Exception e) {
			return null;
		}
	}
	
}
