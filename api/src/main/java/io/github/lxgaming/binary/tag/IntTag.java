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

public final class IntTag implements Tag {
    
    private int value;
    
    public IntTag() {
        this(0);
    }
    
    public IntTag(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return this.value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
    
    @Override
    public @NonNull IntTag copy() {
        return new IntTag(this.value);
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(this.value);
    }
    
    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof IntTag && this.value == ((IntTag) obj).value);
    }
}