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

package main;

import com.google.googlejavaformat.java.Formatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;
import org.netbeans.api.java.project.JavaProjectConstants;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import utils.Common;
import utils.FileAdaptor;

@ActionID(
        category = "Refactoring",
        id = "main.ProjectSrcGJC"
)
@ActionRegistration(
        iconBase = "main/ux-design_x16.png",
        displayName = "#CTL_ProjectSrcGJC"
)
@ActionReferences({
    @ActionReference(path = "Menu/Refactoring", position = -500),
    @ActionReference(path = "Toolbars/Build", position = -20)
})
@Messages("CTL_ProjectSrcGJC=Apply GoogleJavaCode style to all project java sources")

public final class ProjectSrcGJC implements ActionListener {

    private final Project project;

    public ProjectSrcGJC(Project project) {
        this.project = project;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {                
        try {
            if(project!=null) {
                //System.out.println("PLUGIN-> Project directory: " + project.getProjectDirectory().getPath().toString());
                Sources src =  ProjectUtils.getSources(project);
                SourceGroup [] sg = src.getSourceGroups(JavaProjectConstants.SOURCES_TYPE_JAVA);
                
                /*
                for (SourceGroup s : sg) {
                    System.out.println(s.getRootFolder().getPath());
                    System.out.println(s.getRootFolder().toURL());
                    System.out.println(s.getRootFolder().toURL().getFile());
                    System.out.println(s.getRootFolder().toURL().getPath());                    
                }
                */
                
                boolean makeBackup = false;
                int option = JOptionPane.showConfirmDialog(null, "Do you want backup sources?", "GoogleJavaCodeStyle plugin", JOptionPane.YES_NO_OPTION);
                
                switch(option) {
                    case JOptionPane.YES_OPTION: 
                        makeBackup = true;
                        break;
                    case JOptionPane.NO_OPTION: 
                        makeBackup = false;
                        break;
                    default:
                        makeBackup = false;
                }
                
                
                File source = null;
                File target = null;
                
                if (sg.length != 0) {
                    // backup current sources
                    if (makeBackup) {
                        target = new File(project.getProjectDirectory().getPath()+File.separator+"back_"+DateFormat.getDateInstance().format(Calendar.getInstance().getTime()));
                        target.mkdir();
                    }
                    for (SourceGroup s: sg) {
                        source = new File(s.getRootFolder().getPath());
                        //System.out.println(s.getRootFolder().getName());
                        //source = new File(s.getRootFolder().getParent().getPath());
                        if (makeBackup) Common.copyFolder(source, new File(target.getAbsolutePath()+File.separator+s.getRootFolder().getName()));
                        // change original sources
                        List<File> files = new ArrayList<File>();
                        Common.getFilesTree(source, files);
                        if (!files.isEmpty()) {
                            File [] arr = files.toArray(new File[files.size()]);                
                            for(int i = 0; i < arr.length; ++i) {
                                String st = FileAdaptor.readFromFileN(arr[i].getAbsolutePath());
                                Formatter formatter = new Formatter();
                                st = formatter.formatSource(st);
                                FileAdaptor.writeToFile(arr[i].getAbsolutePath(), st);                                
                            }
                        }
                    }
                } else JOptionPane.showMessageDialog(null, "Source files not found.", "Error", JOptionPane.ERROR_MESSAGE);
            } else 
                JOptionPane.showMessageDialog(null, "Project not found.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something gets wrong!\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
