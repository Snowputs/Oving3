import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TraadKlientHaandterer extends Thread {
    private Socket forbindelse;

    TraadKlientHaandterer(Socket socket) {
        forbindelse = socket;
    }

    public void run() {
        try {
            /* Åpner strømmer for kommunikasjon med klientprogrammet */
            InputStreamReader leseforbindelse = new InputStreamReader(forbindelse.getInputStream());
            BufferedReader leseren = new BufferedReader(leseforbindelse);
            PrintWriter skriveren = new PrintWriter(forbindelse.getOutputStream(), true);

            /* Sender innledning til klienten */
            skriveren.println("Hei, du har kontakt med tjenersiden! Her kan du addere eller subtrahere!");
            skriveren.println("Skriv to tall med newlines på slutten, og skriv + eller - på en tredje linje for å si om tall 2 skal legges til eller trekkes fra tall 1");


            /* Mottar data fra klienten */
//            String enLinje = leseren.readLine();  // mottar en linje med tekst
//            while (enLinje != null) {  // forbindelsen på klientsiden er lukket
//                System.out.println("En klient skrev: " + enLinje);
//                if(enLinje.matches("^\\d+[-+]\\d+$")){
//                    skriveren.println("Fra tjeneren: " + parseStringMath(enLinje));
//                }else{
//                    skriveren.println("Dårlig format");
//                }
//
//                //skriveren.println(mat.find());  // sender svar til klienten
//                enLinje = leseren.readLine();
//            }

            String out = doMath(leseren, skriveren);
            while (out != null){
                if(!out.equals("Error")) skriveren.println("Svar: " + out);
                out = doMath(leseren, skriveren);
            }

            /* Lukker forbindelsen */
            leseren.close();
            skriveren.close();
            forbindelse.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String doMath(BufferedReader leseren, PrintWriter skriveren) throws IOException {
        /* Mottar data fra klienten */
        String tall1, tall2, operator;
        int num1, num2;
        try{
            skriveren.println("Tall 1: ");
            tall1 = leseren.readLine();
            num1 = Integer.parseInt(tall1);

            skriveren.println("Tall 2: ");
            tall2 = leseren.readLine();
            num2 = Integer.parseInt(tall2);

            skriveren.println("Operator: ");
            operator = leseren.readLine();

            if(operator.equals("-")){
                return "" + (num1 - num2);
            }else if(operator.equals("+")){
                return "" + (num1 + num2);
            } else {
                skriveren.println("Dårlig format på forrige input");
                return "Error";
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
            skriveren.println("Dårlig format på forrige input");
            return "Error";
        }

    }

    public int parseStringMath(String input){
        String operator = "";
        int ans = 0;
        for(int i=0;i<input.length();i++){
            //String.valueOf(input.charAt(i)).matches("^[-+]$")
            if(input.charAt(i) == "-".charAt(0)|| input.charAt(i) == "+".charAt(0)){
                operator = String.valueOf(input.charAt(i));
            }
        }
        String[] nums = input.split("\\D");
        if(operator.equals("+")){
            ans=Integer.parseInt(nums[0])+Integer.parseInt(nums[1]);
        }else{
            ans=Integer.parseInt(nums[0])-Integer.parseInt(nums[1]);
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("-".charAt(0));
    }
}