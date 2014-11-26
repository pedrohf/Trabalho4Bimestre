package trabalho4bimestre.basededados;

import modelo.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class BancoDeDadosEmMemoria {

    // tabela pessoas
    public List<Pessoa> tbPessoas = new ArrayList<Pessoa>();

    private static BancoDeDadosEmMemoria bancoDeDadosEmMemoria;

    //construtor privado não roda o comando new
    private BancoDeDadosEmMemoria() {
        tbPessoas.add( new Pessoa("01","Ze","ze@gmail.com"));
        tbPessoas.add( new Pessoa("02","Joselito","joselito@mail.com"));
        tbPessoas.add( new Pessoa("03","Meeee","me@mail.com"));

    }

    //padrão singleton
    public static BancoDeDadosEmMemoria getInstance() {

        if (bancoDeDadosEmMemoria == null) {
            bancoDeDadosEmMemoria = new BancoDeDadosEmMemoria();
        }

        return bancoDeDadosEmMemoria;
    }

}