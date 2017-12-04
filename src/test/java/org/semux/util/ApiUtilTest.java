/**
 * Copyright (c) 2017 The Semux Developers
 *
 * Distributed under the MIT software license, see the accompanying file
 * LICENSE or https://opensource.org/licenses/mit-license.php
 */
package org.semux.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semux.api.SemuxAPIMock;
import org.semux.config.Config;

public class ApiUtilTest {

    private static final String API_IP = "127.0.0.1";
    private static final int API_PORT = 15171;

    private static SemuxAPIMock api;

    @BeforeClass
    public static void setup() {
        api = new SemuxAPIMock();
        api.start(API_IP, API_PORT);
    }

    @Test
    public void testRequest() throws IOException {
        String cmd = "get_block";

        Config config = api.getKernel().getConfig();
        ApiUtil api = new ApiUtil(new InetSocketAddress(API_IP, API_PORT), config.apiUsername(), config.apiPassword());
        JSONObject obj = api.request(cmd, "number", 0);

        assertTrue(obj.getBoolean("success"));
        assertNotNull(obj.getJSONObject("result"));
    }

    @AfterClass
    public static void teardown() throws IOException {
        api.stop();
    }
}