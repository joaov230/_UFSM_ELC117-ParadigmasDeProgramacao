#include <iostream>
#include <ctime> // std::time()...
#include <cstdlib> // std::rand() // std::srand()...

// Compilar com:
// g++ -std=c++11 -O2 -Wall -g  -o trabalho t1-jvbeltrame.cpp

////////////////////////////////////
// Aluno: João Vítor Forgearini Beltrame
// Matricula: 201713749
////////////////////////////////////

struct Estado {
  bool bomba;
  bool aberta;
  bool marcada;

  // Zera todas as funções
  void zera_tudo (void) {
    bomba = false;
    aberta = false;
    marcada = false;
  }
};

void print_c_i_a_p (void) {
  std::cout << std::endl << "Digite as coordenadas da seguinte maneira: X Y\n- (X e Y devem estar dentro da matriz) -\nQual posicao deseja abrir?" << std::endl;
}

void continua_inserindo_abre_posicao (int* lin, int* col, int tamanho) {
  int l, c;

  // Se a posição inserida é fora da matriz, insere novamente
  do {
    print_c_i_a_p ();
    std::cin >> c >> l;
  } while ((l < 1 || l > tamanho) || (c < 1 || c > tamanho));

  // Diminui 1 de cada (pra poder inserir uma posição de maneira mais intuitiva)
  c--; l--;
  *lin = l; *col = c;
}

// Abre posição e verifica se a bomba explodiu
bool abre_posicao (Estado** mundo, int tamanho) {
  int lin, col;

  continua_inserindo_abre_posicao (&lin, &col, tamanho);
  mundo[lin][col].aberta = true;

  // Se tiver bomba, retorna true, senão, false
  return mundo[lin][col].bomba;
}

void desenha_bomba (Estado** mundo, int l, int c) {
  if (mundo[l][c].aberta && mundo[l][c].bomba) {
    std::cout << " |X| ";
  } else if (mundo[l][c].aberta) {
    std::cout << " ]0[ ";
  } else if (mundo[l][c].marcada) {
    std::cout << " [M] ";
  } else {
    std::cout << " [ ] ";
  }
}

void desenha_conta_bomba (int conta_bombas, int bombas) {
  std::cout << conta_bombas  << "/" << bombas << " bombas marcadas\n" << std::endl;
}

void desenha_numeros_da_matriz (int tamanho) {
  // Printa os números de cada coluna
  std::cout << "\n\n\n" << "  ";
  for (int i=1; i <= tamanho; i++) {
    if (i > 10) {
      std::cout << " " << i << "  ";
    } else {
      std::cout << "  " << i << "  " ;
    }
  }
  std::cout << std::endl;
}

// Desenha a legenda para o player
void desenha_legenda (void) {
  std::cout << "\n[ ] - Fechado | ]0[ - Aberto | |X| - Bomba | [M] - Marcada" << std::endl;
}

// Desenha a matriz
void desenha_matriz (Estado** mundo, int tamanho, int bombas, int conta_bombas) {
  int i=1;

  // Printa o numero das linhas e a matriz
  for (auto l=0; l < tamanho; l++) {
    std::cout << i;
    i++;
    if (i <= 10) {
      std::cout << " ";
    }
    for (auto c=0; c < tamanho; c++) {
      desenha_bomba (mundo, l, c);
    }
    std::cout << std::endl;
  }
}

// Desenha tudo
void desenha_tudo (Estado** mundo, int bombas, int tamanho, int conta_bombas) {

  desenha_numeros_da_matriz (tamanho);

  desenha_matriz (mundo, tamanho, bombas, conta_bombas);

  desenha_conta_bomba (conta_bombas, bombas);
}

// Randomiza o local das bombas
void randomiza_bomba (Estado** mundo, int bombas, int tamanho) {
  int lin=0, col=0;
  for (auto bomb = 0; bomb <= bombas; bomb++) {
    lin = std::rand() % tamanho;
    col = std::rand() % tamanho;
    while (mundo[lin][col].bomba) {
      lin = std::rand() % tamanho;
      col = std::rand() % tamanho;
    }
    mundo[lin][col].bomba = true;
  }
}

void desmarca_bomba_aux (Estado** mundo, int lin, int col) {
  mundo[lin][col].marcada = false;
}

int subtrai_conta_bomba (Estado** mundo, int lin, int col, int conta_bomba) {
  if (mundo[lin][col].bomba && mundo[lin][col].marcada) {
    conta_bomba -= 1;
  }

  desmarca_bomba_aux (mundo, lin, col);

  return conta_bomba;
}

