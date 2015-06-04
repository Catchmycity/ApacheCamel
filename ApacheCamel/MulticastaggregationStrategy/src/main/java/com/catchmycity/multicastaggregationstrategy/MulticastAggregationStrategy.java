package com.catchmycity.multicastaggregationstrategy;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.HashMap;
import java.util.Map;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.processor.aggregate.AggregationStrategy;

/**
 *
 * @author catchmycity
 */
public class MulticastAggregationStrategy {

    public static void main(String args[]) throws Exception {

        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                from("direct:A")
                        .multicast().aggregationStrategy(new AggregationStrategy() {

                            @Override
                            public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
                                Map map = null;
                                //oldExchange will be null at the first route
                                if (oldExchange == null) {
                                    map = new HashMap();
                                } else {
                                    map = (Map) oldExchange.getIn().getBody();
                                }

                                map.put(newExchange.getIn().getHeader("id"), newExchange.getIn().getBody());
                                newExchange.getIn().setBody(map);

                                return newExchange;
                            }
                        })
                        .to("direct:B", "direct:C", "direct:D")
                        .end();

                from("direct:B")
                        .setHeader("id", simple("routeB"))
                        .setBody(simple("This is routeB body response", String.class))
                        .log("${body}").end();
                from("direct:C")
                        .setHeader("id", simple("routeC"))
                        .setBody(simple("This is routeC body response", String.class))
                        .log("${body}").end();
                from("direct:D")
                        .setHeader("id", simple("routeD"))
                        .setBody(simple("This is routeD body response", String.class))
                        .log("${body}").end();
            }
        });
        context.start();
        ProducerTemplate producer = context.createProducerTemplate();

        //To get all three endpoint message in the single map output. The key is based on the header id
        Map map = (Map) producer.sendBody("direct:A", ExchangePattern.InOut, "MessageA");

        System.out.println("Aggregatge Response :" + map);
        
        
//        Output:
//        
//        route2                         INFO  This is routeB body response
//        route3                         INFO  This is routeC body response
//        route4                         INFO  This is routeD body response
//        Aggregatge Response :{routeD=This is routeD body response, routeC=This is routeC body response, routeB=This is routeB body response}
    }
}
