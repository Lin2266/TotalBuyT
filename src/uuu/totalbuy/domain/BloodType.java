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
public enum BloodType {
    O, A, B, AB;

    @Override
    public String toString() {
        return this.name() +'型'; //To change body of generated methods, choose Tools | Templates.
    }
}
