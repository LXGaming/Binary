/*
 * Copyright 2021 Alex Thomson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.lxgaming.binary.tag;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Arrays;

public final class ShortArrayTag implements CollectionTag {
    
    private short[] value;
    
    public ShortArrayTag() {
        this(new short[0]);
    }
    
    public ShortArrayTag(short @NonNull [] value) {
        this.value = value;
    }
    
    public short @NonNull [] getValue() {
        return this.value;
    }
    
    public void setValue(short @NonNull [] value) {
        this.value = value;
    }
    
    public short get(@NonNegative int index) {
        return this.value[index];
    }
    
    public void set(@NonNegative int index, short value) {
        this.value[index] = value;
    }
    
    @Override
    public int size() {
        return this.value.length;
    }
    
    @Override
    public @NonNull ShortArrayTag copy() {
        short[] value = new short[this.value.length];
        System.arraycopy(this.value, 0, value, 0, value.length);
        return new ShortArrayTag(value);
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(this.value);
    }
    
    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof ShortArrayTag && Arrays.equals(this.value, ((ShortArrayTag) obj).value));
    }
}