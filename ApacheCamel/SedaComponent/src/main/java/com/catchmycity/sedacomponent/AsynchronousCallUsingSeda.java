package com.catchmycity.sedacomponent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 *
 * @author catchmycity
 * http://www.catchmycity.com/tutorial/seda-component-for-asynchronous-non-blocking-endpoint-router-invoking-apache-camel_108
 */
public class AsynchronousCallUsingSeda {

    public static void main(String args[]) throws Exception {

        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                from("seda:start").process(new Processor() {

                    @Override
                    public void process(Exchange exchange) throws Exception {
                        Thread.sleep(2000);
                        System.out.println("Inside seda processor");
                    }
                })
                        .end();

                from("direct:start").process(new Processor() {

                    @Override
                    public void process(Exchange exchange) throws Exception {
                        Thread.sleep(2000);
                        System.out.println("Inside direct processor");
                    }
                })
                        .end();
            }
        });

        context.start();
        ProducerTemplate producer = context.createProducerTemplate();

        System.out.println("Before seda call");
        producer.sendBody("seda:start", ExchangePattern.InOnly, "MessageA");

        System.out.println("After seda call");

        System.out.println("Before direct call");
        producer.sendBody("direct:start", ExchangePattern.InOnly, "MessageA");
        System.out.println("After direct call");

//        Output:
//        Before seda call
//        After seda call
//        Before direct call
//        Inside seda processor
//        Inside direct processor
//        After direct call
    }
}
