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

import org.msgpack.core.MessagePacker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

public class MessagePackerUtils {
    
    protected static MethodHandle writeByteAndShort;
    protected static MethodHandle writeByteAndInt;
    protected static MethodHandle writeByteAndLong;
    
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
}