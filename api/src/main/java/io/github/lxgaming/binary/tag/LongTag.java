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

import org.checkerframework.checker.nullness.qual.NonNull;

public final class LongTag implements Tag {
    
    private long value;
    
    public LongTag() {
        this(0L);
    }
    
    public LongTag(long value) {
        this.value = value;
    }
    
    public long getValue() {
        return this.value;
    }
    
    public void setValue(long value) {
        this.value = value;
    }
    
    @Override
    public @NonNull LongTag copy() {
        return new LongTag(this.value);
    }
    
    @Override
    public int hashCode() {
        return Long.hashCode(this.value);
    }
    
    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof LongTag && this.value == ((LongTag) obj).value);
    }
}