package fr.afpa.orm.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import fr.afpa.orm.dto.ClientDTO;
import fr.afpa.orm.entities.Client;
import fr.afpa.orm.repositories.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;

/**
 * TD on partira du principe qu'en raison du principe de responsabilité unique
 * le service ne devra pas nécessairement traiter les résponse HTTP
 * C'est une responsabilité que l'on fera porté au contrôleur
 * 
 * Dans les grande lignes :
 * le service contiendra le code métier (ici uniquement l'appel au repository)
 * Tout ce qui touche au protocol HTTP pourra être mis dans le contrôleur/
 * 
 * Par exemple "ResponseEntity<ClientDTO> getOneClient(UUID id)"
 * pourrais devenir "ClientDTO getOneClient(UUID id)"
 * 
 * Et ça sera au contrôleur de créer un ResponseEntity
 * 
 * Que est l'intérêt ?
 * -> permet de limiter le couplage entre le service (logique métier) et le
 * protocol HTTP
 * --> le service devient réutilisable même dans un projet qui n'est pas une Web
 * API
 * --> si la bibliothèque HTTP change (en raison d'une mise à jour, par
 * exemple), il n'est pas nécessaire de changer les services
 */
@Service
public class ClientService {
    /**
     * TD est-il possible de faire l'injection de dépendance via le constructeur ?
     * Plus d'informations ici :
     * https://keyboardplaying.fr/blogue/2021/01/spring-injection-constructeur/
     */
    // @Autowired
    // ClientRepository clientRepository;

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Convert a Entity to DTO
     * 
     * @param client
     * @return
     */
    private ClientDTO convertToDTO(Client client) {
        return new ClientDTO(client);
    }

    /**
     * Convert a DTO to Entity
     * 
     * @param clientDTO
     * @return
     */
    private Client convertToEntity(ClientDTO clientDTO) {
        return new Client(clientDTO);
    }

    /**
     * Return All Clients
     * 
     * @return
     *         tout les clients
     */
    public List<ClientDTO> getAllClient() {
        return clientRepository.findAll()
                .stream()
                .map(client -> new ClientDTO(client))
                .collect(Collectors.toList());
    }

    /**
     * Return one Client by ID
     * 
     * @param id
     * @return ClientDTO
     *  retourne un client avec l'ID
     */
    public ClientDTO getOneClient(UUID id) {
        return convertToDTO(clientRepository.findById(id).orElse(null));
    }

    /**
     * 
     * Create Client
     * 
     * @param clientDTO
     * @return
     */
    public ClientDTO createClient(ClientDTO clientDTO) {
        return convertToDTO(
                clientRepository.save(
                        convertToEntity(clientDTO)));
    }

    /**
     * Update client
     * 
     * @param id
     * @param clientDTO
     * @return
     * 
     *         on vérifie qu'un Client existe par le biais de l'ID en param
     *         on vérifie que l'ID en param est conforme à l'ID du DTO en param
     *         on applique les changements désignés à une entité avant de faire
     *         un "save"
     *         on retourne une ResponseEntity avec l'entity convertie en DTO
     */
    public ClientDTO updateClient(UUID id, ClientDTO clientDTO) {
        Optional<Client> originalClient = clientRepository.findById(id);

        if (originalClient.isEmpty()) {
            throw new EntityNotFoundException("Client not found");
        }

        if (!id.equals(clientDTO.getId())) {
            throw new IllegalArgumentException("ID mismatch between path and body");
        }

        Client client = originalClient.get();
        client.setFirstName(clientDTO.getFirstName());
        client.setLastName(clientDTO.getLastName());
        client.setBirthdate(clientDTO.getBirthDate());

        return convertToDTO(clientRepository.save(client));
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


/**
 * getAllClientsByInsuranceId
 * @param id
 * @return
 * 
 * renvoie la liste (en DTO) des clients à paritr de l'ID de l'assurance
 */
    public List<ClientDTO> getAllClientsByInsuranceId(Long id) {
        List<Client> clients = clientRepository.findAllByInsuranceId(id);

        if (clients.isEmpty()) {
            throw new EntityNotFoundException("Clients list by Insurance not found");
        }

        List<ClientDTO> clientsDTO = new ArrayList<>();

        for (Client client : clients) {
            clientsDTO.add(convertToDTO(client));
        }

        return clientsDTO;
    }
}
