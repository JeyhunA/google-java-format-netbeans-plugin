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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

/** @author Stranger */
public class Common {

  private Common() {}

  public static void getFilesTree(File rootFile, List<File> files) {
    File[] list = rootFile.listFiles();
    if (list != null) {
      for (File f : list) {
        if (f.isDirectory()) getFilesTree(f, files);
        if (f.isFile() && f.getName().endsWith(".java")) {
          files.add(f);
        }
      }
    }
  }

  public static void copyFolder(File sourceFolder, File destinationFolder) throws IOException {
    //Check if sourceFolder is a directory or file
    //If sourceFolder is file; then copy the file directly to new location
    System.out.println(sourceFolder.toString());
    System.out.println(destinationFolder.toString());
    if (sourceFolder.isDirectory()) {
      //Verify if destinationFolder is already present; If not then create it
      if (!destinationFolder.exists()) {
        destinationFolder.mkdir();
        //System.out.println("Directory created :: " + destinationFolder); // debug
      }

      //Get all files from source directory
      String files[] = sourceFolder.list();

      //Iterate over all files and copy them to destinationFolder one by one
      for (String file : files) {
        File srcFile = new File(sourceFolder, file);
        File destFile = new File(destinationFolder, file);

        //Recursive function call
        copyFolder(srcFile, destFile);
      }
    } else {
      Files.copy(
          sourceFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
      //System.out.println("File copied :: " + destinationFolder); // debug
    }
  }
}
