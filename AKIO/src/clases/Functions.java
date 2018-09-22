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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import vistas.Principal;

/**
 *
 * @author charg
 */
public class Functions {

    private JFileChooser accion = null;
    private File archivo = null;
    private Principal principal;
    public static String ruta = "";

    public void LeerFichero(Principal principal) {
        this.principal = principal;

        accion = new JFileChooser();
        accion.setFileSelectionMode(0);
        FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("AK", "ak");
        accion.setFileFilter(filtroImagen);
        accion.setDialogTitle("Abrir archivo");
        if (accion.showOpenDialog(principal) == JFileChooser.APPROVE_OPTION) {
            archivo = accion.getSelectedFile();
            principal.setTitle("Akio - " + archivo.getName());
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
                    principal.txtPanCode.setText(datos);
                    principal.txtPanCode.setEditable(true);
                    principal.txtPanCode.requestFocus();
                    /*Cierra el flujo*/
                    leeArchivo.close();
                    this.principal.abrioArchivo = true;
                    this.principal.creoNuevo = false;
                    this.principal.guardado = true;
                } else {
                    System.out.println("Fichero No Existe");
                }
            } catch (Exception ex) {
                /*Captura un posible error y le imprime en pantalla*/
                System.out.println(ex.getMessage());
            }
        }
    }

    public void CrearFicheroNuevo(Principal principal, String SCadena, String nombre) {
        this.principal = principal;

        accion = new JFileChooser();
        accion.setFileSelectionMode(0);
        FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("AK", "ak");
        accion.setFileFilter(filtroImagen);
        accion.setDialogTitle("Guardar archivo " + nombre);
        accion.setSelectedFile(new File(nombre));
        if (accion.getSelectedFile().exists()) {

        }
        if (accion.showSaveDialog(principal) == JFileChooser.APPROVE_OPTION) {
            ruta = accion.getSelectedFile().toString();
            archivo = new File(ruta);
            try {
                //Si Existe el fichero lo borra
                if (archivo.exists()) {
                    if (JOptionPane.showConfirmDialog(null, "Se sobreescribira el archivo") == JOptionPane.YES_OPTION) {
                        archivo.delete();
                    } else {
                        JOptionPane.showMessageDialog(null, "Estuvo cerca");
                        archivo = new File(ruta.substring(0, ruta.length()-3) + " - copia.ak");
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

                this.principal.abrioArchivo = true;
                this.principal.creoNuevo = false;
            } catch (Exception ex) {
            }
        }
    }

    public void GuardarFichero(String SCadena, String nombre) {

        System.out.println(ruta);
        archivo = new File(ruta);
        try {
            //Si Existe el fichero lo borra
            if (archivo.exists()) {
                archivo.delete();
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
            //Captura un posible error le imprime en pantalla 
            System.out.println(ex.getMessage());
        }
    }

}
