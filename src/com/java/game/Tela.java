package com.java.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Tela extends JPanel implements ActionListener {
	
	private final static int largura_tela = 400;
	private final static int altura_tela = 400;
	private final static int tamanho_bloco = 10;
	private final static int unidade = largura_tela * altura_tela / (tamanho_bloco * tamanho_bloco);
	private int intervalo = 100;
	private final static String fonte = "Arial";
	private final static int[] eixoX = new int[unidade];
	private final static int[] eixoY = new int[unidade];
	private int corpoCobra = 3;
	private int blocosComidos;
	private int blocoX;
	private int blocoY;
	private char direcao = 'D';
	private boolean rodando = false;
	Timer timer;
	Random random;
	
	public Tela() {
		random = new Random();
		setPreferredSize(new Dimension(largura_tela, altura_tela));
		setBackground(Color.darkGray);
		setFocusable(true);
		addKeyListener(new LeitorDeTeclas());
		iniciarJogo();		
}
	
	private void iniciarJogo() {
		criarBloco();
		rodando = true;
		timer = new Timer(intervalo, this);
		timer.start();
	}

	private void criarBloco() {
   
		blocoX = random.nextInt(largura_tela / tamanho_bloco) * tamanho_bloco;
		blocoY = random.nextInt(altura_tela / tamanho_bloco) * tamanho_bloco;
	}

	public void paintComponent(Graphics g) {
		desenharTela(g);
	}

	private void desenharTela(Graphics g) {
     
		if(rodando) {
			g.setColor(Color.red);
			g.fillRect(blocoX, blocoY, tamanho_bloco, tamanho_bloco);
			
			for(int i = 0; i < corpoCobra; i++) {
				if(i == 0) {
					g.setColor(Color.white);
					g.fillOval(eixoX[0], eixoY[0], tamanho_bloco, tamanho_bloco);
			} else {
				g.setColor(Color.white);
				g.fillOval(eixoX[i], eixoY[i], tamanho_bloco, tamanho_bloco);
			}
			}
			g.setColor(Color.white);
			g.setFont(new Font(fonte, Font.BOLD, 20));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Pontos: " + blocosComidos, (largura_tela - metrics.stringWidth("Pontos: " + blocosComidos)) / 2, g.getFont().getSize());
		} else {
			fimDeJogo(g);
		}
	}

	private void fimDeJogo(Graphics g) {
      g.setColor(Color.white);
      g.setFont(new Font(fonte, Font.BOLD, 20));
		FontMetrics fontePontuacao = getFontMetrics(g.getFont());
		 g.drawString("Pontução: " + blocosComidos, (largura_tela - fontePontuacao.stringWidth("Pontuação: " + blocosComidos + "\nPerdeu comédia")), g.getFont().getSize());
		 g.setColor(Color.red);
		 g.setFont(new Font(fonte, Font.BOLD, 20));
			FontMetrics fonteFinal = getFontMetrics(g.getFont());
			 g.drawString("Perdeu comédia", (largura_tela - fonteFinal.stringWidth("Perdeu comédia")) / 3, altura_tela / 3);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(rodando) {
			andar();
			alcancarBloco();
			validarLimite();
		}
		repaint();
	}

	private void validarLimite() {
		//Cabeça no corpo
    for (int i = corpoCobra; i > 0; i--) {
    	if(eixoX[0] == eixoX[i] && eixoY[0] == eixoY[i]) {
    		rodando = false;
    		break;
    	}
    }
    
    //Cabeça tocou nos lados
    if (eixoX[0] < 0 || eixoX[0] > largura_tela ) {
	rodando = false;
    }
    //Cabeça tocou em cima ou em baixo
    if (eixoY[0] < 0 || eixoY[0] > altura_tela ) {
	rodando = false;
    }
    if(!rodando) {
    	timer.stop();
    }
	}

	private void alcancarBloco() {
		if(eixoX[0] == blocoX && eixoY[0] == blocoY) {
			corpoCobra++;
			blocosComidos++;
			intervalo --;
			timer.stop();
			timer.setDelay(intervalo);
			timer.start();
			criarBloco();
		}
	}

	private void andar() {
		for (int i = corpoCobra; i > 0; i--) {
            eixoX[i] = eixoX[i - 1];
            eixoY[i] = eixoY[i - 1];
        }

        switch (direcao) {
            case 'C':
                eixoY[0] = eixoY[0] - tamanho_bloco;
                break;
            case 'B':
                eixoY[0] = eixoY[0] + tamanho_bloco;
                break;
            case 'E':
                eixoX[0] = eixoX[0] - tamanho_bloco;
                break;
            case 'D':
                eixoX[0] = eixoX[0] + tamanho_bloco;
                break;
            default:
                break;
        }
    }
	 public class LeitorDeTeclas extends KeyAdapter {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            switch (e.getKeyCode()) {
	                case KeyEvent.VK_LEFT:
	                    if (direcao != 'D') {
	                        direcao = 'E';
	                    }
	                    break;
	                case KeyEvent.VK_RIGHT:
	                    if (direcao != 'E') {
	                        direcao = 'D';
	                    }
	                    break;
	                case KeyEvent.VK_UP:
	                    if (direcao != 'B') {
	                        direcao = 'C';
	                    }
	                    break;
	                case KeyEvent.VK_DOWN:
	                    if (direcao != 'C') {
	                        direcao = 'B';
	                    }
	                    break;
	                default:
	                    break;
	            }
	        }
	    }
	
	}