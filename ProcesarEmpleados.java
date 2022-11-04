import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProcesarEmpleados {
    static List<Empleado> empleados;

    public static void main(String[] args) throws IOException{
        cargarArchivo();
        mostrarEmpleadosgananmas();
        primerempleadoquegana();
        empleadospordepartamento();
        sumadesalariosreducestream();
        promediodesalariosdoublestream();


    }



    public static void cargarArchivo() throws IOException {
        Pattern pattern = Pattern.compile(";");
        String filename = "empleado.csv";
        List<Empleado>empleados;
        try(Stream<String> lines = Files.lines(Path.of(filename))) {

            empleados = lines.map(line -> {
                String[] arr = pattern.split(line);
                return new Empleado(arr[0], arr[1], arr[2], Double.parseDouble(arr[3]), arr [4]);
            }).collect(Collectors.toList());
            empleados.forEach(System.out::println);
        }
    }

    // Procesamiento de flujos de objetos Empleado.

    //public static void main(String[] args) {
        // inicializa arreglo de objetos Empleado
        //Empleado[] empleados = {
                //new Empleado("187","Jason", "Red", 5000, "TI"),
                //ew Empleado("200","Ashley", "Green", 7600, "TI"),
                //new Empleado("486","Matthew", "Indigo", 3587.5, "Ventas"),
                //new Empleado("359","James", "Indigo", 4700.77, "Marketing"),
                //new Empleado("837","Luke", "Indigo", 6200, "TI"),
                //new Empleado("365","Jason", "Blue", 3200, "Ventas"),
                //new Empleado("276","Wendy", "Brown", 4236.4, "Marketing")};

        // obtiene vista List de los objetos Empleado
        //List<Empleado> lista = Arrays.asList(empleados);
        // muestra todos los objetos Empleado
        //System.out.println("Lista completa de empleados:");
        //lista.stream().forEach(System.out::println);

        // Predicado que devuelve true para salarios en el rango $4000-$6000
        static Predicate<Empleado> cuatroASeisMil =
                e -> (e.getSalario() >= 4000 && e.getSalario() <= 6000);

    static void mostrarEmpleadosgananmas(){
        // Muestra los empleados con salarios en el rango $4000-$6000
        // en orden ascendente por salario
        System.out.printf(
                "%nEmpleados que ganan $4000-$6000 mensuales ordenados por salario:%n");
        empleados.stream()
                .filter(cuatroASeisMil)
                .sorted(Comparator.comparing(Empleado::getSalario))
                .forEach(System.out::println);
    }

    static void primerempleadoquegana(){
        
        System.out.printf("%nPrimer empleado que gana $4000-$6000:%n%s%n",
                empleados.stream()
                        .filter(cuatroASeisMil)
                        .findFirst()
                        .get());
    }
        // Funciones para obtener primer nombre y apellido de un Empleado
        Function<Empleado, String> porPrimerNombre = Empleado::getPrimerNombre;
        Function<Empleado, String> porApellidoPaterno = Empleado::getApellidoPaterno;

        // Comparator para comparar empleados por primer nombre y luego por apellido paterno
        Comparator<Empleado> apellidoLuegoNombre =
                Comparator.comparing(porApellidoPaterno).thenComparing(porPrimerNombre);


    static void empleadospordepartamento(){



        // agrupa empleados por departamento
        System.out.printf("%nEmpleados por departamento:%n");
        Map<String, List<Empleado>> agrupadoPorDepartamento =
                empleados.stream()
                        .collect(Collectors.groupingBy(Empleado::getDepartamento));
        agrupadoPorDepartamento.forEach(
                (departamento, empleadosEnDepartamento) ->
                {
                    System.out.println(departamento);
                    empleadosEnDepartamento.forEach(
                            empleado -> System.out.printf(" %s%n", empleado));
                }
        );
    }
