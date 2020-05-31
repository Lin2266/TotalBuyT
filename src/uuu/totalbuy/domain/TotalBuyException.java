/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.totalbuy.domain;

/**
 *
 * @author Administrator
 */
public class TotalBuyException extends Exception {

    /**
     * Creates a new instance of <code>TotalBuyException</code> without detail
     * message.
     */
    public TotalBuyException() {
    }

    /**
     * Constructs an instance of <code>TotalBuyException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public TotalBuyException(String msg) {
        super(msg);
    }

    public TotalBuyException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
}
