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

package io.github.lxgaming.binary.serializer.msgpack;

import io.github.lxgaming.binary.tag.BooleanTag;
import io.github.lxgaming.binary.tag.ByteArrayTag;
import io.github.lxgaming.binary.tag.ByteTag;
import io.github.lxgaming.binary.tag.CompoundTag;
import io.github.lxgaming.binary.tag.DoubleArrayTag;
import io.github.lxgaming.binary.tag.DoubleTag;
import io.github.lxgaming.binary.tag.FloatArrayTag;
import io.github.lxgaming.binary.tag.FloatTag;
import io.github.lxgaming.binary.tag.IntArrayTag;
import io.github.lxgaming.binary.tag.IntTag;
import io.github.lxgaming.binary.tag.ListTag;
import io.github.lxgaming.binary.tag.LongArrayTag;
import io.github.lxgaming.binary.tag.LongTag;
import io.github.lxgaming.binary.tag.ShortArrayTag;
import io.github.lxgaming.binary.tag.ShortTag;
import io.github.lxgaming.binary.tag.StringTag;
import io.github.lxgaming.binary.tag.Tag;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MessagePackTest {
    
    private final MessagePackSerializer serializer = new MessagePackSerializer();
    
    @Test
    public void testSerializer() {
        CompoundTag compound = new CompoundTag();
        compound.putBoolean("boolean_false", false);
        compound.putBoolean("boolean_true", true);
        compound.putByte("byte_max", Byte.MAX_VALUE);
        compound.putByte("byte_zero", (byte) 0);
        compound.putByte("byte_min", Byte.MIN_VALUE);
        compound.putShort("short_max", Short.MAX_VALUE);
        compound.putShort("short_zero", (short) 0);
        compound.putShort("short_min", Short.MIN_VALUE);
        compound.putInt("int_max", Integer.MAX_VALUE);
        compound.putInt("int_zero", 0);
        compound.putInt("int_min", Integer.MIN_VALUE);
        compound.putLong("long_max", Long.MAX_VALUE);
        compound.putLong("long_zero", 0L);
        compound.putLong("long_min", Long.MIN_VALUE);
        compound.putFloat("float_max", Float.MAX_VALUE);
        compound.putFloat("float_zero", 0F);
        compound.putFloat("float_min", Float.MIN_VALUE);
        compound.putDouble("double_max", Double.MAX_VALUE);
        compound.putDouble("double_zero", 0D);
        compound.putDouble("double_min", Double.MIN_VALUE);
        compound.putByteArray("byte_array_empty", new byte[0]);
        compound.putByteArray("byte_array", new byte[]{-1, 0, 1});
        compound.putString("string_empty", "");
        compound.putString("string", "Binary");
        compound.putShortArray("short_array_empty", new short[0]);
        compound.putShortArray("short_array", new short[]{-1, 0, 1});
        compound.putIntArray("int_array_empty", new int[0]);
        compound.putIntArray("int_array", new int[]{-1, 0, 1});
        compound.putLongArray("long_array_empty", new long[0]);
        compound.putLongArray("long_array", new long[]{-1, 0, 1});
        compound.putFloatArray("float_array_empty", new float[0]);
        compound.putFloatArray("float_array", new float[]{-1, 0, -1});
        compound.putDoubleArray("double_array_empty", new double[0]);
        compound.putDoubleArray("double_array", new double[]{-1, 0, -1});
        
        compound.putList("empty_list", createList());
        compound.putList("boolean_list", createList(new BooleanTag()));
        compound.putList("byte_list", createList(new ByteTag()));
        compound.putList("short_list", createList(new ShortTag()));
        compound.putList("int_list", createList(new IntTag()));
        compound.putList("long_list", createList(new LongTag()));
        compound.putList("float_list", createList(new FloatTag()));
        compound.putList("double_list", createList(new DoubleTag()));
        compound.putList("byte_array_list", createList(new ByteArrayTag()));
        compound.putList("string_list", createList(new StringTag()));
        compound.putList("short_array_list", createList(new ShortArrayTag()));
        compound.putList("int_array_list", createList(new IntArrayTag()));
        compound.putList("long_array_list", createList(new LongArrayTag()));
        compound.putList("float_array_list", createList(new FloatArrayTag()));
        compound.putList("double_array_list", createList(new DoubleArrayTag()));
        compound.putList("list_list", createList(createList()));
        compound.putList("compound_list", createList(new CompoundTag()));
        
        compound.putCompound("compound", compound.copy());
        
        byte[] bytes = write(compound);
        
        // prettyHexDump(bytes);
        
        Tag tag = read(bytes);
        
        Assertions.assertEquals(compound, tag);
    }
    
    private ListTag createList(Tag... tags) {
        ListTag list = new ListTag();
        for (Tag tag : tags) {
            list.add(tag);
        }
        
        return list;
    }
    
    private void prettyHexDump(byte[] bytes) {
        ByteBuf buffer = Unpooled.wrappedBuffer(bytes);
        System.out.println(ByteBufUtil.prettyHexDump(buffer));
    }
    
    private Tag read(byte[] bytes) {
        try (ByteArrayInputStream input = new ByteArrayInputStream(bytes)) {
            return serializer.read(input);
        } catch (IOException ex) {
            return Assertions.fail(ex);
        }
    }
    
    private byte[] write(Tag tag) {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            serializer.write(output, tag);
            return output.toByteArray();
        } catch (IOException ex) {
            return Assertions.fail(ex);
        }
    }
}