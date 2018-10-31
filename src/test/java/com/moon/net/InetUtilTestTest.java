package com.moon.net;

import com.moon.lang.JoinerUtil;
import org.junit.jupiter.api.Test;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author benshaoye
 * @date 2018/9/17
 */
class InetUtilTestTest {

    @Test
    void testGetLocalIP() throws UnknownHostException {
        System.out.println(InetUtil.getLocalIP4());

        InetAddress address = InetAddress.getLocalHost();

        System.out.println(address.toString());
        System.out.println(address.hashCode());
        System.out.println(JoinerUtil.join(address.getAddress()));
        System.out.println(address.getCanonicalHostName());
        System.out.println(address.getHostAddress());

        Inet6Address.getLocalHost();
    }
}