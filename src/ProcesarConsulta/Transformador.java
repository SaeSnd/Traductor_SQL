package ProcesarConsulta;

import java.util.*;

public class Transformador {

    public ArrayList<String> query; //La consulta que queremos transformar
    public int tamaño; //Longitud del arrayList

    public Transformador(ArrayList<String> query) { //Constructor que recibe el arrayList
        this.query = query;
        tamaño = query.size();
    }

    public String transformar() throws Exception { //Metodo que como tal transforma a SQL
        //Variables STRINGS
        /*Se usaran para formar el resultado como tal*/
        String camposSelect = "*";
        String relaciones = "";
        String condicionWhere = "";

        String unionSeleccion = "";
        String unionCampos = "*";
        String unionTablas = "";
        String unionCondiciones = "";
        boolean opDeUnionActual = false;

        String diferenciaSelect = "";
        String diferenciaCampos = "*";
        String diferenciaTablas = "";
        String diferenciaCondiciones = "";
        boolean opDiferenciaActual = false;
        boolean opInterActual = false;

        String unirTabla = "";
        String unirCondicion = "";
        String innerJoin = "INNER JOIN";
        boolean condicionInnerJoinActual = false;
        //FIN Variables STRINGS

        int i = 0;
        while (i < tamaño) { //Se va recorriendo el arrayList

            String elemento = query.get(i); //Elemento actual del arrayList
            // σ (Seleccion)
            if (elemento.equals("σ")) {
                i++;
                String condicionesEntrada = "";

                while (!query.get(i).equals("(")) {//alamacena todo lo que esta entre la seleccion y (
                    if (query.get(i).equals("&")) {
                        condicionesEntrada += " AND ";
                    } else if (query.get(i).equals("|")) {
                        condicionesEntrada += " OR ";
                    } else {
                        condicionesEntrada += query.get(i);
                    }
                    i++;
                }
                if (opDeUnionActual) {
                    unionCondiciones += "WHERE  ";
                    unionCondiciones += condicionesEntrada;
                } else {
                    condicionWhere += "WHERE ";
                    condicionWhere += condicionesEntrada;
                }
                elemento = query.get(i);
            }

            // ∏ (Proyeccion)
            if (elemento.equals("Π")) {
                String camposAReducirDeEntrada = " ";
                i++;
                while (!query.get(i).equals("(")) {//alamacena toodo lo que esta entre la seleccion y (

                    camposAReducirDeEntrada += query.get(i);
                    i++;
                }

                if (opDiferenciaActual || opInterActual) {
                    diferenciaCampos = "";
                    diferenciaCampos += camposAReducirDeEntrada;
                }

                if (opDeUnionActual) {
                    unionCampos = "";
                    if (unionCampos.length() > 0) {
                        unionCampos += "," + camposAReducirDeEntrada;
                    } else {
                        unionCampos += camposAReducirDeEntrada;
                    }
                }

                if (!opDiferenciaActual && !opDeUnionActual && !opInterActual) {

                    if (camposSelect == "*") {
                        camposSelect = "";
                    }

                    if (camposSelect.length() > 0) {
                        camposSelect += "," + camposAReducirDeEntrada;
                    } else {
                        camposSelect += camposAReducirDeEntrada;
                    }
                }

                //i = relationalInput.indexOf("(", i);
                elemento = query.get(i);
            }

           
            // ∪ (Union)
            if (elemento.equals("∪")) {
                opDeUnionActual = true;
                unionSeleccion += "UNION  SELECT";
            }
            
             if (elemento.equals("∪B")) {
                opDeUnionActual = true;
                unionSeleccion += "UNION ALL SELECT";
            }

            // Χ (Producto Cruz)
            if (elemento.equals("*")) {
                if (opDeUnionActual) {
                    unionTablas += ",";
                } else {
                    relaciones += ",";
                }

            }

            // ∩ (Interseccion)
            if (elemento.equals("∩")) {
                condicionWhere += "WHERE EXISTS(";
                diferenciaSelect += " SELECT ";
                opInterActual = true;
            }

            // - (Diferencia)
            if (elemento.equals("-")) {
                condicionWhere += "WHERE " + "NOT EXISTS(";
                diferenciaSelect += " SELECT ";
                opDiferenciaActual = true;
            }

            //JOIN
            if (elemento.equals("JOIN")) {
                String onConditionsToSnipFromInput = " ";
                i++;
                while (!query.get(i).equals("(")) {//alamacena toodo lo que esta entre la seleccion y (

                    onConditionsToSnipFromInput += query.get(i);
                    i++;
                }
                condicionInnerJoinActual = true;
                unirCondicion += onConditionsToSnipFromInput;
                elemento = query.get(i);
                continue;
            }

            //se revisa que lo que contiene el elemento sea una relacion y que ya no existan operadores
            if (!elemento.equals("(")
                    && !elemento.equals(")")
                    && !elemento.equals("))")
                    && !elemento.equals("*")
                    && !elemento.equals("∪")
                    && !elemento.equals("∪B")
                    && !elemento.equals("-")
                    && !elemento.equals("∩")) {

                if (opDiferenciaActual || opInterActual) {
                    diferenciaTablas += elemento;
                }

                if (opDeUnionActual) {
                    unionTablas += elemento;
                }

                if (condicionInnerJoinActual) {
                    unirTabla += elemento;
                }

                if (!opDiferenciaActual
                        && !opDeUnionActual
                        && !opInterActual
                        && !condicionInnerJoinActual) {
                    relaciones += elemento;
                }

            }

            i++;
        } //Fin de recorrer arrayList

        //Se va construyendo el resultado
        String result = "SELECT  " + camposSelect + " FROM " + relaciones;

        if (unirTabla.length() > 0) {
            result += " " + innerJoin + " " + unirTabla + " " + "ON" + " " + unirCondicion;
        }

        if (condicionWhere.length() > 0) {
            result += " " + condicionWhere;
        }

        if (unionSeleccion.length() > 0) {
           
            result += " " + unionSeleccion + " " + unionCampos + "  FROM " + unionTablas + " "
                    + unionCondiciones;
        }

        if (diferenciaSelect.length() > 0) {
            String fieldsForGeneralToCompare[] = camposSelect.split(",");

            if (!diferenciaCampos.equals(camposSelect)) {
                return "ERROR EN ALGEBRA RELACIONAL AL USAR ALGUNOS OPERADORES BINARIOS LAS RELACIONES DEBEN SER COMPATIBLES(grado y dominio)";
            }

            diferenciaCondiciones += "WHERE";

            for (String c : fieldsForGeneralToCompare) {
                diferenciaCondiciones += " " + relaciones + "." + c + "=" + diferenciaTablas + "." + c;
                if(!c.equals(fieldsForGeneralToCompare[fieldsForGeneralToCompare.length-1])){
                    diferenciaCondiciones+=" AND ";
                }
            }

            diferenciaCondiciones += ")";

            result += " " + diferenciaSelect + " " + diferenciaCampos + " FROM " + diferenciaTablas + " "
                    + diferenciaCondiciones+";";
        }

        return result.trim();
    }
}
