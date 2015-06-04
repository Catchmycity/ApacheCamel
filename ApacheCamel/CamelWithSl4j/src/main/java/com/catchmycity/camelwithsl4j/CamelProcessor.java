/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catchmycity.camelwithsl4j;

import org.apache.camel.CamelContext;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 *
 * @author catchmycity
 *
 */
public class CamelProcessor {

    public static void main(String args[]) throws Exception {

        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {

            @Override
            public void configure() throws Exception {

                from("direct:a").log("It is a router a. body - ${body} - header - ${header.foo}").end();
                from("direct:b").log("It is a router b. body - ${body} - header - ${header.foo}").end();
            }
        });
        context.start();
        ProducerTemplate producer = context.createProducerTemplate();

        producer.sendBodyAndHeader("direct:a", ExchangePattern.InOut, "body message a", "foo", "bar");
        //OutPut:
        //route1 - It is a router a. body - body message a - header - bar

        producer.sendBodyAndHeader("direct:b", ExchangePattern.InOut, "body message b", "foo", "cheese");

        //Output:
        //route2 - It is a router b. body - body message b - header - cheese
    }

}
