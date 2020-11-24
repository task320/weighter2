package weighter;

import java.util.Date;
import java.util.Formatter;

import io.micronaut.runtime.Micronaut;

import weighter.util.Log4jLogger;

public class Application {

    public static void main(String[] args) {
        Date nowTime = new Date();
        try(Formatter fm = new Formatter()){
            fm.format("[%s]Application Start",nowTime.toString());
        }


        Micronaut.run(Application.class);
    }
}
