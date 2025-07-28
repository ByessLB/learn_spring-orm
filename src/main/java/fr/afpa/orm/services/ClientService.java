package fr.afpa.orm.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fr.afpa.orm.dto.ClientDTO;
import fr.afpa.orm.entities.Client;
import fr.afpa.orm.repositories.AccountRepository;
import fr.afpa.orm.repositories.ClientRepository;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    /**
     * Convert a Entity to DTO
     * @param client
     * @return
     */
    private ClientDTO convertToDTO(Client client) {
        return new ClientDTO(client);
    }

    /**
     * Convert a DTO to Entity
     * @param clientDTO
     * @return
     */
    private Client convertToEntity(ClientDTO clientDTO) {
        return new Client(clientDTO);
    }

    /**
     * Return All Clients
     * @return
     * tout les clients
     */
    public List<ClientDTO> getAllClient() {
        return clientRepository.findAll()
                    .stream()
                    .map(client -> new ClientDTO(client))
                    .collect(Collectors.toList());
    }

    /**
     * Return one Client by ID
     * @param id
     * @return
     */
    public ResponseEntity<ClientDTO> getOneClient(UUID id) {
        return new ResponseEntity<ClientDTO>(convertToDTO(clientRepository.findById(id).orElse(null)), HttpStatus.ACCEPTED);
    }

    /**
     * Create Client
     * @param clientDTO
     * @return
     */
    public ResponseEntity<ClientDTO> createClient(ClientDTO clientDTO) {
        return new ResponseEntity<ClientDTO>(convertToDTO(
                clientRepository.save(
                        convertToEntity(clientDTO)
                )),
                HttpStatus.CREATED);
    }

    /**
     * Update client
     * @param id
     * @param clientDTO
     * @return
     * 
     * on vérifie qu'un Client existe par le biais de l'ID en param
     * on vérifie que l'ID en param est conforme à l'ID du DTO en param
     * on applique les changements désignés à une entité avant de faire 
     * un "save"
     * on retourne une ResponseEntity avec l'entity convertie en DTO
     */
    public ResponseEntity<ClientDTO> updateClient (UUID id, ClientDTO clientDTO) {
        Optional<Client> originalClient = clientRepository.findById(id);

        if (originalClient.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        if (id != clientDTO.getId()) {
            return new ResponseEntity<> (null, HttpStatus.BAD_REQUEST);
        }

        Client client = originalClient.get();
        client.setFirstName(clientDTO.getFirstName());
        client.setLastName(clientDTO.getLastName());
        client.setBirthdate(clientDTO.getBirthDate());
        clientRepository.save(client);

        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }

    /**
     * Delete client
     *
     * @param id
     * @param response
     */
    public void removeClient(UUID id, HttpServletResponse response) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