void desenha_d_b (void) {
  std::cout << "Digite onde deseja desmarcar da seguinte maneira: X Y" << std::endl;
  std::cout << "- (X e Y devem estar dentro dos limites da matriz) -" << std::endl;
}

// Desmarca bomba
int desmarca_bomba (Estado** mundo, int tamanho, int conta_bomba) {
  int lin, col;

  do {
    desenha_d_b ();
    std::cin >> col >> lin;
  } while ((lin < 1 || lin > tamanho) || (col < 1 || col > tamanho));

  // Apenas subtrai do valor inserido para tornar a inserção mais intuitiva
  lin--; col--;

  return subtrai_conta_bomba (mundo, lin, col, conta_bomba);;
}

void desenha_j_f_m (void) {
  std::cout << "Essa posicao ja foi marcada, selecione outra" << std::endl;
}

// Enquanto a posição já tenha sido marcada, marque novamente
void ja_foi_marcada (Estado** mundo, int* l, int* c) {
  int lin = *l, col = *c;

  while (mundo[lin][col].marcada) {
    desenha_j_f_m ();
    std::cin >> col >> lin;
    lin--; col--;
  }

  *l = lin; *c = col;
}

void desenha_m_a_b (void) {
  std::cout << "Digite onde deseja marcar da seguinte maneira: X Y" << std::endl;
  std::cout << "- (X e Y precisam estar dentro da matriz) -" << std::endl;
}

void marca_a_bomba (int* l, int* c, int tamanho) {
  int lin, col;

  do {
    desenha_m_a_b ();
    std::cin >> col >> lin;
  } while ((lin < 1 || lin > tamanho) || (col < 1 || col > tamanho));
  lin--; col--;

  *l = lin; *c = col;
}

int verifica_bomba_marcada (Estado** mundo, int lin, int col, int conta_bomba) {
  mundo[lin][col].marcada = true;
  if (mundo[lin][col].bomba && mundo[lin][col].marcada)
    return conta_bomba + 1;
  else
    return conta_bomba;
}

// Marca a bomba
int marca_bomba (Estado** mundo, int tamanho, int conta_bomba) {
  int lin, col;

  marca_a_bomba (&lin, &col, tamanho);

  ja_foi_marcada (mundo, &lin, &col);

  return verifica_bomba_marcada (mundo, lin, col, conta_bomba);
}

void separa_jogada (void) {
  // Separa os jogos
  for (auto i = 0; i < 30; i++) {
    std::cout << " | " << std::endl;
  }
}

void print_i_t_1 (void) {
  std::cout << "Digite o tamanho do campo:" << std::endl;
}

void print_i_t_2 (void) {
  std::cout << std::endl << "A altura precisa ser maior que zero, digite novamente:" << std::endl;
}

int insere_tamanho (void) {
  int tamanho;

  print_i_t_1 ();
  std::cin >> tamanho;
  while (tamanho <= 0) {
    print_i_t_2 ();
    std::cin >> tamanho;
  }
  return tamanho;
}

// Aloca o espaço da matriz
Estado** aloca_matriz (int tamanho) {
  Estado** mundo = new Estado* [tamanho];

  for (auto i = 0; i < tamanho; i++) {
    mundo [i] = new Estado [tamanho];
  }
  return mundo;
}

void zera_matriz (Estado** mundo, int tamanho) {
  for (int l=0; l < tamanho; l++) {
    for (int c=0; c < tamanho; c++) {
      mundo[l][c].zera_tudo ();
    }
  }
}

void desenha_i_b (void) {
  std::cout << "Digite a quantidade de bombas que deseja: (deve ser menor que o campo e maior que 0)" << std::endl;
}

int insere_bombas (int tamanho) {
  int bombas;

  do {
    desenha_i_b ();
    std::cin >> bombas;
  } while (bombas <= 0 || bombas >= tamanho*tamanho);

  return bombas;
}

// Printa na tela os comandos que o jogador pode executar
void printa_comandos (void) {
  std::cout << "Jogar -> 1 | Marcar -> 2 | Desmarcar -> 3" << std::endl;
  std::cout << "Parar -> 4 | Recomecar -> 5" << std::endl;
}

