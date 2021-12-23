/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import fichero.*;
import palabra.*;
import java.util.*;

public class Game {
    int turnos; // Número de turnos que jugaremos en la partida
    int puntos = 0; // Puntos totales de la partida actual
    String usuario = ""; //Nombre del usuario
    Palabra[] diccionario = null; //Diccionario de palabras
    Rubrica[] rubrica = null; // Rubrica con las letras, sus puntos, y cantidad
    public Game() {

    }

    public void menu() throws Exception{ // Menu del juego
        LT lector = new LT();
        char opcion = ' ';
        while (opcion != 's') {  // mientras no salir
            System.out.println("\n\n");
            System.out.println("**************************************");
            System.out.println("*        Menú      *");
            System.out.println("**************************************");
            System.out.println("Escoge una opciÃ³n:");
            System.out.println("   1 Jugar");
            System.out.println("   2 Ver estadísticas");
            System.out.println("   s Salir");
            System.out.print("Opcion? ");
            opcion = lector.leerCaracter();
            switch (opcion) {
                case '1':
                    setUsuario();
                    jugar();
                    break;
                case '2':
                    partidas();
                    break;
            }
        }
        System.out.println("**************************************");
        System.out.println("*            Fin opción              *");
        System.out.println("**************************************");
        System.out.println("\n\n");
    }

    public void jugar() throws Exception{
        
        System.out.println("Escriba la cantidad de turnos que jugará: ");
        setTurnos(); //Establecemos el numero de turnos a jugar
        int turno = 1; //Turno actual
        this.puntos = 0;
        
        if(this.diccionario == null && this.rubrica == null){
                this.diccionario = LecturaFicheroBuffer.leerDiccionario("src\\fichero\\esp.dic", 188259); //Leemos el diccionario y lo almacenamos
                Rubrica.generarRúbrica(); // Generamos la rubrica de puntos 
                rubrica = Rubrica.rubricaPuntos; // La asignamos al juego
        }
        
        for (int i = 0; i < this.turnos; i++) {
            System.out.println("Turno: " + turno);
           
            
            //----------------------TO DO (bolsa de letras)-----------------------------------
            
            cuentaAtras(10); // Cuenta atrás para pensar
   
            Palabra e = Palabra.encontrarPalabra(diccionario, pedirPalabra()); //Buscamos la palabra introducida dentro del diccionario
            
            if(!e.vacia()){ // Si no esta vacía, es decir, si se ha encontrado
                System.out.println(e);
                calcularPuntos(e.getPal());
            }else{
                this.puntos -= 10; //Penalización
                System.out.println("Total de puntos: " + this.puntos);
            }
            turno++; // Turno completado
        }
        guardar();
    }
    
    public void setTurnos() {
        LT leerNumeroTurno = new LT();
        int t = leerNumeroTurno.leerEntero();
        this.turnos = t;
    }
    
    public void setUsuario(){
        LT l = new LT();
        System.out.println("¿Cual es su nombre de usuario?");
        String nombre = l.leerLinea();
        this.usuario = nombre;
    }
    
    public int getTurnos(){
        return this.turnos;
    }
    
    private Palabra pedirPalabra(){
        LT l = new LT();
        String palabra = l.leerLinea();
        char[] palabraChar = palabra.toCharArray();
        Palabra p = new Palabra();
        for (int j = 0; j < palabraChar.length; j++) {
            p.anadirCaracter(palabraChar[j]);
        }
        return p;   
    }
    
    private void guardar() throws Exception{
        String s = "";
        s += "Nombre: " + this.usuario + ", ";
        s += "Turnos: " + this.turnos + ", ";
        s += "Puntos: " + this.puntos + ", ";
        Date date = Calendar.getInstance().getTime();
        s += "Fecha: " +  date;
        EscrituraFichero fw = new EscrituraFichero("src\\fichero\\partidas.txt"); //Fichero que leeremos o crearemos
        fw.escribirString(s); //Escribimos
        fw.cerrar(); //Importante cerrar el buffer
    }
    public void cuentaAtras(int segundos) throws Exception{
        System.out.print("Piense una palabra: ");
        for(int i = segundos ; i >=0 ;i--){
            System.out.print(" "+ i + " ");
            Thread.sleep(1000); // Simplemente paramos la ejecución durante un segundo
        }
        System.out.println("");
        System.out.println("Escriba una palabra: ");
        
    }
    public void calcularPuntos(char[] p) throws Exception{
        for(int i = 0; i<p.length; i++){
            for(Rubrica r : this.rubrica){
                if(r.getLetra() == p[i]){
                    this.puntos += r.getPuntos();
                }
            }
        }
        System.out.println("Total de puntos: " + this.puntos);
    }
    
    private void partidas() throws Exception{
        System.out.println("");
        System.out.println("****************Registro de partidas****************");
        LecturaFicheroBuffer l = new LecturaFicheroBuffer("src\\fichero\\partidas.txt");
        String linea = l.leerTodoLineaALinea();
        while(linea != ""){
            System.out.println(linea);
            linea = l.leerTodoLineaALinea();
        }
    }
}
