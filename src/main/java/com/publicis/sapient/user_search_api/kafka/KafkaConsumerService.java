package com.publicis.sapient.user_search_api.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.publicis.sapient.user_search_api.model.*;
import com.publicis.sapient.user_search_api.repository.*;
import com.publicis.sapient.user_search_api.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.StaleObjectStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KafkaConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CoordinatesRepository coordinatesRepository;
    @Autowired
    private HairRepository hairRepository;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CrptoRepository cryptoRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public KafkaConsumerService( UserService userService) {
        this.userService = userService;
        this.objectMapper = new ObjectMapper();
    }

    @Transactional
    @KafkaListener(topics = "users_topic", groupId = "users-group")
    public void listenToUsersTopic(String message, Acknowledgment acknowledgment) {
        try {
            logger.info("Received message: {}", message);
            Map<String, Object> userMap = objectMapper.readValue(message, Map.class);
            User user = mapToUser(userMap);
            logger.info("Mapped user: {}", user);
            userService.saveUser(user);
            logger.info("User saved with ID: {}", user.getId());
        } catch (JsonProcessingException e) {
            logger.error("Error parsing Kafka message: {}", message, e);
        } catch (Exception e) {
            logger.error("Error processing Kafka message", e);
        }
    }
    public User mapToUser(Map<String, Object> userMap) {
        User user = new User();
        try {
            user.setId((int) ((Number) userMap.get("id")).longValue());
            user.setFirstName((String) userMap.get("firstName"));
            user.setLastName((String) userMap.get("lastName"));
            user.setMaidenName((String) userMap.get("maidenName"));
            user.setAge((Integer) userMap.get("age"));
            user.setGender((String) userMap.get("gender"));
            user.setEmail((String) userMap.get("email"));
            user.setPhone((String) userMap.get("phone"));
            user.setUsername((String) userMap.get("username"));
            user.setPassword((String) userMap.get("password"));
            user.setBirthDate((String) userMap.get("birthDate"));
            user.setImage((String) userMap.get("image"));
            user.setBloodGroup((String) userMap.get("bloodGroup"));
            user.setHeight((Double) userMap.get("height"));
            user.setWeight((Double) userMap.get("weight"));
            user.setEyeColor((String) userMap.get("eyeColor"));
            user.setIp((String) userMap.get("ip"));
            user.setMacAddress((String) userMap.get("macAddress"));
            user.setUniversity((String) userMap.get("university"));
            user.setEin((String) userMap.get("ein"));
            user.setSsn((String) userMap.get("ssn"));
            user.setUserAgent((String) userMap.get("userAgent"));
            user.setRole((String) userMap.get("role"));

            // Mapping for embedded objects
            if (userMap.get("hair") != null) {
                Map<String, Object> hairMap = (Map<String, Object>) userMap.get("hair");
                Hair hair = new Hair();
                hair.setColor((String) hairMap.get("color"));
                hair.setType((String) hairMap.get("type"));
                user.setHair(hairRepository.save(hair));  // Save or use if already exists
            }

            if (userMap.get("address") != null) {
                Map<String, Object> addressMap = (Map<String, Object>) userMap.get("address");
                Address address = new Address();
                address.setAddress((String) addressMap.get("address"));
                address.setCity((String) addressMap.get("city"));
                address.setState((String) addressMap.get("state"));
                address.setStateCode((String) addressMap.get("stateCode"));
                address.setPostalCode((String) addressMap.get("postalCode"));

                if (addressMap.get("coordinates") != null) {
                    Map<String, Object> coordinatesMap = (Map<String, Object>) addressMap.get("coordinates");
                    Coordinates coordinates = new Coordinates();
                    coordinates.setLat((Double) coordinatesMap.get("lat"));
                    coordinates.setLng((Double) coordinatesMap.get("lng"));
                    address.setCoordinates(coordinatesRepository.save(coordinates)); // Save coordinates if needed
                }

                address.setCountry((String) addressMap.get("country"));
                user.setAddress(addressRepository.save(address));  // Save or use if already exists
            }

            if (userMap.get("bank") != null) {
                Map<String, Object> bankMap = (Map<String, Object>) userMap.get("bank");
                Bank bank = new Bank();
                bank.setCardExpire((String) bankMap.get("cardExpire"));
                bank.setCardNumber((String) bankMap.get("cardNumber"));
                bank.setCardType((String) bankMap.get("cardType"));
                bank.setCurrency((String) bankMap.get("currency"));
                bank.setIban((String) bankMap.get("iban"));
                user.setBank(bankRepository.save(bank)); // Save bank if needed
            }

            if (userMap.get("company") != null) {
                Map<String, Object> companyMap = (Map<String, Object>) userMap.get("company");
                Company company = new Company();
                company.setDepartment((String) companyMap.get("department"));
                company.setName((String) companyMap.get("name"));
                company.setTitle((String) companyMap.get("title"));

                if (companyMap.get("address") != null) {
                    Map<String, Object> companyAddressMap = (Map<String, Object>) companyMap.get("address");
                    Address companyAddress = new Address();
                    companyAddress.setAddress((String) companyAddressMap.get("address"));
                    companyAddress.setCity((String) companyAddressMap.get("city"));
                    companyAddress.setState((String) companyAddressMap.get("state"));
                    companyAddress.setStateCode((String) companyAddressMap.get("stateCode"));
                    companyAddress.setPostalCode((String) companyAddressMap.get("postalCode"));
                    company.setAddress(addressRepository.save(companyAddress)); // Save or use if already exists
                }

                user.setCompany(companyRepository.save(company)); // Save company if needed
            }

            if (userMap.get("crypto") != null) {
                Map<String, Object> cryptoMap = (Map<String, Object>) userMap.get("crypto");
                Crypto crypto = new Crypto();
                crypto.setCoin((String) cryptoMap.get("coin"));
                crypto.setWallet((String) cryptoMap.get("wallet"));
                crypto.setNetwork((String) cryptoMap.get("network"));
                user.setCrypto(cryptoRepository.save(crypto)); // Save crypto if needed
            }

        } catch (Exception e) {
            logger.error("Error mapping user data from message: {}", userMap, e);
        }
        return user;
    }



}