// Função que decide o que o jogador vai fazer nessa jogada
void verifica_jogada_e_joga (Estado** mundo, int tamanho, int* conta_bomba, int* marcador, bool* acabou_jogo, bool* recomeca, bool* acabou) {
  int verifica;
  std::cin >> verifica;
  switch (verifica) {
    case 1:
      *acabou = abre_posicao (mundo, tamanho);
      break;
    case 2:
      *conta_bomba = marca_bomba (mundo, tamanho, *conta_bomba);
      (*marcador)++;
      break;
    case 3:
      *conta_bomba = desmarca_bomba (mundo, tamanho, *conta_bomba);
      (*marcador)--;
      break;
    case 4:
      *acabou_jogo = true;
      break;
    case 5:
      *recomeca = true;
      break;
  }
}

void cabou (Estado** mundo, int bombas, int tamanho, int conta_bomba, bool acabou) {
  if (acabou) {
    desenha_tudo (mundo, bombas, tamanho, conta_bomba);
    std::cout << "------------" << std::endl;
    std::cout << "Voce perdeu!" << std::endl;
    std::cout << "------------" << std::endl;
  }
}

void desenha_se_ganhou (Estado** mundo, int bombas, int tamanho, int conta_bomba) {
  desenha_tudo (mundo, bombas, tamanho, conta_bomba);
  std::cout << "------------" << std::endl;
  std::cout << "Voce ganhou!" << std::endl;
  std::cout << "------------" << std::endl;
}

// Verifica se ganhou
bool verifica_se_ganhou (Estado** mundo, int bombas, int tamanho, int conta_bomba, int marcador) {
  int contador = 0;
  for (auto l=0; l < tamanho; l++) {
    for (auto c=0; c < tamanho; c++) {
      if (mundo[l][c].marcada && mundo [l][c].bomba) {
        contador++;
      }
    }
  }

  if (contador == bombas && contador == marcador) {
    desenha_se_ganhou (mundo, bombas, tamanho, conta_bomba);
    return true;
  }
  return false;
}

void deleta_memoria (Estado** mundo, int tamanho) {
  for (auto i=0; i < tamanho; i++) {
    delete[] mundo[i];
  }
  delete[] mundo;
}

void desenha_se_acabou (void) {
  std::cout << "Continuar -> 1\nParar -> 2" << std::endl;
}

void reseta_contadores (bool* acabou, int* conta_bomba, int* marcador) {
  *acabou = false;
  *conta_bomba = 0;
  *marcador = 0;
}

bool continua_ou_acaba (void) {
  int verifica;

  desenha_se_acabou ();
  std::cin >> verifica;

  switch (verifica) {
    case 1:
      return false;
    case 2:
      return true;
  }
}


////////////////////////////////////////
// Main
////////////////////////////////////////
int main (int argc, char** argv) {
    int conta_bomba=0, marcador=0;
    bool acabou = false, recomeca, acabou_jogo = false;
    int tamanho, bombas;

    std::srand (std::time(0));

    // While do menu e jogo
    while (!acabou_jogo) {

      separa_jogada ();

      // Para começar o segundo while (jogo)
      recomeca = false;

      // Insersão do tamanho da matriz
      tamanho = insere_tamanho ();

      // Alocação da matriz do jogo
      Estado** mundo {nullptr};
      mundo = aloca_matriz (tamanho);

      // Zera todos os elementos da matriz
      zera_matriz (mundo, tamanho);

      // Insersão das bombas
      bombas = insere_bombas (tamanho);

      randomiza_bomba (mundo, bombas, tamanho);

      /////////////////////////////////////////
      // JOGO
      /////////////////////////////////////////
      while (recomeca == false && acabou == false && acabou_jogo == false) {

        desenha_tudo (mundo, bombas, tamanho, conta_bomba);

        printa_comandos ();


        // Se acertou uma bomba, acaba a rodada, printa uma ultima vez e volta pro menu
        cabou (mundo, bombas, tamanho, conta_bomba, acabou);

        acabou = verifica_se_ganhou (mundo, bombas, tamanho, conta_bomba, marcador);

        verifica_jogada_e_joga (mundo, tamanho, &conta_bomba, &marcador, &acabou_jogo, &recomeca, &acabou);

      }

      // Acabou o jogo, o programa deleta a memória alocada

      reseta_contadores (&acabou, &conta_bomba, &marcador);

      deleta_memoria (mundo, tamanho);


      if (!acabou_jogo) {
        // Dá a opção para continuar e parar após terminar o jogo
        acabou_jogo = continua_ou_acaba ();
      }
    }
    return 0;
}
