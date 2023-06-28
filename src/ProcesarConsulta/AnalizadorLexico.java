
package ProcesarConsulta;

import java.io.File;


public class AnalizadorLexico {
    
    
    public static void main(String[] args) {
        String ruta="/Traductor_Sql/src/lexico/Lexer.flex";
        crearALex(ruta);
        
    }
    
    public static void crearALex(String ruta){
        File archivo= new File(ruta);
        JFlex.Main.generate(archivo);
    }
    
}
