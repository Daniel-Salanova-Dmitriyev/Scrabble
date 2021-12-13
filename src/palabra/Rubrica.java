/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palabra;

/**
 *
 * @author CASA
 */
public class Rubrica {
    private char letra;
    private int cantidad;
    private int puntos;
    private static Rubrica[] rubricaPuntos;
    private static int totalFichas = 104;
    public Rubrica (char l, int c, int p){
        this.letra = l;
        this.cantidad = c;
        this.puntos = p;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        String str = "{'letra':" + this.letra + " ,'cantidad':" + this.cantidad +", 'puntos':" + this.puntos + "}";
        return str;
    }
    
    
}
