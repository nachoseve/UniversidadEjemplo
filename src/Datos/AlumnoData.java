
package Datos;

import Entidades.Alumno;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class AlumnoData {
    private Connection con = null;
    
    public void guardarAlumno(Alumno alumno){
        String sql = "INSERT INTO alumno (dni, apellido, nombre, fechaNacimiento, estado) VALUES (?,?,?,?,?)";
        try{
           PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
           ps.setInt(1, alumno.getDni());
           ps.setString(2, alumno.getApellido());
           ps.setString(3, alumno.getNombre());
           ps.setDate(4, Date.valueOf(alumno.getFechaNac()));
           ps.setBoolean(5, alumno.isActivo());
           ps.executeUpdate();
           
          ResultSet rs = ps.getGeneratedKeys();
          if(rs.next()){
              alumno.setIdAlumno(rs.getInt("idAlumno"));
          }else{
              System.out.println("No se pudo obtener ID");
        }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al acceder a Alumno"+ex.getMessage());
    }
 }
    public Alumno buscarAlumno(int id){
        Alumno alumno = null;
        String sql = "SELECT dni,apellido,nombre,fechaNac FROM alumno WHERE idAlumno = ? AND estado = 1";
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
            alumno = new  Alumno();
            alumno.setIdAlumno(id);
            alumno.setApellido(rs.getString("apellido"));
            alumno.setNombre(rs.getString("nombre"));
            alumno.setFechaNac(rs.getDate("FechaNacimiento").toLocalDate());
            alumno.setActivo(true);
            
        }else {
                JOptionPane.showMessageDialog(null, "No existe el alumno");
                }
        ps.close();
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Alumno "+ex.getMessage());
        }
        return alumno;
    }
    public Alumno buscarAlumnoPorDni(int dni){
        Alumno alumno = null;
        String sql = "SELECT idAlumno, dni, apellido, nombre, fechaNac FROM alumno WHERE dni = ? AND activo = 1";
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
               alumno = new Alumno();
               alumno.setIdAlumno(rs.getInt("idAlumno"));
               alumno.setDni(rs.getInt("dni"));
               alumno.setApellido(rs.getString("apellido"));
               alumno.setNombre(rs.getString("nombre"));
               alumno.setFechaNac(rs.getDate("fechaNac").toLocalDate());
               alumno.setActivo(true);
            }else{
                JOptionPane.showMessageDialog(null, "No existe alumno con ese id");
            }
            
            ps.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Aluumno "+ex.getMessage());
        }
        return alumno;
    }
    public List<Alumno> listarAlumnos(){
        List<Alumno> alumnos = new ArrayList<>();
        try{
            String sql = "SELECT * FROM alumno where activo = 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
               Alumno alumno = new Alumno();
               alumno.setIdAlumno(rs.getInt("idAlumno"));
               alumno.setDni(rs.getInt("dni"));
               alumno.setApellido(rs.getString("apellido"));
               alumno.setNombre(rs.getString("nombre"));
               alumno.setFechaNac(rs.getDate("fechaNac").toLocalDate());
               alumno.setActivo(rs.getBoolean("activo"));
               alumnos.add(alumno);                       
                
            }
            ps.close();
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Alumno "+ex.getMessage());
        }
        return alumnos;
    }
    public void modificarAlumno(Alumno alumno){
        String sql = "UPDATE alumno SET dni = ?. apellido ? , fechaNac = ? WHERE idAlumno = ?";
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, alumno.getDni());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getNombre());
            ps.setDate(4, Date.valueOf(alumno.getFechaNac()));
            ps.setBoolean(5,alumno.isActivo());
            int exito = ps.executeUpdate();
            
            if(exito == 1){
                JOptionPane.showMessageDialog(null, "Modificado exitosamente.");
            }else{
                JOptionPane.showMessageDialog(null,"El alumno no existe");
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Error al acceder a la tabla Alumno "+ex.getMessage());
        }
    }
    public void eliminarAlumno(int id){
        try{
            String sql = "UPDATE alumno SET activo = 0 WHERE idAlumno = ? ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int fila = ps.executeUpdate();
            
            if(fila==1){
                JOptionPane.showMessageDialog(null," Se eliminó el alumno.");
            }
            ps.close();
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Error al acceder a 'Alumno'.");
        }
    }
}
