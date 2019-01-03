/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import vistas.Principal;

/**
 * @author charg
 * Clase para manejar archivos de texto plano,
 * contiene las funciones leer, crear y guardar/actualizar archivo.
 */
public class Functions {

    private JFileChooser accion = null;
    private File archivo = null;
    private Principal principal;
    public static String ruta = "";

    public Functions(Principal principal) {
        this.principal = principal;
    }

    /**
     * Metodo para leer un archivo de texto plano con formato predeterminado.
     * @return Retorna un valor que comprueba que se pudo leer el archivo 
     * sin ningun problema, en caso contrario mandara una excepción.
     */
    public int LeerFichero() {
        accion = new JFileChooser();
        accion.setFileSelectionMode(0);
        accion.setCurrentDirectory(new File("C:\\Users\\charg\\Documents\\ISC7A\\Lenguajes y Automatas II\\codes\\"));
        FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("AK", "ak");
        accion.setFileFilter(filtroImagen);
        accion.setDialogTitle("Abrir archivo");
        if (accion.showOpenDialog(principal) == JFileChooser.APPROVE_OPTION) {
            archivo = accion.getSelectedFile();
            this.principal.setTitle("Akio - " + archivo.getName());
            ruta = accion.getSelectedFile().toString();
            try {
                /*Si existe el fichero*/
                if (archivo.exists()) {
                    /*Abre un flujo de lectura a el fichero*/
                    BufferedReader leeArchivo = new BufferedReader(new FileReader(archivo));
                    String Slinea, datos = "";
                    /*Lee el fichero linea a linea hasta llegar a la ultima*/
                    while ((Slinea = leeArchivo.readLine()) != null) {
                        /*Imprime la linea leida*/
                        datos = datos + Slinea + "\n";
                    }
                    if (datos.length() > 0) {
                        principal.txtPanCode.setText(datos.substring(0, datos.length() - 1));
                    }
                    principal.txtPanCode.setEditable(true);
                    principal.txtPanCode.requestFocus();
                    /*Cierra el flujo*/
                    leeArchivo.close();
                }
            } catch (IOException ex) {
                /*Captura un posible error y le imprime en pantalla*/
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
        return 200;
    }

    /**
     * Metodo para crear un archivo de texto plano con formato predeterminado.
     * @param nombre
     * @param SCadena cadena de tipo String que contiene la cadena a guardar en un archivo
     * de text plano.
     * @return Retorna un valor que comprueba que se pudo crear el archivo 
     * sin ningun problema, en caso contrario mandara una excepción.
     */
    public int CrearFicheroNuevo(String SCadena, String nombre) {
        accion = new JFileChooser();
        accion.setFileSelectionMode(0);
        accion.setCurrentDirectory(new File("C:\\Users\\charg\\Documents\\ISC7A\\Lenguajes y Automatas II\\codes\\"));
        FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("AK", "ak");
        accion.setFileFilter(filtroImagen);
        accion.setDialogTitle("Guardar archivo " + nombre);
        accion.setSelectedFile(new File(nombre));
        if (accion.showSaveDialog(principal) == JFileChooser.APPROVE_OPTION) {
            ruta = accion.getSelectedFile().toString();
            if (!ruta.endsWith(".ak")) ruta+=".ak";
            archivo = new File(ruta);
            try {
                //Si Existe el fichero lo borra
                if (archivo.exists()) {
                    if (JOptionPane.showConfirmDialog(null, "Se sobreescribira el archivo") == JOptionPane.YES_OPTION) {
                        archivo.delete();
                        archivo = new File(ruta);
                        this.principal.setTitle("Akio - " + archivo.getName());
                    } else {
                        archivo = new File(ruta.substring(0, ruta.length() - 3) + " - copia.ak");
                        this.principal.setTitle("Akio - " + archivo.getName());
                    }
                }
                BufferedWriter wr = new BufferedWriter(new FileWriter(archivo));
                FileWriter escribirArchivo = new FileWriter(archivo, true);
                BufferedWriter buffer = new BufferedWriter(escribirArchivo);
                buffer.write(SCadena);
                //buffer.newLine();
                buffer.close();
                wr.close();
                escribirArchivo.close();
            } catch (Exception ex) {
            }
            return 200;
        } else {
            return 0;
        }
    }

    /**
     * Metodo para guardar un archivo de texto plano con formato predeterminado.
     * @param SCadena cadena de tipo String que contiene la cadena a guardar en un archivo
     * @param nombre
     * @return Retorna un valor que comprueba que se pudo guardar el archivo 
     * sin ningun problema, en caso contrario mandara una excepción.
     */
    public int GuardarFichero(String SCadena, String nombre) {
        try {
            /* Si existe el fichero no lo crea de nuevo */
            if (archivo.exists()) {
                BufferedReader leeArchivo = new BufferedReader(new FileReader(archivo));
                String Slinea, datos = "";
                /*Lee el fichero linea a linea hasta llegar a la ultima*/
                while ((Slinea = leeArchivo.readLine()) != null) {
                    /*Imprime la linea leida*/
                    datos = datos + Slinea + "\n";
                }
                if (datos.length() > 0) {
                    datos = datos.substring(0, datos.length() - 1);
                }
                /* Si el archivo contiene el mismo texto que la caja de texto no lo guardara */
                if (!datos.equals(SCadena)) {
                    BufferedWriter wr = new BufferedWriter(new FileWriter(archivo));
                    FileWriter escribirArchivo = new FileWriter(archivo, true);
                    BufferedWriter buffer = new BufferedWriter(escribirArchivo);
                    buffer.write(SCadena);
                    //buffer.newLine();
                    buffer.close();
                    wr.close();
                    escribirArchivo.close();
                }
                this.principal.setTitle("Akio - " + archivo.getName());
            }
        } catch (IOException ex) {
            /* Captura un posible error le imprime en pantalla  */
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return 200;
    }
}
