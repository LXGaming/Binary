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

public final class LongArrayTag implements CollectionTag {
    
    private long[] value;
    
    public LongArrayTag() {
        this(new long[0]);
    }
    
    public LongArrayTag(long @NonNull [] value) {
        this.value = value;
    }
    
    public long @NonNull [] getValue() {
        return this.value;
    }
    
    public void setValue(long @NonNull [] value) {
        this.value = value;
    }
    
    public long get(@NonNegative int index) {
        return this.value[index];
    }
    
    public void set(@NonNegative int index, long value) {
        this.value[index] = value;
    }
    
    @Override
    public int size() {
        return this.value.length;
    }
    
    @Override
    public @NonNull LongArrayTag copy() {
        long[] value = new long[this.value.length];
        System.arraycopy(this.value, 0, value, 0, value.length);
        return new LongArrayTag(value);
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(this.value);
    }
    
    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof LongArrayTag && Arrays.equals(this.value, ((LongArrayTag) obj).value));
    }
}