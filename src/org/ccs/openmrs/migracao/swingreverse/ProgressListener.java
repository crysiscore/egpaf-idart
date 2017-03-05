/*
 * Decompiled with CFR 0_114.
 */
package org.ccs.openmrs.migracao.swingreverse;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JProgressBar;

class ProgressListener
implements PropertyChangeListener {
    private final JProgressBar progressBar;

    ProgressListener(JProgressBar progressBar) {
        this.progressBar = progressBar;
        this.progressBar.setValue(0);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String strPropertyName = evt.getPropertyName();
        if ("progress".equals(strPropertyName)) {
            this.progressBar.setIndeterminate(false);
            int progress = (Integer)evt.getNewValue();
            this.progressBar.setValue(progress);
        }
    }
}

