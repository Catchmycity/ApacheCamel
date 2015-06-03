/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catchmycity.filetransfer.helloworld;

import java.io.File;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 *
 * @author catchmycity
 */
public class CamelHelloWorld {

    public static void main(String args[]) throws Exception {

        doFileSetup();
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {

            @Override
            public void configure() throws Exception {

                //To move all file from sourceLocation folde to destLocation folder from c drive
                from("file:C:\\sourceLocation?noop=true").process(new Processor() {

                    @Override
                    public void process(Exchange arg0) throws Exception {
                        System.out.println("hello camel!");
                    }
                }).
                        to("file:C:\\destLocation").end();
            }
        });
        context.start();
        Thread.sleep(3000);
        context.stop();
    }

    public static void doFileSetup() throws Exception {
        File f = new File("c:\\sourceLocation");
        f.delete();
        f.mkdir();
        File fs1 = new File("c:\\sourceLocation\\text1.txt");
        fs1.delete();
        fs1.createNewFile();
        File f1 = new File("c:\\destLocation");
        f1.delete();
        f1.mkdir();
    }
}
