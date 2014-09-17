package com.vitorueda.app.projeto.com.vitorueda.app.Questionario;

/**
 * Created by lsitec61.ueda on 17/09/2014.
 */
public class MultipleChoice {
    private String mPergunta;
    private String [] mRespostas;

    public MultipleChoice(String pergunta, int respostasQuantidade) {
        mPergunta = pergunta;
        mRespostas = new String[respostasQuantidade];
    }

    public String geraConteudo() {
        String msg="";

        return msg;
    }
}
