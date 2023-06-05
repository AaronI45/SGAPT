/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sgapt.modelo.pojo;

import java.util.ArrayList;

/**
 *
 * @author zS21022065
 */
public class EmpleadoRespuesta {
    private int codigoRespuesta;
    private int idEmpleado;
    private ArrayList <Empleado> empleados;
    
    public EmpleadoRespuesta(){
        
    }
    public EmpleadoRespuesta(int codigoRespuesta, ArrayList<Empleado> empleados){
        this.codigoRespuesta = codigoRespuesta;
        this.empleados = empleados;
        
    }
    public int getCodigoRespuesta(){
        return codigoRespuesta;
    }
    
    public void setCodigoRespuesta(int codigoRespuesta){
        this.codigoRespuesta = codigoRespuesta;
    }
    public ArrayList<Empleado>getEmpleados(){
        return empleados;   
    }
    public void setEmpleados(ArrayList<Empleado> empleados){
        this.empleados = empleados;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    
    @Override
    public String toString(){
    return "EmpleadoRespuesta{ " + "codigoRespuesta=" + codigoRespuesta + ", empleados" + empleados + '}';
    
    }
    
}
