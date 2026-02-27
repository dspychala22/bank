import java.math.BigDecimal;

public class Client {

    //dane klienta
    private String imie;
    private String nazwisko;
    private String id;
    private final String numerKonta;
    private BigDecimal saldo;
    private final String pin;

    public Client(String imieUser, String nazwiskoUser, String idUser, String numerKontaUser, BigDecimal saldoUser, String pinUser) {

        imie = imieUser;
        nazwisko = nazwiskoUser;
        id = idUser;
        numerKonta = numerKontaUser;
        saldo = saldoUser;
        pin = pinUser;

    }

    public String getImie() {
        return imie;
    }
    public void setImie(String imie) {
        this.imie = imie;
    }
    public String getNazwisko() {
        return nazwisko;
    }
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNumerKonta() {
        return numerKonta;
    }
    public BigDecimal getSaldo() {
        return saldo;
    }
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
    public String getPin() {
        return pin;
    }

    public void wplata(BigDecimal kwota) {
        if (kwota == null) return;
        if (kwota.compareTo(BigDecimal.ZERO) <= 0) return;
        saldo = saldo.add(kwota);

    }

    public boolean wyplata(BigDecimal kwota) {
        if (kwota == null) return false;
        if (kwota.compareTo(BigDecimal.ZERO) <= 0) return false;

        if (this.saldo.compareTo(kwota) >= 0) {
            this.saldo = this.saldo.subtract(kwota);
            return true;
        }
        return false;
    }

    public boolean przelewDo(Client odbiorca, BigDecimal kwota) {
        if (odbiorca == null) return false;
        if (this.id.equalsIgnoreCase(odbiorca.id)) return false; // do siebie nie

        if (wyplata(kwota)) {
            odbiorca.wplata(kwota);
            return true;
        }
        return false;
    }
}

