package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import model.Acuracia;
import model.Arvore;
import model.Atributo;
import model.DataSet;
import model.DivisorBase;
import model.Instancia;
import model.ProcessadorArquivo;
import view.TelaArvore;
import view.TelaPrincipal;

public class ControllerTelaPrincipal implements ActionListener{

	private TelaPrincipal telaPrincipal;
	private DataSet dataSet;
	private Arvore arvore;
		
	public ControllerTelaPrincipal(TelaPrincipal telaPrincipal) {
		this.telaPrincipal = telaPrincipal;
		this.telaPrincipal.getBtnBuscar().addActionListener(this);
		this.telaPrincipal.getBtnTestar().addActionListener(this);
		this.telaPrincipal.getBtnArvore().addActionListener(this);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.telaPrincipal.getBtnBuscar()) {
			String caminho = this.telaPrincipal.abrirSelecionadorDeArquivo();
			
			if(caminho != null) {
				ProcessadorArquivo processadorArquivo = new ProcessadorArquivo();
	            processadorArquivo.setArquivo(caminho);
	            
	            dataSet = null;
	            
	            try {
					dataSet = processadorArquivo.getRegistros();
					this.telaPrincipal.getTextField().setText(caminho);		            
				} catch (Exception except) {
					this.telaPrincipal.exibirMensagemErro("Arquivo incompatível!");
					return;
				}
	            	            
	            Collections.shuffle(dataSet.getRegistros());            
	            
	            this.telaPrincipal.reiniciarPanelTeste();
	            this.telaPrincipal.getTextAreaResultado().setText("");
	            
	            ArrayList<String []> lista = dataSet.getQuantidadeInstanciasPorClasse();
	            
	            ArrayList<Atributo> atributos = dataSet.getAtributos();
	            
	            for(int i = 0; i < atributos.size() - 1; i++) {
	            	this.telaPrincipal.exibirAtributos(atributos.get(i).getNome());
	            	this.telaPrincipal.exibirComboAtributos(atributos.get(i).getValores());
	            	this.telaPrincipal.repaint();
	            }
	            
	            this.telaPrincipal.reiniciarPanelDataSet();
	            this.telaPrincipal.exibirClasses(lista);
	            
	            String atributoClasse = dataSet.getRegistroAt(0).getAtributos().get(dataSet.getRegistroAt(0).getAtributos().size()-1);
	            
	            dataSet.setAtributoDeClasse(atributoClasse);	            
			}
            
		}
		
		if(e.getSource() == this.telaPrincipal.getBtnTestar()) {
			if(dataSet != null) {
				arvore = new Arvore();
				
				
				try {
					arvore.construir(dataSet);
					Instancia instanciaTeste = new Instancia();
				    
				    for(int i = 0; i < this.telaPrincipal.getAtributos().size(); i++) {
				    	instanciaTeste.add(this.telaPrincipal.getAtributo(i), this.telaPrincipal.getValorSelecionadoComboBox(i));
				    }
				    
				    this.telaPrincipal.getTextAreaResultado().setText("Classificação: "+ arvore.predict(instanciaTeste));
				} catch (Exception e2) {
					this.telaPrincipal.exibirMensagemErro("Arquivo incompatível!");
				}
			    
			    
			    
			    
//			    DivisorBase divisorBase = new DivisorBase(dataSet, 70);
//			    double acuracia [] = Acuracia.calcular(divisorBase.baseTeste(), arvore);
//				
//			    System.out.println("\n\nCálculo\n");
//			    System.out.println("Acertos: "+acuracia[0]);
//			    System.out.println("Erros: "+acuracia[1]);
//			    System.out.println("Acurácia: "+acuracia[2]);
			    
			    			    
			    
				
			}else {
				this.telaPrincipal.exibirMensagemErro("Nenhuma base foi selecionada!");
			}
		}
		
		if(e.getSource() == this.telaPrincipal.getBtnArvore()) {
			if(dataSet != null) {
				try {
					arvore = new Arvore();
				    arvore.construir(dataSet);
				    new TelaArvore(arvore);
				} catch (Exception e2) {
					this.telaPrincipal.exibirMensagemErro("Arquivo incompatível!");
				}
				
			}
		}
		
	}

}
