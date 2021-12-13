/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fichero;
import palabra.Palabra;
import palabra.Rubrica;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class LecturaFicheroBuffer {
    
    public static Palabra[] leer(String fileName){
        Palabra[] palabras= new Palabra[188259];
        Path path = Paths.get(fileName);
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String str;
            Palabra p;
            int contador = 0;
            while ((str = reader.readLine()) != null) {
                p = new Palabra();
                char[] charArray = str.toCharArray();
                for(int i=0; i<charArray.length; i++){
                    p.anadirCaracter(charArray[i]);
                }
                palabras[contador] = p;
                contador++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }   
        return  palabras;
    }
    public static Rubrica[] alfabeto(String fileName){
        Path path = Paths.get(fileName);
        Rubrica[] rubricaLetras = new Rubrica[26];
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String str;
            String[] strArr = new String[28];
            Rubrica r;
            int c = 0;
            int k = 0;
            while ((str = reader.readLine()) != null) {
                strArr[c] = str;
                c++;
            }
            
            for(int i=1; i<strArr.length;i++){
                //Lega hasta qaui
                char[] linea = strArr[i].toCharArray();
                char letra = linea[0]; //Letra
                int puntos = 0;
                int cantidad = 0;
                int selector = 1;
                boolean letraEncontrada = false;
                for(int j=1;j<linea.length;i++){
                   if(linea[j] != ' ' && linea[j+1] == ' ' && selector == 1){
                       puntos += linea[j];
                   }
                   if(linea[j] != ' ' && linea[j+1] != ' ' && selector == 1){
                       puntos += linea[j];
                       puntos += linea[j+1] * 10;
                   }
                    if(linea[j] != ' ' && linea[j+1] == ' ' && selector == 2){
                       cantidad += linea[j];
                   }
                   if(linea[j] != ' ' && linea[j+1] != ' ' && selector == 2){
                       cantidad += linea[j];
                       cantidad += linea[j+1] * 10;
                   } 
                }
                r = new Rubrica(letra,cantidad,puntos);
                System.out.println(r);
                rubricaLetras[k] = r;
                k++;
            }
           
        } catch (IOException e) {
            e.printStackTrace();
        }   
         for(Rubrica r: rubricaLetras){
            System.out.println(r);
        }
         return rubricaLetras;
    }
}
