package com.hillert.upload.web.formobjects;

import org.springframework.web.multipart.MultipartFile;

/**
 * This class represents a Data Import form.
 *
 * @author Gunnar Hillert
 */
public class DataImportForm {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The imported file.
     */
    private MultipartFile file;

    /**
     * @return Returns the file.
     */
    public final MultipartFile getFile() {
        return file;
    }

    /**
     * @param file The file to set.
     */
    public final void setFile(final MultipartFile file) {
        this.file = file;
    }

}
