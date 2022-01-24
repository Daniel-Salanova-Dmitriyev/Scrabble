/**
 *
 * @author Daniel Salanova Dmitriyev Grupo 2
 */
package fichero;
import java.io.*;
import palabra.*;
public class EscrituraFichero {
    private FileWriter f;
    private BufferedWriter bw;
    
    public EscrituraFichero(String nom) throws Exception {
        f = new FileWriter(nom, true); //Con true indicamos que se mantiene el archivo anterior para guardar partida sin borrar el contenido
        bw = new BufferedWriter(f);
    }
    
    public void cerrar() throws Exception{
        bw.close();
        f.close();
    }
    
    public void escribirString (String s) throws Exception{
        bw.newLine(); // Salto de linea
        bw.write(s);
    }
    
}
