package br.ufrn.imd.libralibre.controller;

import br.ufrn.imd.libralibre.model.Livro;
import br.ufrn.imd.libralibre.model.LivroDigital;
import br.ufrn.imd.libralibre.model.LivroFisico;
import br.ufrn.imd.libralibre.service.BibliotecaService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastroLivroController {

    @FXML private TextField campoIsbn;
    @FXML private TextField campoTitulo;
    @FXML private TextField campoAutor;
    @FXML private TextField campoCopias;
    @FXML private CheckBox checkDigital;

    private BibliotecaService service;
    private Stage dialogStage;
    private boolean okClicked = false;

    public void setService(BibliotecaService service) {
        this.service = service;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void initialize() {
        // nada por enquanto
    }

    // marcar "Livro Digital", desabilita o campo de cópias

    @FXML
    private void handleTipoLivroMudou() {
        if (checkDigital.isSelected()) {
            campoCopias.setDisable(true);
            campoCopias.setText("");
        } else {
            campoCopias.setDisable(false);
            campoCopias.setText("1");
        }
    }

    @FXML
    private void handleSalvar() {
        if (isEntradaValida()) {
            String isbn = campoIsbn.getText();
            String titulo = campoTitulo.getText();
            String autor = campoAutor.getText();
            
            Livro novoLivro;

            if (checkDigital.isSelected()) {
                novoLivro = new LivroDigital(isbn, titulo, autor);
            } else {
                int copias = Integer.parseInt(campoCopias.getText());
                novoLivro = new LivroFisico(isbn, titulo, autor, copias);
            }

            try {
                service.adicionarLivro(novoLivro);
                okClicked = true;
                dialogStage.close();
            } catch (IllegalStateException e) {
                mostrarAlerta("Erro ao Salvar", e.getMessage());
            }
        }
    }

    @FXML
    private void handleCancelar() {
        dialogStage.close();
    }

    private boolean isEntradaValida() {
        String erro = "";

        if (campoIsbn.getText() == null || campoIsbn.getText().isEmpty()) erro += "ISBN inválido!\n";
        if (campoTitulo.getText() == null || campoTitulo.getText().isEmpty()) erro += "Título inválido!\n";
        if (campoAutor.getText() == null || campoAutor.getText().isEmpty()) erro += "Autor inválido!\n";
        
        if (!checkDigital.isSelected()) {
            try {
                int copias = Integer.parseInt(campoCopias.getText());
                if (copias < 0) erro += "Cópias deve ser maior ou igual a 0!\n";
            } catch (NumberFormatException e) {
                erro += "Cópias deve ser um número inteiro!\n";
            }
        }

        if (erro.isEmpty()) {
            return true;
        } else {
            mostrarAlerta("Campos Inválidos", erro);
            return false;
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}