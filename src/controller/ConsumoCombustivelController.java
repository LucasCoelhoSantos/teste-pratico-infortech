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
		
		consumoCombustivelView.actionListenerAbastecer(new AbastecerListener());
		consumoCombustivelView.actionListenerPercorrer(new PercorrerListener());
		
		consumoCombustivelView.actionListenerVisualizar(new VisualizarListener());
		consumoCombustivelView.actionListenerInsere(new InsereListener());
		consumoCombustivelView.actionListenerEditar(new EditarListener());
		consumoCombustivelView.actionListenerDeletar(new DeletarListener());
		
		consumoCombustivelView.executarView();
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
			ConsumoCombustivel consumo = consumoCombustivelView.obterEditarConsumo();
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
		        consumoCombustivelView.mostrarMensagem("Abastecido com sucesso!");
			}
		}
	}
	
	public class PercorrerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ConsumoCombustivel consumo = consumoCombustivelView.obterEditarConsumo();
			double distancia = consumoCombustivelView.getDistanciaPercorrer();
			double litrosUtilizados = consumo.rodar(distancia);
			double combustivelDisponivel = consumo.contar();
            
            if (distancia == -1.0) {
            	consumoCombustivelView.mostrarMensagem("Valor inválido!");
            }
            else if (litrosUtilizados > combustivelDisponivel) {
            	consumoCombustivelView.mostrarMensagem("Não há combustível suficiente para percorrer a distância.");
            } else {
            	consumo.setCombustivelDisponivel(combustivelDisponivel - litrosUtilizados);
            	consumoCombustivelDAO.update(consumo);
            	consumoCombustivelView.mostrarMensagem("Percorreu a distância com sucesso! Litros utilizados: " + litrosUtilizados);
            	consumoCombustivelView.limparCampos();
            	consumoCombustivelView.setEnableBotoes(false);
            }
		}
	}
	
	public class VisualizarListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
	    	atualizarListaConsumoCombustivel();
	    	
	    	consumoCombustivelView.limparCampos();
	    	consumoCombustivelView.setEnableBotoes(false);
	    	consumoCombustivelView.mostrarMensagem("Busca concluída!");
		}
	}
	
	public class InsereListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ConsumoCombustivel consumo = consumoCombustivelView.obterInserirConsumo();
			System.out.println(consumo.getNumeroDeSerie());
			System.out.println(consumo.getCapacidade());
			System.out.println(consumo.getPortador());
			System.out.println(consumo.contar());
			System.out.println(consumo.getCombustivelDisponivel());
	    	consumoCombustivelDAO.inserir(consumo);
	    	
	    	consumoCombustivelView.limparCampos();
	    	consumoCombustivelView.setEnableBotoes(false);
	    	consumoCombustivelView.mostrarMensagem("Adicionado com sucesso!");
		}
	}
	
	public class EditarListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
	        int numeroDeSerie = consumoCombustivelView.getNumeroDeSerieSelecionado();
	        ConsumoCombustivel consumoAtualizado = consumoCombustivelView.obterEditarConsumo();
	        consumoAtualizado.setNumeroDeSerie(numeroDeSerie);

	        consumoCombustivelDAO.update(consumoAtualizado);
	        
	        consumoCombustivelView.limparCampos();
	        consumoCombustivelView.setEnableBotoes(false);
	        consumoCombustivelView.mostrarMensagem("Editado com sucesso!");
		}
	}
	
	public class DeletarListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int numeroDeSerie = consumoCombustivelView.getNumeroDeSerieSelecionado();
			consumoCombustivelDAO.delete(numeroDeSerie);
			
			consumoCombustivelView.limparCampos();
			consumoCombustivelView.setEnableBotoes(false);
			consumoCombustivelView.mostrarMensagem("Excluído com sucesso!");
		}
	}
}
