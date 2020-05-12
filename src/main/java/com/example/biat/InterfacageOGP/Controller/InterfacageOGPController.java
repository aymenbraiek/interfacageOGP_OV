package com.example.biat.InterfacageOGP.Controller;


import java.io.IOException;
import java.net.SocketException;
import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import com.example.biat.InterfacageOGP.Service.NiveauProjetService;


import java.util.regex.Pattern;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "this my OGP Controller")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/biat")
@Slf4j
public class InterfacageOGPController {
    // private static final Logger LOGGER = LoggerFactory.getLogger(InterfacageOGPController.class);
    @Qualifier("OVDataSource")
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NiveauProjetService niveauservice;
    int retVal = 0, reval_1, lengthname, lengtabriavation;
    Integer resultname, resultabriavation, resultidprojectAndtype;

    private static final String sqlnameexistorNo = "select count(*) from niveauprojet where nom = ?";
    private static final String sqlabriviation = "select count(*) from niveauprojet where abreviation = ?";
    private static final String sqlfortypeandinstance = "select count(*) from niveauprojet where typeprojet=? and idProjet=?";
    Boolean validedname, validedabriavation;
    String message;

    Boolean checkCractername = false, checkCracterabriviation = false, hasUppercase = false, actifNotValid = false;
    private static final String path = "C:\\Users\\trac.admin\\Bitnami_Trac_Stack_projects\\";
    private static final String pathcom = "\\conf\\trac.ini";
    private final String path_1 = "C:\\Users\\trac.admin\\Bitnami_Trac_Stack_projects\\anomalies_t24\\conf\\trac.ini";
    private static final String path_2 = "C:\\Users\\trac.admin\\Bitnami_Trac_Stack_projects\\livraisons_t24\\conf\\trac.ini";


    @GetMapping("/FindByName")
    @ApiOperation(value = "Find  niveau projets by name")
    public int getNiveauProjetByName(@RequestParam("name") String name) {
        Integer queryForObject = jdbcTemplate.queryForObject("select count(*) from niveauprojet where nom = ?",
                Integer.class, name);
        System.out.println(queryForObject);
        return queryForObject == null ? 0 : queryForObject;
    }

    @GetMapping("/FindByidProjet")
    @ApiOperation(value = "Find  niveau projets by idprojet")
    public int getNiveauProjetByIdPorjet(@RequestParam("idProjet") String idProjet) {

        Integer queryForObject = jdbcTemplate.queryForObject("select count(*) from niveauprojet where idProjet = ?",
                Integer.class, idProjet);
        System.out.println(queryForObject);
        return queryForObject == null ? 0 : queryForObject;
    }

    @GetMapping("/FindProjet")
    @ApiOperation(value = "Find  niveau projets by name")
    public List<Map<String, Object>> getProjetByName(@RequestParam("name") String name) {
        final List<Map<String, Object>> results = jdbcTemplate.queryForList(
                "SELECT * FROM niveauprojet WHERE nom=?", name);

        return results == null ? null : results;
    }


    @GetMapping("/FindByAbreviation")
    @ApiOperation(value = "Find  niveau projets by Abreviation")
    public int getNiveauProjetByAbreviation(@RequestParam("abreviation") String abreviation) {

        Integer queryForObject = jdbcTemplate.queryForObject("select count(*) from niveauprojet where abreviation = ?",
                Integer.class, abreviation);
        System.out.println(queryForObject);
        return queryForObject == null ? 0 : queryForObject;
    }


