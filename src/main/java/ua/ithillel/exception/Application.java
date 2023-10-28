package ua.ithillel.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import ua.ithillel.exception.client.CurrencyApiClient;
import ua.ithillel.exception.client.CurrencyApiClientDefault;
import ua.ithillel.exception.converter.CurrencyConverter;
import ua.ithillel.exception.converter.CurrencyConverterDefault;
import ua.ithillel.exception.ui.CurrencyConverterUi;

import java.io.*;
import java.net.http.HttpClient;

//class Parent {
//}
//
//class Child extends Parent {
//    public void doSomething() {
//        System.out.println("Doing something");
//    }
//}



public class Application {
    public static void main(String[] args) throws IOException {

        // ui -> converter -> client -> ... -> (web server)

        HttpClient httpClient = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        CurrencyApiClient client = new CurrencyApiClientDefault(httpClient, objectMapper);
        CurrencyConverter converter = new CurrencyConverterDefault(client);

        CurrencyConverterUi currencyConverterUi = new CurrencyConverterUi(converter);


//        String[][] twoDimesionalArr; // 4 x 4




//        String s1 = readFromFileStream("file1.txt");

//        try {
//            String s1 = readFromFileStream("file.txt");
//
//        } catch (FileNotFoundException e) {
//            System.out.println("File doesn't exist. Consider creaating it");
//        } catch (IOException e) {
//            System.out.println("Someone already closed that file");
//        } catch (Exception e) {
//            System.out.println("General exception");
//        }


//        String s = readTextFromFile("file.txt");
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//
//        } catch (ClassNotFoundException e) {
//            System.out.println("Exception:" + e.getMessage());
//
//        } finally {
//            System.out.println("");
//        }

        // unchecked
//        int[] arr = {1, 2, 3};
//        int index = 3;
//        if (index < arr.length) {
//
//        }
//        System.out.println("arr[0] = " +  arr[0]);
//        System.out.println("arr[1] = " +  arr[1]);
//        System.out.println("arr[2] = " +  arr[2]);
//        System.out.println("arr[3] = " +  arr[3]);
//
//
//        Parent p = new Child(); // is-a
//        Child c = (Child) p;
//
//        Parent parent = new Parent();
//        if (parent instanceof Child) {
//            Child parentChild = (Child) parent;
//            parentChild.doSomething();
//        }


//        c.doSomething();
    }

    private static String readTextFromFile(String fileName) {
        try {
            Reader rd = new FileReader(fileName);
            BufferedReader br = new BufferedReader(rd);

            StringBuffer buffer = new StringBuffer();

            br.lines().forEach(buffer::append);

            return buffer.toString();
        } catch (FileNotFoundException e) {
            System.out.println("File not found:" + e.getMessage());

            return "";
        } finally {
//            return ""; // priority 1
        }

    }

    private static String readFromFileStream(String fileName) throws FileNotFoundException, IOException {
        InputStream in = new FileInputStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        StringBuffer buffer = new StringBuffer();

        br.lines().forEach(buffer::append);

        br.close();

        return buffer.toString();
    }
}
