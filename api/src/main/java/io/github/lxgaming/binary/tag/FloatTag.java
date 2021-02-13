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

public final class FloatTag implements Tag {
    
    private float value;
    
    public FloatTag() {
        this(0F);
    }
    
    public FloatTag(float value) {
        this.value = value;
    }
    
    public float getValue() {
        return this.value;
    }
    
    public void setValue(float value) {
        this.value = value;
    }
    
    @Override
    public @NonNull FloatTag copy() {
        return new FloatTag(this.value);
    }
    
    @Override
    public int hashCode() {
        return Float.hashCode(this.value);
    }
    
    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof FloatTag && this.value == ((FloatTag) obj).value);
    }
}