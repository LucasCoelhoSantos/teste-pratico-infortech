package view;

import model.ConsumoCombustivel;
import controller.ConsumoCombustivelController;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

public class ConsumoCombustivelView extends JFrame {
	private ConsumoCombustivelController consumoCombustivelcontroller;
	
	private int numeroDeSerieSelecionado;
	private JTable tabela;
	private DefaultTableModel modeloTabela;
	
	private JTextField capacidadeTextField;
	private JTextField portadorTextField;
	private JTextField combustivelDisponivelTextField;
	private JTextField quantidadeCombustivelTextField;
	private JButton abastecerButton;
	private JTextField distanciaTextField;
	private JButton percorrerDistanciaButton;
	
	private JButton adicionarButton;
	private JButton visualizarButton;
	private JButton editarButton;
	private JButton deletarButton;
	
	public ConsumoCombustivelView() {
		// Configurações da janela
		setTitle("Consumo Combustível");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
		setLocationRelativeTo(null);
		
		// Criação da tabela
		String[] colunas = {"Número de Série", "Capacidade", "Portador", "Combustível Disponível"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modeloTabela);
        
        // Panel tabela
        JPanel tabelaPanel = new JPanel(new BorderLayout());
        tabelaPanel.add(new JScrollPane(tabela), BorderLayout.CENTER);
		
		// Panel dados
        JPanel dadosPanel = new JPanel(new BorderLayout());
        
        JPanel camposPanel = new JPanel(new GridLayout(3, 2));
        capacidadeTextField = new JTextField();
        portadorTextField = new JTextField();
        combustivelDisponivelTextField = new JTextField();
        combustivelDisponivelTextField.setEditable(false);
        quantidadeCombustivelTextField = new JTextField();
        distanciaTextField = new JTextField();
        abastecerButton = new JButton("Abastecer");
        percorrerDistanciaButton = new JButton("Percorrer");
        
        camposPanel.add(new JLabel("Capacidade (Litros):"));
        camposPanel.add(capacidadeTextField);
        camposPanel.add(new JLabel("Portador:"));
        camposPanel.add(portadorTextField);
        camposPanel.add(new JLabel("Combustivel Disponivel (Litros):"));
        camposPanel.add(combustivelDisponivelTextField);
        
        camposPanel.add(new JLabel("Abastecer Carro (Litros):"));
        JPanel quantidadeCombustivel = new JPanel(new GridLayout(1, 2));
        quantidadeCombustivel.add(quantidadeCombustivelTextField);
        quantidadeCombustivel.add(abastecerButton);
        camposPanel.add(quantidadeCombustivel);
        
        camposPanel.add(new JLabel("Percorrer Distancia (Km):"));
        JPanel distanciaPanel = new JPanel(new GridLayout(1, 2));
        distanciaPanel.add(distanciaTextField);
        distanciaPanel.add(percorrerDistanciaButton);
        camposPanel.add(distanciaPanel);
        
        dadosPanel.add(camposPanel, BorderLayout.CENTER);
        
        // Panel CRUD
        JPanel crudPanel = new JPanel();
        visualizarButton = new JButton("Recarregar");
        crudPanel.add(visualizarButton);
        adicionarButton = new JButton("Adicionar");
        crudPanel.add(adicionarButton);
        editarButton = new JButton("Editar");
        crudPanel.add(editarButton);
        deletarButton = new JButton("Deletar");
        crudPanel.add(deletarButton);

        // Painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setSize(1280, 720);
        painelPrincipal.add(tabelaPanel, BorderLayout.NORTH);
        painelPrincipal.add(dadosPanel, BorderLayout.CENTER);
        painelPrincipal.add(crudPanel, BorderLayout.SOUTH);
        
        add(painelPrincipal);
        
        setEnableBotoes(false);
        
        tabela.getSelectionModel().addListSelectionListener(e -> {
            Object[] rowData = getSelectedRowData();
            if (rowData != null) {
                capacidadeTextField.setText(rowData[1].toString());
                portadorTextField.setText(rowData[2].toString());
                combustivelDisponivelTextField.setText(rowData[3].toString());
                
                setEnableBotoes(true);
            }
        });
	}
	
