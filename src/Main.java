import hash.HashTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        long startTime = System.nanoTime();

        HashTable hashTable1 = new HashTable(1000);
        HashTable hashTable2 = new HashTable(1000);

        String line;
        String splitBy = ",";
        int id = 1;

        try (BufferedReader br = new BufferedReader(new FileReader("bussines.csv"))) {
            while ((line = br.readLine()) != null) {
                String[] business = line.split(splitBy);
                if (business.length >= 5) {
                    String key = business[1];
                    String value = "ID=" + business[0] + ", Address=" + business[2] +
                            ", City=" + business[3] + ", State=" + business[4];

                    long time1 = hashTable1.measurePutTime(key, value, 1);
                    long time2 = hashTable2.measurePutTime(key, value, 2);

                    System.out.println("[" + id + "] Business [ID=" + business[0] + ", Name=" +
                            business[1] + ", Address=" + business[2] + ", City=" +
                            business[3] + ", State=" + business[4] + "]");
                    System.out.println("Tiempo para HashFunction1: " + time1 + " ns");
                    System.out.println("Tiempo para HashFunction2: " + time2 + " ns");
                    id++;
                } else {
                    System.err.println("Saltando línea inválida: " + line);
                }
            }

            Scanner scanner = new Scanner(System.in);
            boolean exit = false;
            while (!exit) {
                System.out.println("\nMenu:");
                System.out.println("1. Buscar por clave");
                System.out.println("2. Mostrar datos por índice");
                System.out.println("3. Salir");
                System.out.print("Selecciona una opción: ");
                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        System.out.print("Introduce la Clave a Buscar: ");
                        String searchKey = scanner.nextLine().trim();

                        String foundValue1 = hashTable1.searchValueByKey(searchKey, 1);
                        String foundValue2 = hashTable2.searchValueByKey(searchKey, 2);

                        if (foundValue1 != null || foundValue2 != null) {
                            if (foundValue1 != null) {
                                System.out.println("Clave '" + searchKey + "' encontrada en Tabla Hash 1. " + foundValue1);
                            }
                            if (foundValue2 != null) {
                                System.out.println("Clave '" + searchKey + "' encontrada en Tabla Hash 2. " + foundValue2);
                            }
                        } else {
                            System.out.println("Clave '" + searchKey + "' no encontrada.");
                        }
                        break;

                    case 2:
                        System.out.print("Introduce el índice para mostrar datos: ");
                        int searchIndex = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Datos en el índice " + searchIndex + " de la Tabla Hash 1:");
                        List<String> data1 = hashTable1.getDataAtIndex(searchIndex);
                        for (String data : data1) {
                            System.out.println(data);
                        }

                        System.out.println("Datos en el índice " + searchIndex + " de la Tabla Hash 2:");
                        List<String> data2 = hashTable2.getDataAtIndex(searchIndex);
                        for (String data : data2) {
                            System.out.println(data);
                        }
                        break;

                    case 3:
                        exit = true;
                        break;

                    default:
                        System.out.println("Opción no válida. Inténtalo de nuevo.");
                        break;
                }
            }
            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
