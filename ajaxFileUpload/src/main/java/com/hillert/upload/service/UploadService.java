/**
 * Gunnar Hillert, 2006
 */
package com.hillert.upload.service;

import java.io.InputStream;

/**
 * Central service class.
 *
 * @author Gunnar Hillert
 *
 */
public interface UploadService {

    /**
     * Save the file to the default temp directory.
     * @param inputStream Data of the file
     * @param fileName Name of the file uploaded
     */
    void saveFile(InputStream inputStream, String fileName);

}
