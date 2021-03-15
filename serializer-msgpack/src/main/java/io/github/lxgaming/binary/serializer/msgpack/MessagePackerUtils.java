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

import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

public class MessagePackerUtils {
    
    private static MethodHandle writeByteAndShort;
    private static MethodHandle writeByteAndInt;
    private static MethodHandle writeByteAndLong;
    
    static {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        
        try {
            Method writeByteAndShortMethod = MessagePacker.class.getDeclaredMethod("writeByteAndShort", byte.class, short.class);
            writeByteAndShortMethod.setAccessible(true);
            writeByteAndShort = lookup.unreflect(writeByteAndShortMethod);
            
            Method writeByteAndIntMethod = MessagePacker.class.getDeclaredMethod("writeByteAndInt", byte.class, int.class);
            writeByteAndIntMethod.setAccessible(true);
            writeByteAndInt = lookup.unreflect(writeByteAndIntMethod);
            
            Method writeByteAndLongMethod = MessagePacker.class.getDeclaredMethod("writeByteAndLong", byte.class, long.class);
            writeByteAndLongMethod.setAccessible(true);
            writeByteAndLong = lookup.unreflect(writeByteAndLongMethod);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
    
    public static MessagePacker packShort(MessagePacker packer, short value) throws IOException {
        try {
            if (value < 0) {
                MessagePackerUtils.writeByteAndShort.invoke(packer, MessagePack.Code.INT16, value);
            } else {
                MessagePackerUtils.writeByteAndShort.invoke(packer, MessagePack.Code.UINT16, value);
            }
            
            return packer;
        } catch (IOException ex) {
            throw ex;
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }
    
    public static MessagePacker packInt(MessagePacker packer, int value) throws IOException {
        try {
            if (value < 0) {
                MessagePackerUtils.writeByteAndInt.invoke(packer, MessagePack.Code.INT32, value);
            } else {
                MessagePackerUtils.writeByteAndInt.invoke(packer, MessagePack.Code.UINT32, value);
            }
            
            return packer;
        } catch (IOException ex) {
            throw ex;
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }
    
    public static MessagePacker packLong(MessagePacker packer, long value) throws IOException {
        try {
            if (value < 0) {
                MessagePackerUtils.writeByteAndLong.invoke(packer, MessagePack.Code.INT64, value);
            } else {
                MessagePackerUtils.writeByteAndLong.invoke(packer, MessagePack.Code.UINT64, value);
            }
            
            return packer;
        } catch (IOException ex) {
            throw ex;
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }
}