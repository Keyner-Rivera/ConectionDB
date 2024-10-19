package conectiondb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/bdzoma";
    private static final String USER = "root";
    private static final String PASSWORD = "0000";

    public static Connection conectar() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            //System.out.println("Conexión exitosa a la base de datos");
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
        return conexion;
    }// fin conectar()
    
    public static void insertarProducto() {
        Scanner entrada = new Scanner(System.in);
        String codigo, nombre, fecha;
        double precio;
        int cantidad;
        System.out.println("Ingrese el código del producto: ");
        codigo = entrada.nextLine();
        System.out.println("Ingrese el nombre del producto: ");
        nombre = entrada.nextLine();
        System.out.println("Ingrese el precio del producto: ");
        precio = entrada.nextDouble();
        System.out.println("Ingrese la cantidad del producto: ");
        cantidad = entrada.nextInt();
        System.out.println("Ingrese la fecha del producto: ");
        fecha = entrada.nextLine();
        
    String query = "INSERT INTO producto (codigoProducto, nombreProducto, precioUnitario, cantidadProducto, fechaVencimiento) VALUES (?,?, ?, ?, ?)";
    try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(query)) {
        pst.setString(1, codigo);
        pst.setString(2, nombre);
        pst.setDouble(3, precio);
        pst.setInt(4, cantidad);
        pst.setDate(5, java.sql.Date.valueOf(fecha));
        pst.executeUpdate();
        System.out.println("Producto ingresado correctamente");
    } catch (SQLException e) {
    }
}// fin insertarProducto()
public static void listarProductos() {
    String query = "SELECT * FROM producto";
    try (Connection con = Conexion.conectar(); 
         Statement st = con.createStatement(); 
         ResultSet rs = st.executeQuery(query)) {
        
        boolean hayResultados = false;
        while (rs.next()) {
            hayResultados = true;
            System.out.println("\n---------------------------");
            System.out.println("Codigo: " + rs.getString("codigoProducto"));
            System.out.println("Nombre: " + rs.getString("nombreProducto"));
            System.out.println("Precio: " + rs.getDouble("precioUnitario"));
            System.out.println("Cantidad: " + rs.getInt("cantidadProducto"));
            System.out.println("Fecha de vencimiento: " + rs.getDate("fechaVencimiento"));
            System.out.println("---------------------------\n");
        }

        if (!hayResultados) {
            System.out.println("No se encontraron productos.");
        }
    } catch (SQLException e) {
        System.out.println("Error al listar productos: " + e.getMessage());
    }
}// fin listarProductos()
    
    public static void actualizarProducto() {
        Scanner ent = new Scanner(System.in); 
        String codigoProducto, nombre;
        double precio;
        System.out.println("Ingrese el codigo del producto a modificar: ");
        codigoProducto = ent.nextLine();
        System.out.println("Ingrese el nuevo nombre del producto: ");
        nombre = ent.nextLine();
        System.out.println("Ingrese el nuevo precio del producto: ");
        precio = ent.nextInt();
        
    String query = "UPDATE producto SET nombreProducto = ?, precioUnitario = ? WHERE codigoProducto = ?";
    try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(query)) {
        pst.setString(1, nombre);
        pst.setDouble(2, precio);
        pst.setString(3, codigoProducto);
        pst.executeUpdate();
        System.out.println("Producto actualizado correctamente");
    } catch (SQLException e) {
    }
}// fin actualizarProducto
    
public static void eliminarProducto() {
    Scanner en = new Scanner(System.in);
    String  codigoProducto;
    
    System.out.println("Ingrese el codigo del producto a eliminar: ");
    codigoProducto = en.nextLine();
    
    String query = "DELETE FROM producto WHERE codigoProducto = ?";
    try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(query)) {
        pst.setString(1, codigoProducto);
        pst.executeUpdate();
        System.out.println("Producto eliminado correctamente");
    } catch (SQLException e) {
        //e.printStackTrace();
    }
}// fin eliminarProducto

public static void buscarProducto() {
    Scanner entra = new Scanner(System.in);
    String codigoProducto;

    System.out.println("\nIngrese el codigo del producto que desea consultar: ");
    codigoProducto = entra.nextLine();

    String query = "SELECT * FROM producto WHERE codigoProducto = ?";
    
    try (Connection con = Conexion.conectar(); 
         PreparedStatement pst = con.prepareStatement(query)) {
        
        // Asignar el valor al parámetro de la consulta
        pst.setString(1, codigoProducto);
        
        // Ejecutar la consulta
        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {  // Verificar si hay resultados
                System.out.println("\n---------------------------");
                System.out.println("Codigo: " + rs.getString("codigoProducto"));
                System.out.println("Nombre: " + rs.getString("nombreProducto"));
                System.out.println("Precio: " + rs.getDouble("precioUnitario"));
                System.out.println("Cantidad: " + rs.getInt("cantidadProducto"));
                System.out.println("Fecha de vencimiento: " + rs.getDate("fechaVencimiento"));
                System.out.println("---------------------------\n");
            } else {
                System.out.println("\nNo se encontro el producto con el codigo: " + codigoProducto +"\n");
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al buscar el producto: " + e.getMessage());
    }
}

    public static void main(String[] args) {
        Scanner enter = new Scanner(System.in);
        int opc;
        do{
        System.out.println("****************************** \n" + "******  Menu Principal  ******\n" +
                "    1....Ingresar Producto \n" +
                "    2.....Mostrar Productos \n" +
                "    3......Buscar Producto \n" +
                "    4...Modificar Producto \n" +
                "    5....Eliminar Producto  \n" + 
                "    6................Salir \n"+
                "\nSeleccione una opcion del menu:");
        opc = enter.nextInt();
        switch(opc){
            case 1:
                insertarProducto();
                break;
            case 2:
                listarProductos();
                break;
            case 3:
                buscarProducto();
                break;
            case 4:
                actualizarProducto();
                break;
            case 5:
                eliminarProducto();
                break;
            case 6:
                break;
            default:
                System.out.println("\nIngrese una opcion valida\n");
                break;
        }
        }while(opc!=6);
    }
}// fin main

