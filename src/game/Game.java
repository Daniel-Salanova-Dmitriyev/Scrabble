/**
 *
 * @author Daniel Salanova Dmitriyev Grupo 2
 */
package game;

import fichero.*;
import palabra.*;
import java.util.*;

public class Game {

    int turnos; // Número de turnos que jugaremos en la partida
    int puntos = 0; // Puntos totales de la partida actual
    int puntosBot = 0; //Puntos del bot
    int segundos; // Segundos para pensar
    String usuario = ""; //Nombre del usuario
    String idioma;
    Palabra[] diccionario = null; //Diccionario de palabras
    Rubrica[] rubrica = null; // Rubrica con las letras, sus puntos, y cantidad

    public Game(int segundos) {
        this.segundos = segundos;
    }
    
    /* El menu por el que podremos navegar al iniciar el juego*/
    public void menu() throws Exception { // Menu del juego
        LT lector = new LT();
        char opcion = ' ';
        while (opcion != 's') {  // mientras no salir
            System.out.println("\n\n");
            System.out.println("**************************************");
            System.out.println("*        Menú      *");
            System.out.println("**************************************");
            System.out.println("Escoge una opcion:");
            System.out.println("   1 Jugar");
            System.out.println("   2 Ver estadísticas");
            System.out.println("   s Salir");
            System.out.print("Opcion? ");
            opcion = lector.leerCaracter();
            switch (opcion) {
                case '1':
                    char opcionJugar = ' ';
                    while (opcionJugar != 's') { // mientras no salir
                        System.out.println("**************************************");
                        System.out.println("*        Menú      *");
                        System.out.println("**************************************");
                        System.out.println("Escoge un modo:");
                        System.out.println("   1 Un solo jugador");
                        System.out.println("   2 Bot extremo");
                        System.out.println("   3 Bot nivelado");
                        System.out.println("   4 Simulacion");
                        System.out.println("   s Salir");
                        System.out.print("Opcion? ");
                        opcionJugar = lector.leerCaracter();
                        switch (opcionJugar) {
                            case '1':
                                Rubrica.generarRubrica(); // Generamos la rubrica de puntos
                                this.rubrica = Rubrica.rubricaPuntos; // La asignamos al juego
                                setUsuario(); //Colocamos el nombre del usuario
                                seleccionarIdioma(); // Seleccion de idioma
                                jugar();
                                break;
                            case '2':
                                Rubrica.generarRubrica(); // Generamos la rubrica de puntos
                                this.rubrica = Rubrica.rubricaPuntos; // La asignamos al juego
                                setUsuario(); //Colocamos el nombre del usuario
                                seleccionarIdioma(); // Seleccion de idioma
                                jugarBotExtremo();
                                break;
                            case '3':
                                Rubrica.generarRubrica(); // Generamos la rubrica de puntos
                                this.rubrica = Rubrica.rubricaPuntos; // La asignamos al juego
                                setUsuario(); //Colocamos el nombre del usuario
                                seleccionarIdioma(); // Seleccion de idioma
                                
                                char nivel = 0; //Lo tratamos como caracter porque en el caso de que sea un int y alguien incorpora un caracter da error
                                while (nivel - '0' <= 0 || nivel - '0' > 9) {
                                    System.out.println("Elija un nivel del 1-9");
                                    nivel = lector.leerCaracter();
                                }
                                System.out.println("Nivel bot: " + nivel);
                                jugarBotNivelado(nivel - '0', false); // Se hace la conversión automatica de char a int al restar los dos carácteres, y es false porque es el modo nivelado
                                break;
                            case '4':
                                Rubrica.generarRubrica(); // Generamos la rubrica de puntos
                                this.rubrica = Rubrica.rubricaPuntos; // La asignamos al juego
                                setUsuario(); //Colocamos el nombre del usuario
                                seleccionarIdioma(); // Seleccion de idioma

                                char nivelSimulacion = '0';
                                while (nivelSimulacion - '0' <= 0 || nivelSimulacion - '0' > 9) {
                                    
                                    System.out.println("Elija un nivel del 1-9");
                                    nivelSimulacion = lector.leerCaracter();
                                }
                                System.out.println("Nivel simulacion: " + nivelSimulacion);
                                jugarBotNivelado(nivelSimulacion - '0', true); //True por ser el modo simulacion
                                break;
                        }
                    }

                    break;
                case '2':
                    estadisticas();
                    break;
            }
        }
        System.out.println("**************************************");
        System.out.println("*            Fin opción              *");
        System.out.println("**************************************");
        System.out.println("\n\n");
    }

