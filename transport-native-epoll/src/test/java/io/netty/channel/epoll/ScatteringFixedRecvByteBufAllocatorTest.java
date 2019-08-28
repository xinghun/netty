/*
 * Copyright 2019 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.channel.epoll;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ScatteringFixedRecvByteBufAllocatorTest {

    @Test
    public void testAllocate() {
        int size = 512;
        int numBuffers = 4;
        ScatteringFixedRecvByteBufAllocator allocator = new ScatteringFixedRecvByteBufAllocator(size, numBuffers);
        ScatteringHandle handle = (ScatteringHandle) allocator.newHandle();
        List<ByteBuf> buffers = new ArrayList<ByteBuf>();
        handle.allocateScattering(UnpooledByteBufAllocator.DEFAULT, buffers);
        assertEquals(numBuffers, buffers.size());

        for (ByteBuf buffer: buffers) {
            assertEquals(size, buffer.writableBytes());
            assertTrue(buffer.release());
        }
    }
}
