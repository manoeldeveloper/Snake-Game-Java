package com.java.game;

import javax.swing.JFrame;

public class Iniciar extends JFrame {
public static void main(String[] args) {
	new Iniciar();
}
   Iniciar() {
	   add(new Tela());
       setTitle("Jogo da Cobrinha");
       setDefaultCloseOperation(EXIT_ON_CLOSE);
       setResizable(false);
       pack();
       setVisible(true);
       setLocationRelativeTo(null);
}
}
