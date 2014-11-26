package telas;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import trabalho4bimestre.basededados.BancoDeDadosEmMemoria;
import logica.PessoaBusinnes;
import modelo.Pessoa;

import static javafx.scene.layout.Priority.ALWAYS;


public class TelaPesquisa extends Application {

    private ObservableList<Pessoa> dadosGrid = FXCollections.observableArrayList();


    private BorderPane borderPane = new BorderPane();

    private TableView<Pessoa> gridPessoas;

    private Stage stage;

    @Override
    public void start(Stage palco) throws Exception {


        dadosGrid.addAll(BancoDeDadosEmMemoria.getInstance().tbPessoas);

        palco.setTitle("Pesquisa");

        borderPane.paddingProperty().setValue(new Insets(10));
        borderPane.setPrefSize(780, 590);

        HBox boxCampoDePesquisa = montaPainelDePesquisa();

        borderPane.setTop(boxCampoDePesquisa);

        gridPessoas = montaGridPessoas();

        borderPane.setCenter(gridPessoas);


        ToolBar toolBar = montaBarraDeFerramentas();

        borderPane.setBottom(toolBar);

        palco.setScene(new Scene(borderPane, 800, 600));
        palco.show();

        this.stage = palco;
    }

    private ToolBar montaBarraDeFerramentas() {

        ToolBar toolBar = new ToolBar();

        Button btnNovo = new Button("Novo");
        Button btnAlterar = new Button("Alterar");
        Button btnExcluir = new Button("Excluir");

        toolBar.getItems().addAll(btnNovo, btnAlterar, btnExcluir);


        btnNovo.setOnAction(click -> {

            invocarTelaDeCadastro(null);

        });

        btnExcluir.setOnAction(click -> {


            if (gridPessoas.getSelectionModel().getSelectedItem() != null) {
                Pessoa pessoa = gridPessoas.getSelectionModel().getSelectedItems().get(0);
                PessoaBusinnes businnes = new PessoaBusinnes();
                businnes.remover(pessoa);
                //atualizar a tela
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        dadosGrid = FXCollections.observableArrayList();
                        dadosGrid.addAll(BancoDeDadosEmMemoria.getInstance().tbPessoas);
                        gridPessoas.setItems(null);
                        gridPessoas.setItems(dadosGrid);
                    }
                });


            }
        });


        btnAlterar.setOnAction(click -> {
            if (gridPessoas.getSelectionModel().getSelectedItem() != null) {
                Pessoa pessoa = gridPessoas.getSelectionModel().getSelectedItems().get(0);

                //chamar a tela de cadastro

                invocarTelaDeCadastro(pessoa);

                PessoaBusinnes businnes = new PessoaBusinnes();
                businnes.alterarPessoa(pessoa);


            }
        });


        return toolBar;
    }

    private void invocarTelaDeCadastro(Pessoa pessoa) {
        TelaCadastro cadastro = new TelaCadastro(stage);
        stage.setScene(new Scene(cadastro.getTela(), 800, 600));

        if (pessoa == null) {
            cadastro.iniciaInsercao();
        } else {
            cadastro.iniciaAlteracao(pessoa);
        }

    }

    private HBox montaPainelDePesquisa() {

        HBox boxCampoDePesquisa = new HBox();
        boxCampoDePesquisa.paddingProperty().setValue(new Insets(10));
        boxCampoDePesquisa.setHgrow(borderPane, ALWAYS);
        //campo de texto
        TextField ct = new TextField();
        ct.setPrefColumnCount(60);

        ct.promptTextProperty().setValue("digite aqui para pesquisar");

        //botão de pesquisa
        Button btnPesquisa = new Button("Busca");
        boxCampoDePesquisa.getChildren().add(ct);
        boxCampoDePesquisa.getChildren().add(btnPesquisa);

        btnPesquisa.setOnAction(action -> {
            String chave = ct.textProperty().get();
            PessoaBusinnes businnes = new PessoaBusinnes();
            try {
                Pessoa pessoa = businnes.pesquisaPorCodigo(chave);
                if (pessoa != null) {
                    dadosGrid.clear();
                    dadosGrid.add(pessoa);
                } else {
                    System.out.println(" Pessoa não encontrada");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        // final TextField edtCodigo = new TextField();
        return boxCampoDePesquisa;
    }

    private TableView<Pessoa> montaGridPessoas() {

        //Grid de pesquisa com Table view
        TableColumn colunaCodigo = new TableColumn();
        colunaCodigo.minWidthProperty().setValue(100);
        //cabecalho da coluna
        colunaCodigo.setText("Código");
        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));

        TableColumn colunaNome = new TableColumn();
        colunaNome.minWidthProperty().setValue(100);
        colunaNome.setText("Nome");
        colunaNome.setCellValueFactory(new PropertyValueFactory("nome"));

        TableColumn colunaEmail = new TableColumn();
        colunaEmail.minWidthProperty().setValue(100);
        colunaEmail.setText("Email");
        colunaEmail.setMinWidth(200);
        colunaEmail.setCellValueFactory(new PropertyValueFactory("email"));

        TableView<Pessoa> gridPessoas = new TableView();

        gridPessoas.getColumns().addAll(colunaCodigo, colunaNome, colunaEmail);
        gridPessoas.setItems(dadosGrid);


        return gridPessoas;
    }


    public void atualizarGrid() {
        //atualizar a tela
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                dadosGrid = FXCollections.observableArrayList();
                dadosGrid.addAll(BancoDeDadosEmMemoria.getInstance().tbPessoas);
                gridPessoas.setItems(null);
                gridPessoas.setItems(dadosGrid);
            }
        });
    }
}