    private void seleccionarIdioma() throws Exception {
        int eleccion = 0;
        int cantidadPalabras; // Cantidad palabras dentro del diccionario
        String ruta; // ruta del archivo diccionario
        LT l = new LT();
        System.out.println(" ");
        System.out.println("Seleccione un idioma: ");
        System.out.println("   1 Castellano");
        System.out.println("   2 Catalan");
        System.out.println("¿Cual elige?");
        while (eleccion != '1' && eleccion != '2') {
            eleccion = l.leerCaracter();
        }
        if (eleccion == '1') { // Si elegimos castellano
            ruta = "src\\fichero\\esp.dic";
            cantidadPalabras = Palabra.cantidadPalabrasEnDiccionario(ruta);
            this.idioma = "esp";
        } else { // Si elegimos catalan
            ruta = "src\\fichero\\cat.dic";
            cantidadPalabras = Palabra.cantidadPalabrasEnDiccionario(ruta);
            this.idioma = "cat";
        }
        this.diccionario = LecturaFichero.leerDiccionario(ruta, cantidadPalabras); //Leemos el diccionario y lo almacenamos
    }

    private void jugar() throws Exception { // 1 solo jugador
        System.out.println("Escriba la cantidad de turnos que jugará: ");
        setTurnos(); //Establecemos el numero de turnos a jugar
        int turno = 1; //Turno actual
        this.puntos = 0; // Ponemos los puntos a 0

        for (int i = 0; i < this.turnos; i++) {
            System.out.println("Turno: " + turno);
            iniciarTurno();
            turno++; // Turno completado
        }

        guardar(false, 4); //Guardamos datos de la partida dentro del fichero partidas.txt
    }

    private void jugarBotExtremo() throws Exception { //Modo Cerebro Superior
        System.out.println("Escriba la cantidad de turnos que jugará: ");
        setTurnos(); //Establecemos el numero de turnos a jugar
        int turno = 1; //Turno actual
        this.puntos = 0; // Ponemos los puntos a 0
        this.puntosBot = 0; // Ponemos los puntos del bot a 0

        for (int i = 0; i < this.turnos; i++) {
            System.out.println("Turno: " + turno);
            Palabra[] fichasContrincante = generar(false); //Generamos las fichas del contrincante
            Palabra[] posiblesPalabras = Palabra.comprobarEnFichasPalabras(fichasContrincante, this.diccionario); //Todas las palabras que se pueden crear con las fichas que tiene
            Palabra elegidaBot = mejorPalabra(posiblesPalabras); //Palabra con mayor puntuación
            iniciarTurno();
            System.out.println("Total de puntos del bot: " + this.puntosBot);
            turno++; // Turno completado
        }

        guardar(true, resultadoPartida()); //Guardamos datos de la partida dentro del fichero partidas.txt
    }

    private void jugarBotNivelado(int nivel, boolean simulacion) throws Exception { // 2 Modos, simulacion y bot nivelado
        System.out.println("Escriba la cantidad de turnos que jugará: ");
        setTurnos(); //Establecemos el numero de turnos a jugar
        int turno = 1; //Turno actual
        this.puntos = 0; // Ponemos los puntos a 0
        this.puntosBot = 0; // Ponemos los puntos del bot a 0
        for (int i = 0; i < this.turnos; i++) {
            System.out.println("Turno: " + turno);
            Rubrica.generarRubrica();
            this.rubrica = Rubrica.rubricaPuntos;
            Palabra[] fichasContrincante = generar(false); //Generamos las fichas del contrincante
            Palabra[] filtradasPorNivel;

            if (simulacion) { //Simulacion
                Palabra[] palabrasAleatorias = Palabra.creadorPalabras(fichasContrincante, nivel); // Crea palabras con las ficahs de manera aleatoria
                filtradasPorNivel = Palabra.comprobacionPalabra(palabrasAleatorias, this.diccionario); //Comprobamos que las palabras existan dentro del diccionario
            } else { //Bot Nivelado
                Palabra[] posiblesPalabras = Palabra.comprobarEnFichasPalabras(fichasContrincante, this.diccionario); // Todas las palabras que puede crar con las fichas
                filtradasPorNivel = Palabra.filtrarPalabrasSegunNivel(nivel, posiblesPalabras); // Cogerá de todas las palabras que puede crear con sus fichas la misma cantidad que el nivel que tiene de manera aleatoria  
            }

            Palabra elegidaBot = mejorPalabra(filtradasPorNivel); // Se quedará la mayor puntuación tiene
            iniciarTurno();
            System.out.println("Total de puntos del bot: " + this.puntosBot);
            turno++; // Turno completado

        }
        guardar(true, resultadoPartida()); //Guardamos datos de la partida dentro del fichero partidas.txt
    }

