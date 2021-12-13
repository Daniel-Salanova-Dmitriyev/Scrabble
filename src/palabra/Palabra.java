package palabra;
public class Palabra {

    private final int max = 20;
    private char[] pal;
    private int n;
    

    public Palabra() {
        pal = new char[max];
        n = 0;
    }
    
    public static void leerArray(Palabra[] a){
        for(Palabra p : a){
            System.out.println(p);
        }
    }
    public int puntosPalabra(char[] rubrica){
        int total = 0;
        for(int i=0;i<this.pal.length;i++){
            total += puntosLetra(this.pal[i], rubrica);
        }
        return total;
    }
    public int puntosLetra(char l, char[] rubrica){
        int total = 0;
        int i = 0;
        while(l != rubrica[i]){
            i++;
        }
        // total += rubrica[i].puntos;
        return total;
    }
    public static Palabra encontrarPalabra(Palabra[] diccionario, Palabra palabra){
        Palabra encontrada = new Palabra();
        for(Palabra p: diccionario){
            if(p.n == palabra.n && p.pal[0] == palabra.pal[0]){
                if(sonIguales(palabra, p)){
                     encontrada = p;
                     break;
                }
            }
        }
        return encontrada;
    }

    public void anadirCaracter(char c) {
        pal[n++] = c;
    }

    public boolean vacia() {
        return (n == 0);
    }
    public static boolean sonIguales(Palabra p1, Palabra p2) {
      boolean iguales = true;
      for(int i=0;i<p1.n;i++){
          if(p1.pal[i] != p2.pal[i]){
              iguales = false;
              break;
          }else{
              iguales = true;
          }
      }
      return iguales;
    }
    
    @Override
    public String toString() {
        String res = "{'palabra': ";
        for (int i = 0; i < n; i++) {
            res = res + pal[i];
        }
        res += ",'tamaño':" + n + "}";
        return res;
    }
}