    public void limparCampos() {
        capacidadeTextField.setText("");
        portadorTextField.setText("");
        combustivelDisponivelTextField.setText("");
        quantidadeCombustivelTextField.setText("");
        distanciaTextField.setText("");
    }
    
    public void setEnableBotoes(boolean trueOrFalse) {
        abastecerButton.setEnabled(trueOrFalse);
        percorrerDistanciaButton.setEnabled(trueOrFalse);
        adicionarButton.setEnabled(!trueOrFalse);
        editarButton.setEnabled(trueOrFalse);
        deletarButton.setEnabled(trueOrFalse);
        quantidadeCombustivelTextField.setEnabled(trueOrFalse);
        distanciaTextField.setEnabled(trueOrFalse);
    }
    
    public void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }
    
    public Object[] getSelectedRowData() {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow != -1) {
        	numeroDeSerieSelecionado = (int) modeloTabela.getValueAt(selectedRow, 0);
            int columnCount = modeloTabela.getColumnCount();
            Object[] rowData = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                rowData[i] = modeloTabela.getValueAt(selectedRow, i);
            }
            return rowData;
        } else {
        	setEnableBotoes(false);
        }
        return null;
    }
	
    public int getNumeroDeSerieSelecionado() {
        return numeroDeSerieSelecionado;
    }
    
    public ConsumoCombustivel obterInserirConsumo() {
    	String capacidade = capacidadeTextField.getText();
    	String portador = portadorTextField.getText();
    	return new ConsumoCombustivel(Double.parseDouble(capacidade), portador);
    }
    
    public ConsumoCombustivel obterEditarConsumo() {
    	int numeroDeSerie = getNumeroDeSerieSelecionado();
    	String capacidade = capacidadeTextField.getText();
    	String portador = portadorTextField.getText();
    	String combustivelDisponivel = combustivelDisponivelTextField.getText();
    	return new ConsumoCombustivel(numeroDeSerie, Double.parseDouble(capacidade), portador, Double.parseDouble(combustivelDisponivel));
    }
    
    public double getLitrosAbastecer() {
        String texto = quantidadeCombustivelTextField.getText();
        try {
        	return Double.parseDouble(texto);        	
        } catch (NumberFormatException e) {
        	return -1.0;
        }
    }
    
    public double getDistanciaPercorrer() {
        String distancia = distanciaTextField.getText();
        try {
        	return Double.parseDouble(distancia);
	    } catch (NumberFormatException e) {
	    	return -1.0;
	    }
    }
    
    public void mostrarListaConsumoCombustivel(Object[][] data) {
        DefaultTableModel modeloTabela = (DefaultTableModel) tabela.getModel();
        modeloTabela.setRowCount(0);

        for (Object[] rowData : data) {
            modeloTabela.addRow(rowData);
        }
    }
    
    public void selectionListenerTable(ListSelectionListener listener) {
        tabela.getSelectionModel().addListSelectionListener(listener);
    }
    
    public void actionListenerAbastecer(ActionListener l) {
    	abastecerButton.addActionListener(l);
    }
    
    public void actionListenerPercorrer(ActionListener l) {
    	percorrerDistanciaButton.addActionListener(l);
    }
    
    public void actionListenerVisualizar(ActionListener l) {
    	visualizarButton.addActionListener(l);
    }
    
    public void actionListenerInsere(ActionListener l) {
    	adicionarButton.addActionListener(l);
    }
    
    public void actionListenerEditar(ActionListener l) {
    	editarButton.addActionListener(l);
    }
    
    public void actionListenerDeletar(ActionListener l) {
    	deletarButton.addActionListener(l);
    }
    
    public void executarView() {
    	setVisible(true);
    }
}
