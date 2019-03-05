import java.io.BufferedReader;
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
            skriveren.println("Hei, du har kontakt med tjenersiden!");
            skriveren.println("Skriv et addisjon eller subtraksjonstykke uten mellomrom eller ekstra spesielle tegn.");
            skriveren.println("eksempler: 1+1, 20-12. Avslutt med linjeskift.");


            /* Mottar data fra klienten */
            String enLinje = leseren.readLine();  // mottar en linje med tekst
            while (enLinje != null) {  // forbindelsen på klientsiden er lukket
                System.out.println("En klient skrev: " + enLinje);
                if(enLinje.matches("^\\d+[-+]\\d+$")){
                    skriveren.println("Fra tjeneren: " + parseStringMath(enLinje));
                }else{
                    skriveren.println("Dårlig format");
                }

                //skriveren.println(mat.find());  // sender svar til klienten
                enLinje = leseren.readLine();
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