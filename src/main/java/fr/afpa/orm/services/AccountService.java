package fr.afpa.orm.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fr.afpa.orm.dto.AccountDTO;
import fr.afpa.orm.entities.Account;
import fr.afpa.orm.entities.Client;
import fr.afpa.orm.repositories.AccountRepository;
import fr.afpa.orm.repositories.ClientRepository;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    /**
     * Constructeur souhaité dans le controller
     * Permet la conversion d'un "Account" en "AccountDTO"
     * 
     * @param account
     * @return
     */
    private AccountDTO convertToDTO(Account account) {
        return new AccountDTO(account);
    }

    /**
     * Instanciation d'un Account à partir d'un AccountDTO
     * 
     * Attention, la méthode retourne "null" en cas de client non retrouvé en BDD
     * (peut arriver si pas de id dans le DTO)
     * 
     * @param accountDTO Le DTO à traiter
     * @return Instance de l'entité Account nouvellement créée
     */
    private Account convertToAccount(AccountDTO accountDTO) {

        try {
            UUID clientId = accountDTO.getClient().getId();
            Optional<Client> optionalClient = clientRepository.findById(clientId);

            if (optionalClient.isEmpty()) {
                return null;
            }

            Account account = new Account(
                    accountDTO.getCreationTime(),
                    accountDTO.getBalance(),
                    accountDTO.getActive(),
                    optionalClient.get());

            if (accountDTO.getId() != null) {
                account.setId(accountDTO.getId());
            }
            return account;
        } catch (IllegalArgumentException e) {
            // e.printStackTrace();
            System.err.printf("Les éléments ne permettent pas la convertion en entité : ", e.getMessage());
        }

        return null;

    }

    /**
     * Retour de la liste de compte
     *
     * @return
     */
    public List<AccountDTO> getAllOwners() {
        /* Première version */
        // List<Account> listEntities = accountRepository.findAll();
        // List<AccountDTO> listDTO = new ArrayList<>();

        // for (Account account : listEntities) {
        // listDTO.add(convertToDTO(account));
        // }
        // return listDTO;

        /* Seconde version (en utilisant Steam) */
        return accountRepository.findAll()
                .stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
    }

    /**
     * Retourne un compte avec son client à partir de son ID
     * 
     * @param id
     * @return
     */
    public AccountDTO getAccountById(Long id) {
        return convertToDTO(accountRepository.findById(id).orElse(null));
    }

    /**
     * Creation d'un compte d'un client
     * 
     * @param accountDTO
     * @return retour d'un ResponseEntity<AccountDTO> après convertion en AccountDTO
     *         du retour
     *         de la sauvegarde de l'entité qui, auparavant, provient d'un
     *         AccountDTO converti en
     *         entité
     */
    public ResponseEntity<AccountDTO> createAccount(AccountDTO accountDTO) {
        return new ResponseEntity<AccountDTO>(convertToDTO(
                accountRepository.save(
                        convertToAccount(accountDTO))),
                HttpStatus.CREATED);
    }

    /**
     * Update Account
     * 
     * @param id
     * @param accountDTO
     * 
     *                   Vérifie que l'entité ayant l'Id entré en param1 est
     *                   identique
     *                   l'id de l'AccountDTO entré en param2.
     *                   Plusieurs vérification effectué pour permettre de
     *                   sauvegarder ou pas
     *                   les éléments rentrés
     *                   Plusieurs réponse de prévu selon l'accomplissement de la
     *                   méthode
     */
    public ResponseEntity<AccountDTO> updateAccount(long id, AccountDTO accountDTO) {
        Optional<Account> originalAccount = accountRepository.findById(id);

        if (originalAccount.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        if (id != accountDTO.getId()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Account account = originalAccount.get();
        account.setBalance(accountDTO.getBalance());
        account.setActive(accountDTO.getActive());
        accountRepository.save(account);

        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);

    }

    public void removeAccout(long id, HttpServletResponse response) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}