    private void iniciarTurno() throws Exception {

        Palabra[] fichas = generar(true); //Generamos las fichas
        cuentaAtras(this.segundos); // Cuenta atrás para pensar
        Palabra miPalabra = Palabra.pedirPalabra();
        if (miPalabra.comprobarPalabraEnFichas(fichas)) { //Primera comprobación, si te has inventado letras no existentes dentro de la bolsa de fichas
            Palabra e = Palabra.encontrarPalabra(diccionario, miPalabra); //Buscamos la palabra introducida dentro del diccionario

            if (!e.vacia()) { // Si no esta vacía, es decir, si se ha encontrado
                System.out.println(e);
                calcularPuntos(e.getPal());
            } else { // Palabra no existente
                this.puntos -= 10; //Penalización
                System.out.println("Palabra no existente, has perdido -10 puntos");

            }

        } else { //Palabra con letras/espacios... que no tenemos
            this.puntos -= 10; //Penalización
            System.out.println("Has hecho trampas, has perdido -10 puntos");

        }
        System.out.println("Total de puntos: " + this.puntos);
    }

    private int resultadoPartida() {
        int i = 0;
        if (this.puntosBot > this.puntos) {
            System.out.println("Partida perdida");
            i = 2;
        } else if (this.puntosBot == this.puntos) {
            System.out.println("Partida empatada");
            i = 0;
        } else {
            i = 1;
            System.out.println("Partida ganada");
        }
        return i; //DEvolvemos código de partida para cuando vayamos a guardar el registro
    }

    public void setTurnos() {
        LT leerNumeroTurno = new LT();
        char[] t = leerNumeroTurno.leerLinea().toCharArray();
        Palabra aux = new Palabra();
        boolean esNumero = false;
        while (!esNumero) { //Mientras todas los caracteres no sean numeros
            aux = new Palabra();
            for (int i = 0; i < t.length; i++) {
                if (t[i] < '0' || t[i] > '9') { //Comprobamos que ese caracter represente un numero
                    t = leerNumeroTurno.leerLinea().toCharArray();
                    esNumero = false;
                    break;
                }else{
                    aux.anadirCaracter(t[i]);
                    esNumero = true;
                }
                
            }
            
        }
        
        this.turnos = aux.convertir(); //Convertimos el caracter a numero
    }

    public void setUsuario() {
        LT l = new LT();
        System.out.println("¿Cual es su nombre de usuario?");
        String nombre = l.leerLinea();
        this.usuario = nombre;
    }

    public int getTurnos() {
        return this.turnos;
    }

    private void guardar(boolean bot, int resultado) throws Exception { //Guardar partida dentro del fichero de partidas
        String s = "";
        s += "Nombre: " + this.usuario;
        s += ", Turnos: " + this.turnos;
        s += ", Puntos: " + this.puntos;
        Date date = Calendar.getInstance().getTime(); // Fecha completa (dia, hora, año, mes...)

        EscrituraFichero fw = new EscrituraFichero("src\\fichero\\partidas.txt"); //Fichero que leeremos o crearemos
        if (bot) { //Si se ha jugado con un bot, añadimos más campos
            s += ", Puntos bot: " + this.puntosBot;
            if (resultado == 0) {
                s += ", Resultado: Empate";
            }
            if (resultado == 1) {
                s += ", Resultado: Ganada";
            } else {
                s += ", Resultado: Derrota";
            }

        }
    
        s += ", Idioma: ";
        
        for (char c : this.idioma.toCharArray()) {
            s += c;
        }
        
        s += ", Fecha: " + date;
        fw.escribirString(s); //Escribimos
        fw.cerrar(); //Importante cerrar el buffer
    }

