package com.example.CoreUser.Util;

import static org.junit.jupiter.api.Assertions.*;

class AppUtilTest {

    @org.junit.jupiter.api.Test
    void getID() {
        String data = "test";
        String id = AppUtil.getID(data);
        assertNotNull(id);
        assertTrue(id.contains(data));
        assertTrue(id.contains("."));
    }

}