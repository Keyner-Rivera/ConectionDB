package conectiondb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ZomaSystem {
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
        System.out.println( "\n***************************************\n"+"********  Ingreso de Producto  ********\n" + "***************************************\n");
        System.out.println("Ingrese el codigo del producto: ");
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
    try (Connection con = ZomaSystem.conectar(); PreparedStatement pst = con.prepareStatement(query)) {
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
    try (Connection con = ZomaSystem.conectar(); 
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
        System.out.println( "\n*********************************************\n"+"********  Actualizacion de Producto  ********\n" + "*********************************************\n");
        System.out.println("Ingrese el codigo del producto a modificar: ");
        codigoProducto = ent.nextLine();
        System.out.println("Ingrese el nuevo nombre del producto: ");
        nombre = ent.nextLine();
        System.out.println("Ingrese el nuevo precio del producto: ");
        precio = ent.nextInt();
        
    String query = "UPDATE producto SET nombreProducto = ?, precioUnitario = ? WHERE codigoProducto = ?";
    try (Connection con = ZomaSystem.conectar(); PreparedStatement pst = con.prepareStatement(query)) {
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
    
    System.out.println( "\n*************************************\n"+"********  Elminiar Producto  ********\n" + "*************************************\n");
    System.out.println("Ingrese el codigo del producto a eliminar: ");
    codigoProducto = en.nextLine();
    
    String query = "DELETE FROM producto WHERE codigoProducto = ?";
    try (Connection con = ZomaSystem.conectar(); PreparedStatement pst = con.prepareStatement(query)) {
        pst.setString(1, codigoProducto);
        pst.executeUpdate();
        System.out.println("Producto eliminado correctamente");
    } catch (SQLException e) {
        //e.printStackTrace();
    }
}// fin eliminarProducto

public static void buscarCodProducto() {
    Scanner entra = new Scanner(System.in);
    String codigoProducto;

    System.out.println( "\n*****************************************\n"+"*********  Busqueda de Producto  ********\n" + "*****************************************\n");
    System.out.println("\nIngrese el codigo del producto que desea consultar: ");
    codigoProducto = entra.nextLine();

    String query = "SELECT * FROM producto WHERE codigoProducto = ?";
    
    try (Connection con = ZomaSystem.conectar(); 
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

public static void buscarNomProducto() {
    Scanner entra = new Scanner(System.in);
    String nombreProducto;

    System.out.println( "\n*****************************************\n"+"*********  Busqueda de Producto  ********\n" + "*****************************************\n");
    System.out.println("\nIngrese el nombre del producto que desea consultar: ");
    nombreProducto = entra.nextLine();

    String query = "SELECT * FROM producto WHERE nombreProducto = ?";
    
    try (Connection con = ZomaSystem.conectar(); 
         PreparedStatement pst = con.prepareStatement(query)) {
        
        // Asignar el valor al parámetro de la consulta
        pst.setString(1, nombreProducto);
        
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
                System.out.println("\nNo se encontro el producto con el codigo: " + nombreProducto +"\n");
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al buscar el producto: " + e.getMessage());
    }
}

public static void transaccion() {
    Scanner entra = new Scanner(System.in);
    String nombreProducto;
    int cantidadProducto;

    System.out.println( "\n************************************\n"+"***********  Transaccion  **********\n" + "************************************\n");
    System.out.println("\nIngrese el nombre del producto que desea adquirir: ");
    nombreProducto = entra.nextLine();

    String querySelect = "SELECT * FROM producto WHERE nombreProducto = ?";
    String queryUpdate = "UPDATE producto SET cantidadProducto = ? WHERE nombreProducto = ?";

    try (Connection con = ZomaSystem.conectar(); 
         PreparedStatement pstSelect = con.prepareStatement(querySelect)) {

        // Asignar el valor al parámetro de la consulta SELECT
        pstSelect.setString(1, nombreProducto);

        // Ejecutar la consulta SELECT
        try (ResultSet rs = pstSelect.executeQuery()) {
            if (rs.next()) {  // Verificar si hay resultados
                int cantidadActual = rs.getInt("cantidadProducto");

                System.out.println("\nHay " + cantidadActual + " unidades del producto " + nombreProducto + ", cuantas unidades desea comprar?");
                cantidadProducto = entra.nextInt();

                // Verificar si hay suficiente inventario
                if (cantidadProducto <= cantidadActual) {
                    int nuevaCantidad = cantidadActual - cantidadProducto;

                    // Crear nuevo PreparedStatement para la consulta UPDATE
                    try (PreparedStatement pstUpdate = con.prepareStatement(queryUpdate)) {
                        pstUpdate.setInt(1, nuevaCantidad);
                        pstUpdate.setString(2, nombreProducto);
                        pstUpdate.executeUpdate();
                        
                        System.out.println("\nProducto ordenado correctamente. Quedan " + nuevaCantidad + " unidades en inventario.\n");
                    }
                } else {
                    System.out.println("\nNo hay suficiente inventario. Solo hay " + cantidadActual + " unidades disponibles.\n");
                }
            } else {
                System.out.println("\nNo se encontró el producto con el nombre: " + nombreProducto + "\n");
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al realizar la transacción: " + e.getMessage());
    }
}

public static void reporteria() {
    String query = "SELECT * FROM producto";
    try (Connection con = ZomaSystem.conectar(); 
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
}

    public static void main(String[] args) {
        Scanner enter = new Scanner(System.in);
        int opc;
        do{
        System.out.println("********************************** \n" + "*******  Zoma Coffe Shop  ********\n"+"**********************************\n"+
                "********  Menu Principal  ********\n" + "**********************************\n\n" +
                "   1...........Ingresar Producto \n" +
                "   2...........Mostrar Productos \n" +
                "   3.........Actualizar Producto \n" +
                "   4...........Eliminar Producto  \n" + 
                "   5..Buscar Producto por Codigo \n" +
                "   6..Buscar Producto por Nombre \n" +
                "   7.....................Compras \n" +
                "   8..................Reporteria \n" +
                "   9................-......Salir \n"+
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
                actualizarProducto();
                break;
            case 4:
                eliminarProducto();
                break;
            case 5:
                buscarCodProducto();
                break;
            case 6:
                buscarNomProducto();
                break;
            case 7:
                transaccion();
                break;
            case 8:
                reporteria();
                break;
            case 9:
                break;
            default:
                System.out.println("\nIngrese una opcion valida\n");
                break;
        }
        }while(opc!=9);
    }
}// fin main
