package com.example.biat.InterfacageOGP.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.telnet.TelnetClient;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NiveauProjetService {


    public String executerCommandeListEnvironnementCreateProjet(String url, String username, String password,
                                                                String commandeList, String nom) {
        TelnetClient telnet = null;

        InputStream in = null;
        PrintStream out = null;
        String resultatCommande = "";
        telnet = new TelnetClient();
        T24Tools t24tools;

        try {
            try {
                System.out.println("connection telnet serveur : " + url + ": port 23");
                telnet.connect(url, 23);
            } catch (Exception e) {

                telnet.connect(url, 23);
            }
            // Get input and output stream references
            in = telnet.getInputStream();
            // System.out.println(in.toString());
            out = new PrintStream(telnet.getOutputStream());
            // System.out.println(out.toString());
            t24tools = new T24Tools(in, out);
            t24tools.readUntil("login: ");
            t24tools.write(username);
            t24tools.readUntil("Password: ");
            t24tools.write(password + "\n\n");
            if (nom.equals("VERSIONNING")) {
                t24tools.readUntil("#");
            } // else {
            // t24tools.readUntil("-->");
            // }
            t24tools.write("bash\n");
            t24tools.readUntil("#");
            t24tools.write(commandeList + ";echo END.PROCESS");
            t24tools.readUntil("END.PROCESS");
            t24tools.write("exit");

            try {
                in.close();
                out.close();
            } catch (Exception e) {
            }
        } catch (Exception exep) {
            exep.printStackTrace();

        } finally {

        }

        return resultatCommande;
    }


    public void WriteTracini(String pathfile, String nom) {
        try {

            String FILE = pathfile;
            List<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get(FILE), StandardCharsets.UTF_8));

            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).contains("projet.options =")) {
                    fileContent.set(i, fileContent.get(i) + "|" + nom);
                    break;
                }
            }

            Files.write(Paths.get(FILE), fileContent, StandardCharsets.UTF_8);

        } catch (Exception e) {
            System.out.println("Problem reading file.");
            log.error("Problem reading file.");
        }

    }


}
	

