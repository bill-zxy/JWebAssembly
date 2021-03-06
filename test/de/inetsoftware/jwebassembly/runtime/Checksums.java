/*
 * Copyright 2019 Volker Berlin (i-net software)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.inetsoftware.jwebassembly.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.zip.CRC32;

import org.junit.ClassRule;
import org.junit.runners.Parameterized.Parameters;

import de.inetsoftware.jwebassembly.ScriptEngine;
import de.inetsoftware.jwebassembly.WasmRule;
import de.inetsoftware.jwebassembly.api.annotation.Export;

/**
 * @author Volker Berlin
 */
public class Checksums extends AbstractBaseTest {
    
    @ClassRule
    public static WasmRule rule = new WasmRule( TestClass.class ); 

    public Checksums( ScriptEngine script, String method, Object[] params ) {
        super( rule, script, method, params );
    }

    @Parameters(name="{0}-{1}")
    public static Collection<Object[]> data() {
        ArrayList<Object[]> list = new ArrayList<>();
        for( ScriptEngine script : ScriptEngine.testEngines() ) {
            addParam( list, script, "crcUpdate" );
            addParam( list, script, "crcUpdateBytes" );
        }
        rule.setTestParameters( list );
        return list;
    }

    static class TestClass {

        @Export
        static int crcUpdate() {
            CRC32 crc = new CRC32();
            crc.update( 42 );
            return (int)crc.getValue();
        }

        @Export
        static int crcUpdateBytes() {
            CRC32 crc = new CRC32();
            byte[] a = new byte[1000];
            for( int i = 0; i < a.length; i++ ) {
                a[i] = (byte)i;
            }
            crc.update( a );
            return (int)crc.getValue();
        }
    }
}
