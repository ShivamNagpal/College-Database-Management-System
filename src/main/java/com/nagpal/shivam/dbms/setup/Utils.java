package com.nagpal.shivam.dbms.setup;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Utils {
    static String[] getRootCredentials() throws IOException {
        int n = 4;
        String[] credentials = new String[n];
        Scanner scanner = new Scanner(new File("root_credentials.txt"));
        for (int i = 0; i < n; i++) {
            credentials[i] = scanner.nextLine();
        }

        return credentials;
    }
}
