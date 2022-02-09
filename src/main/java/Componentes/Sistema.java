/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Componentes;
import Vistas.Proceso;

import java.util.ArrayList;

/**
 *
 * @author USUARIO
 */
public class Sistema {
    int tm_max;
    int max_clientes;
    int num_evento=0;
    TablaDistribuciones Archivo_TELL;
    TablaDistribuciones Archivo_TS;
    Aleatorio numA=new Aleatorio();
    Evento evento=new Evento();
    Variables variables=new Variables();
    ArrayList<Clientes> Lista_espera=new ArrayList<Clientes>();
    ArrayList<Clientes> Lista_servicio=new ArrayList<Clientes>();
    EstadisticasCostos EC;
    
    public Sistema(int tm,int cant_clientes,int cant_servidores,TablaDistribuciones ATELL,TablaDistribuciones ATS,EstadisticasCostos E){
       this.tm_max=tm;
       this.max_clientes=cant_clientes;
       this.Archivo_TELL=ATELL;
       this.Archivo_TS=ATS;
       for(int i=0;i<=cant_servidores-1;i++){
        this.variables.addServidor(true);
        //this.evento.setDT(999);
        this.evento.setDT(tm+1);
       }
       this.EC=E;
       
    }
    
    private Clientes searchClientInServer(int indexS){
      System.out.println("---Encontrando---/Clientes en servicio: "+Lista_servicio.size());
      for(int i=0;i<Lista_servicio.size();i++){
          if(indexS==Lista_servicio.get(i).getNroS()){
             Clientes salida=Lista_servicio.get(i);
             Lista_servicio.remove(i);
             System.out.println("---Cliente encontrado---/Clientes en servicio: "+Lista_servicio.size());
             return salida;
          }
      }
      System.out.println("---Cliente NO encontrado---/Clientes en servicio: "+Lista_servicio.size());
      return null;
    };
    
