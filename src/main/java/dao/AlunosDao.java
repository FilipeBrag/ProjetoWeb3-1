package dao;

import br.edu.ifsp.Projeto1.Aluno;
import jakarta.persistence.EntityManager;

import java.util.List;


public class AlunosDao {
    private EntityManager em;

    public AlunosDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Aluno aluno) {
        em.getTransaction().begin();
        em.persist(aluno);
        em.getTransaction().commit();
    }

    public boolean excluir(String nome) {
        em.getTransaction().begin();
        try {
            List<Aluno> alunos = em.createQuery("SELECT a FROM Aluno a WHERE a.nome = :nome", Aluno.class)
                    .setParameter("nome", nome)
                    .getResultList();

            if (alunos.isEmpty()) {
                em.getTransaction().rollback(); // ou commit dependendo da lógica
                return false;
            }

            for (Aluno aluno : alunos) {
                em.remove(aluno);
            }

            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public Aluno alterar(Aluno aluno){
        em.getTransaction().begin();
        Aluno atualizado = null;

        try {
            atualizado = em.merge(aluno);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }

        return atualizado;
    }

    public Aluno buscar(String nome){
        try {
            List<Aluno> alunos = em.createQuery("SELECT a FROM Aluno a WHERE a.nome = :nome", Aluno.class)
                    .setParameter("nome", nome)
                    .getResultList();

            if (alunos.isEmpty()) {
                return null;
            }

            return alunos.get(0); // Retorna o primeiro encontrado
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Aluno> listarAprovados(){
        return this.em.createQuery("SELECT a FROM Aluno a WHERE a.status = 'Aprovado'", Aluno.class)
                .getResultList();
    }
}
