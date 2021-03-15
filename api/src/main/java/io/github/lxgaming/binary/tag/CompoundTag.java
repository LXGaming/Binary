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
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class CompoundTag implements CollectionTag {
    
    private final Map<String, Tag> tags;
    
    public CompoundTag() {
        this(new HashMap<>());
    }
    
    public CompoundTag(@NonNull Map<String, Tag> tags) {
        this.tags = tags;
    }
    
    public boolean contains(@NonNull String key) {
        return this.tags.containsKey(key);
    }
    
    public @Nullable Tag get(@NonNull String key) {
        return this.tags.get(key);
    }
    
    public void put(@NonNull String key, @NonNull Tag tag) {
        this.tags.put(key, tag);
    }
    
    public void remove(@NonNull String key) {
        this.tags.remove(key);
    }
    
    public void clear() {
        this.tags.clear();
    }
    
    public @NonNull Set<String> keySet() {
        return this.tags.keySet();
    }
    
    public @NonNull Collection<Tag> values() {
        return this.tags.values();
    }
    
    public @NonNull Set<Map.Entry<String, Tag>> entrySet() {
        return this.tags.entrySet();
    }
    
    // region Boolean
    
    public boolean getBoolean(@NonNull String key) {
        return getBoolean(key, false);
    }
    
    public boolean getBoolean(@NonNull String key, boolean defaultValue) {
        Tag tag = this.tags.get(key);
        return tag instanceof BooleanTag ? ((BooleanTag) tag).getValue() : defaultValue;
    }
    
    public void putBoolean(@NonNull String key, boolean value) {
        this.tags.put(key, new BooleanTag(value));
    }
    
    // endregion
    // region Byte
    
    public byte getByte(@NonNull String key) {
        return getByte(key, (byte) 0);
    }
    
    public byte getByte(@NonNull String key, byte defaultValue) {
        Tag tag = this.tags.get(key);
        return tag instanceof ByteTag ? ((ByteTag) tag).getValue() : defaultValue;
    }
    
    public void putByte(@NonNull String key, byte value) {
        this.tags.put(key, new ByteTag(value));
    }
    
    // endregion
    // region Short
    
    public short getShort(@NonNull String key) {
        return getShort(key, (short) 0);
    }
    
    public short getShort(@NonNull String key, short defaultValue) {
        Tag tag = this.tags.get(key);
        return tag instanceof ShortTag ? ((ShortTag) tag).getValue() : defaultValue;
    }
    
    public void putShort(@NonNull String key, short value) {
        this.tags.put(key, new ShortTag(value));
    }
    
    // endregion
    // region Int
    
    public int getInt(@NonNull String key) {
        return getInt(key, 0);
    }
    
    public int getInt(@NonNull String key, int defaultValue) {
        Tag tag = this.tags.get(key);
        return tag instanceof IntTag ? ((IntTag) tag).getValue() : defaultValue;
    }
    
    public void putInt(@NonNull String key, int value) {
        this.tags.put(key, new IntTag(value));
    }
    
    // endregion
    // region Long
    
    public long getLong(@NonNull String key) {
        return getLong(key, 0L);
    }
    
    public long getLong(@NonNull String key, long defaultValue) {
        Tag tag = this.tags.get(key);
        return tag instanceof LongTag ? ((LongTag) tag).getValue() : defaultValue;
    }
    
    public void putLong(@NonNull String key, long value) {
        this.tags.put(key, new LongTag(value));
    }
    
    // endregion
    // region Float
    
    public float getFloat(@NonNull String key) {
        return getFloat(key, 0F);
    }
    
    public float getFloat(@NonNull String key, float defaultValue) {
        Tag tag = this.tags.get(key);
        return tag instanceof FloatTag ? ((FloatTag) tag).getValue() : defaultValue;
    }
    
    public void putFloat(@NonNull String key, float value) {
        this.tags.put(key, new FloatTag(value));
    }
    
    // endregion
    // region Double
    
    public double getDouble(@NonNull String key) {
        return getDouble(key, 0D);
    }
    
    public double getDouble(@NonNull String key, double defaultValue) {
        Tag tag = this.tags.get(key);
        return tag instanceof DoubleTag ? ((DoubleTag) tag).getValue() : defaultValue;
    }
    
    public void putDouble(@NonNull String key, double value) {
        this.tags.put(key, new DoubleTag(value));
    }
    
    // endregion
    // region ByteArray
    
    public byte @NonNull [] getByteArray(@NonNull String key) {
        Tag tag = this.tags.get(key);
        return tag instanceof ByteArrayTag ? ((ByteArrayTag) tag).getValue() : new byte[0];
    }
    
    public byte @NonNull [] getByteArray(@NonNull String key, byte @NonNull [] defaultValue) {
        Tag tag = this.tags.get(key);
        return tag instanceof ByteArrayTag ? ((ByteArrayTag) tag).getValue() : defaultValue;
    }
    
    public void putByteArray(@NonNull String key, byte @NonNull [] value) {
        this.tags.put(key, new ByteArrayTag(value));
    }
    
    // endregion
    // region String
    
    public @NonNull String getString(@NonNull String key) {
        return getString(key, "");
    }
    
    public @NonNull String getString(@NonNull String key, @NonNull String defaultValue) {
        Tag tag = this.tags.get(key);
        return tag instanceof StringTag ? ((StringTag) tag).getValue() : defaultValue;
    }
    
    public void putString(@NonNull String key, @NonNull String value) {
        this.tags.put(key, new StringTag(value));
    }
    
    // endregion
    // region ShortArray
    
    public short @NonNull [] getShortArray(@NonNull String key) {
        Tag tag = this.tags.get(key);
        return tag instanceof ShortArrayTag ? ((ShortArrayTag) tag).getValue() : new short[0];
    }
    
    public short @NonNull [] getShortArray(@NonNull String key, short @NonNull [] defaultValue) {
        Tag tag = this.tags.get(key);
        return tag instanceof ShortArrayTag ? ((ShortArrayTag) tag).getValue() : defaultValue;
    }
    
    public void putShortArray(@NonNull String key, short @NonNull [] value) {
        this.tags.put(key, new ShortArrayTag(value));
    }
    
    // endregion
    // region IntArray
    
    public int @NonNull [] getIntArray(@NonNull String key) {
        Tag tag = this.tags.get(key);
        return tag instanceof IntArrayTag ? ((IntArrayTag) tag).getValue() : new int[0];
    }
    
    public int @NonNull [] getIntArray(@NonNull String key, int @NonNull [] defaultValue) {
        Tag tag = this.tags.get(key);
        return tag instanceof IntArrayTag ? ((IntArrayTag) tag).getValue() : defaultValue;
    }
    
    public void putIntArray(@NonNull String key, int @NonNull [] value) {
        this.tags.put(key, new IntArrayTag(value));
    }
    
    // endregion
    // region LongArray
    
    public long @NonNull [] getLongArray(@NonNull String key) {
        Tag tag = this.tags.get(key);
        return tag instanceof LongArrayTag ? ((LongArrayTag) tag).getValue() : new long[0];
    }
    
    public long @NonNull [] getLongArray(@NonNull String key, long @NonNull [] defaultValue) {
        Tag tag = this.tags.get(key);
        return tag instanceof LongArrayTag ? ((LongArrayTag) tag).getValue() : defaultValue;
    }
    
    public void putLongArray(@NonNull String key, long @NonNull [] value) {
        this.tags.put(key, new LongArrayTag(value));
    }
    
    // endregion
    // region FloatArray
    
    public float @NonNull [] getFloatArray(@NonNull String key) {
        Tag tag = this.tags.get(key);
        return tag instanceof FloatArrayTag ? ((FloatArrayTag) tag).getValue() : new float[0];
    }
    
    public float @NonNull [] getFloatArray(@NonNull String key, float @NonNull [] defaultValue) {
        Tag tag = this.tags.get(key);
        return tag instanceof FloatArrayTag ? ((FloatArrayTag) tag).getValue() : defaultValue;
    }
    
    public void putFloatArray(@NonNull String key, float @NonNull [] value) {
        this.tags.put(key, new FloatArrayTag(value));
    }
    
    // endregion
    // region DoubleArray
    
    public double @NonNull [] getDoubleArray(@NonNull String key) {
        Tag tag = this.tags.get(key);
        return tag instanceof DoubleArrayTag ? ((DoubleArrayTag) tag).getValue() : new double[0];
    }
    
    public double @NonNull [] getDoubleArray(@NonNull String key, double @NonNull [] defaultValue) {
        Tag tag = this.tags.get(key);
        return tag instanceof DoubleArrayTag ? ((DoubleArrayTag) tag).getValue() : defaultValue;
    }
    
    public void putDoubleArray(@NonNull String key, double @NonNull [] value) {
        this.tags.put(key, new DoubleArrayTag(value));
    }
    
    // endregion
    // region List
    
    public @NonNull ListTag getList(@NonNull String key) {
        Tag tag = this.tags.get(key);
        return tag instanceof ListTag ? (ListTag) tag : new ListTag();
    }
    
    public @NonNull ListTag getList(@NonNull String key, @NonNull ListTag defaultValue) {
        Tag tag = this.tags.get(key);
        return tag instanceof ListTag ? (ListTag) tag : defaultValue;
    }
    
    public @NonNull ListTag getList(@NonNull String key, @NonNull Class<? extends Tag> type) {
        Tag tag = this.tags.get(key);
        if (tag instanceof ListTag) {
            ListTag list = (ListTag) tag;
            if (list.getType() == type) {
                return list;
            }
        }
        
        return new ListTag();
    }
    
    public @NonNull ListTag getList(@NonNull String key, @NonNull Class<? extends Tag> type, @NonNull ListTag defaultValue) {
        Tag tag = this.tags.get(key);
        if (tag instanceof ListTag) {
            ListTag list = (ListTag) tag;
            if (list.getType() == type) {
                return list;
            }
        }
        
        return defaultValue;
    }
    
    public void putList(@NonNull String key, @NonNull ListTag value) {
        this.tags.put(key, value);
    }
    
    // endregion
    // region Compound
    
    public @NonNull CompoundTag getCompound(@NonNull String key) {
        Tag tag = this.tags.get(key);
        return tag instanceof CompoundTag ? (CompoundTag) tag : new CompoundTag();
    }
    
    public @NonNull CompoundTag getCompound(@NonNull String key, @NonNull CompoundTag defaultValue) {
        Tag tag = this.tags.get(key);
        return tag instanceof CompoundTag ? (CompoundTag) tag : defaultValue;
    }
    
    public void putCompound(@NonNull String key, @NonNull CompoundTag value) {
        this.tags.put(key, value);
    }
    
    // endregion
    
    @Override
    public int size() {
        return this.tags.size();
    }
    
    @Override
    public boolean isEmpty() {
        return this.tags.isEmpty();
    }
    
    @Override
    public @NonNull CompoundTag copy() {
        CompoundTag compound = new CompoundTag();
        for (Map.Entry<String, Tag> entry : this.tags.entrySet()) {
            compound.put(entry.getKey(), entry.getValue().copy());
        }
        
        return compound;
    }
    
    @Override
    public int hashCode() {
        return this.tags.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof CompoundTag && this.tags.equals(((CompoundTag) obj).tags));
    }
}