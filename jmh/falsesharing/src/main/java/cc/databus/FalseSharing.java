/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package cc.databus;

import org.openjdk.jmh.annotations.*;

public class FalseSharing {

    @State(Scope.Group)
    public static class StateBaseLine {
        int readOnly;
        int writeOnly;
    }

    @Benchmark
    @Warmup(iterations = 2, time = 1)
    @Group("onlyReader")
    @Fork(2)
    public int onlyReader(StateBaseLine s) {
        return s.readOnly;
    }

    @Benchmark
    @Warmup(iterations = 2, time = 1)
    @Group("onlyWriter")
    @Fork(2)
    public int onlyWriter(StateBaseLine s) {
        return ++s.writeOnly;
    }

    @Benchmark
    @Warmup(iterations = 2, time = 1)
    @Group("baseline")
    @Fork(2)
    public int reader(StateBaseLine s) {
        // 只读操作
        return s.readOnly;
    }

    @Benchmark
    @Group("baseline")
    @Warmup(iterations = 2, time = 1)
    @Fork(2)
    public int writer(StateBaseLine s) {
        // 只写
        return ++s.writeOnly;
    }


    @State(Scope.Group)
    public static class PaddedStateBaseLine {
        int a1, a2, a3, a4, a5, a6, a7, a8;
        @sun.misc.Contended("group1")
        int readOnly;
        int b1, b2, b3, b4, b5, b6, b7, b8;
        @sun.misc.Contended("group2")
        int writeOnly;
    }

    @Benchmark
    @Warmup(iterations = 2, time = 1)
    @Group("padded")
    @Fork(2)
    public int reader(PaddedStateBaseLine s) {
        // 只读操作
        return s.readOnly;
    }

    @Benchmark
    @Group("padded")
    @Warmup(iterations = 2, time = 1)
    @Fork(2)
    public int writer(PaddedStateBaseLine s) {
        // 只写
        return ++s.writeOnly;
    }

    @State(Scope.Group)
    public static class ContendedStateBaseLine {
        @sun.misc.Contended("group1")
        int readOnly;
        @sun.misc.Contended("group2")
        int writeOnly;
    }


    @Benchmark
    @Warmup(iterations = 2, time = 1)
    @Group("contended")
    @Fork(2)
    public int reader(ContendedStateBaseLine s) {
        // 只读操作
        return s.readOnly;
    }

    @Benchmark
    @Group("contended")
    @Warmup(iterations = 2, time = 1)
    @Fork(2)
    public int writer(ContendedStateBaseLine s) {
        // 只写
        return ++s.writeOnly;
    }
}
