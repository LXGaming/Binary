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

import io.github.lxgaming.binary.BinarySerializer;
import io.github.lxgaming.binary.tag.BooleanTag;
import io.github.lxgaming.binary.tag.ByteArrayTag;
import io.github.lxgaming.binary.tag.ByteTag;
import io.github.lxgaming.binary.tag.CollectionTag;
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
import org.checkerframework.checker.nullness.qual.NonNull;
import org.msgpack.core.ExtensionTypeHeader;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessageFormat;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.core.buffer.OutputStreamBufferOutput;
import org.msgpack.value.ValueType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class MessagePackSerializer implements BinarySerializer {
    
    public static final byte ARRAY_ID = 0x00;
    public static final byte LIST_ID = 0x01;
    
    protected final MessagePack.PackerConfig packerConfig;
    protected final MessagePack.UnpackerConfig unpackerConfig;
    
    public MessagePackSerializer() {
        this(MessagePack.DEFAULT_PACKER_CONFIG, MessagePack.DEFAULT_UNPACKER_CONFIG);
    }
    
    public MessagePackSerializer(MessagePack.@NonNull PackerConfig packerConfig, MessagePack.@NonNull UnpackerConfig unpackerConfig) {
        this.packerConfig = packerConfig;
        this.unpackerConfig = unpackerConfig;
    }
    
    @Override
    public @NonNull Tag read(@NonNull InputStream input) throws IOException {
        try (MessageUnpacker unpacker = unpackerConfig.newUnpacker(input)) {
            return read(unpacker);
        }
    }
    
    @Override
    public void write(@NonNull OutputStream output, @NonNull Tag tag) throws IOException {
        try (MessagePacker packer = new MessagePackerImpl(new OutputStreamBufferOutput(output), packerConfig)) {
            write(packer, tag);
        }
    }
    
    public @NonNull Tag read(@NonNull MessageUnpacker unpacker) throws IOException {
        MessageFormat format = unpacker.getNextFormat();
        ValueType type = format.getValueType();
        if (format == MessageFormat.BOOLEAN) {
            return new BooleanTag(unpacker.unpackBoolean());
        } else if (format == MessageFormat.POSFIXINT || format == MessageFormat.UINT8 || format == MessageFormat.INT8 || format == MessageFormat.NEGFIXINT) {
            return new ByteTag(unpacker.unpackByte());
        } else if (format == MessageFormat.UINT16 || format == MessageFormat.INT16) {
            return new ShortTag(unpacker.unpackShort());
        } else if (format == MessageFormat.UINT32 || format == MessageFormat.INT32) {
            return new IntTag(unpacker.unpackInt());
        } else if (format == MessageFormat.UINT64 || format == MessageFormat.INT64) {
            return new LongTag(unpacker.unpackLong());
        } else if (format == MessageFormat.FLOAT32) {
            return new FloatTag(unpacker.unpackFloat());
        } else if (format == MessageFormat.FLOAT64) {
            return new DoubleTag(unpacker.unpackDouble());
        } else if (type.isStringType()) {
            return new StringTag(unpacker.unpackString());
        } else if (type.isArrayType()) {
            return readArray(unpacker);
        } else if (type.isMapType()) {
            return readCompound(unpacker);
        } else if (type.isExtensionType()) {
            return readExtension(unpacker);
        } else {
            throw new UnsupportedOperationException(String.format("%s is not supported", format));
        }
    }
    
    public void write(@NonNull MessagePacker packer, @NonNull Tag tag) throws IOException {
        if (tag instanceof BooleanTag) {
            packer.packBoolean(((BooleanTag) tag).getValue());
        } else if (tag instanceof ByteTag) {
            packer.packByte(((ByteTag) tag).getValue());
        } else if (tag instanceof ShortTag) {
            packer.packShort(((ShortTag) tag).getValue());
        } else if (tag instanceof IntTag) {
            packer.packInt(((IntTag) tag).getValue());
        } else if (tag instanceof LongTag) {
            packer.packLong(((LongTag) tag).getValue());
        } else if (tag instanceof FloatTag) {
            packer.packFloat(((FloatTag) tag).getValue());
        } else if (tag instanceof DoubleTag) {
            packer.packDouble(((DoubleTag) tag).getValue());
        } else if (tag instanceof StringTag) {
            packer.packString(((StringTag) tag).getValue());
        } else if (tag instanceof CollectionTag) {
            writeCollection(packer, (CollectionTag) tag);
        } else {
            throw new UnsupportedOperationException(String.format("%s is not supported", tag.getClass().getName()));
        }
    }
    
    protected CollectionTag readArray(@NonNull MessageUnpacker unpacker) throws IOException {
        byte code = unpacker.unpackByte();
        int size = unpacker.unpackArrayHeader();
        if (code == MessagePack.Code.INT8) {
            ByteArrayTag tag = new ByteArrayTag(new byte[size]);
            for (int index = 0; index < size; index++) {
                tag.set(index, unpacker.unpackByte());
            }
            
            return tag;
        } else if (code == MessagePack.Code.INT16) {
            ShortArrayTag tag = new ShortArrayTag(new short[size]);
            for (int index = 0; index < size; index++) {
                tag.set(index, unpacker.unpackShort());
            }
            
            return tag;
        } else if (code == MessagePack.Code.INT32) {
            IntArrayTag tag = new IntArrayTag(new int[size]);
            for (int index = 0; index < size; index++) {
                tag.set(index, unpacker.unpackInt());
            }
            
            return tag;
        } else if (code == MessagePack.Code.INT64) {
            LongArrayTag tag = new LongArrayTag(new long[size]);
            for (int index = 0; index < size; index++) {
                tag.set(index, unpacker.unpackLong());
            }
            
            return tag;
        } else if (code == MessagePack.Code.FLOAT32) {
            FloatArrayTag tag = new FloatArrayTag(new float[size]);
            for (int index = 0; index < size; index++) {
                tag.set(index, unpacker.unpackFloat());
            }
            
            return tag;
        } else if (code == MessagePack.Code.FLOAT64) {
            DoubleArrayTag tag = new DoubleArrayTag(new double[size]);
            for (int index = 0; index < size; index++) {
                tag.set(index, unpacker.unpackDouble());
            }
            
            return tag;
        } else {
            throw new UnsupportedOperationException(String.format("%s is not supported", code));
        }
    }
    
    protected void writeCollection(@NonNull MessagePacker packer, @NonNull CollectionTag collection) throws IOException {
        if (collection instanceof ByteArrayTag) {
            MessageBufferPacker arrayPacker = packerConfig.newBufferPacker();
            arrayPacker.packByte(MessagePack.Code.INT8);
            arrayPacker.packArrayHeader(collection.size());
            for (byte value : ((ByteArrayTag) collection).getValue()) {
                arrayPacker.packByte(value);
            }
            
            writeExtension(packer, ARRAY_ID, arrayPacker.toByteArray());
        } else if (collection instanceof ShortArrayTag) {
            MessageBufferPacker arrayPacker = packerConfig.newBufferPacker();
            arrayPacker.packByte(MessagePack.Code.INT16);
            arrayPacker.packArrayHeader(collection.size());
            for (short value : ((ShortArrayTag) collection).getValue()) {
                arrayPacker.packShort(value);
            }
            
            writeExtension(packer, ARRAY_ID, arrayPacker.toByteArray());
        } else if (collection instanceof IntArrayTag) {
            MessageBufferPacker arrayPacker = packerConfig.newBufferPacker();
            arrayPacker.packByte(MessagePack.Code.INT32);
            arrayPacker.packArrayHeader(collection.size());
            for (int value : ((IntArrayTag) collection).getValue()) {
                arrayPacker.packInt(value);
            }
            
            writeExtension(packer, ARRAY_ID, arrayPacker.toByteArray());
        } else if (collection instanceof LongArrayTag) {
            MessageBufferPacker arrayPacker = packerConfig.newBufferPacker();
            arrayPacker.packByte(MessagePack.Code.INT64);
            arrayPacker.packArrayHeader(collection.size());
            for (long value : ((LongArrayTag) collection).getValue()) {
                arrayPacker.packLong(value);
            }
            
            writeExtension(packer, ARRAY_ID, arrayPacker.toByteArray());
        } else if (collection instanceof FloatArrayTag) {
            MessageBufferPacker arrayPacker = packerConfig.newBufferPacker();
            arrayPacker.packByte(MessagePack.Code.FLOAT32);
            arrayPacker.packArrayHeader(collection.size());
            for (float value : ((FloatArrayTag) collection).getValue()) {
                arrayPacker.packFloat(value);
            }
            
            writeExtension(packer, ARRAY_ID, arrayPacker.toByteArray());
        } else if (collection instanceof DoubleArrayTag) {
            MessageBufferPacker arrayPacker = packerConfig.newBufferPacker();
            arrayPacker.packByte(MessagePack.Code.FLOAT64);
            arrayPacker.packArrayHeader(collection.size());
            for (double value : ((DoubleArrayTag) collection).getValue()) {
                arrayPacker.packDouble(value);
            }
            
            writeExtension(packer, ARRAY_ID, arrayPacker.toByteArray());
        } else if (collection instanceof CompoundTag) {
            writeCompound(packer, (CompoundTag) collection);
        } else if (collection instanceof ListTag) {
            MessageBufferPacker listPacker = new MessageBufferPackerImpl(packerConfig);
            writeList(listPacker, (ListTag) collection);
            writeExtension(packer, LIST_ID, listPacker.toByteArray());
        } else {
            throw new UnsupportedOperationException(String.format("%s is not supported", collection.getClass().getName()));
        }
    }
    
    protected CompoundTag readCompound(@NonNull MessageUnpacker unpacker) throws IOException {
        int size = unpacker.unpackMapHeader();
        CompoundTag compound = new CompoundTag();
        for (int index = 0; index < size; index++) {
            String key = unpacker.unpackString();
            Tag value = read(unpacker);
            compound.put(key, value);
        }
        
        return compound;
    }
    
    protected void writeCompound(@NonNull MessagePacker packer, @NonNull CompoundTag compound) throws IOException {
        packer.packMapHeader(compound.size());
        for (Map.Entry<String, Tag> entry : compound.entrySet()) {
            packer.packString(entry.getKey());
            write(packer, entry.getValue());
        }
    }
    
    protected CollectionTag readExtension(@NonNull MessageUnpacker unpacker) throws IOException {
        ExtensionTypeHeader header = unpacker.unpackExtensionTypeHeader();
        byte[] bytes = unpacker.readPayload(header.getLength());
        MessageUnpacker extensionPacker = unpackerConfig.newUnpacker(bytes);
        if (header.getType() == ARRAY_ID) {
            return readArray(extensionPacker);
        } else if (header.getType() == LIST_ID) {
            return readList(extensionPacker);
        } else {
            throw new UnsupportedOperationException(String.format("%s is not supported", header.getType()));
        }
    }
    
    protected void writeExtension(@NonNull MessagePacker packer, byte extensionType, byte[] bytes) throws IOException {
        packer.packExtensionTypeHeader(extensionType, bytes.length);
        packer.addPayload(bytes);
    }
    
    protected ListTag readList(@NonNull MessageUnpacker unpacker) throws IOException {
        int size = unpacker.unpackArrayHeader();
        ListTag list = new ListTag();
        for (int index = 0; index < size; index++) {
            Tag tag = read(unpacker);
            list.add(tag);
        }
        
        return list;
    }
    
    protected void writeList(@NonNull MessagePacker packer, @NonNull ListTag list) throws IOException {
        packer.packArrayHeader(list.size());
        for (Tag tag : list) {
            write(packer, tag);
        }
    }
}