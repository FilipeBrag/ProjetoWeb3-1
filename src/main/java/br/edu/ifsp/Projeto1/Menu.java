package br.edu.ifsp.Projeto1;

import dao.AlunosDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Scanner sc = new Scanner(System.in);

    public void menu(){
        int op = 0;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Aluno");
        EntityManager em = emf.createEntityManager();
        AlunosDao dao = new AlunosDao(em);
        while(op != 6){
            System.out.println("--------MENU--------\n");
            System.out.println("1 - Cadastrar Aluno");
            System.out.println("2 - Excluir Aluno");
            System.out.println("3 - Alterar Aluno");
            System.out.println("4 - Buscar Aluno pelo nome");
            System.out.println("5 - Listar Alunos (Com status aprovado)");
            System.out.println("6 - Sair\n");

            System.out.println("Digite a opção desejada: ");
            op = sc.nextInt();

            switch (op) {
                case 1 ->{
                    System.out.println("Cadastrando aluno...");
                    System.out.println("Digite o nome: ");
                    String nome = sc.next();
                    System.out.println("Digite o RA: ");
                    String ra = sc.next();
                    System.out.println("Digite o email: ");
                    String email = sc.next();
                    System.out.println("Digite a nota 1: ");
                    BigDecimal nota1 = sc.nextBigDecimal();
                    System.out.println("Digite a nota 2: ");
                    BigDecimal nota2 = sc.nextBigDecimal();
                    System.out.println("Digite a nota 3: ");
                    BigDecimal nota3 = sc.nextBigDecimal();
                    Aluno aluno = new Aluno(nome, ra, email, nota1, nota2, nota3);
                    dao.cadastrar(aluno);
                    System.out.println("Aluno cadastrado com sucesso!");
                }
                case 2 ->{
                    System.out.println("EXCLUIR ALUNO: ");
                    System.out.println("Digite o nome do aluno: ");
                    String nome = sc.next();
                    if(dao.excluir(nome)) System.out.println("Aluno excluido com sucesso!");
                    else System.out.println("Aluno não encontrado!");
                }
                case 3 ->{
                    System.out.println("ALTERAR ALUNO: ");
                    System.out.println("Digite o nome do aluno: ");
                    String nome = sc.next();
                    Aluno alunoAlt = dao.buscar(nome);
                    Aluno aluno = dao.alterar(alunoAlt);
                    if(aluno == null) System.out.println("Aluno não encontrado!");
                    else {
                        System.out.println(aluno.toString());
                        int op2 = 0;
                        while(op2 != 7){
                            System.out.println("--------MENU ALTERAR ALUNO--------\n");
                            System.out.println("1 - Alterar nome");
                            System.out.println("2 - Alterar RA");
                            System.out.println("3 - Alterar email");
                            System.out.println("4 - Alterar nota 1");
                            System.out.println("5 - Alterar nota 2");
                            System.out.println("6 - Alterar nota 3");
                            System.out.println("7 - Sair\n");
                            op2 = sc.nextInt();
                            switch (op2) {
                                case 1 ->{
                                    System.out.println("Digite o novo nome: ");
                                    String novoNome = sc.next();
                                    aluno.setNome(novoNome);
                                }
                                case 2 ->{
                                    System.out.println("Digite o novo RA: ");
                                    String novoRa = sc.next();
                                    aluno.setRa(novoRa);
                                }
                                case 3 ->{
                                    System.out.println("Digite o novo email: ");
                                    String novoEmail = sc.next();
                                    aluno.setEmail(novoEmail);
                                }
                                case 4 ->{
                                    System.out.println("Digite a nova nota 1: ");
                                    BigDecimal novaNota = sc.nextBigDecimal();
                                    aluno.setNota1(novaNota);
                                }
                                case 5 ->{
                                    System.out.println("Digite a nova nota 2: ");
                                    BigDecimal novaNota = sc.nextBigDecimal();
                                    aluno.setNota2(novaNota);
                                }
                                case 6 ->{
                                    System.out.println("Digite a nova nota 3: ");
                                    BigDecimal novaNota = sc.nextBigDecimal();
                                    aluno.setNota3(novaNota);
                                }
                                case 7 ->{
                                    System.out.println("Aluno alterado com sucesso!");
                                }
                                default -> {
                                    System.out.println("Opção inválida");
                                }
                            }
                        }
                    }
                }
                case 4 ->{
                    System.out.println("BUSCAR ALUNO: ");
                    System.out.println("Digite o nome do aluno: ");
                    String nome = sc.next();
                    Aluno aluno = dao.buscar(nome);
                    if(aluno == null) System.out.println("Aluno não encontrado!");
                    else System.out.println(aluno.toString());
                }
                case 5 ->{
                    System.out.println("LISTAR ALUNOS APROVADOS: ");
                    List<Aluno> alunosAprovados = dao.listarAprovados();
                    for(Aluno aluno : alunosAprovados){
                        System.out.println(aluno.toString());
                    }
                }
                case 6 ->{
                    System.out.println("Saindo...");
                }
                default ->{
                    System.out.println("Opção inválida!");
                }
            }
        }
        sc.close();
    }
}
