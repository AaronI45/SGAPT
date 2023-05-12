/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package sgapt.modelo.dao;

import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sgapt.modelo.pojo.Empleado;
import sgapt.util.ShaConversor;

/**
 *
 * @author super
 */
public class SesionDAOTest {
    
    public SesionDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of verificarUsuarioSesion method, of class SesionDAO.
     */
    @Test
    public void testVerificarUsuarioSesion() throws Exception {
        System.out.println("verificarUsuarioSesion");
        String usuario = "albertsan";
        String password = "123456";
        Empleado expResult = new Empleado();
        expResult.setIdEmpleado(1);
        Empleado result = null;
        try{
            result = SesionDAO.verificarUsuarioSesion(usuario, password);    
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        assertEquals(expResult, result);
    }
    
}
