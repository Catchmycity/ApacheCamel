/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catchmycity.callsimplejavafunctionincamel;

/**
 *
 * @author catchmycity
 */
public class Calculator {

    public int add10(int a) {
        System.out.println("Method Signature : int add(int a)");
        return a + 10;
    }

    public int add(int a, Integer b) {
        System.out.println("Method Signature : int a, Integer b");
        return a + b;
    }
}