# Zist Dictionary

<h2>Disclaimer</h2>
<p>
  O aplicativo para android Zist Dictionary foi desenvolvido para se encaixar nos requisitos do trabalho de Dispositivos Móveis da UDESC 2024.1, tendo como Professor da Disciplina Mattheus da Hora.
  Dito isso, neste README, estarei elegendo os objetivos propostos do projeto e também do escopo do trabalho.
</p>

<h2>Sobre</h2>
<p>
  O aplicativo se trata de um dicionário de palavras pessoal, onde ao adicionar uma nova palavra, ela é automaticamente traduzida.
</p>
---

## Objetivos do projeto (Nível escopo de requerimento do enunciado do trabalho)
> - Utilizar arquitetura MVVM
> - Utilizar a api de Câmera do Android
> - Utilizar integrações com Api's utilizando Retrofit (Neste caso em específico, optei por utilizar o Ktor, pois é direcionado ao kotlin, conforme acordado com o professor)
> - Utilizar integrações com o Google Firebase
> - Google Firebase Integrations:
> > - Authentication
> > - Firestore
> - Utilizar components Android ( Neste caso como foi utilizado Android Compose com Kotlin, os components são diferentes do enunciado do trabalho )
---

## Objetos do projeto (Nível requisitos gerados por mim

| Código | Descrição                                                                                                           |
|--------|---------------------------------------------------------------------------------------------------------------------|
| RF001  | O sistema deve ser capaz de manter usuário.                                                                         |
| RF002  | O sistema deve ser capaz de permitir ao usuário adicionar palavras ao seu dicionário pessoal.                       |
| RF003  | O sistema deve ser capaz de traduzir a palavra inserida pelo usuário no dicionário pessoal automaticamente.         |
| RF004  | O sistema deve ser capaz de gerar uma frase utilizando a palavra inserida pelo usuário nos dois idiomas.            |
| RF005  | O sistema deve ser capaz de permitir ao usuário ter dicionários em mais de um idioma.                               |
| RF006  | O sistema deve ser capaz de permitir ao usuário que selecione o idioma que ele quer o dicionário, e a ordem de tradução, por exemplo PT - EN ou EN - PT. |


