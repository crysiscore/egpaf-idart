/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.celllife.idart.gui.login;

import org.celllife.idart.database.hibernate.User;

/**
 *
 * @author ColacoVM
 */
public class Sessao{
   private static Sessao instance = null;
   private User usuario;
   private Sessao(){
   }
   public void setUsuario(User usuario){
      this.usuario = usuario;
   }
   public User getUsuario(){
       return usuario;
   }
   public static Sessao getInstance(){
         if(instance == null){
               instance = new Sessao();
         }
        return instance;
   }
}