package pl.javastart.internet_bakery;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class ShoppingCart {
    private List<Product> listaProduktow = new ArrayList<>();

    public void add(Product product) {
        listaProduktow.add(product);
    }
    public List<Product> ListaProduktow(){
        return listaProduktow;
    }

}


