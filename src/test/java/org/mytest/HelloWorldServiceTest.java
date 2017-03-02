package org.mytest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Application;
import java.net.Socket;

/**
 * Created by marijn on 16-2-17.
 */
public class HelloWorldServiceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig((HelloWorldService.class));
    }

    @Test
    public void helloPathParamTest() {
        String response = target("hello/message").request().get(String.class);
        Assert.assertTrue("Jersey says: message".equals(response));
    }

}