    private void cuentaAtras(int segundos) throws Exception {
        System.out.println("");
        System.out.print("Piense una palabra: ");
        for (int i = segundos; i >= 0; i--) {
            System.out.print(" " + i + " ");
            Thread.sleep(1000); // Simplemente paramos el hilo de ejecución durante un segundo
        }
        System.out.println("");
        System.out.println("Escriba una palabra: ");

    }

    private void calcularPuntos(char[] p) throws Exception {
        int total = 0;
        for (int i = 0; i < p.length; i++) { // Recorremos cada letra de una palabra
            for (Rubrica r : this.rubrica) { // Recorremos toda la rubrica
                if (r.getLetra() == p[i]) {
                    total += r.getPuntos();
                    this.puntos += r.getPuntos();
                }
            }
        }
        System.out.println("Has ganado " + total + " puntos");
    }

    private void estadisticas() throws Exception {
        System.out.println("");
        System.out.println("**************** Registro de partidas ****************");
        LecturaFichero l = new LecturaFichero("src\\fichero\\partidas.txt");
        String linea = l.leerTodoLineaALinea();
        System.out.println(linea);
    }

    private Palabra[] generar(boolean player) throws Exception {
        Palabra[] fichas = new Palabra[11]; // Fichas para la partida
        for (int i = 0; i < fichas.length; i++) {
            fichas[i] = mezclar();
        }

        if (!player) {
            System.out.print("Fichas bot: ");
        } else {
            System.out.print("Tus fichas: ");
        }
        for (int i = 0; i < fichas.length; i++) {
            if (fichas[i] != null) {
                System.out.print(fichas[i].getPal()[0] + " ");
            }
        }

        return fichas;
    }

    private Palabra mezclar() throws Exception {
        Random r = new Random();
        int numeroLetras = this.rubrica.length;
        int n = r.nextInt(numeroLetras); //entre 0 y total - 1
        if (Rubrica.numeroLetras == 0) { // Cuando nos quedemos sin letras, devolvemos todas las fichas a la 'bolsa'
            Rubrica.generarRubrica();
            this.rubrica = Rubrica.rubricaPuntos;
        }
        while (this.rubrica[n].getCantidad() == 0) {
            n = r.nextInt(numeroLetras);
        }
        this.rubrica[n].setCantidad(-1); // Como la hemos usado descontamos de la cantidad
        Palabra l = new Palabra();
        l.anadirCaracter(this.rubrica[n].getLetra());
        Rubrica.numeroLetras -= 1; // Descontamos del total de letras una letra que usamos
        return l;
    }

    private Palabra mejorPalabra(Palabra[] posiblesPalabras) { //Busca la palabra con mayor puntuacion
        Palabra mejor = new Palabra();
        int puntuacionPrincipal = 0; //Puntuacion mejor palabra
        int puntuacionSecundaria = 0; //Puntuacion palabra actual
        for (Palabra p : posiblesPalabras) { //Recorremos las palabras
            if (p != null) {
                for (int c = 0; c < p.getN(); c++) { //Recorremos los caracteres de una palabra
                    for (Rubrica r : this.rubrica) { //Recorremos la rubrica para puntuar la palabra
                        if (r.getLetra() == p.getPal()[c]) {
                            puntuacionSecundaria += r.getPuntos(); //Puntuamos la palabra

                        }
                    }
                }
                if (puntuacionSecundaria >= puntuacionPrincipal) { // Si es mayor, es la mejor palabra hasta el momento
                    puntuacionPrincipal = puntuacionSecundaria;
                    mejor = p;
                }
                puntuacionSecundaria = 0; //Ponemos a 0 la puntuación de la siguiente palabra a tratar
            }
        }
        System.out.println(" ");

        if (mejor.vacia()) { //Sirve para el bot modo simulacion, ya que puede haber veces en las que no tenga ninguna palabra existente en el diccionario
            System.out.print("Palabra no encontrada, penalización al bot");
            System.out.println("");
            this.puntosBot -= 10;
        } else {
            System.out.print("Mejor Palabra bot es " + mejor + " , de puntos: " + puntuacionPrincipal);
            System.out.println("");
            this.puntosBot += puntuacionPrincipal;
        }

        return mejor;
    }

}
