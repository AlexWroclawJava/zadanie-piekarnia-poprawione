package pl.javastart.internet_bakery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class BakeryController {

    private ShoppingCart shoppingCart;
    private Product product;

    private int licznik_bulek = 0;
    private int licznik_chlebow = 0;
    private double cena_bulki_kajzerki = 1.0;
    private double cena_chleba_pszennego = 3.0;


    @Autowired
    public BakeryController(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @GetMapping("/")
    public String pokazSklep() {
        return "success.html";
    }

    @RequestMapping("/koszyk")
    @ResponseBody
    public String wyswietlKoszyk() {

        int amount_of_products = licznik_bulek + licznik_chlebow;
        String s1 = "<html>Wszystkie dostępne produkty w koszyku: </html>";
        String s2 = "<html><br/><br/>łączna ilość produktów w koszyku: </html>";

        return s1 + shoppingCart.ListaProduktow() + s2 + amount_of_products;

    }

    @RequestMapping("/dodaj")
    public String dodajProdukt(
            @RequestParam( value = "bulkaName",     required = true)  String bulka,
            @RequestParam( value = "bulkaPrice",    required = true)  double cena_bulki,
            @RequestParam( value = "bulkaQuantity", required = false, defaultValue = "0") int ilosc_bulek,
            @RequestParam( value = "chlebName",     required = true)  String chleb,
            @RequestParam( value = "chlebPrice",    required = true)  double cena_chleba,
            @RequestParam( value = "chlebQuantity", required = false, defaultValue = "0") int ilosc_chlebow)
    {
        Product chb = new Product(chleb, cena_chleba, ilosc_chlebow);
        Product blk = new Product(bulka, cena_bulki, ilosc_bulek);

        if (ilosc_bulek < 0) {
            return "error_message.html";
        }

        if (ilosc_chlebow < 0) {
            return "error_message.html";
        }

        if (ilosc_bulek != 0) {
            int i = 0;
            do {
                shoppingCart.add(blk);
                i++;
            } while (i < ilosc_bulek) ;
            licznik_bulek = licznik_bulek + ilosc_bulek;
        }

        if (ilosc_chlebow != 0) {
            int j = 0;
            do {
                shoppingCart.add(chb);
                j++;
            } while (j < ilosc_chlebow) ;
            licznik_chlebow = licznik_chlebow + ilosc_chlebow;
        }

        return "redirect:/success2.html";
    }

    @RequestMapping("/koszyk/paragon")
    @ResponseBody
    public String paragon() {

        double total_suma = Math.round(licznik_bulek * cena_bulki_kajzerki + licznik_chlebow * cena_chleba_pszennego);
        return "Do zapłaty: "  + total_suma + " zł";

    }
}

