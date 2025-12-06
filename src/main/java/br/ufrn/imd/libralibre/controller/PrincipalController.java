package br.ufrn.imd.libralibre.controller;

import java.io.IOException;

import br.ufrn.imd.libralibre.model.Livro;
import br.ufrn.imd.libralibre.service.BibliotecaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class PrincipalController {

    @FXML
    private TableView<Livro> tabelaLivros;

    @FXML
    private TableColumn<Livro, String> colunaIsbn;

    @FXML
    private TableColumn<Livro, String> colunaTitulo;

    @FXML
    private TableColumn<Livro, String> colunaAutor;

    @FXML
    private TableColumn<Livro, String> colunaStatus;


    private final BibliotecaService service = new BibliotecaService();
    

    private ObservableList<Livro> listaLivros;

    @FXML
    public void initialize() {
        // configurar as colunas
        colunaIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colunaAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colunaStatus.setCellValueFactory(cellData -> {
            Livro livro = cellData.getValue();
            if (livro.estaDisponivel()) {
                return new javafx.beans.property.SimpleStringProperty("Disponível");
            } else {
                return new javafx.beans.property.SimpleStringProperty("Indisponível");
            }
        });

        carregarDadosTabela();
    }

    private void carregarDadosTabela() {
        // transforma a lista normal p servableList
        listaLivros = FXCollections.observableArrayList(service.getAcervo());
        
        tabelaLivros.setItems(listaLivros);

        tabelaLivros.refresh();
    }


    //ações dos botoes

    @FXML
    private void handleNovoLivro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufrn/imd/libralibre/view/CadastroLivro.fxml"));
            VBox page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Novo Livro");
            dialogStage.initModality(Modality.WINDOW_MODAL); // impede clicar na janela de trás
            //dialogStage.initOwner(primaryStage); // precisaria ter acesso ao stage principal
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // passa os dados para o controller da janelinha
            CadastroLivroController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setService(service);

            dialogStage.showAndWait();

            if (controller.isOkClicked()) {
                carregarDadosTabela();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleNovoUsuario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufrn/imd/libralibre/view/CadastroUsuario.fxml"));
            VBox page = loader.load();

            // cria a janela)
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Novo Usuário");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // 3. cfg o controller
            CadastroUsuarioController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setService(service);

            dialogStage.showAndWait();

            if (controller.isOkClicked()) {
                System.out.println("Usuário salvo com sucesso!");
                // add atualizar uma tabela de usuários se visível.
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEmprestimo() {
        try {
            // 1. Carregar o FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufrn/imd/libralibre/view/RealizarEmprestimo.fxml"));
            VBox page = loader.load();

            // 2. Criar a Janela
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Realizar Empréstimo");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // 3. Configurar Controller
            RealizarEmprestimoController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setService(service);

            // 4. Mostrar
            dialogStage.showAndWait();

            // 5. Se houve sucesso, atualizamos a tabela principal
            // (Pois o número de cópias disponíveis do livro pode ter mudado)
            if (controller.isOkClicked()) {
                carregarDadosTabela();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDevolucao() {
        try {
            // 1. Carregar a tela
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufrn/imd/libralibre/view/Devolucao.fxml"));
            VBox page = loader.load();

            // 2. Configurar o Palco
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Registrar Devolução");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // 3. Injetar dependências
            DevolucaoController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setService(service);

            // 4. Exibir
            dialogStage.showAndWait();

            // 5. Atualizar a tabela (o estoque do livro aumentou!)
            if (controller.isOkClicked()) {
                carregarDadosTabela();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAtualizar() {
        System.out.println("Atualizando a tabela..."); // Log para confirmação
        carregarDadosTabela();
    }


    // Adicione no PrincipalController.java

    @FXML
    private void handleRelatorio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufrn/imd/libralibre/view/Relatorio.fxml"));
            // O Relatorio.fxml tem um BorderPane como raiz, então usamos BorderPane aqui (ou Parent genérico)
            Parent page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Relatório Consolidado");
            // dialogStage.initModality(Modality.WINDOW_MODAL); // Opcional: Relatório pode ficar aberto
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            RelatorioController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setService(service); // Injeta o serviço

            dialogStage.show(); // show() em vez de showAndWait() permite deixar o relatório aberto ao lado

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}