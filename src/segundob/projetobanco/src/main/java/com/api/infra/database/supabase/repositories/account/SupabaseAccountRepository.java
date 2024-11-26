package com.api.infra.database.supabase.repositories.account;

import com.api.data.protocols.database.account.*;
import com.api.domain.dto.account.CreateAccountDTO;
import com.api.domain.entities.Account;
import com.api.infra.database.supabase.models.AccountModel;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class SupabaseAccountRepository implements ILoadAccountByEmailRepository, ILoadAccountByDocumentRepository, ICreateAccountRepository {
    private final EntityManagerFactory entityManagerFactory;

    public SupabaseAccountRepository() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("SupabasePU");
    }

    public Account createAccount(CreateAccountDTO createAccountDTO) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            AccountModel accountModel = new AccountModel(
                    null,
                    createAccountDTO.getDocument(),
                    createAccountDTO.getName(),
                    createAccountDTO.getEmail(),
                    generateAccountNumber(),
                    createAccountDTO.getPassword(),
                    LocalDateTime.now(),
                    null
            );

            em.persist(accountModel);

            transaction.commit();

            return new Account(
                    accountModel.getId(),
                    accountModel.getDocument(),
                    accountModel.getName(),
                    accountModel.getEmail(),
                    accountModel.getAccountNumber(),
                    accountModel.getPassword(),
                    accountModel.getCreatedAt(),
                    accountModel.getDisabledAt()
            );
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao criar conta: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public Account loadAccountByEmail(String email) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            AccountModel accountModel = em.createQuery(
                            "SELECT a FROM AccountModel a WHERE a.email = :email", AccountModel.class)
                    .setParameter("email", email)
                    .getSingleResult();

            return new Account(
                    accountModel.getId(),
                    accountModel.getDocument(),
                    accountModel.getName(),
                    accountModel.getEmail(),
                    accountModel.getAccountNumber(),
                    accountModel.getPassword(),
                    accountModel.getCreatedAt(),
                    accountModel.getDisabledAt()
            );
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public Account loadAccountByDocument(String document) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            AccountModel accountModel = em.createQuery(
                            "SELECT a FROM AccountModel a WHERE a.document = :document", AccountModel.class)
                    .setParameter("document", document)
                    .getSingleResult();

            return new Account(
                    accountModel.getId(),
                    accountModel.getDocument(),
                    accountModel.getName(),
                    accountModel.getEmail(),
                    accountModel.getAccountNumber(),
                    accountModel.getPassword(),
                    accountModel.getCreatedAt(),
                    accountModel.getDisabledAt()
            );
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    private String generateAccountNumber() {
        int agencyNumber = ThreadLocalRandom.current().nextInt(1000, 9999);
        int accountBase = ThreadLocalRandom.current().nextInt(100000, 999999);

        int checkDigit = (agencyNumber + accountBase) % 10;

        return String.format("%04d-%06d-%d", agencyNumber, accountBase, checkDigit);
    }
}