/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Componentes;

import java.util.ArrayList;

/**
 *
 * @author Daniel Bermudez
 */
public class Evento {
    private int AT; //Tiempo de llegada del cliente al sistema
    private ArrayList<Integer> lista_DT; //Lista de los tiempo de salida de cada servidor 
    private ArrayList<Integer> lista_AT; //Lista de los tiempo de entrada de cada cliente 
    private ArrayList<Integer> lista_STOcupado;
    private ArrayList<Integer> lista_STDesocupado;
    
    public Evento(){
      this.AT=0;
      this.lista_DT=new ArrayList<Integer>();
      this.lista_AT=new ArrayList<Integer>();
      this.lista_STOcupado=new ArrayList<Integer>();
      this.lista_STDesocupado=new ArrayList<Integer>();
    };
    
    
    public void addTiempoOcupado(int tm){
       this.lista_STOcupado.add(tm);
    }
    
    public void addTiempoDesocupado(int tm){
       this.lista_STDesocupado.add(tm);
    }
    
    
    public int getTiempoOcupado(int index){
       return this.lista_STOcupado.get(index);
    }
    
    public int setTiempoOcupado(int index,int tm){
        System.out.println("Ocupado Servidor "+index+": tm("+tm+") + acum("+this.lista_STOcupado.get(index)+")");
       return this.lista_STOcupado.set(index,tm+this.lista_STOcupado.get(index));
    }
    
    public int getTiempoDesocupado(int index){
       return this.lista_STDesocupado.get(index);
    }
    
    public int setTiempoDesocupado(int index,int tm){
       System.out.println("Desocupado Servidor "+index+": tm("+tm+") ant("+this.lista_STDesocupado.get(index)+")"); 
       return this.lista_STDesocupado.set(index,tm);
    }
    
    public int getAT(){
      return this.AT;
    }
    
    public void setAT(int at){
      this.AT=at;
    }
    
    public ArrayList<Integer> getLista(){
      return this.lista_DT;
    }
    
    public void setDT(int dt){
      this.lista_DT.add(dt);
    }
    
    public int nextSalida(){
       int nextD = 999;
       for(int i=0;i<this.lista_DT.size();i++){
           if(this.lista_DT.get(i)<nextD)
              nextD=this.lista_DT.get(i);
       }
       return nextD;
    }

    public int nextLlegada(){
       int nextA = 999;
       for(int i=0;i<this.lista_AT.size();i++){
           if(this.lista_AT.get(i) < nextA)
              nextA = this.lista_AT.get(i);
       }
       return nextA;
    }
    
    public int nextExit(){
       int nextD = 999;
       int indexS = 0;
       for(int i=0;i<this.lista_DT.size();i++){
           if(this.lista_DT.get(i)<nextD){
              nextD=this.lista_DT.get(i);
              indexS=i;
           }   
       }
       return indexS;
    }
    
    public int getDT(int i){
       return this.lista_DT.get(i);
    }
    public void updateDT(int i,int dt){
      this.lista_DT.set(i, dt);
    }
    
    public String printATDT(){
       String ret="AT:"+this.AT+" ";
       for(int i=0;i<this.lista_DT.size();i++){
         ret=ret+"DT"+i+":"+this.lista_DT.get(i)+" ";
       };
       return ret;
    }
}
