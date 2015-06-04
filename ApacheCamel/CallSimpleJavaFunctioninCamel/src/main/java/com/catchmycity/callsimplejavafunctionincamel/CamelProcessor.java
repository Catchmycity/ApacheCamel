/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catchmycity.callsimplejavafunctionincamel;

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
                from("direct:invokeAddFuntionTwoParam").bean(Calculator.class, "add", true).end();
                from("direct:invokeAddFuntionTwoParamWithHeader").bean(Calculator.class, "add(${header.a},${header.b})").end();
                from("direct:invokeaddFuntionWithHarcode").bean(Calculator.class, "add(2,4)").end();
            }
        });
        context.start();

        ProducerTemplate producer = context.createProducerTemplate();

        Object res = producer.sendBody("direct:invokeaddFuntion", ExchangePattern.InOut, 1);
        System.out.println(res);

        Object arr[] = new Object[2];
        arr[0] = 1;
        arr[1] = 3;
        Object b = producer.sendBody("direct:invokeAddFuntionTwoParam", ExchangePattern.InOut, arr);
        System.out.println(b);

        Map paramMap = new HashMap();
        paramMap.put("a", 1);
        paramMap.put("b", 1);

        Object result = producer.sendBodyAndHeaders("direct:invokeAddFuntionTwoParamWithHeader", ExchangePattern.InOut, null, paramMap);
        System.out.println(result);

        Object harcoderesult = producer.sendBody("direct:invokeaddFuntionWithHarcode", ExchangePattern.InOut, null);
        System.out.println(harcoderesult);
    }

}
