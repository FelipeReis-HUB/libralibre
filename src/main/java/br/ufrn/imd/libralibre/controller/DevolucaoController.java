package br.ufrn.imd.libralibre.controller;

import br.ufrn.imd.libralibre.service.BibliotecaService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DevolucaoController {

    @FXML private TextField campoIsbn;
    @FXML private TextField campoIdUsuario;

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
    private void handleConfirmar() {
        if (isEntradaValida()) {
            String isbn = campoIsbn.getText().trim();
            String idUsuario = campoIdUsuario.getText().trim();

            try {
                // o serviço valida se o empréstimo existe
                // e atualiza o estoque.
                service.realizarDevolucao(isbn, idUsuario);
                
                okClicked = true;
                mostrarAlertaInformacao("Sucesso", "Livro devolvido com sucesso!");
                dialogStage.close();
                
            } catch (IllegalStateException e) {
                mostrarAlertaErro("Erro na Devolução", e.getMessage());
            } catch (Exception e) {
                mostrarAlertaErro("Erro Inesperado", "Ocorreu um erro ao processar a devolução.");
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleCancelar() {
        dialogStage.close();
    }

    private boolean isEntradaValida() {
        String erro = "";
        if (campoIsbn.getText() == null || campoIsbn.getText().trim().isEmpty()) erro += "ISBN é obrigatório.\n";
        if (campoIdUsuario.getText() == null || campoIdUsuario.getText().trim().isEmpty()) erro += "ID do usuário é obrigatório.\n";

        if (erro.isEmpty()) {
            return true;
        } else {
            mostrarAlertaErro("Campos Inválidos", erro);
            return false;
        }
    }

    private void mostrarAlertaErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
    
    private void mostrarAlertaInformacao(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}