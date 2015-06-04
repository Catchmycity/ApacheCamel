/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catchmycity.contentbasedrouter;

import org.apache.camel.CamelContext;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 *
 * @author catchmycity
 * http://www.catchmycity.com/tutorial/content-based-router-apache-camel_106
 */
public class CamelProcessor {

    public static void main(String args[]) throws Exception {

        
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {

            @Override
            public void configure() throws Exception {

                from("direct:a")
                        .choice()
                        .when(header("foo").isEqualTo("bar"))
                        .to("direct:b")
                        .when(header("foo").isEqualTo("cheese"))
                        .to("direct:c")
                        .otherwise()
                        .to("direct:d");

                from("direct:b").log("It is a router b").end();
                from("direct:c").log("It is a router c").end();
                from("direct:d").log("It is a router d").end();

            }
        });
        context.start();
        ProducerTemplate producer = context.createProducerTemplate();

        String bodyContent = null;

        producer.sendBodyAndHeader("direct:a", ExchangePattern.InOut, bodyContent, "foo", "bar");
        //OutPut:
        //[main] INFO route2 - It is a router b

        producer.sendBodyAndHeader("direct:a", ExchangePattern.InOut, bodyContent, "foo", "cheese");

        //Output:
        //[main] INFO route3 - It is a router c
        
//        http://www.catchmycity.com/tutorial/content-based-router-apache-camel_106
    }

}
