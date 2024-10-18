/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.network;

/**
 *
 * @author ousmane3ndiaye
 */
public class Commande {
    
    public String action;
    public String [] options;
    
    // action -op object -op ojbject .....  

    public Commande(String commandLine) {
        String[] tab = commandLine.split(" ");
        action=tab[0];
        for (int i = 1; i < tab.length; i=i+2) {
            options[(i-1)/2]=tab[0]+" "+tab[i+1];
        }
         
    }  

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }
    
    
    
    
    
}
