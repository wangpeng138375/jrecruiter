/*
 * Copyright 2002-2005 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hillert.upload.web.ajax;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import be.telio.mediastore.ui.upload.MonitoredDiskFileItemFactory;
import be.telio.mediastore.ui.upload.UploadListener;

/**
 *
 * This class extends Spring's CommonsMultipartResolver (Spring 2.0)
 * and overrides the resolveMultipart method. A few lines of code have
 * been added to that method.
 *
 * @author Gunnar Hillert
 *
 * @see org.springframework.web.multipart.commons.CommonsMultipartResolver
 */
public class AjaxFileUploadMultipartResolver extends CommonsMultipartResolver {

    /**
     * Constructor.
     */
    public AjaxFileUploadMultipartResolver() {
        super();
    }

    /**
     * Constructor for standalone usage. Determines the servlet container's
     * temporary directory via the given ServletContext.
     *
     * @param servletContext
     *            the ServletContext to use
     */
    public AjaxFileUploadMultipartResolver(ServletContext servletContext) {
        this();
        setServletContext(servletContext);
    }

    public MultipartHttpServletRequest resolveMultipart(
            HttpServletRequest request) throws MultipartException {
        String encoding = determineEncoding(request);
        FileUpload fileUpload1 =
               prepareFileUpload(encoding); //renamed fileUpload to fileUpload1

        // Beginn of added code
        UploadListener listener = new UploadListener(request, 30);
        FileItemFactory factory = new MonitoredDiskFileItemFactory(listener);
        ServletFileUpload fileUpload = new ServletFileUpload(factory);
        fileUpload.setSizeMax(fileUpload1.getSizeMax());
        fileUpload.setHeaderEncoding(fileUpload1.getHeaderEncoding());
        //end of added code

        try {
            List fileItems = ((ServletFileUpload) fileUpload)
                    .parseRequest(request);
            MultipartParsingResult parsingResult = parseFileItems(fileItems,
                    encoding);
            return new DefaultMultipartHttpServletRequest(request,
                    parsingResult.getMultipartFiles(), parsingResult
                            .getMultipartParameters());
        } catch (FileUploadBase.SizeLimitExceededException ex) {
            throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(),
                    ex);
        } catch (FileUploadException ex) {
            throw new MultipartException(
                    "Could not parse multipart servlet request", ex);
        }
    }

}

