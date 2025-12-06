package br.ufrn.imd.libralibre.controller;

import br.ufrn.imd.libralibre.model.Emprestimo;
import br.ufrn.imd.libralibre.service.BibliotecaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ListagemEmprestimosController {

    @FXML private TableView<EmprestimoViewModel> tabelaEmprestimos;
    @FXML private TableColumn<EmprestimoViewModel, String> colunaLivro;
    @FXML private TableColumn<EmprestimoViewModel, String> colunaUsuario;
    @FXML private TableColumn<EmprestimoViewModel, String> colunaDataEmp;
    @FXML private TableColumn<EmprestimoViewModel, String> colunaDataDev;
    @FXML private TableColumn<EmprestimoViewModel, String> colunaStatus;

    private BibliotecaService service;
    private Stage dialogStage;

    public void setService(BibliotecaService service) {
        this.service = service;
        carregarDados();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void initialize() {
        colunaLivro.setCellValueFactory(new PropertyValueFactory<>("tituloLivro"));
        colunaUsuario.setCellValueFactory(new PropertyValueFactory<>("nomeUsuario"));
        colunaDataEmp.setCellValueFactory(new PropertyValueFactory<>("dataEmprestimo"));
        colunaDataDev.setCellValueFactory(new PropertyValueFactory<>("dataDevolucao"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void carregarDados() {
        if (service == null) return;

        ObservableList<EmprestimoViewModel> listaView = FXCollections.observableArrayList();

        for (Emprestimo emp : service.getTodosEmprestimos()) {
            
            String titulo = service.buscarLivroPorIsbn(emp.getIsbnLivro())
                    .map(l -> l.getTitulo())
                    .orElse("Livro Desconhecido (" + emp.getIsbnLivro() + ")");

            String usuario = service.buscarUsuarioPorId(emp.getIdUsuario())
                    .map(u -> u.getNome())
                    .orElse("Usuário Desconhecido (" + emp.getIdUsuario() + ")");

            listaView.add(new EmprestimoViewModel(emp, titulo, usuario));
        }

        tabelaEmprestimos.setItems(listaView);
    }
}