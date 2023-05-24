package controller;

import model.ConsumoCombustivel;
import view.ConsumoCombustivelView;
import dao.ConsumoCombustivelDAO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

public class ConsumoCombustivelController {
	private ConsumoCombustivelView consumoCombustivelView = null;
	private ConsumoCombustivelDAO consumoCombustivelDAO = null;
	
	public void executar() {
		System.out.println("Iniciando controlador");
		consumoCombustivelView = new ConsumoCombustivelView();
		consumoCombustivelDAO = new ConsumoCombustivelDAO();
		
		// Cria três carros de portadores quaisquer diferentes (um com capacidade para 50 litros, outro para 55 e o último para 40);
		ConsumoCombustivel consumoCombustivel1 = new ConsumoCombustivel(50.0, "Portador 1");
		consumoCombustivelDAO.inserir(consumoCombustivel1);
	    ConsumoCombustivel consumoCombustivel2 = new ConsumoCombustivel(55.0, "Portador 2");
	    consumoCombustivelDAO.inserir(consumoCombustivel2);
	    ConsumoCombustivel consumoCombustivel3 = new ConsumoCombustivel(40.0, "Portador 3");
	    consumoCombustivelDAO.inserir(consumoCombustivel3);
		
		consumoCombustivelView.actionListenerAbastecer(new AbastecerListener());
		consumoCombustivelView.actionListenerPercorrer(new PercorrerListener());
		
		consumoCombustivelView.actionListenerVisualizar(new VisualizarListener());
		consumoCombustivelView.actionListenerInsere(new InsereListener());
		consumoCombustivelView.actionListenerEditar(new EditarListener());
		consumoCombustivelView.actionListenerDeletar(new DeletarListener());
		
		consumoCombustivelView.executarView();
		atualizarListaConsumoCombustivel();
	}
	
	public void atualizarListaConsumoCombustivel() {
	    ArrayList<ConsumoCombustivel> lista = (ArrayList<ConsumoCombustivel>) consumoCombustivelDAO.read();

	    // Converter os dados para o formato adequado para a view (Object[][])
	    Object[][] data = new Object[lista.size()][4];
	    for (int i = 0; i < lista.size(); i++) {
	        ConsumoCombustivel consumo = lista.get(i);
	        data[i][0] = consumo.getNumeroDeSerie();
	        data[i][1] = consumo.getCapacidade();
	        data[i][2] = consumo.getPortador();
	        data[i][3] = consumo.getCombustivelDisponivel();
	    }
	    consumoCombustivelView.mostrarListaConsumoCombustivel(data);
	}
	
	public class AbastecerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ConsumoCombustivel consumo = new ConsumoCombustivel(consumoCombustivelView.getViewNumeroDeSerieSelecionado(), consumoCombustivelView.getViewCapacidade(), consumoCombustivelView.getViewPortador());
			double litros = consumoCombustivelView.getLitrosAbastecer();
			
			if (litros == -1.0) {
				consumoCombustivelView.mostrarMensagem("Valor inválido!");
			}
			else if (litros <= 0) {
				consumoCombustivelView.mostrarMensagem("Nada foi abastecido!");
			}
			else if (litros > consumo.getCapacidade()) {
				consumoCombustivelView.mostrarMensagem("Não é possível abastecer mais do que a capacidade do tanque!");
			}
			else {
				consumo.abastecer(litros);
		        consumoCombustivelDAO.update(consumo);
		        
		        consumoCombustivelView.limparCampos();
		        consumoCombustivelView.setEnableBotoes(false);
		        atualizarListaConsumoCombustivel();
		        consumoCombustivelView.mostrarMensagem("Abastecido " + litros + " litros com sucesso!");
			}
		}
	}
	
	public class PercorrerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ConsumoCombustivel consumo = new ConsumoCombustivel(consumoCombustivelView.getViewNumeroDeSerieSelecionado(), consumoCombustivelView.getViewCapacidade(), consumoCombustivelView.getViewPortador(), consumoCombustivelView.getViewCombustivelDisponivel());
			double distancia = consumoCombustivelView.getDistanciaPercorrer();
			double litrosUtilizados = consumo.rodar(distancia);
			double combustivelDisponivel = consumo.contar();
            
            if (distancia == -1.0) {
            	consumoCombustivelView.mostrarMensagem("Valor inválido!");
            }
            else if (distancia <= 0) {
            	consumoCombustivelView.mostrarMensagem("Nada foi percorrido!");
            }
            else if (litrosUtilizados > combustivelDisponivel) {
            	consumoCombustivelView.mostrarMensagem("Não há combustível suficiente para percorrer a distância.");
            } else {
            	consumo.setCombustivelDisponivel(combustivelDisponivel - litrosUtilizados);
            	consumoCombustivelDAO.update(consumo);
            	
            	consumoCombustivelView.limparCampos();
            	consumoCombustivelView.setEnableBotoes(false);
            	atualizarListaConsumoCombustivel();
            	consumoCombustivelView.mostrarMensagem("Carro de " + consumo.getPortador() + " percorreu a distância com sucesso! Litros utilizados: " + litrosUtilizados);
            }
		}
	}
	
	public class VisualizarListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {	    	
	    	consumoCombustivelView.limparCampos();
	    	consumoCombustivelView.setEnableBotoes(false);
	    	atualizarListaConsumoCombustivel();
	    	consumoCombustivelView.mostrarMensagem("Tabela recarregada!");
		}
	}
	
	public class InsereListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ConsumoCombustivel consumo = new ConsumoCombustivel(consumoCombustivelView.getViewCapacidade(), consumoCombustivelView.getViewPortador());
			
			if (consumoCombustivelView.getViewCapacidade() == -1.0) {
				consumoCombustivelView.mostrarMensagem("Valor inválido!");
			} else {
		    	consumoCombustivelDAO.inserir(consumo);
		    	
		    	consumoCombustivelView.limparCampos();
		    	consumoCombustivelView.setEnableBotoes(false);
		    	atualizarListaConsumoCombustivel();
		    	consumoCombustivelView.mostrarMensagem("Carro adicionado com sucesso!");
			}
		}
	}
	
	public class EditarListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
	        int numeroDeSerie = consumoCombustivelView.getViewNumeroDeSerieSelecionado();
	        ConsumoCombustivel consumoAtualizado = new ConsumoCombustivel(consumoCombustivelView.getViewNumeroDeSerieSelecionado(), consumoCombustivelView.getViewCapacidade(), consumoCombustivelView.getViewPortador());
	        consumoAtualizado.setNumeroDeSerie(numeroDeSerie);

	        consumoCombustivelDAO.update(consumoAtualizado);
	        
	        consumoCombustivelView.limparCampos();
	        consumoCombustivelView.setEnableBotoes(false);
	        atualizarListaConsumoCombustivel();
	        consumoCombustivelView.mostrarMensagem("Carro editado com sucesso!");
		}
	}
	
	public class DeletarListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int numeroDeSerie = consumoCombustivelView.getViewNumeroDeSerieSelecionado();
			consumoCombustivelDAO.delete(numeroDeSerie);
			
			consumoCombustivelView.limparCampos();
			consumoCombustivelView.setEnableBotoes(false);
			atualizarListaConsumoCombustivel();
			consumoCombustivelView.mostrarMensagem("Carro excluído com sucesso!");
		}
	}
}
