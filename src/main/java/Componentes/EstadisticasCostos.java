/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Componentes;

import java.util.ArrayList;

/**
 *
 * @author USUARIO
 */
public class EstadisticasCostos {
    Integer Tm_max=0;
    Integer Max_C=0;
    Double L=0.0;
    Integer Cant_L=0;
    Double Lq=0.0;
    Integer Cant_Lq=0;
    Double W=0.0;
    Integer Cant_W=0;
    Double Wq=0.0;
    Integer Cant_Wq=0;
    Integer numSer=0;
    Integer cantCola=0;
    Integer cantSinEspera=0;
    Integer cantNoAtendidos=0;
    Integer costo_TS=0;
    Integer costo_TW=0;
    Integer costo_Ocupado=0;
    Integer costo_Desocupado=0;
    Integer costo_TiempoExtra=0;
    Integer costo_OpNormal=0;
    Integer costo_SisTiempoExtra=0;
    ArrayList<Double> porcentajeUtilizacion = new ArrayList<Double>();
    private ArrayList<Integer> lista_STOcupado=new ArrayList<Integer>();
    private ArrayList<Integer> lista_STDesocupado=new ArrayList<Integer>() ;
    Double ProEspera=0.0;
    String Unidad="";
    Double resul_TS=0.0;
    Double resul_TW=0.0;
    Double resul_Ocupado=0.0;
    Double resul_Desocupado=0.0;
    Double resul_TiempoExtra=0.0;
    Double resul_OpNormal=0.0;
    Double resul_SisTiempoExtra=0.0;
    ArrayList<Double> resulCostoPUOcupado = new ArrayList<Double>();
    ArrayList<Double> resulCostoPUDesocupado = new ArrayList<Double>();
    
    
    public EstadisticasCostos(int Max_Tm,int Max_C,int numServers,int costoTS,int costoTW,int costoOcupado,int costoDesocupado,int costoTiempoE,int costoOPnormal,int costoSisTiempoExtra,String uni){
        this.Max_C=Max_C;
        this.Tm_max=Max_Tm;
        this.numSer=numServers;
        for(int i=0;i<numServers;i++){
            this.porcentajeUtilizacion.add(0.0);
            this.lista_STDesocupado.add(0);
            this.lista_STOcupado.add(0);
        }
        this.costo_TS=costoTS;
        this.costo_TW=costoTW;
        this.costo_Ocupado=costoOcupado;
        this.costo_Desocupado=costoDesocupado;
        this.costo_TiempoExtra=costoTiempoE;
        this.costo_OpNormal=costoOPnormal;
        this.costo_SisTiempoExtra=costoSisTiempoExtra;
        this.Unidad=uni;
    }
    
    public void setNoAtendidos(int cant){
       this.cantNoAtendidos=cant;
    }
    
    public void setCantCola(int cant){
       this.cantCola=cant;
    }
    
    public void setCantSinEspera(int cant){
       this.cantSinEspera=cant;
    }
    
    public void CalcularEspera(int cantCliente,int cantEspera){
        Double a=cantCliente*1.0,b=cantEspera*1.0;
        this.ProEspera=b/a;
    }
    
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
    
    public void CalcularPorUtilizacion(int tm){
        for(int i=0;i<this.numSer;i++){
          double a=this.lista_STOcupado.get(i)*1.0;
          this.porcentajeUtilizacion.set(i,(Double) (a/tm)*100);
        }
    }
    
    public int Acumular_L(int cantcTM){
        System.out.println("Sumar: cantcTm"+cantcTM+" + L:"+this.Cant_L);
        return this.Cant_L=this.Cant_L+cantcTM;
    }
    
    public Double Calcular_L(int tm){
       return this.L=(this.Cant_L*1.0)/(tm*1.0);
    }
    
    public int Acumular_Lq(int cantcTM){
          System.out.println("Sumar: cantcTM:"+cantcTM+" + Lq:"+this.Cant_Lq);
        return this.Cant_Lq=this.Cant_Lq+cantcTM;
    }
    
    public Double Calcular_Lq(int tm){
        return this.Lq=(this.Cant_Lq*1.0)/(tm*1.0);
    }
    
