import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static void main(String[] args) {

        //Co się będzie wyświetlać jako pierwsze, powitalne menu

        Scanner scanner = new Scanner(System.in);
        String userChoice = " ";

        System.out.println("Witaj w TwójBank");
        System.out.println("Wybierz działanie, które chcesz wykonać");
        System.out.println("1 - Utwórz konto");
        System.out.println("2 - Zaloguj się");
        System.out.println("3 - Wyświetl listę klientów");
        System.out.println("4 - Zamknij program");
        System.out.println("Podaj wybrany numer, żeby przejść dalej");

        //lista klientów
        List<Client> klienci = new ArrayList<>();

        klienci.add(new Client("Anna", "Nowak", "user1", "1001", new BigDecimal("1200.00"), "1001"));
        klienci.add(new Client("Jan", "Kowalski", "user2", "1002", new BigDecimal("50.00"), "1002"));
        klienci.add(new Client("Marta", "Zielińska", "user3", "1003", new BigDecimal("2500.00"), "1003"));
        klienci.add(new Client("Ola", "Wiśniewska", "user4", "1004", new BigDecimal("1000.00"), "1004"));
        klienci.add(new Client("Piotr", "Wójcik", "user5", "1005", new BigDecimal("80000.00"), "1005"));

        int licznikKlientow = klienci.size() + 1;


        //pętla wyboru, jesli user wybierze 4 albo wpisze Zamknij program to działanie się zakończy
        while (!(userChoice.equals("4")
                || userChoice.equalsIgnoreCase("Zamknij program"))) {

            userChoice = scanner.nextLine();
            System.out.printf("Wybrałeś %s.%n", userChoice);

            //formularz i zapis nowego usera
            if (userChoice.equalsIgnoreCase("1")) {
                System.out.println("Wypełnij formularz!");

                System.out.println("Podaj imie: ");
                String imieUser = scanner.nextLine();

                System.out.println("Podaj nazwisko: ");
                String nazwiskoUser = scanner.nextLine();

                String idUser = "User" + String.format("%04d", licznikKlientow);
                String numerKontaUser = "1006" + licznikKlientow;

                System.out.println("Podaj PIN: ");
                String pinUser = scanner.nextLine();

                System.out.println("Podaj saldo początkowe: ");
                BigDecimal saldoUser = scanner.nextBigDecimal();

                Client nowyKlient = new Client(imieUser, nazwiskoUser, idUser, numerKontaUser, saldoUser, pinUser);

                klienci.add(nowyKlient);

                System.out.println("Konto zostało utworzone!");
                System.out.println("Twoje ID: " + idUser);
                System.out.println("Twój numer konta: " + numerKontaUser);
                System.out.println();
                System.out.println("____________________________________________________");
                System.out.println("Jeśli chcesz kontynuować wbierz: ");
                System.out.println("2 - Zaloguj się");
                System.out.println("3 - Wyświetl listę klientów");
                System.out.println("4 - Zamknij program");

                licznikKlientow++;
            }

            //logowanie z pinem
            else if (userChoice.equalsIgnoreCase("2")) {
                System.out.println("Logowanie do Twojego konta osobistego");
                System.out.println();
                System.out.println("_____________________________________________");
                System.out.print("Podaj ID: ");
                String id = scanner.nextLine().trim();

                Client zalogowany = null;

                //sprawdzenie czy ID istnieje czy nie
                for (Client c : klienci) {
                    if (c.getId().equalsIgnoreCase(id)) {
                        zalogowany = c;
                        break;
                    }
                }

                if (zalogowany == null) {
                    System.out.println("Nie ma klienta o takim ID.");
                } else {
                    int proby = 3;

                    //ograniczenie logowania
                    while (proby > 0) {
                        System.out.print("Potwierdź PIN: ");
                        String pin = scanner.nextLine().trim();

                        if (zalogowany.getPin().equals(pin)) {
                            System.out.println("Zalogowano: " + zalogowany.getImie() + " " + zalogowany.getNazwisko());
                            System.out.println("Twoje saldo: " + zalogowany.getSaldo() + " PLN");
                            //opcje po zalogowaniu
                            System.out.println("4 - Zamknij program");
                            System.out.println("5 - Wykonaj przelew");

                            System.out.println("Wybrałeś " + userChoice);
                            userChoice = scanner.nextLine();
                            //wyjście z aplikacji
                            if (!(userChoice.equals("4") || userChoice.equalsIgnoreCase("Zamknij program")));
                            //początek wykonania przelewu
                            if (userChoice.equals("5")){
                                System.out.println("Podaj ID odbiory: ");
                                String idOdbiorcy = scanner.nextLine().trim();

                                Client odbiorca = null;
                                for (Client c : klienci) {
                                    if (c.getId().equalsIgnoreCase(idOdbiorcy)) {
                                        odbiorca = c;
                                        break;
                                    }
                                }

                                if (odbiorca == null) {
                                    System.out.println("Nie znaleziono klienta.");
                                }
                                else if (odbiorca.getId().equalsIgnoreCase(zalogowany.getId())) {
                                    System.out.println("Nie możesz zrobić przelewu do siebie.");
                                }
                                else {
                                    System.out.print("Podaj kwotę przelewu: ");

                                    BigDecimal kwota;

                                    try {
                                        kwota = new BigDecimal(scanner.nextLine());
                                    } catch (NumberFormatException e) {
                                        System.out.println("Nieprawidłowa kwota.");
                                        continue;
                                    }

                                    if (kwota.compareTo(BigDecimal.ZERO) <= 0) {
                                        System.out.println("Kwota musi być większa od 0.");
                                    }
                                    else if (zalogowany.getSaldo().compareTo(kwota) < 0) {
                                        System.out.println("Niewystarczające saldo.");
                                    }
                                    else {
                                        // wykonanie przelewu
                                        zalogowany.setSaldo(zalogowany.getSaldo().subtract(kwota));
                                        odbiorca.setSaldo(odbiorca.getSaldo().add(kwota));

                                        System.out.println("Przelew wykonany!");
                                        System.out.println("Nowe saldo: " + zalogowany.getSaldo() + " PLN");


                                    }
                                }

                            }

                            //break; // wyjście z pętli logowania


                        } else {
                            proby--;
                            System.out.println(" Błędny PIN. Pozostało prób: " + proby);
                        }
                    }

                    if (proby == 0) {
                        System.out.println(" Za dużo prób. Logowanie przerwane.");
                    }
                }
            }

            //lista klientów wyświetlenie
            else if (userChoice.equalsIgnoreCase("3")) {
                System.out.println("Lista klientów");
                for (Client c : klienci) {
                    System.out.println(
                            c.getImie() + " " + c.getNazwisko()
                                    + " | ID: " + c.getId()
                                    + " | Konto: " + c.getNumerKonta()
                                    + " | Saldo: " + c.getSaldo() + " PLN"
                    );
                }

            } else {
                System.out.printf("%s nie jest dostępne na liście. Proszę wybrać prawidłową opcje.%n", userChoice);
            }
        }


        //komunikat na koniec pętli
        System.out.println("Dziękujemy za skorzystanie z naszego banku!");

        scanner.close();
    }
}