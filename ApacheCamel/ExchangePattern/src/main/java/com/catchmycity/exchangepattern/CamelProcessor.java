/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catchmycity.exchangepattern;

import java.util.HashMap;
import java.util.Map;
import org.apache.camel.CamelContext;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 *
 * @author catchmycity
 */
public class CamelProcessor {

    public static void main(String args[]) throws Exception {

        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {

            @Override
            public void configure() throws Exception {

                from("direct:invokeaddFuntion").bean(Calculator.class, "add10").end();

            }
        });
        context.start();
        ProducerTemplate producer = context.createProducerTemplate();
        Object res = producer.sendBody("direct:invokeaddFuntion", ExchangePattern.InOut, 1);
        System.out.println(res);

//        Output:
//        11
        res = producer.sendBody("direct:invokeaddFuntion", ExchangePattern.InOnly, 1);
        System.out.println(res);
//        Output:(InOnly) only output
//        null

        //http://www.catchmycity.com/tutorial/apache-camel-difference-between-exchangepattern-inout-and-exchangepattern-inonly-in-apache-camel_101
    }

}
