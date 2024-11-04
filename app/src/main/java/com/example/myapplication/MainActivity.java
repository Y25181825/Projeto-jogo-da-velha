package com.example.jogodavelha;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView txt_placarJogador1, txt_placarJogador2, txt_statusJogo;
    private Button []btn = new Button[9];
    private Button btn_reiniciarJogo;

    private boolean jogador;
    private int numJogadas, placarJogador1, placarJogador2;

    private int [] jogadas = {0,0,0,0,0,0,0,0,0};

    private int [][] vitoria = {
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_placarJogador1 = findViewById(R.id.txt_placarJogador1);
        txt_placarJogador2 = findViewById(R.id.txt_placarJogador2);
        txt_statusJogo = findViewById(R.id.txt_statusJogo);

        btn_reiniciarJogo = findViewById(R.id.btn_reiniciarJogo);

        for (int i = 0; i < btn.length; i++){
            int resId = getResources().getIdentifier("btn_"+i, "id", getPackageName());
            btn[i] = findViewById(resId);
            btn[i].set0nClickListener(this);
        }

        jogador = true;
        placarJogador1 = 0;
        placarJogador2 = 0;
        numJogadas = 1;

        btn_reiniciarJogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placarJogador1 = 0;
                placarJogador2 = 0;
                reiniciaJogo();
                exibePlacar;
            }
        });

    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), "Botão pressionado", Toast.LENGTH_SHORT).show();

        if (!((Button)view).getText().toString().equals("")){
            return;
        }

        int numBtn = Integer.parseInt(view.getResources().getResourceEntryName(view.getId()).substring(4,5));

        if (jogador) {
            ((Button)view).setText("X");
            jogadas[numBtn] = 1;
        }else{
            ((Button)view).setText("O");
            jogadas[numBtn] = 2;
        }

        if (venceu()){
            if(jogador){
                placarJogador1++;
                Toast.makeText(view.getContext(), "Jogador 1 venceu", Toast.LENGTH_SHORT).show();
            }else {
                placarJogador2++;
                Toast.makeText(view.getContext(), "Jogador 2 venceu", Toast.LENGTH_SHORT).show();
            }
            exibePlacar();
            reiniciaJogo();
        }else if (numJogadas == 9){
            Toast.makeText(view.getContext(), "Deu velha", Toast.LENGTH_SHORT).show();
            reiniciaJogo();
        }else {
            numJogadas++;
            jogador = !jogador;
        }
    }

    private void exibePlacar(){
        txt_placarJogador1.setText(""+ placarJogador1);
        txt_placarJogador2.setText(""+ placarJogador2);

        if (placarJogador1 > placarJogador2){
            txt_statusJogo.setText("Jogador 1 está ganhando");
        }else if (placarJogador1 < placarJogador2){
            txt_statusJogo.setText("Jogador 2 está ganhando");
        }else {
            txt_statusJogo.setText("O jogo está empatado");
        }
    }

    private void venceu() {
        numJogadas = 1;
        jogador = true;
        for (int i = 0 < btn.length; i++){
            btn[i].setText("");
            jogadas[i] = 0;
        }
    }

    private boolean venceu(){
        boolean resultado = false;

        for (int [] jogadasefetuadas: vitoria){
            if (jogadas[jogadasefetuadas[0]]==jogadas[jogadasefetuadas[1]] &&
                    jogadas[jogadasefetuadas[1]]==jogadas[jogadasefetuadas[2]] &&
                    jogadas[jogadasefetuadas[0]]!=0) {
                resultado = true;
            }
        }

        return resultado;
    }
}
