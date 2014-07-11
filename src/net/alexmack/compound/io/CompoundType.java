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

import java.math.BigDecimal;
import java.math.BigInteger;

import net.alexmack.compound.Compound;
import net.alexmack.compound.CompoundCast;
import net.alexmack.compound.io.operator.CompoundInput;
import net.alexmack.compound.io.operator.CompoundOutput;

public enum CompoundType {
	UNKNOWN(Byte.MIN_VALUE, "????", null),
	COMPOUND((byte)(Byte.MIN_VALUE + (byte)1), "comp", null),
	
	NULL((byte)0x00, "null", new CompoundTypeIO(){

		@Override
		public void write(CompoundOutput out, Object val) throws Exception {}

		@Override
		public Object read(CompoundInput in) throws Exception {
			return null;
		}}),
	
	BOOLEAN((byte)0x01, "bool", new CompoundTypeIO(){

		@Override
		public void write(CompoundOutput out, Object val) throws Exception {
			out.writeBoolean((Boolean) val);
		}

		@Override
		public Object read(CompoundInput in) throws Exception {
			return in.readBoolean();
		}}),
	
	INTEGER((byte)0x02, "intg", new CompoundTypeIO(){

		@Override
		public void write(CompoundOutput out, Object val) throws Exception {
			out.writeInt(CompoundCast.asInt(val));
		}

		@Override
		public Object read(CompoundInput in) throws Exception {
			return in.readInt();
		}}),
	INTEGER_LONG((byte)0x03, "intl", new CompoundTypeIO(){

		@Override
		public void write(CompoundOutput out, Object val) throws Exception {
			out.writeLong(CompoundCast.asLong(val));
		}

		@Override
		public Object read(CompoundInput in) throws Exception {
			return in.readLong();
		}}),
	
	DOUBLE((byte)0x04, "dobl", new CompoundTypeIO(){

		@Override
		public void write(CompoundOutput out, Object val) throws Exception {
			out.writeDouble(CompoundCast.asDouble(val));
		}

		@Override
		public Object read(CompoundInput in) throws Exception {
			return in.readDouble();
		}}),
	
	STRING((byte)0x05, "strg", new CompoundTypeIO(){

		@Override
		public void write(CompoundOutput out, Object val) throws Exception {
			out.writeString((String) val);
		}

		@Override
		public Object read(CompoundInput in) throws Exception {
			return in.readString();
		}}),
		
	BIG_INTEGER((byte)0x06, "bigi", new CompoundTypeIO() {

		@Override
		public void write(CompoundOutput out, Object val) throws Exception {
			out.writeString(((BigInteger)val).toString(CompoundIO.RADIX));
		}

		@Override
		public Object read(CompoundInput in) throws Exception {
			return new BigInteger(in.readString(), CompoundIO.RADIX);
		}
		
	}),
	
	BIG_DECIMAL((byte)0x07, "bigd", new CompoundTypeIO() {

		@Override
		public void write(CompoundOutput out, Object val) throws Exception {
			out.writeString(((BigDecimal)val).toString());
		}

		@Override
		public Object read(CompoundInput in) throws Exception {
			return new BigDecimal(in.readString());
		}
		
	});
	
	public final byte ID;
	public final String ID_STRING;
	
	public final CompoundTypeIO IO;
	
	CompoundType(byte i, String s, CompoundTypeIO io) {
		ID = i;
		ID_STRING = s;
		IO = io;
	}
	
	public boolean isValid(){
		return IO != null;
	}
	
	public static CompoundType get(Object o){
		if (o == null)
			return NULL;
		else if (o instanceof Compound)
			return COMPOUND;
		else if (o instanceof Boolean)
			return BOOLEAN;
		else if (o instanceof Byte || o instanceof Short || o instanceof Integer)
			return INTEGER;
		else if (o instanceof Long)
			return INTEGER_LONG;
		else if (o instanceof Float || o instanceof Double)
			return DOUBLE;
		else if (o instanceof String)
			return STRING;
		else if (o instanceof BigInteger)
			return BIG_INTEGER;
		else if (o instanceof BigDecimal)
			return BIG_DECIMAL;
		return UNKNOWN;
	}
	
	public static CompoundType get(byte i){
		for (CompoundType sct : values())
			if (sct.ID == i)
				return sct;
		
		return null;
	}
	
	public static CompoundType get(String i){
		for (CompoundType sct : values())
			if (sct.ID_STRING.equalsIgnoreCase(i))
				return sct;
		
		return null;
	}
	
}