package br.ufrn.imd.libralibre.controller;

import br.ufrn.imd.libralibre.model.Usuario;
import br.ufrn.imd.libralibre.service.BibliotecaService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastroUsuarioController {

    @FXML private TextField campoId;
    @FXML private TextField campoNome;

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
    private void handleSalvar() {
        if (isEntradaValida()) {
            String id = campoId.getText();
            String nome = campoNome.getText();

            Usuario novoUsuario = new Usuario(id, nome);

            try {
                service.adicionarUsuario(novoUsuario);
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

        if (campoId.getText() == null || campoId.getText().trim().isEmpty()) {
            erro += "ID inválido!\n";
        }
        if (campoNome.getText() == null || campoNome.getText().trim().isEmpty()) {
            erro += "Nome inválido!\n";
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