/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.nifi.processors.csv;

import junit.framework.Assert;
import org.apache.nifi.util.MockFlowFile;
import org.apache.nifi.util.TestRunner;
import org.apache.nifi.util.TestRunners;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;


public class CsvToJsonTest {

    static final String HEADER = "gender,title,first,last,street,city,state,zip,email,username,password,salt,md5,sha1,sha256,registered,dob,phone,cell,SSN,large,medium,thumbnail,version,nationality";

    static final String LINE = "male,mr,milton,smith,\"9642 poplar dr\",buffalo,maine,17970,milton.smith57@example.com,bigkoala268,a1234567,a8TB6rjM,bfce5453ffd070744599042d3ce7c220,650598890601a181de83f8f4777c1a2cb8293b03,cb372f11db5c2506037f2b69eb155989c34999085cb366e5e5f7f027b1175775,1310241508,919372257,(710)-178-9841,(508)-785-4817,670-52-2567,http://api.randomuser.me/portraits/men/48.jpg,http://api.randomuser.me/portraits/med/men/48.jpg,http://api.randomuser.me/portraits/thumb/men/48.jpg,0.6,US";

    private TestRunner testRunner;

    @Before
    public void init() {
        testRunner = TestRunners.newTestRunner(CsvToJson.class);
    }

    @Test
    public void testProcessor() {
        testRunner.setProperty(CsvToJson.CSV_HEADER, HEADER);
        testRunner.enqueue(LINE);
        testRunner.run();
        testRunner.assertAllFlowFilesTransferred(CsvToJson.SUCCESS, 1);

        List<MockFlowFile> flowFiles = testRunner.getFlowFilesForRelationship(CsvToJson.SUCCESS);
        Assert.assertEquals(1, flowFiles.size());

        MockFlowFile flowFile = flowFiles.get(0);
        System.out.println(new String(flowFile.toByteArray(), StandardCharsets.UTF_8));
    }

}
