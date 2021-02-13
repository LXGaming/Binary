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

public final class ByteArrayTag implements CollectionTag {
    
    private byte[] value;
    
    public ByteArrayTag() {
        this(new byte[0]);
    }
    
    public ByteArrayTag(byte @NonNull [] value) {
        this.value = value;
    }
    
    public byte @NonNull [] getValue() {
        return this.value;
    }
    
    public void setValue(byte @NonNull [] value) {
        this.value = value;
    }
    
    public byte get(@NonNegative int index) {
        return this.value[index];
    }
    
    public void set(@NonNegative int index, byte value) {
        this.value[index] = value;
    }
    
    @Override
    public int size() {
        return this.value.length;
    }
    
    @Override
    public @NonNull ByteArrayTag copy() {
        byte[] value = new byte[this.value.length];
        System.arraycopy(this.value, 0, value, 0, value.length);
        return new ByteArrayTag(value);
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(this.value);
    }
    
    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof ByteArrayTag && Arrays.equals(this.value, ((ByteArrayTag) obj).value));
    }
}