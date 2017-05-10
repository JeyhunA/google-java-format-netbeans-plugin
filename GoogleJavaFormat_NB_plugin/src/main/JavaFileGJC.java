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
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.cookies.EditorCookie;
        
@ActionID(
        category = "Refactoring",
        id = "JavaCodeStyle.GoogleStyle"
)
@ActionRegistration(
        iconBase = "main/html_x16.png",
        displayName = "#CTL_GoogleStyle"
)

@ActionReferences({
    @ActionReference(path = "Menu/Refactoring", position = -100, separatorAfter= -50),
    @ActionReference(path = "Toolbars/Build", position = 600, separatorAfter = 650)
})

@Messages("CTL_GoogleStyle=Apply GoogleJavaCode style to file")


public final class JavaFileGJC implements ActionListener {

    private final EditorCookie context;

    public JavaFileGJC(EditorCookie context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        try {
            JEditorPane pane = context.getOpenedPanes()[0];
            String code = pane.getText();            
            Formatter formatter = new Formatter();
            code = formatter.formatSource(code);
            pane.setText(code);
            pane.setCaretPosition(0);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Something gets wrong!\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}