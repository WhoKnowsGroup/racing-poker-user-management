/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pokerace.user;

import javax.ejb.embeddable.EJBContainer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.pokerace.ejb.iface.UserManager;
import com.pokerace.ejb.model.User;

import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class User_registerTest {
    
    public User_registerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of register method, of class User_register.
     */
    @Test
    //aroth:  this testcase always fails, even if the assertion is removed; it's not clear if this is actually a valid testcase
    public void testRegister() throws Exception {
        System.out.println("register");
        String email = "";
        String password = "";
        String firstname = "";
        String lastname = "";
        String nickname = "";
        String gender = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        UserManager instance = (UserManager)container.getContext().lookup("java:global/classes/UserEJB!com.pokerace.ejb.iface.UserManager");
        boolean expResult = false;
        
        User user = new User(email, password, firstname, lastname, nickname, gender);
        boolean result = instance.register(user);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