    public void ComenzarSimulacion(){
       int indexCliente=0,cantCTotal=0,cantCEnServicio=0;
       Clientes actualCliente = null,nextCliente=null,salidaCliente=null;
       int cantCola=0,cantSinEspera=0;
       int tm=0,prev_tm=0,t_extra=this.tm_max,c_max=this.max_clientes;
       Proceso proceso=new Proceso();
       proceso.setVisible(true);
       do{
           
        if(this.max_clientes==0)
            c_max=this.variables.getCantClientes()+1;
        
        if(indexCliente==0)
         actualCliente=new Clientes(indexCliente,Archivo_TELL.getTiempo(this.numA.generarNumero()),Archivo_TS.getTiempo(this.numA.generarNumero()));
        //PROCESO DE ENTRADA
        if((this.evento.getAT()<this.evento.nextSalida(t_extra) 
           //&& indexCliente<this.max_clientes     
           &&this.evento.getAT()<this.tm_max
           && this.variables.getCantClientes()<c_max
           )
           //||
           //(this.evento.getAT()<this.evento.nextSalida() 
           //&& indexCliente<this.max_clientes
           //&& this.variables.getCantClientes()<this.max_clientes     
           //)
           ){
           /* 
           if(actualCliente.getNroS()==-2){
             actualCliente=new Clientes(indexCliente,Archivo_TELL.getTiempo(this.numA.generarNumero()),-1);
           }*/
           cantCTotal=cantCTotal+1;
           prev_tm=tm;
           tm=this.evento.getAT();
           this.variables.setTM(tm);
           actualCliente.setTm_Llegada(tm);
           if(this.variables.getCantClientes()!=0){
               EC.Acumular_L((tm-prev_tm)*this.variables.getCantClientes());
           };
           int indexServer=this.variables.getAvaibleServer();
           //Servidores ocupados,el cliente ingresa a la cola de espera
           if(indexServer==-1){
            cantCola=cantCola+1;   
            System.out.println("\nServidores ocupados.Cliente Nro "+actualCliente.getNro()+" Entro en espera");
            this.Lista_espera.add(actualCliente);
            if(this.variables.getWL()!=0){
               EC.Acumular_Lq((tm-prev_tm)*this.variables.getWL());
            };
            this.variables.upCantClientes();
            this.variables.upWL();
            //if(indexCliente<this.max_clientes-1){ 
              nextCliente=new Clientes(indexCliente+1,Archivo_TELL.getTiempo(this.numA.generarNumero()),-1);  
              System.out.println("Se actualiza AT con la suma de TM("+tm+") y TELL("+nextCliente.getTELL()+")");  
              this.evento.setAT(nextCliente.getTELL()+tm);
             //}
           }
           else{
            cantCEnServicio=cantCEnServicio+1;   
            cantSinEspera=cantSinEspera+1;   
            this.variables.upCantClientes();
            this.variables.setStatusServidor(indexServer,false);
            actualCliente.setNroS(indexServer);
            EC.setTiempoDesocupado(indexServer, tm);
           
            this.Lista_servicio.add(actualCliente);
        

             if(num_evento==0){
              //System.out.println("Se actualiza AT con TELL("+this.clientes.getTabla().get(indexCliente).getTELL());  
              nextCliente=new Clientes(indexCliente+1,Archivo_TELL.getTiempo(this.numA.generarNumero()),-1);
              this.evento.setAT(nextCliente.getTELL()+tm);
              this.evento.updateDT(indexServer,actualCliente.getTS()+tm);
            } else {
              //Si no es el primer evento se suma el TELL con TM  
              //Suma de TM con TELL 
             //if(indexCliente<this.max_clientes-1){ 
              //System.out.println("Se actualiza AT con la suma de TM("+this.timeModeling+") y TELL("+this.clientes.getTabla().get(indexCliente).getTELL()+")");  
              actualCliente.setTS(Archivo_TS.getTiempo(this.numA.generarNumero()));
              this.evento.updateDT(indexServer,actualCliente.getTS()+tm);
              nextCliente=new Clientes(indexCliente+1,Archivo_TELL.getTiempo(this.numA.generarNumero()),-1);
              this.evento.setAT(nextCliente.getTELL()+tm);
             //}
             //else System.out.println("Ultimo cliente ingresado");
            }
           }
          
         
          System.out.println("\n");
          System.out.println("-----EVENTO---------");
          System.out.println("NroEvento: "+this.num_evento+"/ LLEGADA / NroCliente:"+actualCliente.getNro()+"/");
          this.variables.printVariables();
          System.out.println("------LISTA DE EVENTOS-------");
          System.out.println(this.evento.printATDT());
          System.out.println("-------------FIN---------------- ");
          System.out.println("\n");
          System.out.println("\n");
         
          System.out.println("-------ESTADO DE L-------");
          System.out.println("tm previo: "+prev_tm+" tm actual: "+tm+" cant_L: "+EC.Cant_L);
          System.out.println("--------------------------");
          System.out.println("-------ESTADO DE Lq-------");
          System.out.println("tm previo: "+prev_tm+" tm actual: "+tm+" cant_Lq: "+EC.Cant_Lq);
          System.out.println("--------------------------");
          System.out.println("-------ESTADO DE W-------");
          System.out.println("cant_W: "+EC.Cant_W);
          System.out.println("--------------------------");
          System.out.println("-------ESTADO DE Wq-------");
          System.out.println("cant_Wq: "+EC.Cant_Wq);
          System.out.println("--------------------------");
          
          
          proceso.RegistrarEvento("\n");
          proceso.RegistrarEvento("-----EVENTO------");
          proceso.RegistrarEvento("NroEvento: "+Integer.toString(this.num_evento)+"/ LLEGADA / NroCliente:"+Integer.toString(actualCliente.getNro())+"/");
          proceso.RegistrarEvento("-----VARIABLES DEL SISTEMA-----");
          proceso.RegistrarEvento(this.variables.stringVariables());
          proceso.RegistrarEvento("------LISTA DE EVENTOS-------");
          proceso.RegistrarEvento(this.evento.printATDT());
          proceso.RegistrarEvento("-------------FIN---------------- ");
          proceso.RegistrarEvento("\n");
          
          indexCliente=indexCliente+1;
          actualCliente=nextCliente;
        }
        //PROCESO DE SALIDA
        else{
          int indexS=this.evento.nextExit(t_extra);
       
         

          salidaCliente=searchClientInServer(indexS);
          
          salidaCliente.setNroS(-2);
          
          prev_tm=tm;
          tm=this.evento.nextSalida(t_extra);
          this.variables.setTM(tm);
          if(this.variables.getCantClientes()!=0){
               EC.Acumular_L((tm-prev_tm)*this.variables.getCantClientes());
           };
          
          EC.Acumular_W(tm-salidaCliente.getTm_llegada());
          
          this.variables.susCantClientes();
          
          if(!this.Lista_espera.isEmpty() && tm<=this.tm_max){
            Clientes clienteCola=this.Lista_espera.get(0);//Obtiene el primer cliente de la cola
            this.Lista_espera.remove(0);//Remueve el cliente de la cola
            clienteCola.setNroS(indexS);
            clienteCola.setTS(Archivo_TS.getTiempo(this.numA.generarNumero()));
            this.Lista_servicio.add(clienteCola);
            if(this.variables.getWL()!=0){
               EC.Acumular_Lq((tm-prev_tm)*this.variables.getWL());
            };
            EC.Acumular_Wq(tm-clienteCola.getTm_llegada());
            this.variables.susWL();
            //Suma de TM con TS 
            this.evento.updateDT(indexS,clienteCola.getTS()+tm);
          }
          else{
            if(tm>=this.tm_max){
               this.EC.setTSExtra(indexS,tm);
            }  
            cantCEnServicio=cantCEnServicio-1;   
            this.evento.updateDT(indexS,t_extra+1);
            this.variables.setStatusServidor(indexS,true);
            EC.setTiempoOcupado(indexS,tm-EC.getTiempoDesocupado(indexS));
          }
          System.out.println("\n");
          System.out.println("-----EVENTO---------");
          System.out.println("NroEvento: "+num_evento+"/ SALIDA /NroCliente: "+salidaCliente.getNro()+"/"+tm);
          this.variables.printVariables();
          System.out.println("------LISTA DE EVENTOS-------");
          System.out.println(this.evento.printATDT());
          System.out.println("-------------FIN---------------- ");
          System.out.println("\n");
          
          System.out.println("\n");
         
          System.out.println("-------ESTADO DE L-------");
          System.out.println("tm previo: "+prev_tm+" tm actual: "+tm+" cant_L: "+EC.Cant_L);
          System.out.println("--------------------------");
          System.out.println("-------ESTADO DE Lq-------");
          System.out.println("tm previo: "+prev_tm+" tm actual: "+tm+" cant_Lq: "+EC.Cant_Lq);
          System.out.println("--------------------------");
          System.out.println("-------ESTADO DE W-------");
          System.out.println("cant_W: "+EC.Cant_W);
          System.out.println("--------------------------");
          System.out.println("-------ESTADO DE Wq-------");
          System.out.println("cant_Wq: "+EC.Cant_Wq);
          System.out.println("--------------------------");
          
          proceso.RegistrarEvento("\n");
          proceso.RegistrarEvento("-----EVENTO------");
          proceso.RegistrarEvento("NroEvento: "+Integer.toString(this.num_evento)+"/ SALIDA / NroCliente:"+Integer.toString(salidaCliente.getNro())+"/");
          proceso.RegistrarEvento("-----VARIABLES DEL SISTEMA-----");
          proceso.RegistrarEvento(this.variables.stringVariables());
          proceso.RegistrarEvento("------LISTA DE EVENTOS-------");
          proceso.RegistrarEvento(this.evento.printATDT());
          proceso.RegistrarEvento("-------------FIN---------------- ");
          proceso.RegistrarEvento("\n");
        }    
           
        this.num_evento=this.num_evento+1;
        /*
         if(tm>=this.tm_max && cantCEnServicio>0 ){
             System.out.println("Tiempo extra clientes aun en servicio: "+cantCEnServicio);
             t_extra=t_extra+5;
          }
         else if(tm>=this.tm_max && cantCEnServicio<1){
            t_extra=this.tm_max;
         }*/
           
       }while(tm<=t_extra); //&& (this.variables.getCantClientes()!=0 || !this.Lista_espera.isEmpty())); //&& indexCliente<this.max_clientes);
       
  
       
       System.out.println("------LISTA DE OCUAPCION DE SERVIDORES DE TM: "+tm+" -------");
        
       for(int i=0;i<=this.evento.getLista().size()-1;i++){
          System.out.println("Servidor "+i+"con Ocupacion de: "+(EC.getTiempoOcupado(i))+"%");
       }
       
       System.out.println("-----------------------------------");
       System.out.println("\n \nCant clientes: "+cantCTotal);
       EC.Calcular_L(tm);
       EC.Calcular_Lq(tm);
       EC.CalcularPorUtilizacion(tm);
       EC.Calcular_W(cantCTotal+1);
       EC.Calcular_Wq(cantCTotal+1);
       EC.CalcularEspera(cantCTotal+1,cantCola);
       EC.calcularTExtraSistema(tm);
       EC.calcularSisOPNormal(tm);
       EC.setCantSinEspera(cantSinEspera);
       EC.setCantCola(cantCola);
       EC.setNoAtendidos(this.variables.getWL());
       EC.CalcularCostos();
       proceso.setEC(EC);
      
    }
    
  
}
