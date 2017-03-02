package org.mytest;

import javax.ws.rs.core.Application;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashSet;
import java.util.Set;

import java.io.File;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.shell.ShellSettings;

/**
 * Created by marijn on 16-2-17.
 */
public class TestApplication extends Application {

    File db_location = new File("/data/tagaid/neo4jdb");
    GraphDatabaseService db;

    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(HelloWorldService.class);
        return s;
    }


    @PostConstruct
    public void initialize() {
        try {
            db = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(db_location)
                    .setConfig(ShellSettings.remote_shell_enabled, "true")
                    .setConfig(ShellSettings.remote_shell_port, "5555")
                    .newGraphDatabase();
            System.out.println("Neo4j startup succeeded"); // logged in tomcat catalina.out
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Neo4j startup failed"); // logged in tomcat catalina.out
        }
    }

    @PreDestroy
    public void cleanUp() {
        try {
            db.shutdown();
            System.out.println("Neo4j shutdown succeeded"); // logged in tomcat catalina.out
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Neo4j shutdown failed!"); // logged in tomcat catalina.out
        }
        db = null; // should not be necessary
    }
}
