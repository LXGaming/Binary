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

import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;

import java.io.IOException;

public class MessageBufferPackerImpl extends MessageBufferPacker {
    
    public MessageBufferPackerImpl(MessagePack.PackerConfig config) {
        super(config);
    }
    
    @Override
    public MessagePacker packShort(short v) throws IOException {
        return MessagePackerUtils.packShort(this, v);
    }
    
    @Override
    public MessagePacker packInt(int r) throws IOException {
        return MessagePackerUtils.packInt(this, r);
    }
    
    @Override
    public MessagePacker packLong(long v) throws IOException {
        return MessagePackerUtils.packLong(this, v);
    }
}