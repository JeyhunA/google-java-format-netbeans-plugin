/*
 * Copyright 2017 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Stranger
 */
public class FileAdaptor {
    
    private FileAdaptor() {
        
    }
    
    /**
     * Reads text from a file with line separator
     *
     * @param fileName file name
     * @return file content as a string
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String readFromFileN(String fileName) throws FileNotFoundException, IOException {

        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        String line, result_string = "";

        try {
            while ((line = reader.readLine()) != null) {
                result_string += line + "\n";
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return result_string;
    }

    /**
     * Reads text from a file
     *
     * @param fileName file name
     * @return file content as a string
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String readFromFile(String fileName) throws FileNotFoundException, IOException {

        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        String line, result_string = "";

        try {
            while ((line = reader.readLine()) != null) {
                result_string += line;
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return result_string;
    }    
    
    /**
     * Writes text to a file
     *
     * @param fileName file name
     * @param text text for writing
     */
    public static void writeToFile(String fileName, String text) throws IOException {

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(text);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
    
}
