package coe817_project;

import java.io.Serializable;
/**
 *
 * @author Sachin
 */
public enum KeyOperation implements Serializable{                               // variable to be set of predefined constants (must be equal to one of the values)
    ADD, SUB;                                                                   // Two possible operation (Add and Subtract) that can be applied to key when attached to message to a client
}
