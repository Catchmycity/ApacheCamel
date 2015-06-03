package com.catchmycity.camelcfxwebservicecurrencycalculator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import net.webservicex.Currency;
import org.apache.camel.CamelContext;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 *
 * @author catchmycity
 * http://www.catchmycity.com/tutorial/apache-camel-a-simple-program-to-invoke-webservice-using-java2wsdl-plugin-in-apache-camel-cxf-component_102
 */
public class CurrencyCalculator {

    public static void main(String args[]) throws Exception {

        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                from("direct:start")
                        .to("cxf://http://www.webservicex.com/CurrencyConvertor.asmx?serviceClass=net.webservicex.CurrencyConvertorSoap")
                        .end();
            }
        });
        context.start();
        Thread.sleep(9000);

        ProducerTemplate producer = context.createProducerTemplate();

        Object a[] = new Object[2];
        a[0] = Currency.USD;
        a[1] = Currency.INR;

        Object res = producer.sendBody("direct:start", ExchangePattern.InOut, a);
        System.out.println(res);

    }
    //http://www.catchmycity.com/tutorial/apache-camel-a-simple-program-to-invoke-webservice-using-java2wsdl-plugin-in-apache-camel-cxf-component_102

}