    public int Acumular_W(int tm){
        return this.Cant_W=this.Cant_W+tm;
    }
    
    public Double Calcular_W(int cantC){
        return this.W=(this.Cant_W*1.0)/(cantC*1.0);
    }
    
    public int Acumular_Wq(int tm){
        return this.Cant_Wq=this.Cant_Wq+tm;
    }
    
    public Double Calcular_Wq(int cantC){
        return this.Wq=this.Cant_Wq*1.0/cantC*1.0;
    }
    
    public void CalcularCostos(){
       this.resul_TS=this.W*this.costo_TS;
       this.resul_TW=this.Wq*this.costo_TW;
       for(int i=0;i<this.numSer;i++){
           this.resulCostoPUOcupado.add((this.porcentajeUtilizacion.get(i)/100.0)*this.costo_Ocupado);
           this.resulCostoPUDesocupado.add(((100.0-this.porcentajeUtilizacion.get(i))/100.0)*this.costo_Desocupado);
       }
    }
    
    public String getUtilizacion(){
       String a="";
       Double b=0.0;
       for(int i=0;i<this.numSer;i++){
         b=b+this.porcentajeUtilizacion.get(i);  
         a=a+"\n Porcentaje de utilizacion del servidor "+i+" "+this.porcentajeUtilizacion.get(i)+"% \n";
       }
       a=a+"\n \n Porcentaje total: "+b+" %\n";
       return a;
    }
    
    public String getCostoUtilizacion(){
       String a="\n";
       for(int i=0;i<this.numSer;i++){
         a=a+"  Costo del servidor "+i+" estando ocupado: "+this.resulCostoPUOcupado.get(i)+"\n";
         a=a+"  Costo del servidor "+i+" estando desocupado: "+this.resulCostoPUDesocupado.get(i)+"\n";
         a=a+"\n";
       }
       return a;
    }
    
    
    public String verDatosIniciales(){
       int numS=this.numSer+1; 
       return "---------DATOS INICIALES DE LA SIMULACION---------\n \n"
              +"Cantidad de tiempo a simular: "+this.Tm_max+"\n"
              +"Cantidad maxima de clientes en sistema: "+this.Max_C+"\n"
              +"Cantidad servidores: "+numS+"\n"
              +"Costo de tiempo de servicio: "+this.costo_TS+"\n"
              +"Costo de tiempo de espera: "+this.costo_TW+"\n"
              +"Costo de servidor ocupado: "+this.costo_Ocupado+"\n"
              +"Costo de servidor desocupado: "+this.costo_Desocupado+"\n"
              +"Costo de servidor en tiempo extra: "+this.costo_TiempoExtra+"\n"
              +"Costo en operacion normal: "+this.costo_OpNormal+"\n"
              +"Costo en sistema en tiempo extra: "+this.costo_SisTiempoExtra+"\n"
              +"------------------------------------------------------\n" 
              +"\n"+"\n" 
       
       ;        
     
    }
    
    public String verResultados(){
      
       return "\nCantidad de clientes que no esperan: "+this.cantSinEspera+"\n"
              +"\nCantidad de clientes que se van sin ser antendidos: "+this.cantNoAtendidos+"\n"
              +"\nProbabilidad de esperar: "+this.ProEspera+"\n"
              +"\nCantidad promedio de clientes en sistema: "+this.L+"\n"
              +"\nCantidad promedio de clientes en cola: "+this.Lq+"\n"
              +"\nTiempo promedio de un cliente en sistema: "+this.W+"\n"
              +"\nTiempo promedio de un cliente en cola: "+this.Wq+"\n"
              +"\n"+this.getUtilizacion()
              +"\n\n------------COSTOS-----------------\n"
              +"\nSISTEMA: \n"
              +"  Costo operacion normal del sistema: \n"
              +"  Costo operacion extra del sistema: \n"
              +"SERVIDORES: \n"
              +this.getCostoUtilizacion()
              +"CLIENTES: \n" 
              +"  Costo de tiempo en servicio del cliente: "+this.resul_TS+"\n"
              +"  Costo de tiempo en espera del cliente: "+this.resul_TW; 
    
    }
            
}