    @PostMapping("/addNiveauProjet")
    @ResponseBody
    @ApiOperation(value = "create new NiveauProjet")
    public String addNiveauprojet(@RequestParam("name") String name, @RequestParam("abriavation") String abriavation,
                                  @RequestParam("actif") String actif, @RequestParam("releaseprevue") String releaseprevue,
                                  @RequestParam("metier") String metier
            , @RequestParam("idPoject") String idPoject, @RequestParam("instancetrac") String instancetrac) throws SocketException, IOException {

        StringBuilder builder = new StringBuilder();
        StringBuilder builderPath = new StringBuilder(path);
        log.debug("path" + builderPath.toString());
        log.debug("-----------------------------------");
        System.out.println("------------------------");
        log.debug("-----------------------------------");
        resultname = jdbcTemplate.queryForObject(sqlnameexistorNo, Integer.class, name);
        resultabriavation = jdbcTemplate.queryForObject(sqlabriviation, Integer.class, abriavation);
        resultidprojectAndtype = jdbcTemplate.queryForObject(sqlfortypeandinstance, Integer.class, instancetrac, idPoject);
        System.out.println(resultidprojectAndtype);
        log.debug("" + resultidprojectAndtype);
        List<Map<String, Object>> listVers = jdbcTemplate
                .queryForList("select * from environnement where nom='VERSIONNING'");

        // traitement circuit
        System.out.println("------------------------");
        switch (actif) {
            case "CIRCUIT PROJET":
                actif = "CP";
                break;
            case "CIRCUIT RELEASE":
                actif = "CR";
                break;
            case "CIRCUIT DIGITAL":
                actif = "CD";
                break;
            default:
                actif = "CH";
                break;
        }

        System.out.println("------------------------");
        System.out.println("actif" + actif);
        System.out.println("------------------------");
        String nom = (String) listVers.get(0).get("nom");
        String url = (String) listVers.get(0).get("url");
        String username = (String) listVers.get(0).get("envUserName");
        String password = (String) listVers.get(0).get("envPassword");
        String commandeList = "mkdir test2020555555";
        // String commandeList = "SVN_NEW_BI" + " " + abriavation;
        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
        for (int i = 0; i < abriavation.length(); i++) {
            hasUppercase = !Character.isUpperCase(abriavation.charAt(i)) ? true : false;
        }
        checkCractername = regex.matcher(name).find() ? true : false;
        checkCracterabriviation = regex.matcher(abriavation).find() ? true : false;
        lengthname = name.length();
        lengtabriavation = abriavation.length();
        System.out.println(lengthname);
        validedname = resultname == 0 && !checkCractername && lengthname < 70 && lengthname > 0 ? true : false;
        System.out.println("-----------------------");

        validedabriavation = resultabriavation == 0 && !checkCracterabriviation && !hasUppercase && lengtabriavation == 4 ? true : false;
        System.out.println("resulname" + validedname);
        System.out.println("resulat abriavation" + validedabriavation);
        if (!validedname) {

            message = "Merci de vérifier que :Le nom de projet n’existe pas,Le nom de projet ne contient pas des caractères spéciaux,Le nombre des caractères saisies ne dépasse pas les 70 caractères";
            // return message;
            builder.append("\n");
            builder.append(message);
        }
        if (!validedabriavation) {

            message = "Merci de saisir une abréviation composée de 4 lettre en majuscule.";
            builder.append("\n");
            builder.append(message);
        }
        if (resultidprojectAndtype == 1) {

            message = "Une instance TRAC est déjà liée à cet ID Projet.";
            builder.append("\n");
            builder.append(message);
        }


        if (validedname && validedabriavation && resultidprojectAndtype == 0) {

            retVal = jdbcTemplate.update("INSERT INTO niveauprojet VALUES (?,?,?,?,?,?,?,?)", name, abriavation, actif, releaseprevue, metier, instancetrac, "BIAT", idPoject);
            switch (instancetrac) {
                case "T24":
                    log.info("instance trac t24 anomalie et livraison");
                    niveauservice.WriteTracini(path_1, name);
                    niveauservice.WriteTracini(path_2, name);
                    break;
                default:
                    builderPath.append(instancetrac);
                    builderPath.append(pathcom);
                    log.info("path final" + builderPath.toString());
                    System.out.println(builderPath);
                    niveauservice.WriteTracini(builderPath.toString(), name);
                    break;
            }

            String resultatExecution = niveauservice.executerCommandeListEnvironnementCreateProjet(url, username, password, commandeList, nom);
            System.out.println(resultatExecution.toString());
            System.out.println("result insert" + retVal);

        }

        if (retVal >= 1) {
            System.out.println("result insert" + retVal);
            message = "Projet A été créé avec succes";
            builder.append("\n");
            builder.append(message);
        } //else {
        // message = "some thing is wrong!!!!!!!";
        //  builder.append("\n");
        //  builder.append(message);
        // }

        String result = builder.toString();
        return result;

    }


}
