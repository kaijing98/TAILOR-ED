/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author timothynotman
 */
public class EventExistsException extends Exception {

    /**
     * Creates a new instance of <code>EventExistsException</code> without
     * detail message.
     */
    public EventExistsException() {
    }

    /**
     * Constructs an instance of <code>EventExistsException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public EventExistsException(String msg) {
        super(msg);
    }
}
