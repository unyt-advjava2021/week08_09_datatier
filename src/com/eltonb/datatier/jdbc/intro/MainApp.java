package com.eltonb.datatier.jdbc.intro;

import java.io.IOException;

public class MainApp {

    public static void main(String[] args) {
        try {
            IntroJDBC intro = new IntroJDBC();
            intro.go();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
