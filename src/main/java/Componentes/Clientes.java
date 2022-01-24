/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Componentes;

/**
 *
 * @author Daniel Bermudez
 */
public class Clientes {
    private int Nro; //Nro del cliente
    private int tell; //Tiempo entre llegadas
    private int ts; //Tiempo de servicio
    private int NroS=-1;//Servidor en el que esta el cliente(-1) representa que no esta asignado a ningun servidor
    private int tm_entrada=0;
    
    Clientes(int nro,int tell,int ts){
      this.Nro=nro;
      this.tell=tell;
      this.ts=ts;
    }
    
    public int getTm_llegada(){
       return this.tm_entrada;
    }
    
    public void setTm_Llegada(int tm){
       this.tm_entrada=tm;
    }
    
    public int getNro(){
      return this.Nro;
    }
    
    public int getTELL(){
      return this.tell;
    }
    
    public int getTS(){
      return this.ts;
    }
    public void setNroS(int NroS){
        this.NroS=NroS;
    }
    public int getNroS(){
       return this.NroS;
    }
    
    public int setTS(int ts){
       return this.ts=ts;
    }
    
    @Override
    public String toString(){
        return "Cliente Nro: "+this.Nro+" /TS:  "+this.ts+" /TLL:"+this.tell;
    }
    
}
