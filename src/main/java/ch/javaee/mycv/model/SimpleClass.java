package ch.javaee.mycv.model;

import javax.ejb.Stateless;

/**
 * Created by marco on 14/03/16.
 */

@Stateless
public class SimpleClass {

    public String getHelloWorld(){
        return "HelloWorld";
    }
}
