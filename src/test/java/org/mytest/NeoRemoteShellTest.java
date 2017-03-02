package org.mytest;

import org.junit.Assert;
import org.junit.Test;

import java.net.Socket;

import java.io.File;

import java.util.concurrent.TimeUnit;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.shell.ShellSettings;

/**
 * Created by marijn on 2-3-17.
 */
public class NeoRemoteShellTest {


    File db_location = new File("/data/tagaid/neo4jdb");
    GraphDatabaseService db;

    public void initialize() {
        try {
            db = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(db_location)
                    .setConfig(ShellSettings.remote_shell_enabled, "true")
                    .setConfig(ShellSettings.remote_shell_port, "5555")
                    .newGraphDatabase();
            System.out.println("Neo4j startup succeeded");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Neo4j startup failed");
        }
    }

    public void cleanUp() {
        try {
            db.shutdown();
            System.out.println("Neo4j shutdown succeeded");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Neo4j shutdown failed!");
        }
        db = null; // should not be necessary
    }

    // Test used for checking port availability for Neo4j remote shell
    public boolean portAvailable(String host, Integer port) {
        Socket s = null;
        try {
            s = new Socket(host, port);
            return false;
        }
        catch (Exception e) {
            return true;
        }
        finally {
            if(s != null) {
                try {s.close();}
                catch(Exception e){}
            }
        }
    }

    public void waitForNeo(Long waitTime) {
        try {
            TimeUnit.SECONDS.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPort() {
        Assert.assertTrue(portAvailable("localhost", 5555));
        initialize();
        waitForNeo(5L);
        Assert.assertFalse(portAvailable("localhost", 5555));
        cleanUp();
        waitForNeo(5L);
        Assert.assertTrue(portAvailable("localhost", 5555));
    }
}
