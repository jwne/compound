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

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Indicates the identifier to store a field under when
 * using {@link CompoundTagIO} to store {@link Object}
 * fields in {@link Compound} instances.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CompoundTag {
	
	/**
	 * The identifier with which values are to be stored.
	 * Must be unique to other identifiers used in the class.
	 */
	public String id();
	
	/**
	 * This field is included when calculating the exact identifier
	 * to be used in the {@link Compound}. This allows exclusion of
	 * old data, for example when a datatype is changed, without a
	 * new {@link id} field.
	 */
	public long version() default 0L;
	
	/**
	 * If <code>true</code> problems while handling this
	 * {@link CompoundTag} will be ignored, preventing a
	 * {@link CompoundTagException} from being thrown.
	 * <br />Defaults to <code>false</code>.
	 */
	public boolean ignoreProblems() default false;
	
}
