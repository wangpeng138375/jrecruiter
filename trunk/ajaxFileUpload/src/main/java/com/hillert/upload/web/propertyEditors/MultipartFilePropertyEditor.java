package com.hillert.upload.web.propertyEditors;

import java.beans.PropertyEditorSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Gunnar Hillert
 *
 */
public class MultipartFilePropertyEditor extends PropertyEditorSupport {

    /**
     * Logger declaration.
     */
    private static final Log LOGGER = LogFactory
            .getLog(MultipartFilePropertyEditor.class);

    /**
     * Constructor.
     */
    public MultipartFilePropertyEditor() {
        super();
    }

    /**
     * Sets the multipart file into the property editor
     *
     * @param value The multipart file
     */
    public void setValue(final Object value) {
        if (LOGGER.isDebugEnabled()) {
            if (value != null) {
                LOGGER.debug("Object = " + value.getClass().getName());
            } else {
                LOGGER.debug("Object = null");
            }
        }

        if (value instanceof MultipartFile) {
            super.setValue(value);
        } else if (value != null) {
            LOGGER.error("Object is not a multipart file - "
                    + value.getClass().getName());
        }
    }

    /**
     * Overrides the set as text method so no exceptions are thrown
     *
     * @param text The text
     * @throws IllegalArgumentException If either the String is
     * badly formatted or if this kind of property can't be expressed
     * as text
     */
    public void setAsText(final String text) throws IllegalArgumentException {
        return;
    }

    /**
     * Retrieves a textual representation of the multipart
     * file from the property editor.
     *
     * @return A textual representation of the multipart file
     */
    public String getAsText() {
        Object value = getValue();

        if (value != null) {
            return (((MultipartFile) value).getOriginalFilename());
        }

        return ("");
    }
}
