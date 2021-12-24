package palabra;
public class Palabra {

    private final int max = 20;
    private char[] pal;
    private int n;
    

    public Palabra() {
        pal = new char[max];
        n = 0;
    }
    public char[] getPal(){
        return this.pal;
    }
    
    public int getN(){
        return this.n;
    }
    public static void leerArray(Palabra[] a){
        for(Palabra p : a){
            System.out.println(p);
        }
    }

    public static Palabra encontrarPalabra(Palabra[] diccionario, Palabra palabra){ //Buscamos la palabra dentro del diccionario
        Palabra encontrada = new Palabra();
        for(Palabra p: diccionario){
            if(p.n == palabra.n && p.pal[0] == palabra.pal[0]){
                if(sonIguales(palabra, p)){ // Si la hemos encontrado paramos el bucle
                     encontrada = p;
                     break;
                }
            }
        }
        return encontrada; // devolvemos la palabra encontrada o una palabra vacia en caso de no haberla encontrado
    }

    public void anadirCaracter(char c) {
        pal[n++] = c;
    }

    public boolean vacia() {
        return (n == 0);
    }
    public static boolean sonIguales(Palabra p1, Palabra p2) { // Comparamos dos palabras
      boolean iguales = true;
      for(int i=0; i<p1.n ;i++){
          if(p1.pal[i] != p2.pal[i]){
              iguales = false;
              break;
          }else{
              iguales = true;
          }
      }
      return iguales;
    }
    
    public int convertir(){ // convierte a num
        int res = 0;
        int mult = 1;
        int n = this.n;
        for(int i = this.n - 1;i>=0;i--){
            res += (this.pal[i] - '0') * mult;
            mult *= 10;
        }
        return res;
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
