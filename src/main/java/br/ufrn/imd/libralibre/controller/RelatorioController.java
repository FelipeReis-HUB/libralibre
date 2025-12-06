package br.ufrn.imd.libralibre.controller;

import br.ufrn.imd.libralibre.service.BibliotecaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.Map;

public class RelatorioController {

    @FXML private TableView<ItemRelatorio> tabelaRelatorio;
    @FXML private TableColumn<ItemRelatorio, Integer> colunaRank;
    @FXML private TableColumn<ItemRelatorio, String> colunaTitulo;
    @FXML private TableColumn<ItemRelatorio, String> colunaIsbn;
    @FXML private TableColumn<ItemRelatorio, Long> colunaQtd;
    @FXML private Label lblTotal;

    private BibliotecaService service;
    private Stage dialogStage;

    public void setService(BibliotecaService service) {
        this.service = service;
        carregarDados(); // Carrega assim que o serviço é injetado
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void initialize() {
        colunaRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colunaIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colunaQtd.setCellValueFactory(new PropertyValueFactory<>("qtdEmprestimos"));
    }

    private void carregarDados() {
        if (service == null) return;

        // busca os dados
        Map<String, Long> relatorioMap = service.getRelatorioConsolidado();
        ObservableList<ItemRelatorio> listaExibicao = FXCollections.observableArrayList();

        int rank = 1;
        // transforma cada entrada do mapa em um ItemRelatorio
        for (Map.Entry<String, Long> entry : relatorioMap.entrySet()) {
            String isbn = entry.getKey();
            Long qtd = entry.getValue();

            // busca o título para ficar bonito
            String titulo = service.buscarLivroPorIsbn(isbn)
                                   .map(livro -> livro.getTitulo())
                                   .orElse("Desconhecido");

            listaExibicao.add(new ItemRelatorio(rank++, titulo, isbn, qtd));
        }

        // define na tabela
        tabelaRelatorio.setItems(listaExibicao);
        lblTotal.setText("Total de Empréstimos da Biblioteca: " + service.getTotalEmprestimosRegistrados());
    }
}