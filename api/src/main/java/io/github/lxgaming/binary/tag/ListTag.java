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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ListTag implements CollectionTag, Iterable<Tag> {
    
    private final List<Tag> tags;
    private Class<? extends Tag> type;
    
    public ListTag() {
        this(Tag.class);
    }
    
    public ListTag(@NonNull Class<? extends Tag> type) {
        this(new ArrayList<>(), type);
    }
    
    public ListTag(@NonNull List<Tag> tags, @NonNull Class<? extends Tag> type) {
        this.tags = tags;
        this.type = type;
    }
    
    public boolean contains(@NonNull Tag tag) {
        return this.tags.contains(tag);
    }
    
    public @NonNull Tag get(@NonNegative int index) {
        return this.tags.get(index);
    }
    
    public boolean add(@NonNull Tag tag) {
        checkType(tag.getClass());
        return this.tags.add(tag);
    }
    
    public void add(@NonNegative int index, @NonNull Tag tag) {
        checkType(tag.getClass());
        this.tags.add(index, tag);
    }
    
    public @NonNull Tag set(@NonNegative int index, @NonNull Tag tag) {
        checkType(tag.getClass());
        return this.tags.set(index, tag);
    }
    
    public boolean remove(@NonNull Tag tag) {
        return this.tags.remove(tag);
    }
    
    public @NonNull Tag remove(@NonNegative int index) {
        return this.tags.remove(index);
    }
    
    public void clear() {
        this.tags.clear();
    }
    
    public @NonNull Class<? extends Tag> getType() {
        return type;
    }
    
    private void checkType(@NonNull Class<? extends Tag> type) {
        if (this.type == Tag.class) {
            this.type = type;
        }
        
        if (this.type != type) {
            throw new IllegalArgumentException(String.format("Trying to add tag of type %s to list of %s", type, this.type));
        }
    }
    
    @Override
    public @NonNull Iterator<Tag> iterator() {
        return this.tags.iterator();
    }
    
    @Override
    public int size() {
        return this.tags.size();
    }
    
    @Override
    public boolean isEmpty() {
        return this.tags.isEmpty();
    }
    
    @Override
    public @NonNull ListTag copy() {
        ListTag list = new ListTag(this.type);
        for (Tag tag : this.tags) {
            list.tags.add(tag.copy());
        }
        
        return list;
    }
    
    @Override
    public int hashCode() {
        return this.tags.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof ListTag && this.tags.equals(((ListTag) obj).tags));
    }
}