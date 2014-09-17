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
package net.alexmack.compound.tag;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;

import net.alexmack.compound.Compound;

/**
 * Provides methods for reading/writing {@link Compound} instances using
 * {@link CompoundTag} annotations.
 */
public class CompoundTagIO {

	/**
	 * Writes fields marked with {@link CompoundTag} annotations
	 * from the given {@link Object} into a {@link Compound}, which
	 * can be used to re-assign the fields at a later date using the
	 * {@link read()} method.
	 * @throws CompoundTagException
	 */
	public static Compound write(Object object) {
		try {
			Compound compound = new Compound();
			Class<? extends Object> objectClass = object.getClass();
			for (Field field : findFields(objectClass)) {
				field.setAccessible(true);
				
				CompoundTag tag = findTag(field);
				if (tag == null) continue;
				
				try {
					compound.set(tag.id() + Long.toHexString(tag.version()), field.get(object));
				}catch (Exception e) {
					if (!tag.ignoreProblems())
						throw e;
				}
			}
			return compound;
		}catch (Exception e) {
			if (e instanceof CompoundTagException) {
				throw (CompoundTagException) e;
			}
			throw new CompoundTagException(null, e);
		}
	}
	
	/**
	 * Reads fields marked with {@link CompoundTag} annotations
	 * from the given {@link Compound} into a the given {@link Object}.
	 * @throws CompoundTagException
	 */
	public static void read(Object object, Compound compound) {
		try {
			Class<? extends Object> objectClass = object.getClass();
			for (Field field : findFields(objectClass)) {
				field.setAccessible(true);
				
				CompoundTag tag = findTag(field);
				if (tag == null) continue;
				
				try {
					field.set(object, compound.get(tag.id() + Long.toHexString(tag.version())));
				}catch (Exception e) {
					if (!tag.ignoreProblems())
						throw e;
				}
			}
		}catch (Exception e) {
			if (e instanceof CompoundTagException) {
				throw (CompoundTagException) e;
			}
			throw new CompoundTagException(null, e);
		}
	}
	
	private static HashSet<Field> findFields(Class<? extends Object> c) {
		HashSet<Field> fields = new HashSet<>();
		for (Field f : c.getDeclaredFields())
			fields.add(f);
		
		Class<?> s = c.getSuperclass();
		if (s == null)
			return fields;
		
		fields.addAll(findFields(s));
		return fields;
	}
	
	private static CompoundTag findTag(Field f) {
		for (Annotation a : f.getAnnotations()) {
			if (a instanceof CompoundTag) {
				return (CompoundTag) a;
			}
		}
		return null;
	}
	
}
