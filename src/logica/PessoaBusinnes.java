package logica;

import trabalho4bimestre.basededados.BancoDeDadosEmMemoria;
import modelo.Pessoa;

import java.util.List;
import java.util.function.Consumer;


public class PessoaBusinnes {

    private BancoDeDadosEmMemoria banco = BancoDeDadosEmMemoria.getInstance();

    public void salvar(Pessoa pessoa) {

        banco.tbPessoas.add(pessoa);

        System.out.println("salvando pessoa: " + pessoa);
    }

    public Pessoa pesquisaPorCodigo(String chave) {

        List<Pessoa> pessoas = banco.tbPessoas;

        for (Pessoa pessoa : pessoas) {
            if (pessoa.getCodigo().equals(chave)) {
                return pessoa;
            }
        }

        return null;
    }

    public void remover(Pessoa pessoa) {
        banco.tbPessoas.remove(pessoa);
    }

    public void alterarPessoa(Pessoa pessoaSelecionada) {

        banco.tbPessoas.stream().forEach(new Consumer<Pessoa>() {
            @Override
            public void accept(Pessoa pessoa) {
                if (pessoa.getCodigo().equals(pessoaSelecionada.getCodigo())) {
                    pessoa.setNome(pessoaSelecionada.getNome());
                    pessoa.setEmail(pessoaSelecionada.getEmail());
                }
            }
        });

    }
}
