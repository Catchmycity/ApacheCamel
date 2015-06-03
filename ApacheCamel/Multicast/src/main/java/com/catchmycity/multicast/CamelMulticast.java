package com.catchmycity.multicast;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.apache.camel.CamelContext;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 *
 * @author catchmycity
 */
public class CamelMulticast {

    public static void main(String args[]) throws Exception {

        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                from("direct:A")
                        .multicast()
                        .to("direct:B", "direct:C", "direct:D")
                        .end();

                from("direct:B").setBody(simple("${body} - MessageB", String.class))
                        .log("${body}").end();
                from("direct:C").setBody(simple("${body} - MessageC", String.class))
                        .log("${body}").end();
                from("direct:D").setBody(simple("${body} - MessageD", String.class))
                        .log("${body}").end();
            }
        });
        context.start();
        ProducerTemplate producer = context.createProducerTemplate();

        Object res = producer.sendBody("direct:A", ExchangePattern.InOut, "MessageA");
        System.out.println(res);

      
//        http://www.catchmycity.com/tutorial/apache-camel-multicast-in-apache-camel_103
        
//        
//        Output:
//        DefaultCamelContext            INFO  Total 4 routes, of which 4 is started.
//DefaultCamelContext            INFO  Apache Camel 2.15.1 (CamelContext: camel-1) started in 1.123 seconds
//route2                         INFO  MessageA - MessageB
//route3                         INFO  MessageA - MessageC
//route4                         INFO  MessageA - MessageD
//MessageA - MessageD
    }
}
