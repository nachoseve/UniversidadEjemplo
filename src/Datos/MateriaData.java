
package Datos;

import Entidades.Materia;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class MateriaData {
    private Connection con = null;
    
    public void guardarMateria(Materia materia){
               String sql = "INSERT INTO materia ( nombre, año, estado) VALUES (?,?,?)";
        try{
           PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
           ps.setString(1, materia.getNombre());
           ps.setInt(2, materia.getAnioMateria());
           ps.setBoolean(3, materia.isActivo());
           ps.executeUpdate();
           
          ResultSet rs = ps.getGeneratedKeys();
          if(rs.next()){
              materia.setIdMateria(rs.getInt("idMateria"));
          }else{
              System.out.println("No se pudo obtener ID");
        }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al acceder a Materia"+ex.getMessage());
    } 

    }
        public Materia buscarMateria(int id){
        Materia materia = null;
        String sql = "SELECT nombre, año, activo FROM materia WHERE idMateria = ? AND estado = 1";
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
            materia = new  Materia();
            materia.setIdMateria(id);
            materia.setNombre(rs.getString("nombre"));
            materia.setAnioMateria(rs.getInt("anioMateria"));
            materia.setActivo(true);
            
        }else {
                JOptionPane.showMessageDialog(null, "No existe el materia");
                }
        ps.close();
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Materia "+ex.getMessage());
        }
        return materia;
    }
    public void modificarMateria(Materia materia){
        String sql = "UPDATE materia SET  nombre = ?, anioMateria ? , activo = ? WHERE idMateria = ?";
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, materia.getNombre());
            ps.setInt(2, materia.getAnioMateria());
            ps.setBoolean(3,materia.isActivo());
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
    public void eliminarMateria(int id){
        try{
            String sql = "UPDATE materia SET activo = 0 WHERE idAlumno = ? ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int fila = ps.executeUpdate();
            
            if(fila==1){
                JOptionPane.showMessageDialog(null," Se eliminó la materia.");
            }
            ps.close();
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Error al acceder a 'Materia'.");
        }
    }
        public List<Materia> listarMateria(){
        List<Materia> materias = new ArrayList<>();
        try{
            String sql = "SELECT * FROM materia where activo = 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
               Materia materia = new Materia();
               materia.setIdMateria(rs.getInt("idMateria"));
               materia.setNombre(rs.getString("nombre"));
               materia.setAnioMateria(rs.getInt("anioMateria"));
               materia.setActivo(rs.getBoolean("activo"));
               materias.add(materia);                       
                
            }
            ps.close();
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Alumno "+ex.getMessage());
        }
        return materias;
    }        
}
