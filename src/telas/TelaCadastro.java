package telas;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logica.PessoaBusinnes;
import modelo.Pessoa;


public class TelaCadastro {

    private Stage palco;
    private VBox janela;
    private EstadoTela estadoTela = EstadoTela.INSERINDO;
    private Pessoa entidade;
    private TextField edtCodigo = new TextField();
    private TextField edtNome = new TextField();
    private TextField edtEmail = new TextField();


    public TelaCadastro(Stage palco) {
        this.palco = palco;

        janela = new VBox();
        //componentes de entrada de dados
        Label lblCodigo = new Label("Codigo:");


        edtCodigo.promptTextProperty().setValue("informe o código");
        janela.getChildren().addAll(lblCodigo, edtCodigo);
        //adicionando componentes ao janela principal
        Label lblNome = new Label();
        lblNome.setText("Nome: ");


        //adicionando componentes ao janela principal
        janela.getChildren().add(lblNome);
        janela.getChildren().add(edtNome);
        //adicionando componentes ao janela principal
        Label lblEmail = new Label("Email:");

        edtEmail.promptTextProperty().setValue("Digite aqui o email");
        janela.getChildren().addAll(lblEmail, edtEmail);

        // controles
        Button btnConfirmar = new Button("Confirmar");


        btnConfirmar.setOnAction(event -> {
            PessoaBusinnes businnes = new PessoaBusinnes();

            if (estadoTela.equals(EstadoTela.INSERINDO)) {
                Pessoa pessoa = new Pessoa(edtCodigo.getText(), edtNome.getText(), edtEmail.getText());
                businnes.salvar(pessoa);
                edtCodigo.textProperty().setValue(null);
                edtNome.textProperty().setValue(null);
                edtEmail.textProperty().setValue(null);
                
                
                /* Alterar os dados do usuário*/
            } else if (estadoTela.equals(EstadoTela.EDITANDO)) {
                this.entidade.setCodigo(edtCodigo.getText());
                this.entidade.setNome(edtNome.getText());
                this.entidade.setEmail(edtEmail.getText());
                
                businnes.alterarPessoa(this.entidade);
                try {
                    abrirTelaDePesquisa(palco);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button btnJanelaPesquisa = new Button("Pesquisa");

        ToolBar toolBar = new ToolBar();
        toolBar.getItems().addAll(btnConfirmar, btnJanelaPesquisa);

        janela.getChildren().add(toolBar);


        btnJanelaPesquisa.setOnAction(action -> {
            //comandos a executar quanto clicarmos no botão pesquisa
            try {


                abrirTelaDePesquisa(palco);

            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    private void abrirTelaDePesquisa(Stage palco) throws Exception {
        TelaPesquisa pesquisa = new TelaPesquisa();
        pesquisa.start(palco);
        pesquisa.atualizarGrid();
    }

    public VBox getTela() {
        return this.janela;
    }

    public void iniciaInsercao() {
        this.estadoTela = EstadoTela.INSERINDO;
    }

    public void iniciaAlteracao(Pessoa pessoa) {
        this.entidade = pessoa;
        this.estadoTela = EstadoTela.EDITANDO;
        edtCodigo.textProperty().setValue(pessoa.getCodigo());
        edtNome.textProperty().setValue(pessoa.getNome());
        edtEmail.textProperty().setValue(pessoa.getEmail());

    }
}